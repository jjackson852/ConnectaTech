package com.notify.app.mobile.core;

import com.parse.ParseUser;

import java.io.Serializable;

public class TechService implements Serializable {

    private static final long serialVersionUID = -6641292855569752034L;

    private String title;
    private String description;
    private String objectId;
    private String basePrice;
    private String zipCode;
    private ParseUser provider;

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(final String objectId) {
        this.objectId = objectId;
    }

    public String getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(final String basePrice) {
        this.basePrice = basePrice;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(final String zipCode) {
        this.zipCode = zipCode;
    }

    public ParseUser getCreatedBy() {
        return provider;
    }

    public void setCreatedBy(final ParseUser provider) {
        this.provider = provider;
    }

}