package com.example.runorn_dadata_demo.model.entity;


import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(columnDefinition = "INT GENERATED ALWAYS AS IDENTITY")
  private Long id;

  @Column(name = "login" ,nullable = false, unique = true)
  private String login;

  @Column(name = "created_at", nullable = false)
  private LocalDate created;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private List<Address> addresses;

}
