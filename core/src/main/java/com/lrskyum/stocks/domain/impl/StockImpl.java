/*
 * Copyright (c) 2015.
 * Lars Ruben Skyum
 */
package com.lrskyum.stocks.domain.impl;

import com.lrskyum.stocks.domain.Stock;

import java.io.Serializable;

public class StockImpl implements Stock, Serializable {
    private static final long serialVersionUID = 1L;

    private final String aSymbol;
    private final String aName;

    public StockImpl(String symbol, String name) {
        aSymbol = symbol;
        aName = name;
    }

    public StockImpl(String symbol) {
        aSymbol = symbol;
        aName = null;
    }

    @Override
    public String getSymbol() {
        return aSymbol;
    }

    @Override
    public String getName() {
        return aName;
    }

    @Override
    public String toString() {
        return "Stock{" +
               "aSymbol='" + aSymbol + '\'' +
               ", aName='" + aName + '\'' +
               '}';
    }
}
