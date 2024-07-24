package com.fademo.facsvreport;

import java.util.List;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

@Configuration(proxyBeanMethods = false)
public class HttpMessageConvertersConfiguration {
    @Bean
    public HttpMessageConverters customConverters() {
        HttpMessageConverter<List<Transaction>> csvConverter = new CsvHttpMessageConverter();
        return new HttpMessageConverters(csvConverter);
    }
}
