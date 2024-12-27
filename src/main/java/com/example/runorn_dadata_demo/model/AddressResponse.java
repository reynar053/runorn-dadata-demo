package com.example.runorn_dadata_demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressResponse {
  @JsonProperty
  private String source;
  @JsonProperty("country")
  private String country;
  @JsonProperty("postal_code")
  private String postalCode;
  @JsonProperty("region")
  private String region;
  @JsonProperty("region_type")
  private String regionType;
  @JsonProperty
  private String qc;
  @JsonProperty
  private List<Metro> metro;
}
@Data
class Metro {
  private String name;
  private String line;
  private Double distance;
}

