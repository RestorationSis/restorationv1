package com.restorationservice.restorationv1.model.customer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;

@Getter
@JsonDeserialize(using = State.StateDeserializer.class)
public enum State {
  ALABAMA("AL", "Alabama"),
  ALASKA("AK", "Alaska"),
  ARIZONA("AZ", "Arizona"),
  ARKANSAS("AR", "Arkansas"),
  CALIFORNIA("CA", "California"),
  COLORADO("CO", "Colorado"),
  CONNECTICUT("CT", "Connecticut"),
  DELAWARE("DE", "Delaware"),
  FLORIDA("FL", "Florida"),
  GEORGIA("GA", "Georgia"),
  HAWAII("HI", "Hawaii"),
  IDAHO("ID", "Idaho"),
  ILLINOIS("IL", "Illinois"),
  INDIANA("IN", "Indiana"),
  IOWA("IA", "Iowa"),
  KANSAS("KS", "Kansas"),
  KENTUCKY("KY", "Kentucky"),
  LOUISIANA("LA", "Louisiana"),
  MAINE("ME", "Maine"),
  MARYLAND("MD", "Maryland"),
  MASSACHUSETTS("MA", "Massachusetts"),
  MICHIGAN("MI", "Michigan"),
  MINNESOTA("MN", "Minnesota"),
  MISSISSIPPI("MS", "Mississippi"),
  MISSOURI("MO", "Missouri"),
  MONTANA("MT", "Montana"),
  NEBRASKA("NE", "Nebraska"),
  NEVADA("NV", "Nevada"),
  NEW_HAMPSHIRE("NH", "New Hampshire"),
  NEW_JERSEY("NJ", "New Jersey"),
  NEW_MEXICO("NM", "New Mexico"),
  NEW_YORK("NY", "New York"),
  NORTH_CAROLINA("NC", "North Carolina"),
  NORTH_DAKOTA("ND", "North Dakota"),
  OHIO("OH", "Ohio"),
  OKLAHOMA("OK", "Oklahoma"),
  OREGON("OR", "Oregon"),
  PENNSYLVANIA("PA", "Pennsylvania"),
  RHODE_ISLAND("RI", "Rhode Island"),
  SOUTH_CAROLINA("SC", "South Carolina"),
  SOUTH_DAKOTA("SD", "South Dakota"),
  TENNESSEE("TN", "Tennessee"),
  TEXAS("TX", "Texas"),
  UTAH("UT", "Utah"),
  VERMONT("VT", "Vermont"),
  VIRGINIA("VA", "Virginia"),
  WASHINGTON("WA", "Washington"),
  WEST_VIRGINIA("WV", "West Virginia"),
  WISCONSIN("WI", "Wisconsin"),
  WYOMING("WY", "Wyoming");

  private final String code;
  private final String name;

  State(String code, String name) {
    this.code = code;
    this.name = name;
  }

  public static class StateDeserializer extends JsonDeserializer<State> {

    @Override
    public State deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
        throws IOException, JsonProcessingException {

      String value = jsonParser.getText().toUpperCase().trim();

      for (State state : State.values()) {
        if (state.name().equals(value)) {
          return state;
        }
      }

      throw new IllegalArgumentException("Invalid State value");
    }
  }
}
