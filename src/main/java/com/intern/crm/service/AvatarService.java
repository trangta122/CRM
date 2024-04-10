package com.intern.crm.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AvatarService {
    //Upload avatar
    void uploadAvatar(MultipartFile file) throws IOException;
}
