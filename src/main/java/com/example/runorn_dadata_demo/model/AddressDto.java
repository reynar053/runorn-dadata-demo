package com.example.runorn_dadata_demo.model;

import com.example.runorn_dadata_demo.model.entity.Address;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddressDto {
  private Long id;
  private String source;
  private String country;
  private String postalCode;
  private String region;
  private String regionType;
  private String qc;
  private LocalDateTime createdAt;
  private UserDto user;
}
