package com.example.runorn_dadata_demo.registry;

import com.example.runorn_dadata_demo.model.Item;
import com.example.runorn_dadata_demo.model.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderRegistryImpl implements OrderRegistry {
  Map<String, List<Item>> orders = new HashMap<>();

  @Override
  public void registerOrder(Order order) {
    orders.put(order.getAddress(), order.getItems());
  }

  public Map<String, List<Item>> getOrders() {
    return orders;
  }
}
