package com.zyrox.client;


import com.zyrox.client.cache.definitions.ItemDefinition;

public final class AnimableItem extends Animable {

	public final Model getRotatedModel() {
		ItemDefinition itemDef = ItemDefinition.forID(ID);
		return itemDef.getItemModelFinalised(amount);
	}

	public AnimableItem() {
	}

	public int ID;
	public int x;
	public int y;
	public int amount;
}
