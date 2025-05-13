package com.example.runorn_dadata_demo.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DaDataApiResponse {
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
}

