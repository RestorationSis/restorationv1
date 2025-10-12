package com.restorationservice.restorationv1.model.dto.claim;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DamageTypeDTO {

  private Long damageId;

  @NotNull
  private String name;

  @NotNull
  private String description;
}
