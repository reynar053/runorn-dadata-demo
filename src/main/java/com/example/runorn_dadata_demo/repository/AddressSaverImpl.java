package com.example.runorn_dadata_demo.repository;

import com.example.runorn_dadata_demo.model.AddressResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@Slf4j
public class AddressSaverImpl implements AddressSaver {
  private final Map<Integer, AddressResponse> addressMap = new HashMap<>();
  private int id = 0;


  @Override
  public void saveFirstAddress(List<AddressResponse> addresses) {
   AddressResponse address = addresses.get(0);
   id++;
   log.info("{} addresses saved", address);
   addressMap.put(id, address);
  }

@Override
  public AddressResponse getAddressById(int id) {
    return addressMap.get(id);
  }
}
