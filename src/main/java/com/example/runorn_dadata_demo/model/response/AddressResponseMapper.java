package com.example.runorn_dadata_demo.model.response;


import com.example.runorn_dadata_demo.model.entity.Address;

public class AddressResponseMapper {
  public static AddressResponseDto toDto(Address address) {
    AddressResponseDto dto = new AddressResponseDto();
    dto.setSource(address.getSource());
    dto.setCountry(address.getCountry());
    dto.setPostalCode(address.getPostalCode());
    return dto;
  }
}
