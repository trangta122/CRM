package com.intern.crm.repository;

import com.intern.crm.entity.ERole;
import com.intern.crm.entity.Role;
import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole roleName);
}