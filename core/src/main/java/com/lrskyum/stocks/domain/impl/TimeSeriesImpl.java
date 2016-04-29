/*
 * Copyright (c) 2016.
 * Lars Ruben Skyum
 */

package com.lrskyum.stocks.domain.impl;

import com.lrskyum.stocks.domain.Currency;
import com.lrskyum.stocks.domain.Quote;
import com.lrskyum.stocks.domain.TimeSeries;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

/**
*
*/
public class TimeSeriesImpl<T extends Quote> extends ArrayList<T> implements TimeSeries<T>, Externalizable {
    private Currency currency;

    // For serialization only
    public TimeSeriesImpl() {
        super();
    }

    public TimeSeriesImpl(Currency currency) {
        super();
        this.currency = currency;
    }

    public TimeSeriesImpl(Currency currency, int initialCapacity) {
        super(initialCapacity);
        this.currency = currency;
    }

    @Override
    public Currency getCurrency() {
        return currency;
    }

    @Override
    public T set(int index, T element) {
        if (index >= size()) {  // 1 >= 1
            super.addAll(Collections.nCopies(index - size() + 1, null));
        }
        return super.set(index, element);
    }

    @Override
    public T getQuoteAt(final LocalDateTime dateTime) {
        Quote q = new QuoteImpl(dateTime, null);
        int index = Collections.binarySearch(this, q, (q1, q2) -> q1.getDateTime().compareTo(q2.getDateTime()));
        return index >= 0 ? get(index) : null;
    }

    @Override
    public T getEarliest() {
        return size() > 0 ? get(0) : null;
    }

    @Override
    public T getLatest() {
        return size() > 0 ? get(size()-1) : null;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(currency);
        out.writeInt(size());
        for (T t : this) {
            out.writeObject(t);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        currency = (Currency)in.readObject();
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            add((T) in.readObject());
        }
    }

    @Override
    public String toString() {
        return "TimeSeriesImpl{" +
                "currency=" + currency +
                ", super=" + super.toString() +
                '}';
    }
}
