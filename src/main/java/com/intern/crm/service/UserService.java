package com.intern.crm.service;

import com.intern.crm.entity.User;
import com.intern.crm.payload.response.UserResponse;
import com.intern.crm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserResponse> findAllUsers();
    Optional<UserResponse> findUserById(String id);
    UserResponse updateUser(UserResponse user, String id);
    void deleteUser(String id);
    Page<User> findAll(Pageable pageable);
    Page<User> findByEmailContaining(String email, Pageable pageable);
}
