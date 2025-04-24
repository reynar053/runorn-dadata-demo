package com.example.runorn_dadata_demo.model;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "addresses")
public class Address {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(columnDefinition = "INT GENERATED ALWAYS AS IDENTITY")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "source")
  private String source;
  @Column(name = "country")
  private String country;

  @Column(name = "postal_code")
  private String postalCode;

  @Column(name = "region")
  private String region;

  @Column(name = "region_type")
  private String regionType;

  @Column(name = "qc")
  private String qc;

  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;
}