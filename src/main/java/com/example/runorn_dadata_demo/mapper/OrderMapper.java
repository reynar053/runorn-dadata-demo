package com.example.runorn_dadata_demo.mapper;

import com.example.runorn_dadata_demo.model.OrderDto;
import com.example.runorn_dadata_demo.model.OrderItemDto;
import com.example.runorn_dadata_demo.model.entity.Order;
import com.example.runorn_dadata_demo.model.entity.OrderItem;
import com.example.runorn_dadata_demo.model.entity.OrderStatus;
import com.example.runorn_dadata_demo.model.request.OrderRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderMapper {
    public static OrderDto toDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderDate(order.getOrderDate());
        orderDto.setStatus(order.getStatus());
        orderDto.setId(order.getId());
        orderDto.setUser(UserMapper.toDto(order.getUser()));
        orderDto.setStatus(order.getStatus());
        orderDto.setAddress(AddressDBMapper.toDto(order.getShippingAddress()));
        List<OrderItemDto> itemDtos = order.getItems().stream()
                .map(OrderItemMapper::toDto).toList();
        orderDto.setOrderItems(itemDtos);
        return orderDto;
    }
} 