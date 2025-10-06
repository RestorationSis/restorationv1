package com.restorationservice.restorationv1.model.dto.claim;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClaimInsuranceAdjusterDTO {
  private Long insuranceAdjusterId;
  private Long claimId;
  private String fullName;
  private String email;
  private String phoneNumber;
  private String extension;
}
