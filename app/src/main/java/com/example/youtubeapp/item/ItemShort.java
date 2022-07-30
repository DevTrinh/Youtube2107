package com.example.youtubeapp.item;

public class ItemShort {
    private String urlShort;
    private String titleChannel;
    private String titleShort;
    private String urlAvtChannel;
    private String idChannel;


    public String getIdChannel() {
        return idChannel;
    }

    public void setIdChannel(String idChannel) {
        this.idChannel = idChannel;
    }

    public String getUrlShort() {
        return urlShort;
    }

    public void setUrlShort(String urlShort) {
        this.urlShort = urlShort;
    }

    public String getTitleChannel() {
        return titleChannel;
    }

    public void setTitleChannel(String titleChannel) {
        this.titleChannel = titleChannel;
    }

    public String getTitleShort() {
        return titleShort;
    }

    public void setTitleShort(String titleShort) {
        this.titleShort = titleShort;
    }

    public String getUrlAvtChannel() {
        return urlAvtChannel;
    }

    public void setUrlAvtChannel(String urlAvtChannel) {
        this.urlAvtChannel = urlAvtChannel;
    }

    public ItemShort(String urlShort, String titleChannel, String titleShort, String urlAvtChannel, String idChannel) {
        this.urlShort = urlShort;
        this.titleChannel = titleChannel;
        this.titleShort = titleShort;
        this.urlAvtChannel = urlAvtChannel;
        this.idChannel = idChannel;
    }
}
