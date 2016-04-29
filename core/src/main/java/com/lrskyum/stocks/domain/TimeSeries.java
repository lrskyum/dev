/*
 * Copyright (c) 2016.
 * Lars Ruben Skyum
 */

package com.lrskyum.stocks.domain;

import java.time.LocalDateTime;
import java.util.List;

/**
*
*/
public interface TimeSeries<T extends Quote> extends List<T> {
    Currency getCurrency();
    T getQuoteAt(LocalDateTime dateTime);
    T getEarliest();
    T getLatest();
}
