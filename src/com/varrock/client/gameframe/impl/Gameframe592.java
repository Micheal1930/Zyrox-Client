package com.varrock.client.gameframe.impl;

import com.varrock.client.*;
import com.varrock.client.cache.definitions.MobDefinition;
import com.varrock.client.cache.node.Deque;
import com.varrock.client.content.player_tab.PlayerTabData;
import com.varrock.client.content.player_tab.PlayerTabManager;
import com.varrock.client.gameframe.Gameframe;

public class Gameframe592 implements Gameframe {

	private int[] TAB_DRAW = {0, 1, 2, 14, 3, 4, 5, 6, 15, 8, 9, 7, 11, 12, 13, 16};
	private int[] TAB_POS = {0, 30, 60, 90, 120, 150, 180, 210, 0, 30, 60, 90, 120, 150, 0, 210};
	private int[] SIDEICONS_TAB = {0, 1, 2, 14, 3, 4, 5, 6, 15, 8, 9, 7, 11, 12, 13, 16};
	private int[] SIDEICONS_POSITION_X = {8, 37, 67, 97, 127, 159, 187, 217, 7, 38, 69, 97, 127, 157, 8, 217};
	private int[] SIDEICONS_POSITION_Y = {9, 9, 8, 8, 8, 8, 8, 8, 307, 306, 306, 307, 306, 306, 304, 308};
	// private int[] SIDEICONS_OLD_FRAME_POSITIONX = {13, 46, 80, 111, 111, 144, 178, 212, 9, 46, 81, 112, 145, 178, 212, 217};
	// private int[] SIDEICONS_OLD_FRAME_POSITIONY = {9, 8, 8, 7, 5, 3, 4, 8, 304, 306, 306, 307, 306, 306, 306, 308};
	private int[] id = {20, 89, 21, 22, 23, 24, 25, 26, 95, 28, 29, 27, 31, 32, 33, 90, 149};
	private int[] tab = {0, 1, 2, 14, 3, 4, 5, 6, 15, 8, 9, 7, 11, 12, 13, 16};
	private int[] positionX = {0, 29, 59, 89, 119, 151, 179, 209, 0, 29, 59, 89, 119, 151, 179, 209};
	//private int[] positionX = {8, 37, 67, 97, 127, 159, 187, 217, 7, 38, 69, 97, 127, 157, 187, 217};
	private int[] positionY = {9, 9, 8, 8, 8, 8, 8, 8, /* second row */ 8, 8, 8, 9, 8, 8, 8, 9};

	private int[] tabs = {
			COMBAT_TAB,
			STATS_TAB,
			QUEST_TAB,
			ACHIEVEMENTS_TAB,
			INVENTORY_TAB,
			EQUIPMENT_TAB,
			PRAYER_TAB,
			MAGIC_TAB,
			// SECOND HALF
			SUMMONING_TAB,
			FRIENDS_TAB,
			IGNORES_TAB,
			CLAN_CHAT_TAB,
			SETTINGS_TAB,
			EMOTES_TAB,
			INFORMATION_TAB,
			LOGOUT_TAB,
	};

	private Client client;
	private boolean fixed;
	private int currentTab;

	public Gameframe592(Client client, boolean fixed, int currentTab) {
		this.client = client;
		this.fixed = fixed;
		this.currentTab = currentTab;
	}

	@Override
	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}

	@Override
	public boolean getFixed() {
		return fixed;
	}

	@Override
	public void setCurrentTab(int tab) {
		this.currentTab = tab;
	}

	@Override
	public int getCurrentTab() {
		return currentTab;
	}

	@Override
	public void processTabAreaClick() {
		if (client.clickInRegion(Client.clientWidth - 21, 0, Client.clientWidth, 21) && client.tabInterfaceIDs[10] != -1) {
			Client.needDrawTabArea = true;
			currentTab = 10;
			Client.tabAreaAltered = true;
		}
		if (fixed) {
			int positionX = Client.clientWidth - 244;
			int positionY = 169;
			if (client.clickMode3 == 1) {
				if (client.clickInRegion(positionX, positionY, positionX + 30, positionY + 36)
						&& client.tabInterfaceIDs[0] != -1) {
					Client.needDrawTabArea = true;
					currentTab = 0;
					Client.tabAreaAltered = true;
				}
				positionX += 30;
				if (client.clickInRegion(positionX, positionY, positionX + 30, positionY + 36)
						&& client.tabInterfaceIDs[1] != -1) {
					Client.needDrawTabArea = true;
					currentTab = 1;
					Client.tabAreaAltered = true;
				}
				positionX += 30;
				if (client.clickInRegion(positionX, positionY, positionX + 30, positionY + 36)
						&& client.tabInterfaceIDs[2] != -1) {
					Client.needDrawTabArea = true;
					currentTab = 2;
					Client.tabAreaAltered = true;
				}
				positionX += 30;
				if (client.clickInRegion(positionX, positionY, positionX + 30, positionY + 36)
						&& client.tabInterfaceIDs[14] != -1) {
					Client.needDrawTabArea = true;
					currentTab = 14;
					Client.tabAreaAltered = true;
				}
				positionX += 30;
				if (client.clickInRegion(positionX, positionY, positionX + 30, positionY + 36)
						&& client.tabInterfaceIDs[3] != -1) {
					Client.needDrawTabArea = true;
					currentTab = 3;
					Client.tabAreaAltered = true;
				}
				positionX += 30;
				if (client.clickInRegion(positionX, positionY, positionX + 30, positionY + 36)
						&& client.tabInterfaceIDs[4] != -1) {
					Client.needDrawTabArea = true;
					currentTab = 4;
					Client.tabAreaAltered = true;
				}
				positionX += 30;
				if (client.clickInRegion(positionX, positionY, positionX + 30, positionY + 36)
						&& client.tabInterfaceIDs[5] != -1) {
					Client.needDrawTabArea = true;
					currentTab = 5;
					Client.tabAreaAltered = true;
				}
				positionX += 30;
				if (client.clickInRegion(positionX, positionY, positionX + 30, positionY + 36)
						&& client.tabInterfaceIDs[6] != -1) {
					Client.needDrawTabArea = true;
					currentTab = 6;
					Client.tabAreaAltered = true;
				}
				positionX = Client.clientWidth - 244;
				positionY = Client.clientHeight - 36; // 89
				if (client.clickInRegion(positionX, positionY, positionX + 30, positionY + 36)
						&& client.tabInterfaceIDs[13] != -1) {
					Client.needDrawTabArea = true;
					currentTab = 13;
					Client.tabAreaAltered = true;
				}
				positionX += 30;
				if (client.clickInRegion(positionX, positionY, positionX + 30, positionY + 36)
						&& client.tabInterfaceIDs[8] != -1) {
					Client.needDrawTabArea = true;
					currentTab = 8;
					Client.tabAreaAltered = true;
				}
				positionX += 30;
				if (client.clickInRegion(positionX, positionY, positionX + 30, positionY + 36)
						&& client.tabInterfaceIDs[9] != -1) {
					Client.needDrawTabArea = true;
					currentTab = 9;
					Client.tabAreaAltered = true;
				}
				positionX += 30;
				if (client.clickInRegion(positionX, positionY, positionX + 30, positionY + 36)
						&& client.tabInterfaceIDs[7] != -1) {
					Client.needDrawTabArea = true;
					currentTab = 7;
					Client.tabAreaAltered = true;
				}
				positionX += 30;

				if (client.clickInRegion(positionX, positionY, positionX + 30, positionY + 36)
						&& client.tabInterfaceIDs[11] != -1) {
					Client.needDrawTabArea = true;
					currentTab = 11;
					Client.tabAreaAltered = true;
				}
				positionX += 30;
				if (client.clickInRegion(positionX, positionY, positionX + 30, positionY + 36)
						&& client.tabInterfaceIDs[12] != -1) {
					Client.needDrawTabArea = true;
					currentTab = 12;
					Client.tabAreaAltered = true;
				}
				positionX += 30;
                   /* if (Client.clickInRegion(positionX, positionY, positionX + 30, positionY + 36)
                            &&client.tabInterfaceIDs[13] != -1) {
                        Client.needDrawTabArea = true;
                        currentTab = 13;
                        Client.tabAreaAltered = true;
                    }*/
				positionX += 30;
				if (client.clickInRegion(positionX, positionY, positionX + 30, positionY + 36)
						&& client.tabInterfaceIDs[16] != -1) {
					Client.needDrawTabArea = true;
					currentTab = 16;
					Client.tabAreaAltered = true;
				}
			}
		} else {

			int offsetX = (Client.clientWidth >= client.smallTabs ? Client.clientWidth - 480 : Client.clientWidth - 240);
			int positionY = (Client.clientWidth >= client.smallTabs ? Client.clientHeight - 37 : Client.clientHeight - 74);
			int secondPositionY = Client.clientHeight - 37;
			int secondOffsetX = Client.clientWidth >= client.smallTabs ? 240 : 0;
			if (client.clickMode3 == 1) {
				if (client.clickInRegion(positionX[0] + offsetX, positionY, positionX[0] + offsetX + 30, positionY + 37)
						&& client.tabInterfaceIDs[tab[0]] != -1) {
					if (currentTab == tab[0]) {
						client.showTab = !client.showTab;
					} else {
						client.showTab = true;
					}
					currentTab = tab[0];
					Client.needDrawTabArea = true;
					Client.tabAreaAltered = true;
				} else if (client.clickInRegion(positionX[1] + offsetX, positionY, positionX[1] + offsetX + 30, positionY + 37)
						&& client.tabInterfaceIDs[tab[1]] != -1) {
					if (currentTab == tab[1]) {
						client.showTab = !client.showTab;
					} else {
						client.showTab = true;
					}
					currentTab = tab[1];
					Client.needDrawTabArea = true;
					Client.tabAreaAltered = true;
				} else if (client.clickInRegion(positionX[2] + offsetX, positionY, positionX[2] + offsetX + 30, positionY + 37)
						&& client.tabInterfaceIDs[tab[2]] != -1) {
					if (currentTab == tab[2]) {
						client.showTab = !client.showTab;
					} else {
						client.showTab = true;
					}
					currentTab = tab[2];
					Client.needDrawTabArea = true;
					Client.tabAreaAltered = true;
				} else if (client.clickInRegion(positionX[3] + offsetX, positionY, positionX[3] + offsetX + 30, positionY + 37)
						&& client.tabInterfaceIDs[tab[3]] != -1) {
					if (currentTab == tab[3]) {
						client.showTab = !client.showTab;
					} else {
						client.showTab = true;
					}
					currentTab = tab[3];
					Client.needDrawTabArea = true;
					Client.tabAreaAltered = true;
				} else if (client.clickInRegion(positionX[4] + offsetX, positionY, positionX[4] + offsetX + 30, positionY + 37)
						&& client.tabInterfaceIDs[tab[4]] != -1) {
					if (currentTab == tab[4]) {
						client.showTab = !client.showTab;
					} else {
						client.showTab = true;
					}
					currentTab = tab[4];
					Client.needDrawTabArea = true;
					Client.tabAreaAltered = true;
				} else if (client.clickInRegion(positionX[5] + offsetX, positionY, positionX[5] + offsetX + 30, positionY + 37)
						&& client.tabInterfaceIDs[tab[5]] != -1) {
					if (currentTab == tab[5]) {
						client.showTab = !client.showTab;
					} else {
						client.showTab = true;
					}
					currentTab = tab[5];
					Client.needDrawTabArea = true;
					Client.tabAreaAltered = true;
				} else if (client.clickInRegion(positionX[6] + offsetX, positionY, positionX[6] + offsetX + 30, positionY + 37)
						&& client.tabInterfaceIDs[tab[6]] != -1) {
					if (currentTab == tab[6]) {
						client.showTab = !client.showTab;
					} else {
						client.showTab = true;
					}
					currentTab = tab[6];
					Client.needDrawTabArea = true;
					Client.tabAreaAltered = true;
				} else if (client.clickInRegion(positionX[7] + offsetX, positionY, positionX[7] + offsetX + 30, positionY + 37)
						&& client.tabInterfaceIDs[tab[7]] != -1) {
					if (currentTab == tab[7]) {
						client.showTab = !client.showTab;
					} else {
						client.showTab = true;
					}
					currentTab = tab[7];
					Client.needDrawTabArea = true;
					Client.tabAreaAltered = true;
				} else if (client.clickInRegion(positionX[8] + offsetX + secondOffsetX, secondPositionY,
						positionX[8] + offsetX + secondOffsetX + 30, secondPositionY + 37)) {
					if (currentTab == tab[8]) {
						client.showTab = !client.showTab;
					} else {
						client.showTab = true;
					}
					currentTab = tab[8];
					Client.needDrawTabArea = true;
					Client.tabAreaAltered = true;
				} else if (client.clickInRegion(positionX[9] + offsetX + secondOffsetX, secondPositionY,
						positionX[9] + offsetX + secondOffsetX + 30, secondPositionY + 37)) {
					if (currentTab == tab[9]) {
						client.showTab = !client.showTab;
					} else {
						client.showTab = true;
					}
					currentTab = tab[9];
					Client.needDrawTabArea = true;
					Client.tabAreaAltered = true;
				} else if (client.clickInRegion(positionX[10] + offsetX + secondOffsetX, secondPositionY,
						positionX[10] + offsetX + secondOffsetX + 30, secondPositionY + 37)) {
					if (currentTab == tab[10]) {
						client.showTab = !client.showTab;
					} else {
						client.showTab = true;
					}
					currentTab = tab[10];
					Client.needDrawTabArea = true;
					Client.tabAreaAltered = true;
				} else if (client.clickInRegion(positionX[11] + offsetX + secondOffsetX, secondPositionY,
						positionX[11] + offsetX + secondOffsetX + 30, secondPositionY + 37)) {
					if (currentTab == tab[11]) {
						client.showTab = !client.showTab;
					} else {
						client.showTab = true;
					}
					currentTab = tab[11];
					Client.needDrawTabArea = true;
					Client.tabAreaAltered = true;
				} else if (client.clickInRegion(positionX[12] + offsetX + secondOffsetX, secondPositionY,
						positionX[12] + offsetX + secondOffsetX + 30, secondPositionY + 37)) {
					if (currentTab == tab[12]) {
						client.showTab = !client.showTab;
					} else {
						client.showTab = true;
					}
					currentTab = tab[12];
					Client.needDrawTabArea = true;
					Client.tabAreaAltered = true;
				} else if (client.clickInRegion(positionX[13] + offsetX + secondOffsetX, secondPositionY,
						positionX[13] + offsetX + secondOffsetX + 30, secondPositionY + 37)) {
					if (currentTab == tab[13]) {
						client.showTab = !client.showTab;
					} else {
						client.showTab = true;
					}
					currentTab = tab[13];
					Client.needDrawTabArea = true;
					Client.tabAreaAltered = true;
/*                } else if (Client.clickInRegion(positionX[14] + offsetX + secondOffsetX, secondPositionY,
                        positionX[14] + offsetX + secondOffsetX + 30, secondPositionY + 37)) {
                    if (currentTab == tab[14]) {
                        showTab = !showTab;
                    } else {
                        showTab = true;
                    }
                    currentTab = tab[14];
                    Client.needDrawTabArea = true;
                    Client.tabAreaAltered = true;*/
				}/* else if (Client.clickInRegion(positionX[15] + offsetX + secondOffsetX, secondPositionY,
                        positionX[15] + offsetX + secondOffsetX + 30, secondPositionY + 37)) {
                    if (currentTab == tab[15]) {
                        showTab = !showTab;
                    } else {
                        showTab = true;
                    }
                    currentTab = tab[15];
                    Client.needDrawTabArea = true;
                    Client.tabAreaAltered = true;
                }*/
			}
		}
	}

	@Override
	public void processTabAreaHovers() {
		// Moneypouch hover
		int orbX = getCoinOrbX();
		int orbY = getCoinOrbY();
		if (fixed ? client.mouseInRegion(515, 85, 515 + 34, 85 + 34)
				: client.mouseInRegion(orbX, orbY, orbX + 34, orbY + 34)) {
			client.menuActionName[4] = client.coinToggle ? "Hide Money Pouch" : "Show Money Pouch";
			client.menuActionID[4] = 72667;
			client.menuActionName[3] = "Withdraw Money Pouch";
			client.menuActionID[3] = 72666;
			client.menuActionName[2] = "Open Pricechecker";
			client.menuActionID[2] = 72668;
			client.menuActionName[1] = "Examine Money Pouch";
			client.menuActionID[1] = 72669;
			client.menuActionRow = 5;
		}
		if (fixed) {
			int x = Client.clientWidth - 244;
			int y = 169;
			boolean hoveringTab = false;

			for (int i = 0; i < tabs.length - 1; i++) {
				if (client.mouseInRegion(x, y, x + 30, y + 36)) {
					Client.needDrawTabArea = true;
					client.tabHover = tabs[i];
					processTabAreaTooltips();
					Client.tabAreaAltered = true;
					hoveringTab = true;
					break;
				}
				x += 30;
				if (i == 7) {
					x = Client.clientWidth - 244;
					y = Client.clientHeight - 36;
				}
			}

			if (client.mouseInRegion(Client.clientWidth - 21, 0, Client.clientWidth, 21)) {
				client.tabHover = LOGOUT_TAB;
				processTabAreaTooltips();
				return;
			}

			if (!hoveringTab) {
				Client.needDrawTabArea = true;
				client.tabHover = -1;
				Client.tabAreaAltered = true;
			}
		} else {
			int offsetX = (Client.clientWidth >= client.smallTabs ? Client.clientWidth - 480 : Client.clientWidth - 240);
			int positionY = (Client.clientWidth >= client.smallTabs ? Client.clientHeight - 37 : Client.clientHeight - 74);
			int secondPositionY = Client.clientHeight - 37;
			int secondOffsetX = Client.clientWidth >= client.smallTabs ? 240 : 0;
			if (client.mouseInRegion(Client.clientWidth - 21, 0, Client.clientWidth, 21)) {
				client.tabHover = 10;
				processTabAreaTooltips();
			} else if (client.mouseInRegion(positionX[0] + offsetX, positionY, positionX[0] + offsetX + 30, positionY + 37)) {
				client.tabHover = tab[0];
				processTabAreaTooltips();
				Client.needDrawTabArea = true;
				Client.tabAreaAltered = true;
			} else if (client.mouseInRegion(positionX[1] + offsetX, positionY, positionX[1] + offsetX + 30, positionY + 37)) {
				client.tabHover = tab[1];
				processTabAreaTooltips();
				Client.needDrawTabArea = true;
				Client.tabAreaAltered = true;
			} else if (client.mouseInRegion(positionX[2] + offsetX, positionY, positionX[2] + offsetX + 30, positionY + 37)) {
				client.tabHover = tab[2];
				processTabAreaTooltips();
				Client.needDrawTabArea = true;
				Client.tabAreaAltered = true;
			} else if (client.mouseInRegion(positionX[3] + offsetX, positionY, positionX[3] + offsetX + 30, positionY + 37)) {
				client.tabHover = tab[3];
				processTabAreaTooltips();
				Client.needDrawTabArea = true;
				Client.tabAreaAltered = true;
			} else if (client.mouseInRegion(positionX[4] + offsetX, positionY, positionX[4] + offsetX + 30, positionY + 37)) {
				client.tabHover = tab[4];
				processTabAreaTooltips();
				Client.needDrawTabArea = true;
				Client.tabAreaAltered = true;
			} else if (client.mouseInRegion(positionX[5] + offsetX, positionY, positionX[5] + offsetX + 30, positionY + 37)) {
				client.tabHover = tab[5];
				processTabAreaTooltips();
				Client.needDrawTabArea = true;
				Client.tabAreaAltered = true;
			} else if (client.mouseInRegion(positionX[6] + offsetX, positionY, positionX[6] + offsetX + 30, positionY + 37)) {
				client.tabHover = tab[6];
				processTabAreaTooltips();
				Client.needDrawTabArea = true;
				Client.tabAreaAltered = true;
			} else if (client.mouseInRegion(positionX[7] + offsetX, positionY, positionX[7] + offsetX + 30, positionY + 37)) {
				client.tabHover = tab[7];
				processTabAreaTooltips();
				Client.needDrawTabArea = true;
				Client.tabAreaAltered = true;
			} else if (client.mouseInRegion(positionX[8] + offsetX + secondOffsetX, secondPositionY,
					positionX[8] + offsetX + secondOffsetX + 30, secondPositionY + 37)) {
				client.tabHover = tab[8];
				processTabAreaTooltips();
				Client.needDrawTabArea = true;
				Client.tabAreaAltered = true;
			} else if (client.mouseInRegion(positionX[9] + offsetX + secondOffsetX, secondPositionY,
					positionX[9] + offsetX + secondOffsetX + 30, secondPositionY + 37)) {
				client.tabHover = tab[9];
				processTabAreaTooltips();
				Client.needDrawTabArea = true;
				Client.tabAreaAltered = true;
			} else if (client.mouseInRegion(positionX[10] + offsetX + secondOffsetX, secondPositionY,
					positionX[10] + offsetX + secondOffsetX + 30, secondPositionY + 37)) {
				client.tabHover = tab[10];
				processTabAreaTooltips();
				Client.needDrawTabArea = true;
				Client.tabAreaAltered = true;
			} else if (client.mouseInRegion(positionX[11] + offsetX + secondOffsetX, secondPositionY,
					positionX[11] + offsetX + secondOffsetX + 30, secondPositionY + 37)) {
				client.tabHover = tab[11];
				processTabAreaTooltips();
				Client.needDrawTabArea = true;
				Client.tabAreaAltered = true;
			} else if (client.mouseInRegion(positionX[12] + offsetX + secondOffsetX, secondPositionY,
					positionX[12] + offsetX + secondOffsetX + 30, secondPositionY + 37)) {
				client.tabHover = tab[12];
				processTabAreaTooltips();
				Client.needDrawTabArea = true;
				Client.tabAreaAltered = true;
			} else if (client.mouseInRegion(positionX[13] + offsetX + secondOffsetX, secondPositionY,
					positionX[13] + offsetX + secondOffsetX + 30, secondPositionY + 37)) {
				client.tabHover = tab[13];
				processTabAreaTooltips();
				Client.needDrawTabArea = true;
				Client.tabAreaAltered = true;
			/*} else if (client.mouseInRegion(positionX[14] + offsetX + secondOffsetX, secondPositionY,
					positionX[14] + offsetX + secondOffsetX + 30, secondPositionY + 37)) {
				client.tabHover = tab[14];
				processTabAreaHovers();
				Client.needDrawTabArea = true;
				Client.tabAreaAltered = true;
			} else if (client.mouseInRegion(positionX[15] + offsetX + secondOffsetX, secondPositionY,
					positionX[15] + offsetX + secondOffsetX + 30, secondPositionY + 37)) {
				client.tabHover = tab[15];
				processTabAreaHovers();
				Client.needDrawTabArea = true;
				Client.tabAreaAltered = true;*/
			} else {
				client.tabHover = -1;
				Client.needDrawTabArea = true;
				Client.tabAreaAltered = true;
			}
		}
	}

	@Override
	public void drawMinimap() {
		int xPosOffset = fixed ? 0 : Client.clientWidth - 246;
		if (fixed) {
			client.mapAreaIP.initDrawingArea();
			//drawSpecOrb();
		}
		if (client.minimapStatus == 2) {
			Client.cacheSprite[67].drawSprite((fixed ? 32 : Client.clientWidth - 162), (fixed ? 9 : 5));
			if (fixed) {
				Client.cacheSprite[6].drawSprite(0, 0);
				client.rightFrameSprite.drawSprite(0, 3);
				//drawSpecOrb();
			} else {
				Client.cacheSprite[36].drawSprite(Client.clientWidth - 167, 0);
				Client.cacheSprite[37].drawSprite(Client.clientWidth - 172, 0);
			}
			Client.cacheSprite[38].drawSprite(fixed ? -1 : Client.clientWidth - 188, fixed ? 46 : 40);
			if (client.hoverPos == 0) {
				Client.cacheSprite[39].drawSprite(fixed ? -1 : Client.clientWidth - 188, fixed ? 46 : 40);
			}
			Client.cacheSprite[30].drawSprite((fixed ? 246 : Client.clientWidth) - 21, 0);
			if (client.tabHover != -1) {
				if (client.tabHover == 10) {
					Client.cacheSprite[34].drawSprite((fixed ? 246 : Client.clientWidth) - 21, 0);
				}
			}
			if (client.tabInterfaceIDs[currentTab] != -1) {
				if (currentTab == 10) {
					Client.cacheSprite[35].drawSprite((fixed ? 246 : Client.clientWidth) - 21, 0);
				}
			}
			drawHPOrb();
			drawPrayerOrb();
			drawRunOrb();
			drawSummoningOrb();
			client.compass[0].rotate(33, client.viewRotation, client.anIntArray1057, 256, client.anIntArray968, 25, (fixed ? 8 : 5),
					(fixed ? 8 + xPosOffset : Client.clientWidth - 167), 33, 25);
			client.gameScreenIP.initDrawingArea();
			return;
		}
		int i = client.viewRotation + client.minimapRotation & 0x7ff;
		int j = 48 + Client.myPlayer.x / 32;
		int l2 = 464 - Client.myPlayer.y / 32;
		client.miniMap.rotate(fixed ? 156 : 156, i, client.minimapYPosArray, 256 + client.minimapZoom, client.minimapXPosArray, l2,
				(fixed ? 8 : 4/* 5 */), (fixed ? 32 : Client.clientWidth - 162),
				fixed ? 156 : 156, j);
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
		DrawingArea.drawPixels(3, (fixed ? 84 : 80), (fixed ? 107 + xPosOffset : Client.clientWidth - 88),
				0xffffff, 3);

		if (fixed) {
			Client.cacheSprite[6].drawSprite(xPosOffset, 0);
			client.rightFrameSprite.drawSprite(0, 3);
			//drawSpecOrb();
		} else {
			Client.cacheSprite[36].drawSprite(Client.clientWidth - 167, 0);
			Client.cacheSprite[37].drawSprite(Client.clientWidth - 172, 0);
		}
		Client.cacheSprite[38].drawSprite(fixed ? -1 : Client.clientWidth - 217, fixed ? 46 : 3);
		if (client.hoverPos == 0) {
			Client.cacheSprite[39].drawSprite(fixed ? -1 : Client.clientWidth - 217, fixed ? 46 : 3);
		}
		Client.cacheSprite[30].drawSprite((fixed ? 246 : Client.clientWidth) - 21, 0);
		if (client.tabHover != -1) {
			if (client.tabHover == 10) {
				Client.cacheSprite[34].drawSprite((fixed ? 246 : Client.clientWidth) - 21, 0);
			}
		}
		if (client.tabInterfaceIDs[currentTab] != -1) {
			if (currentTab == 10) {
				Client.cacheSprite[35].drawSprite((fixed ? 246 : Client.clientWidth) - 21, 0);
			}
		}
		drawHPOrb();
		drawPrayerOrb();
		drawRunOrb();
		drawSummoningOrb();
		drawCoinOrb();
		//drawTeleIcon();
		client.compass[0].rotate(33, client.viewRotation, client.anIntArray1057, 256, client.anIntArray968, 25, (fixed ? 8 : 5),
				(fixed ? 8 + xPosOffset : Client.clientWidth - 167), 33, 25);
		if (client.menuOpen && client.menuScreenArea == 3) {
			client.drawMenu();
		}
		client.gameScreenIP.initDrawingArea();
	}

	@Override
	public void markMinimap(Sprite sprite, int x, int y) {
		if (sprite == null) {
			return;
		}
		try {
			int offX = fixed ? 0 : Client.clientWidth - 249;
			int k = client.viewRotation + client.minimapRotation & 0x7ff;
			int l = x * x + y * y;
			if (l > 6400) {
				return;
			}
			int i1 = Model.SINE[k];
			int j1 = Model.COSINE[k];
			i1 = (i1 * 256) / (client.minimapZoom + 256);
			j1 = (j1 * 256) / (client.minimapZoom + 256);
			int k1 = y * i1 + x * j1 >> 16;
			int l1 = y * j1 - x * i1 >> 16;
			if (fixed) {
				sprite.drawSprite(((105 + k1) - sprite.maxWidth / 2) + 4 + offX, 88 - l1 - sprite.maxHeight / 2 - 4);
			} else {
				sprite.drawSprite(((77 + k1) - sprite.maxWidth / 2) + 4 + (Client.clientWidth - 167),
						85 - l1 - sprite.maxHeight / 2 - 4);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void rightClickMapArea() {
		if (client.mouseInRegion(Client.clientWidth - (fixed ? 249 : 217), fixed ? 46 : 3,
				Client.clientWidth - (fixed ? 249 : 217) + 34, (fixed ? 46 : 3) + 34)) {
			client.menuActionName[1] = "Reset counter";
			client.menuActionID[1] = 1007;
			client.menuActionName[2] = client.showXP ? "Hide counter" : "Show counter";
			client.menuActionID[2] = 1006;
			client.menuActionRow = 3;
		}
		if (client.mouseX >= Client.clientWidth - (fixed ? 0 : 66)
				&& client.mouseX <= Client.clientWidth - (fixed ? 0 : 41)
				&& client.mouseY >= (fixed ? 142 : 142) && client.mouseY <= (fixed ? 167 : 167)) {
			client.menuActionName[1] = "Home Teleport";
			client.menuActionID[1] = 1716;
			client.menuActionRow = 2;
			client.homeHover = true;
		} else {
			client.homeHover = false;
		}
		if (client.mouseInRegion(fixed ? Client.clientWidth - 58 : getOrbX(1), getOrbY(1),
				(fixed ? Client.clientWidth - 58 : getOrbX(1)) + 57, getOrbY(1) + 34)) {

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
		if (client.mouseInRegion(522, 121, 559, 157)) {
			client.menuActionName[client.menuActionRow] = "Teleports";
			client.menuActionID[client.menuActionRow] = 1716;
			client.menuActionRow++;
		}
		if (client.mouseInRegion(fixed ? Client.clientWidth - 58 : getOrbX(2), getOrbY(2),
				(fixed ? Client.clientWidth - 58 : getOrbX(2)) + 57, getOrbY(2) + 34)) {
			client.menuActionName[client.menuActionRow] = "Rest";
			client.menuActionID[client.menuActionRow] = 1048;
			client.menuActionRow++;
			client.menuActionName[client.menuActionRow] = "Turn run mode " + (client.running ? "off" : "on");
			client.menuActionID[client.menuActionRow] = 1047;
			client.menuActionRow++;
		}
		if (client.mouseInRegion(fixed ? 692 : getOrbX(0), getOrbY(0), (fixed ? 748 : getOrbX(0) + 57),
				getOrbY(0) + 30)) {
			client.menuActionName[1] = "Cure Poision";
			client.menuActionID[1] = 10002;
			client.menuActionName[2] = "Heal";
			client.menuActionID[2] = 10001;
			client.menuActionRow = 3;
		}
		int x = client.mouseX; // Face North on compass
		int y = client.mouseY;
		if (x >= 531 && x <= 557 && y >= 7 && y <= 40) {
			client.menuActionName[1] = "Face North";
			client.menuActionID[1] = 696;
			client.menuActionRow = 2;
		}
		if (client.mouseInRegion(fixed ? Client.clientWidth - 74 : getOrbX(3), getOrbY(3),
				(fixed ? Client.clientWidth - 74 : getOrbX(3)) + 57, getOrbY(3) + 34)) {
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
	}

	@Override
	public void processMapAreaMouse() {
		if (client.mouseInRegion(Client.clientWidth - (fixed ? 249 : 217), fixed ? 46 : 3,
				Client.clientWidth - (fixed ? 249 : 217) + 34, (fixed ? 46 : 3) + 34)) {
			client.hoverPos = 0;// xp counter
		} else if (client.mouseInRegion(fixed ? Client.clientWidth - 58 : getOrbX(1), getOrbY(1),
				(fixed ? Client.clientWidth - 58 : getOrbX(1)) + 57, getOrbY(1) + 34)) {
			client.hoverPos = 1;// prayer
		} else if (client.mouseInRegion(fixed ? Client.clientWidth - 58 : getOrbX(2), getOrbY(2),
				(fixed ? Client.clientWidth - 58 : getOrbX(2)) + 57, getOrbY(2) + 34)) {
			client.hoverPos = 2;// run
		} else if (client.mouseInRegion(fixed ? Client.clientWidth - 74 : getOrbX(3), getOrbY(3),
				(fixed ? Client.clientWidth - 74 : getOrbX(3)) + 57, getOrbY(3) + 34)) {
			client.hoverPos = 3;// summoning
		} else {
			client.hoverPos = -1;
		}
	}

	@Override
	public void drawCoinOrb3DScreen() {
		int x = getCoinOrbX();
		int y = getCoinOrbY();
		SpriteLoader.sprites[647].drawSprite(fixed ? 447 : x - 67, y + 2);
		if (!fixed) {
			SpriteLoader.sprites[592].drawSprite(x, y);
		}
		if (RSInterface.interfaceCache[8135].message != null) {
			String cash = client.getMoneyInPouch();
			x = fixed ? 464 : x - 47;
			if (fixed) {
				x += client.newRegularFont.getTextWidth(client.getMoneyInPouch()) - 2;
				if (x > 490) {
					x -= 26;
				} else if (x >= 485) {
					x -= 15;
				} else if (x == 470) {
					x += 10;
				}
			}
			client.newRegularFont.drawBasicString("" + cash, x, y + 21,
					client.getAmountColor(Long.parseLong(RSInterface.interfaceCache[8135].message)), 0);
		}
	}

	private void drawSideIcons(boolean fixed) {
		if (fixed) {
			for (int index = 0; index < 15; index++) {
				if (index == 8 && client.tabInterfaceIDs[SIDEICONS_TAB[index]] > 0) {
					client.tabInterfaceIDs[SIDEICONS_TAB[index]] = -1;
				}
				if (index == 14) {
					client.tabInterfaceIDs[SIDEICONS_TAB[index]] = 54017;
				}
				if (client.tabInterfaceIDs[SIDEICONS_TAB[index]] != -1) {
					int x = SIDEICONS_POSITION_X[index];
					int y = SIDEICONS_POSITION_Y[index];
					int draw = index;

					if (index == 2 && client.doingDung) {
						x -= 1;
						draw = 16;
					}
					int spriteId = 657 + draw;
					if (spriteId == 659) {
						spriteId = PlayerTabManager.VIEWING_TAB.getSpriteId();
						if (PlayerTabManager.VIEWING_TAB == PlayerTabData.INFORMATION)
							y += 2;
					}
					SpriteLoader.sprites[spriteId].drawSprite(x, y);
				}
			}
		} else {
			for (int index = 0; index < 14; index++) {
				int offsetX = Client.clientWidth >= client.smallTabs ? 482 : 242;
				if (offsetX == 482 && index > 7) {
					offsetX -= 240;
				}
				int offsetY = Client.clientWidth >= client.smallTabs ? 37 : (index > 7 ? 37 : 74);
				if (client.tabInterfaceIDs[tab[index]] != -1 || index == 8) {
					if (index == 8) {
						client.tabInterfaceIDs[tab[index]] = 54017;
					}
					int i = index;
					if (index == 2 && client.doingDung) {
						i = 16;
					}
					SpriteLoader.sprites[657 + i].drawSprite((Client.clientWidth - offsetX) + SIDEICONS_POSITION_X[index],
							(Client.clientHeight - offsetY) + positionY[index]);
//					SpriteLoader.sprites[657 + i].drawSprite((Client.clientWidth - offsetX) + SIDEICONS_POSITION_X[index],
//							(Client.clientHeight - offsetY) + SIDEICONS_POSITION_Y[index]);
				}
			}
		}
	}

	private void drawTabs() {
		if (fixed) {
			int xPos = 2;
			int yPos = 0;
			if (currentTab < client.tabInterfaceIDs.length && client.tabInterfaceIDs[currentTab] != -1) {
				switch (currentTab) {
					case 0:
					case 1:
					case 2:
						xPos += currentTab * 30;
						yPos = 0;
						break;
					case 3:
					case 4:
					case 5:
					case 6:
						xPos += (currentTab + 1) * 30;
						yPos = 0;
						break;
					case 7:
						xPos = 2 + ((currentTab - 4) * 30);
						yPos = 299;
						break;
					case 8:
					case 9:
					case 11:
					case 12:
						xPos = 2 + ((currentTab - 7) * 30);
						yPos = 299;
						break;
					case 14:
						yPos = 0;
						xPos = 92;
						break;
					case 13:
					case 15:
						xPos = 2;
						yPos = 299;
						break;
					case 16:
						xPos = 212;
						yPos = 299;
						break;
				}
				if (currentTab != 10) {
					Client.cacheSprite[17].drawARGBSprite(xPos - 4, yPos);
				}
			}
		} else {
			for (int index = 0; index < TAB_DRAW.length; index++) {
				int offsetX = Client.clientWidth >= client.smallTabs ? 481 : 241;
				if (offsetX == 481 && index > 7) {
					offsetX -= 240;
				}
				int offsetY = Client.clientWidth >= client.smallTabs ? 37 : (index > 7 ? 37 : 74);
				if (currentTab == TAB_DRAW[index] && client.tabInterfaceIDs[TAB_DRAW[index]] != -1) {
					Client.cacheSprite[17].drawARGBSprite((Client.clientWidth - offsetX - 4) + TAB_POS[index],
							(Client.clientHeight - offsetY));
				}
			}
		}
	}

	private int getCoinOrbX() {
		return fixed ? -2 : Client.clientWidth - 40;
	}

	private int getCoinOrbY() {
		if (fixed) {
			return 84;
		}
		return 175;
	}

	private void drawCoinOrb() {
		int x = getCoinOrbX();
		int y = getCoinOrbY();
		boolean hover = fixed ? client.mouseInRegion(515, 85, 515 + 34, 85 + 34)
				: client.mouseInRegion(x, y, x + 34, y + 34);
		if (hover) {
			SpriteLoader.sprites[630].drawSprite(x, y);
		} else {
			SpriteLoader.sprites[592].drawSprite(x, y);
		}
		if (fixed) {
			SpriteLoader.sprites[593].drawSprite(-1, y);
		}
	}

	private int getTeleIconX() {
		return !fixed ? Client.clientWidth - 35 : 39;
	}

	private int getTeleIconY() {
		if (fixed) {
			return 136;
		}
		return 135;
	}

	private void drawTeleIcon() {
		int x = getTeleIconX();
		int y = getTeleIconY();
		boolean hover = fixed ? client.mouseInRegion(515, 85, 515 + 34, 85 + 34)
				: client.mouseInRegion(x, y, x + 34, y + 34);
		if (hover) {
			SpriteLoader.sprites[971].drawSprite(x, y);
		} else {
			SpriteLoader.sprites[971].drawSprite(x, y);
		}

	}

	private int getOrbX(int orb) {
		switch (orb) {
			case 0:
				return !fixed ? Client.clientWidth - 212 : 172;
			case 1:
				return !fixed ? Client.clientWidth - 215 : 188;
			case 2:
				return !fixed ? Client.clientWidth - 203 : 188;
			case 3:
				return !fixed ? Client.clientWidth - 180 : 172;
			case 4:
				return 4;
		}
		return 0;
	}

	private int getOrbY(int orb) {
		switch (orb) {
			case 0:
				return !fixed ? 39 : 15;
			case 1:
				return !fixed ? 73 : 54;
			case 2:
				return !fixed ? 107 : 93;
			case 3:
				return !fixed ? 141 : 128;
			case 4:
				return 120;
		}
		return 0;
	}

	private void drawHPOrb() {
		int currentHp = (client.currentStats[3] / 10);
		int health = (int) (((double) client.currentStats[3] / (double) client.currentMaxStats[3]) * 100D);
		if (!Client.getOption("constitution")) {
			currentHp = (int) Math.ceil(currentHp / 10.0);
		}
		int x = getOrbX(0);
		int y = getOrbY(0);
		client.orbs[fixed ? 0 : 11].drawSprite(x, y);
		if (health >= 75) {
			client.newSmallFont.drawCenteredString(Integer.toString(currentHp), x + (fixed ? 42 : 15), y + 26, 65280,
					0);
		} else if (health >= 50) {
			client.newSmallFont.drawCenteredString(Integer.toString(currentHp), x + (fixed ? 42 : 15), y + 26,
					0xffff00, 0);
		} else if (health >= 25) {
			client.newSmallFont.drawCenteredString(Integer.toString(currentHp), x + (fixed ? 42 : 15), y + 26,
					0xfca607, 0);
		} else if (health >= 0) {
			client.newSmallFont.drawCenteredString(Integer.toString(currentHp), x + (fixed ? 42 : 15), y + 26,
					0xf50d0d, 0);

		}
		if (client.poisoned && !client.venom) {
			Client.cacheSprite[1029].drawSprite(x + (fixed ? 3 : 27), y + 3);
		} else if (!client.poisoned && client.venom) {
			Client.cacheSprite[1030].drawSprite(x + (fixed ? 3 : 27), y + 3);
		} else {
			client.orbs[2].drawSprite(x + (fixed ? 3 : 27), y + 3);
		}
		double percent = (health / 100D);
		double fillHP = 27 * percent;
		int depleteFill = 27 - (int) fillHP;
		client.orbs[1].myHeight = depleteFill;
		DrawingArea.height = depleteFill;
		client.orbs[1].drawSprite(x + (fixed ? 3 : 27), y + 3);
		client.orbs[3].drawSprite(x + (fixed ? 9 : 33), y + 11);
	}

	private void drawPrayerOrb() {
		int currentPray = client.currentStats[5] / 10;
		if (currentPray <= 0 && client.quickPrsActive) {
			client.quickPrsActive = false;
		}
		int maxPray = client.currentMaxStats[5] / 10;
		int prayer = (int) (((double) currentPray / (double) maxPray) * 100D);
		int x = getOrbX(1);
		int y = getOrbY(1);
		client.orbs[fixed ? (client.hoverPos == 1 ? 12 : 0) : (client.hoverPos == 1 ? 13 : 11)].drawSprite(x, y);
		if (prayer >= 75) {
			client.newSmallFont.drawCenteredString(Integer.toString(currentPray), x + (fixed ? 42 : 15), y + 26,
					65280, 0);
		} else if (prayer >= 50) {
			client.newSmallFont.drawCenteredString(Integer.toString(currentPray), x + (fixed ? 42 : 15), y + 26,
					0xffff00, 0);
		} else if (prayer >= 25) {
			client.newSmallFont.drawCenteredString(Integer.toString(currentPray), x + (fixed ? 42 : 15), y + 26,
					0xfca607, 0);
		} else if (prayer >= 0) {
			client.newSmallFont.drawCenteredString(Integer.toString(currentPray), x + (fixed ? 42 : 15), y + 26,
					0xf50d0d, 0);

		}
		client.orbs[client.quickPrsActive ? 10 : 4].drawSprite(x + (fixed ? 3 : 27), y + 3);
		double percent = (prayer / 100D);
		double fillPrayer = 27 * percent;
		int depleteFill = 27 - (int) fillPrayer;
		client.orbs[17].myHeight = depleteFill;
		DrawingArea.height = depleteFill;
		client.orbs[17].drawSprite(x + (fixed ? 3 : 27), y + 3);
		client.orbs[5].drawSprite(x + (fixed ? 7 : 31), y + 7);
	}

	private void drawRunOrb() {
		int run = (int) (((double) client.currentEnergy / (double) 100) * 100D);
		int x = getOrbX(2);
		int y = getOrbY(2);
		client.orbs[fixed ? (client.hoverPos == 2 ? 12 : 0) : (client.hoverPos == 2 ? 13 : 11)].drawSprite(x, y);
		if (run <= 100 && run >= 75) {
			client.newSmallFont.drawCenteredString(Integer.toString(client.currentEnergy), x + (fixed ? 42 : 15), y + 26,
					65280, 0);
		} else if (run <= 74 && run >= 50) {
			client.newSmallFont.drawCenteredString(Integer.toString(client.currentEnergy), x + (fixed ? 42 : 15), y + 26,
					0xffff00, 0);
		} else if (run <= 49 && run >= 25) {
			client.newSmallFont.drawCenteredString(Integer.toString(client.currentEnergy), x + (fixed ? 42 : 15), y + 26,
					0xfca607, 0);
		} else if (run <= 24 && run >= 0) {
			client.newSmallFont.drawCenteredString(Integer.toString(client.currentEnergy), x + (fixed ? 42 : 15), y + 26,
					0xf50d0d, 0);

		}
		client.orbs[!client.running ? 6 : 8].drawSprite(x + (fixed ? 3 : 27), y + 3);
		double percent = (run / 100D);
		double fillRun = 27 * percent;
		int depleteFill = 27 - (int) fillRun;
		client.orbs[18].myHeight = depleteFill;
		DrawingArea.height = depleteFill;
		client.orbs[18].drawSprite(x + (fixed ? 3 : 27), y + 3);
		client.orbs[!client.running ? 7 : 9].drawSprite(x + (fixed ? 10 : 34), y + 7);
	}

	private void drawSummoningOrb() {
		int summoning = (int) (((double) client.currentStats[23] / (double) client.currentMaxStats[23]) * 100D);
		int x = getOrbX(3);
		int y = getOrbY(3);
		client.orbs[fixed ? (client.hoverPos == 3 ? 12 : 0) : (client.hoverPos == 3 ? 13 : 11)].drawSprite(x, y);
		if (summoning <= 100 && summoning >= 75) {
			client.newSmallFont.drawCenteredString(Integer.toString(client.currentStats[23]), x + (fixed ? 42 : 15), y + 26,
					65280, 0);
		} else if (summoning <= 74 && summoning >= 50) {
			client.newSmallFont.drawCenteredString(Integer.toString(client.currentStats[23]), x + (fixed ? 42 : 15), y + 26,
					0xffff00, 0);
		} else if (summoning <= 49 && summoning >= 25) {
			client.newSmallFont.drawCenteredString(Integer.toString(client.currentStats[23]), x + (fixed ? 42 : 15), y + 26,
					0xfca607, 0);
		} else if (summoning <= 24 && summoning >= 0) {
			client.newSmallFont.drawCenteredString(Integer.toString(client.currentStats[23]), x + (fixed ? 42 : 15), y + 26,
					0xf50d0d, 0);
		}
		client.orbs[client.hasFamiliar ? 16 : 14].drawSprite(x + (fixed ? 3 : 27), y + 3);
		double percent = (summoning / 100D);
		double fillSummoning = 27 * percent;
		int depleteFill = 27 - (int) fillSummoning;
		client.orbs[19].myHeight = depleteFill;
		DrawingArea.height = depleteFill;
		client.orbs[19].drawSprite(x + (fixed ? 3 : 27), y + 3);
		client.orbs[15].drawSprite(x + (fixed ? 9 : 33), y + 9);
	}

	private void drawSpecOrb() {
		if (!fixed) {
			return;
		}
		int spec = (int) (((double) client.currentSpec / (double) 100) * 100D);
		int x = getOrbX(4);
		int y = getOrbY(4);
		double percent = (spec / 100D);
		double f = 39 * percent;
		int depleteFill = 39 - (int) f;
		if (client.specActivated) {
			SpriteLoader.sprites[619].drawSprite(x, y);
		} else {
			SpriteLoader.sprites[617].drawSprite(x, y);
		}
		SpriteLoader.sprites[618].myHeight = depleteFill;
		DrawingArea.height = depleteFill;
		SpriteLoader.sprites[618].drawSprite(x, y);
		SpriteLoader.sprites[620].drawSprite(x + 7, y + 8);
	}

	private void processTabAreaTooltips() {
		String[] tooltipString = {"Combat Styles", "Stats", "Player Panel", "Inventory", "Worn Equipment",
				"Prayer List", "Magic Spellbook", "Clan Chat", "Friends List", "Ignore List", "Logout", "Options",
				"Emotes", fixed ? "Summoning" : "", "Achievements", fixed ? "" : "Summoning", ""};
		if (tooltipString[client.tabHover].equals("")) {
			return;
		}
		client.menuActionName[1] = tooltipString[client.tabHover];
		client.menuActionID[1] = 1076;
		client.menuActionRow = 2;
	}

	@Override
	public void handleTabArea() {
		if (fixed) {
			Client.cacheSprite[13].drawSprite(0, 0);
		} else {
			if (Client.clientWidth >= client.smallTabs) {
				for (int positionX = Client.clientWidth - 480, positionY = Client.clientHeight
						- 37, index = 0; positionX <= Client.clientWidth - 30 && index < 16; positionX += 30, index++) {
					Client.cacheSprite[15].drawSprite(positionX, positionY);
				}
				if (client.showTab) {
					Client.cacheSprite[18].drawTransparentSprite(Client.clientWidth - 197, Client.clientHeight - 37 - 267, 150);
					Client.cacheSprite[19].drawSprite(Client.clientWidth - 204, Client.clientHeight - 37 - 274);
				}
			} else {
				for (int positionX = Client.clientWidth - 240, positionY = Client.clientHeight
						- 74, index = 0; positionX <= Client.clientWidth - 30 && index < 8; positionX += 30, index++) {
					Client.cacheSprite[15].drawSprite(positionX, positionY);
				}
				for (int positionX = Client.clientWidth - 240, positionY = Client.clientHeight
						- 37, index = 0; positionX <= Client.clientWidth - 30 && index < 8; positionX += 30, index++) {
					Client.cacheSprite[15].drawSprite(positionX, positionY);
				}
				if (client.showTab) {
					Client.cacheSprite[18].drawTransparentSprite(Client.clientWidth - 197, Client.clientHeight - 74 - 267, 150);
					Client.cacheSprite[19].drawSprite(Client.clientWidth - 204, Client.clientHeight - 74 - 274);
				}
			}
		}
		if (client.invOverlayInterfaceID == -1) {
			client.drawTabHover(fixed);
			if (client.showTab) {
				drawTabs();
			}
			drawSideIcons(fixed);
		}
	}

}
