package com.intern.crm.service;

import com.intern.crm.entity.Stage;
import com.intern.crm.payload.model.StageModel;

import java.util.List;
import java.util.Optional;

public interface StageService {
    List<StageModel> listAllStages();
    Optional<StageModel> getStageById(String id);

    StageModel editStage(String id, StageModel stageModel);
    void deleteStageById(String id);
}
