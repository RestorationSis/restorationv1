 package com.restorationservice.restorationv1.model.dto.policy;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PolicyFullDTO {
  private long policyId;
  private long addressId;
  private String insuranceCompany;
  private String policyType;
  private List<String> coverageTypes;
  private String policyNumber;
  private String coverageLimit;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
  private LocalDate fromDate;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
  private LocalDate expirationDate;
  private String policyHolder;
  private String status;
}
