package com.lrskyum.stocks.domain;

/**
 *
 */
public interface ExchangeRate extends HistoricQuote {
    Currency getForeign();  // Domestic is normalized to USD

    ExchangeRate getInverse();
}
