package com.example.runorn_dadata_demo;

import com.example.runorn_dadata_demo.model.Address;
import com.example.runorn_dadata_demo.model.AddressResponse;
import com.example.runorn_dadata_demo.model.User;
import com.example.runorn_dadata_demo.repository.AddressRepository;
import com.example.runorn_dadata_demo.repository.AddressSaverDBImpl;
import com.example.runorn_dadata_demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressSaverDBImplTest {

  @Mock
  private AddressRepository addressRepository;

  @Mock
  private UserService userService;

  @InjectMocks
  private AddressSaverDBImpl addressSaver;

  private AddressResponse addressResponse;
  private User mockUser;

  @BeforeEach
  void setUp() {
    addressResponse = new AddressResponse();
    addressResponse.setCountry("Россия");
    addressResponse.setQc("0");
    addressResponse.setRegion("Москва");
    addressResponse.setPostalCode("127642");
    addressResponse.setSource("мск сухонска 11 89");
    addressResponse.setRegionType("г");

    mockUser = new User();
    mockUser.setLogin("testLogin");
  }

  @Test
  void testSaveFirstAddress_shouldSaveMappedAddress() {
    when(userService.createUser("testLogin")).thenReturn(mockUser);

    addressSaver.saveFirstAddress(List.of(addressResponse), "testLogin");

    ArgumentCaptor<Address> captor = ArgumentCaptor.forClass(Address.class);
    verify(addressRepository, times(1)).save(captor.capture());

    Address savedAddress = captor.getValue();
    assertEquals("Россия", savedAddress.getCountry());
    assertEquals("0", savedAddress.getQc());
    assertEquals("Москва", savedAddress.getRegion());
    assertEquals("127642", savedAddress.getPostalCode());
    assertEquals("мск сухонска 11 89", savedAddress.getSource());
    assertEquals("г", savedAddress.getRegionType());
    assertEquals(mockUser, savedAddress.getUser());
    assertNotNull(savedAddress.getCreatedAt());
  }

  @Test
  void testGetAddressById_shouldReturnAddress() {
    Address address = new Address();
    address.setId(1L);
    when(addressRepository.findById(1L)).thenReturn(Optional.of(address));

    Optional<Address> result = addressSaver.getAddressById(1L);

    assertTrue(result.isPresent());
    assertEquals(1L, result.get().getId());
    verify(addressRepository, times(1)).findById(1L);
  }
}
