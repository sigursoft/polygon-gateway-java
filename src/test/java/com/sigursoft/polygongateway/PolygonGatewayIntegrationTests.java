package com.sigursoft.polygongateway;

import com.sigursoft.polygongateway.domain.ExchangeRate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.wiremock.integrations.testcontainers.WireMockContainer;

import java.math.BigDecimal;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Testcontainers
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class PolygonGatewayIntegrationTests {

    @Container
    static WireMockContainer wiremockServer = new WireMockContainer("wiremock/wiremock:3.9.2")
            .withMappingFromResource("fx", PolygonGatewayIntegrationTests.class, "mapping.json");

    @DynamicPropertySource
    static void props(DynamicPropertyRegistry registry) {
        registry.add("polygon.baseUrl", () -> "http://localhost:" + wiremockServer.getPort());
    }

    @Test
    @DisplayName("should return correct exchange rate from polygon mock")
    public void testSuccessfullyReturnExchangeRateFromPolygon(@Autowired WebTestClient webClient) {
        // arrange
        var expectedResponse = new ExchangeRate("USD", "EUR", new BigDecimal("0.9407"));
        // act & assert
        webClient.get()
                .uri("/exchange-rate/USD/EUR")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ExchangeRate.class)
                .isEqualTo(expectedResponse);
    }
}
