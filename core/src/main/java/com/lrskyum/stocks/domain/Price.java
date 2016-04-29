/*
 * Copyright (c) 2015.
 * Lars Ruben Skyum
 */

package com.lrskyum.stocks.domain;

/**
 *
 */
public interface Price extends Comparable<Price> {
    double getValue();
}
