/*
 * Copyright (c) 2015.
 * Lars Ruben Skyum
 */
package com.lrskyum.stocks.domain.impl;

import com.lrskyum.stocks.domain.Price;

import java.io.Serializable;

public class PriceImpl implements Price, Serializable {
    private static final long serialVersionUID = 1L;

    private final double aPrice;

    public PriceImpl(double price) {
        aPrice = price;
    }

    public double getValue() {
        return aPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PriceImpl)) return false;

        PriceImpl price = (PriceImpl) o;

        if (Double.compare(price.aPrice, aPrice) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(aPrice);
        return (int) (temp ^ (temp >>> 32));
    }

    @Override
    public int compareTo(Price o) {
        return Double.compare(aPrice, o.getValue());
    }

    @Override
    public String toString() {
        return "PriceImpl{" +
                "aPrice=" + aPrice +
                '}';
    }
}
