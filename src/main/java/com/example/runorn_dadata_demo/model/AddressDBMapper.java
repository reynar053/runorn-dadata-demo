package com.example.runorn_dadata_demo.model;

import com.example.runorn_dadata_demo.model.entity.Address;
import com.example.runorn_dadata_demo.model.response.DaDataApiResponse;

import java.time.LocalDateTime;


public class AddressDBMapper {

  public static Address toDtoDaDAta(DaDataApiResponse daDataApiResponse) {
    Address address = new Address();
    address.setCountry(daDataApiResponse.getCountry());
    address.setQc(daDataApiResponse.getQc());
    address.setRegion(daDataApiResponse.getRegion());
    address.setPostalCode(daDataApiResponse.getPostalCode());
    address.setSource(daDataApiResponse.getSource());
    address.setRegionType(daDataApiResponse.getRegionType());
    address.setCreatedAt(LocalDateTime.now());
    return address;
  }
  public static AddressDto toDto(Address address) {
    AddressDto addressDto = new AddressDto();
    addressDto.setCountry(address.getCountry());
    addressDto.setQc(address.getQc());
    addressDto.setRegion(address.getRegion());
    addressDto.setPostalCode(address.getPostalCode());
    addressDto.setSource(address.getSource());
    addressDto.setRegionType(address.getRegionType());
    addressDto.setCreatedAt(address.getCreatedAt());
    addressDto.setId(address.getId());
    return addressDto;
  }
}
