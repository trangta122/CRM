package com.intern.crm.controller;

import com.intern.crm.entity.Activity;
import com.intern.crm.payload.model.ActivityModel;
import com.intern.crm.payload.request.CreateActivityRequest;
import com.intern.crm.payload.response.MessageResponse;
import com.intern.crm.repository.ActivityRepository;
import com.intern.crm.service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*")
@Tag(name = "Activity", description = "Activity Management APIs")
@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/activities")
public class ActivityController {
    @Autowired
    ActivityService activityService;
    @Autowired
    ActivityRepository activityRepository;

    @GetMapping("/schedule/{id}")
    public ResponseEntity<?> getSchedule(@PathVariable("id") String id) {
        List<Activity> activities = activityRepository.findScheduleActivityByOpportunityId(id);

        return ResponseEntity.ok().body(activities);
    }

    @GetMapping("/auto/{id}")
    public ResponseEntity<?> getAuto(@PathVariable("id") String id) {
        List<Activity> activities = activityRepository.findAutoActivityByOpportunityId(id);
        return ResponseEntity.ok().body(activities);
    }

    @Operation(summary = "Add an activity for an opportunity")
    @PostMapping("/opportunity/{id}")
    public ResponseEntity<?> addActivity(@PathVariable("id") String id, @RequestBody CreateActivityRequest request){
        activityService.createActivity(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Create a planned activity successfully."));
    }

    @Operation(summary = "Retrieve all activities")
    @GetMapping("/all")
    public ResponseEntity<?> findAllActivities() {
        return ResponseEntity.status(HttpStatus.OK).body(activityService.listAllActivities());
    }

    @Operation(summary = "Retrieve an activity by ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getActivityById(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(activityService.getActivityById(id));
    }

    @Operation(summary = "Retrieve all activities of an opportunity")
    @GetMapping("/opportunity/{id}")
    public ResponseEntity<?> getAllActivitiesByOpportunityId(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(activityService.getAllActivitiesByOpportunityId(id));
    }

    @Operation(summary = "Pagination")
    @GetMapping("")
    public ResponseEntity<?> getAllActivities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "date") String sortBy
            ) {
        return ResponseEntity.status(HttpStatus.OK).body(activityService.getAllActivity(page, size, sortBy));
    }

    @Operation(summary = "Update activity by ID")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateActivity(@PathVariable("id") String id, @RequestBody ActivityModel request) {
        return ResponseEntity.status(HttpStatus.OK).body(activityService.editActivity(id, request));
    }
}
