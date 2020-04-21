package com.varrock.client.content;

import com.varrock.client.*;
import com.varrock.client.cache.definitions.ItemDefinition;

public abstract class ChatboxItemSearch {

	public static boolean SEARCHING_ITEM = false;

	public static void draw(RSFontSystem searchFont, int searchTextColor, boolean searchTextShadow,
							RSFontSystem resultFont, int resultColors, boolean resultShadow, int offsetY) {
		int startX = 7;
		int nameBoxY = 120 + offsetY;
		int width = 505;
		DrawingArea.drawFilledPixels(startX, nameBoxY, width, 16, 0, 60);
		DrawingArea.drawHorizontalLine(startX, nameBoxY, width, 0x807660);
		
		int searchX = 5;

		SpriteLoader.sprites[1396].drawSprite(searchX + 5, nameBoxY + 1);
		searchFont.drawBasicString(input + "*", searchX + 30, nameBoxY + 12, searchTextColor, searchTextShadow ? 1 : -1);

		int resultX = 60;
		int resultY = 7 + offsetY;
		DrawingArea.drawVerticalLine(resultX, resultY, 113, 0x807660);
		DrawingArea.drawVerticalLine(resultX + 1, resultY, 113, 0x807660);
		DrawingArea.drawVerticalLine(resultX + 2, resultY, 113, 0x807660);
		

		int resultOffsetY = Client.getClient().getClientSize() == 0 ? Client.instance.gameScreenIP.height : Client.clientHeight - 165;
		int mouseX = Client.instance.mouseX;
		int mouseY = Client.instance.mouseY;
		for (int i = 0; i < CURRENT_ITEM_RESULT; i++) {
			int yPos = 10 + i * resultFont.baseCharacterHeight - scrollBarPosition;
			if (yPos > 0 && yPos < 110) {
				String resultName = ITEM_RESULT_NAMES[i];
				if (resultName == null)
					continue;
				resultFont.drawBasicString(resultName, resultX + 5, yPos + 10 + offsetY, resultColors, resultShadow ? 1 : -1);
				
				if (mouseX >= resultX + 5 && mouseX <= 497
						&& mouseY >= yPos + 5 + resultOffsetY && mouseY <= yPos + resultOffsetY + resultFont.baseCharacterHeight) {
					hoveredItemIndex = i;

					if (Client.instance.clickMode3 == 1) {

						//send item to server
						Client.stream.createFrame(182);

						String name = ItemDefinition.forID(ITEM_RESULT_IDS[hoveredItemIndex]).name;

						Client.stream.writeByte(name.length() + 1);
						Client.stream.writeString(name);


						//close chatbox
						closeItemSearch();
					}
				}
			}
		}
		if (CURRENT_ITEM_RESULT > 0) {
			if (hoveredItemIndex > CURRENT_ITEM_RESULT)
				hoveredItemIndex = CURRENT_ITEM_RESULT;
			
			DrawingArea.drawFilledPixels(resultX + 5,
				hoveredItemIndex * resultFont.baseCharacterHeight - scrollBarPosition + offsetY + 10,
				432, resultFont.baseCharacterHeight, 0, Client.getClient().getClientSize() == 0 ? 30 : 60);
			
			scrollBarHeight = CURRENT_ITEM_RESULT * resultFont.baseCharacterHeight + 7;

			if(scrollBarHeight < 113)
				scrollBarHeight = 114;

				Client.instance.drawScrollbar(113, scrollBarPosition, 7 + offsetY, 497, scrollBarHeight, false, Client.getClient().getClientSize() != 0);
					
			final int itemId = ITEM_RESULT_IDS[hoveredItemIndex];
			if (itemId > 0) {
				final int itemX = 14;
				final int itemY = 15 + offsetY;

				SpriteLoader.sprites[1397].drawSprite(itemX, itemY);
				
				final Sprite itemImage = ItemDefinition.getSprite(itemId, 1, 0);
				if (itemImage != null) {
					itemImage.drawSprite(itemX + 4, itemY + 3);
				}
			}
		}
	}
	
	public static void search(String name) {
		ITEM_RESULT_NAMES = new String[ITEM_RESULT_NAMES.length];
		ITEM_RESULT_IDS = new int[ITEM_RESULT_IDS.length];
		CURRENT_ITEM_RESULT = 0;
		
		if (name.length() <= 0)
			return;
		
		name = name.toLowerCase();


		for (int i = 0; i < ItemDefinition.totalItems + 1000; i++) {
			if (CURRENT_ITEM_RESULT >= ITEM_RESULT_NAMES.length)
				break;
			final ItemDefinition definition = ItemDefinition.forID(i);
			if(definition == null) {
				continue;
			}

			if (definition.name == null
					|| isInvalidItem(definition))
				continue;

			if (definition.name.toLowerCase().contains(name)) {
				ITEM_RESULT_NAMES[CURRENT_ITEM_RESULT] = definition.name;
				ITEM_RESULT_IDS[CURRENT_ITEM_RESULT] = definition.id;
				CURRENT_ITEM_RESULT++;
			}
		}

		for (int i = 30000; i < 30_000 + ItemDefinition.totalItemsOSRS; i++) {
			if (CURRENT_ITEM_RESULT >= ITEM_RESULT_NAMES.length)
				break;
			final ItemDefinition definition = ItemDefinition.forID(i);
			if(definition == null) {
				continue;
			}

			if (definition.name == null
					|| isInvalidItem(definition))
				continue;

			if (definition.name.toLowerCase().contains(name)) {
				ITEM_RESULT_NAMES[CURRENT_ITEM_RESULT] = definition.name;
				ITEM_RESULT_IDS[CURRENT_ITEM_RESULT] = definition.id;
				CURRENT_ITEM_RESULT++;
			}
		}
	}

	public static void reset() {
		input = "";
	}
	
	public static boolean isInvalidItem(ItemDefinition definition) {
		final int itemId = definition.id;

		if(definition.certTemplateID > 0) {
			return true;
		}

		if(definition.lentItemID > 0) {
			return true;
		}

		return false;
	}
	
	public static String input = "";
	
	public static int scrollBarHeight;
	
	public static int hoveredItemIndex;
	
	public static int scrollBarPosition;
	
	public static String[] ITEM_RESULT_NAMES = new String[200];

	public static int[] ITEM_RESULT_IDS = new int[200];

	public static int CURRENT_ITEM_RESULT;

	public static void closeItemSearch() {
		SEARCHING_ITEM = false;
		Client.instance.showInput = false;
		Client.inputTaken = true;
		Client.instance.drawChatArea();
	}
}
