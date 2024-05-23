package com.intern.crm.service.impl;

import com.intern.crm.entity.Avatar;
import com.intern.crm.entity.ERole;
import com.intern.crm.entity.Role;
import com.intern.crm.entity.User;
import com.intern.crm.payload.model.FileModel;
import com.intern.crm.payload.model.UserModel;
import com.intern.crm.payload.request.CreateUserRequest;
import com.intern.crm.payload.request.EmailRequest;
import com.intern.crm.payload.response.MessageResponse;
import com.intern.crm.repository.AvatarRepository;
import com.intern.crm.repository.RoleRepository;
import com.intern.crm.repository.UserRepository;
import com.intern.crm.service.EmailService;
import com.intern.crm.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    AvatarRepository avatarRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    EmailService emailService;

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
                    case "Admin":
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
        //Generate password
        String password = RandomStringUtils.randomAlphanumeric(10);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(roles);
        user.setAvatar(avatarRepository.findById(defaultAvatar).get());

        userRepository.save(user);

        EmailRequest emailRequest = new EmailRequest(
                "tathitrang1202@gmail.com",
                "New account email",
                "Please sign in with the username and password below to start your work: " + "\n" +
                        "Username: " + user.getUsername() + "\n" +
                        "Password: " + password
        );

        emailService.sendSimpleEmail(emailRequest);
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

            FileModel avatar = modelMapper.map(user.getAvatar(), FileModel.class);
            userModel.setAvatar(avatar);

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

        FileModel avatar = modelMapper.map(user.getAvatar(), FileModel.class);
        userModel.setAvatar(avatar);

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
        userModel1.setAvatar(modelMapper.map(u.getAvatar(), FileModel.class));

        return userModel1;
    }

    @Override
    public Map<String, Object> pagingUser(int page, int size, String sortBy) {
        List<User> users = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        Page<User> pageUsers;
        pageUsers = userRepository.findAll(paging);

        users = pageUsers.getContent();

        List<UserModel> userModelList = new ArrayList<>();
        for (User u : users) {
            UserModel userModel = modelMapper.map(u, UserModel.class);
            List<String> roles = u.getRoles().stream().map(e -> modelMapper.map(e.getName(), String.class)).collect(Collectors.toList());
            userModel.setRole(roles);
            userModel.setAvatar(modelMapper.map(u.getAvatar(), FileModel.class));
            userModelList.add(userModel);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("users", userModelList);
        response.put("currentPage", pageUsers.getNumber());
        response.put("totalItems", pageUsers.getTotalElements());
        response.put("totalPages", pageUsers.getTotalPages());

        return response;
    }

//    @Override
//    public void deleteUser(String id) {
//        userRepository.deleteById(id);
//    }

}
