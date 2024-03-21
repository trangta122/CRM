package com.intern.crm.service;

import com.intern.crm.entity.Opportunity;
import com.intern.crm.payload.model.OpportunityModel;
import org.springframework.web.multipart.MultipartFile;

public interface OpportunityService {
    Opportunity savedOpportunity(OpportunityModel model, String stageId);
    void importExcel(MultipartFile file);
}
