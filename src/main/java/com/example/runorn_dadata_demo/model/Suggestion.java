package com.example.runorn_dadata_demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Suggestion {
  @JsonProperty("suggestions")
  String suggestions;
}
