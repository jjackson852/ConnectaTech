package com.notify.app.mobile.core;

import com.parse.ParseUser;

import java.io.Serializable;
import java.util.Date;

public class TechService implements Serializable {

    private static final long serialVersionUID = -6641292855569752034L;

    private String title;
    private String description;
    private String objectId;
    private String basePrice;
    private String state;
    private String zipCode;
    private String category;
    private String chargeType;
    private Date createdAt;
    private String providerUsername;

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

    public String getProviderUsername() {
        return providerUsername;
    }

    public void setProviderUsername(final String providerUsername) {
        this.providerUsername = providerUsername;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public Date getCreatedAt() {return createdAt;}

    public void setCreatedAt(final Date createdAt) {this.createdAt = createdAt;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(final String chargeType) {
        this.chargeType = chargeType;
    }

    public String getState() {
        return state;
    }

    public void setState(final String state) {
        this.state = state;
    }

}
