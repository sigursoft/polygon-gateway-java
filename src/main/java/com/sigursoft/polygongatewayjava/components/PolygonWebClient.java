package com.sigursoft.polygongatewayjava.components;

import com.sigursoft.polygongatewayjava.domain.PolygonForexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class PolygonWebClient {

    @Autowired
    private WebClient polygon;

    public PolygonForexResponse fetchPreviousDayExchangeRate(String buyCurrency, String sellCurrency) {
        var currencyPair = buyCurrency + sellCurrency;
        Mono<PolygonForexResponse> exchangeRate = polygon.get()
                .uri("/v2/aggs/ticker/C:" + currencyPair + "/prev")
                .retrieve()
                .bodyToMono(PolygonForexResponse.class);
        return exchangeRate.block();
    }

}
