package com.example.runorn_dadata_demo.model.factory;

import com.example.runorn_dadata_demo.model.entity.Address;
import com.example.runorn_dadata_demo.model.response.DaDataApiResponse;

import java.time.LocalDateTime;

public class AddressFactory {
  public static Address createAddress(DaDataApiResponse daDataApiResponse) {
    Address address = new Address();
    address.setSource(daDataApiResponse.getSource());
    address.setCountry(daDataApiResponse.getCountry());
    address.setPostalCode(daDataApiResponse.getPostalCode());
    address.setRegion(daDataApiResponse.getRegion());
    address.setRegionType(daDataApiResponse.getRegionType());
    address.setQc(daDataApiResponse.getQc());
    address.setCreatedAt(LocalDateTime.now());
    return address;
  }
}
