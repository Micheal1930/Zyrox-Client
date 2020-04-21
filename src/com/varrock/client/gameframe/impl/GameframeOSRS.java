package com.varrock.client.gameframe.impl;

import static com.varrock.client.Client.cacheSprite;

import com.varrock.client.*;
import com.varrock.client.cache.definitions.MobDefinition;
import com.varrock.client.cache.node.Deque;
import com.varrock.client.gameframe.Gameframe;
import com.varrock.client.gameframe.GameframeConstants;

public class GameframeOSRS implements Gameframe {

	private Button buttonHovered = null;
	private static final int SPRITE_OFFSET = 1436;
	private static int[][] FIXED_SIDE_SELECTED_TABS_OLD = {{COMBAT_TAB, 8}, {MAGIC_TAB, 9}, {CLAN_CHAT_TAB, 10}, {SUMMONING_TAB, 11}};
	private static int[][] FIXED_SIDE_ICONS_OLD_DATA = {{972, 14, 8, 17, 9, 3, 0, 44, 35, COMBAT_TAB}, //Icon id, x fixed coordinate, y fixed coordinate, x coordinate, y coordinate, tab x, tab y, tab x2, tab y2, tabId
			{973, 45, 7, 17, 9, 41, 0, 77, 35, STATS_TAB},
			{974, 80, 7, 17, 9, 74, 0, 110, 36, QUEST_TAB},
			{976, 111, 4, 17, 9, 107, 0, 143, 35, INVENTORY_TAB},
			{977, 143, 2, 17, 9, 140, 0, 176, 35, EQUIPMENT_TAB},
			{978, 177, 3, 17, 9, 173, 0, 209, 35, PRAYER_TAB},
			{979, 211, 6, 17, 9, 206, 0, 247, 35, MAGIC_TAB},
			{983, 12, 303, 17, 9, 3, 298, 44, 334, CLAN_CHAT_TAB},
			{981, 46, 305, 17, 9, 41, 298, 77, 334, FRIENDS_TAB},
			{982, 79, 305, 17, 9, 74, 298, 110, 334, IGNORES_TAB},
			{34 + SPRITE_OFFSET, 113, 301, 17, 9, 107, 298, 143, 334, LOGOUT_TAB},
			{984, 145, 304, 17, 9, 140, 298, 176, 334, SETTINGS_TAB},
			{985, 180, 302, 17, 9, 173, 298, 209, 334, EMOTES_TAB},
			{342, 214, 302, 17, 9, 206, 298, 247, 334, SUMMONING_TAB}};
	private static int[][] RESIZ_SIDE_ICONS_OLD_DATA = {
			{972, 0, 6, 17, 9, 3, 0, 44, 35, 0, COMBAT_TAB}, //Icon id, x resiz coordinate small, y resiz coordinate small, x coordinate big, y coordinate big, tab x, tab y, tab x2, tab y2, tabId
			{973, 30, 5, 17, 9, 41, 0, 77, 35, 0, STATS_TAB},
			{974, 65, 5, 17, 9, 74, 0, 110, 36, 0, QUEST_TAB},
			{976, 96, 2, 17, 9, 107, 0, 143, 35, 0, INVENTORY_TAB},
			{977, 128, 0, 17, 9, 140, 0, 176, 35, 0, EQUIPMENT_TAB},
			{978, 162, 1, 17, 9, 173, 0, 209, 35, 0, PRAYER_TAB},
			{979, 195, 4, 17, 9, 206, 0, 247, 35, 0, MAGIC_TAB},

			{983, 0, 2, 17, 9, 3, 298, 44, 334, 1, CLAN_CHAT_TAB},
			{981, 33, 4, 17, 9, 41, 298, 77, 334, 1, FRIENDS_TAB},
			{982, 66, 4, 17, 9, 74, 298, 110, 334, 1, IGNORES_TAB},
			{34 + SPRITE_OFFSET, 100, 0, 17, 9, 107, 298, 143, 334, 1, LOGOUT_TAB},
			{984, 132, 3, 17, 9, 140, 298, 176, 334, 1, SETTINGS_TAB},
			{985, 167, 1, 17, 9, 173, 298, 209, 334, 1, EMOTES_TAB},
			{342, 197, 1, 17, 9, 206, 298, 247, 334, 1, SUMMONING_TAB}
	};
	private String[] tooltips = {"Combat Styles", "Stats", "Quest tab", "Inventory", "Worn Equipment",
			"Prayer List", "Magic Spellbook", "Clan Chat", "Friends List", "Ignore List", "Logout", "Options",
			"Emotes", "Summoning", "Achievements", "Summoning", "Information"};

	private Client client;
	private boolean fixed;
	private int currentTab;

	public GameframeOSRS(Client client, boolean fixed, int currentTab) {
		this.client = client;
		this.fixed = fixed;
		this.currentTab = currentTab;
	}

	@Override
	public boolean getFixed() {
		return fixed;
	}

	@Override
	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}

	@Override
	public int getCurrentTab() {
		return currentTab;
	}

	@Override
	public void setCurrentTab(int currentTab) {
		this.currentTab = currentTab;
	}

	@Override
	public void drawMinimap() {
		int xPosOffset = fixed ? 0 : Client.clientWidth - 246;
		if (fixed) {
			client.mapAreaIP.initDrawingArea();
		}
		if (client.minimapStatus == 2) {
			cacheSprite[67].drawSprite((fixed ? 32 : Client.clientWidth - 162), (fixed ? 9 : 5));
			if (fixed) {
				cacheSprite[SPRITE_OFFSET].drawSprite(xPosOffset, 0);
				client.rightFrameSprite.drawSprite(0, 3);
			} else {
				cacheSprite[28 + SPRITE_OFFSET].drawSprite(Client.clientWidth - 172, 0);
			}
			drawXPButton();
			drawHPOrb();
			drawPrayerOrb();
			drawRunOrb();
			drawSummoningOrb();
			drawWorldMapButton();
			client.compass[0].rotate(33, client.viewRotation, client.anIntArray1057, 256, client.anIntArray968, 25, (fixed ? 4 : 5),
					(fixed ? 29 + xPosOffset : Client.clientWidth - 167), 33, 25);
			client.gameScreenIP.initDrawingArea();
			return;
		}
		int i = client.viewRotation + client.minimapRotation & 0x7ff;
		int xPos = 50 + Client.myPlayer.x / 32;
		int yPos = 466 - Client.myPlayer.y / 32;
		client.miniMap.rotate(fixed ? 156 : 156, i, client.minimapYPosArray, 256 + client.minimapZoom, client.minimapXPosArray, yPos, (fixed ? 7 : 7/* 5 */), (fixed ? 50 : Client.clientWidth - 158), fixed ? 156 : 156, xPos);
		for (int j5 = 0; j5 < client.mapFunctionsLoadedAmt; j5++) {
			int k = (client.mapFunctionTileX[j5] * 4 + 2) - Client.myPlayer.x / 32;
			int i3 = (client.mapFunctionTileY[j5] * 4 + 2) - Client.myPlayer.y / 32;
			try {
				markMinimap(client.currentMapFunctionSprites[j5], k, i3);
				for (int iconI = 0; iconI < client.customMinimapIcons.size(); iconI++) {
					markMinimap(client.customMinimapIcons.get(iconI).getSprite(),
							((client.customMinimapIcons.get(iconI).getX() - Client.baseX) * 4 + 2) - Client.myPlayer.x / 32,
							((client.customMinimapIcons.get(iconI).getY() - Client.baseY) * 4 + 2) - Client.myPlayer.y / 32);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for (int k5 = 0; k5 < 104; k5++) {
			for (int l5 = 0; l5 < 104; l5++) {
				Deque class19 = client.groundArray[client.plane][k5][l5];
				if (class19 != null) {
					int l = (k5 * 4 + 2) - Client.myPlayer.x / 32;
					int j3 = (l5 * 4 + 2) - Client.myPlayer.y / 32;
					markMinimap(client.mapDotItem, l, j3);
				}
			}
		}
		for (int i6 = 0; i6 < client.npcCount; i6++) {
			NPC npc = client.npcArray[client.npcIndices[i6]];
			if (npc != null && npc.isVisible()) {
				MobDefinition entityDef = npc.desc;// to review
				if (entityDef.childrenIDs != null) {
					entityDef = entityDef.getAlteredNPCDef();
				}
				if (entityDef != null && entityDef.drawMinimapDot && entityDef.clickable) {
					int i1 = npc.x / 32 - Client.myPlayer.x / 32;
					int k3 = npc.y / 32 - Client.myPlayer.y / 32;
					markMinimap(client.mapDotNPC, i1, k3);
				}
			}
		}
		for (int j6 = 0; j6 < client.playerCount; j6++) {
			Player player = client.playerArray[client.playerIndices[j6]];
			if (player != null && player.isVisible()) {
				int j1 = player.x / 32 - Client.myPlayer.x / 32;
				int l3 = player.y / 32 - Client.myPlayer.y / 32;
				boolean flag1 = false;
				boolean isInClan = false;
				if (client.clanMembers.contains(player.name))
					isInClan = true;
				long l6 = TextClass.longForName(player.name);
				for (int k6 = 0; k6 < client.friendsCount; k6++) {
					if (l6 != client.friendsListAsLongs[k6] || client.friendsNodeIDs[k6] == 0) {
						continue;
					}
					flag1 = true;
					break;
				}
				boolean flag2 = false;
				if (player.team != 0 && Client.myPlayer.team == player.team) {
					flag2 = true;
				}
				if (flag1)
					markMinimap(client.mapDotFriend, j1, l3);
				else if (isInClan)
					markMinimap(client.mapDotClan, j1, l3);
				else if (flag2)
					markMinimap(client.mapDotTeam, j1, l3);
				else
					markMinimap(client.mapDotPlayer, j1, l3);
			}
		}
		if (client.anInt855 != 0 && Client.loopCycle % 20 < 10) {
			if (client.anInt855 == 1 && client.anInt1222 >= 0 && client.anInt1222 < client.npcArray.length) {
				NPC class30_sub2_sub4_sub1_sub1_1 = client.npcArray[client.anInt1222];
				if (class30_sub2_sub4_sub1_sub1_1 != null) {
					int k1 = class30_sub2_sub4_sub1_sub1_1.x / 32 - Client.myPlayer.x / 32;
					int i4 = class30_sub2_sub4_sub1_sub1_1.y / 32 - Client.myPlayer.y / 32;
					client.drawTargetArrow(client.mapMarker, i4, k1);
				}
			}
			if (client.anInt855 == 2) {
				int l1 = ((client.anInt934 - Client.baseX) * 4 + 2) - Client.myPlayer.x / 32;
				int j4 = ((client.anInt935 - Client.baseY) * 4 + 2) - Client.myPlayer.y / 32;
				client.drawTargetArrow(client.mapMarker, j4, l1);
			}
			if (client.anInt855 == 10 && client.anInt933 >= 0 && client.anInt933 < client.playerArray.length) {
				Player class30_sub2_sub4_sub1_sub2_1 = client.playerArray[client.anInt933];
				if (class30_sub2_sub4_sub1_sub2_1 != null) {
					int i2 = class30_sub2_sub4_sub1_sub2_1.x / 32 - Client.myPlayer.x / 32;
					int k4 = class30_sub2_sub4_sub1_sub2_1.y / 32 - Client.myPlayer.y / 32;
					client.drawTargetArrow(client.mapMarker, k4, i2);
				}
			}
		}
		if (client.destX != 0) {
			int j2 = (client.destX * 4 + 2) - Client.myPlayer.x / 32;
			int l4 = (client.destY * 4 + 2) - Client.myPlayer.y / 32;
			markMinimap(client.mapFlag, j2, l4);
		}

		DrawingArea.drawPixels(3, (fixed ? 83 : 83), (fixed ? 125 + xPosOffset : Client.clientWidth - 83), 0xffffff, 3); // 5 to right, 3 down
		if (fixed) {
			cacheSprite[SPRITE_OFFSET].drawSprite(xPosOffset, 0);
			client.rightFrameSprite.drawSprite(0, 3);
		} else {
			cacheSprite[28 + SPRITE_OFFSET].drawSprite(Client.clientWidth - 182, 0);
		}
		drawXPButton();
		drawHPOrb();
		drawPrayerOrb();
		drawRunOrb();
		drawSummoningOrb();
		drawCoinOrb();
		drawWorldMapButton();
		if (fixed) {
			client.compass[0].rotate(33, client.viewRotation, client.anIntArray1057, 256, client.anIntArray968, 25, 4, 29 + xPosOffset, 33, 25);
		} else {
			cacheSprite[32 + SPRITE_OFFSET].drawRotatedSprite(35, client.viewRotation, 256, 25, 5, Client.clientWidth - 177, 35, 25);
		}
		if (client.menuOpen && client.menuScreenArea == 3) {
			client.drawMenu();
		}
		client.gameScreenIP.initDrawingArea();
	}

	@Override
	public void drawCoinOrb3DScreen() {
		if (client.coinToggle && RSInterface.interfaceCache[8135].message != null) {
			String cash = client.getMoneyInPouch();
			if (fixed) {
				cacheSprite[SPRITE_OFFSET + (buttonHovered == Button.COIN_ORB ? 21 : 20)].drawAdvancedSprite(455, 136);
				client.newSmallFont.drawCenteredString(cash, 487, 154, client.getAmountColor(Long.parseLong(RSInterface.interfaceCache[8135].message)), 0);
			} else {
				client.newSmallFont.drawCenteredString(cash, Client.clientWidth - 90, 184, client.getAmountColor(Long.parseLong(RSInterface.interfaceCache[8135].message)), 0);
			}
		}
	}

	@Override
	public void markMinimap(Sprite sprite, int x, int y) {
		if (sprite == null) {
			return;
		}
		int l = x * x + y * y;
		if (l > 6400) {
			return;
		}
		int k = client.viewRotation + client.minimapRotation & 0x7ff;
		int sin = Model.SINE[k];
		int cosine = Model.COSINE[k];
		sin = (sin * 256) / (client.minimapZoom + 256);
		cosine = (cosine * 256) / (client.minimapZoom + 256);
		int realX = y * sin + x * cosine >> 16;
		int realY = y * cosine - x * sin >> 16;
		int spriteOffsetX = sprite.maxWidth / 2;
		int spriteOffsetY = sprite.maxHeight / 2;
		if (fixed) {
			sprite.drawSprite(realX + 127 - spriteOffsetX,-realY + 84 - spriteOffsetY);
		} else {
			sprite.drawSprite(realX - spriteOffsetX + (Client.clientWidth - 81), -realY - spriteOffsetY + 84);
		}
	}

	@Override
	public void processTabAreaClick() {
		if (fixed) {
			for (int[] tab : FIXED_SIDE_ICONS_OLD_DATA) {
				if (client.clickInRegion(tab[5] + 519, tab[6] + 168, tab[7] + 519, tab[8] + 168)) {
					Client.needDrawTabArea = true;
					currentTab = tab[9];
					Client.tabAreaAltered = true;
				}
			}
		} else {
			if (isBigResized()) {
				int x = Client.clientWidth - 462;
				int y = Client.clientHeight - 37;
				for (int i = 0; i < 14; i++) {
					if (client.clickInRegion(x, y, x += 33, y + 37)) {
						Client.needDrawTabArea = true;
						currentTab = i;
						Client.tabAreaAltered = true;
					}
				}
			} else {
				int x = Client.clientWidth - 231;
				int y = Client.clientHeight - 74;
				for (int i = 0; i < 14; i++) {
					if (client.clickInRegion(x, y, x += 33, y + 37)) {
						Client.needDrawTabArea = true;
						currentTab = i;
						Client.tabAreaAltered = true;
					}
					if (i == 6) {
						y += 37;
						x = Client.clientWidth - 231;
					}
				}
			}
		}

	}

	@Override
	public void processTabAreaHovers() {
		if (fixed) {
			for (int[] tab : FIXED_SIDE_ICONS_OLD_DATA) {
				if (client.mouseInRegion(tab[5] + 519, tab[6] + 168, tab[7] + 519, tab[8] + 168)) {
					client.menuActionName[1] = tooltips[tab[9]];
					client.menuActionID[1] = 1076;
					client.menuActionRow = 2;
					return;
				}
			}
		} else {
			if (isBigResized()) {
				int x = Client.clientWidth - 462;
				int y = Client.clientHeight - 37;
				for (int i = 0; i < 14; i++) {
					if (client.mouseInRegion(x, y, x += 33, y + 37)) {
						client.menuActionName[1] = tooltips[i];
						client.menuActionID[1] = 1076;
						client.menuActionRow = 2;
					}
				}
			} else {
				int x = Client.clientWidth - 231;
				int y = Client.clientHeight - 74;
				for (int i = 0; i < 14; i++) {
					if (client.mouseInRegion(x, y, x += 33, y + 37)) {
						client.menuActionName[1] = tooltips[i];
						client.menuActionID[1] = 1076;
						client.menuActionRow = 2;
					}
					if (i == 6) {
						y += 37;
						x = Client.clientWidth - 231;
					}
				}
			}
		}
	}

	@Override
	public void rightClickMapArea() {
		if (buttonHovered == Button.XP_COUNTER) {
			client.menuActionName[1] = client.showXP ? "Hide counter" : "Show counter";
			client.menuActionID[1] = 1006;
			client.menuActionRow = 2;
		}
		if (buttonHovered == Button.TELEPORT_ORB) {
			client.menuActionName[1] = "Teleports";// this is reg size
			client.menuActionID[1] = 1716;
			client.menuActionRow = 2;
		}
		if (buttonHovered == Button.PRAYER_ORB) {
			String prayerType = (client.prayerInterfaceType == 5608) ? "prayers" : "curses";
			boolean inProcess = (client.tabInterfaceIDs[5] == 17200 || client.tabInterfaceIDs[5] == 17234);
			client.menuActionName[client.menuActionRow] = (inProcess ? "Finish" : "Select") + " " + "quick " + prayerType
					+ (inProcess ? " selection" : "");
			client.menuActionID[client.menuActionRow] = 1046;
			client.menuActionRow++;
			client.menuActionName[client.menuActionRow] = "Turn quick " + prayerType + " " + (client.quickPrsActive ? "off" : "on");
			client.menuActionID[client.menuActionRow] = 1045;
			client.menuActionRow++;
		}
		if (buttonHovered == Button.RUN_ORB) {
			client.menuActionName[client.menuActionRow] = "Rest";
			client.menuActionID[client.menuActionRow] = 1048;
			client.menuActionRow++;
			client.menuActionName[client.menuActionRow] = "Turn run mode " + (client.running ? "off" : "on");
			client.menuActionID[client.menuActionRow] = 1047;
			client.menuActionRow++;
		}
		if (buttonHovered == Button.COMPASS) {
			client.menuActionName[1] = "Face North";
			client.menuActionID[1] = 696;
			client.menuActionRow = 2;
		}
		if (buttonHovered == Button.SUMMONING_ORB) {
			client.menuActionName[5] = "Take BoB";
			client.menuActionID[5] = 1118;
			client.menuActionName[4] = "Cast Special";
			client.menuActionID[4] = 1117;
			client.menuActionName[3] = "Call Familiar";
			client.menuActionID[3] = 1119;
			client.menuActionName[2] = "Renew Familiar";
			client.menuActionID[2] = 1120;
			client.menuActionName[1] = "Dismiss Familiar";
			client.menuActionID[1] = 1121;
			client.menuActionRow = 6;
		}
		if (buttonHovered == Button.COIN_ORB) {
			client.menuActionName[5] = client.coinToggle ? "Hide Money Pouch" : "Show Money Pouch";
			client.menuActionID[5] = 72667;
			client.menuActionName[4] = "@gre@Withdraw 1b Tickets";
			client.menuActionID[4] = 72671;
			client.menuActionName[3] = "Withdraw Money Pouch";
			client.menuActionID[3] = 72666;
			client.menuActionName[2] = "Open Pricechecker";
			client.menuActionID[2] = 72668;
			client.menuActionName[1] = "Examine Money Pouch";
			client.menuActionID[1] = 72669;
			client.menuActionRow = 6;
		}
	}

	@Override
	public void processMapAreaMouse() {
		if (fixed) {
			if (client.mouseInRegion(Client.clientWidth - 249, 21, Client.clientWidth - 249 + 26, 21 + 26)) {
				buttonHovered = Button.XP_COUNTER;
			} else if (client.mouseInRegion(Client.clientWidth - 249, 75, Client.clientWidth - 249 + 56, 75 + 33)) {
				buttonHovered = Button.PRAYER_ORB;
			} else if (client.mouseInRegion(Client.clientWidth - 239, 107, Client.clientWidth - 239 + 56, 107 + 33)) {
				buttonHovered = Button.RUN_ORB;
			} else if (client.mouseInRegion(Client.clientWidth - 217, 132, Client.clientWidth - 217 + 56, 132 + 33)) {
				buttonHovered = Button.SUMMONING_ORB;
			} else if (client.mouseInRegion(Client.clientWidth - 220, 4, Client.clientWidth - 220 + 32, 4 + 32)) {
				buttonHovered = Button.COMPASS;
			} else if (client.mouseInRegion(Client.clientWidth - 53, 117, Client.clientWidth - 53 + 29, 117 + 29)) {
				buttonHovered = Button.TELEPORT_ORB;
			} else if (client.mouseInRegion(Client.clientWidth - 249, 140, Client.clientWidth - 249 + 26, 140 + 26)) {
				buttonHovered = Button.COIN_ORB;
			} else {
				buttonHovered = null;
			}
		} else {
			if (client.mouseInRegion(Client.clientWidth - 211, 27, Client.clientWidth - 211 + 26, 27 + 26)) {
				buttonHovered = Button.XP_COUNTER;
			} else if (client.mouseInRegion(Client.clientWidth - 211, 81, Client.clientWidth - 211 + 56, 81 + 33)) {
				buttonHovered = Button.PRAYER_ORB;
			} else if (client.mouseInRegion(Client.clientWidth - 201, 113, Client.clientWidth - 201 + 56, 113 + 33)) {
				buttonHovered = Button.RUN_ORB;
			} else if (client.mouseInRegion(Client.clientWidth - 179, 138, Client.clientWidth - 179 + 56, 138 + 33)) {
				buttonHovered = Button.SUMMONING_ORB;
			} else if (client.mouseInRegion(Client.clientWidth - 177, 5, Client.clientWidth - 177 + 35, 5 + 35)) {
				buttonHovered = Button.COMPASS;
			} else if (client.mouseInRegion(Client.clientWidth - 34, 123, Client.clientWidth - 34 + 29, 123 + 29)) {
				buttonHovered = Button.TELEPORT_ORB;
			} else if (client.mouseInRegion(Client.clientWidth - 65, 165, Client.clientWidth - 65 + 26, 165 + 26)) {
				buttonHovered = Button.COIN_ORB;
			} else {
				buttonHovered = null;
			}
		}
	}

	private void drawXPButton() {
		int offset = 0;
		if (client.showXP)
			offset = 1;
		int x = fixed ? 0 : Client.clientWidth - 211;
		int y = fixed ? 21 : 27;
		cacheSprite[13 + SPRITE_OFFSET + offset].drawSprite(x, y);
		if (buttonHovered == Button.XP_COUNTER) {
			cacheSprite[15 + SPRITE_OFFSET + offset].drawSprite(x, y);
		}
	}

	private void drawHPOrb() {
        int currentHP = client.currentStats[3] / 10;
        int maxHP = client.currentMaxStats[3] / 10;
        int health = (int) (((double) client.currentStats[3] / (double) client.currentMaxStats[3]) * 100D);
        if (!Client.getOption("constitution")) {
            currentHP = currentHP / 10;
            maxHP = maxHP / 10;
        }
        int x = fixed ? 0 : Client.clientWidth - 211;
        int y = fixed ? 41 : 47;
        cacheSprite[1 + SPRITE_OFFSET].drawSprite(x, y);
        client.newSmallFont.drawCenteredString(Integer.toString(currentHP), x + (fixed ? 15 : 15), y + 26, 0x00ff00, 0);
        if (client.poisoned && !client.venom) {
            Client.cacheSprite[1029].drawSprite(x + (fixed ? 27 : 27), y + 4);
        } else if (!client.poisoned && client.venom) {
            Client.cacheSprite[1030].drawSprite(x + (fixed ? 27 : 27), y + 4);
        } else {
            cacheSprite[4 + SPRITE_OFFSET].drawSprite(x + (fixed ? 27 : 27), y + 4);
        }
        cacheSprite[27 + SPRITE_OFFSET].myHeight = cacheSprite[27 + SPRITE_OFFSET].myWidth * (maxHP - currentHP) / maxHP;
        cacheSprite[27 + SPRITE_OFFSET].drawSprite(x + (fixed ? 27 : 27), y + 4);
        if (health <= 25 && health != 0) {
            cacheSprite[3 + SPRITE_OFFSET].drawAdvancedSprite(x + (fixed ? 33 : 33), y + 10, 125 + (int) (125 * Math.sin(Client.loopCycle / 7.0)));
        } else {
            cacheSprite[3 + SPRITE_OFFSET].drawSprite(x + (fixed ? 33 : 33), y + 10);
        }
    }

	private void drawPrayerOrb() {
		int currentPray = client.currentStats[5] / 10;
		int maxPray = client.currentMaxStats[5] / 10;
		if (currentPray < 0) {
			currentPray = 0;
		}
		if (maxPray <= 0) {
			maxPray = 1;
		}
		int prayer = currentPray * 100 / maxPray;
		int x = fixed ? 0 : Client.clientWidth - 211;
		int y = fixed ? 75 : 81;
		cacheSprite[SPRITE_OFFSET + (buttonHovered == Button.PRAYER_ORB ? 2 : 1)].drawSprite(x, y);
		client.newSmallFont.drawCenteredString(Integer.toString(currentPray), x + (fixed ? 15 : 15), y + 26, GameframeConstants.getOrbColor(currentPray, maxPray), 0);
		if (client.quickPrsActive)
			cacheSprite[8 + SPRITE_OFFSET].drawSprite(x + (fixed ? 27 : 27), y + 4);
		else
			cacheSprite[7 + SPRITE_OFFSET].drawSprite(x + (fixed ? 27 : 27), y + 4);
		cacheSprite[27 + SPRITE_OFFSET].myHeight = cacheSprite[27 + SPRITE_OFFSET].myWidth * (maxPray - currentPray) / maxPray;
		cacheSprite[27 + SPRITE_OFFSET].drawSprite(x + (fixed ? 27 : 27), y + 4);
		if (prayer <= 25 && prayer != 0) {
			cacheSprite[6 + SPRITE_OFFSET].drawAdvancedSprite(x + (fixed ? 30 : 31), y + 7, 125 + (int) (125 * Math.sin(Client.loopCycle / 7.0)));
		} else {
			cacheSprite[6 + SPRITE_OFFSET].drawSprite(x + (fixed ? 30 : 31), y + 7);
		}
	}

	private void drawRunOrb() {
		int currentRun = client.currentEnergy;
		int maxRun = 100;
		int x = fixed ? 10 : Client.clientWidth - 201;
		int y = fixed ? 107 : 113;
		cacheSprite[SPRITE_OFFSET + (buttonHovered == Button.RUN_ORB ? 2 : 1)].drawSprite(x, y);
		client.newSmallFont.drawCenteredString(Integer.toString(currentRun), x + (fixed ? 15 : 15), y + 26, GameframeConstants.getOrbColor(currentRun, maxRun), 0);
		cacheSprite[SPRITE_OFFSET + (client.running ? 12 : 11)].drawSprite(x + (fixed ? 27 : 27), y + 4);
		cacheSprite[27 + SPRITE_OFFSET].myHeight = cacheSprite[27 + SPRITE_OFFSET].myWidth * (maxRun - currentRun) / maxRun;
		cacheSprite[27 + SPRITE_OFFSET].drawSprite(x + (fixed ? 27 : 27), y + 4);
		cacheSprite[SPRITE_OFFSET + (client.running ? 10 : 9)].drawSprite(x + (fixed ? 33 : 34), y + 8);
	}

	private void drawSummoningOrb() {
		int currentSum = client.currentStats[23];
		int maxSum = client.currentMaxStats[23];
		if (currentSum < 0) {
			currentSum = 0;
		}
		if (maxSum <= 0) {
			maxSum = 1;
		}
		int x = fixed ? 32 : Client.clientWidth - 179;
		int y = fixed ? 132 : 138;
		cacheSprite[SPRITE_OFFSET + (buttonHovered == Button.SUMMONING_ORB ? 2 : 1)].drawSprite(x, y);
		client.newSmallFont.drawCenteredString(Integer.toString(currentSum), x + (fixed ? 15 : 15), y + 26, GameframeConstants.getOrbColor(currentSum, maxSum), 0);
		(RSInterface.interfaceCache[14321].mediaID == 4000 ? cacheSprite[26 + SPRITE_OFFSET] : cacheSprite[25 + SPRITE_OFFSET]).drawSprite(x + (fixed ? 27 : 27), y + 4);
		cacheSprite[27 + SPRITE_OFFSET].myHeight = cacheSprite[27 + SPRITE_OFFSET].myWidth * (maxSum - currentSum) / maxSum;
		cacheSprite[27 + SPRITE_OFFSET].drawSprite(x + (fixed ? 27 : 27), y + 4);
		client.orbs[15].drawSprite(x + (fixed ? 32 : 33), y + 8);
	}

	private void drawCoinOrb() {
		int x = fixed ? 0 : Client.clientWidth - 65;
		int y = fixed ? 140 : 166;
		if (client.coinToggle) {
			cacheSprite[SPRITE_OFFSET + (buttonHovered == Button.COIN_ORB ? 21 : 20)].drawAdvancedSprite(x-57, y); // 29
		} else {
			cacheSprite[SPRITE_OFFSET + (buttonHovered == Button.COIN_ORB ? 19 : 18)].drawAdvancedSprite(x, y); // 70
		}
	}

	private void drawWorldMapButton() {
		int x = fixed ? 196 : Client.clientWidth - 34;
		int y = fixed ? 117 : 123;
		cacheSprite[22 + SPRITE_OFFSET].drawSprite(x, y);
		cacheSprite[SPRITE_OFFSET + (buttonHovered == Button.TELEPORT_ORB ? 24 : 23)].drawSprite(x + 4, y + 4);
	}

	private boolean isBigResized() {
		return Client.clientWidth >= client.smallTabs;
	}

	@Override
	public void handleTabArea() {
		int selectedSprite = 12;
		if (fixed) {
			SpriteLoader.sprites[45 + SPRITE_OFFSET].drawSprite(-3, 0);
			for (int[] selectedTabs : FIXED_SIDE_SELECTED_TABS_OLD) {
				if (selectedTabs[0] == currentTab) {
					selectedSprite = selectedTabs[1];
					break;
				}
			}
			for (int[] tabs : FIXED_SIDE_ICONS_OLD_DATA) {
				if (tabs[9] == currentTab) {
					cacheSprite[selectedSprite].drawSprite(tabs[5], tabs[6]);
				}
				cacheSprite[tabs[0]].drawSprite(tabs[1], tabs[2]);
			}
		} else {
			if (isBigResized()) {
				cacheSprite[31 + SPRITE_OFFSET].drawSprite(Client.clientWidth - 462, Client.clientHeight - 37);
				if (client.showTab) {
					cacheSprite[18].drawTransparentSprite(Client.clientWidth - 197, Client.clientHeight - 37 - 267, 150);
					cacheSprite[19].drawSprite(Client.clientWidth - 204, Client.clientHeight - 37 - 274);
				}
				int x = Client.clientWidth - 462;
				x += 33 * currentTab; // offset for selected tab
				cacheSprite[selectedSprite].drawSprite(x, Client.clientHeight - 37);
			} else {
				cacheSprite[30 + SPRITE_OFFSET].drawSprite(Client.clientWidth - 231, Client.clientHeight - 73);
				if (client.showTab) {
					cacheSprite[18].drawTransparentSprite(Client.clientWidth - 197, Client.clientHeight - 74 - 267, 150);
					cacheSprite[19].drawSprite(Client.clientWidth - 204, Client.clientHeight - 74 - 274);
				}
				int x = Client.clientWidth - 231;
				x += 33 * (currentTab % 7);
				int y = Client.clientHeight - 74;
				y += 37 * (currentTab / 7);
				cacheSprite[selectedSprite].drawSprite(x, y);
			}
			for (int[] tabs : RESIZ_SIDE_ICONS_OLD_DATA) {
				if (tabs[9] == 1) {
					cacheSprite[tabs[0]].drawSprite(Client.clientWidth - 226 + tabs[1], Client.clientHeight - 34 + tabs[2]); // Lower
				} else {
					cacheSprite[tabs[0]].drawSprite(Client.clientWidth - (isBigResized() ? 455 : 224) + tabs[1], Client.clientHeight - (isBigResized() ? 35 : 71) + tabs[2]); // Upper
				}
			}
		}
	}


}