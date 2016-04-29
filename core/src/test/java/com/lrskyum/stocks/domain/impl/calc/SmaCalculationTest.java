/*
 * Copyright (c) 2016.
 * Lars Ruben Skyum
 */

package com.lrskyum.stocks.domain.impl.calc;

import com.lrskyum.stocks.domain.Currency;
import com.lrskyum.stocks.domain.DataFactory;
import com.lrskyum.stocks.domain.Quote;
import com.lrskyum.stocks.domain.TimeSeries;
import com.lrskyum.stocks.domain.impl.PriceImpl;
import com.lrskyum.stocks.domain.impl.QuoteImpl;
import com.lrskyum.stocks.domain.impl.TimeSeriesImpl;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 *
 */
public class SmaCalculationTest {
    private TimeSeries<? extends Quote> source;

    @Before
    public void setUp() throws Exception {
        source = new TimeSeriesImpl<Quote>(Currency.USD) {{
            add(new QuoteImpl(LocalDateTime.parse("2007-12-03T10:15:30"), new PriceImpl(10.0)));
            add(new QuoteImpl(LocalDateTime.parse("2007-12-04T10:15:30"), new PriceImpl(20.0)));
            add(new QuoteImpl(LocalDateTime.parse("2007-12-05T10:15:30"), new PriceImpl(25.0)));
            add(new QuoteImpl(LocalDateTime.parse("2007-12-06T10:15:30"), new PriceImpl(30.0)));
        }};
    }

    @Test
    public void testCalc() throws Exception {
        TimeSeries<? extends Quote> res = new SmaCalculation(new DataFactory(), 3, source).calc();
        System.out.println(res);
    }
}
