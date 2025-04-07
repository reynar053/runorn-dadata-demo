package com.example.runorn_dadata_demo.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;


import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
  @Setter
  @Getter
  @jakarta.persistence.Id
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String login;

  @Column(name = "created_at", nullable = false)
  private LocalDate created;

  public User(String login, LocalDate created) {
    this.login = login;
    this.created = created;
  }
}
