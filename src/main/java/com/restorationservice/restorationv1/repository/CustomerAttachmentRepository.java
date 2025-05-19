package com.restorationservice.restorationv1.repository;

import java.util.List;
import java.util.Optional;

import com.restorationservice.restorationv1.model.customer.attachment.CustomerAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAttachmentRepository extends JpaRepository<CustomerAttachment, Long> {
  @Query(value = "SELECT * FROM customer_attachment WHERE fk_customer_id = :customerId AND deleted = false", nativeQuery = true)
  List<CustomerAttachment> findByCustomerIdAndDeletedFalse(Long customerId);

  Optional<CustomerAttachment> findByDbFileId(String dbFileId);
}
