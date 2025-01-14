package com.restorationservice.restorationv1.model;

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

  public static Country fromCode(String code) {
    for (Country country : values()) {
      if (country.getCode().equalsIgnoreCase(code)) {
        return country;
      }
    }
    throw new IllegalArgumentException("No country found for code: " + code);
  }
}
