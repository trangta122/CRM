package com.intern.crm.controller;

import com.intern.crm.entity.Opportunity;
import com.intern.crm.helper.ExcelHelper;
import com.intern.crm.payload.model.OpportunityModel;
import com.intern.crm.payload.request.CreateOpportunityRequest;
import com.intern.crm.payload.response.MessageResponse;
import com.intern.crm.repository.OpportunityRepository;
import com.intern.crm.repository.StageRepository;
import com.intern.crm.service.OpportunityService;
import com.intern.crm.service.impl.OpportunityServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@Tag(name = "Opportunity", description = "Opportunity Management APIs")
@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/opportunities")
public class OpportunityController {
    @Autowired
    OpportunityRepository opportunityRepository;
    @Autowired
    StageRepository stageRepository;
    @Autowired
    OpportunityService opportunityService;

    @Operation(summary = "Create an opportunity")
    @PostMapping("")
    public ResponseEntity<?> createOpportunity(
            @RequestBody CreateOpportunityRequest opportunityModel) {
        opportunityService.createOpportunity(opportunityModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Create an opportunity successfully."));
    }

    @Operation(summary = "Import opportunities from Excel file")
    @PostMapping(value = "/excel", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> uploadFile(@RequestPart MultipartFile file) {
        if (ExcelHelper.hasExcelFormat(file)) {
                opportunityService.importExcel(file);
                return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("The Excel file is uploaded: " + file.getOriginalFilename()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Please upload another Excel file!"));
    }

    @Operation(summary = "Find all opportunities")
    @GetMapping("/all")
    public ResponseEntity<List<OpportunityModel>> listAllOpportunities() {
        List<OpportunityModel> opportunityModelList = opportunityService.listAllOpportunities();
        return ResponseEntity.status(HttpStatus.OK).body(opportunityModelList);
    }

    @Operation(summary = "Get an opportunity by ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getAnOpportunityById(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(opportunityService.findOpportunityById(id));
    }

    @Operation(summary = "Pagination")
    @GetMapping("")
    public ResponseEntity<?> getAllOpportunities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortBy
    ) {

        return ResponseEntity.status(HttpStatus.OK).body(opportunityService.pagingOpportunity(page, size, sortBy));
    }

    @Operation(summary = "Edit information, convert stage")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOpportunity(
            @PathVariable("id") String opportunityId,
            @RequestParam(value = "stageId", required = false) String stageId,
            @RequestBody OpportunityModel opportunityModel) {
        return ResponseEntity.status(HttpStatus.OK).body(opportunityService.updateOpportunity(opportunityModel, opportunityId, stageId));
    }

    @Operation(summary = "ADMIN: Assign salesperson")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{opportunityId}/salesperson/{salespersonId}")
    public ResponseEntity<?> assignSalesperson(
            @PathVariable("opportunityId") String id1,
            @PathVariable("salespersonId") String id2
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(opportunityService.assignSalesperson(id1, id2));
    }

    @Operation(summary = "Retrieve all opportunities of an contact")
    @GetMapping("/contact/{id}")
    public ResponseEntity<?> getAllOpportunitiesByContactId(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(opportunityService.findOpportunityByContactId(id));
    }
}
