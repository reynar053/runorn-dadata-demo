package com.example.runorn_dadata_demo.service;

import com.example.runorn_dadata_demo.http.DaDataClient;
import com.example.runorn_dadata_demo.model.AddressResponse;
import com.example.runorn_dadata_demo.model.AddressResponseDto;
import com.example.runorn_dadata_demo.model.AddressResponseMapper;
import com.example.runorn_dadata_demo.repository.AddressSaver;
import com.example.runorn_dadata_demo.repository.AddressSaverImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DaDataService {
  private final DaDataClient daDataClient;
  private final AddressSaver addressStorage;


  public DaDataService(AddressSaverImpl addressSaverImpl, DaDataClient daDataClient) {
    this.addressStorage = addressSaverImpl;
    this.daDataClient = daDataClient;

  }

  public AddressResponse cleanAddress(String address) {
    List<AddressResponse> addressResponseList;
    addressResponseList = daDataClient.sendRequest(address);
    log.info("{} PIVO", addressResponseList.toString());
    if (addressResponseList.isEmpty()) {
      throw new NullPointerException("Список пустой :(");
    }
    addressStorage.saveFirstAddress(addressResponseList);
    return addressResponseList.get(0);
  }

  public AddressResponseDto getAddressById(int id) {
   Optional<AddressResponse> addressResponseOpt = addressStorage.getAddressById(id);
   AddressResponse addressResponse = addressResponseOpt.orElseThrow(() -> new RuntimeException("Значение не найдено!"));
   return AddressResponseMapper.toDto(addressResponse);
  }


}
