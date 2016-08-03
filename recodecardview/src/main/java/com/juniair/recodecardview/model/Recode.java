package com.juniair.recodecardview.model;

/**
 * Created by juniair on 2016-08-03.
 */
public class Recode {
    private String name;
    private String thumbnailURL;

    public Recode() {
    }

    public Recode(String name, String thumbnailURL) {
        this.name = name;
        this.thumbnailURL = thumbnailURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }
}
