package com.example.runorn_dadata_demo.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;


@Builder(toBuilder = true)
@Data
@Getter
public class AddressRequest {
  @JsonProperty("query")
  private String query;
}
