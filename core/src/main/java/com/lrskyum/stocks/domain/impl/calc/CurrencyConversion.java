package com.lrskyum.stocks.domain.impl.calc;

import com.lrskyum.stocks.domain.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.function.BiFunction;

/**
 *
 */
public class CurrencyConversion {
    private final DataFactory aDataFactory;

    public CurrencyConversion(DataFactory aDataFactory) {
        this.aDataFactory = aDataFactory;
    }

    public TimeSeries<Quote> normalize(TimeSeries<? extends Quote> quotes, TimeSeries<? extends Quote> currency) throws IOException {
        assert quotes.getCurrency() == currency.getCurrency();
        TimeSeries<Quote> result = aDataFactory.createTimeSeries(Currency.USD);
        return merge(result, quotes, currency, (qQ, qC) -> {
            Price pNew = aDataFactory.createPrice(qQ.getPrice().getValue() / qC.getPrice().getValue());
            return aDataFactory.createQuote(qQ.getDateTime(), pNew);
        });
    }

    public TimeSeries<Quote> denormalize(TimeSeries<? extends Quote> quotes, TimeSeries<? extends Quote> currency) throws IOException {
        assert quotes.getCurrency() == Currency.USD;
        TimeSeries<Quote> result = aDataFactory.createTimeSeries(currency.getCurrency());
        return merge(result, quotes, currency, (qQ, qC) -> {
            Price pNew = aDataFactory.createPrice(qQ.getPrice().getValue() * qC.getPrice().getValue());
            return aDataFactory.createQuote(qQ.getDateTime(), pNew);
        });
    }

    private TimeSeries<Quote> merge(TimeSeries<Quote> result, TimeSeries<? extends Quote> quotes,
                                    TimeSeries<? extends Quote> currency,
                                    BiFunction<? super Quote, ? super Quote, Quote> f) throws IOException {
        final Iterator<? extends Quote> iQuote = quotes.iterator();
        final Iterator<? extends Quote> iCurrency = currency.iterator();
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
                    result.add(f.apply(qQuote, qCurrency));
                    qQuote = qCurrency = null;
                    break;
                } else if (cmp > 0) {
                    // qCurrency is lagging, so get next qCurrency but keep qQuote
                    qCurrency = null;
                } else {
                    // qQuote is lagging, so get next qQuote and keep qCurrency
                    qQuote = null;
                    break;
                }
            }
        }
        return result;
    }
}
