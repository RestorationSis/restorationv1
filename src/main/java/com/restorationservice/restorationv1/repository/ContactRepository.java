package com.restorationservice.restorationv1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restorationservice.restorationv1.model.policy.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
  List<Contact> findByCustomerId(Long customerId);
}
