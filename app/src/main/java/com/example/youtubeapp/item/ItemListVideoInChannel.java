package com.example.youtubeapp.item;

public class ItemListVideoInChannel {
    private String idVideo;
    private String titleList;
    private String urlImageList;
    private String titleChannel;
    private String numberVideo;

    public String getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(String idVideo) {
        this.idVideo = idVideo;
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

    public ItemListVideoInChannel(String idVideo, String titleList, String urlImageList, String titleChannel, String numberVideo) {
        this.idVideo = idVideo;
        this.titleList = titleList;
        this.urlImageList = urlImageList;
        this.titleChannel = titleChannel;
        this.numberVideo = numberVideo;
    }
}
