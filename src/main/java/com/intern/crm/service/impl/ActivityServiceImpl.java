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
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    OpportunityRepository opportunityRepository;
    @Autowired
    ActivityRepository activityRepository;
    @Autowired
    ModelMapper modelMapper;
    DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");

    private Pageable createPageRequest(int page, int size) {
        return PageRequest.of(page, size);
    }
    @Override
    public void createActivity(String id, CreateActivityRequest request) {
        Opportunity opportunity = opportunityRepository.findById(id).get();
        Activity activity = modelMapper.map(request, Activity.class);

        String detail = "Planned activites | " + request.getType() + ": " + request.getSummary() + " on " + dateFormat.format(request.getDate());
        activity.setDetail(detail);
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
    public Page<ActivityModel> getScheduleActivityByOpportunityId(String id, int page, int size) {
        Pageable pageable = createPageRequest(page, size);

        List<ActivityModel> activities = listActivities(activityRepository.findScheduleActivityByOpportunityId(id));
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), activities.size());

        List<ActivityModel> pageContent = activities.subList(start, end);

        return new PageImpl<>(pageContent, pageable, activities.size());
    }

    @Override
    public Page<ActivityModel> getAutoActivityByOpportunityId(String id, int page, int size) {
        Pageable pageable = createPageRequest(page, size);
        List<ActivityModel> activityModels = listActivities(activityRepository.findAutoActivityByOpportunityId(id));
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), activityModels.size());

        List<ActivityModel> pageContent = activityModels.subList(start, end);

        return new PageImpl<>(pageContent, pageable, activityModels.size());
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
    public ActivityModel editActivity(String id, ActivityModel activityModel) {
        Activity activity = activityRepository.findById(id).get();
        if (activity.getType() != activityModel.getType()
                || activity.getSummary() != activityModel.getSummary()
                || activity.getDate() != activityModel.getDate()
                || activity.isDone() != activityModel.isDone())
        {
            activity.setType(activityModel.getType());
            activity.setSummary(activityModel.getSummary());
            activity.setDate(activityModel.getDate());
            activity.setDetail("Planned activites | " + activity.getType() + ": " + activity.getSummary() + " on " + dateFormat.format(activity.getDate()));
            activity.setDone(activityModel.isDone());
        }

        activity.setLastModifiedDate(new Date());
        activityRepository.save(activity);

        return modelMapper.map(activity, ActivityModel.class);
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
