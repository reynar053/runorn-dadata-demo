package com.example.runorn_dadata_demo.controller;

import com.example.runorn_dadata_demo.model.Order;
import com.example.runorn_dadata_demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// TODO: прикрутить админские действия.
@RestController
@RequestMapping("/api/v1/order/")
@RequiredArgsConstructor
public class OrderController {
  private final OrderService orderService;
  @GetMapping()
  List<Order> getOrders(){
    return orderService.findAll();
  }
}
