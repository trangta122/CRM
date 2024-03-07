package com.intern.crm.service.impl;

import com.intern.crm.entity.User;
import com.intern.crm.payload.response.UserResponse;
import com.intern.crm.repository.RoleRepository;
import com.intern.crm.repository.UserRepository;
import com.intern.crm.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<UserResponse> findAllUsers() {
        List<User> users = userRepository.findAll();

            return users.stream().map(u -> modelMapper.map(u, UserResponse.class)).collect(Collectors.toList());
    }

    @Override
    public Optional<UserResponse> findUserById(String id) {
        Optional<User> existsUser = userRepository.findById(id);
        UserResponse userResponse = modelMapper.map(existsUser, UserResponse.class);
        return Optional.ofNullable(userResponse);
    }

    @Override
    public UserResponse updateUser(UserResponse user, String id) {
        User userMatching = userRepository.findById(id).get();
        userMatching.setFirstname(user.getFirstname());
        userMatching.setLastname(user.getLastname());
        userMatching.setEmail(user.getEmail());
        userMatching.setPhone(user.getPhone());
        userMatching.setBirthday(user.getBirthday());
        userMatching.setGender(user.getGender());
        userMatching.setLastModifiedDate(new Date());
        //without setting role name
        userRepository.save(userMatching);
        UserResponse u = modelMapper.map(userMatching, UserResponse.class);
        return u;
    }

//    @Override
//    public User updateUser(User user, String id) {
//        User existingUser = userRepository.findById(id).get();
//
//        existingUser.setFirstname(user.getFirstname());
//        existingUser.setLastname(user.getLastname());
//        existingUser.setPhone(user.getPhone());
//        existingUser.setBirthday(user.getBirthday());
//        existingUser.setGender(user.getGender());
//
//        User savedUser = userRepository.save(existingUser);
//        return savedUser;
//    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
    @Override
    public Page<User> findByEmailContaining(String email, Pageable pageable) {
        return userRepository.findByEmailContaining(email, pageable);
    }
}
