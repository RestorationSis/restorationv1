package com.restorationservice.restorationv1.service;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.restorationservice.restorationv1.model.customer.attachment.FileExtension;
import com.restorationservice.restorationv1.model.dto.attachment.CustomerAttachmentDTO;

public interface FileAttachmentService {
  String uploadFile(MultipartFile file, FileExtension fileExtension, String attachmentType, Long customerId, Long policyId) throws IOException;
  ResponseEntity<Resource> downloadFile(String fileName) throws IOException;
  List<CustomerAttachmentDTO> getAttachmentsByCustomerId(Long customerId);
  void deleteAttachment(Long attachmentId);

}
