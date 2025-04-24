package com.example.runorn_dadata_demo.service;

import com.example.runorn_dadata_demo.http.DaDataClient;
import com.example.runorn_dadata_demo.model.Address;
import com.example.runorn_dadata_demo.model.AddressResponse;
import com.example.runorn_dadata_demo.model.AddressResponseDto;
import com.example.runorn_dadata_demo.model.AddressResponseMapper;
import com.example.runorn_dadata_demo.repository.AddressSaver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DaDataService {
  private final DaDataClient daDataClient;
  @Qualifier("addressDB")
  private final AddressSaver addressStorage;


  public DaDataService(@Qualifier("addressDB") AddressSaver addressSaver, DaDataClient daDataClient) {
    this.addressStorage = addressSaver;
    this.daDataClient = daDataClient;
  }

  public AddressResponse cleanAddress(String address, String login) {
    List<AddressResponse> addressResponseList;
    addressResponseList = daDataClient.sendRequest(address);
    log.info("{} Вот тут адреса", addressResponseList.toString());
    if (addressResponseList.isEmpty()) {
      throw new NullPointerException("Список пустой :(");
    }
    addressStorage.saveFirstAddress(addressResponseList, login);
    return addressResponseList.get(0);
  }

  public AddressResponseDto getAddressById(Long id) throws Throwable {
   Optional<?> addressResponseOpt = addressStorage.getAddressById(id);
   Address addressResponse = (Address) addressResponseOpt.orElseThrow(() -> new RuntimeException("Значение не найдено!"));
   return AddressResponseMapper.toDto(addressResponse);
  }
}
