package com.juniair.customview.model;

/**
 * Created by juniair on 2016-08-04.
 */
public class Recode {
    private String title;
    private String thumbnailURL;

    public Recode() {

    }

    public Recode(String title, String thumbnailURL) {
        this.title = title;
        this.thumbnailURL = thumbnailURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }
}
