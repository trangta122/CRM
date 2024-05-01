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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TempalteFileServiceImpl implements TemplateFileService {
    @Autowired
    TemplateFileRepository fileRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public Map<String, Object> pagination(int page, int size, String sortBy) {
        List<TemplateFile> files = new ArrayList<>();

        Pageable paging = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        Page<TemplateFile> filePage;

        filePage = fileRepository.findAll(paging);
        files = filePage.getContent();

        List<FileModel> fileModels = new ArrayList<>();

        for (TemplateFile file : files) {
            FileModel fileModel = modelMapper.map(file, FileModel.class);
            fileModels.add(fileModel);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("contacts", fileModels);
        response.put("currentPage", filePage.getNumber());
        response.put("totalItems", filePage.getTotalElements());
        response.put("totalPages", filePage.getTotalPages());

        return response;
    }

    @Override
    public String saveFile(MultipartFile file, boolean isFile) {
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS_");
        String currentDateTime = dateFormatter.format(new Date());
        String path = currentDateTime + file.getOriginalFilename();
        saveAs(file, path ); //save raw file to server

        TemplateFile f = new TemplateFile();
        f.setName(path);
        f.setType(file.getContentType());
        f.setPhysicalPath("/uploads/" + path);
        f.setIsFile(isFile);
        fileRepository.save(f); //save ìnformation of file (name, type, path) to database
        String url = MvcUriComponentsBuilder.fromMethodName(
                TemplateFileController.class, "downloadFile", f.getId().toString()).build().toString();
        String downloadLink = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/download/")
                .path(f.getId())
                .toUriString();
        return "Upload file successfully.";
    }

    @Override
    public Resource downloadFile(String id) throws Exception {
        TemplateFile file = fileRepository.findById(id).orElseThrow(() -> new Exception("File not found"));
        return load(file.getName());
    }

    @Override
    public FileModel getFileById(String id) {
        TemplateFile file = fileRepository.findById(id).get();
        FileModel fileModel = modelMapper.map(file, FileModel.class);
        return fileModel;
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
