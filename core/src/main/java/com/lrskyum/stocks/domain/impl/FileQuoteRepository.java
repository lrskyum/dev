/*
 * Copyright (c) 2016.
 * Lars Ruben Skyum
 */

package com.lrskyum.stocks.domain.impl;

import com.lrskyum.stocks.domain.HistoricQuote;
import com.lrskyum.stocks.domain.QuoteRepository;
import com.lrskyum.stocks.domain.TimeSeries;
import com.lrskyum.stocks.domain.impl.io.IOFactory;
import com.lrskyum.stocks.domain.impl.io.RepositoryObjectInput;
import com.lrskyum.stocks.domain.impl.io.RepositoryObjectOutput;

import java.io.*;

/**
 *
 */
public class FileQuoteRepository implements QuoteRepository {
    private IOFactory ioFactory;

    public FileQuoteRepository(IOFactory ioFactory) {
        this.ioFactory = ioFactory;
    }

    @Override
    public void store(String id, TimeSeries<HistoricQuote> series) throws IOException {
        try (RepositoryObjectOutput<TimeSeries<HistoricQuote>> oo = ioFactory.createOutputStream(id)) {
            oo.write(series);
        }
    }

    @Override
    public TimeSeries<HistoricQuote> restore(String id) throws ClassNotFoundException, IOException {
        try (RepositoryObjectInput<TimeSeries<HistoricQuote>> oi = ioFactory.createObjectInput(id)) {
            return oi.read();
        } catch (FileNotFoundException e) {
            return null;
        }
    }
}
