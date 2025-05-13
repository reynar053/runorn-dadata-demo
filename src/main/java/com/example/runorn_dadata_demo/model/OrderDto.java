package com.example.runorn_dadata_demo.model;

import com.example.runorn_dadata_demo.model.entity.OrderItem;
import com.example.runorn_dadata_demo.model.entity.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {
  private Long id;
  private UserDto user;
  private AddressDto address;
  private LocalDateTime orderDate;
  private OrderStatus status;
  private List<OrderItemDto> orderItems;
}
