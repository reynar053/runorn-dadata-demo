package com.example.runorn_dadata_demo.model.factory;

import com.example.runorn_dadata_demo.model.entity.OrderItem;
import com.example.runorn_dadata_demo.model.request.OrderItemRequest;

public class OrderItemFactory {
  public static OrderItem createOrderItem(OrderItemRequest orderItemRequest) {
    OrderItem orderItem = new OrderItem();
    orderItem.setProductName(orderItemRequest.getProductName());
    orderItem.setQuantity(orderItemRequest.getQuantity());
    orderItem.setPrice(orderItemRequest.getPrice());
    return orderItem;
  }
}
