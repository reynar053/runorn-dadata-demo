package com.example.runorn_dadata_demo.service;

import com.example.runorn_dadata_demo.http.DaDataClient;
import com.example.runorn_dadata_demo.model.*;
import com.example.runorn_dadata_demo.model.entity.*;
import com.example.runorn_dadata_demo.model.request.OrderRequest;
import com.example.runorn_dadata_demo.model.response.DaDataApiResponse;
import com.example.runorn_dadata_demo.repository.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final DaDataClient daDataClient;
    private final AddressSaver addressSaver;

    public OrderService(OrderItemRepository orderItemRepository, OrderRepository orderRepository, UserService userService,
                       DaDataClient daDataClient, @Qualifier("addressDB") AddressSaver addressSaver) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.daDataClient = daDataClient;
        this.addressSaver = addressSaver;
    }

    @Transactional
    public OrderDto createOrder(OrderRequest orderRequest) {
        if (orderRequest == null) {
            throw new IllegalArgumentException("Order request cannot be null");
        }
        if (!StringUtils.hasText(orderRequest.getUsername())) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (!StringUtils.hasText(orderRequest.getRawAddress())) {
            throw new IllegalArgumentException("Address cannot be empty");
        }
        if (orderRequest.getItems() == null || orderRequest.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }
        
        User user = userService.getUserByLogin(orderRequest.getUsername());
        UserDto userDto = UserMapper.toDto(user);

        List<DaDataApiResponse> daDataApiResponseList = daDataClient.sendRequest(orderRequest.getRawAddress());
        DaDataApiResponse daDataApiResponse = daDataApiResponseList.get(0);
        Address address = AddressDBMapper.toDtoDaDAta(daDataApiResponse);
        address.setUser(user);
        addressSaver.saveFirstAddress(address);
        AddressDto addressDto = AddressDBMapper.toDto(address);
        addressDto.setUser(userDto);

        Order order = OrderMapper.toOrder(orderRequest);
        order.setUser(user);
        order.setShippingAddress(address);
        orderRepository.save(order);
        orderItemRepository.saveAll(order.getItems());

        OrderDto orderDto = OrderMapper.toDto(order);
        orderDto.setUser(userDto);
        orderDto.setAddress(addressDto);

        return orderDto;
    }

    @Transactional(readOnly = true)
    public List<OrderDto> findOrdersByUsername(String username) {
        if (!StringUtils.hasText(username)) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        
        User user = userService.getUserByLogin(username);
        UserDto userDto = UserMapper.toDto(user);
        List<Order> orders = orderRepository.findByUserIdWithDetails(user.getId());
        return orders.stream().map(order -> {
            Address address = order.getShippingAddress();
            AddressDto addressDto = AddressDBMapper.toDto(address);
            addressDto.setUser(userDto);

            OrderDto orderDto = OrderMapper.toDto(order);
            orderDto.setUser(userDto);
            orderDto.setAddress(addressDto);

            return orderDto;
        }).collect(Collectors.toList());
    }
}
