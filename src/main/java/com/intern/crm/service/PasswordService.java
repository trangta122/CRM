package com.intern.crm.service;

import com.intern.crm.payload.request.ChangePasswordRequest;
import com.intern.crm.payload.request.ForgotPasswordRequest;
import org.springframework.data.domain.PageRequest;

public interface PasswordService {
    //Change password
    String changePassword(ChangePasswordRequest passwordRequest);

    //Forgot password
    String forgotPassword(ForgotPasswordRequest forgotPasswordRequest);
}
