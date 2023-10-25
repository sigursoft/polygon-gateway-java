package com.sigursoft.polygongateway.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PolygonForexResponse(
        // The exchange symbol that this item is traded under.
        String ticker,
        // The number of aggregates (minute or day) used to generate the response.
        Long queryCount,
        // The total number of results for this request.
        Long resultsCount,
        // Whether this response was adjusted for splits.
        Boolean adjusted,
        // Exchange rate data for the requested symbol.
        List<PolygonForexEntry> results,
        // The status of this request's response.
        String status,
        // A request id assigned by the server.
        @JsonProperty("request_id") String requestId, Long count) {
}
