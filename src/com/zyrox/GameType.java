package com.zyrox;

import com.zyrox.util.StringUtils;

/**
 * Created by Jonny on 8/20/2019
 **/
public enum GameType {

    LOCAL("127.0.0.1"),
  LIVE("localhost"),
   //LIVE("51.89.232.12"),
    BETA("192.99.69.48");// why cant i log in

    private String ip;

    GameType(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public String getName() {
        return StringUtils.capitalize(name().toLowerCase().replaceAll("_", " "));
    }

}
