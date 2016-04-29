/*
 * Copyright (c) 2016.
 * Lars Ruben Skyum
 */

package com.lrskyum.stocks.domain.impl.calc;

import com.lrskyum.stocks.domain.*;
import com.lrskyum.stocks.domain.impl.PriceImpl;
import com.lrskyum.stocks.domain.impl.QuoteImpl;
import com.lrskyum.stocks.domain.impl.TimeSeriesImpl;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class MovingAvgTradeSignalsCalculationTest {
    private TimeSeries<? extends Quote> source;

    @Before
    public void setUp() throws Exception {
        source = new TimeSeriesImpl<Quote>(Currency.USD) {{
            add(new QuoteImpl(LocalDateTime.parse("2007-12-03T10:15:30"), new PriceImpl(50.0)));
            add(new QuoteImpl(LocalDateTime.parse("2007-12-04T10:15:30"), new PriceImpl(30.0)));
            add(new QuoteImpl(LocalDateTime.parse("2007-12-05T10:15:30"), new PriceImpl(10.0)));
            add(new QuoteImpl(LocalDateTime.parse("2007-12-06T10:15:30"), new PriceImpl(30.0)));
            add(new QuoteImpl(LocalDateTime.parse("2007-12-07T10:15:30"), new PriceImpl(50.0)));    // BUY
            add(new QuoteImpl(LocalDateTime.parse("2007-12-08T10:15:30"), new PriceImpl(70.0)));
            add(new QuoteImpl(LocalDateTime.parse("2007-12-09T10:15:30"), new PriceImpl(50.0)));
            add(new QuoteImpl(LocalDateTime.parse("2007-12-10T10:15:30"), new PriceImpl(30.0)));    // SELL
            add(new QuoteImpl(LocalDateTime.parse("2007-12-11T10:15:30"), new PriceImpl(10.0)));
        }};
    }

    @Test
    public void testGetTradeSignals() throws Exception {
        TimeSeries<? extends Quote> fastTs = new SmaCalculation(new DataFactory(), 2, source).calc();
        System.out.println(fastTs.stream().map(v -> v.getDateTime().getDayOfMonth()+":"+v.getPrice()).collect(Collectors.toList()));
        TimeSeries<? extends Quote> slowTs = new SmaCalculation(new DataFactory(), 3, source).calc();
        System.out.println(slowTs.stream().map(v -> v.getDateTime().getDayOfMonth()+":"+v.getPrice()).collect(Collectors.toList()));

        List<TradeSignal> trades = new MovingAvgTradeSignalsCalculation(fastTs, slowTs).getTradeSignals();
        System.out.println(trades);
    }
}
