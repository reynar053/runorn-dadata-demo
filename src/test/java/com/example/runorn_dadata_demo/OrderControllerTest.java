package com.example.runorn_dadata_demo;

import com.example.runorn_dadata_demo.controller.OrderController;
import com.example.runorn_dadata_demo.model.OrderDto;
import com.example.runorn_dadata_demo.model.entity.OrderStatus;
import com.example.runorn_dadata_demo.model.request.OrderItemRequest;
import com.example.runorn_dadata_demo.model.request.OrderRequest;
import com.example.runorn_dadata_demo.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createOrder_shouldReturnCreatedOrder() throws Exception {
        String username = "testUser";
        String rawAddress = "Moscow, Red Square 1";

        OrderItemRequest itemRequest = new OrderItemRequest();
        itemRequest.setProductName("Test Product");
        itemRequest.setQuantity(2);
        itemRequest.setPrice(new BigDecimal("100.00"));

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setUsername(username);
        orderRequest.setRawAddress(rawAddress);
        orderRequest.setItems(List.of(itemRequest));

        OrderDto orderDto = new OrderDto();
        orderDto.setId(1L);
        orderDto.setOrderDate(LocalDateTime.now());
        orderDto.setStatus(OrderStatus.CREATED);

        when(orderService.createOrder(any(OrderRequest.class))).thenReturn(orderDto);

        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("CREATED"));
    }

    @Test
    void getAllOrders_shouldReturnOrdersList() throws Exception {
        String username = "testUser";

        OrderDto orderDto = new OrderDto();
        orderDto.setId(1L);
        orderDto.setOrderDate(LocalDateTime.now());
        orderDto.setStatus(OrderStatus.CREATED);

        List<OrderDto> orders = List.of(orderDto);

        when(orderService.findOrdersByUsername(username)).thenReturn(orders);

        mockMvc.perform(get("/api/orders")
                .param("username", username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].status").value("CREATED"));
    }

    @Test
    void createOrder_shouldReturnBadRequest_whenInvalidRequest() throws Exception {
        OrderRequest invalidRequest = new OrderRequest();

        when(orderService.createOrder(any(OrderRequest.class)))
                .thenThrow(new IllegalArgumentException("Username cannot be empty"));

        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Username cannot be empty"));
    }

    @Test
    void getAllOrders_shouldReturnBadRequest_whenUsernameMissing() throws Exception {
        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createOrder_shouldReturnInternalServerError_whenServiceThrowsException() throws Exception {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setUsername("testUser");
        orderRequest.setRawAddress("Moscow, Red Square 1");
        orderRequest.setItems(List.of(new OrderItemRequest()));

        when(orderService.createOrder(any(OrderRequest.class)))
                .thenThrow(new RuntimeException("Service error"));

        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Service error"));
    }

    @Test
    void getAllOrders_shouldReturnInternalServerError_whenServiceThrowsException() throws Exception {
        String username = "testUser";

        when(orderService.findOrdersByUsername(username))
                .thenThrow(new RuntimeException("Service error"));

        mockMvc.perform(get("/api/orders")
                .param("username", username))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Service error"));
    }
}
