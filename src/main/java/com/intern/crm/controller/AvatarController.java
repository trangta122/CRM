package com.intern.crm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.intern.crm.payload.response.MessageResponse;
import com.intern.crm.service.AvatarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin("*")
@Tag(name = "Avatar")
@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/avatars")
public class AvatarController {
    @Autowired
    AvatarService avatarService;
    @Operation(summary = "Upload avatar")
    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> uploadAvatar(@RequestPart MultipartFile file) throws IOException {
        avatarService.uploadAvatar(file);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Upload avatar successfully."));
    }
}

