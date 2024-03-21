package com.intern.crm.service.impl;

import com.intern.crm.entity.Opportunity;
import com.intern.crm.entity.Stage;
import com.intern.crm.helper.ExcelHelper;
import com.intern.crm.payload.model.OpportunityModel;
import com.intern.crm.payload.model.StageModel;
import com.intern.crm.repository.OpportunityRepository;
import com.intern.crm.repository.StageRepository;
import com.intern.crm.service.OpportunityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class OpportunityServiceImpl implements OpportunityService {
    @Autowired
    OpportunityRepository opportunityRepository;
    @Autowired
    StageRepository stageRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Opportunity savedOpportunity(OpportunityModel opportunityModel, String stageId) {
        Stage stage = stageRepository.findById(stageId).get();
        Opportunity opportunity = modelMapper.map(opportunityModel, Opportunity.class);
        opportunity.setStage(stage);
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
}
