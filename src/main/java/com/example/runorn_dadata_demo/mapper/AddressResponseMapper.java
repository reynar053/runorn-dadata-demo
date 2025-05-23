package com.example.runorn_dadata_demo.mapper;

import com.example.runorn_dadata_demo.model.response.AddressResponseDto;
import com.example.runorn_dadata_demo.model.response.DaDataApiResponse;
import org.springframework.stereotype.Component;

@Component
public class AddressResponseMapper {
    public static AddressResponseDto toDto(DaDataApiResponse daDataApiResponse) {
        AddressResponseDto addressResponseDto = new AddressResponseDto();
        addressResponseDto.setSource(daDataApiResponse.getSource());
        addressResponseDto.setCountry(daDataApiResponse.getCountry());
        addressResponseDto.setPostalCode(daDataApiResponse.getPostalCode());
        return addressResponseDto;
    }
} 