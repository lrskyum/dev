package com.lrskyum.stocks.domain;


import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 */
public interface HistoricQuote extends Quote, Serializable {
    LocalDate getDate();

    Price getOpen();

    Price getHigh();

    Price getLow();

    Price getClose();

    Price getAdjusted();

    long getVolume();
}
