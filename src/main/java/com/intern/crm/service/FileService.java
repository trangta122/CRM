package com.intern.crm.service;

import com.intern.crm.entity.File;
import com.intern.crm.payload.model.FileModel;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Stream;

public interface FileService {
    //Upload a file
    void saveFile(MultipartFile file);

    //Download a file
    Resource downloadFile(String id) throws Exception;

    //Pagination
    Map<String, Object> pagination(int page, int size, String sortBy);

    //Get a file by File's ID
    FileModel getFileById(String id);
}
