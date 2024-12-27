package com.example.runorn_dadata_demo.controller;

import com.example.runorn_dadata_demo.model.ItemDto;
import com.example.runorn_dadata_demo.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/")
@RequiredArgsConstructor
public class ItemController {
  private final ItemService itemService;

  @GetMapping()
  public List<ItemDto> getItem() {
    return itemService.getItems();
  }
}
