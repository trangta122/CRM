package com.intern.crm.controller;

import com.intern.crm.payload.model.ContactModel;
import com.intern.crm.payload.request.CreateContactRequest;
import com.intern.crm.payload.response.MessageResponse;
import com.intern.crm.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "*")
@Tag(name = "Contact", description = "Contact Management APIs")
@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/contacts")
public class ContactController {
    @Autowired
    ContactService contactService;

    @Operation(summary = "Add a contact for an opportunity by opportunity's ID")
    @PostMapping("/opportunity/{id}")
    public ResponseEntity<?> addContact(@PathVariable("id") String opportunityId, @RequestBody CreateContactRequest contactRequest) {
        contactService.createContact(opportunityId, contactRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Add contact successfully."));
    }

    @Operation(summary = "Retrieve all contacts")
    @GetMapping("/all")
    public ResponseEntity<?> findAllContacts() {
        return ResponseEntity.status(HttpStatus.OK).body(contactService.listAllContacts());
    }

    @Operation(summary = "Retrieve a contact by ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getContactById(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(contactService.findContactById(id));
    }

    @Operation(summary = "Retrieve all contacts of an opportunity by Opportunity's ID")
    @GetMapping("/opportunity/{id}")
    public ResponseEntity<?> getAllContactByOpportunityId(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(contactService.findContactByOpportunityId(id));
    }

    @Operation(summary = "Update a contact by ID")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateContact(@PathVariable("id") String id, @RequestBody ContactModel contactModel) {
        return ResponseEntity.status(HttpStatus.OK).body(contactService.editContactById(id, contactModel));
    }

    @Operation(summary = "Pagination, Sort")
    @GetMapping("")
    public ResponseEntity<?> getAllContacts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "fullname") String sortBy
            ) {

        return ResponseEntity.status(HttpStatus.OK).body(contactService.pagingContact(page, size, sortBy));
    }

}
