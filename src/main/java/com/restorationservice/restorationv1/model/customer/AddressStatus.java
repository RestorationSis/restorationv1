package com.restorationservice.restorationv1.model.customer;

public enum AddressStatus {
  ACTIVE("ACTIVE"),
  INACTIVE("INACTIVE");
  private final String status;

  AddressStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }
}
