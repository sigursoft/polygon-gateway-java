package com.sigursoft.polygongatewayjava.service;

import com.sigursoft.polygongatewayjava.components.PolygonWebClient;
import com.sigursoft.polygongatewayjava.domain.ExchangeRate;
import com.sigursoft.polygongatewayjava.domain.PolygonForexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExchangeRateService {

    @Autowired
    private PolygonWebClient polygonWebClient;

    public ExchangeRate provideExchangeRate(String buyCurrency, String sellCurrency) {
        PolygonForexResponse polygonRate = polygonWebClient.fetchPreviousDayExchangeRate(buyCurrency, sellCurrency);
        var rate = polygonRate.results().get(0).vw();
        return new ExchangeRate(buyCurrency, sellCurrency, rate);
    }
}
