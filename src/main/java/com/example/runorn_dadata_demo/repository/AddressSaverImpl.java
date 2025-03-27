package com.example.runorn_dadata_demo.repository;

import com.example.runorn_dadata_demo.model.AddressResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@Component
@Slf4j
public class AddressSaverImpl implements AddressSaver {

  private final Map<Integer, AddressResponse> addressMap = new HashMap<>();
  private final AtomicInteger idGenerator = new AtomicInteger();

  @Override
  public void saveAddress(List<AddressResponse> addresses) {
   AddressResponse address = addresses.get(0);
   int id = idGenerator.getAndIncrement();
   log.info("{} addresses saved", address);
   addressMap.put(id, address);
  }

  public AddressResponse getAddressById(int id) {
    return addressMap.get(id);
  }
}
