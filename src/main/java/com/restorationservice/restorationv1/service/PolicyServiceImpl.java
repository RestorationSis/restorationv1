package com.restorationservice.restorationv1.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restorationservice.restorationv1.component.EntityChangeLogListener;
import com.restorationservice.restorationv1.mapper.PolicyMapper;
import com.restorationservice.restorationv1.model.customer.Address;
import com.restorationservice.restorationv1.model.dto.policy.PolicyDTO;
import com.restorationservice.restorationv1.model.dto.policy.PolicyFullDTO;
import com.restorationservice.restorationv1.model.policy.Coverage;
import com.restorationservice.restorationv1.model.policy.InsuranceCompany;
import com.restorationservice.restorationv1.model.policy.Policy;
import com.restorationservice.restorationv1.model.policy.PolicyType;
import com.restorationservice.restorationv1.model.policy.Status;
import com.restorationservice.restorationv1.repository.AddressRepository;
import com.restorationservice.restorationv1.repository.InsuranceCompanyRepository;
import com.restorationservice.restorationv1.repository.PolicyCoverageNativeRepository;
import com.restorationservice.restorationv1.repository.PolicyHolderNativeRepository;
import com.restorationservice.restorationv1.repository.PolicyRepository;
import com.restorationservice.restorationv1.repository.PolicyTypeRepository;

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
  @Autowired
  private PolicyHolderNativeRepository policyHolderNativeRepository;

  @Autowired
  private PolicyTypeRepository policyTypeRepository;

  @Override
  @Transactional
  public long addPolicy(PolicyDTO policyDTO) {
    Optional<Address> address = addressRepository.findById(policyDTO.getAddressId());
    if (address.isPresent()) {
    List<Policy> policies = policyRepository.findPoliciesByAddressId(address.get().getId());
      validateExpirationDate(policyDTO.getExpirationDate());
      validateHasPolicyActive(policies, policyDTO.getStatus());

      policyRepository.addPolicy(
          policyDTO.getAddressId(),
          policyDTO.getInsuranceCompanyId(),
          policyDTO.getPolicyNumber(),
          policyDTO.getPolicyTypeId(),
          policyDTO.getCoverageLimit(),
          policyDTO.getFromDate(),
          policyDTO.getExpirationDate()
      );
      Long policyId = policyRepository.getLastInsertId();
      addressRepository.addPolicyId(policyId, address.get().getId());
      listener.onPrePersist(policyDTO);
      addCoverageToPolicy(policyId, policyDTO.getCoverageIds(), true);
      addPolicyHoldersToPolicy(policyId, policyDTO.getPolicyHolders());

      return policyId;
    }
    else {
      throw new IllegalArgumentException("Address with ID " + policyDTO.getAddressId() + " does not exist.");
    }
  }

  private void addPolicyHoldersToPolicy(long policyId, List<String> policyHolders) {
    policyHolders.forEach(name -> policyHolderNativeRepository.addPolicyHolder(policyId, name));
  }

  private void updatePolicyHoldersToPolicy(long policyId, List<String> policyHolders) {
    policyHolderNativeRepository.removeAllPolicyHolders(policyId);
    policyHolders.forEach(name -> policyHolderNativeRepository.addPolicyHolder(policyId, name));
  }


  private void addCoverageToPolicy(long policyId, List<Long> coverageIds, boolean newPolicy) {
    if(!newPolicy) {
      Policy policy = policyRepository.getReferenceById(policyId);

      List<Long> existingCoverageIds = policy.getCoverages().stream()
          .map(Coverage::getId)
          .toList();

      List<Long> coverageIdsToAdd = coverageIds.stream()
          .filter(coverageId -> !existingCoverageIds.contains(coverageId))
          .toList();

      List<Long> coverageIdsToRemove = existingCoverageIds.stream()
          .filter(existingId -> !coverageIds.contains(existingId))
          .toList();

      coverageIdsToAdd.forEach(coverageId -> policyCoverageNativeRepository.addPolicyCoverage(policyId, coverageId));
      coverageIdsToRemove.forEach(coverageId -> policyCoverageNativeRepository.removePolicyCoverage(policyId, coverageId));
    }
    else {
      coverageIds.forEach(coverageId -> policyCoverageNativeRepository.addPolicyCoverage(policyId, coverageId));
    }
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
      if(policyDTO.getStatus().equals(Status.ACTIVE.name())) {
        List<Policy> policies = policyRepository.findPoliciesByAddressId(policyDTO.getAddressId());

        if (policies.stream()
            .anyMatch(p -> p.getStatus() == Status.ACTIVE && !p.getId().equals(policyDTO.getPolicyId()))) {
          throw new IllegalArgumentException(
              "There is already an active policy for the address: " + policyDTO.getAddressId());
        }
      }
        validateExpirationDate(policyDTO.getExpirationDate());

          policyRepository.updatePolicy(
              policyDTO.getPolicyId(),
              policyDTO.getInsuranceCompanyId(),
              policyDTO.getPolicyNumber(),
              policyDTO.getPolicyTypeId(),
              policyDTO.getCoverageLimit(),
              policyDTO.getFromDate(),
              policyDTO.getExpirationDate(),
              policyDTO.getStatus());

          addCoverageToPolicy(policyDTO.getPolicyId(), policyDTO.getCoverageIds(), false);
          updatePolicyHoldersToPolicy(policyDTO.getPolicyId(), policyDTO.getPolicyHolders());

          return policyDTO;

      } else {
        throw new IllegalArgumentException("Address with ID " + policyDTO.getAddressId() + " does not exist.");
      }
  }

  @Override
  public PolicyFullDTO getPolicyById(String policyId) {
    try {
      Long id = Long.parseLong(policyId);

      return PolicyMapper.toFullDTO(policyRepository.findById(id).orElse(null));
    } catch (NumberFormatException ignored) {
    }
    return null;
  }

  @Override
  public List<PolicyFullDTO> listAllPolicies() {
    return policyRepository.findAll()
        .stream()
        .map(PolicyMapper::toFullDTO)
        .collect(Collectors.toList());
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
  public List<PolicyFullDTO> getPoliciesByAddressId(Long addressId) {
    List<Policy> policies = policyRepository.findPoliciesByAddressId(addressId);
    List<PolicyFullDTO> policyDTOList = new ArrayList<>();

    policies.forEach(policy -> {
      policyDTOList.add(PolicyMapper.toFullDTO(policy));
    });

    return policyDTOList;
  }

  @Override
  public List<PolicyType> listAllPolicyTypes() {
   return policyTypeRepository.findAll();
  }

  private void validateHasPolicyActive(List<Policy> policies, String requestStatus) {
    if(requestStatus.equalsIgnoreCase("ACTIVE")) {
      for (Policy policy : policies) {
        if (policy.getStatus().equals(Status.ACTIVE)) {
          throw new IllegalArgumentException("There is already an Active policy");
        }
      }
    }
  }
}

