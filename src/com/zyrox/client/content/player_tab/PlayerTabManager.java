package com.zyrox.client.content.player_tab;

import com.zyrox.client.RSInterface;

/**
 * Created by Jonny on 6/7/2019
 **/
public class PlayerTabManager {

    public static PlayerTabData VIEWING_TAB = PlayerTabData.INFORMATION;

    public static boolean isButton(int buttonId) {
        PlayerTabData playerTabData = PlayerTabData.getPlayerTabForButtonId(buttonId);
        if(playerTabData != null) {
            displayTab(playerTabData);
            return true;
        }
        return false;
    }

    public static void displayTab(PlayerTabData playerTab) {
        if(VIEWING_TAB != playerTab) {
            VIEWING_TAB = playerTab;

            for(PlayerTabData playerTabData : PlayerTabData.VALUES) {
                boolean clicked = playerTab == playerTabData;

                RSInterface.interfaceCache[playerTabData.getButtonId()].disabledColor = clicked ? 0x4C4127 : 0x705D49;
            }

            RSInterface.interfaceCache[23508].children[12] = VIEWING_TAB.getInterfaceId();
            RSInterface.interfaceCache[23520].message = VIEWING_TAB.getName();
        }
    }
}
