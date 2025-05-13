package com.example.runorn_dadata_demo;


import com.example.runorn_dadata_demo.model.entity.User;
import com.example.runorn_dadata_demo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerIT {

  @Container
  static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:13-alpine")
      .withReuse(true)
      .withDatabaseName("testdb")
      .withUsername("test")
      .withPassword("test")
      .withInitScript("init.sql");

  @AfterAll
  static void tearDown() {
    container.close();
  }

  @DynamicPropertySource
  static void  configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.username", container::getUsername);
    registry.add("spring.datasource.password", container::getPassword);
  }
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private UserRepository repository;

  @BeforeEach
  void cleanDb() {
    repository.deleteAll();
  }

  @Test
  void testCreateAndReadUser() throws Exception {
    User user = new User();
    user.setLogin("testUser");
    user.setCreated(LocalDate.now());
    user.setAddresses(Collections.emptyList());

    mockMvc.perform(post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.login").value("testcontainers"));
  }

  @Test
  void testGetAllUsers() throws Exception {
    User user = new User();
    user.setLogin("testUser");
    user.setCreated(LocalDate.now());
    User user2 = new User();
    user2.setLogin("testUser2");
    user2.setCreated(LocalDate.now());
    repository.save(user);
    repository.save(user2);

    mockMvc.perform(get("/api/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2));
  }
}
