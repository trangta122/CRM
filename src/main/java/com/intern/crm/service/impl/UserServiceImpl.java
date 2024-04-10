package com.intern.crm.service.impl;

import com.intern.crm.entity.Avatar;
import com.intern.crm.entity.ERole;
import com.intern.crm.entity.Role;
import com.intern.crm.entity.User;
import com.intern.crm.payload.model.UserModel;
import com.intern.crm.payload.request.CreateUserRequest;
import com.intern.crm.payload.response.MessageResponse;
import com.intern.crm.repository.AvatarRepository;
import com.intern.crm.repository.RoleRepository;
import com.intern.crm.repository.UserRepository;
import com.intern.crm.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    AvatarRepository avatarRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${spring.default.avatar}")
    private String defaultAvatar;

    @Override
    public String createUser(CreateUserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            return "Error: Username is already taken.";
        }

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return "Error: Email is already taken.";
        }

        User user = modelMapper.map(userRequest, User.class);

        Set<String> strRoles = userRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRoles(roles);
        Avatar avatar = avatarRepository.findById(defaultAvatar).get();
        user.setAvatar(avatar);
        userRepository.save(user);
        return "Create user successfully.";
    }

    @Override
    public List<UserModel> findAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserModel> userModels = new ArrayList<>();
        for (User user: users){
            UserModel userModel = modelMapper.map(user,UserModel.class);
            List<String> roles = (user.getRoles().stream().map(e -> modelMapper.map(e.getName(), String.class)).collect(Collectors.toList()));
            userModel.setRole(roles);
            userModel.setAvatar(user.getAvatar().getId());
            userModels.add(userModel);
        }
            return userModels;
    }

    @Override
    public UserModel findUserById(String id) {
        User user = userRepository.findById(id).get();
        UserModel userModel = modelMapper.map(user, UserModel.class);
        List<String> roles = userRepository.findById(id).get().getRoles().stream().map(e -> modelMapper.map(e.getName(), String.class)).collect(Collectors.toList());
        userModel.setRole(roles);
        userModel.setAvatar(user.getAvatar().getId());

        return userModel;
    }

    @Override
    public UserModel updateUser(UserModel userModel, String id) {
        User u = userRepository.findById(id).get();

        u.setFirstname(userModel.getFirstname());
        u.setLastname(userModel.getLastname());
        u.setFullname(userModel.getFirstname() + " " + userModel.getLastname());
        u.setEmail(userModel.getEmail());
        u.setPhone(userModel.getPhone());
        u.setBirthday(userModel.getBirthday());
        u.setGender(userModel.getGender());
        u.setLastModifiedDate(new Date());

        userRepository.save(u);
        UserModel userModel1 = modelMapper.map(u, UserModel.class);
        userModel1.setRole(userModel.getRoles());
        userModel1.setAvatar(u.getAvatar().getId());
        return userModel1;
    }

//    @Override
//    public void deleteUser(String id) {
//        userRepository.deleteById(id);
//    }

}
