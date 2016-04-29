/*
 * Copyright (c) 2015.
 * Lars Ruben Skyum
 */
package com.lrskyum.stocks.domain.impl;

import com.lrskyum.stocks.domain.Quote;
import com.lrskyum.stocks.domain.Trade;

import java.io.Serializable;

public class TradeImpl implements Trade, Serializable {
    private static final long serialVersionUID = 1L;

    private final Quote aBuy;
    private final Quote aSell;

    public TradeImpl(Quote buy, Quote sell) {
        aBuy = buy;
        aSell = sell;
    }

    @Override
    public Quote getBuy() {
        return aBuy;
    }

    @Override
    public Quote getSell() {
        return aSell;
    }

    @Override
    public double getYield() {
        double buy = aBuy.getPrice().getValue();
        double sell = aSell.getPrice().getValue();
        return (sell - buy) / buy;
    }

    @Override
    public String toString() {
        return "Trade{" +
               "aBuy=" + aBuy +
               ", aSell=" + aSell +
               ", Yield=" + getYield()+
               '}';
    }
}
