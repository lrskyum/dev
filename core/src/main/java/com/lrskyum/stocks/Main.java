/*
 * Copyright (c) 2015.
 * Lars Ruben Skyum
 */
package com.lrskyum.stocks;


import com.lrskyum.stocks.domain.*;
import com.lrskyum.stocks.domain.impl.TimeSeriesImpl;
import com.lrskyum.stocks.domain.impl.strategy.SmaTradingStrategy;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    private static final String aSymbol = "msft";
    private static final double aKurtagePercent = 0.5;
    private static final DataFactory aDataFactory = new DataFactory();

//    public static class CurrencyConversion {
//        public TimeSeries<? extends Quote> convert(Currency targetForeign, TimeSeries<? extends Quote> source) throws IOException {
//            TimeSeries<? extends Quote> targetCurrencySeries = aDataFactory.createCurrencyQuoteSource().getQuotes(targetForeign);
//
//        }
//    }

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
