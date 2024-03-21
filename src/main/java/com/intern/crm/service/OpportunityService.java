package com.intern.crm.service;

import com.intern.crm.entity.Opportunity;
import com.intern.crm.payload.model.OpportunityModel;
import com.intern.crm.payload.request.CreateOpportunityRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OpportunityService {
    Opportunity savedOpportunity(CreateOpportunityRequest model, String stageId);
    void importExcel(MultipartFile file);

    List<OpportunityModel> listAllOpportunities();
    OpportunityModel findOpportunityById(String id);
}
