package com.varrock.client.cache.download;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.varrock.GameType;
import com.varrock.client.Client;
import com.varrock.client.signlink;

/**
 * Created by Jonny on 8/21/2019
 **/
public class CacheDownloader {

    public static boolean NEEDS_REDOWNLOAD = false;

    public static void init() throws IOException {
    	System.out.println("init downloader");
        loadAllData();
        boolean stop = false;
        for(CacheDownloadType cacheDownloadType : CacheDownloadType.values()) {
            if(stop) {
            	System.out.println("personalSize "+cacheDownloadType.personalSize+ " vs remoteSiz "+cacheDownloadType.remoteSize );
                cacheDownloadType.personalSize = cacheDownloadType.remoteSize;
                cacheDownloadType.personalLastModified = cacheDownloadType.remoteLastModified;
            } else {
                stop = download(cacheDownloadType);
            }
        }
    }

    public static void loadAllData() throws MalformedURLException {
        loadSizes();
        for(CacheDownloadType cacheDownloadType : CacheDownloadType.values()) {
            cacheDownloadType.remoteSize = getFileSize(new URL(cacheDownloadType.getUrl()));
            cacheDownloadType.remoteLastModified = getLastModified(new URL(cacheDownloadType.getUrl()));
        }
    }

    /**
     * Starts the initial download process
     * @param cache
     */
    public static boolean download(CacheDownloadType type) {
        if(!needsUpdate(type)) {
            return false;
        }

        try {
            download(type.getFileName(), type.getUrl());
            unZip(type.getFileName());

            File downloaded = new File(signlink.findcachedir() + File.separator + type.getFileName());

            if(downloaded != null && downloaded.exists()) {
                downloaded.delete();
            }

            type.personalSize = type.remoteSize;
            type.personalLastModified = type.remoteLastModified;

            NEEDS_REDOWNLOAD = false;

           /* if(type == CacheDownloadType.OSRS_MAPS || type == CacheDownloadType.REG_MAPS || type == CacheDownloadType.REG_MODELS) {
                type.repack = true;
            }*/

            saveSizes();

            return type == CacheDownloadType.CACHE;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean needsUpdate(CacheDownloadType type) {
        if (type == CacheDownloadType.CACHE) {
            File file = new File(signlink.findcachedir() + File.separator + "main_file_cache.dat");

            if(NEEDS_REDOWNLOAD) {
                return true;
            }

            if(!file.exists()) {
                return true;
            }

            if(file.length() <= 100_000) {
                return true;
            }

            return false;
        }

        return (((type.remoteSize != type.personalSize) || (type.remoteLastModified != type.personalLastModified)) && type.isUpdater());
    }

    /**
     * Downloads the cache
     * @param fileName
     * @param downloadUrl
     * @throws IOException
     */
    public static void download(String fileName, String downloadUrl) throws IOException {
        URL url = new URL(downloadUrl);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.addRequestProperty("User-Agent", "Mozilla/4.76");
        int responseCode = httpConn.getResponseCode();

        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = signlink.findcachedir() + File.separator + fileName;

            // opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);

            int bytesRead = -1;
            byte[] buffer = new byte[4096];
            long startTime = System.currentTimeMillis();
            int downloaded = 0;
            long numWritten = 0;
            int length = httpConn.getContentLength();
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
                numWritten += bytesRead;
                downloaded += bytesRead;
                int percentage = (int)(((double)numWritten / (double)length) * 100D);
                @SuppressWarnings("unused")
                int downloadSpeed = (int) ((downloaded / 1024) / (1 + ((System.currentTimeMillis() - startTime) / 1000)));
                Client.instance.drawLoadingText(percentage, (new StringBuilder()).append("Downloading "+fileName+" ("+downloadSpeed+"kb/s): "+percentage+"%").toString());
            }

            outputStream.close();
            inputStream.close();

        } else {
            System.out.println("Varrock.io replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
    }

    /**
     * Starts the intiial file unzipping process
     * @param fileName
     */
    public static void unZip(String fileName) {
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(signlink.findcachedir() + fileName));
            ZipInputStream zin = new ZipInputStream(in);
            ZipEntry e;
            while ((e = zin.getNextEntry()) != null) {
                if (e.isDirectory()) {
                    (new File(signlink.findcachedir() + e.getName())).mkdir();
                } else {
                    if (e.getName().equals(signlink.findcachedir() + fileName)) {
                        unzip(zin, signlink.findcachedir() + fileName);
                        break;
                    }
                    unzip(zin, signlink.findcachedir() + e.getName());
                }
            }
            zin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Actually unzips the file...
     * @param zin
     * @param s
     * @throws IOException
     */
    public static void unzip(ZipInputStream zin, String s) throws IOException {
        FileOutputStream out = new FileOutputStream(s);
        byte[] b = new byte[1024];
        int len = 0;
        while ((len = zin.read(b)) != -1) {
            out.write(b, 0, len);
        }
        out.close();
    }

    public static long getFileSize(URL url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            return conn.getContentLengthLong();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public static long getLastModified(URL url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            return conn.getLastModified();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public static void saveSizes() {
        try {
            PrintWriter writer = new PrintWriter(signlink.findcachedir() + File.separator + "versions", "UTF-8");
            for(CacheDownloadType cacheDownloadType : CacheDownloadType.values()) {
                writer.println(cacheDownloadType.toString()+":"+cacheDownloadType.personalSize+":size");
                writer.println(cacheDownloadType.toString()+":"+cacheDownloadType.personalLastModified+":modified");
            }
            writer.println(CacheDownloadType.CACHE.toString()+":redownload:"+NEEDS_REDOWNLOAD+"");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadSizes() {
        File file = new File(signlink.findcachedir() + File.separator + "versions");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch(FileNotFoundException e) {
            return;
        }
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                String[] args = line.split(":");
                if (args.length <= 1)
                    continue;
                String token = args[0], value = args[1], type = args[2];

                CacheDownloadType cacheType = CacheDownloadType.valueOf(token);

                switch(type) {
                    case "size":
                        long size = Long.parseLong(value);
                        cacheType.personalSize = size;
                        break;
                    case "modified":
                        long modified = Long.parseLong(value);
                        cacheType.personalLastModified = modified;
                        break;
                    case "redownload":
                        NEEDS_REDOWNLOAD = Boolean.parseBoolean(value);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
