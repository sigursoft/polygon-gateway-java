package com.sigursoft.polygongateway.service;

import com.sigursoft.polygongateway.components.PolygonWebClient;
import com.sigursoft.polygongateway.domain.ExchangeRate;
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
        var polygonRate = polygonWebClient.fetchPreviousDayExchangeRate(buyCurrency, sellCurrency);
        return polygonRate.log().flatMap(
                        polygonForexResponse -> Mono.just(
                                new ExchangeRate(buyCurrency, sellCurrency, polygonForexResponse.results().get(0).vw())
                        )
                );
    }
}
