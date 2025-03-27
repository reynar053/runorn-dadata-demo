package com.example.runorn_dadata_demo.controller;

import com.example.runorn_dadata_demo.repository.AddressSaverImpl;
import com.example.runorn_dadata_demo.service.DaDataService;
import com.example.runorn_dadata_demo.model.AddressRequestDto;
import com.example.runorn_dadata_demo.model.AddressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/v1/address")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AddressController {
  private final DaDataService daDataService;

  public final AddressSaverImpl addressSaver;



  @PostMapping("/clean")
  public AddressResponse cleanAddress(@RequestBody AddressRequestDto requestDto) {
    System.out.println(requestDto);
    return daDataService.cleanAddress(requestDto.getCity() + " " + requestDto.getStreet() + " " + requestDto.getApartment());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGeneralException(Exception ex) {
    return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @GetMapping
  public AddressResponse getAddressById(@RequestParam int id) {
    return addressSaver.getAddressById(id);
  }
}
