package com.restorationservice.restorationv1.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.restorationservice.restorationv1.model.policy.Policy;
@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
  @Modifying
  @Query(value = "INSERT INTO policies (address_fk, insurance_company_fk, policy_number, policy_type_fk, coverage_fk, coverage_limit, from_date, expiration_date) " +
      "VALUES (:addressId, :insuranceCompanyId, :policyNumber, :policyTypeId, :coverageId, :coverageLimit, :fromDate, :expirationDate)",
      nativeQuery = true)
  void addPolicy(
      @Param("addressId") long addressId,
      @Param("insuranceCompanyId") long insuranceCompanyId,
      @Param("policyNumber") String policyNumber,
      @Param("policyTypeId") long policyTypeId,
      @Param("coverageId") long coverageId,
      @Param("coverageLimit") String coverageLimit,
      @Param("fromDate") LocalDate fromDate,
      @Param("expirationDate") LocalDate expirationDate);


  @Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
  Long getLastInsertId();

  @Modifying
  @Query(value = "UPDATE policies SET insurance_company_fk = :insuranceCompanyId, policy_number = :policyNumber, " +
      "policy_type_fk = :policyTypeId, coverage_fk = :coverageId, coverage_limit = :coverageLimit, " +
      "from_date = :fromDate, expiration_date = :expirationDate, address_fk =:addressId WHERE Id_policy = :policyId",
      nativeQuery = true)
  void updatePolicy(
      @Param("policyId") long policyId,
      @Param("addressId") long addressId,
      @Param("insuranceCompanyId") long insuranceCompanyId,
      @Param("policyNumber") String policyNumber,
      @Param("policyTypeId") long policyTypeId,
      @Param("coverageId") long coverageId,
      @Param("coverageLimit") String coverageLimit,
      @Param("fromDate") LocalDate fromDate,
      @Param("expirationDate") LocalDate expirationDate);

}

