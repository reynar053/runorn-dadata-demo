package com.example.runorn_dadata_demo.registry;

import com.example.runorn_dadata_demo.model.Item;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemRegistryImpl implements ItemRegistry {
  List<Item> itemStorage = List.of(
      Item.builder().id(1).quantity(9).name("Чак-чак").build(),
      Item.builder().id(2).quantity(100).name("Эчпочмак").build());

  public List<Item> getItems() {
    return itemStorage;
  }
}
