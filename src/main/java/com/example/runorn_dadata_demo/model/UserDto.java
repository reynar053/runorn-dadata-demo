package com.example.runorn_dadata_demo.model;

import com.example.runorn_dadata_demo.model.entity.User;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDto {
  private Long id;
  private String login;
  private LocalDate created;
}
