/*
 * Copyright (c) 2015.
 * Lars Ruben Skyum
 */
package com.lrskyum.stocks.domain.impl;

import com.lrskyum.stocks.domain.Price;
import com.lrskyum.stocks.domain.Quote;

import java.io.Serializable;
import java.time.LocalDateTime;

public class QuoteImpl implements Quote, Serializable {
    private static final long serialVersionUID = 1L;

    private final LocalDateTime aDateTime;
    private final Price aPrice;

    public QuoteImpl(LocalDateTime dateTime, Price price) {
        aDateTime = dateTime;
        aPrice = price;
    }

    @Override
    public LocalDateTime getDateTime() {
        return aDateTime;
    }

    @Override
    public Price getPrice() {
        return aPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quote)) return false;

        Quote quote = (Quote) o;

        if (!aDateTime.equals(quote.getDateTime())) return false;
        if (!aPrice.equals(quote.getPrice())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = aDateTime.hashCode();
        result = 31 * result + aPrice.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Quote{" +
               "aDateTime=" + aDateTime +
               ", aPrice=" + aPrice +
               '}';
    }
}
