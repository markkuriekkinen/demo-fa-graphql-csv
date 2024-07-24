package com.fademo.facsvreport;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;

public class CsvHttpMessageConverter extends AbstractGenericHttpMessageConverter<List<Transaction>> {

    public CsvHttpMessageConverter() {
        super(MediaType.parseMediaType("text/csv"));
    }

    @Override
    protected void writeInternal(List<Transaction> transactions, Type type, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        outputMessage.getBody();
        try (CSVPrinter printer = new CSVPrinter(
                new OutputStreamWriter(outputMessage.getBody(), "UTF-8"),
                CSVFormat.EXCEL)) {
            // Print the CSV header first.
            printer.printRecord("portfolioShortName", "securityName", "security ISIN code",
                    "currencyCode", "amount", "unitPrice", "tradeAmount",
                    "typeName", "transactionDate", "settlementDate");
            for (Transaction t : transactions) {
                printer.printRecord(
                    t.portfolioShortName(),
                    t.securityName(),
                    t.security() == null ? null : t.security().isinCode(),
                    t.currencyCode(),
                    t.amount(),
                    t.unitPrice(),
                    t.tradeAmount(),
                    t.typeName(),
                    t.transactionDate(),
                    t.settlementDate());
            }
        }
    }

    @Override
    protected List<Transaction> readInternal(Class<? extends List<Transaction>> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        throw new UnsupportedOperationException("Reading is not implemented. Use this class for writing.");
    }

    @Override
    public List<Transaction> read(Type type, Class<?> contextClass, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        throw new UnsupportedOperationException("Reading is not implemented. Use this class for writing.");
    }
}
