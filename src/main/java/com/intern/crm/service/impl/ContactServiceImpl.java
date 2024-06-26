package com.intern.crm.service.impl;

import com.intern.crm.entity.Contact;
import com.intern.crm.entity.Opportunity;
import com.intern.crm.payload.model.ContactModel;
import com.intern.crm.payload.model.OpportunityModel;
import com.intern.crm.payload.request.CreateContactRequest;
import com.intern.crm.repository.ContactRepository;
import com.intern.crm.repository.OpportunityRepository;
import com.intern.crm.service.ContactService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    OpportunityRepository opportunityRepository;
    @Autowired
    ContactRepository contactRepository;
    @Autowired
    ModelMapper modelMapper;
    private Pageable createPageRequest(int page, int size) {
        return PageRequest.of(page, size);
    }

    //Add a contact for an opportunity
    @Override
    public Contact createContact(String opportunityId, CreateContactRequest request) {
        Opportunity opportunity = opportunityRepository.findById(opportunityId).get();
        Contact contact = modelMapper.map(request, Contact.class);
        opportunity.addContact(contact);
        return contactRepository.save(contact);
    }

    //Retrieve all contacts
    @Override
    public List<ContactModel> listAllContacts() {
        List<Contact> contacts = contactRepository.findAll();
        List<ContactModel> contactModels = new ArrayList<>();

        for (Contact contact : contacts) {
            ContactModel contactModel = modelMapper.map(contact, ContactModel.class);
            contactModels.add(contactModel);
        }
        return contactModels;
    }

    //Retrieve a contact by ID
    @Override
    public ContactModel findContactById(String id) {
        Contact contact = contactRepository.findById(id).get();
        ContactModel contactModel = modelMapper.map(contact, ContactModel.class);
        return contactModel;
    }

    @Override
    public Page<ContactModel> findContactByOpportunityId(String id, int page, int size) {
        List<Contact> contacts = contactRepository.findContactsByOpportunitiesId(id);
        List<ContactModel> contactModels = new ArrayList<>();

        for (Contact contact : contacts) {
            ContactModel contactModel = modelMapper.map(contact, ContactModel.class);
            contactModels.add(contactModel);
        }

        Pageable pageable = createPageRequest(page, size);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), contactModels.size());

        List<ContactModel> contactPage = contactModels.subList(start, end);

        return  new PageImpl<>(contactPage, pageable, contactModels.size());
    }

    //Update contact by ID
    @Override
    public ContactModel editContactById(String id, ContactModel contactModel) {
        Contact contact = contactRepository.findById(id).get();

        contact.setFirstname(contactModel.getFirstname());
        contact.setLastname(contactModel.getLastname());
        contact.setFullname(contactModel.getLastname() + " " + contactModel.getFirstname());
        contact.setEmail(contactModel.getEmail());
        contact.setPhone(contactModel.getPhone());
        contact.setBirthday(contactModel.getBirthday());
        contact.setGender(contactModel.getGender());
        contact.setJobPosition(contactModel.getJobPosition());
        contact.setLastModifiedDate(new Date());

        contactRepository.save(contact);
        ContactModel contactModel1 = modelMapper.map(contact, ContactModel.class);
        return contactModel1;
    }

    @Override
    public Map<String, Object> pagingContact(int page, int size, String sortBy) {
        List<Contact> contacts = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        Page<Contact> contactPage;
        contactPage = contactRepository.findAll(paging);
        contacts = contactPage.getContent();

        List<ContactModel> contactModels = new ArrayList<>();
        for (Contact contact : contacts) {
            ContactModel contactModel = modelMapper.map(contact, ContactModel.class);
            contactModels.add(contactModel);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("contacts", contactModels);
        response.put("currentPage", contactPage.getNumber());
        response.put("totalItems", contactPage.getTotalElements());
        response.put("totalPages", contactPage.getTotalPages());

        return response;
    }

}
