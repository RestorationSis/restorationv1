package com.restorationservice.restorationv1.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restorationservice.restorationv1.model.dto.policy.PolicyDTO;
import com.restorationservice.restorationv1.model.dto.policy.PolicyFullDTO;
import com.restorationservice.restorationv1.model.policy.Coverage;
import com.restorationservice.restorationv1.model.policy.InsuranceCompany;
import com.restorationservice.restorationv1.model.policy.Policy;
import com.restorationservice.restorationv1.model.policy.PolicyType;
import com.restorationservice.restorationv1.service.CoverageService;
import com.restorationservice.restorationv1.service.PolicyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/policy")
@RequiredArgsConstructor
public class PolicyController {

  private final PolicyService policyService;
  private final CoverageService coverageService;

  @PostMapping("/register")
  public ResponseEntity<PolicyDTO> register(@Valid @RequestBody PolicyDTO request) {
    long policyId = policyService.addPolicy(request);
    request.setPolicyId(policyId);
    return ResponseEntity.ok(request);
  }

  @DeleteMapping("/{policyId}")
  public ResponseEntity<Void> removePolicy(@PathVariable String policyId) {
    boolean isRemoved = policyService.removePolicy(policyId);
    if (isRemoved) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/update")
  public ResponseEntity<PolicyDTO> updatePolicy(@Valid @RequestBody PolicyDTO request) {
      PolicyDTO updatedPolicy = policyService.updatePolicy(request);
      return ResponseEntity.ok(updatedPolicy);
  }

  @GetMapping("/{policyId}")
  public ResponseEntity<PolicyFullDTO> getPolicyById(@PathVariable String policyId) {
    PolicyFullDTO policy = policyService.getPolicyById(policyId);
    if (policy != null) {
      return ResponseEntity.ok(policy);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/all")
  public ResponseEntity<List<PolicyFullDTO>> listAllPolicies() {
    List<PolicyFullDTO> policies = policyService.listAllPolicies();
    return ResponseEntity.ok(policies);
  }

  // insurance company
  @PostMapping("/insuranceCompany/register")
  public ResponseEntity<InsuranceCompany> registerInsuranceCompany(@Valid @RequestBody InsuranceCompany insuranceCompany) {
    InsuranceCompany savedCompany = policyService.addInsuranceCompany(insuranceCompany);
    return ResponseEntity.ok(savedCompany);
  }

  @DeleteMapping("/insuranceCompany/{companyId}")
  public ResponseEntity<Void> removeInsuranceCompany(@PathVariable Long companyId) {
    boolean isRemoved = policyService.removeInsuranceCompany(companyId);
    return isRemoved ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }

  @PutMapping("/insuranceCompany/update")
  public ResponseEntity<InsuranceCompany> updateInsuranceCompany(@Valid @RequestBody InsuranceCompany insuranceCompany) {
    InsuranceCompany updatedCompany = policyService.updateInsuranceCompany(insuranceCompany);
    return ResponseEntity.ok(updatedCompany);
  }

  @GetMapping("/insuranceCompany/{companyId}")
  public ResponseEntity<InsuranceCompany> getInsuranceCompanyById(@PathVariable Long companyId) {
    InsuranceCompany company = policyService.getInsuranceCompanyById(companyId);
    return company != null ? ResponseEntity.ok(company) : ResponseEntity.notFound().build();
  }

  @GetMapping("/insuranceCompany/all")
  public ResponseEntity<List<InsuranceCompany>> listAllInsuranceCompanies() {
    List<InsuranceCompany> companies = policyService.listAllInsuranceCompanies();
    return ResponseEntity.ok(companies);
  }
  @GetMapping("/address/{addressId}")
  public ResponseEntity<List<PolicyFullDTO>> getPoliciesByAddress(@PathVariable Long addressId) {
    List<PolicyFullDTO> policies = policyService.getPoliciesByAddressId(addressId);
    return ResponseEntity.ok(policies);
  }


  @PostMapping("/coverage/register")
  public ResponseEntity<Coverage> registerCoverage(@Valid @RequestBody Coverage request) {
    Coverage coverage = coverageService.addCoverage(request);
    return ResponseEntity.ok(coverage);
  }

  @DeleteMapping("/coverage/{coverageId}")
  public ResponseEntity<Void> removeCoverage(@PathVariable Long coverageId) {
    boolean isRemoved = coverageService.removeCoverage(coverageId);
    return isRemoved ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }

  @PutMapping("/coverage/update")
  public ResponseEntity<Coverage> updateCoverage(@Valid @RequestBody Coverage request) {
    Coverage updatedCoverage = coverageService.updateCoverage(request);
    return ResponseEntity.ok(updatedCoverage);
  }

  @GetMapping("/coverage/{coverageId}")
  public ResponseEntity<Coverage> getCoverageById(@PathVariable Long coverageId) {
    Coverage coverage = coverageService.getCoverageById(coverageId);
    return coverage != null ? ResponseEntity.ok(coverage) : ResponseEntity.notFound().build();
  }

  @GetMapping("/coverage/all")
  public ResponseEntity<List<Coverage>> listAllCoverages() {
    List<Coverage> coverages = coverageService.listAllCoverages();
    return ResponseEntity.ok(coverages);
  }

  @GetMapping("/policyType/all")
  public ResponseEntity<List<PolicyType>> listAllPolicyTypes() {
    List<PolicyType> policyTypes = policyService.listAllPolicyTypes();
    return ResponseEntity.ok(policyTypes);
  }
}
