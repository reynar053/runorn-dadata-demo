package com.example.runorn_dadata_demo.model;

import com.example.runorn_dadata_demo.model.entity.OrderItem;
import com.example.runorn_dadata_demo.model.request.OrderItemRequest;

public class OrderItemMapper {

  public static OrderItem toOrderItem(OrderItemRequest orderItemRequest) {
    OrderItem orderItem = new OrderItem();
    orderItem.setProductName(orderItemRequest.getProductName());
    orderItem.setQuantity(orderItemRequest.getQuantity());
    orderItem.setPrice(orderItemRequest.getPrice());
    return orderItem;
  }
  public static OrderItemDto toDto(OrderItem orderItem) {
    OrderItemDto orderItemDto = new OrderItemDto();
    orderItemDto.setId(orderItem.getId());
    orderItemDto.setProductName(orderItem.getProductName());
    orderItemDto.setQuantity(orderItem.getQuantity());
    orderItemDto.setPrice(orderItem.getPrice());
    return orderItemDto;
  }
}
