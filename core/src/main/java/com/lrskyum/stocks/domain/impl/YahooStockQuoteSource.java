package com.lrskyum.stocks.domain.impl;

import com.lrskyum.stocks.domain.*;

import java.io.IOException;

/**
 *
 */
public class YahooStockQuoteSource implements StockQuoteSource {
    private final YahooQuoteSource quoteSource;

    public YahooStockQuoteSource(DataFactory dataFactory) {
        quoteSource = new YahooQuoteSource(dataFactory);
    }

    @Override
    public TimeSeries<HistoricQuote> getQuotes(Stock stock) throws IOException {
        return quoteSource.getQuotes(stock.getSymbol(), Currency.USD);
    }
}
