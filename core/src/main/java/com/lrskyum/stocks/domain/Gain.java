/*
 * Copyright (c) 2016.
 * Lars Ruben Skyum
 */

package com.lrskyum.stocks.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
*
*/
public class Gain {
    private final List<Trade> trades;
    private Double yield;

    public Gain(List<Trade> trades) {
        this.trades = trades;
    }

    public Gain(Trade... trades) {
        this.trades = Arrays.asList(trades);
    }

    public Gain(Trade trade) {
        this.trades = Collections.<Trade>singletonList(trade);
    }

    public List<Trade> getTrades() {
        return trades;
    }

    public double getYield() {
        if (yield == null) {
            yield = 1.0;
            for (Trade trade : trades) {
                yield *= (1.0 + trade.getYield());
            }
        }
        return yield;
    }

    @Override
    public String toString() {
        return "Gain{" +
               "yield=" + yield +
               ", trade#=" + trades.size() +
               '}';
    }
}
