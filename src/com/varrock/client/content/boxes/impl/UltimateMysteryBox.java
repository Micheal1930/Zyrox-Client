package com.varrock.client.content.boxes.impl;

import java.util.ArrayList;
import java.util.List;

import com.varrock.client.content.Item;
import com.varrock.client.content.Items;
import com.varrock.client.content.boxes.LootBox;

public class UltimateMysteryBox implements LootBox {

    /**
     * The items that will be given when there is no rare drops.
     */
    private List<Item> commonItems = new ArrayList<>();

    /**
     * The items that match the 'uncommon' rarity.
     */
    private List<Item> uncommonItems = new ArrayList<>();

    /**
     * The items that match the 'rare' rarity.
     */
    private List<Item> rareItems = new ArrayList<>();

    /**
     * The items that match the 'super rare' rarity.
     */
    private List<Item> superRareItems = new ArrayList<>();

    /**
     * The items that match the 'ultimate' rarity.
     */
    private List<Item> ultimateItems = new ArrayList<>();

    /**
     * Constructor and loader for the box.
     */
    public UltimateMysteryBox() {
        rareItems.add(new Item(Items.ABYSSAL_VINE_WHIP_1, 1));
        rareItems.add(new Item(Items.ELYSIAN_SPIRIT_SHIELD, 1));
        rareItems.add(new Item(Items.SPECTRAL_SPIRIT_SHIELD, 1));
        rareItems.add(new Item(Items.ARCANE_SPIRIT_SHIELD, 1));
        rareItems.add(new Item(Items.TOXIC_STAFF_OF_THE_DEAD, 1));
        rareItems.add(new Item(Items.KORASIS_SWORD, 1));
        rareItems.add(new Item(Items.INFERNAL_CAPE, 1));
        rareItems.add(new Item(Items.ULTIMATE_MYSTERY_BOX, 1));
        rareItems.add(new Item(Items.SERPENTINE_HELM, 1));
        rareItems.add(new Item(Items.BANDOS_CHESTPLATE, 1));
        rareItems.add(new Item(Items.BANDOS_TASSETS, 1));

        superRareItems.add(new Item(Items.DRYGORE_LONGSWORD, 1));
        superRareItems.add(new Item(Items.DRYGORE_MACE, 1));
        superRareItems.add(new Item(Items.DRYGORE_RAPIER, 1));
        superRareItems.add(new Item(Items.TOXIC_BLOWPIPE, 1));
        superRareItems.add(new Item(Items.TORVA_FULL_HELM, 1));
        superRareItems.add(new Item(Items.TORVA_PLATEBODY, 1));
        superRareItems.add(new Item(Items.TORVA_PLATELEGS, 1));
        superRareItems.add(new Item(Items.VIRTUS_MASK, 1));
        superRareItems.add(new Item(Items.VIRTUS_ROBE_LEGS, 1));
        superRareItems.add(new Item(Items.VIRTUS_ROBE_TOP, 1));
        superRareItems.add(new Item(Items.PERNIX_BODY, 1));
        superRareItems.add(new Item(Items.PERNIX_CHAPS, 1));
        superRareItems.add(new Item(Items.PERNIX_COWL, 1));
        superRareItems.add(new Item(Items.RING_OF_THE_GODS, 1));
        superRareItems.add(new Item(Items.DIVINE_SPIRIT_SHIELD, 1));
        superRareItems.add(new Item(Items.ARMADYL_GODSWORD, 1));
        superRareItems.add(new Item(Items.DRAGON_CLAWS, 1));
        superRareItems.add(new Item(Items.ARMADYL_CROSSBOW, 1));
        superRareItems.add(new Item(Items.DIVINE_SPIRIT_SHIELD, 1));
        superRareItems.add(new Item(Items.ZARYTE_BOW, 1));

        ultimateItems.add(new Item(Items.GREEN_PARTYHAT, 1));
        ultimateItems.add(new Item(Items.PURPLE_PARTYHAT, 1));
        ultimateItems.add(new Item(Items.RED_PARTYHAT, 1));
        ultimateItems.add(new Item(Items.WHITE_PARTYHAT, 1));
        ultimateItems.add(new Item(Items.YELLOW_PARTYHAT, 1));
        ultimateItems.add(new Item(Items.BLUE_PARTYHAT, 1));
        ultimateItems.add(new Item(Items.SANTA_HAT, 1));
        ultimateItems.add(new Item(Items.BLUE_HALLOWEEN_MASK, 1));
        ultimateItems.add(new Item(Items.GREEN_HALLOWEEN_MASK, 1));
        ultimateItems.add(new Item(Items.RED_HALLOWEEN_MASK, 1));
        ultimateItems.add(new Item(Items.JUSTICIAR_CHESTGUARD, 1));
        ultimateItems.add(new Item(Items.JUSTICIAR_FACEGUARD, 1));
        ultimateItems.add(new Item(Items.JUSTICIAR_LEGGUARDS, 1));
        ultimateItems.add(new Item(Items.ANCESTRAL_HAT, 1));
        ultimateItems.add(new Item(Items.ANCESTRAL_ROBE_BOTTOM, 1));
        ultimateItems.add(new Item(Items.ANCESTRAL_ROBE_TOP, 1));
        ultimateItems.add(new Item(Items.DEVOUT_BOOTS, 1));
        ultimateItems.add(new Item(Items.SCYTHE_OF_VITUR, 1));
        ultimateItems.add(new Item(Items.GHRAZI_RAPIER, 1));
        ultimateItems.add(new Item(Items.TWISTED_BOW, 1));

    }

    @Override
    public List<Item> commonItems() {
        return commonItems;
    }

    @Override
    public List<Item> uncommonItems() {
        return uncommonItems;
    }

    @Override
    public List<Item> rareItems() {
        return rareItems;
    }

    @Override
    public List<Item> superRareItems() {
        return superRareItems;
    }

    @Override
    public List<Item> ultimateItems() {
        return ultimateItems;
    }

    @Override
    public double uncommonChance() {
        return -1;
    }

    @Override
    public double rareChance() {
        return 1.0 / 1.0;
    }

    @Override
    public double superRareChance() {
        return 1.0 / 10.0;
    }

    @Override
    public double ultimateChance() {
        return 1.0 / 25.0;
    }

    @Override
	public String boxName() {
		return "Ultimate Mystery Box";
	}
}
