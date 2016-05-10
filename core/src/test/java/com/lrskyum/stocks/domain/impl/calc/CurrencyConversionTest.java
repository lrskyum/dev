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
            add(new QuoteImpl(LocalDateTime.parse("2016-04-11T00:00:00"), new PriceImpl(100)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-12T00:00:00"), new PriceImpl(90)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-13T00:00:00"), new PriceImpl(80)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-14T00:00:00"), new PriceImpl(70)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-15T00:00:00"), new PriceImpl(60)));    // BUY
            add(new QuoteImpl(LocalDateTime.parse("2016-04-18T00:00:00"), new PriceImpl(50)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-19T00:00:00"), new PriceImpl(40)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-20T00:00:00"), new PriceImpl(30)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-21T00:00:00"), new PriceImpl(20)));
        }};
        TimeSeries<? extends Quote> currency = new TimeSeriesImpl<Quote>(Currency.DKK) {{
            add(new QuoteImpl(LocalDateTime.parse("2016-04-11T00:00:00"), new PriceImpl(9)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-12T00:00:00"), new PriceImpl(8)));   // SELL
            add(new QuoteImpl(LocalDateTime.parse("2016-04-13T00:00:00"), new PriceImpl(7)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-14T00:00:00"), new PriceImpl(6)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-15T00:00:00"), new PriceImpl(5)));    // BUY
            add(new QuoteImpl(LocalDateTime.parse("2016-04-18T00:00:00"), new PriceImpl(4)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-19T00:00:00"), new PriceImpl(3)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-20T00:00:00"), new PriceImpl(2)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-21T00:00:00"), new PriceImpl(1)));
        }};

        TimeSeries<? extends Quote> normalized = new CurrencyConversion(aDataFactory).normalize(quotes, currency);
        System.out.println(normalized.stream().map(v -> v.getDateTime().getDayOfMonth()+":"+v.getPrice()).collect(Collectors.toList()));
    }

    @Test
    public void testNormalize2() throws Exception {
        TimeSeries<? extends Quote> quotes = new TimeSeriesImpl<Quote>(Currency.DKK) {{
            add(new QuoteImpl(LocalDateTime.parse("2016-04-14T00:00:00"), new PriceImpl(100)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-15T00:00:00"), new PriceImpl(90)));    // BUY
            add(new QuoteImpl(LocalDateTime.parse("2016-04-18T00:00:00"), new PriceImpl(80)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-19T00:00:00"), new PriceImpl(70)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-20T00:00:00"), new PriceImpl(60)));
        }};
        TimeSeries<? extends Quote> currency = new TimeSeriesImpl<Quote>(Currency.DKK) {{
            add(new QuoteImpl(LocalDateTime.parse("2016-04-15T00:00:00"), new PriceImpl(9)));    // BUY
            add(new QuoteImpl(LocalDateTime.parse("2016-04-18T00:00:00"), new PriceImpl(8)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-19T12:00:00"), new PriceImpl(7)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-20T00:00:00"), new PriceImpl(6)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-21T00:00:00"), new PriceImpl(5)));
        }};

        TimeSeries<? extends Quote> normalized = new CurrencyConversion(aDataFactory).normalize(quotes, currency);
        System.out.println(normalized.stream().map(v -> v.getDateTime().getDayOfMonth()+":"+v.getPrice()).collect(Collectors.toList()));
    }

    @Test
    public void testDenormalize() throws Exception {
        TimeSeries<? extends Quote> quotes = new TimeSeriesImpl<Quote>(Currency.USD) {{
            add(new QuoteImpl(LocalDateTime.parse("2016-04-11T00:00:00"), new PriceImpl(20)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-12T00:00:00"), new PriceImpl(30)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-13T00:00:00"), new PriceImpl(40)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-14T00:00:00"), new PriceImpl(50)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-15T00:00:00"), new PriceImpl(60)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-18T00:00:00"), new PriceImpl(70)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-19T00:00:00"), new PriceImpl(80)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-20T00:00:00"), new PriceImpl(90)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-21T00:00:00"), new PriceImpl(100)));
        }};
        TimeSeries<? extends Quote> currency = new TimeSeriesImpl<Quote>(Currency.DKK) {{
            add(new QuoteImpl(LocalDateTime.parse("2016-04-11T00:00:00"), new PriceImpl(9)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-12T00:00:00"), new PriceImpl(8)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-13T00:00:00"), new PriceImpl(7)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-14T00:00:00"), new PriceImpl(6)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-15T00:00:00"), new PriceImpl(5)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-18T00:00:00"), new PriceImpl(4)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-19T00:00:00"), new PriceImpl(3)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-20T00:00:00"), new PriceImpl(2)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-21T00:00:00"), new PriceImpl(1)));
        }};

        TimeSeries<? extends Quote> normalized = new CurrencyConversion(aDataFactory).denormalize(quotes, currency);
        System.out.println(normalized.stream().map(v -> v.getDateTime().getDayOfMonth()+":"+v.getPrice()).collect(Collectors.toList()));
    }

    @Test
    public void testDenormalize2() throws Exception {
        TimeSeries<? extends Quote> quotes = new TimeSeriesImpl<Quote>(Currency.DKK) {{
            add(new QuoteImpl(LocalDateTime.parse("2016-04-14T00:00:00"), new PriceImpl(60)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-15T00:00:00"), new PriceImpl(70)));    // BUY
            add(new QuoteImpl(LocalDateTime.parse("2016-04-18T00:00:00"), new PriceImpl(80)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-19T00:00:00"), new PriceImpl(90)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-20T00:00:00"), new PriceImpl(100)));
        }};
        TimeSeries<? extends Quote> currency = new TimeSeriesImpl<Quote>(Currency.DKK) {{
            add(new QuoteImpl(LocalDateTime.parse("2016-04-15T00:00:00"), new PriceImpl(9)));    // BUY
            add(new QuoteImpl(LocalDateTime.parse("2016-04-18T00:00:00"), new PriceImpl(8)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-19T12:00:00"), new PriceImpl(7)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-20T00:00:00"), new PriceImpl(6)));
            add(new QuoteImpl(LocalDateTime.parse("2016-04-21T00:00:00"), new PriceImpl(5)));
        }};

        TimeSeries<? extends Quote> normalized = new CurrencyConversion(aDataFactory).denormalize(quotes, currency);
        System.out.println(normalized.stream().map(v -> v.getDateTime().getDayOfMonth()+":"+v.getPrice()).collect(Collectors.toList()));
    }

}
