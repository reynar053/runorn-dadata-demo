package com.example.runorn_dadata_demo.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserDto {
  private Long id;
  private String login;
  private LocalDate created;
  private List<AddressDto> addresses;
}
