package com.example.runorn_dadata_demo.repository;

import com.example.runorn_dadata_demo.model.AddressResponse;

import java.util.List;

public interface AddressSaver {
  void saveFirstAddress(List<AddressResponse> addresses);
  AddressResponse getAddressById(int id);
}
