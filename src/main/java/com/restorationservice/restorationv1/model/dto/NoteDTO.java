package com.restorationservice.restorationv1.model.dto;

import com.restorationservice.restorationv1.model.customer.Note;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteDTO {
  private long customerId;
  private Note note;
}
