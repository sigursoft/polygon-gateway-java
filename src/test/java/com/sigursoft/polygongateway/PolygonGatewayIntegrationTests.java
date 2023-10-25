package com.sigursoft.polygongateway;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.sigursoft.polygongateway.domain.ExchangeRate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

@WireMockTest(httpPort = 8099)
@SpringBootTest(webEnvironment = RANDOM_PORT, properties = { "polygon.baseUrl=http://localhost:8099" })
public class PolygonGatewayIntegrationTests {

    @Test
    @DisplayName("should return correct exchange rate from polygon mock")
    public void testSuccessfullyReturnExchangeRateFromPolygon(@Autowired WebTestClient webClient) {
        // arrange
        var expectedResponse = new ExchangeRate("USD", "EUR", new BigDecimal("0.9407"));
        stubFor(get(urlEqualTo("/v2/aggs/ticker/C:USDEUR/prev"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("polygon-response.json")));
        // act & assert
        webClient.get()
                .uri("/exchange-rate/USD/EUR")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ExchangeRate.class)
                .isEqualTo(expectedResponse);
    }
}
