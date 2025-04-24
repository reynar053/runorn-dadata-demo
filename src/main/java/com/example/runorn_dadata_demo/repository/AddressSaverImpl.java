package com.example.runorn_dadata_demo.repository;

import com.example.runorn_dadata_demo.model.AddressResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;


@Component
@Slf4j
public class AddressSaverImpl implements AddressSaver {
  private Map<Long, AddressResponse> addressMap = new HashMap<>();
  private AtomicInteger idGenerator = new AtomicInteger(1);


  @Override
  public void saveFirstAddress(List<AddressResponse> addresses, String login) {
    AddressResponse address = addresses.get(0);
    Long id = (long) idGenerator.getAndIncrement();
    log.info("{} addresses saved", address);
    addressMap.put(id, address);
  }

  @Override
  public Optional<AddressResponse> getAddressById(Long id) {
    return Optional.ofNullable(addressMap.get(id));
  }
}