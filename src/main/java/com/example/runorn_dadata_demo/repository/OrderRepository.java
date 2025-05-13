package com.example.runorn_dadata_demo.repository;

import com.example.runorn_dadata_demo.model.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
  @Override
  Order save(Order order);

  @EntityGraph(value = "Order.withDetails")
  @Query("SELECT o FROM Order o WHERE o.user.id = :userId")
  List<Order> findByUserIdWithDetails(@Param("userId") Long userId);
}
