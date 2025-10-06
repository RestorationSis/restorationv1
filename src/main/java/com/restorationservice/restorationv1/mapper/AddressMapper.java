package com.restorationservice.restorationv1.mapper;

import org.springframework.stereotype.Component;

import com.restorationservice.restorationv1.model.customer.Address;

import com.restorationservice.restorationv1.model.dto.AddressDTOnoPolicy;
import com.restorationservice.restorationv1.model.policy.Policy;
@Component
public class AddressMapper {

  public AddressDTOnoPolicy toDTO(Address address) {
    if (address == null) {
      return null;
    }

    return AddressDTOnoPolicy.builder()
        .id(address.getId())
        .streetAddress(address.getStreetAddress())
        .unitApartmentSuite(address.getUnitApartmentSuite())
        .city(address.getCity())
        .state(address.getState())
        .country(address.getCountry())
        .zipCode(address.getZipCode())
        .isPrimary(address.getIsPrimary())
        .createdBy(address.getCreatedBy())
        .policyId(address.getPolicy() != null ? address.getPolicy().getId() : 0)
        .status(address.getStatus())
        .build();
  }
}