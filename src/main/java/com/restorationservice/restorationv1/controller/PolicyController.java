package com.restorationservice.restorationv1.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restorationservice.restorationv1.model.dto.policy.PolicyDTO;
import com.restorationservice.restorationv1.model.policy.InsuranceCompany;
import com.restorationservice.restorationv1.model.policy.Policy;
import com.restorationservice.restorationv1.service.PolicyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/policy")
@RequiredArgsConstructor
public class PolicyController {

  private final PolicyService policyService;

  @PostMapping("/register")
  public ResponseEntity<PolicyDTO> register(@Valid @RequestBody PolicyDTO request) {
    policyService.addPolicy(request);
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
  public ResponseEntity<Policy> getPolicyById(@PathVariable String policyId) {
    Policy policy = policyService.getPolicyById(policyId);
    if (policy != null) {
      return ResponseEntity.ok(policy);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/all")
  public ResponseEntity<List<Policy>> listAllPolicies() {
    List<Policy> policies = policyService.listAllPolicies();
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
}
