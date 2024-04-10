package com.intern.crm.repository;

import com.intern.crm.entity.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvatarRepository extends JpaRepository<Avatar, String> {
    @Override
    void deleteById(String s);
}
