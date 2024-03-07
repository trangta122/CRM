package com.intern.crm.controller;

import com.intern.crm.entity.ERole;
import com.intern.crm.entity.Role;
import com.intern.crm.entity.User;
import com.intern.crm.payload.request.CreateUserRequest;
import com.intern.crm.payload.response.MessageResponse;
import com.intern.crm.payload.response.UserResponse;
import com.intern.crm.repository.RoleRepository;
import com.intern.crm.repository.UserRepository;
import com.intern.crm.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "ADMIN: Create a new user",
    description = "Create a new user with: firstname, lastname, email, phone, birthday, gender, username, password and role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create a user successfully"),
            @ApiResponse(responseCode = "401", description = "Permission denied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        //check if username was existed
        if(userRepository.existsByUsername(createUserRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken."));
        }

        //check if email was existed
        if (userRepository.existsByEmail(createUserRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already taken."));
        }

        //create new user's account
        User user = new User(
                createUserRequest.getFirstname(),
                createUserRequest.getLastname(),
                createUserRequest.getLastname() + " " + createUserRequest.getFirstname(),
                createUserRequest.getEmail(),
                createUserRequest.getPhone(),
                createUserRequest.getBirthday(),
                createUserRequest.getGender(),
                createUserRequest.getUsername(),
                createUserRequest.getPassword());

        List<String> strRoles = createUserRequest.getRole();
        List<Role> roles = new ArrayList<>();

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

        user.setRoles((List<Role>) roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Create user successfully!"));
    }

    @Operation(summary = "ADMIN: Find all users",
                description = "List all users with id, firstname, lastname, fullname, email, phone, birthday, gender, username and role. Only admin can do this.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find all users successfully"),
            @ApiResponse(responseCode = "401", description = "Permission denied"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> userResponseList = userService.findAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(userResponseList);
    }

    @Operation(summary = "ADMIN: Retrieve a user by ID",
            description = "Get information about a user by ID with: id, firstname, lastname, fullname, email, phone, birthday, gender, username and role.")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<UserResponse>> getUserById (@PathVariable("id")String id) {
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    @Operation(summary = "ADMIN: Update information for specifying user by ID.", description = "Update: firstname, lastname, email, phone, birthday, gender and username.")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> editUser(@RequestBody UserResponse user, @PathVariable("id") String id) {
        return new ResponseEntity<UserResponse>(userService.updateUser(user, id), HttpStatus.OK);
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
            List<User> users = new ArrayList<User>();
            Pageable paging = PageRequest.of(page, size, Sort.by(sortBy).descending());

            Page<User> pageUsers;
            pageUsers = userRepository.findAll(paging);
            if (email == null) {
                pageUsers = userRepository.findAll(paging);
            } else {
                pageUsers = userRepository.findByEmailContaining(email, paging);
            }

            users = pageUsers.getContent();
            List<UserResponse> userResponseList = users.stream().map(u -> modelMapper.map(u, UserResponse.class)).collect(Collectors.toList());

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
