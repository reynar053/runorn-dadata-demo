package com.example.runorn_dadata_demo.model;

import com.example.runorn_dadata_demo.model.entity.User;

public class UserMapper {
  public static UserDto toDto(User user) {
    UserDto dto = new UserDto();
    dto.setId(user.getId());
    dto.setLogin(user.getLogin());
    dto.setCreated(user.getCreated());
    return dto;
  }
}
