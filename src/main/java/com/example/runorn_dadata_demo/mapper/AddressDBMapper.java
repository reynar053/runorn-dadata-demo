package com.example.runorn_dadata_demo.mapper;

import com.example.runorn_dadata_demo.model.AddressDto;
import com.example.runorn_dadata_demo.model.entity.Address;
import com.example.runorn_dadata_demo.model.response.DaDataApiResponse;
import org.springframework.stereotype.Component;

@Component
public class AddressDBMapper {
    public static AddressDto toDto(Address address) {
        AddressDto addressDto = new AddressDto();
        addressDto.setId(address.getId());
        addressDto.setSource(address.getSource());
        addressDto.setCountry(address.getCountry());
        addressDto.setPostalCode(address.getPostalCode());
        addressDto.setRegion(address.getRegion());
        addressDto.setRegionType(address.getRegionType());
        addressDto.setQc(address.getQc());
        addressDto.setUser(UserMapper.toDto(address.getUser()));
        return addressDto;
    }
} 