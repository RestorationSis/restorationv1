package com.restorationservice.restorationv1.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restorationservice.restorationv1.model.customer.Address;
import com.restorationservice.restorationv1.model.customer.Customer;
import com.restorationservice.restorationv1.model.dto.AddressDTO;
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
  public ResponseEntity<Customer> updateClient(@RequestBody Customer request) {
    try {
      Customer updatedClient = customerService.updateClient(request);
      return ResponseEntity.ok(updatedClient);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/address/update")
  public ResponseEntity<AddressDTO> updateAddress(@RequestBody AddressDTO request) {
    try {
      AddressDTO updatedClient = customerService.updateClientAddress(request);
      return ResponseEntity.ok(updatedClient);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/address/add")
  public ResponseEntity<AddressDTO> addAddress(@RequestBody AddressDTO request) {
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

  @PutMapping("/note/update")
  public ResponseEntity<NoteDTO> updateNote(@RequestBody NoteDTO request) {
    try {
      NoteDTO updatedClient = customerService.updateClientNote(request);
      return ResponseEntity.ok(updatedClient);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/note/add")
  public ResponseEntity<NoteDTO> addNote(@RequestBody NoteDTO request) {
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

  @GetMapping("/{clientId}")
  public ResponseEntity<Customer> getClientById(@PathVariable String clientId) {
    Customer client = customerService.getClientById(clientId);
    if (client != null) {
      return ResponseEntity.ok(client);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/all")
  public ResponseEntity<List<Customer>> listAllClients() {
    List<Customer> clients = customerService.listAllClients();
    return ResponseEntity.ok(clients);
  }
}
