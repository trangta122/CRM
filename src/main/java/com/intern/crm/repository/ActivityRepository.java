package com.intern.crm.repository;

import com.intern.crm.entity.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface ActivityRepository extends JpaRepository<Activity, String> {

    List<Activity> findActivityByOpportunityId(String id);
    @Query(value = "SELECT a.* FROM Activity a WHERE ((a.type <> 'Auto' AND a.is_done = true) OR a.type = 'Auto') AND a.opportunity_id = :id ORDER BY a.last_modified_date DESC", nativeQuery = true)
    List<Activity> findAutoActivityByOpportunityId(@Param("id") String id);

    @Query(value = "SELECT a.* FROM Activity  a WHERE a.type <> 'Auto' AND a.is_done = false AND a.opportunity_id = :id ORDER BY a.last_modified_date DESC", nativeQuery = true)
    List<Activity> findScheduleActivityByOpportunityId(@Param("id") String id);

    Page<Activity> findAll(Pageable pageable);
}
