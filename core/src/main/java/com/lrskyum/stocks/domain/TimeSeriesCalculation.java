/*
 * Copyright (c) 2016.
 * Lars Ruben Skyum
 */

package com.lrskyum.stocks.domain;

/**
 *
 */
public interface TimeSeriesCalculation {
    TimeSeries<? extends Quote> calc();
}
