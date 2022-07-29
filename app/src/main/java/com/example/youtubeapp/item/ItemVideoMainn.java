package com.example.youtubeapp.item;

public class ItemVideoMainn {
    private String urlImage;
    private String urlImageChannel;
    private String timeUp;
    private String viewer;
    private String titleChannel;
    private String titleVideo;
    private String idVideo;
    private String idChannel;

    public String getTitleVideo() {
        return titleVideo;
    }

    public void setTitleVideo(String titleVideo) {
        this.titleVideo = titleVideo;
    }


    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getUrlImageChannel() {
        return urlImageChannel;
    }

    public void setUrlImageChannel(String urlImageChannel) {
        this.urlImageChannel = urlImageChannel;
    }

    public String getTimeUp() {
        return timeUp;
    }

    public void setTimeUp(String timeUp) {
        this.timeUp = timeUp;
    }

    public String getViewer() {
        return viewer;
    }

    public void setViewer(String viewer) {
        this.viewer = viewer;
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

    public String getIdChannel() {
        return idChannel;
    }

    public void setIdChannel(String idChannel) {
        this.idChannel = idChannel;
    }

    public ItemVideoMainn(String urlImage, String timeUp,
                          String titleChannel, String titleVideo,
                          String idVideo, String idChannel) {
        this.urlImage = urlImage;
        this.timeUp = timeUp;
        this.viewer = viewer;
        this.titleChannel = titleChannel;
        this.titleVideo = titleVideo;
        this.idVideo = idVideo;
        this.idChannel = idChannel;
    }
}
