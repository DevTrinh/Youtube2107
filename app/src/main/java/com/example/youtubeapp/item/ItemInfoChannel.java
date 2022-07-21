package com.example.youtubeapp.item;

import com.example.youtubeapp.fragment.FragmentHome;

public class ItemInfoChannel {
    private String urlAvt;
    private String urlBanner;
    private String timeCreateChannel;
    private String titleChannel;
    private String description;
    private String urlListUpload;
    private String viewCount;
    private String subscriberCount;
    private String videoCount;
    private String idChannel;

    public String getIdChannel() {
        return idChannel;
    }

    public void setIdChannel(String idChannel) {
        this.idChannel = idChannel;
    }

    public String getUrlAvt() {
        return urlAvt;
    }

    public void setUrlAvt(String urlAvt) {
        this.urlAvt = urlAvt;
    }

    public String getUrlBanner() {
        return urlBanner;
    }

    public void setUrlBanner(String urlBanner) {
        this.urlBanner = urlBanner;
    }

    public String getTimeCreateChannel() {
        return timeCreateChannel;
    }

    public void setTimeCreateChannel(String timeCreateChannel) {
        this.timeCreateChannel = timeCreateChannel;
    }

    public String getTitleChannel() {
        return titleChannel;
    }

    public void setTitleChannel(String titleChannel) {
        this.titleChannel = titleChannel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlListUpload() {
        return urlListUpload;
    }

    public void setUrlListUpload(String urlListUpload) {
        this.urlListUpload = urlListUpload;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getSubscriberCount() {
        if (Integer.parseInt(subscriberCount) > 1){
            return FragmentHome.formatData(Integer.parseInt(subscriberCount)) + " subscribers";
        }
        else{
            return subscriberCount + " subscriber";
        }
    }

    public void setSubscriberCount(String subscriberCount) {
        this.subscriberCount = subscriberCount;
    }

    public String getVideoCount() {
        if (Integer.parseInt(videoCount) > 1){
            return videoCount + " videos";
        }
        else{
            return videoCount + " video";
        }
    }

    public void setVideoCount(String videoCount) {
        this.videoCount = videoCount;
    }

    public ItemInfoChannel(String urlAvt, String urlBanner,
                           String timeCreateChannel, String titleChannel,
                           String description, String urlListUpload,
                           String viewCount, String subscriberCount,
                           String videoCount, String idChannel) {
        this.urlAvt = urlAvt;
        this.urlBanner = urlBanner;
        this.timeCreateChannel = timeCreateChannel;
        this.titleChannel = titleChannel;
        this.description = description;
        this.urlListUpload = urlListUpload;
        this.viewCount = viewCount;
        this.subscriberCount = subscriberCount;
        this.videoCount = videoCount;
        this.idChannel = idChannel;
    }
}
