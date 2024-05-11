package com.intern.crm.repository;

import com.intern.crm.entity.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StageRepository extends JpaRepository<Stage, String> {
}
