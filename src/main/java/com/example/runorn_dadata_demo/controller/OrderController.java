package com.example.runorn_dadata_demo.controller;

import com.example.runorn_dadata_demo.model.OrderDto;
import com.example.runorn_dadata_demo.model.request.OrderRequest;
import com.example.runorn_dadata_demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
  private final OrderService orderService;

  @Autowired
  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping
  public ResponseEntity<OrderDto> createOrder(@RequestBody OrderRequest orderRequest) {
    return ResponseEntity.ok(orderService.createOrder(orderRequest));
  }

  @GetMapping
  public ResponseEntity<List<OrderDto>> getAllOrders(@RequestParam(value = "username") String username) {
    return ResponseEntity.ok(orderService.findOrdersByUsername(username));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleException(Exception ex) {
    return ResponseEntity.internalServerError().body(ex.getMessage());
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<String> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
    return ResponseEntity.badRequest().body("Required parameter '" + ex.getParameterName() + "' is missing");
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
    return ResponseEntity.badRequest().body(ex.getMessage());
  }
}
