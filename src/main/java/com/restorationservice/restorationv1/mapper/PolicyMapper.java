package com.restorationservice.restorationv1.mapper;

import com.restorationservice.restorationv1.model.dto.policy.PolicyDTO;
import com.restorationservice.restorationv1.model.dto.policy.PolicyFullDTO;
import com.restorationservice.restorationv1.model.policy.Policy;
import java.util.List;
import java.util.stream.Collectors;

public class PolicyMapper {
  public static PolicyDTO toDTO(Policy policy) {
    if (policy == null) {
      return null;
    }

    List<Long> coverageIds = null;
    if (policy.getCoverages() != null) {
      coverageIds = policy.getCoverages().stream()
          .map(coverage -> coverage.getId()) // Asumiendo que tu entidad Coverage tiene un método getId()
          .collect(Collectors.toList());
    }

    return PolicyDTO.builder()
        .policyId(policy.getId())
        .addressId(policy.getAddress() != null ? policy.getAddress().getId() : null)
        .insuranceCompanyId(policy.getInsuranceCompany() != null ? policy.getInsuranceCompany().getId() : null)
        .policyTypeId(policy.getPolicyType() != null ? policy.getPolicyType().getId() : null)
        .coverageIds(coverageIds)
        .policyNumber(policy.getPolicyNumber())
        .coverageLimit(policy.getCoverageLimit())
        .fromDate(policy.getFromDate())
        .expirationDate(policy.getExpirationDate())
        .build();
  }

  public static PolicyFullDTO toFullDTO(Policy policy) {
    if (policy == null) {
      return null;
    }

    List<String> coverageTypes = null;
    if (policy.getCoverages() != null) {
      coverageTypes = policy.getCoverages().stream()
          .map(coverage -> coverage.getCoverageType())
          .collect(Collectors.toList());
    }

    return PolicyFullDTO.builder()
        .policyId(policy.getId())
        .addressId(policy.getAddress() != null ? policy.getAddress().getId() : null)
        .insuranceCompany(policy.getInsuranceCompany() != null ? policy.getInsuranceCompany().getCommercialName() : null)
        .policyType(policy.getPolicyType() != null ? policy.getPolicyType().getCodType() : null)
        .coverageTypes(coverageTypes)
        .policyNumber(policy.getPolicyNumber())
        .coverageLimit(policy.getCoverageLimit())
        .fromDate(policy.getFromDate())
        .expirationDate(policy.getExpirationDate())
        .status(policy.getStatus().name())
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

    // Aquí necesitarás lógica adicional para manejar la lista de coverageIds
    // y cómo se relacionan con las entidades Coverage. Esto dependerá
    // de cómo estés manejando la persistencia de las relaciones ManyToMany.
    // Por ejemplo, podrías necesitar buscar las entidades Coverage por sus IDs
    // y asignarlas a la colección 'coverages' de la entidad Policy.

    return policy;
  }
}