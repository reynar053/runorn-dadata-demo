package com.example.runorn_dadata_demo;

import com.example.runorn_dadata_demo.model.AddressResponse;
import com.example.runorn_dadata_demo.repository.AddressSaverImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AddressSaverImplTest {
  @InjectMocks
  private AddressSaverImpl addressSaver;

  @Mock
  private AddressResponse addressResponse;

  @Spy
  private Map<Integer, AddressResponse> addressMap = new HashMap<>();

  @Test
  void saveFirstAddressTest() {
    List<AddressResponse> addressResponseList = List.of(addressResponse);
    addressSaver.saveFirstAddress(addressResponseList);
    assertEquals(1, addressMap.size());
  }

  @Test
  void saveFirstAddressTestFail() {
    List<AddressResponse> emptyList = List.of();
    assertThrows(IndexOutOfBoundsException.class, () -> addressSaver.saveFirstAddress(emptyList));
  }

  @Test
  void getAddressById_shouldReturnShortResponseForExistingId() {
    AddressResponse address = new AddressResponse();
    address.setSource("мск сухонска 11 89");
    address.setCountry("Россия");
    address.setPostalCode("127642");
    address.setRegion("Москва");
    address.setRegionType("г");
    address.setQc("0");
    List<AddressResponse> addressResponsesList = List.of(address);
    addressSaver.saveFirstAddress(addressResponsesList);

    Optional<AddressResponse> result = addressSaver.getAddressById(1);

    assertTrue(result.isPresent());
    assertEquals("мск сухонска 11 89", result.get().getSource());
    assertEquals("Россия", result.get().getCountry());
    assertEquals("127642", result.get().getPostalCode());
  }
}