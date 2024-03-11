package com.intern.crm.service.impl;

import com.intern.crm.entity.User;
import com.intern.crm.payload.response.UserModel;
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
    public List<UserModel> findAllUsers() {
        List<User> users = userRepository.findAll();

            return users.stream().map(u -> modelMapper.map(u, UserModel.class)).collect(Collectors.toList());
    }

    @Override
    public Optional<UserModel> findUserById(String id) {
        Optional<User> existsUser = userRepository.findById(id);
        UserModel userResponse = modelMapper.map(existsUser, UserModel.class);
        return Optional.ofNullable(userResponse);
    }

    @Override
    public UserModel updateUser(UserModel userModel, String id) {
        User userMatchingID = userRepository.findById(id).get();
        userMatchingID.setFirstname(userModel.getFirstname());
        userMatchingID.setLastname(userModel.getLastname());
        userMatchingID.setEmail(userModel.getEmail());
        userMatchingID.setPhone(userModel.getPhone());
        userMatchingID.setBirthday(userModel.getBirthday());
        userMatchingID.setGender(userModel.getGender());
        userMatchingID.setLastModifiedDate(new Date());
        userMatchingID.setRole(userModel.getRole());
        //without setting role name
        userRepository.save(userMatchingID);
        UserModel user = modelMapper.map(userMatchingID, UserModel.class);
        return user;
    }

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

    @Override
    public List<UserModel> findByRoleId(Integer id) {
        return null;
    }
}
