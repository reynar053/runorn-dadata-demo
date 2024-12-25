package com.example.runorn_dadata_demo;

import com.example.runorn_dadata_demo.model.AddressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressController {
  private final DaDataService daDataService;

  @GetMapping("/clean")
  public AddressResponse cleanAddress(@RequestParam String address) {
    return daDataService.cleanAddress(address);
  }

  // Обработчик ошибок для конкретных исключений
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  // Обработчик ошибок для всех исключений
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGeneralException(Exception ex) {
    return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
