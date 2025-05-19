package com.restorationservice.restorationv1.model.dto;

import com.restorationservice.restorationv1.model.customer.Language;
import lombok.Data;

@Data
public class CustomerDTO {

  private long id;
  private String firstName;
  private String middleName;
  private String lastName;
  private String email;
  private String cellphone;
  private Language prefferedLanguage;
}
