package com.example.runorn_dadata_demo.repository;

import com.example.runorn_dadata_demo.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
