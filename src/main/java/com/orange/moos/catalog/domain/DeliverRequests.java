package com.orange.moos.catalog.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

public @Data
@Builder
class DeliverRequests {
    private String orderId;
    private String orderReferenceId;
    private LocalDateTime receivedDate = LocalDateTime.now();
    private LocalDateTime updatedDate = LocalDateTime.now();
    @Singular
    private Map<String, String> headerParameters;
    @Singular
    private Set<OrderLine> lines;

}
