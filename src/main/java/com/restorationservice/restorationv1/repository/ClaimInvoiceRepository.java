package com.restorationservice.restorationv1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restorationservice.restorationv1.model.claim.ClaimInvoice;

@Repository
public interface ClaimInvoiceRepository extends JpaRepository<ClaimInvoice, Long> {

  List<ClaimInvoice> findByClaim_ClaimId(Long claimId);
}
