package com.restorationservice.restorationv1.model.customer;

import java.util.Date;
import java.util.List;
import java.util.UUID;


import com.restorationservice.restorationv1.component.EntityChangeLogListener;
import com.restorationservice.restorationv1.component.EntityListener;
import com.restorationservice.restorationv1.security.SecurityUtils;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners({EntityListener.class, EntityChangeLogListener.class})

@Table(name="customer")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  private long id;
  @Column(name = "number", unique = true, nullable = false, updatable = false)
  private Long number;

  @NotNull(message = "first name field must not be null")
  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "middle_name", nullable = false)
  private String middleName;

  @NotNull(message = "last name field must not be null")
  @Column(name = "last_name", nullable = false)
  private String lastName;

  @NotNull(message = "email field must not be null")
  @Email(message = "Email should be valid")
  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @NotNull(message = "cell phone field must not be null")
  @Column(name = "cellphone", nullable = false, unique = true)
  private String cellphone;

  @OneToMany(targetEntity = Address.class, cascade = CascadeType.ALL)
  @JoinColumn(name = "customer_fk", referencedColumnName = "id")
  private List<Address> address;

  @OneToMany(targetEntity = Note.class, cascade = CascadeType.ALL)
  @JoinColumn(name = "notes_fk", referencedColumnName = "id")
  private List<Note> notes;

  @Column(name = "created_on", updatable = false,  nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdOn;

  @Column(name = "last_update_on", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastUpdatedOn;

  @NotNull(message = "language field must not be null")
  @Column(name = "preferred_language", nullable = false)
  @Enumerated(EnumType.STRING)
  private Language prefferedLanguage;

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private CustomerStatus status;

  @Column(name = "created_by")
  private String createdBy;

  @PrePersist
  private void onCreate() {
    this.createdOn = new Date();
    this.createdBy = SecurityUtils.getAuthenticatedUsername();
  }

  public void setNumber(Long number) {
    this.number = number;
  }

}
