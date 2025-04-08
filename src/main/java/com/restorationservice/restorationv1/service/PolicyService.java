package com.restorationservice.restorationv1.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.restorationservice.restorationv1.model.dto.policy.PolicyDTO;
import com.restorationservice.restorationv1.model.policy.InsuranceCompany;
import com.restorationservice.restorationv1.model.policy.Policy;

public interface PolicyService {
  void addPolicy(PolicyDTO policy);

  boolean removePolicy(String policyId);

  PolicyDTO updatePolicy(PolicyDTO policy);

  PolicyDTO getPolicyById(String policyId);

  List<Policy> listAllPolicies();

  @Transactional
  InsuranceCompany addInsuranceCompany(InsuranceCompany company);

  boolean removeInsuranceCompany(Long companyId);

  @Transactional
  InsuranceCompany updateInsuranceCompany(InsuranceCompany company);

  InsuranceCompany getInsuranceCompanyById(Long companyId);

  List<InsuranceCompany> listAllInsuranceCompanies();
  List<PolicyDTO> getPoliciesByAddressId(Long addressId);
}
