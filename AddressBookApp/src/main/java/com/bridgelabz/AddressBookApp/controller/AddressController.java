package com.bridgelabz.AddressBookApp.controller;

import com.bridgelabz.AddressBookApp.dto.AddressDTO;
import com.bridgelabz.AddressBookApp.model.Address;
import com.bridgelabz.AddressBookApp.repository.AddressRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addressbook")
public class AddressController {
    private final AddressRepository repository;

    public AddressController(AddressRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/add")
    public ResponseEntity<Address> addAddress(@Valid @RequestBody AddressDTO dto) {
        Address newEntry = new Address(dto);
        return ResponseEntity.ok(repository.save(newEntry));
    }

    @GetMapping("/list")
    public ResponseEntity<List<Address>> getAllAddresses() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Long id) {
        return ResponseEntity.of(repository.findById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id, @Valid @RequestBody AddressDTO dto) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setName(dto.getName());
                    existing.setPhoneNumber(dto.getPhoneNumber());
                    existing.setAddress(dto.getAddress());
                    return ResponseEntity.ok(repository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok("Entry deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }
}
