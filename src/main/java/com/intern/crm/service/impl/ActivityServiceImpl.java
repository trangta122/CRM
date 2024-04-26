package com.intern.crm.service.impl;

import com.intern.crm.entity.Activity;
import com.intern.crm.entity.Opportunity;
import com.intern.crm.payload.model.ActivityModel;
import com.intern.crm.payload.request.CreateActivityRequest;
import com.intern.crm.repository.ActivityRepository;
import com.intern.crm.repository.OpportunityRepository;
import com.intern.crm.service.ActivityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    OpportunityRepository opportunityRepository;
    @Autowired
    ActivityRepository activityRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public void createActivity(String id, CreateActivityRequest request) {
        Opportunity opportunity = opportunityRepository.findById(id).get();
        Activity activity = modelMapper.map(request, Activity.class);
        activity.setOpportunity(opportunity);
        activityRepository.save(activity);
    }

    @Override
    public List<ActivityModel> listAllActivities() {
        List<Activity> activities = activityRepository.findAll();

        return listActivities(activities);
    }

    @Override
    public ActivityModel getActivityById(String id) {
        Activity activity = activityRepository.findById(id).get();
        ActivityModel activityModel = modelMapper.map(activity, ActivityModel.class);
        return activityModel;
    }

    @Override
    public List<ActivityModel> getAllActivitiesByOpportunityId(String id) {
        List<Activity> activities = activityRepository.findActivityByOpportunityId(id);

        return listActivities(activities);
    }

    @Override
    public Map<String, Object> getAllActivity(int page, int size, String sortBy) {
        List<Activity> activities = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size, Sort.by(sortBy).descending());

        Page<Activity> activityPage;
        activityPage = activityRepository.findAll(paging);

        activities = activityPage.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("activities", listActivities(activities));
        response.put("currentPage", activityPage.getNumber());
        response.put("totalItems", activityPage.getTotalElements());
        response.put("totalPages", activityPage.getTotalPages());

        return response;
    }

    @Override
    public ActivityModel editActivity(String id, ActivityModel model) {
        Activity activity = activityRepository.findById(id).get();

        activity.setDetail(model.getDetail());
        activity.setDate(model.getDate());
        activity.setLastModifiedDate(new Date());
        activityRepository.save(activity);
        ActivityModel activityModel = modelMapper.map(activity, ActivityModel.class);
        return activityModel;
    }

    public List<ActivityModel> listActivities(List<Activity> activities) {
        List<ActivityModel> activityModels = new ArrayList<>();
        for (Activity activity : activities) {
            ActivityModel activityModel = modelMapper.map(activity, ActivityModel.class);
            activityModels.add(activityModel);
        }

        return activityModels;
    }
}
