package com.intern.crm.controller;

import com.intern.crm.entity.ERole;
import com.intern.crm.entity.Role;
import com.intern.crm.entity.User;
import com.intern.crm.payload.request.ChangePasswordRequest;
import com.intern.crm.payload.request.CreateUserRequest;
import com.intern.crm.payload.response.MessageResponse;
import com.intern.crm.payload.model.UserModel;
import com.intern.crm.repository.RoleRepository;
import com.intern.crm.repository.UserRepository;
import com.intern.crm.service.PasswordService;
import com.intern.crm.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
@CrossOrigin(origins = "*")
@Tag(name = "User", description = "User Management APIs")
@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ModelMapper modelMapper;


    @Operation(summary = "ADMIN: Create a new user")
    @PostMapping("")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRequest));
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
    public ResponseEntity<UserModel> getUserById (@PathVariable("id")String id) {
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    @Operation(summary = "ADMIN: Update information for specifying user by ID.", description = "Update: firstname, lastname, email, phone, birthday, gender and username.")
    @PutMapping("/{id}")
    public ResponseEntity<UserModel> editUser(@RequestBody UserModel user, @PathVariable("id") String id) {
        return new ResponseEntity<UserModel>(userService.updateUser(user, id), HttpStatus.OK);
    }

    @Operation(summary = "ADMIN: Pagination")
    @GetMapping("")
    public ResponseEntity<?> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "fullname") String sortBy //sort
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.pagingUser(page, size, sortBy));
    }

    @Operation(summary = "ADMIN: List roles")
    @GetMapping("/roles")
    public List<Role> getAll() {
        return roleRepository.findAll();
    }



    //    @Operation(summary = "ADMIN: Delete a user by ID")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") String id) {
//        userService.deleteUser(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
}
