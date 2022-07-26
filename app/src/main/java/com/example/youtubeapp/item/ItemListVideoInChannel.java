package com.example.youtubeapp.item;

public class ItemListVideoInChannel {
    private String idList;
    private String titleList;
    private String urlImageList;
    private String titleChannel;
    private String numberVideo;
    private String describe;

    public String getIdList() {
        return idList;
    }

    public void setIdList(String idList) {
        this.idList = idList;
    }

    public String getTitleList() {
        return titleList;
    }

    public void setTitleList(String titleList) {
        this.titleList = titleList;
    }

    public String getUrlImageList() {
        return urlImageList;
    }

    public void setUrlImageList(String urlImageList) {
        this.urlImageList = urlImageList;
    }

    public String getTitleChannel() {
        return titleChannel;
    }

    public void setTitleChannel(String titleChannel) {
        this.titleChannel = titleChannel;
    }

    public String getNumberVideo() {
       return numberVideo;
    }

    public void setNumberVideo(String numberVideo) {
        this.numberVideo = numberVideo;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public ItemListVideoInChannel(String idList, String titleList, String urlImageList, String titleChannel, String numberVideo, String describe) {
        this.idList = idList;
        this.titleList = titleList;
        this.urlImageList = urlImageList;
        this.titleChannel = titleChannel;
        this.numberVideo = numberVideo;
        this.describe = describe;
    }
}
