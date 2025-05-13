package com.example.runorn_dadata_demo;

import com.example.runorn_dadata_demo.http.DaDataClient;
import com.example.runorn_dadata_demo.model.*;
import com.example.runorn_dadata_demo.model.entity.*;
import com.example.runorn_dadata_demo.model.request.OrderItemRequest;
import com.example.runorn_dadata_demo.model.request.OrderRequest;
import com.example.runorn_dadata_demo.model.response.DaDataApiResponse;
import com.example.runorn_dadata_demo.repository.*;
import com.example.runorn_dadata_demo.service.OrderService;
import com.example.runorn_dadata_demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserService userService;

    @Mock
    private DaDataClient daDataClient;

    @Mock
    private AddressSaver addressSaver;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService(orderItemRepository, orderRepository, userService, daDataClient, addressSaver);
    }

    @Test
    void createOrder_shouldCreateAndReturnOrderDto() {
        String username = "testUser";
        String rawAddress = "Moscow, Red Square 1";
        
        User user = new User();
        user.setId(1L);
        user.setLogin(username);
        user.setCreated(LocalDate.now());

        DaDataApiResponse daDataApiResponse = new DaDataApiResponse();
        Address address = new Address();
        address.setId(1L);
        address.setUser(user);

        OrderItemRequest itemRequest = new OrderItemRequest();
        itemRequest.setProductName("Test Product");
        itemRequest.setQuantity(2);
        itemRequest.setPrice(new BigDecimal("100.00"));

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setUsername(username);
        orderRequest.setRawAddress(rawAddress);
        orderRequest.setItems(List.of(itemRequest));

        Order order = new Order();
        order.setId(1L);
        order.setUser(user);
        order.setShippingAddress(address);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.CREATED);

        when(userService.getUserByLogin(username)).thenReturn(user);
        when(daDataClient.sendRequest(rawAddress)).thenReturn(List.of(daDataApiResponse));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderItemRepository.saveAll(any())).thenReturn(new ArrayList<>());

        OrderDto result = orderService.createOrder(orderRequest);

        assertNotNull(result);
        assertEquals(username, result.getUser().getLogin());
        assertEquals(OrderStatus.CREATED, result.getStatus());
        assertNotNull(result.getOrderItems());
        assertEquals(1, result.getOrderItems().size());

        verify(userService).getUserByLogin(username);
        verify(daDataClient).sendRequest(rawAddress);
        verify(orderRepository).save(any(Order.class));
        verify(orderItemRepository).saveAll(any());
        verify(addressSaver).saveFirstAddress(any(Address.class));
    }

    @Test
    void findOrdersByUsername_shouldReturnListOfOrders() {
        String username = "testUser";
        Long userId = 1L;

        User user = new User();
        user.setId(userId);
        user.setLogin(username);
        user.setCreated(LocalDate.now());

        Address address = new Address();
        address.setId(1L);
        address.setUser(user);

        Order order = new Order();
        order.setId(1L);
        order.setUser(user);
        order.setShippingAddress(address);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.CREATED);

        List<Order> orders = List.of(order);

        when(userService.getUserByLogin(username)).thenReturn(user);
        when(orderRepository.findByUserIdWithDetails(userId)).thenReturn(orders);

        List<OrderDto> result = orderService.findOrdersByUsername(username);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(username, result.get(0).getUser().getLogin());
        assertEquals(OrderStatus.CREATED, result.get(0).getStatus());

        verify(userService).getUserByLogin(username);
        verify(orderRepository).findByUserIdWithDetails(userId);
    }

    @Test
    void createOrder_shouldThrowException_whenUserNotFound() {
        String username = "nonexistentUser";
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setUsername(username);
        orderRequest.setRawAddress("Moscow, Red Square 1");
        
        OrderItemRequest itemRequest = new OrderItemRequest();
        itemRequest.setProductName("Test Product");
        itemRequest.setQuantity(2);
        itemRequest.setPrice(new BigDecimal("100.00"));
        orderRequest.setItems(List.of(itemRequest));

        when(userService.getUserByLogin(username))
            .thenThrow(new RuntimeException("User not found with login: " + username));

        assertThrows(RuntimeException.class, () -> orderService.createOrder(orderRequest));
        verify(userService).getUserByLogin(username);
        verifyNoInteractions(daDataClient, orderRepository, orderItemRepository, addressSaver);
    }

    @Test
    void findOrdersByUsername_shouldThrowException_whenUserNotFound() {
        String username = "nonexistentUser";

        when(userService.getUserByLogin(username))
            .thenThrow(new RuntimeException("User not found with login: " + username));

        assertThrows(RuntimeException.class, () -> orderService.findOrdersByUsername(username));
        verify(userService).getUserByLogin(username);
        verifyNoInteractions(orderRepository);
    }

    @Test
    void createOrder_shouldThrowException_whenUsernameIsEmpty() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setUsername("");
        orderRequest.setRawAddress("Moscow, Red Square 1");
        orderRequest.setItems(List.of(new OrderItemRequest()));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> orderService.createOrder(orderRequest));
        assertEquals("Username cannot be empty", exception.getMessage());
        verifyNoInteractions(userService, daDataClient, orderRepository, orderItemRepository, addressSaver);
    }

    @Test
    void createOrder_shouldThrowException_whenAddressIsEmpty() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setUsername("testUser");
        orderRequest.setRawAddress("");
        orderRequest.setItems(List.of(new OrderItemRequest()));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> orderService.createOrder(orderRequest));
        assertEquals("Address cannot be empty", exception.getMessage());
        verifyNoInteractions(userService, daDataClient, orderRepository, orderItemRepository, addressSaver);
    }

    @Test
    void createOrder_shouldThrowException_whenItemsIsEmpty() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setUsername("testUser");
        orderRequest.setRawAddress("Moscow, Red Square 1");
        orderRequest.setItems(List.of());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> orderService.createOrder(orderRequest));
        assertEquals("Order must contain at least one item", exception.getMessage());
        verifyNoInteractions(userService, daDataClient, orderRepository, orderItemRepository, addressSaver);
    }

    @Test
    void findOrdersByUsername_shouldThrowException_whenUsernameIsEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> orderService.findOrdersByUsername(""));
        assertEquals("Username cannot be empty", exception.getMessage());
        verifyNoInteractions(userService, orderRepository);
    }
}
