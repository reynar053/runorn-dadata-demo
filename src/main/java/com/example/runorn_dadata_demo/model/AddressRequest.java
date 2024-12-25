package com.example.runorn_dadata_demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Builder(toBuilder = true)
@Data
@Getter
public class AddressRequest {
  @JsonProperty("query")
  private String query;
}
