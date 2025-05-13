package com.example.runorn_dadata_demo.model.request;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private String username;
    private String rawAddress;
    private List<OrderItemRequest> items;
}
