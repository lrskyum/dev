/*
 * Copyright (c) 2015.
 * Lars Ruben Skyum
 */

package com.lrskyum.stocks.domain;

import java.time.LocalDateTime;

/**
 *
 */
public interface TradeSignal {
    enum Signal {
        BUY,
        SELL
    }

    Signal getSignal();
    LocalDateTime getDateTime();
}
