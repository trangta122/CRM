package com.intern.crm.repository;

import com.intern.crm.entity.TemplateFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateFileRepository extends JpaRepository<TemplateFile, String> {
    Page<TemplateFile> findAll(Pageable pageable);
}
