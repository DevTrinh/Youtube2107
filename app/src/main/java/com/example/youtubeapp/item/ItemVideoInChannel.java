package com.example.youtubeapp.item;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.youtubeapp.fragment.FragmentHome;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class ItemVideoInChannel {
    private String titleVideo;
    private String timeUpVideo;
    private String viewCount;
    private String urlImage;
    private String idVideo;

    public String getTitleVideo() {
        return titleVideo;
    }

    public void setTitleVideo(String titleVideo) {
        this.titleVideo = titleVideo;
    }

    public String getTimeUpVideo() {
        return timeUpVideo;
    }

    public void setTimeUpVideo(String timeUpVideo) {
        this.timeUpVideo = timeUpVideo;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public ItemVideoInChannel(String titleVideo, String timeUpVideo, String urlImage, String idVideo) {
        this.titleVideo = titleVideo;
        this.timeUpVideo = timeUpVideo;
        this.urlImage = urlImage;
        this.idVideo = idVideo;
    }

    public String getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(String idVideo) {
        this.idVideo = idVideo;
    }

}
