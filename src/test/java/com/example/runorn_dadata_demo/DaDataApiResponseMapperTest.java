package com.example.runorn_dadata_demo;

import com.example.runorn_dadata_demo.model.entity.Address;
import com.example.runorn_dadata_demo.model.response.AddressResponseDto;
import com.example.runorn_dadata_demo.model.response.AddressResponseMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DaDataApiResponseMapperTest {

  @Test
  void toDto_shouldMapAllFieldsCorrectly() {
    Address address = new Address();
    address.setSource("мск сухонска 11 89");
    address.setCountry("Россия");
    address.setPostalCode("127642");

    AddressResponseDto dto = AddressResponseMapper.toDto(address);

    assertNotNull(dto);
    assertEquals(address.getSource(), dto.getSource());
    assertEquals(address.getCountry(), dto.getCountry());
    assertEquals(address.getPostalCode(), dto.getPostalCode());
  }
}
