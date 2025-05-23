package com.example.runorn_dadata_demo.mapper;

import com.example.runorn_dadata_demo.model.UserDto;
import com.example.runorn_dadata_demo.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setLogin(user.getLogin());
        return userDto;
    }
} 