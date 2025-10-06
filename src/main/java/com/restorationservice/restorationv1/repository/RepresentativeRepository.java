package com.restorationservice.restorationv1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restorationservice.restorationv1.model.claim.Representative;

public interface RepresentativeRepository extends JpaRepository<Representative, Long> {

}
