package com.zyrox.client.content;

import club.minnced.discord.rpc.*;

public class RichPresence {

    private final String CLIENT_ID = "685496763624587291";

    private DiscordRPC lib;
    private DiscordRichPresence presence;

    public void initiate() {
        lib = DiscordRPC.INSTANCE;
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        lib.Discord_Initialize(CLIENT_ID, handlers, true, "");
        presence = new DiscordRichPresence();
        presence.startTimestamp = System.currentTimeMillis() / 1000;
        presence.largeImageKey = "Zyrox";
        presence.smallImageKey = "Zyrox";
        presence.details = getPlayercount() + " PLAYERS ONLINE";
        presence.state = "Zyrox.io";
        updatePresence();
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                lib.Discord_RunCallbacks();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {}
            }
        }, "RPC-Callback-Handler").start();
    }

    
    public void reloadPresence(){
    	presence.details = getPlayercount() + " PLAYERS ONLINE";
    	presence.state = "Zyrox.IO";
    }
    public static String getPlayercount(){
    	return "212";
    }
    
    public boolean presenceIsNull() {
        return presence == null;
    }

    public void updateDetails(String details) {
        presence.details = details;
        updatePresence();
    }

    public void updateState(String state) {
        presence.state = state;
        updatePresence();
    }

    public void updateSmallImageKey(String key) {
        presence.smallImageKey = key;
        updatePresence();
    }

    private void updatePresence() {
        lib.Discord_UpdatePresence(presence);
    }
}
