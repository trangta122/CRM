package com.intern.crm.repository;

import com.intern.crm.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Page<User> findAll(Pageable pageable);
    Page<User> findByEmailContaining(String email, Pageable pageable);

    @Transactional
    void deleteByRoleId(String roleId); //delete all Users of a Role specified by roleId

    List<User> findByRoleId(String roleId); //returns all Users of a Role specified by roleId
}
