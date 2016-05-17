package com.lrskyum.stocks.domain.impl;

import com.lrskyum.stocks.domain.Price;
import com.lrskyum.stocks.domain.TradePlan;

/**
 *
 */
public class FlatFeeTradePlan implements TradePlan {
    private Price fee;

    public FlatFeeTradePlan(Price fee) {
        this.fee = fee;
    }

    @Override
    public Price commission(Price stockPrice, double count) {
        return count > 0 ? fee : new PriceImpl(0);
    }
}
