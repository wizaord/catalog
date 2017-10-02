package com.orange.moos.catalog.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.time.LocalDateTime;
import java.util.Map;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@orderLineId", scope = OrderLine.class)
@Data
@Builder
public class OrderLine {
    private String customerId;
    private String action;
    private String productId;
    private String status;
    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime updateDate = LocalDateTime.now();
    @Singular
    private Map<String, String> parameters;

}
