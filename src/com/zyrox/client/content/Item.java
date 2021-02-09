package com.zyrox.client.content;

/**
 * Created by Jonny on 8/23/2019
 **/
public class Item {

    private int itemId;
    private int amount;

    public Item(int itemId, int amount) {
        this.itemId = itemId;
        this.amount = amount;
    }

    public Item(int itemId) {
        this.itemId = itemId;
        this.amount = 1;
    }

    public int getItemId() {
        return itemId;
    }

    public int getAmount() {
        return amount;
    }
}
