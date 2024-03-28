package com.intern.crm.service.impl;

import com.intern.crm.entity.Role;
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
        List<UserModel> userModels = new ArrayList<>();
        for (User user: users){
            UserModel userModel = modelMapper.map(user,UserModel.class);
            List<String> roles = (user.getRoles().stream().map(e -> modelMapper.map(e.getName(), String.class)).collect(Collectors.toList()));
            userModel.setRole(roles);
            userModels.add(userModel);
        }
            return userModels;
    }

    @Override
    public Optional<UserModel> findUserById(String id) {
        Optional<User> existsUser = userRepository.findById(id);
        UserModel userResponse = modelMapper.map(existsUser, UserModel.class);
        List<String> roles = userRepository.findById(id).get().getRoles().stream().map(e -> modelMapper.map(e.getName(), String.class)).collect(Collectors.toList());
        userResponse.setRole(roles);
        return Optional.ofNullable(userResponse);
    }

    @Override
    public UserModel updateUser(UserModel userModel, String id) {
        User u = userRepository.findById(id).get();

        u.setFirstname(userModel.getFirstname());
        u.setLastname(userModel.getLastname());
        u.setFullname(userModel.getLastname() + " " + userModel.getFirstname());
        u.setEmail(userModel.getEmail());
        u.setPhone(userModel.getPhone());
        u.setBirthday(userModel.getBirthday());
        u.setGender(userModel.getGender());
        u.setLastModifiedDate(new Date());

        userRepository.save(u);
        UserModel userModel1 = modelMapper.map(u, UserModel.class);
        userModel1.setRole(userModel.getRoles());
        return userModel1;
    }

//    @Override
//    public void deleteUser(String id) {
//        userRepository.deleteById(id);
//    }

}
