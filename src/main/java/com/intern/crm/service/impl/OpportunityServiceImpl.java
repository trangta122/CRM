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
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${crm.app.lost}")
    private String lostId;

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
    public OpportunityModel updateOpportunity(OpportunityModel opportunityModel, String opportunityId) {
        Opportunity opportunity = opportunityRepository.findById(opportunityId).get();

        //update stage's revenue
//        Stage stage = stageRepository.findById(opportunity.getStage().getId()).get();
//        stage.setRevenue(stage.getRevenue() + opportunityModel.getRevenue() - opportunity.getRevenue());
//        stage.setLastModifiedDate(new Date());
//        stageRepository.save(stage);

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
        opportunity.setLostNote(opportunityModel.getLostNote());

        opportunity.setLastModifiedDate(new Date());

        opportunity.setStage(stageRepository.findById(opportunityModel.getStage()).get());

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

    @Override
    public List<OpportunityModel> findOpportunityByContactId(String id) {
        List<Opportunity> opportunities = opportunityRepository.findOpportunityByContactsId(id);
        List<OpportunityModel> opportunityModels = new ArrayList<>();

        for (Opportunity opportunity : opportunities) {
            opportunityModels.add(setStageAndSalesperson(opportunity));
        }
        return opportunityModels;
    }


    public OpportunityModel setStageAndSalesperson(Opportunity o) {
        OpportunityModel opportunityModel = modelMapper.map(o, OpportunityModel.class);

        if (o.getStage() != null) {
            opportunityModel.setStage(o.getStage().getId());
        } else opportunityModel.setStage("");

        if (o.getSalesperson() != null) {
            opportunityModel.setSalesperson(o.getSalesperson().getId());
        } else opportunityModel.setSalesperson("");

        return opportunityModel;
    }
}
