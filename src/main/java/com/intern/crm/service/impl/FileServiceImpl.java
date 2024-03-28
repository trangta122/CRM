package com.intern.crm.service.impl;

import com.intern.crm.payload.model.FileModel;
import com.intern.crm.repository.FileRepository;
import com.intern.crm.service.FileService;
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

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    FileRepository fileRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public void saveFile(MultipartFile file) {
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS_");
        String currentDateTime = dateFormatter.format(new Date());
        String path = currentDateTime + file.getOriginalFilename();
        saveAs(file, path );
        com.intern.crm.entity.File f = new com.intern.crm.entity.File();
        f.setName(file.getOriginalFilename());
        f.setType(file.getContentType());
        f.setPhysicalPath(path);
        fileRepository.save(f);
    }

    @Override
    public Resource downloadFile(String id) throws Exception {
        com.intern.crm.entity.File file = fileRepository.findById(id).orElseThrow(() -> new Exception("File not found"));
        return load(file.getPhysicalPath());
    }

    @Override
    public Map<String, Object> pagination(int page, int size, String sortBy) {
        List<com.intern.crm.entity.File> files = new ArrayList<>();

        Pageable paging = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        Page<com.intern.crm.entity.File> filePage;

        filePage = fileRepository.findAll(paging);
        files = filePage.getContent();

        List<FileModel> fileModels = new ArrayList<>();

        for (com.intern.crm.entity.File file : files) {
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
    public FileModel getFileById(String id) {
        com.intern.crm.entity.File file = fileRepository.findById(id).get();
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
            File directory = new File(convertRelativeToAbsolutePath(relativePath).getParent().toString());
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
        } else if (isUnix()) {
            return "/home/JavaRootPath/";
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
