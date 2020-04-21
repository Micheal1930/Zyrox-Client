package com.varrock.client.gameframe;

import com.varrock.client.Sprite;

public interface Gameframe {

	enum Button {
		COMPASS,
		XP_COUNTER,
		PRAYER_ORB,
		RUN_ORB,
		COIN_ORB,
		SUMMONING_ORB,
		TELEPORT_ORB
	}

	void setFixed(boolean fixed);
	boolean getFixed();

	void setCurrentTab(int tab);
	int getCurrentTab();

	//void drawTabArea();
	void handleTabArea();
	void processTabAreaClick();
	void processTabAreaHovers();

	void drawMinimap();
	void markMinimap(Sprite sprite, int x, int y);
	void rightClickMapArea();
	void processMapAreaMouse();

	void drawCoinOrb3DScreen();

	int COMBAT_TAB = 0;
	int STATS_TAB = 1;
	int QUEST_TAB = 2;
	int INVENTORY_TAB = 3;
	int EQUIPMENT_TAB = 4;
	int PRAYER_TAB = 5;
	int MAGIC_TAB = 6;
	int CLAN_CHAT_TAB = 7;
	int FRIENDS_TAB = 8;
	int IGNORES_TAB = 9;
	int LOGOUT_TAB = 10;
	int SETTINGS_TAB = 11;
	int EMOTES_TAB = 12;
	int MUSIC_TAB = -1; // Not sure what the music tab id is.
	int ACHIEVEMENTS_TAB = 14;
	int SUMMONING_TAB = 13;
	int INFORMATION_TAB = 16;

}
