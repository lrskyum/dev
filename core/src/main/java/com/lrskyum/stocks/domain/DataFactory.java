/*
 * Copyright (c) 2015.
 * Lars Ruben Skyum
 */
package com.lrskyum.stocks.domain;

import com.lrskyum.stocks.domain.impl.*;
import com.lrskyum.stocks.domain.impl.io.IOFactoryImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DataFactory {
    private static DataFactory aInstance;

    public static synchronized DataFactory getInstance() {
        if (aInstance == null) {
            aInstance = new DataFactory();
        }
        return aInstance;
    }

    public Quote createQuote(LocalDateTime time, Price price) {
        return createQuote(time, price, 0);
    }

    public Quote createQuote(LocalDateTime time, Price price, long volume) {
        return new QuoteImpl(time, price);
    }

    public HistoricQuote createQuote(LocalDate time, Price open, Price high, Price low, Price close, Price adjusted, long volume) {
        return new HistoricQuoteImpl(time, open, high, low, close, adjusted, volume);
    }

    public Price createPrice(String price) {
        return new PriceImpl(Double.parseDouble(price));
    }

    public Price createPrice(double price) {
        return new PriceImpl(price);
    }

    public Price createPrice(Price p) {
        return createPrice(p.getValue());
    }

    public Stock createStock(String symbol) {
        return new StockImpl(symbol, null);
    }

    public Stock createStock(String symbol, String name) {
        return new StockImpl(symbol, name);
    }

    public Stock createStock(Stock s) {
        return createStock(s.getSymbol(), s.getName());
    }

    public QuoteRepository createQuoteRepository() {
        return new FileQuoteRepository(new IOFactoryImpl());
    }

    public <T extends Quote> TimeSeries<T> createTimeSeries(Currency currency) {
        return new TimeSeriesImpl<>(currency);
    }

    public <T extends Quote> TimeSeries<T> createTimeSeries(Currency currency, final int initialCapacity) {
        return new TimeSeriesImpl<>(currency, initialCapacity);
    }

    public StockQuoteSource createStockQuoteSource() {
        return new YahooStockQuoteSource(this);
    }

    public CurrencyQuoteSource createCurrencyQuoteSource() {
        return new YahooCurrencyQuoteSource(this);
    }
}

