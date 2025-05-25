package com.example.runorn_dadata_demo.model.factory;

import com.example.runorn_dadata_demo.mapper.OrderItemMapper;
import com.example.runorn_dadata_demo.model.entity.*;
import com.example.runorn_dadata_demo.model.request.OrderRequest;

import java.time.LocalDateTime;
import java.util.List;

public class OrderFactory {
  public static Order createOrder(OrderRequest orderRequest, User user, Address address) {
    Order order = new Order();
    order.setOrderDate(LocalDateTime.now());
    order.setUser(user);
    order.setShippingAddress(address);
    List<OrderItem> itemRequests = orderRequest.getItems().stream()
        .map(OrderItemFactory::createOrderItem).peek(orderItem -> orderItem.setOrder(order)).toList();
    order.setItems(itemRequests);
    return order;
  }
}
