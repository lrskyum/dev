/*
 * Copyright (c) 2016.
 * Lars Ruben Skyum
 */

package com.lrskyum.stocks.domain.impl.calc;

import com.lrskyum.stocks.domain.Quote;
import com.lrskyum.stocks.domain.TradeSignal;
import com.lrskyum.stocks.domain.TradeSignalCalculation;
import com.lrskyum.stocks.domain.impl.TradeSignalImpl;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MovingAvgTradeSignalsCalculation implements TradeSignalCalculation {
    private final List<? extends Quote> fastMa;
    private final List<? extends Quote> slowMa;

    public MovingAvgTradeSignalsCalculation(List<? extends Quote> fastMa, List<? extends Quote> slowMa) {
        this.fastMa = fastMa;
        this.slowMa = slowMa;
    }

    @Override
    public List<TradeSignal> getTradeSignals() {
        List<TradeSignal> trades = new ArrayList<TradeSignal>(Math.max(fastMa.size(), slowMa.size()));

        for (int fastIndex = 1, slowIndex = 1; fastIndex < fastMa.size() && slowIndex < slowMa.size(); fastIndex++, slowIndex++) {
            // Expect fast to have start before slow - find equal dates
            for (; fastIndex < fastMa.size() && !fastMa.get(fastIndex).getDateTime().equals(slowMa.get(slowIndex).getDateTime()); fastIndex++)
                ;

            if (fastMa.get(fastIndex).getPrice().getValue() >= slowMa.get(slowIndex).getPrice().getValue() &&
                    fastMa.get(fastIndex-1).getPrice().getValue() < slowMa.get(slowIndex-1).getPrice().getValue()) {
                trades.add(new TradeSignalImpl(TradeSignal.Signal.BUY, fastMa.get(fastIndex).getDateTime()));
            } else if (fastMa.get(fastIndex).getPrice().getValue() <= slowMa.get(slowIndex).getPrice().getValue() &&
                    fastMa.get(fastIndex-1).getPrice().getValue() > slowMa.get(slowIndex-1).getPrice().getValue()) {
                trades.add(new TradeSignalImpl(TradeSignal.Signal.SELL, fastMa.get(fastIndex).getDateTime()));
            }
        }
        return trades;
    }
}
