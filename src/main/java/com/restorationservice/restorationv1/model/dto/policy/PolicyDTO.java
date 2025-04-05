package com.restorationservice.restorationv1.model.dto.policy;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restorationservice.restorationv1.model.policy.Policy;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PolicyDTO {
  private long policyId;
  private long addressId;
  private long insuranceCompanyId;
  private long policyTypeId;
  private long coverageId;
  private String policyNumber;
  private String coverageLimit;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
  private LocalDate fromDate;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
  private LocalDate expirationDate;
}
