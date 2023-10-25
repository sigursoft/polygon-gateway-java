package com.sigursoft.polygongateway.service;

import com.sigursoft.polygongateway.domain.ExchangeRate;
import com.sigursoft.polygongateway.domain.PolygonForexEntry;
import com.sigursoft.polygongateway.domain.PolygonForexResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.sigursoft.polygongateway.components.PolygonWebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExchangeRateServiceTest {

	@Mock
	PolygonWebClient polygonWebClient;

	@InjectMocks
	ExchangeRateService exchangeRateService;

	@Test
	public void testProvideExchangeRate() {
		// given
		String buyCurrency = "USD";
		String sellCurrency = "EUR";
		List<PolygonForexEntry> results = new ArrayList<>();
		results.add(new PolygonForexEntry("C:EURUSD", 189479L, new BigDecimal("1.0912"), new BigDecimal("1.08602"),
				new BigDecimal("1.10127"), new BigDecimal("1.10327"), new BigDecimal("1.0851"),
				Instant.ofEpochMilli(1675295999999L), 189479L));
		PolygonForexResponse response = new PolygonForexResponse("C:USDEUR", 1L, 1L, true, results, "OK",
				"4f73a05f3ad97ca6598a5f499c6062e3", 1L);
		when(polygonWebClient.fetchPreviousDayExchangeRate(buyCurrency, sellCurrency)).thenReturn(Mono.just(response));
		// when
		ExchangeRate rate = exchangeRateService.provideExchangeRate(buyCurrency, sellCurrency).block();
		// then
		var expectedRate = new BigDecimal("1.0912");
		verify(polygonWebClient).fetchPreviousDayExchangeRate(buyCurrency, sellCurrency);
		assertNotNull(rate);
		assertEquals("USD", rate.buyCurrency(), "buyCurrency should be USD");
		assertEquals("EUR", rate.sellCurrency(), "sellCurrency should be EUR");
		assertEquals(expectedRate, rate.rate(), "rate should be 1");
	}
}
