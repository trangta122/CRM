package com.intern.crm.repository;

import com.intern.crm.entity.Attachment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<Attachment, String> {
    Page<Attachment> findAll(Pageable pageable);
}
