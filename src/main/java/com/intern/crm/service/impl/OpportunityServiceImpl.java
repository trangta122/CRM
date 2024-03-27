package com.intern.crm.service.impl;

import com.intern.crm.entity.Opportunity;
import com.intern.crm.entity.Stage;
import com.intern.crm.helper.ExcelHelper;
import com.intern.crm.payload.model.OpportunityModel;
import com.intern.crm.payload.request.CreateOpportunityRequest;
import com.intern.crm.repository.OpportunityRepository;
import com.intern.crm.repository.StageRepository;
import com.intern.crm.repository.UserRepository;
import com.intern.crm.service.OpportunityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Override
    public Opportunity createOpportunity(CreateOpportunityRequest opportunityModel, String stageId, Boolean isCustomer) {
        Stage stage = stageRepository.findById(stageId).get();
        Opportunity opportunity = modelMapper.map(opportunityModel, Opportunity.class);
        opportunity.setStage(stage);
        opportunity.setCustomer(isCustomer);
        return opportunityRepository.save(opportunity);
    }

    @Override
    public void importExcel(MultipartFile file) {
        try {
            List<Opportunity> opportunityList = ExcelHelper.excelToOpportunities(file.getInputStream());
            int a = 1;
            opportunityRepository.saveAll(opportunityList);
            int b =2;
        } catch (IOException e) {
            throw new RuntimeException("Import Excel data is failed to store: " + e.getMessage());
        }
    }

    @Override
    public List<OpportunityModel> listAllOpportunities() {
        List<Opportunity> opportunities = opportunityRepository.findAll();
        List<OpportunityModel> opportunityModels = new ArrayList<>();

        for (Opportunity opportunity : opportunities) {
            opportunityModels.add(setStageAndSalesperson(opportunity));
        }
        return opportunityModels;
    }

    @Override
    public OpportunityModel findOpportunityById(String id) {
        Opportunity opportunity = opportunityRepository.findById(id).get();
        return setStageAndSalesperson(opportunity);
    }

    @Override
    public OpportunityModel updateOpportunity(OpportunityModel opportunityModel, String opportunityId, String stageId) {
        Opportunity opportunity = opportunityRepository.findById(opportunityId).get();

        opportunity.setName(opportunityModel.getName());
        opportunity.setEmail(opportunityModel.getEmail());
        opportunity.setPhone(opportunityModel.getPhone());
        opportunity.setAddress(opportunityModel.getAddress());
        opportunity.setWebsite(opportunityModel.getWebsite());
        opportunity.setDescription(opportunityModel.getDescription());
        opportunity.setRevenue(opportunityModel.getRevenue());
        opportunity.setLastModifiedDate(new Date());

        opportunity.setStage(stageRepository.findById(stageId).get());
        opportunityRepository.save(opportunity);
        return setStageAndSalesperson(opportunity);
    }

    @Override
    public OpportunityModel assignSalesperson(String opportunityId, String userId) {
        Opportunity opportunity = opportunityRepository.findById(opportunityId).get();
        opportunity.setSalesperson(userRepository.findById(userId).get());
        opportunity.setLastModifiedDate(new Date());
        opportunityRepository.save(opportunity);
        return setStageAndSalesperson(opportunity);
    }


    public OpportunityModel setStageAndSalesperson(Opportunity o) {
        OpportunityModel opportunityModel = modelMapper.map(o, OpportunityModel.class);

        if (o.getStage() != null) {
            opportunityModel.setStageId(o.getStage().getId());
        } else opportunityModel.setStageId("");

        if (o.getSalesperson() != null) {
            opportunityModel.setSalespersonId(o.getSalesperson().getId());
        } else opportunityModel.setSalespersonId("");

        return opportunityModel;
    }
}
