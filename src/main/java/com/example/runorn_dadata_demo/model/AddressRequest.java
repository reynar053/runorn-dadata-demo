package com.example.runorn_dadata_demo.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder(toBuilder = true)
@Data
@Getter
public class AddressRequest {
  private String query;
}
