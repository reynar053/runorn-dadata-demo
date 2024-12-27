package com.example.runorn_dadata_demo.service;

import com.example.runorn_dadata_demo.model.Order;
import com.example.runorn_dadata_demo.registry.OrderRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
  OrderRegistry registry;

  @Override
  public List<Order> findAll() {
    return registry.getOrders()
        .entrySet()
        .stream()
        .map(entry -> Order.builder()
            .address(entry.getKey())
            .items(entry.getValue())
            .build())
        .toList();
  }
}
