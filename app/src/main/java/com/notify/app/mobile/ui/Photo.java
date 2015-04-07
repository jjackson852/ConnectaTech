package com.notify.app.mobile.ui;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
/*
* An extension of ParseObject that makes
* it more convenient to access information
* about a given Photo
*/
@ParseClassName("Photo")
public class Photo extends ParseObject {

    public Photo() {
// A default constructor is required.
    }
    public String getTitle() {
        return getString("title");
    }
    public void setTitle(String title) {
        put("title", title);
    }
    public ParseUser getAuthor() {
        return getParseUser("author");
    }
    public void setAuthor(ParseUser user) {
        put("author", user);
    }
    public String getRating() {
        return getString("rating");
    }
    public void setRating(String rating) {
        put("rating", rating);
    }
    public ParseFile getPhotoFile() {
        return getParseFile("photo");
    }
    public void setPhotoFile(ParseFile file) {
        put("photo", file);
    }
}