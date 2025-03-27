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
  void saveAddressTest() {
    List<AddressResponse> addressResponseList = List.of(addressResponse);
    addressSaver.saveAddress(addressResponseList);
    assertEquals(1, addressSaver.getAddressesList().size());
  }

  @Test
  void saveAddressTestFail(){
    assertThrows(NullPointerException.class,
        () -> addressSaver.saveAddress(null));
  }
}
