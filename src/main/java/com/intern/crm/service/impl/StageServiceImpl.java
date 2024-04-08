package com.intern.crm.service.impl;

import com.intern.crm.entity.Opportunity;
import com.intern.crm.entity.Stage;
import com.intern.crm.payload.model.OpportunityModel;
import com.intern.crm.payload.model.StageModel;
import com.intern.crm.repository.OpportunityRepository;
import com.intern.crm.repository.StageRepository;
import com.intern.crm.service.StageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StageServiceImpl implements StageService {
    @Autowired
    StageRepository stageRepository;
    @Autowired
    OpportunityRepository opportunityRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public List<StageModel> listAllStages() {
        List<Stage> stage = stageRepository.findAll();
        return stage.stream().map(s -> modelMapper.map(s, StageModel.class)).collect(Collectors.toList());
    }

    @Override
    public Optional<StageModel> getStageById(String id) {
        Optional<Stage> stage = stageRepository.findById(id);
        StageModel sM = modelMapper.map(stage, StageModel.class);
        return Optional.ofNullable(sM);
    }

    @Override
    public StageModel editStage(String id, StageModel stageModel) {
        Stage stage = stageRepository.findById(id).get();

        stage.setName(stageModel.getName());
        stage.setRevenue(stageModel.getRevenue());
        stage.setLastModifiedDate(new Date());

        stageRepository.save(stage);
        StageModel sM = modelMapper.map(stage, StageModel.class);
        return sM;
    }

    @Override
    public void deleteStageById(String id) {
        stageRepository.deleteById(id);
    }

    @Override
    public List<OpportunityModel> getOpportunitiesByStageId(String id) {
        List<Opportunity> opportunities = opportunityRepository.findByStageId(id);
        List<OpportunityModel> opportunityDisplays = new ArrayList<>();

        for (Opportunity opportunity : opportunities) {
            OpportunityModel opportunityModel = modelMapper.map(opportunity, OpportunityModel.class);
            opportunityModel.setSalesperson(opportunity.getSalesperson().getFirstname() + " " + opportunity.getSalesperson().getLastname());
            opportunityDisplays.add(opportunityModel);
        }
        return opportunityDisplays;
    }
}
