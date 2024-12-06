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

import com.restorationservice.restorationv1.model.Client;
import com.restorationservice.restorationv1.service.ClientService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class ClientController {

  private final ClientService clientService;

  @PostMapping("/register")
  public ResponseEntity<Client> register(@Valid @RequestBody Client request) {
    Client client = clientService.addClient(request);
    return ResponseEntity.ok(client);
  }

  @DeleteMapping("/{clientId}")
  public ResponseEntity<Void> removeClient(@PathVariable String clientId) {
    boolean isRemoved = clientService.removeClient(clientId);
    if (isRemoved) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/update")
  public ResponseEntity<Client> updateClient(@RequestBody Client request) {
    try {
      Client updatedClient = clientService.updateClient(request);
      return ResponseEntity.ok(updatedClient);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/{clientId}")
  public ResponseEntity<Client> getClientById(@PathVariable String clientId) {
    Client client = clientService.getClientById(clientId);
    if (client != null) {
      return ResponseEntity.ok(client);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/all")
  public ResponseEntity<List<Client>> listAllClients() {
    List<Client> clients = clientService.listAllClients();
    return ResponseEntity.ok(clients);
  }
}
