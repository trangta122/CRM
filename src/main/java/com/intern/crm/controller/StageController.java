package com.intern.crm.controller;

import com.intern.crm.entity.Stage;
import com.intern.crm.payload.model.StageModel;
import com.intern.crm.payload.response.MessageResponse;
import com.intern.crm.repository.StageRepository;
import com.intern.crm.service.StageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stage")
@SecurityRequirement(name = "Authorization")
@Tag(name = "Stage", description = "Stage Management APIs")
public class StageController {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    StageService stageService;
    @Autowired
    StageRepository stageRepository;

    @Operation(summary = "ADMIN: Create a new stage")
    @PostMapping("")
    public ResponseEntity<?> createStage(@RequestBody StageModel stageModel) {
        Stage stage = modelMapper.map(stageModel, Stage.class);
        stageRepository.save(stage);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Create a stage successfully."));
    }

    @Operation(summary = "ADMIN: Get all stages")
    @GetMapping("/all")
    public ResponseEntity<List<StageModel>> getAllStages() {
        List<StageModel> stageList = stageService.listAllStages();
        return ResponseEntity.status(HttpStatus.OK).body(stageList);
    }

    @Operation(summary = "ADMIN: Retrieve a stage by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<StageModel>> getStageById(@PathVariable("id") String id) {
        return new ResponseEntity<>(stageService.getStageById(id), HttpStatus.OK);
    }

    @Operation(summary = "ADMIN: Edit a stage")
    @PutMapping("/{id}")
    public ResponseEntity<StageModel> updateStage(@PathVariable("id") String id, @RequestBody StageModel stageModel) {
        return new ResponseEntity<StageModel>(stageService.editStage(id, stageModel), HttpStatus.OK);
    }
}
