package com.sigursoft.polygongateway.controller;

import com.sigursoft.polygongateway.domain.ExchangeRate;
import com.sigursoft.polygongateway.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class ExchangeRateController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExchangeRateService exchangeRateService;

	@GetMapping(value = "/exchange-rate/{buyCurrency}/{sellCurrency}", produces = "application/json")
	public Mono<ExchangeRate> provideExchangeRate(@PathVariable String buyCurrency, @PathVariable String sellCurrency) {
		logger.info("Providing exchange rate");
		return exchangeRateService.provideExchangeRate(buyCurrency, sellCurrency);
	}
}
