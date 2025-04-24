package com.example.runorn_dadata_demo.repository;

import com.example.runorn_dadata_demo.model.Address;
import com.example.runorn_dadata_demo.model.AddressResponse;
import com.example.runorn_dadata_demo.model.User;
import com.example.runorn_dadata_demo.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Qualifier("addressDB")
public class AddressSaverDBImpl implements AddressSaver {
  private final AddressRepository addressRepository;
  private final UserService userService;

  public AddressSaverDBImpl(AddressRepository addressRepository, UserService userService) {
    this.addressRepository = addressRepository;
    this.userService = userService;
  }

  @Override
  public void saveFirstAddress(List<AddressResponse> addresses, String login) {
    User user = userService.createUser(login);
    AddressResponse firstAddress = addresses.get(0);
    Address address = new Address();
    address.setCountry(firstAddress.getCountry());
    address.setQc(firstAddress.getQc());
    address.setRegion(firstAddress.getRegion());
    address.setPostalCode(firstAddress.getPostalCode());
    address.setSource(firstAddress.getSource());
    address.setRegionType(firstAddress.getRegionType());
    address.setUser(user);
    address.setCreatedAt(LocalDateTime.now());
    addressRepository.save(address);
  }

  @Override
  public Optional<Address> getAddressById(Long id) {
    return addressRepository.findById(id);
  }
}
