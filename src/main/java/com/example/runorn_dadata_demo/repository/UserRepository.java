package com.example.runorn_dadata_demo.repository;

import com.example.runorn_dadata_demo.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByLogin(String login);
}
