package com.sigursoft.polygongateway.components;

import com.jayway.jsonpath.JsonPath;
import com.sigursoft.polygongateway.domain.PolygonForexEntry;
import com.sigursoft.polygongateway.domain.PolygonForexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Component
public class PolygonWebClient {

	@Autowired
	private WebClient polygon;

	public Mono<PolygonForexResponse> fetchPreviousDayExchangeRate(String buyCurrency, String sellCurrency) {
		var currencyPair = buyCurrency + sellCurrency;
		return polygon.get().uri("/v2/aggs/ticker/C:" + currencyPair + "/prev")
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.toEntity(String.class)
                .flatMap(response -> {
					var responseJson = JsonPath.parse(response.getBody());
					var results = List.of(new PolygonForexEntry(
							responseJson.read("$.results[0].T"),
							Long.valueOf(responseJson.read("$.results[0].v", String.class)),
							BigDecimal.valueOf(Double.parseDouble(responseJson.read("$.results[0].vw", String.class))),
							BigDecimal.valueOf(Double.parseDouble(responseJson.read("$.results[0].o", String.class))),
							BigDecimal.valueOf(Double.parseDouble(responseJson.read("$.results[0].c", String.class))),
							BigDecimal.valueOf(Double.parseDouble(responseJson.read("$.results[0].h", String.class))),
							BigDecimal.valueOf(Double.parseDouble(responseJson.read("$.results[0].l", String.class))),
							Instant.ofEpochMilli(responseJson.read("$.results[0].t")),
							Long.valueOf(responseJson.read("$.results[0].n", String.class))));
					PolygonForexResponse forex = new PolygonForexResponse(
							responseJson.read("$.ticker"),
							Long.valueOf(responseJson.read("$.queryCount", String.class)),
							Long.valueOf(responseJson.read("$.resultsCount", String.class)),
							responseJson.read("$.adjusted"),
							results,
							responseJson.read("$.status"),
							responseJson.read("$.request_id"),
							Long.valueOf(responseJson.read("$.count", String.class))
					);
					return Mono.just(forex);
				});
	}

}
