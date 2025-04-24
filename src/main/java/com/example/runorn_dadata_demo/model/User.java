package com.example.runorn_dadata_demo.model;


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

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Address> addresses;

  public User(String login, LocalDate created) {
    this.login = login;
    this.created = created;
  }
  public User(String login, LocalDate created, List<Address> addresses) {
    this.login = login;
    this.created = created;
    this.addresses = addresses;
  }

}
