package com.intern.crm.service.impl;

import com.intern.crm.controller.TemplateFileController;
import com.intern.crm.entity.TemplateFile;
import com.intern.crm.payload.model.FileModel;
import com.intern.crm.repository.TemplateFileRepository;
import com.intern.crm.service.TemplateFileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TemplateFileServiceImpl implements TemplateFileService {
    @Autowired
    TemplateFileRepository fileRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public FileModel saveTemplate(MultipartFile file) {
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS_");
        String currentDateTime = dateFormatter.format(new Date());
        String path = currentDateTime + file.getOriginalFilename();
        saveAs(file, path ); //save raw file to server

        TemplateFile template = new TemplateFile();
        template.setName(path);
        template.setType(file.getContentType());
        template.setPhysicalPath("uploads/" + path);
        template.setTemplate(true);
        fileRepository.save(template); //save ìnformation of file (name, type, path) to database

        return modelMapper.map(template, FileModel.class);
    }

    @Override
    public Resource downloadFile(String id) throws Exception {
        TemplateFile file = fileRepository.findById(id).orElseThrow(() -> new Exception("File not found"));
        return load(file.getName());
    }

    public Resource load(String filename) {
        try {
            Path file = convertRelativeToAbsolutePath(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public void saveAs(MultipartFile file, String relativePath) {
        try {
            java.io.File directory = new java.io.File(convertRelativeToAbsolutePath(relativePath).getParent().toString());
            if (! directory.exists()){
                directory.mkdirs();
            }

            Files.copy(file.getInputStream(), convertRelativeToAbsolutePath(relativePath));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    private String getRootPath() throws Exception {
        if(isWindows()) {
            return "uploads/";
        } else {
            throw new Exception("Cannot set root path because of Unknow OS");
        }
    }

    private Path convertRelativeToAbsolutePath(String relativePath) throws Exception {
        return Paths.get(getRootPath() + relativePath);
    }

    private static String OS = System.getProperty("os.name").toLowerCase();
    private boolean isWindows() {
        return OS.contains("windows");
    }
    private boolean isMac() {
        return OS.contains("mac");
    }
    private boolean isUnix() {
        return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
    }
    private boolean isSolaris() {
        return OS.contains("sunos");
    }
}
