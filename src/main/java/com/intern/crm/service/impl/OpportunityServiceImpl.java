package com.intern.crm.service.impl;

import com.intern.crm.entity.Activity;
import com.intern.crm.entity.Opportunity;
import com.intern.crm.entity.Stage;
import com.intern.crm.entity.User;
import com.intern.crm.helper.ExcelHelper;
import com.intern.crm.payload.model.FileModel;
import com.intern.crm.payload.model.OpportunityModel;
import com.intern.crm.payload.model.StageModel;
import com.intern.crm.payload.model.UserModel;
import com.intern.crm.payload.request.CreateOpportunityRequest;
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
    ModelMapper modelMapper;
    @Value("${crm.app.lost}")
    private String lostId;
    DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
    @Override
    public Opportunity createOpportunity(CreateOpportunityRequest opportunityModel, String stageId, Boolean isCustomer) {
        Stage stage = stageRepository.findById(stageId).get();
        Opportunity opportunity = modelMapper.map(opportunityModel, Opportunity.class);
        opportunity.setStage(stage);
        opportunity.setCustomer(isCustomer);
        opportunity.setProbability((float) 0);
        return opportunityRepository.save(opportunity);
    }

    @Override
    public void importExcel(MultipartFile file) {
        try {
            List<Opportunity> opportunityList = ExcelHelper.excelToOpportunities(file.getInputStream());

            opportunityRepository.saveAll(opportunityList);

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
    public OpportunityModel updateOpportunity(OpportunityModel opportunityModel, String opportunityId) {
        String detail;
        Opportunity opportunity = opportunityRepository.findById(opportunityId).get();

        //Update stage's revenue
        Stage stage = stageRepository.findById(opportunity.getStage().getId()).get();
        stage.setRevenue(stage.getRevenue() + opportunityModel.getRevenue() - opportunity.getRevenue());
        stage.setLastModifiedDate(new Date());
        stageRepository.save(stage);

        //Log stage changed
        if (opportunity.getStage().getName() != opportunityModel.getStage().getName()) {
            detail = "Stage changed: " + opportunity.getStage().getName() + " -> " + opportunityModel.getStage().getName();
            Activity activity = new Activity("Auto-log", detail, new Date(), false);
        }

        //Log expected revenue changed
        if (opportunity.getRevenue() != opportunityModel.getRevenue()) {
            detail = "Expected revenue changed " + opportunity.getRevenue()  + " -> " + opportunityModel.getRevenue() + "VND";
            Activity activity = new Activity("Auto-log", detail, new Date(), false);
        }

        //Log probability changed
        if (opportunity.getProbability() != opportunityModel.getProbability()) {
            detail = "Probability changed: " + opportunity.getProbability() + " -> " + opportunityModel.getProbability();
            Activity activity = new Activity("Auto-log", detail, new Date(), false);
        }

        //Log salesperson changed
        if (opportunity.getSalesperson() == null) {
            detail = "Salesperson changed: None -> " + opportunityModel.getSalesperson().getFullname();
            Activity activity = new Activity("Auto-log", detail, new Date(), false);
        } else if (opportunity.getSalesperson().getId() != opportunityModel.getSalesperson().getId()) {
            detail = "Salesperson changed: " + opportunity.getSalesperson().getFullname() + " -> " + opportunityModel.getSalesperson().getFullname();
            Activity activity = new Activity("Auto-log", detail, new Date(), false);
        }

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

        //set stage
        opportunity.setStage(stageRepository.findById(opportunityModel.getStage().getId()).get());

        opportunityRepository.save(opportunity);

        return mapOpportunity(opportunity);
    }

    @Override
    public OpportunityModel assignSalesperson(String opportunityId, String userId) {
        Opportunity opportunity = opportunityRepository.findById(opportunityId).get();
        opportunity.setSalesperson(userRepository.findById(userId).get());
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
}
