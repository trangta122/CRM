package com.intern.crm.service;

import com.intern.crm.payload.model.FileModel;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.Map;

public interface TemplateFileService {
    //Upload a file
    FileModel saveTemplate(MultipartFile file);

    //Download a file
    Resource downloadFile(String id) throws Exception;

}
