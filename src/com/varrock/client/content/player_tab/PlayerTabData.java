package com.varrock.client.content.player_tab;

import com.varrock.util.StringUtils;

/**
 * Created by Jonny on 6/7/2019
 **/
public enum PlayerTabData {

    INFORMATION(23510,23608, 1356),

    OPTIONS(23512, 23808, 1362),

   // ACHIEVEMENTS(23514, 24108),

    QUESTS(23514,50_500, 22);

    public static PlayerTabData[] VALUES = PlayerTabData.values();

    private final int buttonId;
    private final int interfaceId;
    private final String name;
    private final int spriteId;

    PlayerTabData(int buttonId, int interfaceId, int spriteId) {
        this.buttonId = buttonId;
        this.interfaceId = interfaceId;
        this.spriteId = spriteId;
        this.name = StringUtils.capitalize(toString().toLowerCase().replaceAll("_", " "));
    }

    public int getButtonId() {
        return buttonId;
    }

    public static PlayerTabData getPlayerTabForButtonId(int buttonId) {
        for(PlayerTabData playerTabData : VALUES) {
            if(playerTabData.getButtonId() == buttonId) {
                return playerTabData;
            }
        }
        return null;
    }

    public int getInterfaceId() {
        return interfaceId;
    }

    public String getName() {
        return name;
    }

    public int getSpriteId() {
        return spriteId;
    }}
