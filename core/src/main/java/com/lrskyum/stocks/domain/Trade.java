/*
 * Copyright (c) 2015.
 * Lars Ruben Skyum
 */

package com.lrskyum.stocks.domain;

/**
 *
 */
public interface Trade {
    Quote getBuy();

    Quote getSell();

    double getYield();
}
