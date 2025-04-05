package com.restorationservice.restorationv1.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.restorationservice.restorationv1.model.customer.attachment.FileExtension;
import com.restorationservice.restorationv1.model.dto.attachment.CustomerAttachmentDTO;
import com.restorationservice.restorationv1.service.FileAttachmentService;

@RestController
@RequestMapping("/api/v1/customer/attachment")
public class FileAttachmentController {

  @Autowired
  private FileAttachmentService fileAttachmentService;

  @PostMapping("/upload")
  public String uploadFile(
      @RequestParam("file") MultipartFile file,
      @RequestParam("fileExtension") FileExtension fileExtension,
      @RequestParam("attachmentType") String attachmentType,
      @RequestParam("customerId") Long customerId,
      @RequestParam(value = "policyId", required = false) Long policyId
  ) throws IOException {
    return fileAttachmentService.uploadFile(file, fileExtension, attachmentType, customerId, policyId);
  }


  @GetMapping("/download/{fileName}")
  public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName) throws IOException {
    // Llama al servicio, que ya devuelve un ResponseEntity<Resource>
    return fileAttachmentService.downloadFile(fileName);
  }


  @GetMapping("/customer/{customerId}")
  public List<CustomerAttachmentDTO> getAttachmentsByCustomerId(@PathVariable("customerId") Long customerId) {
    return fileAttachmentService.getAttachmentsByCustomerId(customerId);
  }
  @DeleteMapping("/{attachmentId}")
  public ResponseEntity<String> deleteAttachment(@PathVariable Long attachmentId) {
    fileAttachmentService.deleteAttachment(attachmentId);
    return ResponseEntity.ok("Attachment deleted successfully.");
  }
}
