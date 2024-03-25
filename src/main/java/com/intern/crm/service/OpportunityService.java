package com.intern.crm.service;

import com.intern.crm.entity.Opportunity;
import com.intern.crm.payload.model.OpportunityModel;
import com.intern.crm.payload.request.CreateOpportunityRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OpportunityService {
    Opportunity createOpportunity(CreateOpportunityRequest model, String stageId, Boolean isCustomer);
    void importExcel(MultipartFile file);
    List<OpportunityModel> listAllOpportunities();
    OpportunityModel findOpportunityById(String id);
}
