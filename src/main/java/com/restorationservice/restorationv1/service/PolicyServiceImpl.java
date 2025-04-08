package com.restorationservice.restorationv1.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restorationservice.restorationv1.component.EntityChangeLogListener;
import com.restorationservice.restorationv1.mapper.PolicyMapper;
import com.restorationservice.restorationv1.model.customer.Address;
import com.restorationservice.restorationv1.model.dto.policy.PolicyDTO;
import com.restorationservice.restorationv1.model.policy.Coverage;
import com.restorationservice.restorationv1.model.policy.InsuranceCompany;
import com.restorationservice.restorationv1.model.policy.Policy;
import com.restorationservice.restorationv1.model.policy.Status;
import com.restorationservice.restorationv1.repository.AddressRepository;
import com.restorationservice.restorationv1.repository.InsuranceCompanyRepository;
import com.restorationservice.restorationv1.repository.PolicyCoverageNativeRepository;
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
  @Autowired
  private PolicyCoverageNativeRepository policyCoverageNativeRepository;

  @Override
  @Transactional
  public void addPolicy(PolicyDTO policyDTO) {
    Optional<Address> address = addressRepository.findById(policyDTO.getAddressId());
    if (address.isPresent()) {
      validateExpirationDate(policyDTO.getExpirationDate());
      if (address.get().getPolicyId() == null) {

          policyRepository.addPolicy(
            policyDTO.getAddressId(),
            policyDTO.getInsuranceCompanyId(),
            policyDTO.getPolicyNumber(),
            policyDTO.getPolicyTypeId(),
            policyDTO.getCoverageLimit(),
            policyDTO.getFromDate(),
            policyDTO.getExpirationDate(),
              policyDTO.getPolicyHolder()
        );
        Long policyId = policyRepository.getLastInsertId();
        address.get().setPolicyId(policyId);
        addressRepository.save(address.get());
        listener.onPrePersist(policyDTO);
        addCoverageToPolicy(policyId, policyDTO.getCoverageIds(), true);
      } else {
        throw new IllegalArgumentException("Address already has a policy");
      }
    } else {
      throw new IllegalArgumentException("Address with ID " + policyDTO.getAddressId() + " does not exist.");
    }
  }

  private void addCoverageToPolicy(long policyId, List<Long> coverageIds, boolean newPolicy) {
    Policy policy = policyRepository.getReferenceById(policyId);

    List<Long> existingCoverageIds = policy.getCoverages().stream()
        .map(Coverage::getId)
        .toList();

    List<Long> coverageIdsToAdd = coverageIds.stream()
        .filter(coverageId -> !existingCoverageIds.contains(coverageId))
        .toList();

    coverageIdsToAdd.forEach(coverageId -> policyCoverageNativeRepository.addPolicyCoverage(policyId, coverageId));
  }

  private void validateExpirationDate(LocalDate expirationDate){
    if(expirationDate.isBefore(LocalDate.now())){
    throw new IllegalArgumentException("Incorrect expiration date");
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
  public PolicyDTO updatePolicy(PolicyDTO policyDTO) {
    if (policyRepository.existsById(policyDTO.getPolicyId())) {
      Optional<Address> address = addressRepository.findById(policyDTO.getAddressId());
      if (address.isPresent()) {
        validateExpirationDate(policyDTO.getExpirationDate());
        if (address.get().getPolicyId() == null || address.get().getPolicyId().equals(policyDTO.getPolicyId())) {

          // Update the policy using the updatePolicy method
          policyRepository.updatePolicy(
              policyDTO.getPolicyId(), // Include the policyId for the WHERE clause
              policyDTO.getAddressId(),
              policyDTO.getInsuranceCompanyId(),
              policyDTO.getPolicyNumber(),
              policyDTO.getPolicyTypeId(),
              policyDTO.getCoverageLimit(),
              policyDTO.getFromDate(),
              policyDTO.getExpirationDate(),
              policyDTO.getPolicyHolder()
          );

          // Update the address's policyId if it's not already set or matches the current policy
          if (address.get().getPolicyId() == null) {
            address.get().setPolicyId(policyDTO.getPolicyId());
            addressRepository.save(address.get());
          }

          // Update coverages for the policy
          // Assuming addCoverageToPolicy handles updates based on newPolicy flag
          addCoverageToPolicy(policyDTO.getPolicyId(), policyDTO.getCoverageIds(), false); // Set newPolicy to false for updates


          return policyDTO;

        } else {
          throw new IllegalArgumentException("Address already has a policy associated with a different policy ID.");
        }
      } else {
        throw new IllegalArgumentException("Address with ID " + policyDTO.getAddressId() + " does not exist.");
      }
    } else {
      throw new IllegalArgumentException("Policy with ID " + policyDTO.getPolicyId() + " does not exist.");
    }
  }

  @Override
  public PolicyDTO getPolicyById(String policyId) {
    try {
      Long id = Long.parseLong(policyId);

      return PolicyMapper.toDTO(policyRepository.findById(id).orElse(null));
    } catch (NumberFormatException ignored) {
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

  @Scheduled(cron = "0 0 0 * * ?")  // Cron expression  12 AM everyday
  @Transactional
  public void updateExpiredPoliciesStatus() {
    LocalDate today = LocalDate.now();
    List<Policy> policies = policyRepository.findAllByExpirationDateBeforeAndStatusNot(today, Status.INACTIVE);

    for (Policy policy : policies) {
      policy.setStatus(Status.INACTIVE);
      policyRepository.save(policy);
    }
  }
  public List<PolicyDTO> getPoliciesByAddressId(Long addressId) {
    List<Policy> policies = policyRepository.findPoliciesByAddressId(addressId);
    List<PolicyDTO> policyDTOList = new ArrayList<>();

    policies.forEach(policy -> {
      policyDTOList.add(PolicyMapper.toDTO(policy));
    });

    return policyDTOList;
  }
}

