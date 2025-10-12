package com.restorationservice.restorationv1.model.claim;

import com.restorationservice.restorationv1.component.EntityChangeLogListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@EntityListeners(EntityChangeLogListener.class)
@Table(name = "damage_type")
public class DamageType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "damage_id", updatable = false, nullable = false)
  private Long damageId;

  @NotNull
  @Column(name = "name")
  private String name;

  @NotNull
  @Column(name = "description", columnDefinition = "TEXT", nullable = false)
  private String description;
}
