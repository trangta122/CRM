package com.intern.crm.service;

import com.intern.crm.payload.model.FileModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AvatarService {
    //Upload avatar
    void uploadAvatar(MultipartFile file) throws IOException;

    //Get avatar by ID
    FileModel getAvatarById(String id);
}
