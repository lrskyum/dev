/*
 * Copyright (c) 2015.
 * Lars Ruben Skyum
 */
package com.lrskyum.stocks.domain;

// EURO reference exchange rate
// http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml
// http://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist.xml
// ISO 4217 Currency Code List

// http://www.ecb.europa.eu/stats/exchange/eurofxref/html/dkk.xml
public enum Currency {
    AUD("Australia Dollar"),
    BGN("Bulgaria Lev"),
    BRL("Brazil Real"),
    CAD("Canada Dollar"),
    CHF("Switzerland Franc"),
    CNY("China Yuan Renminbi"),
    CZK("Czech Republic Koruna"),
    DKK("Denmark Krone"),
    EUR("EURO"),
    GBP("United Kingdom Pound"),
    HKD("Hong Kong Dollar"),
    HRK("Croatia Kuna"),
    HUF("Hungary Forint"),
    IDR("Indonesia Rupiah"),
    ILS("Israel Shekel"),
    INR("India Rupee"),
    JPY("Japan Yen"),
    KRW("Korea (South) Won"),
    LTL("Lithuania Litas"),
    LVL("Latvia Lat"),
    MXN("Mexico Peso"),
    MYR("Malaysia Ringgit"),
    NOK("Norway Krone"),
    NZD("New Zealand Dollar"),
    PHP("Philippines Peso"),
    PLN("Poland Zloty"),
    RON("Romania New Leu"),
    RUB("Russia Ruble"),
    SEK("Sweden Krona"),
    SGD("Singapore Dollar"),
    THB("Thailand Baht"),
    TRY("Turkey Lira"),
    USD("United States Dollar"),
    ZAR("South Africa Rand");

    private static final long serialVersionUID = 1L;

    private String aName;

    Currency(String name) {
        aName = name;
    }

    public String getName() {
        return aName;
    }
}
