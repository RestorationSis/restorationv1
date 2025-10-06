package com.restorationservice.restorationv1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.restorationservice.restorationv1.model.claim.RepresentativeType;

public interface RepresentativeCatalogRepository extends JpaRepository<RepresentativeType, Long> {

}
