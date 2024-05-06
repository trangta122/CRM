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
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
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

    @Operation(summary = "Retrieve all planned activities of an opportunity by Opportunity's ID")
    @GetMapping("/schedule/{id}")
    public ResponseEntity<Page<ActivityModel>> getSchedule(@PathVariable("id") String id,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "5") int size) {
        Page<ActivityModel> activityPage = activityService.getScheduleActivityByOpportunityId(id, page, size);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Page-Number", String.valueOf(activityPage.getNumber()));
        headers.add("X-Page-Size", String.valueOf(activityPage.getSize()));
        return ResponseEntity.ok()
                .headers(headers)
                .body(activityPage);
    }
    @Operation(summary = "Retrieve all done planned activities and auto-log activity of an opportunity by Opportunity's ID")
    @GetMapping("/auto/{id}")
    public ResponseEntity<?> getAuto(@PathVariable("id") String id,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "5") int size) {

        Page<ActivityModel> activityPage = activityService.getAutoActivityByOpportunityId(id, page, size);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Page-Number", String.valueOf(activityPage.getNumber()));
        headers.add("X-Page-Size", String.valueOf(activityPage.getSize()));
        return ResponseEntity.ok()
                .headers(headers)
                .body(activityPage);
    }

    @Operation(summary = "Add an activity for an opportunity by Opportunity's ID")
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

    @Operation(summary = "Retrieve an activity by  activity's ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getActivityById(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(activityService.getActivityById(id));
    }

    @Operation(summary = "Retrieve all activities of an opportunity by Opportunity's ID")
    @GetMapping("/opportunity/{id}")
    public ResponseEntity<?> getAllActivitiesByOpportunityId(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(activityService.getAllActivitiesByOpportunityId(id));
    }

//    @Operation(summary = "Pagination")
//    @GetMapping("")
//    public ResponseEntity<?> getAllActivities(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "3") int size,
//            @RequestParam(defaultValue = "date") String sortBy
//            ) {
//        return ResponseEntity.status(HttpStatus.OK).body(activityService.getAllActivity(page, size, sortBy));
//    }

    @Operation(summary = "Update an activity by activity's ID")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateActivity(@PathVariable("id") String id, @RequestBody ActivityModel request) {
        return ResponseEntity.status(HttpStatus.OK).body(activityService.editActivity(id, request));
    }
}
