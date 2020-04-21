package com.varrock.client.content.youtube;

import java.util.ArrayList;

import com.varrock.client.Client;
import com.varrock.client.RSInterface;
import com.varrock.client.SpriteLoader;
import com.varrock.client.cache.definitions.ItemDefinition;
import com.varrock.client.content.auction_house.AuctionHouseItem;
import com.varrock.util.TextUtil;

/**
 * Created by Jonny on 10/14/2019
 **/
public class YouTubeManager {

    public static ArrayList<YouTubeVideo> videos = new ArrayList<>();

    public static void addVideo(YouTubeVideo video) {
        videos.add(video);

        update();
    }

    public static void update() {

        int itemChildId = 61215;

        itemChildId++;

        for(int childId = 0; childId < 10; childId++) {

            boolean visible = childId < videos.size();

            RSInterface.interfaceCache[itemChildId].invisible = !visible;
            RSInterface.interfaceCache[itemChildId + 1].invisible = !visible;
            RSInterface.interfaceCache[itemChildId + 2].invisible = !visible;
            RSInterface.interfaceCache[itemChildId + 3].invisible = !visible;
            RSInterface.interfaceCache[itemChildId + 4].invisible = !visible;
            RSInterface.interfaceCache[itemChildId + 5].invisible = !visible;
            RSInterface.interfaceCache[itemChildId + 6].invisible = !visible;

            YouTubeVideo youTubeVideo = visible ? videos.get(childId) : null;

            if(youTubeVideo != null) {
                RSInterface.interfaceCache[itemChildId].setSprite("https://img.youtube.com/vi/" + youTubeVideo.getVideoId() + "/mqdefault.jpg", 115, 64);
                RSInterface.interfaceCache[itemChildId + 1].message = youTubeVideo.getTitle();
                RSInterface.interfaceCache[itemChildId + 2].message = TextUtil.wrapLine(Client.instance.newSmallFont, youTubeVideo.getDescription(), 300);
                RSInterface.interfaceCache[itemChildId + 3].message = youTubeVideo.getUploader();
            }

            itemChildId += 7;

        }
    }
}
