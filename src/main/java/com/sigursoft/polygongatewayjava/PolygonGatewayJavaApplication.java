package com.sigursoft.polygongatewayjava;

import com.sigursoft.polygongatewayjava.service.ExchangeRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PolygonGatewayJavaApplication {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Bean
	public CommandLineRunner init(ExchangeRateService exchangeRateService) {
		return args -> {
			var response = exchangeRateService.provideExchangeRate("EUR", "PLN");
			logger.info(response.toString());
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(PolygonGatewayJavaApplication.class, args);
	}

}
