package com.example.runorn_dadata_demo;

import com.example.runorn_dadata_demo.model.AddressRequestDto;
import com.example.runorn_dadata_demo.model.AddressResponse;
import com.example.runorn_dadata_demo.service.DaDataService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
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


		when(daDataService.cleanAddress(anyString())).thenReturn(addressResponse);


		mockMvc.perform(post(URL_CLEAN_PATH)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(addressRequestDto)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.source").value("мск сухонска 11 89"))
				.andExpect(jsonPath("$.country").value("Россия"))
				.andExpect(jsonPath("$.postal_code").value("127642"))
				.andExpect(jsonPath("$.region").value("Москва"))
				.andExpect(jsonPath("$.region_type").value("г"))
				.andExpect(jsonPath("$.qc").value("0"));
	}


}
