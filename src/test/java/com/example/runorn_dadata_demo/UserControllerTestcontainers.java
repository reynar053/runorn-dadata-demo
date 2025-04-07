package com.example.runorn_dadata_demo;

import com.example.runorn_dadata_demo.model.User;
import com.example.runorn_dadata_demo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class UserControllerTestcontainers {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private UserRepository repository;

  @BeforeEach
  void cleanDb() {
    repository.deleteAll();
  }

  @Test
  void testCreateAndReadUser() throws Exception {
    User user = new User("testcontainers", LocalDate.now());

    mockMvc.perform(post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.login").value("testcontainers"));
  }

  @Test
  void testGetAllUsers() throws Exception {
    repository.save(new User("u1", LocalDate.now()));
    repository.save(new User("u2", LocalDate.now()));

    mockMvc.perform(get("/api/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2));
  }
}
