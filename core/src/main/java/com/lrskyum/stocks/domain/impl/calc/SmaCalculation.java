/*
 * Copyright (c) 2016.
 * Lars Ruben Skyum
 */

package com.lrskyum.stocks.domain.impl.calc;

import com.lrskyum.stocks.domain.DataFactory;
import com.lrskyum.stocks.domain.Quote;
import com.lrskyum.stocks.domain.TimeSeries;
import com.lrskyum.stocks.domain.TimeSeriesCalculation;

import java.time.LocalDateTime;
import java.util.Collections;

/**
 *
 */
public class SmaCalculation implements TimeSeriesCalculation {
    private final DataFactory aDataFactory;
    private final int days;
    private final TimeSeries<? extends Quote> source;

    public SmaCalculation(DataFactory aDataFactory, int days, TimeSeries<? extends Quote> source) {
        this.aDataFactory = aDataFactory;
        this.days = days;
        this.source = source;
    }

    @Override
    public TimeSeries<? extends Quote> calc() {
        double sum = 0;
        final TimeSeries<Quote> smaList = aDataFactory.createTimeSeries(source.getCurrency(), source.size() - days + 1);

        for (int size = source.size(), i = size - 1; i >= 0; i--) {
            sum += source.get(i).getPrice().getValue();
            if (size - i > days) {
                sum -= source.get(i + days).getPrice().getValue();
            }
            if (size - i >= days) {
                final LocalDateTime date = source.get(i + days - 1).getDateTime();
                smaList.add(aDataFactory.createQuote(date, aDataFactory.createPrice(sum / days))); // Reverse list instead of add(0,...
            }
        }
        Collections.reverse(smaList);
        return smaList;
    }
}
