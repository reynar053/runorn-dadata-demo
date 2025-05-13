package com.example.runorn_dadata_demo.repository;

import com.example.runorn_dadata_demo.model.entity.Address;
import com.example.runorn_dadata_demo.model.AddressDBMapper;
import com.example.runorn_dadata_demo.model.entity.User;
import com.example.runorn_dadata_demo.model.response.DaDataApiResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Qualifier("addressDB")
public class AddressSaverDBImpl implements AddressSaver {
  private final AddressRepository addressRepository;

  public AddressSaverDBImpl(AddressRepository addressRepository) {
    this.addressRepository = addressRepository;
  }

  @Override
  public void saveFirstAddress(Address address) {
      addressRepository.save(address);
  }

  @Override
  public Optional<Address> getAddressById(Long id) {
    return addressRepository.findById(id);
  }
}
