package com.notify.app.mobile.core;

import java.io.Serializable;

public class Request implements Serializable {

    private static final long serialVersionUID = -6641292855569752035L;

    private String serviceTitle;
    private String addlInfo;
    private String objectId;

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
}
