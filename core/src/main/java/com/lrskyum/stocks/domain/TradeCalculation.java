/*
 * Copyright (c) 2016.
 * Lars Ruben Skyum
 */

package com.lrskyum.stocks.domain;

import com.lrskyum.stocks.domain.impl.TradeImpl;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class TradeCalculation {
    private final TimeSeries<? extends Quote> quotes;
    private final List<TradeSignal> tradeSignals;

    public TradeCalculation(TimeSeries<? extends Quote> quotes, List<TradeSignal> tradeSignals) {
        this.quotes = quotes;
        this.tradeSignals = tradeSignals;
    }

    public List<Trade> calc() {
        List<Trade> tradeList = new ArrayList<Trade>(tradeSignals.size());
        TradeSignal buy = null;
        for (TradeSignal ts : tradeSignals) {
            if (buy == null && ts.getSignal().equals(TradeSignal.Signal.BUY)) {
                buy = ts;
            } else if (buy != null && ts.getSignal().equals(TradeSignal.Signal.SELL)) {
                Quote buyQuote = quotes.getQuoteAt(buy.getDateTime());
                Quote sellQuote = quotes.getQuoteAt(ts.getDateTime());
                Trade trade = new TradeImpl(buyQuote, sellQuote);
                tradeList.add(trade);
                buy = null;
            }
        }
        return tradeList;
    }
}
