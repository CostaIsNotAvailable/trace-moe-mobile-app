package com.example.tracemoeapplication.dtos;

import java.io.Serializable;

public class MatchDto implements Serializable {
    private long anilist;
    private String filename;
    private int episode;
    private double from;
    private double to;
    private float similarity;
    private String video;
    private String image;

    public long getAnilist() {
        return anilist;
    }

    public void setAnilist(long anilist) {
        this.anilist = anilist;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public double getFrom() {
        return from;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        this.to = to;
    }

    public float getSimilarity() {
        return similarity;
    }

    public void setSimilarity(float similarity) {
        this.similarity = similarity;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
