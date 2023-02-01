package com.sigursoft.polygongatewayjava.domain;

import java.math.BigDecimal;

public record ExchangeRate(
        String buyCurrency,
        String sellCurrency,
        BigDecimal rate
) {
}
