package com.example.runorn_dadata_demo;

import com.example.runorn_dadata_demo.model.User;
import com.example.runorn_dadata_demo.repository.UserRepository;
import com.example.runorn_dadata_demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  @Test
  public void testCreateUser() {
    String login = "testUser";
    User expectedUser = new User();
    expectedUser.setLogin(login);
    expectedUser.setCreated(LocalDate.now());

    when(userRepository.save(any(User.class))).thenReturn(expectedUser);

    User actualUser = userService.createUser(login);

    assertNotNull(actualUser);
    assertEquals(login, actualUser.getLogin());
    assertNotNull(actualUser.getCreated());
    verify(userRepository, times(1)).save(any(User.class));
  }

  @Test
  public void testGetUserById_Success() {
    Long id = 1L;
    User expectedUser = new User();
    expectedUser.setId(1L);
    expectedUser.setLogin("testUser");

    when(userRepository.findById(id)).thenReturn(Optional.of(expectedUser));

    User actualUser = userService.getUserById(id);

    assertNotNull(actualUser);
    assertEquals(id, actualUser.getId());
    verify(userRepository, times(1)).findById(id);
  }

  @Test
  public void testGetUserById_NotFound() {
    Long id = 999L;
    when(userRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> userService.getUserById(id));
    verify(userRepository, times(1)).findById(id);
  }

  @Test
  public void testGetUserByLogin_Success() {
    String login = "testUser";
    User expectedUser = new User();
    expectedUser.setId(1L);
    expectedUser.setLogin(login);

    when(userRepository.findByLogin(login)).thenReturn(Optional.of(expectedUser));

    User actualUser = userService.getUserByLogin(login);

    assertNotNull(actualUser);
    assertEquals(login, actualUser.getLogin());
    verify(userRepository, times(1)).findByLogin(login);
  }

  @Test
  public void testGetUserByLogin_NotFound() {
    String login = "nonExistingUser";
    when(userRepository.findByLogin(login)).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> userService.getUserByLogin(login));
    verify(userRepository, times(1)).findByLogin(login);
  }

  @Test
  public void testGetAllUsers() {
    User user1 = new User();
    user1.setId(1L);
    User user2 = new User();
    user2.setId(2L);
    List<User> expectedUsers = List.of(user1, user2);

    when(userRepository.findAll()).thenReturn(expectedUsers);

    List<User> actualUsers = userService.getAllUsers();

    assertNotNull(actualUsers);
    assertEquals(2, actualUsers.size());
    verify(userRepository, times(1)).findAll();
  }

  @Test
  public void testUpdateUser() {
    Long id = 1L;
    String oldLogin = "oldLogin";
    String newLogin = "newLogin";

    User existingUser = new User();
    existingUser.setId(id);
    existingUser.setLogin(oldLogin);

    when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
    when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

    User updatedUser = userService.updateUser(1L, newLogin);

    assertNotNull(updatedUser);
    assertEquals(id, updatedUser.getId());
    assertEquals(newLogin, updatedUser.getLogin());
    verify(userRepository, times(1)).findById(1L);
    verify(userRepository, times(1)).save(existingUser);
  }

  @Test
  public void testDeleteUser() {
    doNothing().when(userRepository).deleteById(1L);

    userService.deleteUser(1L);

    verify(userRepository, times(1)).deleteById(1L);
  }
}
