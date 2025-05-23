package com.example.runorn_dadata_demo;

import com.example.runorn_dadata_demo.mapper.AddressDBMapper;
import com.example.runorn_dadata_demo.model.entity.Address;
import com.example.runorn_dadata_demo.model.factory.AddressFactory;
import com.example.runorn_dadata_demo.model.response.DaDataApiResponse;
import com.example.runorn_dadata_demo.repository.AddressSaverImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class AddressSaverImplTest {
  @InjectMocks
  private AddressSaverImpl addressSaver;

  @Mock
  private DaDataApiResponse daDataApiResponse;

  @Spy
  private Map<Long, DaDataApiResponse> addressMap = new HashMap<>();

  @Test
  void saveFirstAddressTest() {
    addressSaver.saveFirstAddress(AddressFactory.createAddress(daDataApiResponse));
    assertEquals(1, addressMap.size());
  }

  @Test
  void saveFirstAddressTestFail() {

    assertThrows(IndexOutOfBoundsException.class, () -> addressSaver.saveFirstAddress(any()));
  }

  @Test
  void getAddressById_shouldReturnShortResponseForExistingId() {
    DaDataApiResponse address = new DaDataApiResponse();
    address.setSource("мск сухонска 11 89");
    address.setCountry("Россия");
    address.setPostalCode("127642");
    address.setRegion("Москва");
    address.setRegionType("г");
    address.setQc("0");

    addressSaver.saveFirstAddress(AddressFactory.createAddress(address));

    Optional<Address> result = addressSaver.getAddressById(1L);

    assertTrue(result.isPresent());
    assertEquals("мск сухонска 11 89", result.get().getSource());
    assertEquals("Россия", result.get().getCountry());
    assertEquals("127642", result.get().getPostalCode());
  }
}