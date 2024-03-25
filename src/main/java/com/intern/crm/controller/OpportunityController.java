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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    OpportunityServiceImpl opportunityServiceImpl;

    @Operation(summary = "Create an opportunity")
    @PostMapping("")
    public ResponseEntity<?> createOpportunity(
            @RequestBody CreateOpportunityRequest opportunityModel,
            @RequestParam(value = "stageId", required = false) String stageId,
            @RequestParam(value = "isCustomer", required = false, defaultValue = "false") Boolean isCustomer) {
        opportunityService.createOpportunity(opportunityModel, stageId, isCustomer);
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

    @Operation(summary = "Paging, Sort, Filter")
    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAllOpportunities(
            @RequestParam(required = false) String email, //filter
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "email") String sortBy //sort
    ) {
        try {
            List<Opportunity> opportunities = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size, Sort.by(sortBy).descending());

            Page<Opportunity> opportunityPage;
            opportunityPage = opportunityRepository.findAll(paging);
            if (email == null) {
                opportunityPage = opportunityRepository.findAll(paging);
            } else opportunityPage = opportunityRepository.findByEmailContaining(email, paging);

            opportunities = opportunityPage.getContent();

            List<OpportunityModel> opportunityModels = new ArrayList<>();
            for (Opportunity opportunity : opportunities) {
                opportunityModels.add(opportunityServiceImpl.setStageAndSalesperson(opportunity));
            }

            Map<String, Object> response = new HashMap<>();
            response.put("opportunities", opportunityModels);
            response.put("currentPage", opportunityPage.getNumber());
            response.put("totalItems", opportunityPage.getTotalElements());
            response.put("totalPages", opportunityPage.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
