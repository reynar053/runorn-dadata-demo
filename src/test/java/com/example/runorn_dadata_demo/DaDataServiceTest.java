package com.example.runorn_dadata_demo;

import com.example.runorn_dadata_demo.http.DaDataClient;
import com.example.runorn_dadata_demo.model.Address;
import com.example.runorn_dadata_demo.model.AddressResponse;
import com.example.runorn_dadata_demo.model.AddressResponseDto;
import com.example.runorn_dadata_demo.model.AddressResponseMapper;
import com.example.runorn_dadata_demo.repository.AddressSaverDBImpl;
import com.example.runorn_dadata_demo.service.DaDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class DaDataServiceTest {

  @InjectMocks
  private DaDataService daDataService;
  @Mock
  private AddressSaverDBImpl addressSaverDBImpl;
  @Mock
  private DaDataClient daDataClient;

  @BeforeEach
  void setUp() {

  }

  @Test
  void cleanAddressTest() {
    AddressResponse addressResponse = new AddressResponse();
    addressResponse.setSource("мск сухонска 11 89");
    addressResponse.setCountry("Россия");
    addressResponse.setPostalCode("127642");
    addressResponse.setRegion("Москва");
    addressResponse.setRegionType("г");
    addressResponse.setQc("0");

    List<AddressResponse> addressResponsesList = new ArrayList<>();
    addressResponsesList.add(addressResponse);

    when(daDataClient.sendRequest(anyString())).thenReturn(addressResponsesList);

    AddressResponse result = daDataService.cleanAddress("мск сухонска 11 89", "pivo");
    assertNotNull(result);
    assertEquals("мск сухонска 11 89", result.getSource());
    assertEquals("Россия", result.getCountry());
    assertEquals("127642", result.getPostalCode());
    assertEquals("Москва", result.getRegion());
    assertEquals("г", result.getRegionType());
    assertEquals("0", result.getQc());

    verify(addressSaverDBImpl).saveFirstAddress(any(),any());
  }

  @Test
  void cleanAddressTestError() {
    List<AddressResponse> emptyList = Collections.emptyList();
    when(daDataClient.sendRequest(anyString())).thenReturn(emptyList);

    NullPointerException exception = assertThrows(NullPointerException.class, () -> daDataService.cleanAddress("мск сухонска 11 89", "pivo"));
    assertEquals("Список пустой :(", exception.getMessage());
  }

  @Test
  void getAddressByIdTest() throws Throwable {
    Address addressResponse = new Address();
    AddressResponseDto expectedDto = AddressResponseMapper.toDto(addressResponse);

    when(addressSaverDBImpl.getAddressById(1L)).thenReturn(Optional.of(addressResponse));
    AddressResponseDto result = daDataService.getAddressById(1L);

    assertNotNull(result);
    assertEquals(expectedDto, result);
    verify(addressSaverDBImpl, times(1)).getAddressById(1L);
  }

  @Test
  void getAddressByIdTestError() {
    when(addressSaverDBImpl.getAddressById(999L)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class,
        () -> daDataService.getAddressById(999L));

    assertEquals("Значение не найдено!", exception.getMessage());
    verify(addressSaverDBImpl, times(1)).getAddressById(999L);
  }
}
