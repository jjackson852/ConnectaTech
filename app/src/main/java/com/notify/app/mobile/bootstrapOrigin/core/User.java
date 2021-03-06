package com.notify.app.mobile.bootstrapOrigin.core;

import android.app.AlertDialog;
import android.media.Image;
import android.text.TextUtils;

import com.parse.ParseFile;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

    private static final long serialVersionUID = -7495897652017488896L;

    protected String firstName;
    protected String lastName;
    protected String username;
    protected String phone;
    protected String objectId;
    protected String sessionToken;
    protected String gravatarId;
    protected String avatarUrl;
    protected Float avgRating;
    protected String bio;
    protected ParseFile imageFile;
    protected Date createdAt;
    protected String description;


    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(final String objectId) {
        this.objectId = objectId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public ParseFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(final ParseFile imageFile) {
        this.imageFile = imageFile;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Date getCreateAt() {return createdAt;}

    public void  setCreatedAt(final Date createdAt) {this.createdAt = createdAt;}


    //Rating

    public Float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(final Float avgRating) {
        this.avgRating = avgRating;
    }

    public String getGravatarId() {
        return gravatarId;
    }

    public String getAvatarUrl() {
        if (TextUtils.isEmpty(avatarUrl)) {
            String gravatarId = getGravatarId();
            if (TextUtils.isEmpty(gravatarId))
                gravatarId = GravatarUtils.getHash(getUsername());
            avatarUrl = getAvatarUrl(gravatarId);
        }
        return avatarUrl;
    }

    private String getAvatarUrl(String id) {
        if (!TextUtils.isEmpty(id))
            return "https://secure.gravatar.com/avatar/" + id + "?d=404";
        else
            return null;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(final String bio) {
        this.bio = bio;
    }
}
