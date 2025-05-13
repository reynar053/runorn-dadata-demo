package com.example.runorn_dadata_demo.repository;

import com.example.runorn_dadata_demo.model.entity.Address;
import com.example.runorn_dadata_demo.model.entity.User;
import com.example.runorn_dadata_demo.model.response.DaDataApiResponse;

import java.util.List;
import java.util.Optional;

public interface AddressSaver {
  void saveFirstAddress(Address address);
  Optional<?> getAddressById(Long id);
}
