/*
 * Copyright (c) 2016.
 * Lars Ruben Skyum
 */

package com.lrskyum.stocks.domain;

import java.io.IOException;

/**
 *
 */
public interface CurrencyQuoteRepository {
    void store(Currency currency, TimeSeries<HistoricQuote> list) throws IOException;
    TimeSeries<HistoricQuote> restore(Currency currency) throws ClassNotFoundException, IOException;
}
