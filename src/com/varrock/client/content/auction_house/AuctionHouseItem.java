package com.varrock.client.content.auction_house;

/**
 * Created by Jonny on 9/6/2019
 **/
public class AuctionHouseItem {

    private int itemId;

    private int amount;

    private long buyPrice;

    private long auctionPrice;

    private int bids;

    private String timeRemaining;

    public AuctionHouseItem(int itemId, int amount, long buyPrice, long auctionPrice, int bids, String timeRemaining) {
        this.itemId = itemId;
        this.amount = amount;
        this.buyPrice = buyPrice;
        this.auctionPrice = auctionPrice;
        this.bids = bids;
        this.timeRemaining = timeRemaining;
    }

    public int getItemId() {
        return itemId;
    }

    public int getAmount() {
        return amount;
    }

    public long getBuyPrice() {
        return buyPrice;
    }

    public long getAuctionPrice() {
        return auctionPrice;
    }

    public int getBids() {
        return bids;
    }

    public String getTimeRemaining() {
        return timeRemaining;
    }
}
