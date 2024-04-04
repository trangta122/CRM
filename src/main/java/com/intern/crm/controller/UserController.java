package com.intern.crm.controller;

import com.intern.crm.entity.ERole;
import com.intern.crm.entity.Role;
import com.intern.crm.entity.User;
import com.intern.crm.payload.request.CreateUserRequest;
import com.intern.crm.payload.response.MessageResponse;
import com.intern.crm.payload.model.UserModel;
import com.intern.crm.repository.RoleRepository;
import com.intern.crm.repository.UserRepository;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
@CrossOrigin(origins = "*")
@Tag(name = "User", description = "User Management APIs")
@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/user")
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

    @Operation(summary = "ADMIN: Paging, Sort & Filter")
    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAllUsers(
            @RequestParam(required = false) String email, //filter
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "email") String sortBy //sort
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

            List<UserModel> userModelList = new ArrayList<>();
            for (User u : users) {
                UserModel userModel = modelMapper.map(u, UserModel.class);
                List<String> roles = u.getRoles().stream().map(e -> modelMapper.map(e.getName(), String.class)).collect(Collectors.toList());
                userModel.setRole(roles);
                userModelList.add(userModel);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("users", userModelList);
            response.put("currentPage", pageUsers.getNumber());
            response.put("totalItems", pageUsers.getTotalElements());
            response.put("totalPages", pageUsers.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "ADMIN: List roles")
    @GetMapping("/test/roles")
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @GetMapping("/test/users")
    public ResponseEntity<?> testUserPermission() {
        return ResponseEntity.ok(new MessageResponse("Role user allowed. Test for user role permission."));
    }

    //    @Operation(summary = "ADMIN: Delete a user by ID")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") String id) {
//        userService.deleteUser(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
}
