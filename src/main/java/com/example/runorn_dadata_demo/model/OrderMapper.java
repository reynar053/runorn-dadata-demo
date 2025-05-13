package com.example.runorn_dadata_demo.model;

import com.example.runorn_dadata_demo.model.entity.Order;
import com.example.runorn_dadata_demo.model.entity.OrderItem;
import com.example.runorn_dadata_demo.model.entity.OrderStatus;
import com.example.runorn_dadata_demo.model.request.OrderRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderMapper {
  public static Order toOrder(OrderRequest orderRequest) {
    Order order = new Order();
    order.setOrderDate(LocalDateTime.now());
    order.setStatus(OrderStatus.CREATED);
    List<OrderItem> itemRequests = orderRequest.getItems().stream()
        .map(OrderItemMapper::toOrderItem).peek(orderItem -> orderItem.setOrder(order)).toList();
    order.setItems(itemRequests);
    return order;
  }

  public static OrderDto toDto(Order order) {
    OrderDto orderDto = new OrderDto();
    orderDto.setOrderDate(order.getOrderDate());
    orderDto.setStatus(order.getStatus());
    orderDto.setId(order.getId());
    List<OrderItemDto> itemDtos = order.getItems().stream()
        .map(OrderItemMapper::toDto).toList();
    orderDto.setOrderItems(itemDtos);
    return orderDto;
  }
}
