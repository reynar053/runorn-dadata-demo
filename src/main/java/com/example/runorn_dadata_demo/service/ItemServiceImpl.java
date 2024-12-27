package com.example.runorn_dadata_demo.service;

import com.example.runorn_dadata_demo.model.Item;
import com.example.runorn_dadata_demo.model.ItemDto;
import com.example.runorn_dadata_demo.registry.ItemRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{
  private final ItemRegistry itemRegistry;

  public List<ItemDto> getItems() {
    List<Item> items = itemRegistry.getItems();
    return items.stream()
        .map(item -> ItemDto.builder().name(item.getName()).build())
        .toList();
  }
}
