package com.intern.crm.controller;

import com.intern.crm.entity.Role;
import com.intern.crm.entity.User;
import com.intern.crm.payload.request.CreateUserRequest;
import com.intern.crm.payload.response.MessageResponse;
import com.intern.crm.payload.response.UserModel;
import com.intern.crm.repository.RoleRepository;
import com.intern.crm.repository.UserRepository;
import com.intern.crm.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Tag(name = "User", description = "User Management APIs")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
//    @Autowired
//    PasswordEncoder passwordEncoder;
    @Autowired
    ModelMapper modelMapper;


//        //create new user's account
//        User user = new User(
//                createUserRequest.getFirstname(),
//                createUserRequest.getLastname(),
//                createUserRequest.getLastname() + " " + createUserRequest.getFirstname(),
//                createUserRequest.getEmail(),
//                createUserRequest.getPhone(),
//                createUserRequest.getBirthday(),
//                createUserRequest.getGender(),
//                createUserRequest.getUsername(),
//                createUserRequest.getPassword());
//
//        userRepository.save(user);
//
//        return ResponseEntity.ok(new MessageResponse("Create user successfully!"));
//    }
    //retrieve all User of a Role specified by ID

//    @PostMapping("/{roleId}/users")
//    public ResponseEntity<User> createUser(@PathVariable(value = "roleId") String roleId, @RequestBody User user) {
//        Optional<Object> u = roleRepository.findById(roleId).map(role -> {
//            user.setRole(role);
//            return userRepository.save(user);
//        });
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }
    @PostMapping("/{roleId}/user")
    public ResponseEntity<?> createUser(@PathVariable(value = "roleId") String roleId, @RequestBody CreateUserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken."));
        }

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already taken."));
        }

        Optional<CreateUserRequest> u = roleRepository.findById(roleId).map(role -> {userRequest.setRole(role);
            return userRequest;
        });
        User user = modelMapper.map(u, User.class);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("Create user successfully!"));
    }
    @Operation(summary = "ADMIN: Find all users",
                description = "List all users with id, firstname, lastname, fullname, email, phone, birthday, gender, username and role. Only admin can do this.")
    @GetMapping("/all")
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> userResponseList = userService.findAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(userResponseList);
    }

    @Operation(summary = "ADMIN: Retrieve a user by ID",
            description = "Get information about a user by ID with: id, firstname, lastname, fullname, email, phone, birthday, gender, username and role.")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<UserModel>> getUserById (@PathVariable("id")String id) {
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    @Operation(summary = "ADMIN: Update information for specifying user by ID.", description = "Update: firstname, lastname, email, phone, birthday, gender and username.")
    @PutMapping("/{id}")
    public ResponseEntity<UserModel> editUser(@RequestBody UserModel user, @PathVariable("id") String id) {
        return new ResponseEntity<UserModel>(userService.updateUser(user, id), HttpStatus.OK);
    }

    @Operation(summary = "ADMIN: Delete a user by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "ADMIN: Paging, Sort & Filter")
    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAllUsers(
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "email") String sortBy
    ) {
        try {
            List<User> users = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size, Sort.by(sortBy).descending());

            Page<User> pageUsers;
            pageUsers = userRepository.findAll(paging);
            if (email == null) {
                pageUsers = userRepository.findAll(paging);
            } else {
                pageUsers = userRepository.findByEmailContaining(email, paging);
            }

            users = pageUsers.getContent();
            List<UserModel> userResponseList = users.stream().map(u -> modelMapper.map(u, UserModel.class)).collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("users", userResponseList);
            response.put("currentPage", pageUsers.getNumber());
            response.put("totalItems", pageUsers.getTotalElements());
            response.put("totalPages", pageUsers.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
