package com.intern.crm.service;

import com.intern.crm.entity.Opportunity;
import com.intern.crm.payload.model.OpportunityModel;
import com.intern.crm.payload.request.CreateOpportunityRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface OpportunityService {
    //create a new opportunity
    Opportunity createOpportunity(CreateOpportunityRequest model, String stageId, Boolean isCustomer);

    //create many opportunities by import Excel file
    void importExcel(MultipartFile file);

    //get all opportunities
    List<OpportunityModel> listAllOpportunities();

    //get an opportunity by specify ID
    OpportunityModel findOpportunityById(String id);

    //update: edit information, convert stage
    OpportunityModel updateOpportunity(OpportunityModel opportunity, String opportunityId, String stageId);

    //Assign user (salesperson) to take care of opportunity
    OpportunityModel assignSalesperson(String opportunityId, String userId);

    //Retrieve all opportunities of a contact;
    List<OpportunityModel> findOpportunityByContactId(String id);

    //Pagination
    Map<String, Object> pagingOpportunity(int page, int size, String sortBy);
}
