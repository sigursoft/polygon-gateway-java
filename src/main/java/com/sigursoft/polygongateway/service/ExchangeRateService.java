package com.sigursoft.polygongateway.service;

import com.sigursoft.polygongateway.components.PolygonWebClient;
import com.sigursoft.polygongateway.domain.ExchangeRate;
import com.sigursoft.polygongateway.domain.PolygonForexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ExchangeRateService {

    private final PolygonWebClient polygonWebClient;

    @Autowired
    public ExchangeRateService(PolygonWebClient polygonWebClient) {
        this.polygonWebClient = polygonWebClient;
    }

    public Mono<ExchangeRate> provideExchangeRate(String buyCurrency, String sellCurrency) {
        Mono<PolygonForexResponse> polygonRate = polygonWebClient.fetchPreviousDayExchangeRate(buyCurrency, sellCurrency);
        return polygonRate
                .flatMap(polygonForexResponse -> Mono.just(polygonForexResponse.results().get(0).vw()))
                .flatMap(rate -> Mono.just(new ExchangeRate(buyCurrency, sellCurrency, rate)));
    }
}
