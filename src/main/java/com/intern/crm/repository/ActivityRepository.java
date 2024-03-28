package com.intern.crm.repository;

import com.intern.crm.entity.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, String> {
    List<Activity> findActivityByOpportunityId(String id);

    Page<Activity> findAll(Pageable pageable);
}
