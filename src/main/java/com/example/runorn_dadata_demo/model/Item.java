package com.example.runorn_dadata_demo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Item {
  private int id;
  private String name;
  private int quantity;
}
