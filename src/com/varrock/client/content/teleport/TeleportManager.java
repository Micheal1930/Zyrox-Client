package com.varrock.client.content.teleport;

import java.util.ArrayList;

import com.varrock.client.Client;
import com.varrock.client.RSInterface;
import com.varrock.util.TextUtil;

/**
 * Created by Jonny on 6/20/2019
 **/
public class TeleportManager {

    public static ArrayList<TeleportContainer> TELEPORTS = new ArrayList<>();

    public static void clearTeleports() {
        TELEPORTS.clear();
    }

    public static void addTeleport(String name, int wildernessLevel, int spriteId, boolean multi) {
        if(TELEPORTS.size() >= 30) {
            System.out.println("Teleports can not exceed 30 entries.");
            return;
        }
        TeleportContainer teleportContainer = new TeleportContainer(name, wildernessLevel, spriteId, multi);
        TELEPORTS.add(teleportContainer);

        updateTeleports();
    }

    public static void updateTeleports() {

        int teleportId = 51_101;
        int teleportIndex = 0;
        for(int childId = 0; childId < 30; childId++) {

            boolean visible = childId < TELEPORTS.size();

            RSInterface.interfaceCache[teleportId].invisible = !visible;
            RSInterface.interfaceCache[teleportId + 1].invisible = !visible;
            RSInterface.interfaceCache[teleportId + 2].invisible = !visible;
            RSInterface.interfaceCache[teleportId + 3].invisible = !visible;
            RSInterface.interfaceCache[teleportId + 4].invisible = !visible;

            TeleportContainer teleportContainer = visible ? TELEPORTS.get(childId) : null;

            if(teleportContainer != null) {

                RSInterface.interfaceCache[teleportId + 1].disabledSprite = Client.cacheSprite[teleportContainer.getSpriteId()];
                RSInterface.interfaceCache[teleportId + 1].enabledSprite = Client.cacheSprite[teleportContainer.getSpriteId()];

                String teleportName = TextUtil.wrapLine(Client.instance.newSmallFont, teleportContainer.getTeleportName(), 55);

                boolean textNeedsMoved = teleportName.contains("\\n");

                if(teleportName.startsWith("\\n")) {
                    teleportName = teleportName.substring(2);
                }

                RSInterface.interfaceCache[teleportId + 2].message = teleportName;

                RSInterface.interfaceCache[teleportId + 3].message = teleportContainer.getWildernessLevel() > 0 && teleportContainer.getWildernessLevel() < 60 ? String.valueOf(teleportContainer.getWildernessLevel()) : "";

                int y = (textNeedsMoved ? 11 : 17) + (95 * (childId / 3));

                if(y > 0)
                    RSInterface.interfaceCache[51_100].childY[teleportIndex + 2] = y;

                RSInterface.interfaceCache[teleportId + 4].invisible = !teleportContainer.isMulti();

            }

            teleportId += 5;
            teleportIndex += 5;

        }
    }

}
