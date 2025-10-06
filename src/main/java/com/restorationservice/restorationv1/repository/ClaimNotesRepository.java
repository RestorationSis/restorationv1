package com.restorationservice.restorationv1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restorationservice.restorationv1.model.claim.ClaimNotes;

@Repository
public interface ClaimNotesRepository extends JpaRepository<ClaimNotes, Long> {

  List<ClaimNotes> findByClaim_ClaimId(Long claimId);
}
