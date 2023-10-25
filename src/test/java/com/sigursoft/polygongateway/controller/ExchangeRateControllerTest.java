package com.sigursoft.polygongateway.controller;

import com.sigursoft.polygongateway.domain.ExchangeRate;
import com.sigursoft.polygongateway.service.ExchangeRateService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebFluxTest(ExchangeRateController.class)
public class ExchangeRateControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ExchangeRateService exchangeRateService;

    @Test
    @DisplayName("should return 200 OK")
    public void shouldReturn200OK() {
        // arrange
        var expectedResponse = new ExchangeRate("USD", "EUR", new BigDecimal("1.0912"));
        when(exchangeRateService.provideExchangeRate("USD", "EUR")).thenReturn(
                Mono.just(expectedResponse)
        );
        // act
        webTestClient.get()
                .uri("/exchange-rate/USD/EUR")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ExchangeRate.class)
                .isEqualTo(expectedResponse);
        // assert
        verify(exchangeRateService).provideExchangeRate("USD", "EUR");
    }
}
