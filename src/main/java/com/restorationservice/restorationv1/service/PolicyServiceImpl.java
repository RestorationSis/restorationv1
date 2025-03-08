package com.restorationservice.restorationv1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restorationservice.restorationv1.component.EntityChangeLogListener;
import com.restorationservice.restorationv1.model.customer.Address;
import com.restorationservice.restorationv1.model.dto.policy.PolicyDTO;
import com.restorationservice.restorationv1.model.policy.InsuranceCompany;
import com.restorationservice.restorationv1.model.policy.Policy;
import com.restorationservice.restorationv1.repository.AddressRepository;
import com.restorationservice.restorationv1.repository.InsuranceCompanyRepository;
import com.restorationservice.restorationv1.repository.PolicyRepository;

@Service
public class PolicyServiceImpl implements PolicyService {

  @Autowired
  private PolicyRepository policyRepository;
  @Autowired
  private InsuranceCompanyRepository insuranceCompanyRepository;
  @Autowired
  private AddressRepository addressRepository;
  @Autowired
  private EntityChangeLogListener listener;

  @Override
  @Transactional
  public void addPolicy(PolicyDTO policyDTO) {
    Optional<Address> address = addressRepository.findById(policyDTO.getAddressId());
    if (address.isPresent()) {
      if (address.get().getPolicyId() == null) {

        policyRepository.addPolicy(
            policyDTO.getAddressId(),
            policyDTO.getInsuranceCompanyId(),
            policyDTO.getPolicyNumber(),
            policyDTO.getPolicyTypeId(),
            policyDTO.getCoverageId(),
            policyDTO.getCoverageLimit(),
            policyDTO.getFromDate(),
            policyDTO.getExpirationDate()
        );
        Long policyId = policyRepository.getLastInsertId();
        address.get().setPolicyId(policyId);
        addressRepository.save(address.get());
        listener.onPrePersist(policyDTO);
      } else {
        throw new IllegalArgumentException("Address already has a policy");
      }
    } else {
      throw new IllegalArgumentException("Address with ID " + policyDTO.getAddressId() + " does not exist.");
    }
  }


  @Override
  public boolean removePolicy(String policyId) {
    try {
      Long id = Long.parseLong(policyId);
      if (policyRepository.existsById(id)) {
        policyRepository.deleteById(id);
        return true;
      } else {
        throw new IllegalArgumentException("no policy with id " + policyId);
      }
    } catch (NumberFormatException e) {
      // Handle invalid ID format
    }
    return false;
  }

  @Override
  @Transactional
  public PolicyDTO updatePolicy(PolicyDTO policy) {
    if (policyRepository.existsById(policy.getPolicyId())) {
      Optional<Address> address = addressRepository.findById(policy.getAddressId());
      if (address.isPresent()) {
        if (address.get().getPolicyId() != null) {
          policyRepository.updatePolicy(
              policy.getPolicyId(),
              policy.getAddressId(),
              policy.getInsuranceCompanyId(),
              policy.getPolicyNumber(),
              policy.getPolicyTypeId(),
              policy.getCoverageId(),
              policy.getCoverageLimit(),
              policy.getFromDate(),
              policy.getExpirationDate()
          );
          return policy;
        }
        throw new IllegalArgumentException("No policy found for the given address ID.");
      }
      throw new IllegalArgumentException("Address with ID " + policy.getAddressId() + " does not exist.");
    }
    throw new IllegalArgumentException("Address with ID " + policy.getAddressId() + " does not exist.");
  }

  @Override
  public Policy getPolicyById(String policyId) {
    try {
      Long id = Long.parseLong(policyId);
      return policyRepository.findById(id).orElse(null);
    } catch (NumberFormatException e) {
      // Handle invalid ID format
    }
    return null;
  }

  @Override
  public List<Policy> listAllPolicies() {
    return policyRepository.findAll();
  }

  //insurance company

  @Override
  @Transactional
  public InsuranceCompany addInsuranceCompany(InsuranceCompany company) {
    return insuranceCompanyRepository.save(company);
  }

  @Override
  public boolean removeInsuranceCompany(Long companyId) {
    if (insuranceCompanyRepository.existsById(companyId)) {
      insuranceCompanyRepository.deleteById(companyId);
      return true;
    }
    throw new IllegalArgumentException("Insurance Company with ID " + companyId.toString()+  " not exist.");
  }

  @Override
  @Transactional
  public InsuranceCompany updateInsuranceCompany(InsuranceCompany company) {
    if (insuranceCompanyRepository.existsById(company.getId())) {
      return insuranceCompanyRepository.save(company);
    }
    throw new IllegalArgumentException("Insurance Company ID does not exist.");
  }

  @Override
  public InsuranceCompany getInsuranceCompanyById(Long companyId) {
    return insuranceCompanyRepository.findById(companyId).orElse(null);
  }

  @Override
  public List<InsuranceCompany> listAllInsuranceCompanies() {
    return insuranceCompanyRepository.findAll();
  }
}

