package com.notify.app.mobile.core;

import java.io.Serializable;
import java.util.Date;

public class Rating implements Serializable {
//
    private static final long serialVersionUID = -6641292855569752022L;

    private String title;
    private String content;
    private String objectId;
    private String providerInfo;
    private Float rating;
    private String description;
    private Date createdAt;

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

    public Float getRating() {
        return rating;
    }

    public void setRating(final Float rating) {
        this.rating = rating;
    }

    public Date getCreatedAt() {return createdAt;}

    public void setCreatedAt(final Date createdAt) {this.createdAt = createdAt;
    }
}
