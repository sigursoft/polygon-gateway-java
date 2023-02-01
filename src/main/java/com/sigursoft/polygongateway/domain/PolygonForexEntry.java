package com.sigursoft.polygongateway.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.Instant;

public record PolygonForexEntry(
        @JsonProperty("T")
        String symbol,
        // The trading volume of the symbol in the given time period.
        Long v,
        // The volume weighted average price.
        BigDecimal vw,
        // The open price for the symbol in the given time period.
        BigDecimal o,
        // The close price for the symbol in the given time period.
        BigDecimal c,
        // The highest price for the symbol in the given time period.
        BigDecimal h,
        // The lowest price for the symbol in the given time period.
        BigDecimal l,
        // The Unix milliseconds timestamp for the start of the aggregate window.
        Instant t,
        // The number of transactions in the aggregate window.
        Long n
) {
}
