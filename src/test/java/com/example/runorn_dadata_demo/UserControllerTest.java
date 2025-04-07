package com.example.runorn_dadata_demo;

import com.example.runorn_dadata_demo.model.User;
import com.example.runorn_dadata_demo.repository.UserRepository;
import com.example.runorn_dadata_demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  private UserService userService;  // Мокаем сервис

  @Mock
  private UserRepository userRepository;  // Мокаем репозиторий

  @Test
  public void createUser_shouldReturnCreatedUser() throws Exception {
    User createdUser = new User("testUser", LocalDate.now());
    createdUser.setId(1L);

    Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(createdUser);

    Mockito.when(userService.createUser("testUser")).thenReturn(createdUser);

    mockMvc.perform(post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"login\":\"testUser\", \"created\":\"" + LocalDate.now() + "\"}"))
        .andExpect(status().isCreated())  // Ожидаем статус 201 CREATED
        .andExpect(jsonPath("$.login").value("testUser"));
  }

  @Test
  public void getUserById_shouldReturnUser() throws Exception {
    Long id = 1L;
    User user = new User("testUser", LocalDate.now());
    user.setId(id);

    Mockito.when(userService.getUserById(id)).thenReturn(user);

    mockMvc.perform(get("/api/users/{id}", id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.login").value("testUser"))
        .andExpect(jsonPath("$.id").value(id));
  }

  @Test
  public void getUserByLogin_shouldReturnUser() throws Exception {
    String login = "testUser";
    User user = new User(login, LocalDate.now());
    user.setId(1L);

    Mockito.when(userService.getUserByLogin(login)).thenReturn(user);

    mockMvc.perform(get("/api/users/by-login/{login}", login))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.login").value(login));
  }

  @Test
  public void getAllUsers_shouldReturnUsersList() throws Exception {
    List<User> users = Arrays.asList(new User("user1", LocalDate.now()), new User("user2", LocalDate.now()));

    Mockito.when(userService.getAllUsers()).thenReturn(users);

    mockMvc.perform(get("/api/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2));
  }

  @Test
  public void updateUser_shouldReturnUpdatedUser() throws Exception {
    Long userId = 1L;
    User updatedUser = new User("updatedUser", LocalDate.now());
    updatedUser.setId(userId);

    Mockito.when(userService.updateUser(Mockito.eq(userId), Mockito.anyString())).thenReturn(updatedUser);

    mockMvc.perform(put("/api/users/{id}", userId)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"login\":\"updatedUser\", \"created\":\"" + LocalDate.now() + "\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.login").value("updatedUser"));
  }

  @Test
  public void deleteUser_shouldReturnNoContent() throws Exception {
    Long userId = 1L;

    mockMvc.perform(delete("/api/users/{id}", userId))
        .andExpect(status().isNoContent());

    Mockito.verify(userService).deleteUser(userId);
  }
}

