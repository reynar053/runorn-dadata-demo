package com.example.runorn_dadata_demo;

import com.example.runorn_dadata_demo.model.AddressRequest;
import com.example.runorn_dadata_demo.model.AddressResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class DaDataService {
  private final WebClient webClient;

  @Value("${dadata.token}")
  private String token;
  @Value("${dadata.secret_token}")
  private String secretToken;

  public DaDataService(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.baseUrl("https://cleaner.dadata.ru/api/v1/clean/address").build();
  }

  public AddressResponse cleanAddress(String address) {
    AddressRequest request = AddressRequest.builder()
        .query("Москва пушкина 1")
        .build();

    request.setQuery(address);

    return webClient.post()
        .uri("")
        .header("Authorization", "Token " + token)
        .header("X-Secret",  "Token" + secretToken)
        .header("Accept", "application/json")
        .header("Content-Type", "application/json")
        .bodyValue(request)
        .retrieve()
        .bodyToMono(AddressResponse.class)
        .block();
  }
}
