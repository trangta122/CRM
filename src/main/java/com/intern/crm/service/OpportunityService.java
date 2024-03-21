package com.intern.crm.service;

import com.intern.crm.entity.Opportunity;
import com.intern.crm.payload.model.OpportunityModel;

public interface OpportunityService {
    Opportunity savedOpportunity(OpportunityModel model, String stageId);
}
