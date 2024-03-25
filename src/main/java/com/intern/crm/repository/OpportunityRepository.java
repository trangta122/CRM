package com.intern.crm.repository;

import com.intern.crm.entity.Opportunity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, String> {
    Page<Opportunity> findAll(Pageable pageable);
    Page<Opportunity> findByEmailContaining(String email, Pageable pageable);
}
