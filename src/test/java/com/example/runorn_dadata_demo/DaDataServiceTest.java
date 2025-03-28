package com.example.runorn_dadata_demo;

import com.example.runorn_dadata_demo.http.DaDataClient;
import com.example.runorn_dadata_demo.model.AddressResponse;
import com.example.runorn_dadata_demo.repository.AddressSaverImpl;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class DaDataServiceTest {

  @InjectMocks
  private DaDataService daDataService;
  @Mock
  private AddressSaverImpl addressSaverImpl;
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

    AddressResponse result = daDataService.cleanAddress("мск сухонска 11 89");
    assertNotNull(result);
    assertEquals("мск сухонска 11 89", result.getSource());
    assertEquals("Россия", result.getCountry());
    assertEquals("127642", result.getPostalCode());
    assertEquals("Москва", result.getRegion());
    assertEquals("г", result.getRegionType());
    assertEquals("0", result.getQc());

    verify(addressSaverImpl).saveFirstAddress(any());
  }
  @Test
  void cleanAddressTestError(){
    List<AddressResponse> emptyList = Collections.emptyList();
    when(daDataClient.sendRequest(anyString())).thenReturn(emptyList);

    NullPointerException exception = assertThrows(NullPointerException.class, () -> {daDataService.cleanAddress("мск сухонска 11 89");});
    assertEquals("Список пустой :(", exception.getMessage());
  }


}
