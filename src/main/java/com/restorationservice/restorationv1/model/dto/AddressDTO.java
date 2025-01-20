package com.restorationservice.restorationv1.model.dto;

import java.util.List;

import com.restorationservice.restorationv1.model.customer.Address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
  private long customerId;
  private Address address;
}
