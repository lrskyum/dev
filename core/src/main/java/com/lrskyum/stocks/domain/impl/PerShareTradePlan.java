package com.lrskyum.stocks.domain.impl;

import com.lrskyum.stocks.domain.Price;
import com.lrskyum.stocks.domain.TradePlan;

/**
 *
 */
public class PerShareTradePlan implements TradePlan {
    private final Price perSharePrice;

    public PerShareTradePlan(Price perSharePrice) {
        this.perSharePrice = perSharePrice;
    }

    @Override
    public Price commission(Price stockPrice, double count) {
        return new PriceImpl(count * perSharePrice.getValue());
    }
}
