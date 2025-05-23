package com.example.runorn_dadata_demo.mapper;

import com.example.runorn_dadata_demo.model.OrderItemDto;
import com.example.runorn_dadata_demo.model.entity.OrderItem;
import com.example.runorn_dadata_demo.model.request.OrderItemRequest;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {
    public static OrderItemDto toDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setProductName(orderItem.getProductName());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setPrice(orderItem.getPrice());
        return orderItemDto;
    }
} 