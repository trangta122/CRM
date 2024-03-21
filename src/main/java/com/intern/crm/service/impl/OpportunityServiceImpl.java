package com.intern.crm.service.impl;

import com.intern.crm.entity.Opportunity;
import com.intern.crm.entity.Stage;
import com.intern.crm.payload.model.OpportunityModel;
import com.intern.crm.payload.model.StageModel;
import com.intern.crm.repository.OpportunityRepository;
import com.intern.crm.repository.StageRepository;
import com.intern.crm.service.OpportunityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
}
