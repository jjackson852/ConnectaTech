package com.notify.app.mobile.core;

import java.io.Serializable;

public class Rating implements Serializable {
//
    private static final long serialVersionUID = -6641292855569752022L;

    private String title;
    private String content;
    private String objectId;
    private String providerInfo;
    private String rating;
    private String description;

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

    public String getRating() {
        return rating;
    }

    public void setRating(final String rating) {
        this.rating = rating;
    }
}
