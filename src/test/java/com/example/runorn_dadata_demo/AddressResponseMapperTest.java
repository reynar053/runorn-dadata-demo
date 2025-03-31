package com.example.runorn_dadata_demo;

import com.example.runorn_dadata_demo.model.AddressResponse;
import com.example.runorn_dadata_demo.model.AddressResponseDto;
import com.example.runorn_dadata_demo.model.AddressResponseMapper;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class AddressResponseMapperTest {

  @Test
  void toDto_shouldMapAllFieldsCorrectly() {
    AddressResponse address = new AddressResponse();
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
