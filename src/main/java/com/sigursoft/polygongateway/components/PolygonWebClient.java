package com.sigursoft.polygongateway.components;

import com.sigursoft.polygongateway.domain.PolygonForexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class PolygonWebClient {

	@Autowired
	private WebClient polygon;

	public Mono<PolygonForexResponse> fetchPreviousDayExchangeRate(String buyCurrency, String sellCurrency) {
		var currencyPair = buyCurrency + sellCurrency;
		return polygon.get().uri("/v2/aggs/ticker/C:" + currencyPair + "/prev")
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(PolygonForexResponse.class);
	}
}
