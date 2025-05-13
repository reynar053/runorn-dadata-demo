package com.example.runorn_dadata_demo.repository;

import com.example.runorn_dadata_demo.model.entity.Address;

import java.util.Optional;

public interface AddressSaver {
  void saveFirstAddress(Address address);
  Optional<?> getAddressById(Long id);
}
