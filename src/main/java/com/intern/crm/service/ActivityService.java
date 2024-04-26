package com.intern.crm.service;

import com.intern.crm.entity.Activity;
import com.intern.crm.payload.model.ActivityModel;
import com.intern.crm.payload.request.CreateActivityRequest;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ActivityService {
    //Add an activity for an opportunity by Opportunity's ID
    void createActivity(String id, CreateActivityRequest request);

    //Retrieve all activities
    List<ActivityModel> listAllActivities();

    //Retrieve an activity by Activity's ID
    ActivityModel getActivityById(String id);

    //Retrieve all activities of an opportunity by opportunity's ID
    List<ActivityModel> getAllActivitiesByOpportunityId(String id);

    //Paging, sort
    Map<String, Object> getAllActivity( int page, int size, String sortBy);

    //Update activity
    ActivityModel editActivity(String id, ActivityModel model);
}
