package com.restorationservice.restorationv1.model.customer.attachment;

public enum AttachmentType {
  ID("Identification"),
  HOUSE_DOCUMENTS("House Documents"),
  INSURANCE_POLICY("Insurance Policy"),
  CONTRACT("Contract"),
  INVOICE("Invoice"),
  RECEIPT("Receipt"),
  BANK_STATEMENT("Bank Statement"),
  MEDICAL_RECORD("Medical Record");

  private final String description;

  AttachmentType(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}

