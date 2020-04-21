package com.varrock.client.cache.download;

/**
 * Created by Jonny on 8/21/2019
 **/
public enum CacheDownloadType {

    CACHE("cache.zip", "https://varrock.io/download/ZanarisCache.zip"),
   /* SPRITES("sprites.zip", "https://storage.googleapis.com/solacersps/sprites.zip?avoidTheCaches=1", true),
    CONFIG("config.zip", "https://storage.googleapis.com/solacersps/config.zip?avoidTheCaches=1", true),
    OSRS_MAPS("osrs_maps.zip", "https://storage.googleapis.com/solacersps/osrs_maps.zip?avoidTheCaches=1", true),
    REG_MAPS("reg_maps.zip", "https://storage.googleapis.com/solacersps/reg_maps.zip?avoidTheCaches=1", true),
    REG_MODELS("reg_models.zip", "https://storage.googleapis.com/solacersps/reg_models.zip?avoidTheCaches=1", true),*/

    ;

    private final String fileName;
    private final String url;
    private final boolean updater;

    public long personalSize;
    public long remoteSize;

    public long personalLastModified;
    public long remoteLastModified;

    public boolean repack;

    CacheDownloadType(String fileName, String url) {
        this.fileName = fileName;
        this.url = url;
        this.updater = false;
    }

    CacheDownloadType(String fileName, String url, boolean updater) {
        this.fileName = fileName;
        this.url = url;
        this.updater = updater;
    }

    public boolean isUpdater() {
        return updater;
    }

    public String getUrl() {
        return url;
    }

    public String getFileName() {
        return fileName;
    }}
