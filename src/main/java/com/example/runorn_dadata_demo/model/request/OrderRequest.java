package com.example.runorn_dadata_demo.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @NotBlank(message = "Address cannot be empty")
    private String rawAddress;

    @NotEmpty(message = "Order must contain at least one item")
    private List<OrderItemRequest> items;
}
