package com.example.runorn_dadata_demo.repository;

import com.example.runorn_dadata_demo.model.AddressResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
@Slf4j
public class AddressSaverImpl implements AddressSaver {

  private final List<AddressResponse> addressesList = new ArrayList<>();

  @Override
  public void saveAddress(List<AddressResponse> addresses) {
   addressesList.add(addresses.get(0));
    log.info("{} addresses saved", addressesList);
  }
}
