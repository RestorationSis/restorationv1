package com.restorationservice.restorationv1.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContactDTO {

  private Long id;
  private String name;
  private String email;
  private String phone;
  private String contactTpe;
  private String relation;
  private Long customer;

  public ContactDTO(Long id, String name, String email, String phone, String contactTpe, String relation, Long customerId) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.phone = phone;
    this.contactTpe = contactTpe;
    this.relation = relation;
    this.customer = customerId;
  }
}
