package com.varrock.client.content.boxes;

import com.varrock.client.Client;
import com.varrock.client.RSInterface;
import com.varrock.client.cache.definitions.ItemDefinition;
import com.varrock.client.content.Item;
import com.varrock.client.content.Items;
import com.varrock.client.content.boxes.impl.HalloweenBox;
import com.varrock.client.content.boxes.impl.MysteryBox;
import com.varrock.client.content.boxes.impl.SupremeMysteryBox;

/**
 * The variables for box rewards
 */
public class BoxRewards {

    public static int CURRENT_BOX_REWARD = 1;
    public static int CURRENT_REWARD_AMOUNT = 1;

    public static float spinSpeed;
    public static boolean startSpin;

    public static void openBox(int boxId, int rewardId, int rewardAmount, int gotColor) {
        LootBox lootBox = LootBox.boxes.get(boxId);

        if(lootBox != null) {
            openLootBox(lootBox, rewardId, rewardAmount, gotColor);
        }
    }

    public static void openLootBox(LootBox lootBox, int rewardId, int rewardAmount, int gotColor) {
        BoxRewards.CURRENT_BOX_REWARD = rewardId;
        BoxRewards.CURRENT_REWARD_AMOUNT = rewardAmount;
        try {
            RSInterface w = RSInterface.interfaceCache[56100];
            for (int j22 = 0; j22 < 300; j22++) {
                if (j22 == w.inv.length) {
                    break;
                }
                Item item;
                int color = -1;

                double randomFloat = java.lang.Math.random();
                if (!lootBox.ultimateItems().isEmpty() && randomFloat <= lootBox.ultimateChance()) {
                    item = lootBox.ultimateItems().get(Client.random(lootBox.ultimateItems().size()));
                    color = 0xff0000;
                } else if (!lootBox.superRareItems().isEmpty() && randomFloat <= lootBox.superRareChance()) {
                    item = lootBox.superRareItems().get(Client.random(lootBox.superRareItems().size()));

                    if(!(lootBox instanceof SupremeMysteryBox))
                        color = 0xadd8e6;
                } else if (!lootBox.rareItems().isEmpty() && randomFloat <= lootBox.rareChance()) {
                    item = lootBox.rareItems().get(Client.random(lootBox.rareItems().size()));

                    if(lootBox instanceof MysteryBox)
                        color = 0x00ff00;

                    if(lootBox instanceof HalloweenBox)
                        color = 0xffa500;

                } else if (!lootBox.uncommonItems().isEmpty() && randomFloat <= lootBox.uncommonChance()) {
                    item = lootBox.uncommonItems().get(Client.random(lootBox.uncommonItems().size()));
                } else {
                    item = lootBox.commonItems().get(Client.random(lootBox.commonItems().size()));
                }

                w.inv[j22] = item.getItemId() + 1;
                w.invStackSizes[j22] = item.getAmount();
                w.invColors[j22] = color;
            }

            w.inv[51] = rewardId + 1;
            w.invStackSizes[51] = rewardAmount;
            w.invColors[51] = gotColor;
            spinSpeed = 1;
            RSInterface.interfaceCache[56022].xOffset = 0;
            RSInterface.interfaceCache[56100].xOffset = 0;
            RSInterface.interfaceCache[56120].invisible = true;
            startSpin = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startSpinner() {
        RSInterface w = RSInterface.interfaceCache[56022];
        RSInterface w2 = RSInterface.interfaceCache[56100];
        RSInterface rewardInt = RSInterface.interfaceCache[56120];
        RSInterface rewards = RSInterface.interfaceCache[56003];
        if(w.xOffset >= -1000) {
            w.xOffset -= 25;
            w2.xOffset -= 25;
        }

        if(w.xOffset >= -1912 && w.xOffset <= -1001) {
            w.xOffset -= (25 / spinSpeed);
            w2.xOffset -= (25 / spinSpeed);
            spinSpeed = spinSpeed + 0.07f;
        }
        if(w.xOffset >= -2000 && w.xOffset < -1913) {
            w.xOffset -= (25 / spinSpeed);
            w2.xOffset -= (25 / spinSpeed);
            spinSpeed = spinSpeed + 2f;
        }
        if(w.xOffset <= -1913) {
            rewardInt.invisible = false;
            RSInterface.interfaceCache[56005].message = ItemDefinition.forID(BoxRewards.CURRENT_BOX_REWARD).name.replaceAll("_", " ");
            rewards.inv[0] = BoxRewards.CURRENT_BOX_REWARD + 1;
            rewards.invStackSizes[0] = BoxRewards.CURRENT_REWARD_AMOUNT;
            startSpin = false;
        }
    }
}
