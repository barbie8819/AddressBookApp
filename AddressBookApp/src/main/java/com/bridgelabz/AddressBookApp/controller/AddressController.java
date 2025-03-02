package com.bridgelabz.AddressBookApp.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/addressbook")
public class AddressController {

    // In-memory list to store addresses
    private final List<Address> addressList = new ArrayList<>();

    // GET all contacts
    @GetMapping("/contacts")
    public List<Address> getAllContacts() {
        return addressList;
    }

    // GET contact by ID
    @GetMapping("/contacts/{id}")
    public Address getContactById(@PathVariable int id) {
        return addressList.stream()
                .filter(address -> address.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // POST - Add a new contact
    @PostMapping("/contacts")
    public String addContact(@RequestBody Address address) {
        address.setId(addressList.size() + 1); // Assign a unique ID
        addressList.add(address);
        return "Contact added successfully!";
    }

    // PUT - Update an existing contact
    @PutMapping("/contacts/{id}")
    public String updateContact(@PathVariable int id, @RequestBody Address updatedAddress) {
        for (Address address : addressList) {
            if (address.getId() == id) {
                address.setName(updatedAddress.getName());
                address.setEmail(updatedAddress.getEmail());
                address.setPhone(updatedAddress.getPhone());
                address.setCity(updatedAddress.getCity());
                return "Contact updated successfully!";
            }
        }
        return "Contact not found!";
    }

    // DELETE - Remove a contact by ID
    @DeleteMapping("/contacts/{id}")
    public String deleteContact(@PathVariable int id) {
        return addressList.removeIf(address -> address.getId() == id) ?
                "Contact deleted successfully!" : "Contact not found!";
    }

    // Address Class (Nested)
    static class Address {
        private int id;
        private String name;
        private String email;
        private String phone;
        private String city;

        // Getters and Setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }

        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
    }
}
