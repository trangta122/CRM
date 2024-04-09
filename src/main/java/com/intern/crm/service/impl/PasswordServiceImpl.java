package com.intern.crm.service.impl;

import com.intern.crm.entity.User;
import com.intern.crm.payload.request.ChangePasswordRequest;
import com.intern.crm.payload.request.EmailRequest;
import com.intern.crm.payload.request.ForgotPasswordRequest;
import com.intern.crm.repository.UserRepository;
import com.intern.crm.security.service.UserDetailsImpl;
import com.intern.crm.service.EmailService;
import com.intern.crm.service.PasswordService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PasswordServiceImpl implements PasswordService {
    @Autowired
    UserRepository userRepository;
    UserDetailsImpl userDetails;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    EmailService emailService;

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

    @Override
    public String forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        //Check if email is not existed
        if (!userRepository.existsByEmail(forgotPasswordRequest.getEmail())) {
            return "Invalid email";
        }

        //Generate password
        String newPassword = RandomStringUtils.randomAscii(10);

        //Update user's password
        User user = userRepository.findByEmail(forgotPasswordRequest.getEmail());
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setLastModifiedDate(new Date());
        userRepository.save(user);

        //Send email with new password
        EmailRequest emailRequest = new EmailRequest(
                "ttt-batch15bd@sdc.edu.vn",
                "Verification email",
                "Please sign in with the new password below to complete reset password: " + newPassword,
                ""
        );

        emailService.sendSimpleEmail(emailRequest);

        return "Verification email is being sent...";
    }

    public String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return ((UserDetailsImpl) authentication.getPrincipal()).getId();
    }
}
