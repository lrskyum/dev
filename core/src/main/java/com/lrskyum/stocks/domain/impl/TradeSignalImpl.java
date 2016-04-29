/*
 * Copyright (c) 2015.
 * Lars Ruben Skyum
 */
package com.lrskyum.stocks.domain.impl;

import com.lrskyum.stocks.domain.TradeSignal;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TradeSignalImpl implements TradeSignal, Serializable {
    private static final long serialVersionUID = 1L;

    private final Signal signal;
    private final LocalDateTime dateTime;

    public TradeSignalImpl(Signal signal, LocalDateTime dateTime) {
        this.signal = signal;
        this.dateTime = dateTime;
    }

    @Override
    public Signal getSignal() {
        return signal;
    }

    @Override
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "TradeSignalImpl{" +
                "signal=" + signal +
                ", dateTime=" + dateTime +
                '}';
    }
}
