package com.varrock.client.content.youtube;

/**
 * Created by Jonny on 10/15/2019
 **/
public class YouTubeVideo {

    private final String videoId;
    private final String uploader;
    private final String title;
    private final String description;

    public YouTubeVideo(String videoId, String uploader, String title, String description) {
        this.videoId = videoId;
        this.uploader = uploader;
        this.title = title;
        this.description = description;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getUploader() {
        return uploader;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
