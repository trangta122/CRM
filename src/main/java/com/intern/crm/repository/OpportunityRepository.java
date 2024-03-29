package com.intern.crm.repository;

import com.intern.crm.entity.Opportunity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, String> {
    Page<Opportunity> findAll(Pageable pageable);

    Page<Opportunity> findByEmailContaining(String email, Pageable pageable);

    List<Opportunity> findOpportunityByContactsId(String id);

    List<Opportunity> findByStageId(String id);
}
