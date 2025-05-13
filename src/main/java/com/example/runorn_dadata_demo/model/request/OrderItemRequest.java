package com.example.runorn_dadata_demo.model.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemRequest {
  private String productName;
  private int quantity;
  private BigDecimal price;
}
