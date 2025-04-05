package com.restorationservice.restorationv1.mapper;

import com.restorationservice.restorationv1.model.dto.policy.PolicyDTO;
import com.restorationservice.restorationv1.model.policy.Policy;

public class PolicyMapper {
  public static PolicyDTO toDTO(Policy policy) {
    if (policy == null) {
      return null;
    }

    return PolicyDTO.builder()
        .policyId(policy.getId())
        .addressId(policy.getAddress() != null ? policy.getAddress().getId() : null)
        .insuranceCompanyId(policy.getInsuranceCompany() != null ? policy.getInsuranceCompany().getId() : null)
        .policyTypeId(policy.getPolicyType() != null ? policy.getPolicyType().getId() : null)
        .coverageId(policy.getCoverage() != null ? policy.getCoverage().getId() : null)
        .policyNumber(policy.getPolicyNumber())
        .coverageLimit(policy.getCoverageLimit())
        .fromDate(policy.getFromDate())
        .expirationDate(policy.getExpirationDate())
        .build();
  }

  public static Policy toEntity(PolicyDTO policyDTO) {
    if (policyDTO == null) {
      return null;
    }

    Policy policy = new Policy();
    policy.setId(policyDTO.getPolicyId());
    policy.setPolicyNumber(policyDTO.getPolicyNumber());
    policy.setCoverageLimit(policyDTO.getCoverageLimit());
    policy.setFromDate(policyDTO.getFromDate());
    policy.setExpirationDate(policyDTO.getExpirationDate());

    return policy;
  }
}
