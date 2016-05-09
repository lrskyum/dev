package com.lrskyum.stocks.domain.impl.calc;

import com.lrskyum.stocks.domain.*;

import java.io.IOException;
import java.util.Iterator;

/**
 *
 */
public class CurrencyConversion {
    private final DataFactory aDataFactory;

    public CurrencyConversion(DataFactory aDataFactory) {
        this.aDataFactory = aDataFactory;
    }

    public TimeSeries<Quote> normalize(TimeSeries<? extends Quote> quote, TimeSeries<? extends Quote> currency) throws IOException {
        // FIXME: assert that quote currency equals currency
        TimeSeries<Quote> result = aDataFactory.createTimeSeries(Currency.USD);

        Iterator<? extends Quote> iQuote = quote.iterator();
        Iterator<? extends Quote> iCurrency = currency.iterator();
        Quote qQuote = null, qCurrency = null;
        while (iQuote.hasNext() && iCurrency.hasNext()) {
            if (qQuote == null) {
                qQuote = iQuote.next();
            }
            while (iCurrency.hasNext()) {
                if (qCurrency == null) {
                    qCurrency = iCurrency.next();
                }
                final int cmp = qQuote.getDateTime().compareTo(qCurrency.getDateTime());
                if (cmp == 0) {
                    // Match, same date, so put in result list
                    Price pNew = aDataFactory.createPrice(qQuote.getPrice().getValue() / qCurrency.getPrice().getValue());
                    Quote qNew = aDataFactory.createQuote(qQuote.getDateTime(), pNew);
                    result.add(qNew);
                    qQuote = qCurrency = null;
                    break;
                } else if (cmp > 0) {
                    // qCurrency is lagging, so get next qCurrency but keep qQuote
                    qCurrency = null;
                } else {
                    // qQuote is lagging, so get next qqQuote and keep qCurrency
                    qQuote = null;
                    break;
                }
            }
        }
        return result;
    }
}
