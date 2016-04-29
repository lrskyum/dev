/*
 * Copyright (c) 2016.
 * Lars Ruben Skyum
 */

package com.lrskyum.stocks.domain.impl;

import com.lrskyum.stocks.domain.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

/**
 *
 */
public class YahooQuoteSource {
    private final DataFactory aDataFactory;
    private final QuoteRepository aRepository;

    public YahooQuoteSource(DataFactory aDataFactory) {
        this.aDataFactory = aDataFactory;
        aRepository = aDataFactory.createQuoteRepository();
    }

    public TimeSeries<HistoricQuote> getQuotes(String id, Currency currency) throws IOException {
        TimeSeries<HistoricQuote> list = null;
        try {
            list = aRepository.restore(id);
            if (list == null) {
                list = requestQuotes(id, currency);
                aRepository.store(id, list);
            } else {
                final LocalDateTime now = LocalDateTime.now();
                final DayOfWeek dow = now.getDayOfWeek();
                final LocalDateTime latest = list.getLatest().getDateTime();    // Not intra day so far
                final boolean exchangeWasOpenYesterday = dow != DayOfWeek.MONDAY && dow != DayOfWeek.SUNDAY;
                final boolean shouldPullForYesterday = Duration.between(latest.truncatedTo(ChronoUnit.DAYS), now).toHours() > 2*24+6; // 6am the day after.
                if (exchangeWasOpenYesterday && shouldPullForYesterday) {
                    TimeSeries<HistoricQuote> rest = requestQuotes(id, latest.plusDays(1).toLocalDate(), currency);
                    if (rest.size() > 0) {
                        list.addAll(rest);
                        aRepository.store(id, list);
                    }
                }
            }
        }
        catch (ClassNotFoundException e) {
            throw new IOException(e);
        }
        return list;
    }

    public TimeSeries<HistoricQuote> requestQuotes(String id, Currency currency) throws IOException {
        return requestQuotes(id, LocalDate.of(1901, 1, 1), currency);
    }

    public TimeSeries<HistoricQuote> requestQuotes(String id, LocalDate from, Currency currency) throws IOException {
        System.out.println(id + " from date " + from);
        final int day = from.getDayOfMonth();
        final int month = from.getMonth().getValue()-1; // Month is zero indexed at yahoo.
        final int year = from.getYear();

        final TimeSeries<HistoricQuote> quotes = new TimeSeriesImpl<>(currency, 6000);
        URLConnection urlConn = null;
        InputStreamReader inStream = null;
        BufferedReader buff = null;

        try {
            final String symbol = URLEncoder.encode(id);
            final URL url = new URL("http://ichart.finance.yahoo.com/table.csv?s="+symbol+"&g=d&a="+month+"&b="+day+"&c="+year+"&ignore=.csv");

            urlConn = url.openConnection();
            inStream = new InputStreamReader(urlConn.getInputStream());
            buff = new BufferedReader(inStream);
            String line = null;
            while ((line = buff.readLine()) != null) {
                // Skip header line
                if (line.startsWith("Date")) {
                    continue;
                }

                String[] fields = line.split("[,]");
                // Date,Open,High,Low,Close,Volume,Adj Close
                if (fields.length == 7) {
                    try {
                        final LocalDate date = LocalDate.parse(fields[0], DateTimeFormatter.ISO_LOCAL_DATE);
                        final Price open = aDataFactory.createPrice(fields[1]);
                        final Price high = aDataFactory.createPrice(fields[2]);
                        final Price low = aDataFactory.createPrice(fields[3]);
                        final Price close = aDataFactory.createPrice(fields[4]);
                        final long volume = Long.parseLong(fields[5]);
                        final Price adjusted = aDataFactory.createPrice(fields[6]);
                        final HistoricQuote q = aDataFactory.createQuote(date, open, high, low, close, adjusted, volume);
                        quotes.add(q);
                    }
                    catch (DateTimeParseException e) {
                        throw new IOException(e);
                    }
                }
            }
        }
        catch (MalformedURLException e) {
            throw new IOException(e);
        }
        finally {
            try {
                if (buff != null) {
                    buff.close();
                }
            }
            catch (IOException e) {
                System.err.println(e);
            }
        }
        Collections.reverse(quotes);
        return quotes;
    }
}
