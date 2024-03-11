package com.intern.crm.controller;

import com.intern.crm.entity.Role;
import com.intern.crm.payload.response.MessageResponse;
import com.intern.crm.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleRepository roleRepository;
    //create role with name and description
    @PostMapping("")
    public ResponseEntity<?> createRole(@RequestBody Role role) {
        Role createdRole = roleRepository.save(new Role(role.getName(), role.getDescription()));
        return ResponseEntity.ok(new MessageResponse("Create role successfully."));
    }

    //get all roles
    @GetMapping("/all")
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Role>> getRoleById(@PathVariable(value = "id") String roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        return ResponseEntity.ok().body(role);
    }
}
