package com.sigursoft.polygongatewayjava.service;

import com.sigursoft.polygongatewayjava.components.PolygonWebClient;
import com.sigursoft.polygongatewayjava.domain.ExchangeRate;
import com.sigursoft.polygongatewayjava.domain.PolygonForexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ExchangeRateService {

    @Autowired
    private PolygonWebClient polygonWebClient;

    public Mono<ExchangeRate> provideExchangeRate(String buyCurrency, String sellCurrency) {
        Mono<PolygonForexResponse> polygonRate = polygonWebClient.fetchPreviousDayExchangeRate(buyCurrency, sellCurrency);
        return polygonRate
                .flatMap(polygonForexResponse -> Mono.just(polygonForexResponse.results().get(0).vw()))
                .flatMap(rate -> Mono.just(new ExchangeRate(buyCurrency, sellCurrency, rate)));
    }
}
