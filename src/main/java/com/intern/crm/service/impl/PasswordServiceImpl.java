package com.intern.crm.service.impl;

import com.intern.crm.entity.User;
import com.intern.crm.payload.request.ChangePasswordRequest;
import com.intern.crm.repository.UserRepository;
import com.intern.crm.security.service.UserDetailsImpl;
import com.intern.crm.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PasswordServiceImpl implements PasswordService {
    @Autowired
    UserRepository userRepository;

    UserDetailsImpl userDetails;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public String changePassword(ChangePasswordRequest passwordRequest) {
        User user = userRepository.findById(getCurrentUserId()).get();

        boolean checkValidOldPassword = passwordEncoder.matches(passwordRequest.getOldPassword(), user.getPassword());
        if (!checkValidOldPassword) {
            return "Invalid password";
        }

        if (!(passwordRequest.getNewPassword()).equals(passwordRequest.getRenewPassword())) {
            return "Confirm password does not match";
        }

        user.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
        user.setLastModifiedDate(new Date());
        userRepository.save(user);
        return "Change password successfully";
    }

    public String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return ((UserDetailsImpl) authentication.getPrincipal()).getId();
    }
}
