package com.example.runorn_dadata_demo.service;

import com.example.runorn_dadata_demo.http.DaDataClient;
import com.example.runorn_dadata_demo.mapper.OrderMapper;
import com.example.runorn_dadata_demo.model.OrderDto;
import com.example.runorn_dadata_demo.model.entity.Address;
import com.example.runorn_dadata_demo.model.entity.Order;
import com.example.runorn_dadata_demo.model.entity.OrderStatus;
import com.example.runorn_dadata_demo.model.entity.User;
import com.example.runorn_dadata_demo.model.factory.OrderFactory;
import com.example.runorn_dadata_demo.model.request.OrderRequest;
import com.example.runorn_dadata_demo.repository.AddressSaver;
import com.example.runorn_dadata_demo.repository.OrderItemRepository;
import com.example.runorn_dadata_demo.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final DaDataClient daDataClient;
    private final AddressSaver addressSaver;
    private final AddressService addressService;

    public OrderService(OrderItemRepository orderItemRepository, OrderRepository orderRepository,
                        UserService userService, DaDataClient daDataClient,
                        @Qualifier("addressDB") AddressSaver addressSaver, AddressService addressService) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.daDataClient = daDataClient;
        this.addressSaver = addressSaver;
      this.addressService = addressService;
    }

    @Transactional
    public OrderDto createOrder(OrderRequest orderRequest) {
        log.info("Creating new order for user: {}", orderRequest.getUsername());
        
        User user = userService.getUserByLogin(orderRequest.getUsername());
        log.debug("Found user: {}", user.getLogin());

        Address address = addressService.createAddressWithDaData(orderRequest.getRawAddress());
        address.setUser(user);
        addressSaver.saveFirstAddress(address);
        log.debug("Created and saved address: {}", address.getSource());

        Order order = OrderFactory.createOrder(orderRequest, user, address);
        order.setStatus(OrderStatus.CREATED);
        orderRepository.save(order);
        orderItemRepository.saveAll(order.getItems());
        log.info("Order created successfully with id: {}", order.getId());

        return OrderMapper.toDto(order);
    }

    @Transactional(readOnly = true)
    public List<OrderDto> findOrdersByUsername(String username) {
        log.info("Finding orders for user: {}", username);
        
        User user = userService.getUserByLogin(username);
        List<Order> orders = orderRepository.findByUserIdWithDetails(user.getId());
        log.debug("Found {} orders for user: {}", orders.size(), username);
        
        return orders.stream().map(OrderMapper::toDto).collect(Collectors.toList());
    }
}
