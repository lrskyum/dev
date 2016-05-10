/*
 * Copyright (c) 2015.
 * Lars Ruben Skyum
 */
package com.lrskyum.stocks;


import com.lrskyum.stocks.domain.*;
import com.lrskyum.stocks.domain.impl.TimeSeriesImpl;
import com.lrskyum.stocks.domain.impl.calc.CurrencyConversion;
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
    private static final String aStock = "danske.co";
    private static final String aCurrency = "dkk=x";
    private static final double aKurtagePercent = 0.5;
    private static final DataFactory aDataFactory = new DataFactory();

    public static void main(String[] args) throws Exception {
        final LocalTime start = LocalTime.now();

        final LocalDate now = LocalDate.now();
        final LocalDate origin = now.minus(Period.ofYears(5));

        StockQuoteSource stockQuoteSource = aDataFactory.createStockQuoteSource();
        CurrencyQuoteSource currencyQuoteSource = aDataFactory.createCurrencyQuoteSource();
        TimeSeries<? extends Quote> stockQuotes = stockQuoteSource.getQuotes(aDataFactory.createStock(aStock));
        TimeSeries<? extends Quote> stockQuoteList = stockQuotes
            .stream().filter(q -> q.getDateTime().compareTo(origin.atStartOfDay()) >= 0)
            .collect(Collectors.<Quote, TimeSeries<Quote>>toCollection(() -> new TimeSeriesImpl<>(stockQuotes.getCurrency(), stockQuotes.size())));

        TimeSeries<? extends Quote> dkk = currencyQuoteSource.getQuotes(Currency.DKK);
        TimeSeries<? extends Quote> gbp = currencyQuoteSource.getQuotes(Currency.GBP);
        TimeSeries<? extends Quote> normalizedQuotes = new CurrencyConversion(aDataFactory).normalize(stockQuoteList, dkk);
        TimeSeries<? extends Quote> denormalizedQuotes = new CurrencyConversion(aDataFactory).denormalize(normalizedQuotes, gbp);
        final List<TradingStrategy> strategies = Collections.synchronizedList(new ArrayList<>());

        IntStream.range(10, 189)
                .parallel()
                .forEach(i -> {
                    IntStream.range(i + 10, 200)
                            .forEach(j -> {
                                TradingStrategy s = new SmaTradingStrategy(aDataFactory, i, j, denormalizedQuotes).setMaxTrades(25);
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
