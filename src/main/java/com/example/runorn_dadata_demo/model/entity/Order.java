package com.example.runorn_dadata_demo.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NamedEntityGraph(
    name = "Order.withDetails",
    attributeNodes = {
        @NamedAttributeNode("user"),
        @NamedAttributeNode("shippingAddress"),
        @NamedAttributeNode(value = "items")
    }
)
@Entity
@Table(name = "orders")
@Data
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "shipping_address_id", nullable = false)
  private Address shippingAddress;

  @Column(nullable = false)
  private LocalDateTime orderDate;

  @Type(OrderStatusType.class)
  @Column(nullable = false)
  private OrderStatus status;

  @OneToMany(mappedBy = "order")
  private List<OrderItem> items = new ArrayList<>();
}
