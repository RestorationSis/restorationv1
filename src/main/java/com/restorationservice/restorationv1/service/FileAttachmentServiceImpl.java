package com.restorationservice.restorationv1.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.restorationservice.restorationv1.mapper.CustomerAttachmentMapper;
import com.restorationservice.restorationv1.model.customer.Customer;
import com.restorationservice.restorationv1.model.customer.attachment.AttachmentType;
import com.restorationservice.restorationv1.model.customer.attachment.CustomerAttachment;
import com.restorationservice.restorationv1.model.customer.attachment.FileExtension;
import com.restorationservice.restorationv1.model.dto.attachment.CustomerAttachmentDTO;
import com.restorationservice.restorationv1.model.policy.Policy;
import com.restorationservice.restorationv1.repository.CustomerAttachmentRepository;
import com.restorationservice.restorationv1.repository.CustomerRepository;
import com.restorationservice.restorationv1.repository.PolicyRepository;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
public class FileAttachmentServiceImpl implements FileAttachmentService {

  private final S3Client s3Client;
  private CustomerRepository customerRepository;
  private PolicyRepository policyRepository;
  private CustomerAttachmentRepository customerAttachmentRepository;
  private CustomerAttachmentMapper customerAttachmentMapper;

  private static final String BUCKET_NAME = "restoration-bucket-v1";

  @Autowired
  public FileAttachmentServiceImpl(S3Client s3Client, CustomerRepository customerRepository,
      PolicyRepository policyRepository, CustomerAttachmentRepository customerAttachmentRepository, CustomerAttachmentMapper customerAttachmentMapper) {
    this.s3Client = s3Client;
    this.customerRepository = customerRepository;
    this.policyRepository = policyRepository;
    this.customerAttachmentRepository = customerAttachmentRepository;
    this.customerAttachmentMapper = customerAttachmentMapper;
  }

  public String uploadFile(
      MultipartFile file,
      FileExtension fileExtension,
      AttachmentType attachmentType,
      Long customerId,
      Long policyId
  ) throws IOException {
    Customer customer = findCustomerById(customerId);
    Policy policy = (policyId != null) ? findPolicyById(policyId) : null;

    // First, create and save the attachment to get the ID
    CustomerAttachment attachment = new CustomerAttachment();
    attachment.setFileExtension(fileExtension);
    attachment.setAttachmentType(attachmentType);
    attachment.setFileName(file.getOriginalFilename());
    attachment.setCustomer(customer);
    attachment.setPolicy(policy);

    // Save the attachment and get the ID
    CustomerAttachment savedAttachment = customerAttachmentRepository.save(attachment);

    // Now generate the DB file ID using the saved attachment's ID
    String dbFileId = generateDbFileIdName(customerId, file.getOriginalFilename(), savedAttachment.getId());

    // Upload the file to S3
    uploadToS3(file, dbFileId);

    // Save the attachment with the correct dbFileId
    attachment.setDbFileId(dbFileId);
    customerAttachmentRepository.save(attachment);

    return "File uploaded successfully: " + dbFileId;
  }

  private Customer findCustomerById(Long customerId) {
    return customerRepository.findById(customerId)
        .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + customerId));
  }

  private Policy findPolicyById(Long policyId) {
    return policyRepository.findById(policyId)
        .orElseThrow(() -> new IllegalArgumentException("Policy not found with ID: " + policyId));
  }

  private String generateDbFileIdName(Long customerId, String fileName, Long attachmentId) {
    return customerId + "_" + attachmentId + "_" + fileName;
  }

  private void uploadToS3(MultipartFile file, String dbFileId) throws IOException {
    PutObjectRequest putObjectRequest = PutObjectRequest.builder()
        .bucket(BUCKET_NAME)
        .key(dbFileId)
        .build();
    s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
  }

  @Override
  public String downloadFile(String fileName) throws IOException {
    String localPath = "/Users/" + System.getProperty("user.name") + "/Documents/";
    if (!doesObjectExists(fileName)) {
      return "file does not exist";
    }
    GetObjectRequest request = GetObjectRequest.builder()
        .bucket(BUCKET_NAME)
        .key(fileName)
        .build();
    ResponseInputStream<GetObjectResponse> result = s3Client.getObject(request);
    try (FileOutputStream fileOutputStream = new FileOutputStream(localPath + fileName)) {
      System.out.println(localPath + fileName);
      byte[] readBuf = new byte[1024];
      int readLen;
      while ((readLen = result.read(readBuf)) > 0) {
        fileOutputStream.write(readBuf, 0, readLen);
      }
    } catch (IOException e) {
      throw new IOException(e.getMessage());
    }
    return "file downloaded successfully";
  }

  private boolean doesObjectExists(String objectKey) {
    try {
      HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
          .bucket(BUCKET_NAME)
          .key(objectKey)
          .build();
      s3Client.headObject(headObjectRequest);
    } catch (S3Exception e) {
      if (e.statusCode() == 404) {
        return false;
      }
    }
    return true;
  }

  @Override
  public List<CustomerAttachmentDTO> getAttachmentsByCustomerId(Long customerId) {
    return customerAttachmentRepository.findByCustomerId(customerId)
        .stream()
        .map(customerAttachmentMapper::toDto)
        .toList();
  }

  @Override
  public void deleteAttachment(Long attachmentId) {
    CustomerAttachment attachment = customerAttachmentRepository.findById(attachmentId)
        .orElseThrow(() -> new IllegalArgumentException("Attachment not found with id: " + attachmentId));

    deleteFromS3(attachment.getDbFileId());

    attachment.setDeleted(true);
    customerAttachmentRepository.save(attachment);
  }
  private void deleteFromS3(String dbFileId) {
    try {
      DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
          .bucket(BUCKET_NAME)
          .key(dbFileId)
          .build();
      s3Client.deleteObject(deleteObjectRequest);
    } catch (S3Exception e) {
      throw new RuntimeException("Failed to delete file from S3: " + e.getMessage());
    }
  }
}
