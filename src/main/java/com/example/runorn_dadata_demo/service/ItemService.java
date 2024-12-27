package com.example.runorn_dadata_demo.service;

import com.example.runorn_dadata_demo.model.ItemDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemService {
  List<ItemDto> getItems();
}
