package com.intern.crm.service.impl;

import com.intern.crm.entity.User;
import com.intern.crm.payload.model.UserModel;
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
        User u = userRepository.findById(id).get();
        u.setFirstname(userModel.getFirstname());
        u.setLastname(userModel.getLastname());
        u.setEmail(userModel.getEmail());
        u.setPhone(userModel.getPhone());
        u.setBirthday(userModel.getBirthday());
        u.setGender(userModel.getGender());
        u.setLastModifiedDate(new Date());
        userRepository.save(u);
        UserModel userModel1 = modelMapper.map(u, UserModel.class);
        return userModel1;
    }

//    @Override
//    public void deleteUser(String id) {
//        userRepository.deleteById(id);
//    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
    @Override
    public Page<User> findByEmailContaining(String email, Pageable pageable) {
        return userRepository.findByEmailContaining(email, pageable);
    }
}
