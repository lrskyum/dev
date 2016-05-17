package com.lrskyum.stocks.domain;

/**
 *
 */
public interface TradePlan {
    // +FlatFeeTradePlan ($6.95)
    // +PerShareTradePlan ($0.01)
    // GradedPercentageTradePlan
    //      papirtype (aktie, obligation, ...)
    //      exchange specific (DK, NOrdic, EU, ...)
    //      (<= 100,000: 0.75%, min fee, max fee, upper trade limit; > 100,000: 0.50%, no min/max,
    //          averagetrade: additional 0,50% afh. af handelens størrelse)
    // BrokerAssistedTradePlan ($19.95)
    // Minimum Deposit

    Price commission(Price stockPrice, double count);
}
