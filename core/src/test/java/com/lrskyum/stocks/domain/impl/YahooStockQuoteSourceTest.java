package com.lrskyum.stocks.domain.impl;

import com.lrskyum.stocks.domain.Currency;
import com.lrskyum.stocks.domain.DataFactory;
import com.lrskyum.stocks.domain.HistoricQuote;
import com.lrskyum.stocks.domain.TimeSeries;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class YahooStockQuoteSourceTest {
    @Test
    public void testGetQuotes() throws Exception {
        YahooStockQuoteSource source = new YahooStockQuoteSource(new DataFactory());
        // 1st time
        TimeSeries<HistoricQuote> series = source.getQuotes(new StockImpl("MSFT"));
        Assert.assertNotNull(series);
        Assert.assertTrue(series.size() > 500);
        Assert.assertEquals(series.getCurrency(), Currency.USD);
        Assert.assertTrue(series.getEarliest().getAdjusted().getValue() > 0.0);
        Assert.assertTrue(series.getLatest().getAdjusted().getValue() > 5.0);
        // 2nd time
        series = source.getQuotes(new StockImpl("MSFT"));
        Assert.assertNotNull(series);
        Assert.assertTrue(series.size() > 500);
        Assert.assertEquals(series.getCurrency(), Currency.USD);
        Assert.assertTrue(series.getEarliest().getAdjusted().getValue() > 0.0);
        Assert.assertTrue(series.getLatest().getAdjusted().getValue() > 5.0);
    }
}
