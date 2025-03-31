package com.example.runorn_dadata_demo.repository;

import com.example.runorn_dadata_demo.model.AddressResponse;

import java.util.List;
import java.util.Optional;

public interface AddressSaver {
  void saveFirstAddress(List<AddressResponse> addresses);
  Optional<AddressResponse> getAddressById(int id);
}
