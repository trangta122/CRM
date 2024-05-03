package com.intern.crm.service.impl;

import com.intern.crm.audit.AuditorAwareImpl;
import com.intern.crm.entity.Avatar;
import com.intern.crm.entity.User;
import com.intern.crm.payload.model.FileModel;
import com.intern.crm.repository.AvatarRepository;
import com.intern.crm.repository.UserRepository;
import com.intern.crm.service.AvatarService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class AvatarServiceImpl implements AvatarService {
    @Autowired
    AvatarRepository avatarRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordServiceImpl passwordService;
    @Autowired
    ModelMapper modelMapper;
    @Value("${spring.default.avatar}")
    private String defaultAvatar;
    @Override
    public void uploadAvatar(MultipartFile file) throws IOException {
        //Upload avatar
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS_");
        String currentDateTime = dateFormatter.format(new Date());
        String path = currentDateTime + file.getOriginalFilename();
        saveAs(file, path);

        Avatar avatar = new Avatar();
        avatar.setName(file.getOriginalFilename());
        avatar.setType(file.getContentType());
        avatar.setPhysicalPath("uploads/avatars/" + path);
        avatarRepository.save(avatar);

        //Update table user to save new avatar and delete previous avatar
        User user = userRepository.findById(passwordService.getCurrentUserId()).get();

        String prevAvatar = user.getAvatar().getId();
        String prevPath = user.getAvatar().getPhysicalPath();

        user.setAvatar(avatar);

        if (!prevAvatar.equals(defaultAvatar)) {
            Path path1 = Paths.get(prevPath);
            Files.delete(path1); //delete raw file on server
            avatarRepository.deleteById(prevAvatar); //delete on database
        }

        userRepository.save(user);
    }

    @Override
    public FileModel getAvatarById(String id) {
        Avatar avatar = avatarRepository.findById(id).get();
        FileModel model = modelMapper.map(avatar, FileModel.class);
        return model;
    }

    public void saveAs(MultipartFile file, String relativePath) {
        try {
            File directory = new File(convertRelativeToAbsolutePath(relativePath).getParent().toString());
            if (! directory.exists()){
                directory.mkdirs();
            }

            Files.copy(file.getInputStream(), convertRelativeToAbsolutePath(relativePath));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }
    private Path convertRelativeToAbsolutePath(String relativePath) throws Exception {
        return Paths.get(getRootPath() + relativePath);
    }

    private String getRootPath() throws Exception {
        if(isWindows()) {
            return "uploads/avatars/";
        } else {
            throw new Exception("Cannot set root path because of Unknow OS");
        }
    }

    private static String OS = System.getProperty("os.name").toLowerCase();
    private boolean isWindows() {
        return OS.contains("windows");
    }
}
