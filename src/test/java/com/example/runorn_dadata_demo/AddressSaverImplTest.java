package com.example.runorn_dadata_demo;

import com.example.runorn_dadata_demo.model.AddressResponse;
import com.example.runorn_dadata_demo.repository.AddressSaverImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AddressSaverImplTest {
  @InjectMocks
  private AddressSaverImpl addressSaver;

  @Mock
  private AddressResponse addressResponse;

  @Test
  void saveFirstAddressTest() {
    List<AddressResponse> addressResponseList = List.of(addressResponse);
    addressSaver.saveFirstAddress(addressResponseList);
    assertEquals(addressResponse, addressSaver.getAddressById(1));
  }

  @Test
  void saveFirstAddressTestFail(){
    AddressResponse result = addressSaver.getAddressById(999);
    assertNull(result);
  }
}
