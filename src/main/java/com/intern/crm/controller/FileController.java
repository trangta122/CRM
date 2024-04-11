package com.intern.crm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.intern.crm.payload.response.MessageResponse;
import com.intern.crm.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@CrossOrigin(origins = "*")
@Tag(name = "File")
@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/files")
public class FileController {
    @Autowired
    FileService fileService;

    @Operation(summary = "Upload a file")
    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> uploadFile(@RequestPart MultipartFile file) throws JsonMappingException, JsonProcessingException {
        fileService.saveFile(file);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Upload successfully."));
    }

    @Operation(summary = "Download a file by File's ID")
    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable("id") String id) throws Exception {
        Resource file = fileService.downloadFile(id);
        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + file.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(file);
    }

    @Operation(summary = "Pagination")
    @GetMapping("")
    public ResponseEntity<?> pagingFile(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "name") String sortBy) {
        return ResponseEntity.status(HttpStatus.OK).body(fileService.pagination(page, size, sortBy));
    }

    @Operation(summary = "Get a file by File's ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getFileById(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(fileService.getFileById(id));
    }
}
