package com.intern.crm.payload.request;

public class ChangePasswordRequest {
    private String oldPassword;
    private String newPassword;
    private String renewPassword;


    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRenewPassword() {
        return renewPassword;
    }

    public void setRenewPassword(String renewPassword) {
        this.renewPassword = renewPassword;
    }
}
