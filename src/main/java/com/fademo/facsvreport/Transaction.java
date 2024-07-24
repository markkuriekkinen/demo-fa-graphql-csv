package com.fademo.facsvreport;

record Transaction(
    String portfolioShortName,
    String securityName,
    Security security,
    String currencyCode,
    double amount,
    double unitPrice,
    double tradeAmount,
    String typeName,
    String transactionDate,
    String settlementDate) {
}

record Security(String isinCode) {
}
