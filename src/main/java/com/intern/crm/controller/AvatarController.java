package com.intern.crm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.intern.crm.payload.response.MessageResponse;
import com.intern.crm.repository.AvatarRepository;
import com.intern.crm.service.AvatarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin("*")
@Tag(name = "Avatar")
@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/avatars")
public class AvatarController {
    @Autowired
    AvatarService avatarService;
    @Autowired
    AvatarRepository avatarRepository;
    @Operation(summary = "Upload avatar")
    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> uploadAvatar(@RequestPart MultipartFile file) throws IOException {
        avatarService.uploadAvatar(file);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Upload avatar successfully."));
    }

    @Operation(summary = "Display avatar by ID")
    @GetMapping(value = "/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> getImage(@PathVariable("id") String id) throws IOException {
        Path path = Paths.get(avatarRepository.findById(id).get().getPhysicalPath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + path.getFileName().toString())
                .body(resource);
    }
}

