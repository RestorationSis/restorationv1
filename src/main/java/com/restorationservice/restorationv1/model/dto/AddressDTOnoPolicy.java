package com.restorationservice.restorationv1.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.restorationservice.restorationv1.json.Views;
import com.restorationservice.restorationv1.model.customer.AddressStatus;
import com.restorationservice.restorationv1.model.customer.Country;
import com.restorationservice.restorationv1.model.customer.State;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTOnoPolicy {

  @JsonView(Views.Summary.class)
  private long id;

  @NotNull(message = "streetAddress field must not be null")
  @JsonView(Views.Summary.class)
  private String streetAddress;

  @NotNull(message = "unitApartmentSuite field must not be null")
  @JsonView(Views.Summary.class)
  private String unitApartmentSuite;

  @NotNull(message = "city field must not be null")
  @JsonView(Views.Summary.class)
  private String city;

  @JsonView(Views.Summary.class)
  private State state;

  @JsonView(Views.Summary.class)
  private Country country;

  @NotNull(message = "zipCode field must not be null")
  @JsonView(Views.Summary.class)
  private String zipCode;

  @JsonView(Views.Summary.class)
  private Boolean isPrimary;

  @JsonView(Views.Summary.class)
  private String createdBy;

  @JsonView(Views.Summary.class)
  private long policyId;

  @JsonView(Views.Summary.class)
  private AddressStatus status;
}