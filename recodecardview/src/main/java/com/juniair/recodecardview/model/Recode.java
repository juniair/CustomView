package com.juniair.recodecardview.model;

/**
 * Class : Recode <br/>
 *
 */
public class Recode {
    private String recodeTitle;
    private String thumbnailURL;

    public Recode() {
    }

    /**
     *
     * @param recodeTitle 동영상 제목
     * @param thumbnailURL  동영상 URL
     */
    public Recode(String recodeTitle, String thumbnailURL) {
        this.recodeTitle = recodeTitle;
        this.thumbnailURL = thumbnailURL;
    }

    public String getRecodeTitle() {
        return recodeTitle;
    }

    public void setRecodeTitle(String recodeTitle) {
        this.recodeTitle = recodeTitle;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

}
