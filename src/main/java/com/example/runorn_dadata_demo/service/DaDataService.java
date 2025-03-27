package com.example.runorn_dadata_demo.service;

import com.example.runorn_dadata_demo.http.DaDataClient;
import com.example.runorn_dadata_demo.model.AddressResponse;
import com.example.runorn_dadata_demo.repository.AddressSaver;
import com.example.runorn_dadata_demo.repository.AddressSaverImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
    log.info("{} PIVO",addressResponseList.toString());
    if (addressResponseList == null || addressResponseList.isEmpty()) {
      throw new NullPointerException("Список пустой :(");
    }
    addressStorage.saveAddress(addressResponseList);
    return addressResponseList.get(0);
  }




}
