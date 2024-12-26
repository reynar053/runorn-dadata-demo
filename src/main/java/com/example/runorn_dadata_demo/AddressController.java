package com.example.runorn_dadata_demo;

import com.example.runorn_dadata_demo.model.AddressRequestDto;
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

  @PostMapping("/clean")
  public AddressResponse cleanAddress(@RequestBody AddressRequestDto requestDto) {
    System.out.println(requestDto);
    return daDataService.cleanAddress("СТРОКА-ШАБЛОН. ДОБАВИТЬ СТРОКУ ИЗ объекта");
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGeneralException(Exception ex) {
    return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
