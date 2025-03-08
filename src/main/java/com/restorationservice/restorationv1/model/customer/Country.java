package com.restorationservice.restorationv1.model.customer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = Country.CountryDeserializer.class)
public enum Country {
  UNITED_STATES("US", "United States"),
  CANADA("CA", "Canada"),
  UNITED_KINGDOM("GB", "United Kingdom"),
  GERMANY("DE", "Germany"),
  FRANCE("FR", "France"),
  ITALY("IT", "Italy"),
  SPAIN("ES", "Spain"),
  JAPAN("JP", "Japan"),
  CHINA("CN", "China"),
  INDIA("IN", "India"),
  BRAZIL("BR", "Brazil"),
  AUSTRALIA("AU", "Australia"),
  MEXICO("MX", "Mexico"),
  RUSSIA("RU", "Russia"),
  SOUTH_AFRICA("ZA", "South Africa");

  private final String code;
  private final String name;

  Country(String code, String name) {
    this.code = code;
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public static class CountryDeserializer extends JsonDeserializer<Country> {

    @Override
    public Country deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
        throws IOException, JsonProcessingException {

      String value = jsonParser.getText().toUpperCase().trim();

      for (Country country : Country.values()) {
        if (country.name().equals(value)) {
          return country;
        }
      }

      throw new IllegalArgumentException("Invalid Country value");
    }
  }
}
