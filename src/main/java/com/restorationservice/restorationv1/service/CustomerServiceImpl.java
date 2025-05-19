package com.restorationservice.restorationv1.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restorationservice.restorationv1.component.EntityChangeLogListener;
import com.restorationservice.restorationv1.model.customer.Address;
import com.restorationservice.restorationv1.model.customer.Customer;
import com.restorationservice.restorationv1.model.customer.CustomerStatus;
import com.restorationservice.restorationv1.model.customer.Note;
import com.restorationservice.restorationv1.model.dto.AddressDTO;
import com.restorationservice.restorationv1.model.dto.CustomerDTO;
import com.restorationservice.restorationv1.model.dto.NoteDTO;
import com.restorationservice.restorationv1.repository.AddressRepository;
import com.restorationservice.restorationv1.repository.CustomerRepository;
import com.restorationservice.restorationv1.repository.NoteRepository;
import com.restorationservice.restorationv1.security.SecurityUtils;

@Service
public class CustomerServiceImpl implements CustomerService {

  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private AddressRepository addressRepository;

  @Autowired
  private NoteRepository noteRepository;

  @Override
  public Customer addClient(Customer client) {
    validatePrimaryAddress(client.getAddress());
    client.setLastUpdatedOn(new Date());
    return customerRepository.save(client);
  }

  @Override
  public boolean removeClient(String clientId) {
    try {
      Long id = Long.parseLong(clientId);
      if (customerRepository.existsById(id)) {
        customerRepository.deleteById(id);
        return true;
      }
    } catch (NumberFormatException e) {
      // Handle the case where clientId is not a valid number
    }
    return false;
  }

  @Override
  public Customer updateClient(Customer client) {
    validatePrimaryAddress(client.getAddress());
    client.setLastUpdatedOn(new Date());
    if (customerRepository.existsById(client.getId())) {
      return customerRepository.save(client);
    }
    throw new IllegalArgumentException("Client with ID " + client.getId() + " does not exist.");
  }

  @Override
  public CustomerDTO updateClientPersonalInfo(CustomerDTO customerDTO) {
    Optional<Customer> optionalCustomer = customerRepository.findById(customerDTO.getId());
    if (optionalCustomer.isPresent()) {
      try {
        Customer customer = optionalCustomer.get();
        setCustomerPersonalInfo(customer, customerDTO);
        customerRepository.save(customer);
        return customerDTO;
      } catch (Exception e) {
        throw new IllegalArgumentException("Error while updating customer personal info.");
      }
    } else {
      throw new IllegalArgumentException("Client with ID " + customerDTO.getId() + " does not exist.");
    }
  }

  @Override
  public void updateCustomerStatus(Long id, CustomerStatus status) {
    Optional<Customer> optionalCustomer = customerRepository.findById(id);
    if (optionalCustomer.isPresent()) {

      Customer customer = optionalCustomer.get();
      if (!customer.getStatus().equals(status)) {
        customer.setStatus(status);
        try {
          customerRepository.save(customer);
        } catch (Exception e) {
          throw new IllegalArgumentException("Error while updating customer personal info.");
        }
      } else {
        throw new IllegalArgumentException("Customer is already " + status.getStatus());
      }


    } else {
      throw new IllegalArgumentException("Client with ID " + id + " does not exist.");
    }
  }


  @Override
  public Address getAddressById(String addressId) {
    Optional<Address> addressOptional = addressRepository.findById(Long.valueOf(addressId));
    return addressOptional.orElse(null);
  }

  @Override
  @Transactional
  public AddressDTO updateClientAddress(AddressDTO address) {
    List<Address> addressList = addressRepository.findAllByCustomerId(address.getCustomerId());
    addressList.add(address.getAddress());
    Optional<Address> foundAddress = addressList.stream()
        .filter(a -> a.getId() == address.getAddress().getId())
        .findFirst();
    if (foundAddress.isPresent() && !foundAddress.get().getIsPrimary()) {
      validatePrimaryAddress(addressList);
    }
    if (customerRepository.existsById(address.getCustomerId())) {
      addressRepository.updateAddress(
          address.getAddress().getId(),
          address.getCustomerId(),
          address.getAddress().getStreetAddress(),
          address.getAddress().getUnitApartmentSuite(),
          address.getAddress().getCity(),
          address.getAddress().getState().getName(), // Se asume que State es un Enum
          address.getAddress().getCountry().name(), // Se asume que Country es un Enum
          address.getAddress().getZipCode(),
          address.getAddress().getIsPrimary()
      );
      return address;
    }
    throw new IllegalArgumentException("Client with ID " + address.getCustomerId() + " does not exist.");
  }

  @Override
  @Transactional
  public AddressDTO addAddress(AddressDTO address) {
    List<Address> addressList = addressRepository.findAllByCustomerId(address.getCustomerId());
    addressList.add(address.getAddress());
    validatePrimaryAddress(addressList);
    if (customerRepository.existsById(address.getCustomerId())) {
      addressRepository.addAddress(
          address.getCustomerId(),
          address.getAddress().getStreetAddress(),
          address.getAddress().getUnitApartmentSuite(),
          address.getAddress().getCity(),
          address.getAddress().getState().getName(), // Se asume que State es un Enum
          address.getAddress().getCountry().name(), // Se asume que Country es un Enum
          address.getAddress().getZipCode(),
          address.getAddress().getIsPrimary(),
          SecurityUtils.getAuthenticatedUsername()
      );
      EntityChangeLogListener listener = new EntityChangeLogListener();
      listener.onPrePersist(address);
      return address;
    }
    throw new IllegalArgumentException("Client with ID " + address.getCustomerId() + " does not exist.");
  }

  @Override
  @Transactional
  public boolean removeAddress(String addressId) {
    try {
      Long id = Long.parseLong(addressId);
      if (addressRepository.existsById(id)) {
        addressRepository.deleteById(id);
        return true;
      }
    } catch (NumberFormatException e) {
      // Handle the case where clientId is not a valid number
    }
    return false;
  }


  @Override
  @Transactional
  public NoteDTO addNote(NoteDTO note) {
    Optional<Customer> customer = customerRepository.findById(note.getCustomerId());
    if (customer.isPresent()) {

      noteRepository.addNote(
          note.getCustomerId(),
          SecurityUtils.getAuthenticatedFullName(),
          note.getNote().getContent(),
          SecurityUtils.getAuthenticatedUsername(),
          new Date(),
          new Date()
      );
      return note;
    }
    throw new IllegalArgumentException("Customer with ID " + note.getCustomerId() + " does not exist.");
  }

  @Override
  public List<Note> getNotesByCustomerId(String customerId) {
    long id = Long.parseLong(customerId);
    return  noteRepository.findAllByCustomerId(id);
  }

  @Override
  @Transactional
  public NoteDTO updateClientNote(NoteDTO updatedNote) {
    Optional<Note> note = noteRepository.findById(updatedNote.getNote().getId());
    if (note.isPresent() && note.get().getCreatedBy().equals(SecurityUtils.getAuthenticatedUsername())) {
      noteRepository.updateNote(
          updatedNote.getNote().getId(),
          updatedNote.getCustomerId(),
          updatedNote.getNote().getContent(),
          new Date()
      );

      return updatedNote;
    }
    throw new IllegalArgumentException("Client with ID " + updatedNote.getCustomerId() + " does not exist.");
  }

  @Override
  @Transactional
  public boolean removeNote(long customerId, String noteId) {
    try {
      Long id = Long.parseLong(noteId);
      Optional<Note> note = noteRepository.findById(id);
      if (note.isPresent() && note.get().getCreatedBy().equals(SecurityUtils.getAuthenticatedUsername())) {
        noteRepository.deleteById(id);
        return true;
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("user who created the note is different from the current one");
    }
    return false;
  }


  @Override
  public Customer getClientById(String clientId) {
    try {
      Long id = Long.parseLong(clientId);
      return customerRepository.findById(id).orElse(null);
    } catch (NumberFormatException e) {
      // Handle the case where clientId is not a valid number
    }
    return null;
  }

  @Override
  public List<Customer> getCustomersFiltered(String status, String createdBy) {
    CustomerStatus customerStatus = CustomerStatus.ACTIVE;
    if (status != null && !status.isEmpty()) {
      try {
        customerStatus = CustomerStatus.valueOf(status.toUpperCase());
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Incorrect status");
      }
    }
     return customerRepository.findCustomersByStatusAndCreatedBy(customerStatus, createdBy);
  }

  @Override
  public List<Customer> listAllClients() {
    return customerRepository.findAll();
  }

  private void validatePrimaryAddress(List<Address> addresses) {
    if (!(addresses.stream().filter(Address::getIsPrimary).count() <= 1)) {
      throw new IllegalArgumentException("Only one address could be primary");
    }
    if (addresses.stream().noneMatch(Address::getIsPrimary)) {
      throw new IllegalArgumentException("An address should be primary");
    }
  }

  private void setCustomerPersonalInfo(Customer customer, CustomerDTO customerDTO) {
    customer.setFirstName(customerDTO.getFirstName());
    customer.setMiddleName(customerDTO.getMiddleName());
    customer.setLastName(customerDTO.getLastName());
    customer.setEmail(customerDTO.getEmail());
    customer.setCellphone(customerDTO.getCellphone());
    customer.setPrefferedLanguage(customerDTO.getPrefferedLanguage());
  }
}
