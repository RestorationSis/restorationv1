package com.restorationservice.restorationv1.repository;

import com.restorationservice.restorationv1.model.claim.ClaimInsuranceAdjuster;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClaimInsuranceAdjusterRepository extends JpaRepository<ClaimInsuranceAdjuster, Long> {

  List<ClaimInsuranceAdjuster> findByClaim_ClaimId(Long claimId);
}
