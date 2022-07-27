package com.example.youtubeapp.item;

public class ItemVideoInList {
    private String urlImage;
    private String titleVideo;
    private String titleChannel;
    private String idVideo;

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getTitleVideo() {
        return titleVideo;
    }

    public void setTitleVideo(String titleVideo) {
        this.titleVideo = titleVideo;
    }

    public String getTitleChannel() {
        return titleChannel;
    }

    public void setTitleChannel(String titleChannel) {
        this.titleChannel = titleChannel;
    }

    public String getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(String idVideo) {
        this.idVideo = idVideo;
    }

    public ItemVideoInList(String urlImage, String titleVideo, String titleChannel, String idVideo) {
        this.urlImage = urlImage;
        this.titleVideo = titleVideo;
        this.titleChannel = titleChannel;
        this.idVideo = idVideo;
    }

}
