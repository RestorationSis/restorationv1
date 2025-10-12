package com.restorationservice.restorationv1.repository;

import com.restorationservice.restorationv1.model.claim.DamageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DamageTypeRepository extends JpaRepository<DamageType, Long> {
}
