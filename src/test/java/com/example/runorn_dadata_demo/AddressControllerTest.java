package com.example.runorn_dadata_demo;

import com.example.runorn_dadata_demo.controller.AddressController;
import com.example.runorn_dadata_demo.model.AddressRequestDto;
import com.example.runorn_dadata_demo.model.AddressResponse;
import com.example.runorn_dadata_demo.model.AddressResponseDto;
import com.example.runorn_dadata_demo.model.User;
import com.example.runorn_dadata_demo.service.DaDataService;
import com.example.runorn_dadata_demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AddressControllerTest {
  public static final String URL_CLEAN_PATH = "/api/v1/address/clean";

  @Autowired
  private MockMvc mockMvc;

  @Mock
  private DaDataService daDataService;

  @Autowired
  private ObjectMapper objectMapper;

  @Mock
  private UserService userService;

  @Mock
  private AddressResponseDto addressResponseDto;

  @InjectMocks
  private AddressController addressController;

  @BeforeEach
  void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(addressController).build();

    User fakeUser = new User("test-user", LocalDate.now());
    fakeUser.setId(1L);
    fakeUser.setAddresses(Collections.emptyList());

    when(userService.createUser("test-user")).thenReturn(fakeUser);
  }

  @Test
  void cleanAddressTest() throws Exception {
    AddressRequestDto addressRequestDto = new AddressRequestDto();
    addressRequestDto.setCity("мск");
    addressRequestDto.setStreet("сухонска 11");
    addressRequestDto.setApartment("89");

    AddressResponse addressResponse = new AddressResponse();
    addressResponse.setSource("мск сухонска 11 89");
    addressResponse.setCountry("Россия");
    addressResponse.setPostalCode("127642");
    addressResponse.setRegion("Москва");
    addressResponse.setRegionType("г");
    addressResponse.setQc("0");

    when(daDataService.cleanAddress("мск сухонска 11 89", "test-user")).thenReturn(addressResponse);


    mockMvc.perform(post(URL_CLEAN_PATH)
            .header("X-User-Login", "test-user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(addressRequestDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.source").value("мск сухонска 11 89"))
        .andExpect(jsonPath("$.country").value("Россия"))
        .andExpect(jsonPath("$.postal_code").value("127642"))
        .andExpect(jsonPath("$.region").value("Москва"))
        .andExpect(jsonPath("$.region_type").value("г"))
        .andExpect(jsonPath("$.qc").value("0"));

    verify(daDataService,times(1))
        .cleanAddress("мск сухонска 11 89", "test-user");
  }

  @Test
  void getAddressByIdTest() throws Throwable {
    when(daDataService.getAddressById(1L)).thenReturn(addressResponseDto);

    AddressResponseDto testShortResponse = addressController.getAddressById(1L);
    assertNotNull(testShortResponse);
    assertEquals(addressResponseDto, testShortResponse);
    verify(daDataService, times(1)).getAddressById(1L);
  }

  @Test
  void getAddressByIdTestError() throws Throwable {
    when(addressController.getAddressById(999L)).thenThrow(new RuntimeException("Значение не найдено!"));

    RuntimeException exception = assertThrows(RuntimeException.class,
        () -> addressController.getAddressById(999L));

    assertEquals("Значение не найдено!", exception.getMessage());
  }
}
