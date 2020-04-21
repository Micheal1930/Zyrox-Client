package com.varrock.client.content.auction_house;

import java.util.ArrayList;

import com.varrock.client.Client;
import com.varrock.client.RSInterface;
import com.varrock.client.SpriteLoader;
import com.varrock.client.cache.definitions.ItemDefinition;
import com.varrock.client.content.ChatboxItemSearch;

/**
 * Created by Jonny on 9/6/2019
 **/
public class AuctionHouseManager {

    public static ArrayList<AuctionHouseItem> ITEMS = new ArrayList();

    public static void clear() {
        ITEMS.clear();
        update();
    }

    public static boolean isButton(int buttonId) {
        switch(buttonId) {
            case 33130:
                ChatboxItemSearch.SEARCHING_ITEM = true;
                return true;
        }

        return false;
    }

    public static void update() {

        int itemChildId = 33225;

        itemChildId++;

        for(int childId = 0; childId < 21; childId++) {

            boolean visible = childId < ITEMS.size();

            RSInterface.interfaceCache[itemChildId].invisible = !visible;
            RSInterface.interfaceCache[itemChildId + 1].invisible = !visible;
            RSInterface.interfaceCache[itemChildId + 2].invisible = !visible;
            RSInterface.interfaceCache[itemChildId + 3].invisible = !visible;
            RSInterface.interfaceCache[itemChildId + 4].invisible = !visible;
            RSInterface.interfaceCache[itemChildId + 5].invisible = !visible;
            RSInterface.interfaceCache[itemChildId + 6].invisible = !visible;

            AuctionHouseItem auctionHouseItem = visible ? ITEMS.get(childId) : null;

            RSInterface.interfaceCache[itemChildId + 1].inv[0] = auctionHouseItem == null ? -1 : auctionHouseItem.getItemId();
            RSInterface.interfaceCache[itemChildId + 1].invStackSizes[0] = auctionHouseItem == null ? -1 : auctionHouseItem.getAmount();

            if(auctionHouseItem != null) {
                RSInterface.interfaceCache[itemChildId + 2].message = ItemDefinition.forID(auctionHouseItem.getItemId() - 1).name;
                RSInterface.interfaceCache[itemChildId + 3].message = auctionHouseItem.getAuctionPrice() <= 0 ? "" : "Bid: "+Client.formatWithDecimal(auctionHouseItem.getAuctionPrice());
                RSInterface.interfaceCache[itemChildId + 4].message = "Buy: "+Client.formatWithDecimal(auctionHouseItem.getBuyPrice());
                RSInterface.interfaceCache[itemChildId + 5].message = auctionHouseItem.getTimeRemaining();
                RSInterface.interfaceCache[itemChildId + 6].message = auctionHouseItem.getBids()+" bid"+(auctionHouseItem.getBids() > 1 ? "s" : "");
            }

            RSInterface.interfaceCache[itemChildId + 6].extraSprite = auctionHouseItem == null || auctionHouseItem.getTimeRemaining().isEmpty() ? SpriteLoader.sprites[1399] : null;
            RSInterface.interfaceCache[itemChildId + 6].extraSpriteX = 135;
            RSInterface.interfaceCache[itemChildId + 6].extraSpriteY = 51;
            RSInterface.interfaceCache[itemChildId + 6].extraSpriteOpacity = 200;

            itemChildId += 7;

        }
    }

}
