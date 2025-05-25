package com.example.runorn_dadata_demo.service;

import com.example.runorn_dadata_demo.http.DaDataClient;
import com.example.runorn_dadata_demo.mapper.AddressDBMapper;
import com.example.runorn_dadata_demo.mapper.AddressResponseMapper;
import com.example.runorn_dadata_demo.model.AddressDto;
import com.example.runorn_dadata_demo.model.entity.Address;
import com.example.runorn_dadata_demo.model.entity.User;
import com.example.runorn_dadata_demo.model.factory.AddressFactory;
import com.example.runorn_dadata_demo.model.response.DaDataApiResponse;
import com.example.runorn_dadata_demo.model.response.AddressResponseDto;
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

  public Address createAddressWithDaData(String address) {
    List<DaDataApiResponse> daDataApiResponseList;
    daDataApiResponseList = daDataClient.sendRequest(address);
    log.info("{} Вот тут адреса", daDataApiResponseList.toString());
    if (daDataApiResponseList.isEmpty()) {
      throw new NullPointerException("Список пустой :(");
    }
    DaDataApiResponse daDataApiResponse = daDataApiResponseList.get(0);
    return AddressFactory.createAddress(daDataApiResponse);
  }

  public Address cleanAddress(String address, String login) {
    User user = userService.createUser(login);
    Address addressCreated = createAddressWithDaData(address);
    addressCreated.setUser(user);
    addressSaver.saveFirstAddress(addressCreated);
    return addressCreated;
  }

  public AddressDto getAddressById(Long id) throws Throwable {
    Optional<?> addressResponseOpt = addressSaver.getAddressById(id);
    Address addressResponse = (Address) addressResponseOpt.orElseThrow(() ->
        new RuntimeException("Значение не найдено!"));
    return AddressDBMapper.toDto(addressResponse);
  }
}
