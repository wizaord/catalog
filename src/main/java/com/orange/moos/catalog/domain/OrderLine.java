package com.orange.moos.catalog.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class OrderLine {
    private String customerId;
    private String action;
    private String productId;
    private String status;
    private LocalDateTime createDate = LocalDateTime.now();
    private LocalDateTime updateDate = LocalDateTime.now();
    @Singular
    private Map<String, String> parameters;

}
