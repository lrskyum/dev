/*
 * Copyright (c) 2016.
 * Lars Ruben Skyum
 */

package com.lrskyum.stocks.domain;

import java.util.List;

/**
 *
 */
public interface TradeSignalCalculation {
    List<TradeSignal> getTradeSignals();
}
