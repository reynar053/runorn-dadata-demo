package com.example.runorn_dadata_demo;

import com.example.runorn_dadata_demo.mapper.AddressDBMapper;
import com.example.runorn_dadata_demo.model.AddressDto;
import com.example.runorn_dadata_demo.model.entity.Address;
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

    AddressDto dto = AddressDBMapper.toDto(address);

    assertNotNull(dto);
    assertEquals(address.getSource(), dto.getSource());
    assertEquals(address.getCountry(), dto.getCountry());
    assertEquals(address.getPostalCode(), dto.getPostalCode());
  }
}
