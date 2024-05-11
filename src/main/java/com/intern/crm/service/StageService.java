package com.intern.crm.service;

import com.intern.crm.payload.model.OpportunityModel;
import com.intern.crm.payload.model.StageModel;

import java.util.List;
import java.util.Optional;

public interface StageService {
    //List all stages
    List<StageModel> listAllStages();

    //Get a stage by Stage's ID
    Optional<StageModel> getStageById(String id);

    //Update stage by stage's ID
    StageModel editStage(String id, StageModel stageModel);

    //Delete a stage by Stage's ID
    void deleteStageById(String id);

    //List all opportunities of a stage by Stage's ID
    List<OpportunityModel> getOpportunitiesByStageId(String id);
}
