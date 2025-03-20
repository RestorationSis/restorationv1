package com.restorationservice.restorationv1.mapper;

import com.restorationservice.restorationv1.model.dto.attachment.CustomerAttachmentDTO;
import com.restorationservice.restorationv1.model.customer.attachment.CustomerAttachment;
import org.springframework.stereotype.Component;

@Component
public class CustomerAttachmentMapper {

  public CustomerAttachmentDTO toDto(CustomerAttachment entity) {
    if (entity == null) {
      return null;
    }
    CustomerAttachmentDTO dto = new CustomerAttachmentDTO();
    dto.setId(entity.getId());
    dto.setFileExtension(entity.getFileExtension());
    dto.setAttachmentType(entity.getAttachmentType());
    dto.setDbFileId(entity.getDbFileId());
    dto.setFileName(entity.getFileName());
    dto.setCustomer(entity.getCustomer() != null ? entity.getCustomer().getId() : null);
    dto.setPolicy(entity.getPolicy() != null ? entity.getPolicy().getId() : null);
    dto.setDeleted(entity.getDeleted() != null ? "true": "false");
    return dto;
  }

  public CustomerAttachment toEntity(CustomerAttachmentDTO dto) {
    if (dto == null) {
      return null;
    }
    CustomerAttachment entity = new CustomerAttachment();
    entity.setId(dto.getId());
    entity.setFileExtension(dto.getFileExtension());
    entity.setAttachmentType(dto.getAttachmentType());
    entity.setDbFileId(dto.getDbFileId());
    entity.setFileName(dto.getFileName());
    // No se asigna directamente customer ni policy porque solo tenemos los IDs
    return entity;
  }
}