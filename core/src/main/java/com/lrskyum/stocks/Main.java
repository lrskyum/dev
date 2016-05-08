/*
 * Copyright (c) 2015.
 * Lars Ruben Skyum
 */
package com.lrskyum.stocks;


import com.lrskyum.stocks.domain.*;
import com.lrskyum.stocks.domain.impl.TimeSeriesImpl;
import com.lrskyum.stocks.domain.impl.strategy.SmaTradingStrategy;

import java.io.IOException;
import java.time.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    private static final String aSymbol = "msft";
    private static final double aKurtagePercent = 0.5;
    private static final DataFactory aDataFactory = new DataFactory();

    public interface Production {
        Quote apply(Quote q1, Quote q2);
    }

    public class ProdutionImpl implements Production {
        @Override
        public Quote apply(Quote q1, Quote q2) {
            return null; // FXIME
        }
    }

    public static TimeSeries<Quote> convert(TimeSeries<? extends Quote> quote, TimeSeries<? extends Quote> currency, Production p) throws IOException {
        TimeSeries<Quote> result = aDataFactory.createTimeSeries(currency.getCurrency());

        Iterator<? extends Quote> i1 = quote.iterator();
        Iterator<? extends Quote> i2 = currency.iterator();
        Quote q1, q2 = null;
        outer:
        while (i1.hasNext() && i2.hasNext()) {
            q1 = i1.next();
            int cmp = -1; // Anything but 0
            while (q2 == null && i2.hasNext()) {
                q2 = i2.next();
                cmp = q1.getDateTime().compareTo(q2.getDateTime());
                if (cmp == 0) {
                    // Match, same date, so put in result list
                    result.add(p.apply(q1, q2));
                    break;
                } else if (cmp > 0) {
                    // s1 is lagging, so get next q1 but keep q2
                    break outer;
                } else {
                    // s2 is lagging, so get next q2 and keep q1
                }
            }
            q2 = null;
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        final LocalTime start = LocalTime.now();

        final LocalDate now = LocalDate.now();
        final LocalDate origin = now.minus(Period.ofYears(5));

        StockQuoteSource stockQuoteSource = aDataFactory.createStockQuoteSource();
        TimeSeries<? extends Quote> source = stockQuoteSource.getQuotes(aDataFactory.createStock(aSymbol));
        TimeSeries<? extends Quote> quoteList = source
            .stream().filter(q -> q.getDateTime().compareTo(origin.atStartOfDay()) >= 0)
            .collect(Collectors.<Quote, TimeSeries<Quote>>toCollection(() -> new TimeSeriesImpl<>(source.getCurrency(), source.size())));

        final List<TradingStrategy> strategies = Collections.synchronizedList(new ArrayList<>());
        IntStream.range(10, 189)
                .parallel()
                .forEach(i -> {
                    IntStream.range(i + 10, 200)
                            .forEach(j -> {
                                TradingStrategy s = new SmaTradingStrategy(aDataFactory, i, j, quoteList).setMaxTrades(25);
                                Gain g = s.getGain();
                                if (g != null) {
                                    strategies.add(s);
                                }
                            });
                });

        System.out.println("Time: " + Duration.between(start, LocalTime.now()));

        Collections.sort(strategies, (s1, s2) -> s1.getGain().getYield() > s2.getGain().getYield() ? -1 :
                s1.getGain().getYield() < s2.getGain().getYield() ? 1 : 0);
        for (int i = 0, max = Math.min(strategies.size(), 10); i < max; i++) {
            TradingStrategy t = strategies.get(i);
            System.out.println("i=" + i + ": " + t);
        }
        for (int max = strategies.size(), i = max-10; i < max; i++) {
            TradingStrategy t = strategies.get(i);
            System.out.println("i=" + i + ": " + t);
        }
    }
}
