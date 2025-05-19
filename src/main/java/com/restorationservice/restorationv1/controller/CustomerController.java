package com.restorationservice.restorationv1.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.restorationservice.restorationv1.json.Views;
import com.restorationservice.restorationv1.model.customer.Address;
import com.restorationservice.restorationv1.model.customer.Customer;
import com.restorationservice.restorationv1.model.customer.CustomerStatus;
import com.restorationservice.restorationv1.model.customer.Note;
import com.restorationservice.restorationv1.model.dto.AddressDTO;
import com.restorationservice.restorationv1.model.dto.CustomerDTO;
import com.restorationservice.restorationv1.model.dto.NoteDTO;
import com.restorationservice.restorationv1.service.CustomerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService customerService;

  @PostMapping("/register")
  public ResponseEntity<Customer> register(@Valid @RequestBody Customer request) {
    Customer client = customerService.addClient(request);
    return ResponseEntity.ok(client);
  }

  @DeleteMapping("/{clientId}")
  public ResponseEntity<Void> removeClient(@PathVariable String clientId) {
    boolean isRemoved = customerService.removeClient(clientId);
    if (isRemoved) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/update")
  public ResponseEntity<Customer> updateClient(@Valid @RequestBody Customer request) {
    try {
      Customer updatedClient = customerService.updateClient(request);
      return ResponseEntity.ok(updatedClient);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PatchMapping("/updatePersonalInfo")
  public ResponseEntity<CustomerDTO> updatePersonalInfo(@Valid @RequestBody CustomerDTO request) {
      CustomerDTO updatedClient = customerService.updateClientPersonalInfo(request);
      return ResponseEntity.ok(updatedClient);
  }

  @PatchMapping("/{id}/status/{status}")
  public ResponseEntity<Map<String, Object>> updateCustomerStatus(
      @PathVariable Long id,
      @PathVariable CustomerStatus status) {

    customerService.updateCustomerStatus(id, status);
    Map<String, Object> response = new HashMap<>();
    response.put("id", id);
    response.put("status", status);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/{clientId}")
  @JsonView(Views.Detail.class)
  public ResponseEntity<Customer> getClientById(@PathVariable String clientId) {
    Customer client = customerService.getClientById(clientId);
    if (client != null) {
      return ResponseEntity.ok(client);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping()
  @JsonView(Views.Summary.class)
  public ResponseEntity<List<Customer>> getCustomersByStatusAndCreatedBy(
      @RequestParam(required = false) String status,
      @RequestParam(required = false) String createdBy) {
    List<Customer> clients = customerService.getCustomersFiltered(status, createdBy);
    return ResponseEntity.ok(clients);
  }

  @GetMapping("/all")
  @JsonView(Views.Summary.class)
  public ResponseEntity<List<Customer>> listAllClients() {
    List<Customer> clients = customerService.listAllClients();
    return ResponseEntity.ok(clients);
  }

  @PutMapping("/address/update")
  public ResponseEntity<AddressDTO> updateAddress(@Valid @RequestBody AddressDTO request) {
    AddressDTO updatedClient = customerService.updateClientAddress(request);
    return ResponseEntity.ok(updatedClient);
  }

  @PostMapping("/address/add")
  public ResponseEntity<AddressDTO> addAddress(@Valid @RequestBody AddressDTO request) {
    AddressDTO addressDTO = customerService.addAddress(request);
    return ResponseEntity.ok(addressDTO);
  }

  @DeleteMapping("address/{addressId}")
  public ResponseEntity<Void> removeAddress(@PathVariable String addressId) {
    boolean isRemoved = customerService.removeAddress(addressId);
    if (isRemoved) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/address/{addressId}")
  public ResponseEntity<Address> getAddressById(@PathVariable String addressId) {
    Address address = customerService.getAddressById(addressId);
    if (address != null) {
      return ResponseEntity.ok(address);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/note/{customerId}")
  public ResponseEntity<List<Note>> getNotesByCustomerId(@PathVariable String customerId) {
    List<Note> notes = customerService.getNotesByCustomerId(customerId);
    if (notes != null) {
      return ResponseEntity.ok(notes);
    } else {
      return ResponseEntity.notFound().build();
    }
  }


  @PutMapping("/note/update")
  public ResponseEntity<NoteDTO> updateNote(@Valid @RequestBody NoteDTO request) {
    try {
      NoteDTO updatedClient = customerService.updateClientNote(request);
      return ResponseEntity.ok(updatedClient);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/note/add")
  public ResponseEntity<NoteDTO> addNote(@Valid @RequestBody NoteDTO request) {
    NoteDTO noteDTO = customerService.addNote(request);
    return ResponseEntity.ok(noteDTO);
  }

  @DeleteMapping("note/{customerId}/{noteId}")
  public ResponseEntity<Void> removeNote(@PathVariable long customerId, @PathVariable String noteId) {
    boolean isRemoved = customerService.removeNote(customerId, noteId);
    if (isRemoved) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }


}
