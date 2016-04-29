/*
 * Copyright (c) 2016.
 * Lars Ruben Skyum
 */

package com.lrskyum.stocks.domain;

import java.io.IOException;

/**
 *
 */
public interface StockQuoteSource {
    TimeSeries<HistoricQuote> getQuotes(Stock stock) throws IOException;
}
