package com.zyrox.client.content.boxes;

import java.util.*;

import com.zyrox.client.content.Item;
import com.zyrox.client.content.Items;
import com.zyrox.client.content.boxes.impl.*;

/**
 * A simple loot box interface.
 *
 * @author Gabriel || Wolfsdarker
 */
public interface LootBox {

    /**
     * The map of lootboxes.
     */
    Map<Integer, LootBox> boxes = new HashMap<>();

    /**
     * The list of common items inside the box.
     *
     * @return the items
     */
    List<Item> commonItems();

    /**
     * The list of uncommon items inside the box.
     *
     * @return the items
     */
    List<Item> uncommonItems();

    /**
     * The list of rare items inside the box.
     *
     * @return the items
     */
    List<Item> rareItems();

    /**
     * The list of super rare items inside the box.
     *
     * @return the items
     */
    List<Item> superRareItems();

    /**
     * The list of ultimate items inside the box.
     *
     * @return the items
     */
    List<Item> ultimateItems();

    /**
     * The chance to receive an uncommon item.
     *
     * @return the chance
     */
    double uncommonChance();

    /**
     * The chance to receive a rare item.
     *
     * @return the chance
     */
    double rareChance();

    /**
     * The chance to receive a super rare item.
     *
     * @return the chance
     */
    double superRareChance();

    /**
     * The chance to receive a ultimate item.
     *
     * @return the chance
     */
    double ultimateChance();


    String boxName();

    /**
     * Loads all the boxes.
     */
    static void init() {
        boxes.put(Items.HALLOWEEN_BOX, new HalloweenBox());
        boxes.put(Items.MYSTERY_BOX, new MysteryBox());
        boxes.put(Items.SUPER_MYSTERY_BOX, new SuperMysteryBox());
        boxes.put(Items.ULTIMATE_MYSTERY_BOX, new UltimateMysteryBox());
        boxes.put(Items.SUPREME_MYSTERY_BOX, new SupremeMysteryBox());
    }

}