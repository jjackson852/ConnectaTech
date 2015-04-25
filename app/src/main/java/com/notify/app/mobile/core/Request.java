package com.notify.app.mobile.core;

import com.parse.ParseUser;

import java.io.Serializable;
import java.util.Date;

public class Request implements Serializable {

    private static final long serialVersionUID = -6641292855569752035L;

    private String serviceTitle;
    private String addlInfo;
    private String objectId;
    private String custPhoneNumber;
    private String custEmail;
    private Date createdAt;
//    private String submittedBy;

    public String getServiceTitle() {
        return serviceTitle;
    }

    public void setServiceTitle(final String serviceTitle) {
        this.serviceTitle = serviceTitle;
    }

    public String getAddlInfo() {
        return addlInfo;
    }

    public void setAddlInfo(final String addlInfo) {
        this.addlInfo = addlInfo;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(final String objectId) {
        this.objectId = objectId;
    }

    public String getCustPhoneNumber() {
        return custPhoneNumber;
    }

    public void setCustPhoneNumber(final String custPhoneNumber) {
        this.custPhoneNumber = custPhoneNumber;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(final String custEmail) {
        this.custEmail = custEmail;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

//    public String getSubmittedBy(){
//        return submittedBy;
//    }
}
