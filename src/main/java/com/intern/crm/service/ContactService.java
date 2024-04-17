package com.intern.crm.service;

import com.intern.crm.entity.Contact;
import com.intern.crm.payload.model.ContactModel;
import com.intern.crm.payload.model.OpportunityModel;
import com.intern.crm.payload.request.CreateContactRequest;

import java.util.List;
import java.util.Map;

public interface ContactService {
    //Add a contact for an opportunity
    Contact createContact(String opportunityId, CreateContactRequest request);

    //Retrieve all contacts
    List<ContactModel> listAllContacts();

    //Retrieve a contact by ID
    ContactModel findContactById(String id);

    //Retrieve all contacts of an opportunity
    List<ContactModel> findContactByOpportunityId(String id);

    //Update a contact
    ContactModel editContactById(String id, ContactModel contactModel);

    //Pagination, Sort & Filter
    Map<String, Object> pagingContact(int page, int size, String sortBy);
}
