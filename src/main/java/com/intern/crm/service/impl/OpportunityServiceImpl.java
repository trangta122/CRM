package com.intern.crm.service.impl;

import com.intern.crm.entity.Activity;
import com.intern.crm.entity.Opportunity;
import com.intern.crm.entity.Stage;
import com.intern.crm.entity.User;
import com.intern.crm.helper.ExcelHelper;
import com.intern.crm.payload.model.OpportunityModel;
import com.intern.crm.payload.model.UserModel;
import com.intern.crm.payload.request.CreateOpportunityRequest;
import com.intern.crm.repository.ActivityRepository;
import com.intern.crm.repository.OpportunityRepository;
import com.intern.crm.repository.StageRepository;
import com.intern.crm.repository.UserRepository;
import com.intern.crm.service.OpportunityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OpportunityServiceImpl implements OpportunityService {
    @Autowired
    OpportunityRepository opportunityRepository;
    @Autowired
    StageRepository stageRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ActivityRepository activityRepository;
    @Autowired
    ModelMapper modelMapper;
    @Value("${crm.app.lost}")
    private String lostId;
    @Value(("${crm.app.stage}"))
    private String stageId;
    DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");

    @Override
    public Opportunity createOpportunity(CreateOpportunityRequest opportunityRequest) {
        Opportunity opportunity = modelMapper.map(opportunityRequest, Opportunity.class);

        if (opportunity.getName() == null || opportunity.getName() == "") {
            opportunity.setName(opportunity.getCompany());
        }

        opportunity.setCustomer(opportunityRequest.isCustomer());
        opportunity.setProbability((float) 0);

        if (opportunityRequest.getStageId() == null  || opportunityRequest.getStageId() == "") {
            opportunity.setStage(stageRepository.findById(stageId).get());
        } else opportunity.setStage(stageRepository.findById(opportunityRequest.getStageId()).get());


        if (opportunityRequest.getRevenue() != 0) {
            Stage stage = stageRepository.findById(opportunityRequest.getStageId()).get();
            stage.setRevenue(stage.getRevenue() + opportunityRequest.getRevenue());
            stageRepository.save(stage);
        }

        return opportunityRepository.save(opportunity);
    }

    @Override
    public void importExcel(MultipartFile file) {
        try {
            List<Opportunity> opportunities = ExcelHelper.excelToOpportunities(file.getInputStream());

            for (Opportunity opportunity : opportunities) {
                opportunity.setStage(stageRepository.findById(stageId).get());
                opportunity.setRevenue((double) 0);
                opportunity.setProbability((float) 0);
                opportunity.setCustomer(false);
                opportunityRepository.save(opportunity);
            }

        } catch (IOException e) {
            throw new RuntimeException("Import Excel data is failed to store: " + e.getMessage());
        }
    }

    @Override
    public List<OpportunityModel> listAllOpportunities() {
        List<Opportunity> opportunities = opportunityRepository.findAll();
        List<OpportunityModel> opportunityModels = new ArrayList<>();

        for (Opportunity opportunity : opportunities) {
            opportunityModels.add(mapOpportunity(opportunity));
        }
        return opportunityModels;
    }

    @Override
    public OpportunityModel findOpportunityById(String id) {
        Opportunity opportunity = opportunityRepository.findById(id).get();
        return mapOpportunity(opportunity);
    }

    @Override
    public OpportunityModel updateOpportunity(OpportunityModel opportunityModel, String opportunityId, String stageId) {
        String detail;
        Opportunity opportunity = opportunityRepository.findById(opportunityId).get();

        //Log stage changed
        if (stageId != null) {
            if (!(opportunity.getStage().getId()).equals(stageId)) {
                Stage prevStage = opportunity.getStage();
                Stage nextStage = stageRepository.findById(stageId).get();

                detail = "Stage changed: " + prevStage.getName() + " -> " + nextStage.getName();
                saveActivity(opportunityId, "Auto", detail);

                //if opportunity change stage, remove its revenue, move it to next stage
                prevStage.setRevenue(prevStage.getRevenue() - opportunity.getRevenue());
                stageRepository.save(prevStage);

                nextStage.setRevenue(nextStage.getRevenue() + opportunity.getRevenue());
                stageRepository.save(nextStage);

                opportunity.setStage(nextStage);
            }
        }

        //Log expected revenue changed
        if (!opportunity.getRevenue().equals(opportunityModel.getRevenue()) ) {
            detail = "Expected revenue changed: " + new DecimalFormat("0").format(opportunity.getRevenue()) + " VND"  + " -> " + new DecimalFormat("0").format(opportunityModel.getRevenue()) + " VND";
            saveActivity(opportunityId, "Auto", detail);
        }

        //Log probability changed
        if (!opportunity.getProbability().equals(opportunityModel.getProbability())) {
            detail = "Probability changed: " + opportunity.getProbability() + " %" + " -> " + opportunityModel.getProbability() + " %";
            saveActivity(opportunityId, "Auto", detail);
        }

        //Update stage's revenue
        Stage stage = stageRepository.findById(opportunity.getStage().getId()).get();
        stage.setRevenue(stage.getRevenue() + opportunityModel.getRevenue() - opportunity.getRevenue());
        stage.setLastModifiedDate(new Date());
        stageRepository.save(stage);

        opportunity.setName(opportunityModel.getName());
        opportunity.setEmail(opportunityModel.getEmail());
        opportunity.setPhone(opportunityModel.getPhone());
        opportunity.setAddress(opportunityModel.getAddress());
        opportunity.setWebsite(opportunityModel.getWebsite());
        opportunity.setDescription(opportunityModel.getDescription());
        opportunity.setRevenue(opportunityModel.getRevenue());
        opportunity.setPriority(opportunityModel.getPriority());
        opportunity.setCompany(opportunityModel.getCompany());
        opportunity.setProbability(opportunityModel.getProbability());
        opportunity.setLostReason(opportunityModel.getLostReason());
        opportunity.setLastModifiedDate(new Date());

        opportunityRepository.save(opportunity);

        return mapOpportunity(opportunity);
    }

    @Override
    public OpportunityModel assignSalesperson(String opportunityId, String userId) {
        Opportunity opportunity = opportunityRepository.findById(opportunityId).get();
        User user = userRepository.findById(userId).get();
        String detail;

        //Log salesperson changed
        if (opportunity.getSalesperson() == null) {
            detail = "Salesperson changed: None -> " + user.getFullname();
            saveActivity(opportunityId, "Auto", detail);
        }
        if (opportunity.getSalesperson() != null && !(opportunity.getSalesperson().getId()).equals(user.getId())) {
            detail = "Salesperson changed: " + opportunity.getSalesperson().getFullname() + " -> " + user.getFullname();
            saveActivity(opportunityId, "Auto",detail);
        }

        opportunity.setSalesperson(user);
        opportunity.setLastModifiedDate(new Date());
        opportunityRepository.save(opportunity);
        return mapOpportunity(opportunity);
    }

    @Override
    public List<OpportunityModel> findOpportunityByContactId(String id) {
        List<Opportunity> opportunities = opportunityRepository.findOpportunityByContactsId(id);
        List<OpportunityModel> opportunityModels = new ArrayList<>();

        for (Opportunity opportunity : opportunities) {
            opportunityModels.add(mapOpportunity(opportunity));
        }
        return opportunityModels;
    }

    @Override
    public Map<String, Object> pagingOpportunity(int page, int size, String sortBy) {
        List<Opportunity> opportunities = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        Page<Opportunity> opportunityPage;
        opportunityPage = opportunityRepository.findAll(paging);

        opportunities = opportunityPage.getContent();

        List<OpportunityModel> opportunityModels = new ArrayList<>();
        for (Opportunity opportunity : opportunities) {
            opportunityModels.add(mapOpportunity(opportunity));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("opportunities", opportunityModels);
        response.put("currentPage", opportunityPage.getNumber());
        response.put("totalItems", opportunityPage.getTotalElements());
        response.put("totalPages", opportunityPage.getTotalPages());

        return response;
    }


    public OpportunityModel mapOpportunity(Opportunity o) {
        OpportunityModel opportunityModel = modelMapper.map(o, OpportunityModel.class);
        if (o.getSalesperson() != null) {
            User user = userRepository.findById(o.getSalesperson().getId()).get();
            UserModel userModel = modelMapper.map(user, UserModel.class);

            //Set list roles for User model
            userModel.setRole(user.getRoles().stream().map(e -> modelMapper.map(e.getName(), String.class)).collect(Collectors.toList()));

            opportunityModel.setSalesperson(userModel);

        }
        return opportunityModel;
    }

    public void saveActivity(String opporrtunityId, String type ,String detail) {
        Activity activity = new Activity(type, detail, new Date(), false);
        activity.setOpportunity(opportunityRepository.findById(opporrtunityId).get());
        activityRepository.save(activity);
    }
}
