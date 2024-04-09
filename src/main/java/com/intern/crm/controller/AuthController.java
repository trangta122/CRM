package com.intern.crm.controller;

import com.intern.crm.payload.request.ChangePasswordRequest;
import com.intern.crm.payload.request.ForgotPasswordRequest;
import com.intern.crm.payload.request.LoginRequest;
import com.intern.crm.payload.response.JwtResponse;
import com.intern.crm.security.jwt.JwtUtils;
import com.intern.crm.security.service.UserDetailsImpl;
import com.intern.crm.service.PasswordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordService passwordService;
    @Autowired
    JwtUtils jwtUtils;

    @Operation(summary = "Sign in")
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity
                .ok(new JwtResponse(jwt));
    }
    @SecurityRequirement(name = "Authorization")
    @Operation(summary = "Change password")
    @PutMapping("/password")
    public String changePassword(@RequestBody ChangePasswordRequest request) {
        return passwordService.changePassword(request);
    }

    @Operation(summary = "Forgot password")
    @PutMapping("/reset-password")
    public String forgotPassword(@RequestBody ForgotPasswordRequest request) {
        return passwordService.forgotPassword(request);
    }

}
