package com.intern.crm.service;

import com.intern.crm.payload.model.FileModel;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.Map;

public interface TemplateFileService {
    //Upload a file
    String saveFile(MultipartFile file, boolean isFile);

    //Download a file
    Resource downloadFile(String id) throws Exception;

    //Pagination
    Map<String, Object> pagination(int page, int size, String sortBy);

    //Get a file by File's ID
    FileModel getFileById(String id);

}
