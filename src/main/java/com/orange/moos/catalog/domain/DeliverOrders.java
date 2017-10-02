package com.orange.moos.catalog.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@deliverOrdersId", scope = DeliverOrders.class)
@Data
@Builder
public class DeliverOrders {
    private String orderId;
    private String orderReferenceId;
    @Builder.Default
    private LocalDateTime receivedDate = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime updatedDate = LocalDateTime.now();
    @Singular
    private Map<String, String> headerParameters;
    @Singular
    private Set<OrderLine> lines;

}
