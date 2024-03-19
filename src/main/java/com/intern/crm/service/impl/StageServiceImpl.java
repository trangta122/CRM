package com.intern.crm.service.impl;

import com.intern.crm.entity.Stage;
import com.intern.crm.payload.model.StageModel;
import com.intern.crm.payload.model.UserModel;
import com.intern.crm.repository.StageRepository;
import com.intern.crm.service.StageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StageServiceImpl implements StageService {
    @Autowired
    StageRepository stageRepository;
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
}
