package com.example.runorn_dadata_demo.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Order {
//  private int id;
  private List<Item> items;
  private String address;
}
