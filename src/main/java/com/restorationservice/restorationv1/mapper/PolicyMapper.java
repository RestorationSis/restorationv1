package com.restorationservice.restorationv1.mapper;

import com.restorationservice.restorationv1.model.dto.policy.PolicyDTO;
import com.restorationservice.restorationv1.model.dto.policy.PolicyFullDTO;
import com.restorationservice.restorationv1.model.policy.Coverage;
import com.restorationservice.restorationv1.model.policy.Policy;
import com.restorationservice.restorationv1.model.policy.PolicyHolder;

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

    List<String> policyHolders = null;
    if (policy.getPolicyHolders() != null) {
      policyHolders = policy.getPolicyHolders().stream()
          .map(holder -> holder.getHolderName())
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
        .status(policy.getStatus().toString())
        .policyHolders(policyHolders)
        .build();
  }

  public static PolicyFullDTO toFullDTO(Policy policy) {
    if (policy == null) {
      return null;
    }

    List<String> coverageTypes = null;
    List<Long> coverageTypeIds = null;
    if (policy.getCoverages() != null) {
      coverageTypes = policy.getCoverages().stream()
          .map(Coverage::getCoverageType)
          .collect(Collectors.toList());
      coverageTypeIds = policy.getCoverages().stream()
          .map(Coverage::getId)
          .collect(Collectors.toList());
    }

    List<String> policyHolders = null;
    List<Long> policyHolderIds = null;
    if (policy.getPolicyHolders() != null) {
      policyHolders = policy.getPolicyHolders().stream()
          .map(PolicyHolder::getHolderName)
          .collect(Collectors.toList());
      policyHolderIds = policy.getPolicyHolders().stream()
          .map(PolicyHolder::getId)
          .collect(Collectors.toList());
    }

    return PolicyFullDTO.builder()
        .policyId(policy.getId())
        .insuranceCompanyId(policy.getInsuranceCompany().getId())
        .insuranceCompany(policy.getInsuranceCompany() != null ? policy.getInsuranceCompany().getCommercialName() : null)
        .policyTypeId(policy.getPolicyType().getId())
        .policyType(policy.getPolicyType() != null ? policy.getPolicyType().getCodType() : null)
        .coverageTypeIds(coverageTypeIds)
        .coverageTypes(coverageTypes)
        .policyNumber(policy.getPolicyNumber())
        .coverageLimit(policy.getCoverageLimit())
        .fromDate(policy.getFromDate())
        .expirationDate(policy.getExpirationDate())
        .status(policy.getStatus().name())
        .policyHolderIds(policyHolderIds)
        .policyHolders(policyHolders)
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