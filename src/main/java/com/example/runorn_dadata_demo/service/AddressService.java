package com.example.runorn_dadata_demo.service;

import com.example.runorn_dadata_demo.http.DaDataClient;
import com.example.runorn_dadata_demo.model.AddressDBMapper;
import com.example.runorn_dadata_demo.model.entity.Address;
import com.example.runorn_dadata_demo.model.entity.User;
import com.example.runorn_dadata_demo.model.response.DaDataApiResponse;
import com.example.runorn_dadata_demo.model.response.AddressResponseDto;
import com.example.runorn_dadata_demo.model.response.AddressResponseMapper;
import com.example.runorn_dadata_demo.repository.AddressSaver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service

public class AddressService {
  private final DaDataClient daDataClient;
  private final AddressSaver addressSaver;
  private final UserService userService;

  public AddressService(DaDataClient daDataClient,
                        @Qualifier("addressDB") AddressSaver addressSaver, UserService userService) {
    this.daDataClient = daDataClient;
    this.addressSaver = addressSaver;
    this.userService = userService;
  }

  public DaDataApiResponse cleanAddress(String address, String login) {
    List<DaDataApiResponse> daDataApiResponseList;
    daDataApiResponseList = daDataClient.sendRequest(address);
    log.info("{} Вот тут адреса", daDataApiResponseList.toString());
    if (daDataApiResponseList.isEmpty()) {
      throw new NullPointerException("Список пустой :(");
    }
    User user = userService.createUser(login);
    DaDataApiResponse daDataApiResponse = daDataApiResponseList.get(0);
    Address addressDto = AddressDBMapper.toDtoDaDAta(daDataApiResponse);
    addressDto.setUser(user);
    addressSaver.saveFirstAddress(addressDto);
    return daDataApiResponse;
  }

  public AddressResponseDto getAddressById(Long id) throws Throwable {
   Optional<?> addressResponseOpt = addressSaver.getAddressById(id);
   Address addressResponse = (Address) addressResponseOpt.orElseThrow(() ->
       new RuntimeException("Значение не найдено!"));
   return AddressResponseMapper.toDto(addressResponse);
  }
}
