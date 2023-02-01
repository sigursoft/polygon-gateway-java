package com.sigursoft.polygongatewayjava.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PolygonForexResponse(
        // The exchange symbol that this item is traded under.
        String ticker,
        // The number of aggregates (minute or day) used to generate the response.
        Long queryCount,
        // The total number of results for this request.
        Long resultsCount,
        // Whether this response was adjusted for splits.
        Boolean adjusted,
        List<PolygonForexEntry> results,
        // The status of this request's response.
        String status,
        // A request id assigned by the server.
        @JsonProperty("request_id")
        String requestId,
        Long count
) {
}
