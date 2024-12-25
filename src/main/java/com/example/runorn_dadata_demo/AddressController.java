package com.example.runorn_dadata_demo;

import com.example.runorn_dadata_demo.model.AddressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressController {
  private final DaDataService daDataService;

  @PostMapping("/clean")
  public AddressResponse cleanAddress(@RequestBody String address) {
    return daDataService.cleanAddress(address);
  }
}
