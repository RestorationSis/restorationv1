package com.restorationservice.restorationv1.model.policy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "policy_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PolicyType implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "Id_Type", updatable = false, nullable = false)
  private Long id;

  @Column(name = "Group_Type", nullable = false, length = 255)
  private String groupType;

  @Column(name = "Cod_Type", nullable = false, length = 50)
  private String codType;

  @Column(name = "Description_Type", length = 500)
  private String descriptionType;
}
