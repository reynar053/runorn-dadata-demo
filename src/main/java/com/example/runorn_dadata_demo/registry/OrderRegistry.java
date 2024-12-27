package com.example.runorn_dadata_demo.registry;

import com.example.runorn_dadata_demo.model.Item;
import com.example.runorn_dadata_demo.model.Order;

import java.util.List;
import java.util.Map;

public interface OrderRegistry {
  void registerOrder(Order order);
  Map<String, List<Item>> getOrders();
}
