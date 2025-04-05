package com.restorationservice.restorationv1.model.customer.attachment;

import com.restorationservice.restorationv1.model.customer.Customer;
import com.restorationservice.restorationv1.model.policy.Policy;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "customer_attachment",
    uniqueConstraints = @UniqueConstraint(columnNames = {"file_name", "fk_customer_id", "deleted"})
)
@Getter
@Setter
public class CustomerAttachment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private FileExtension fileExtension;

  @Column(nullable = false)
  private String attachmentType;

  @Column(name = "db_file_id_name", unique = true)
  private String dbFileId;

  @Column(name = "file_name", nullable = false)
  private String fileName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fk_customer_id", nullable = false)
  private Customer customer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fk_policy_id", nullable = true)
  private Policy policy;

  @Column(name = "deleted")
  private Boolean deleted = false;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private CustomerAttachmentStatus status = CustomerAttachmentStatus.ACTIVE;
}
