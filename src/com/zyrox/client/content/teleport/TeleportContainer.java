package com.zyrox.client.content.teleport;

/**
 * Created by Jonny on 6/20/2019
 **/
public class TeleportContainer {

    private final String teleportName;
    private final int spriteId;
    private final int wildernessLevel;
    private final boolean multi;

    public TeleportContainer(String teleportName, int wildernessLevel, int spriteId, boolean multi) {
        this.teleportName = teleportName;
        this.spriteId = spriteId;
        this.wildernessLevel = wildernessLevel;
        this.multi = multi;
    }

    public String getTeleportName() {
        return teleportName;
    }

    public int getSpriteId() {
        return spriteId;
    }

    public int getWildernessLevel() {
        return wildernessLevel;
    }

    public boolean isMulti() {
        return multi;
    }

}
