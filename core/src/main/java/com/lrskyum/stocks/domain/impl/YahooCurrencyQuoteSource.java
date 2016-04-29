package com.lrskyum.stocks.domain.impl;

import com.lrskyum.stocks.domain.*;

import java.io.IOException;

/**
 *
 */
public class YahooCurrencyQuoteSource implements CurrencyQuoteSource {
    private final YahooQuoteSource quoteSource;

    public YahooCurrencyQuoteSource(DataFactory dataFactory) {
        quoteSource = new YahooQuoteSource(dataFactory);
    }

    @Override
    public TimeSeries<HistoricQuote> getQuotes(Currency currency) throws IOException {
        return quoteSource.getQuotes(currency.name() + "=X", currency);
    }
}
