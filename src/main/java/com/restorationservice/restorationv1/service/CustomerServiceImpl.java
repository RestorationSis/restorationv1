package com.restorationservice.restorationv1.service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restorationservice.restorationv1.component.EntityChangeLogListener;
import com.restorationservice.restorationv1.mapper.AddressMapper;
import com.restorationservice.restorationv1.mapper.ContactMapper;
import com.restorationservice.restorationv1.model.customer.Address;
import com.restorationservice.restorationv1.model.customer.Customer;
import com.restorationservice.restorationv1.model.customer.CustomerStatus;
import com.restorationservice.restorationv1.model.customer.Note;
import com.restorationservice.restorationv1.model.dto.AddressDTO;
import com.restorationservice.restorationv1.model.dto.AddressDTOnoPolicy;
import com.restorationservice.restorationv1.model.dto.ContactDTO;
import com.restorationservice.restorationv1.model.dto.CustomerDTO;
import com.restorationservice.restorationv1.model.dto.NoteDTO;
import com.restorationservice.restorationv1.model.policy.Contact;
import com.restorationservice.restorationv1.repository.AddressRepository;
import com.restorationservice.restorationv1.repository.ContactRepository;
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

  @Autowired
  private AddressMapper addressMapper;
  @Autowired
  private ContactMapper contactMapper;
  @Autowired
  private ContactRepository contactRepository;

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
        customer.setLastUpdatedOn(new Date());
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
        customer.setLastUpdatedOn(new Date());
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
  public AddressDTOnoPolicy getAddressById(String addressId) {
    Optional<Address> addressOptional = addressRepository.findById(Long.valueOf(addressId));

    return addressMapper.toDTO(addressOptional .orElse(null));
  }

  @Override
  @Transactional
  public AddressDTO updateClientAddress(AddressDTO address) {
    List<Address> addressList = addressRepository.findAllByCustomerId(address.getCustomerId());
    //addressList.add(address.getAddress());
    Optional<Address> foundAddress = addressList.stream()
        .filter(a -> a.getId() == address.getAddress().getId())
        .findFirst();
    if (foundAddress.isPresent()) {
        validatePrimaryAddress(addressList);
      Optional<Customer> optionalCustomer =  customerRepository.findById(address.getCustomerId());

      if (optionalCustomer.isPresent()) {
        optionalCustomer.get().setLastUpdatedOn(new Date());
        customerRepository.save(optionalCustomer.get());
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
      else {
        throw new IllegalArgumentException("Client with ID " + address.getCustomerId() + " does not exist.");
      }
    }
    throw new IllegalArgumentException("Address with id " + address.getAddress().getId() + " does not exist.");
  }

  @Override
  @Transactional
  public AddressDTO addAddress(AddressDTO address) {
    List<Address> addressList = addressRepository.findAllByCustomerId(address.getCustomerId());
    addressList.add(address.getAddress());
    validatePrimaryAddress(addressList);
    Optional<Customer> optionalCustomer =  customerRepository.findById(address.getCustomerId());
    if (optionalCustomer.isPresent()) {
      optionalCustomer.get().setLastUpdatedOn(new Date());
      customerRepository.save(optionalCustomer.get());
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
      customer.get().setLastUpdatedOn(new Date());
      customerRepository.save(customer.get());
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
    Optional<Customer> customer = customerRepository.findById(updatedNote.getCustomerId());
    if (note.isPresent() && note.get().getCreatedBy().equals(SecurityUtils.getAuthenticatedUsername())) {
      noteRepository.updateNote(
          updatedNote.getNote().getId(),
          updatedNote.getCustomerId(),
          updatedNote.getNote().getContent(),
          new Date()
      );

      customer.get().setLastUpdatedOn(new Date());
      customerRepository.save(customer.get());

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
    return customerRepository.findAll().stream()
        .sorted(Comparator.comparing(Customer::getCreatedOn))
        .collect(Collectors.toList());
  }

  private void validatePrimaryAddress(List<Address> addresses) {
    if (!(addresses.stream().filter(Address::getIsPrimary).count() <= 1)) {
      throw new IllegalArgumentException("Only one address could be primary");
    }
    if (addresses.stream().noneMatch(Address::getIsPrimary)) {
      throw new IllegalArgumentException("An address should be primary");
    }
  }

  @Override
  @Transactional
  public ContactDTO addContact(ContactDTO contact) {
    Optional<Customer> customer = customerRepository.findById(contact.getCustomer());
    if (customer.isPresent()) {
      Contact entity = contactMapper.toEntity(contact, customer.get());
      Contact newContact =  contactRepository.save(entity);
      contact.setId(newContact.getId());
      return contact;
    }
    throw new IllegalArgumentException("Customer with ID " + contact.getCustomer() + " does not exist.");
  }

  @Override
  @Transactional
  public ContactDTO updateContact(ContactDTO contact) {
    Optional<Contact> existingContact = contactRepository.findById(contact.getId());
    if (existingContact.isPresent()) {
      Optional<Customer> customer = customerRepository.findById(contact.getCustomer());
      if (customer.isPresent()) {
        Contact updatedContact = contactMapper.toEntity(contact,customer.get());

        contactRepository.save(updatedContact);
        return contact;
      }
      throw new IllegalArgumentException("Customer with ID " + contact.getCustomer() + " does not exist.");
    }
    throw new IllegalArgumentException("Contact with ID " + contact.getId() + " does not exist.");
  }

  @Override
  @Transactional
  public boolean removeContact(String contactId) {
    try {
      Long id = Long.parseLong(contactId);
      if (contactRepository.existsById(id)) {
        contactRepository.deleteById(id);
        return true;
      }
    } catch (NumberFormatException e) {
      // Handle the case where contactId is not a valid number
    }
    return false;
  }

  @Override
  public ContactDTO getContactById(String contactId) {
    try {
      Long id = Long.parseLong(contactId);
      return contactMapper.toDto(contactRepository.findById(id).orElse(null));
    } catch (NumberFormatException e) {
      // Handle the case where contactId is not a valid number
    }
    return null;
  }

  @Override
  public List<ContactDTO> getContactsByCustomerId(String customerId) {
    try {
      Long id = Long.parseLong(customerId);
      return  contactRepository.findByCustomerId(id).stream().map(contact -> contactMapper.toDto(contact)).collect(
          Collectors.toList());
    } catch (NumberFormatException e) {
      // Handle the case where customerId is not a valid number
    }
    return null;
  }

  private void setCustomerPersonalInfo(Customer customer, CustomerDTO customerDTO) {
    customer.setFirstName(customerDTO.getFirstName());
    customer.setMiddleName(customerDTO.getMiddleName());
    customer.setLastName(customerDTO.getLastName());
    customer.setEmail(customerDTO.getEmail());
    customer.setCellphone(customerDTO.getCellphone());
    customer.setPrefferedLanguage(customerDTO.getPrefferedLanguage());
    customer.setTags(customerDTO.getTags());
  }
}
