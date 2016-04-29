/*
 * Copyright (c) 2016.
 * Lars Ruben Skyum
 */

package com.lrskyum.stocks.domain;

import java.io.IOException;

/**
 *
 */
public interface CurrencyQuoteSource {
    TimeSeries<HistoricQuote> getQuotes(Currency currency) throws IOException;
}
