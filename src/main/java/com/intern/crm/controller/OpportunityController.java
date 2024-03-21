package com.intern.crm.controller;

import com.intern.crm.entity.Opportunity;
import com.intern.crm.helper.ExcelHelper;
import com.intern.crm.payload.model.OpportunityModel;
import com.intern.crm.payload.response.MessageResponse;
import com.intern.crm.repository.OpportunityRepository;
import com.intern.crm.repository.StageRepository;
import com.intern.crm.service.OpportunityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;

@Tag(name = "Opportunity", description = "Opportunity Management APIs")
//@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/opportunity")
public class OpportunityController {
    @Autowired
    OpportunityRepository opportunityRepository;
    @Autowired
    StageRepository stageRepository;
    @Autowired
    OpportunityService opportunityService;
    @Operation(summary = "Create an opportunity")
    @PostMapping("")
    public ResponseEntity<?> createAnOpportunity(@RequestBody OpportunityModel opportunityModel, @RequestParam(value = "stageId") String stageId) {
        opportunityService.savedOpportunity(opportunityModel, stageId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Create an opportunity successfully."));
    }

    @Operation(summary = "Import opportunities from Excel file")
    @PostMapping(value = "/excel", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> uploadFile(@RequestPart MultipartFile file) {
        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                opportunityService.importExcel(file);
                return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("The Excel file is uploaded: " + file.getOriginalFilename()));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse("The Excel file is not uploaded: " + file.getOriginalFilename()));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Please upload another Excel file!"));
    }
}
