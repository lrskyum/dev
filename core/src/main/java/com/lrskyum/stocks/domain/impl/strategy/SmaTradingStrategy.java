package com.lrskyum.stocks.domain.impl.strategy;

import com.lrskyum.stocks.domain.*;
import com.lrskyum.stocks.domain.impl.calc.MovingAvgTradeSignalsCalculation;
import com.lrskyum.stocks.domain.impl.calc.SmaCalculation;

import java.util.List;

/**
*
*/
public class SmaTradingStrategy implements TradingStrategy {
    private final DataFactory dataFactory;
    private final TimeSeries<? extends Quote> source;
    private final int fastAvg;
    private final int slowAvg;
    private int maxTrades = Integer.MAX_VALUE;

    private Gain gain;

    public SmaTradingStrategy(DataFactory dataFactory, int fastAvg, int slowAvg, TimeSeries<? extends Quote> source) {
        this.dataFactory = dataFactory;
        this.fastAvg = fastAvg;
        this.slowAvg = slowAvg;
        this.source = source;
    }

    public int getFastAvg() {
        return fastAvg;
    }

    public int getSlowAvg() {
        return slowAvg;
    }

    public int getMaxTrades() {
        return maxTrades;
    }

    public SmaTradingStrategy setMaxTrades(int maxTrades) {
        this.maxTrades = maxTrades;
        return this;
    }

    @Override
    public Gain getGain() {
        if (gain == null) {
            final TimeSeries<? extends Quote> fastSma = new SmaCalculation(dataFactory, fastAvg, source).calc();
            final TimeSeries<? extends Quote> slowSma = new SmaCalculation(dataFactory, slowAvg, source).calc();
            List<TradeSignal> tradeSignals = new MovingAvgTradeSignalsCalculation(fastSma, slowSma).getTradeSignals();
            if (tradeSignals.size()/2 <= maxTrades) {
                List<Trade> trades = new TradeCalculation(source, tradeSignals).calc();
                gain = new Gain(trades);
            }
        }
        return gain;
    }

    @Override
    public String toString() {
        return "SmaTradingStrategy{" +
                "gain=" + gain +
                ", maxTrades=" + maxTrades +
                ", fastAvg=" + fastAvg +
                ", slowAvg=" + slowAvg +
                '}';
    }
}
