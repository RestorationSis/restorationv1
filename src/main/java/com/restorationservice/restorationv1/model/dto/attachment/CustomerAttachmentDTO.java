package com.restorationservice.restorationv1.model.dto.attachment;


import com.restorationservice.restorationv1.model.customer.attachment.FileExtension;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerAttachmentDTO {
  private Long id;
  private FileExtension fileExtension;
  private String attachmentType;
  private String dbFileId;
  private String fileName;
  private Long customer; // ID del Customer
  private Long policy;   // ID del Policy
  private String deleted;
}
