package com.sigursoft.polygongateway.domain;

import java.math.BigDecimal;

public record ExchangeRate(String buyCurrency, String sellCurrency, BigDecimal rate) { }
