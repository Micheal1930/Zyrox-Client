package com.varrock.client.content.creation_ci;

import com.varrock.client.*;
import com.varrock.client.cache.definitions.Animation;
import com.varrock.client.cache.definitions.ItemDefinition;

public class CreationCI {

	public static final int INTERFACE_ID = 55500;
	private static int childId = INTERFACE_ID + 1;
	public static int[] items;

	private static final int ITEM_ZOOM = -1;

	private static RSInterface mainInterface;
	private static TextDrawingArea[] textDrawingAreas;

	public static void load(TextDrawingArea[] tda) {
		textDrawingAreas = tda;
		creationCI();
		creationCI1();
		creationCI2();
		creationCI3();
		creationCI4();
	}

	private static void creationCI() {
		int id = 0;
		mainInterface = RSInterface.addInterface(INTERFACE_ID);
		mainInterface.totalChildren(7);
		RSInterface.addText(childId, "What would you like to cook?", 0x403020, true, false, -1, 2);
		mainInterface.setChild(id++, childId++, 152, -5);
		RSInterface.addText(childId, "Choose a quantity, then click an item to begin.", 0x605048, true, false, -1, 0);
		mainInterface.setChild(id++, childId++, 152, 13);
		RSInterface.addConfigButtonWSpriteLoader(childId, INTERFACE_ID, 1494, 1495, 30, 30, 0, 5, 1085, "Select 1", "1", 0, 0x403020, 0xffffff);
		mainInterface.setChild(id++, childId++, 305, -5);
		RSInterface.addConfigButtonWSpriteLoader(childId, INTERFACE_ID, 1494, 1495, 30, 30, 1, 5, 1085, "Select 5", "5", 0, 0x403020, 0xffffff);
		mainInterface.setChild(id++, childId++, 340, -5);
		RSInterface.addConfigButtonWSpriteLoader(childId, INTERFACE_ID, 1494, 1495, 30, 30, 2, 5, 1085, "Select 10", "10", 0, 0x403020, 0xffffff);
		mainInterface.setChild(id++, childId++, 375, -5);
		RSInterface.addConfigButtonWSpriteLoader(childId, INTERFACE_ID, 1494, 1495, 30, 30, 3, 5, 1085, "Select X", "X", 0, 0x403020, 0xffffff);
		mainInterface.setChild(id++, childId++, 410, -5);
		RSInterface.addConfigButtonWSpriteLoader(childId, INTERFACE_ID, 1494, 1495, 30, 30, 4, 5, 1085, "Select All", "All", 0, 0x403020, 0xffffff);
		mainInterface.setChild(id++, childId++, 445, -5);
	}

	private static void creationCI1() {
		int id = 0;
		RSInterface rsi = RSInterface.addChatboxInterface(childId++); // 55508
		rsi.totalChildren(4);
		rsi.setChild(id++, INTERFACE_ID, 0, 0);

		RSInterface.addHoverButtonWSpriteLoader(childId, 1492, 100, 72, "Select", -1, childId + 1, 1);
		rsi.setChild(id++, childId++, 190, 36);
		RSInterface.addHoveredImageWSpriteLoader(childId, 1493, 100, 72, childId + 1);
		rsi.setChild(id++, childId++, 190, 36);
		childId++;
		RSInterface.addItemModel(childId, 321, 1, 1, ITEM_ZOOM);
		rsi.setChild(id++, childId++, 240, 72);
	}

	private static void creationCI2() {
		int id = 0;
		RSInterface rsi = RSInterface.addChatboxInterface(childId++); //55513
		rsi.totalChildren(7);
		rsi.setChild(id++, INTERFACE_ID, 0, 0);

		RSInterface.addHoverButtonWSpriteLoader(childId, 1492, 100, 72, "Select", -1, childId + 1, 1);
		rsi.setChild(id++, childId++, 130, 36);
		RSInterface.addHoveredImageWSpriteLoader(childId, 1493, 100, 72, childId + 1);
		rsi.setChild(id++, childId++, 130, 36);
		childId++;
		RSInterface.addHoverButtonWSpriteLoader(childId, 1492, 100, 72, "Select", -1, childId + 1, 1);
		rsi.setChild(id++, childId++, 250, 36);
		RSInterface.addHoveredImageWSpriteLoader(childId, 1493, 100, 72, childId + 1);
		rsi.setChild(id++, childId++, 250, 36);
		childId++;
		RSInterface.addItemModel(childId, 325, 1, 1, ITEM_ZOOM);
		rsi.setChild(id++, childId++, 180, 72);
		RSInterface.addItemModel(childId, 325, 1, 1, ITEM_ZOOM);
		rsi.setChild(id++, childId++, 300, 72);
	}

	private static void creationCI3() {
		int id = 0;
		RSInterface rsi = RSInterface.addChatboxInterface(childId++); //55522
		rsi.totalChildren(10);
		rsi.setChild(id++, INTERFACE_ID, 0, 0);

		RSInterface.addHoverButtonWSpriteLoader(childId, 1492, 100, 72, "Select", -1, childId + 1, 1);
		rsi.setChild(id++, childId++, 70, 36);
		RSInterface.addHoveredImageWSpriteLoader(childId, 1493, 100, 72, childId + 1);
		rsi.setChild(id++, childId++, 70, 36);
		childId++;
		RSInterface.addHoverButtonWSpriteLoader(childId, 1492, 100, 72, "Select", -1, childId + 1, 1);
		rsi.setChild(id++, childId++, 190, 36);
		RSInterface.addHoveredImageWSpriteLoader(childId, 1493, 100, 72, childId + 1);
		rsi.setChild(id++, childId++, 190, 36);
		childId++;
		RSInterface.addHoverButtonWSpriteLoader(childId, 1492, 100, 72, "Select", -1, childId + 1, 1);
		rsi.setChild(id++, childId++, 310, 36);
		RSInterface.addHoveredImageWSpriteLoader(childId, 1493, 100, 72, childId + 1);
		rsi.setChild(id++, childId++, 310, 36);
		childId++;
		RSInterface.addItemModel(childId, 325, 1, 1, ITEM_ZOOM);
		rsi.setChild(id++, childId++, 120, 72);
		RSInterface.addItemModel(childId, 325, 1, 1, ITEM_ZOOM);
		rsi.setChild(id++, childId++, 240, 72);
		RSInterface.addItemModel(childId, 325, 1, 1, ITEM_ZOOM);
		rsi.setChild(id++, childId++, 360, 72);
	}

	private static void creationCI4() {
		int id = 0;
		RSInterface rsi = RSInterface.addChatboxInterface(childId++); // 55535
		rsi.totalChildren(13);
		rsi.setChild(id++, INTERFACE_ID, 0, 0);

		RSInterface.addHoverButtonWSpriteLoader(childId, 1492, 100, 72, "Select", -1, childId + 1, 1);
		rsi.setChild(id++, childId++, 10, 36);
		RSInterface.addHoveredImageWSpriteLoader(childId, 1493, 100, 72, childId + 1);
		rsi.setChild(id++, childId++, 10, 36);
		childId++;
		RSInterface.addHoverButtonWSpriteLoader(childId, 1492, 100, 72, "Select", -1, childId + 1, 1);
		rsi.setChild(id++, childId++, 130, 36);
		RSInterface.addHoveredImageWSpriteLoader(childId, 1493, 100, 72, childId + 1);
		rsi.setChild(id++, childId++, 130, 36);
		childId++;
		RSInterface.addHoverButtonWSpriteLoader(childId, 1492, 100, 72, "Select", -1, childId + 1, 1);
		rsi.setChild(id++, childId++, 250, 36);
		RSInterface.addHoveredImageWSpriteLoader(childId, 1493, 100, 72, childId + 1);
		rsi.setChild(id++, childId++, 250, 36);
		childId++;
		RSInterface.addHoverButtonWSpriteLoader(childId, 1492, 100, 72, "Select", -1, childId + 1, 1);
		rsi.setChild(id++, childId++, 370, 36);
		RSInterface.addHoveredImageWSpriteLoader(childId, 1493, 100, 72, childId + 1);
		rsi.setChild(id++, childId++, 370, 36);
		childId++;
		RSInterface.addItemModel(childId, 325, 1, 1, ITEM_ZOOM);
		rsi.setChild(id++, childId++, 60, 72);
		RSInterface.addItemModel(childId, 325, 1, 1, ITEM_ZOOM);
		rsi.setChild(id++, childId++, 180, 72);
		RSInterface.addItemModel(childId, 325, 1, 1, ITEM_ZOOM);
		rsi.setChild(id++, childId++, 300, 72);
		RSInterface.addItemModel(childId, 325, 1, 1, ITEM_ZOOM);
		rsi.setChild(id++, childId++, 420, 72);
	}

	/*public static void drawButtons(RSInterface button) {
		int mouseX = Client.getClient().mouseX;
		int mouseY = Client.getClient().mouseY - 338;
		int amountOfButtons = 0;
		for (int i = 0; i < button.inv.length; i++) {
			if (button.inv[i] == 0) {
				amountOfButtons = i;
				break;
			}
		}
		int startX = 260 - amountOfButtons * 60;
		for (int i = 0; i < amountOfButtons; i++) {
			int x = startX + 10 + (i * 120);
			int y = 51;
			if (mouseX >= x && mouseX <= x + 100 && mouseY >= y && mouseY <= y + 72) {
				Client.cacheSprite[1493].drawSprite(x, y);
			} else {
				Client.cacheSprite[1492].drawSprite(x, y);
			}
			drawModel(button.inv[i], startX + 60 + (i * 120), 85);
		}
	}*/

}
