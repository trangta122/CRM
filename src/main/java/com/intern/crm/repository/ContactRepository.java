package com.intern.crm.repository;

import com.intern.crm.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, String> {
    List<Contact> findContactsByOpportunitiesId(String id);

    Page<Contact> findAll(Pageable pageable);

    Page<Contact> findByEmailContaining(String email, Pageable pageable);
}
