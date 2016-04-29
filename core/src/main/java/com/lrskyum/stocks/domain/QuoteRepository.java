/*
 * Copyright (c) 2016.
 * Lars Ruben Skyum
 */

package com.lrskyum.stocks.domain;

import java.io.IOException;

/**
 *
 */
public interface QuoteRepository {
    void store(String id, TimeSeries<HistoricQuote> list) throws IOException;
    TimeSeries<HistoricQuote> restore(String id) throws ClassNotFoundException, IOException;
}
