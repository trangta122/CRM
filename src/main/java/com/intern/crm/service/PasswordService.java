package com.intern.crm.service;

import com.intern.crm.payload.request.ChangePasswordRequest;
import org.springframework.data.domain.PageRequest;

public interface PasswordService {
    //Change password
    String changePassword(ChangePasswordRequest passwordRequest);
}
