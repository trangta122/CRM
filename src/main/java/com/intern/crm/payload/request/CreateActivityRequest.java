package com.intern.crm.payload.request;

import java.util.Date;

public class CreateActivityRequest {
    private String detail;
    private Date date;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
