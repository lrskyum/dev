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
import java.util.stream.Collectors;

/**
 *
 */
public class CurrencyConversionTest {
    private static final DataFactory aDataFactory = new DataFactory();

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testNormalize() throws Exception {
        TimeSeries<? extends Quote> quotes = new TimeSeriesImpl<Quote>(Currency.DKK) {{
            add(new QuoteImpl(LocalDateTime.parse("2016-04-11T00:00:00"), new PriceImpl(175.50)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-12T00:00:00"), new PriceImpl(174.10)));    // SELL
            add(new QuoteImpl(LocalDateTime.parse("2016-04-13T00:00:00"), new PriceImpl(177.00)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-14T00:00:00"), new PriceImpl(178.40)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-15T00:00:00"), new PriceImpl(178.50)));    // BUY
            add(new QuoteImpl(LocalDateTime.parse("2016-04-18T00:00:00"), new PriceImpl(178.30)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-19T00:00:00"), new PriceImpl(179.60)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-20T00:00:00"), new PriceImpl(176.90)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-21T00:00:00"), new PriceImpl(178.60)));
        }};
        TimeSeries<? extends Quote> currency = new TimeSeriesImpl<Quote>(Currency.DKK) {{
            add(new QuoteImpl(LocalDateTime.parse("2016-04-11T00:00:00"), new PriceImpl(6.52467)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-12T00:00:00"), new PriceImpl(6.52182)));   // SELL
            add(new QuoteImpl(LocalDateTime.parse("2016-04-13T00:00:00"), new PriceImpl(6.53734)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-14T00:00:00"), new PriceImpl(6.59828)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-15T00:00:00"), new PriceImpl(6.6017)));    // BUY
            add(new QuoteImpl(LocalDateTime.parse("2016-04-18T00:00:00"), new PriceImpl(6.5862)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-19T00:00:00"), new PriceImpl(6.58084)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-20T00:00:00"), new PriceImpl(6.55134)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-21T00:00:00"), new PriceImpl(6.58224)));
        }};

        TimeSeries<? extends Quote> normalized = new CurrencyConversion(aDataFactory).normalize(quotes, currency);
        System.out.println(normalized.stream().map(v -> v.getDateTime().getDayOfMonth()+":"+v.getPrice()).collect(Collectors.toList()));
    }

    @Test
    public void testNormalize2() throws Exception {
        TimeSeries<? extends Quote> quotes = new TimeSeriesImpl<Quote>(Currency.DKK) {{
            add(new QuoteImpl(LocalDateTime.parse("2016-04-14T00:00:00"), new PriceImpl(178.40)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-15T00:00:00"), new PriceImpl(178.50)));    // BUY
            add(new QuoteImpl(LocalDateTime.parse("2016-04-18T00:00:00"), new PriceImpl(178.30)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-19T00:00:00"), new PriceImpl(179.60)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-20T00:00:00"), new PriceImpl(176.90)));
        }};
        TimeSeries<? extends Quote> currency = new TimeSeriesImpl<Quote>(Currency.DKK) {{
            add(new QuoteImpl(LocalDateTime.parse("2016-04-15T00:00:00"), new PriceImpl(6.6017)));    // BUY
            add(new QuoteImpl(LocalDateTime.parse("2016-04-18T00:00:00"), new PriceImpl(6.5862)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-19T12:00:00"), new PriceImpl(6.58084)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-20T00:00:00"), new PriceImpl(6.55134)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-21T00:00:00"), new PriceImpl(6.58224)));
        }};

        TimeSeries<? extends Quote> normalized = new CurrencyConversion(aDataFactory).normalize(quotes, currency);
        System.out.println(normalized.stream().map(v -> v.getDateTime().getDayOfMonth()+":"+v.getPrice()).collect(Collectors.toList()));
    }
}
