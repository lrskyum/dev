/*
 * Copyright (c) 2015.
 * Lars Ruben Skyum
 */

package com.lrskyum.stocks.domain;

import java.time.LocalDateTime;

/**
 *
 */
public interface Quote {
    LocalDateTime getDateTime();

    Price getPrice();
}
