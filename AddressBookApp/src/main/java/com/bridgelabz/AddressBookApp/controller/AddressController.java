package com.bridgelabz.AddressBookApp.controller;

import com.bridgelabz.AddressBookApp.model.Address;
import com.bridgelabz.AddressBookApp.repository.AddressRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/addressbook")
public class AddressController {

    private final AddressRepository repository;

    public AddressController(AddressRepository repository) {
        this.repository = repository;
    }

    // GET all contacts
    @GetMapping("/contacts")
    public ResponseEntity<List<Address>> getAllContacts() {
        List<Address> contacts = repository.findAll();
        return ResponseEntity.ok(contacts);
    }

    // GET contact by ID
    @GetMapping("/contacts/{id}")
    public ResponseEntity<Address> getContactById(@PathVariable Long id) {
        Optional<Address> contact = repository.findById(id);
        return contact.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST - Add a new contact
    @PostMapping("/contacts")
    public ResponseEntity<Address> addContact(@RequestBody Address address) {
        Address savedContact = repository.save(address);
        return ResponseEntity.ok(savedContact);
    }

    // PUT - Update an existing contact
    @PutMapping("/contacts/{id}")
    public ResponseEntity<Address> updateContact(@PathVariable Long id, @RequestBody Address updatedAddress) {
        return repository.findById(id).map(existingContact -> {
            existingContact.setName(updatedAddress.getName());
            existingContact.setEmail(updatedAddress.getEmail());
            existingContact.setPhone(updatedAddress.getPhone());
            existingContact.setCity(updatedAddress.getCity());
            repository.save(existingContact);
            return ResponseEntity.ok(existingContact);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE - Remove a contact by ID
    @DeleteMapping("/contacts/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok("Contact deleted successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
