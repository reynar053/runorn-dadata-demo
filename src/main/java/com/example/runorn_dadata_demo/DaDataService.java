package com.example.runorn_dadata_demo;

import com.example.runorn_dadata_demo.model.AddressRequest;
import com.example.runorn_dadata_demo.model.AddressResponse;
import io.netty.handler.logging.LogLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class DaDataService {
  private final WebClient webClient;

  @Value("${dadata.token}")
  private String token;
  @Value("${dadata.secret_token}")
  private String secretToken;

  public DaDataService(WebClient.Builder webClientBuilder) {
    HttpClient httpClient = HttpClient.create()
        .wiretap(this.getClass().getCanonicalName(), LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);
    ClientHttpConnector conn = new ReactorClientHttpConnector(httpClient);

    String postalUri = "http://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/postal_unit";
    String cleanerUri = "https://cleaner.dadata.ru/api/v1/clean/address";

    this.webClient = webClientBuilder
        .clientConnector(conn)
        .filter(logRequest())
        .defaultHeader("Content-Type", "application/json")
        .defaultHeader("Accept", "application/json")
        .baseUrl(cleanerUri)
        .build();
  }

  public AddressResponse cleanAddress(String address) {
    return webClient.post()
        .uri("")
        .header("Authorization", "Token " + token)
        .header("X-Secret",  secretToken)
        .acceptCharset(StandardCharsets.UTF_8)
        .body(BodyInserters.fromValue(List.of(address)))
        .retrieve()
        .bodyToFlux(AddressResponse.class)
        .collectList()
        .block()
        .get(0);
  }

  private ExchangeFilterFunction logRequest() {
    return (clientRequest, next) -> {
      log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
      clientRequest.headers()
          .forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));
      log.info("Body is {}", clientRequest.body());
      return next.exchange(clientRequest);
    };
  }
}
