package com.restorationservice.restorationv1.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.restorationservice.restorationv1.model.customer.attachment.AttachmentType;
import com.restorationservice.restorationv1.model.customer.attachment.CustomerAttachment;
import com.restorationservice.restorationv1.model.customer.attachment.FileExtension;
import com.restorationservice.restorationv1.model.dto.attachment.CustomerAttachmentDTO;

public interface FileAttachmentService {
  String uploadFile(MultipartFile file, FileExtension fileExtension, AttachmentType attachmentType, Long customerId, Long policyId) throws IOException;

  String downloadFile(String fileName) throws IOException;
  List<CustomerAttachmentDTO> getAttachmentsByCustomerId(Long customerId);
  void deleteAttachment(Long attachmentId);

}
