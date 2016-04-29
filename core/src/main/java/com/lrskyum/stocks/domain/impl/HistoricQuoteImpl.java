/*
 * Copyright (c) 2015.
 * Lars Ruben Skyum
 */
package com.lrskyum.stocks.domain.impl;

import com.lrskyum.stocks.domain.HistoricQuote;
import com.lrskyum.stocks.domain.Price;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class HistoricQuoteImpl implements HistoricQuote, Serializable {
    private static final long serialVersionUID = 1L;

    private final LocalDate aDate;
    private final Price aOpen;
    private final Price aHigh;
    private final Price aLow;
    private final Price aClose;
    private final Price aAdjusted;
    private final long aVolume;

    public HistoricQuoteImpl(LocalDate date, Price open, Price high, Price low, Price close, Price adjusted, long volume) {
        aDate = date;
        aOpen = open;
        aHigh = high;
        aLow = low;
        aClose = close;
        aAdjusted = adjusted;
        aVolume = volume;
    }

    @Override
    public LocalDateTime getDateTime() {
        return getDate().atStartOfDay();
    }

    @Override
    public Price getPrice() {
        return getAdjusted();
    }

    @Override
    public LocalDate getDate() {
        return aDate;
    }

    @Override
    public Price getOpen() {
        return aOpen;
    }

    @Override
    public Price getHigh() {
        return aHigh;
    }

    @Override
    public Price getLow() {
        return aLow;
    }

    @Override
    public Price getClose() {
        return aClose;
    }

    @Override
    public Price getAdjusted() {
        return aAdjusted;
    }

    @Override
    public long getVolume() {
        return aVolume;
    }

    @Override
    public String toString() {
        return "HistoricQuote{" +
               "aDate=" + aDate +
               ", aOpen=" + aOpen +
               ", aHigh=" + aHigh +
               ", aLow=" + aLow +
               ", aClose=" + aClose +
               ", aAdjusted=" + aAdjusted +
               ", aVolume=" + aVolume +
               '}';
    }
}
