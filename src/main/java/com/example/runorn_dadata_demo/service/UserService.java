package com.example.runorn_dadata_demo.service;

import com.example.runorn_dadata_demo.model.User;
import com.example.runorn_dadata_demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User createUser(String login) {
    User user = new User();
    user.setLogin(login);
    user.setCreated(LocalDate.now());
    return userRepository.save(user);
  }

  public User getUserById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
  }

  public User getUserByLogin(String login) {
    return userRepository.findByLogin(login)
        .orElseThrow(() -> new RuntimeException("User not found with login: " + login));
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public User updateUser(Long id, String newLogin) {
    User user = getUserById(id);
    user.setLogin(newLogin);
    return userRepository.save(user);
  }

  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }

}
