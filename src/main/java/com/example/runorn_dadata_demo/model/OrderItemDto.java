package com.example.runorn_dadata_demo.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
  private Long id;
  private String productName;
  private int quantity;
  private BigDecimal price;
}
