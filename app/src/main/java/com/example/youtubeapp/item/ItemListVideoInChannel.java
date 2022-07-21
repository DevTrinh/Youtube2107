package com.example.youtubeapp.item;

public class ItemListVideoInChannel {
    private String idVideo;
    private String titleVideo;
    private String urlImageList;
    private String titleChannel;
    private String numberVideo;

    public String getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(String idVideo) {
        this.idVideo = idVideo;
    }

    public String getTitleVideo() {
        return titleVideo;
    }

    public void setTitleVideo(String titleVideo) {
        this.titleVideo = titleVideo;
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

    public ItemListVideoInChannel(String idVideo, String titleVideo, String urlImageList, String titleChannel, String numberVideo) {
        this.idVideo = idVideo;
        this.titleVideo = titleVideo;
        this.urlImageList = urlImageList;
        this.titleChannel = titleChannel;
        this.numberVideo = numberVideo;
    }
}
