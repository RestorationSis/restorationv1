package com.restorationservice.restorationv1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.restorationservice.restorationv1.model.customer.Customer;
import com.restorationservice.restorationv1.model.customer.CustomerStatus;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
  @Query("SELECT c FROM Customer c WHERE " +
      "(:status IS NULL OR c.status = :status) AND " +
      "(:createdBy IS NULL OR c.createdBy = :createdBy)")
  List<Customer> findCustomersByStatusAndCreatedBy(
      @Param("status") CustomerStatus status,
      @Param("createdBy") String createdBy);
}
