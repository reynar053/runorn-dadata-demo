package com.example.runorn_dadata_demo.model;

import lombok.Data;

@Data
public class AddressRequestDto {
  private String city;
  private String street;
  private String apartment;
  private String entrance;
  private String floor;
  private String intercom;
  private String comment;
  private String date;
  private String time;
}