package com.example.runorn_dadata_demo.repository;

import com.example.runorn_dadata_demo.model.AddressResponse;

import java.util.List;

public interface AddressSaver {
  void saveAddress(List<AddressResponse> addresses);
}
