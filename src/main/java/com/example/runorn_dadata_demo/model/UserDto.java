package com.example.runorn_dadata_demo.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {
  private Long id;
  private String login;
  private LocalDate created;
}
