package com.varrock.client.cache.definitions;

import java.io.PrintWriter;

import com.varrock.client.*;
import com.varrock.client.cache.DataType;

public final class MobDefinition {

    public int frontLight = 68;
    public int backLight = 820;
    public int rightLight = 0;
    public int middleLight = -1; // Cannot be 0
    public int leftLight = 0;
    private static final int OSRS_NPCS_OFFSET = 15000;

    public static MobDefinition forID(int i) {
        if (i >= OSRS_NPCS_OFFSET) {
            i -= OSRS_NPCS_OFFSET;

            for (int j = 0; j < 20; j++) {
                if (cacheOSRS[j].type == (long) i) {
                    return cacheOSRS[j];
                }
            }

            cacheIndexOSRS = (cacheIndexOSRS + 1) % 20;

            MobDefinition npc = cacheOSRS[cacheIndexOSRS] = new MobDefinition();

            if (i >= streamIndicesOSRS.length) {
                return null;
            }

            streamOSRS.currentOffset = streamIndicesOSRS[i];
            npc.id = OSRS_NPCS_OFFSET + i;
            npc.type = OSRS_NPCS_OFFSET + i;
            npc.dataType = DataType.OLDSCHOOL;
            npc.readValues(streamOSRS);
            if (npc.name != null && npc.name.contains("Ket-Keh")) {
                npc.name = "Inferno";
                npc.actions = new String[5];
                npc.actions[0] = "Start";
             //   System.out.println("asdfasdfsfsdf " + npc.models[0]);
            }
            if (npc.name != null && npc.name.contains("00ffff")) {
                npc.name = npc.name.replaceAll("<col=00ffff>", "@cya@").replaceAll("</col>", "");
            }
            switch (i) {
                case 3113:
                    npc.name = "The Pet Tradesman";
                    break;
                case 2668:
                    npc.name = "Combat dummy";
                    npc.actions = new String[5];
                    npc.actions[1] = "Attack";
                    break;
                case 6332:
                    npc.name = "Dracula";
                    npc.sizeXZ = 180;
                    npc.sizeY = 180;
                    System.out.println(npc.standAnim);
                    npc.description = "Oh my god it's Dracula!";
                    break;
                case 2669:
                    npc.copy(forID(17668));
                    npc.name = "Muhammad Ali";
                    npc.actions = new String[5];
                    npc.actions[1] = "Attack";
                    npc.sizeXZ *= 3;
                    npc.sizeY *= 3;
                    npc.squaresNeeded = 4;
                    break;
                case 7413:
                    npc.name = "Undead Combat dummy";
                    npc.actions = new String[5];
                    npc.actions[1] = "Attack";
                    break;
                case 7519:
                    npc.name = "Superior Olmlet";
                    npc.actions = new String[5];
                    npc.actions[0] = "Pick-up";
                    npc.actions[2] = "Toggle Special";
                    break;
                case 311:
                    npc.actions[2] = "Trade";
                    break;
                case 8390:
                    npc.name = "Blood money dealer";
                    npc.standAnim = 22313;
                    npc.actions[2] = "Blood money";
                    npc.actions[3] = "Untradeables";
                    break;
                case 2047:
                    npc.actions[0] = "Pick-up";
                    break;
            }
            return npc;
        }

        for (int j = 0; j < 20; j++)
            if (cache[j].type == (long) i)
                return cache[j];
        cacheIndex = (cacheIndex + 1) % 20;
        MobDefinition npc = cache[cacheIndex] = new MobDefinition();
        if (i >= streamIndices.length)
            return null;
        stream.currentOffset = streamIndices[i];
        npc.type = i;
        npc.readValues(stream);

        if (npc.name != null && npc.name.toLowerCase().contains("bank")) {
            if (npc.actions != null) {
                for (int l = 0; l < npc.actions.length; l++) {
                    if (npc.actions[l] != null && npc.actions[l].equalsIgnoreCase("Collect"))
                        npc.actions[l] = null;
                }
            }
        }
        npc.id = i;

        if (i == 5210) {
            npc.name = "Boss fighter";
            npc.actions = new String[] {"Talk-to", null, "Spawn", null, null};
        }

        switch (i) {

            case 683:
                npc.name = "Ranged store";
                break;

            case 546:
                npc.name = "Magic store";
                npc.actions[3] = "Enchant";
                break;

            case 5111:

                npc.name = "Donator trader";
                npc.actions = new String[] {"Talk-to", null, "Trade", null, null};

                int[] tempModels = new int[npc.models.length + 1];

                System.arraycopy(npc.models, 0, tempModels, 0, npc.models.length);

                npc.models = tempModels;

                npc.models[9] = ItemDefinition.forID(1040).maleEquip1;

                break;

            case 541:
                npc.name = "Melee store";
                break;

            case 621:
                npc.name = "@or1@Halloween Twisted bow";
                npc.description = "A mystical bow carved from a very hot place.";
                npc.combatLevel = 0;
                npc.actions = new String[5];
                npc.actions[0] = "Take";
                npc.models = new int[1];
                npc.models[0] = 32799;
                npc.standAnim = 3853;
                npc.walkAnim = 3853;
                npc.sizeXZ = 120;
                npc.sizeY = 120;
                npc.squaresNeeded = 1;
                npc.originalColours = new int[]{16, 24, 33, 13223, 14236};
                npc.destColours = new int[]{4024, 4024, 7073, 4024, 4024};
                break;

            case 622:
                npc.name = "@or1@Dark twisted bow";
                npc.description = "A mystical bow carved from the twisted remains of the Great Olm.";
                npc.combatLevel = 0;
                npc.actions = new String[5];
                npc.actions[0] = "Take";
                npc.models = new int[1];
                npc.models[0] = 32799;
                npc.standAnim = 3853;
                npc.walkAnim = 3853;
                npc.sizeXZ = 120;
                npc.sizeY = 120;
                npc.squaresNeeded = 1;
                npc.originalColours = new int[]{16, 24, 33, 13223, 14236};
                npc.destColours = new int[]{1024, 1024, 937, 1024, 1024};
                break;

            case 623:
                npc.name = "@or1@Twisted bow";
                npc.description = "A mystical bow carved from the twisted remains of the Great Olm.";
                npc.combatLevel = 0;
                npc.actions = new String[5];
                npc.actions[0] = "Take";
                npc.models = new int[1];
                npc.models[0] = 32799;
                npc.standAnim = 3853;
                npc.walkAnim = 3853;
                npc.sizeXZ = 120;
                npc.sizeY = 120;
                npc.squaresNeeded = 1;
                break;

            case 624:
                npc.name = "@red@Scythe of Vitur X";
                npc.description = "It is the Scythe of Vitur X.";
                npc.combatLevel = 0;
                npc.actions = new String[5];
                npc.actions[0] = "Take";
                npc.models = new int[1];
                npc.models[0] = 35742;
                npc.standAnim = 3853;
                npc.walkAnim = 3853;
                npc.sizeXZ = 120;
                npc.sizeY = 120;
                npc.squaresNeeded = 1;
                npc.originalColours = new int[]{784, 790, 796, 536, 61, 78, 49};
                npc.destColours = new int[]{-1253, -1253, -1253, -1253, -1253, -1253, -1253};
                break;

            case 625:
                npc.name = "@red@Scythe of Vitur Y";
                npc.description = "It is the Scythe of Vitur Y.";
                npc.combatLevel = 0;
                npc.actions = new String[5];
                npc.actions[0] = "Take";
                npc.models = new int[1];
                npc.models[0] = 35742;
                npc.standAnim = 3853;
                npc.walkAnim = 3853;
                npc.sizeXZ = 120;
                npc.sizeY = 120;
                npc.squaresNeeded = 1;
                npc.originalColours = new int[]{584, 590, 596, 836, 61, 78, 49};
                npc.destColours = new int[]{14573, 14573, 14573, 14573, 14573, 14573, 14573};
                break;

            case 626:
                npc.name = "@or2@Scythe of Vitur";
                npc.description = "It is the Scythe of Vitur.";
                npc.combatLevel = 0;
                npc.actions = new String[5];
                npc.actions[0] = "Take";
                npc.models = new int[1];
                npc.models[0] = 35742;
                npc.standAnim = 3853;
                npc.walkAnim = 3853;
                npc.sizeXZ = 120;
                npc.sizeY = 120;
                npc.squaresNeeded = 1;
                break;

            case 3975:
                npc.name = "Max Hit Stone";
                npc.description = "Attack with any weapon of your choice to find out what your max hit is!";
                npc.actions = new String[5];
                npc.actions[1] = "Attack";
                break;

            case 132:
                npc.name = "Blitz";
                npc.description = "A master attacker of Varrock.";
                npc.combatLevel = 913;
                npc.actions = new String[5];
                npc.actions[1] = "Attack";
                npc.models = new int[9];
                npc.models[0] = 14395; // Hat
                npc.models[1] = 62746; // Platebody
                npc.models[2] = 62743; // Platelegs
                npc.models[3] = 62582; // Cape
                npc.models[4] = 13307; // Gloves
                npc.models[5] = 53327; // Boots
                npc.models[6] = 9642; // Amulet
                npc.models[7] = 2295; // Weapon
                npc.models[8] = 26423; // Shield
                npc.standAnim = 808;
                npc.walkAnim = 819;
                npc.npcHeadModels = MobDefinition.forID(517).npcHeadModels;
                npc.sizeXZ = 200;
                npc.sizeY = 200;
                npc.squaresNeeded = 2;
                break;
            case 133:
                npc.name = "Cobra";
                npc.description = "A master mager of Varrock.";
                npc.combatLevel = 903;
                npc.actions = new String[5];
                npc.actions[1] = "Attack";
                npc.models = new int[10];
                npc.models[0] = 3188; // Hat
                npc.models[1] = 58366; // Platebody
                npc.models[2] = 58333; // Platelegs
                npc.models[3] = 65297; // Cape
                npc.models[4] = 179; // Gloves
                npc.models[5] = 27738; // Boots
                npc.models[6] = 9642; // Amulet
                npc.models[7] = 56022; // Weapon
                npc.models[8] = 40942; // Shield
                npc.models[9] = 58316;
                npc.standAnim = 808;
                npc.walkAnim = 819;
                npc.npcHeadModels = MobDefinition.forID(517).npcHeadModels;
                npc.sizeXZ = 200;
                npc.sizeY = 200;
                npc.squaresNeeded = 2;
                npc.destColours = new int[]{226770, 34503, 34503, 34503, 34503};
                npc.originalColours = new int[]{926, 65214, 65200, 65186, 62995};
                break;
            case 135:
                npc.name = "Fear";
                npc.description = "A master ranger of Varrock.";
                npc.combatLevel = 844;
                npc.actions = new String[5];
                npc.actions[1] = "Attack";
                npc.models = new int[9];
                npc.models[0] = 26632; // Hat
                npc.models[1] = 20157; // Platebody
                npc.models[2] = 20139; // Platelegs
                npc.models[3] = 65297; // Cape
                npc.models[4] = 20129; // Gloves
                npc.models[5] = 27738; // Boots
                npc.models[6] = 9642; // Amulet
                npc.models[7] = 58380; // Weapon
                npc.models[8] = 20121;
                npc.standAnim = 808;
                npc.walkAnim = 819;
                npc.npcHeadModels = MobDefinition.forID(517).npcHeadModels;
                npc.sizeXZ = 200;
                npc.sizeY = 200;
                npc.destColours = ItemDefinition.forID(10372).newModelColor;
                npc.originalColours = ItemDefinition.forID(10372).editedModelColor;
                npc.squaresNeeded = 2;
                break;
            case 1472:
                npc.name = "Death";
                npc.description = "This boss looks so superior.";
                npc.combatLevel = 941;
                npc.actions = new String[5];
                npc.actions[1] = "Attack";
                npc.models = new int[9];
                npc.models[0] = 55770; // Hat
                npc.models[1] = 55851; // Platebody
                npc.models[2] = 55815; // Platelegs
                npc.models[3] = 65297; // Cape
                npc.models[4] = 55728; // Gloves
                npc.models[5] = 55673; // Boots
                npc.models[6] = 9642; // Amulet
                npc.models[7] = 56046; // Weapon
                npc.models[8] = 38941; // Shield
                npc.standAnim = 808;
                npc.walkAnim = 819;
                npc.npcHeadModels = MobDefinition.forID(517).npcHeadModels;
                npc.sizeXZ = 200;
                npc.sizeY = 200;
                npc.squaresNeeded = 2;
                npc.destColours = new int[]{127, 127, 127, 127};
                npc.originalColours = new int[]{65214, 65200, 65186, 62995};
                break;

            case 1337:
                npc.name = "Superior Arthur";
                npc.description = "The master of the universe.";
                npc.combatLevel = 913;
                npc.actions = new String[5];
                npc.actions[1] = "Attack";
                npc.models = new int[9];
                npc.models[0] = 55770; // Hat
                npc.models[1] = 62746; // Platebody
                npc.models[2] = 62743; // Platelegs
                npc.models[3] = 62582; // Cape
                npc.models[4] = 13307; // Gloves
                npc.models[5] = 53327; // Boots
                npc.models[6] = 9642; // Amulet
                npc.models[7] = 2295; // Weapon
                npc.models[8] = 26423; // Shield
                npc.standAnim = 808;
                npc.walkAnim = 819;
                npc.npcHeadModels = MobDefinition.forID(517).npcHeadModels;
                npc.sizeXZ = 200;
                npc.sizeY = 200;
                npc.squaresNeeded = 2;
                break;

            case 1335:
                npc.name = "Zamorak Dro";
                npc.description = "A cool looking dude, but Arthur looks cooler.";
                npc.combatLevel = 844;
                npc.actions = new String[5];
                npc.actions[1] = "Attack";
                npc.models = new int[9];
                npc.models[0] = 3188; // Hat
                npc.models[1] = 20157; // Platebody
                npc.models[2] = 20139; // Platelegs
                npc.models[3] = 65297; // Cape
                npc.models[4] = 20129; // Gloves
                npc.models[5] = 27738; // Boots
                npc.models[6] = 9642; // Amulet
                npc.models[7] = 58380; // Weapon
                npc.models[8] = 20121;
                npc.standAnim = 808;
                npc.walkAnim = 819;
                npc.npcHeadModels = MobDefinition.forID(517).npcHeadModels;
                npc.sizeXZ = 200;
                npc.sizeY = 200;
                npc.destColours = ItemDefinition.forID(10372).newModelColor;
                npc.originalColours = ItemDefinition.forID(10372).editedModelColor;
                npc.squaresNeeded = 2;
                break;

            case 1334:
                npc.name = "Master Lewis";
                npc.description = "A master mager of Varrock.";
                npc.combatLevel = 903;
                npc.actions = new String[5];
                npc.actions[1] = "Attack";
                npc.models = new int[10];
                npc.models[0] = 3188; // Hat
                npc.models[1] = 58366; // Platebody
                npc.models[2] = 58333; // Platelegs
                npc.models[3] = 65297; // Cape
                npc.models[4] = 179; // Gloves
                npc.models[5] = 27738; // Boots
                npc.models[6] = 9642; // Amulet
                npc.models[7] = 56022; // Weapon
                npc.models[8] = 40942; // Shield
                npc.models[9] = 58316;
                npc.standAnim = 808;
                npc.walkAnim = 819;
                npc.npcHeadModels = MobDefinition.forID(517).npcHeadModels;
                npc.sizeXZ = 200;
                npc.sizeY = 200;
                npc.squaresNeeded = 2;
                npc.destColours = new int[]{226770, 34503, 34503, 34503, 34503};
                npc.originalColours = new int[]{926, 65214, 65200, 65186, 62995};
                break;

            case 23611:
                npc.name = "Draconic Wyrm";
                npc.combatLevel = 399;
                break;

            case 3334:
                npc.name = "WildyWyrm";
                npc.models = new int[]{63604};
                //npc.boundDim = 1;
                npc.standAnim = 12790;
                npc.walkAnim = 12790;
                npc.combatLevel = 382;
                npc.actions = new String[5];
                npc.actions = new String[]{null, "Attack", null, null, null};
                npc.sizeXZ = 225;
                npc.sizeY = 200;
                //npc.sizeXZ = 35;
                //npc.sizeY = 75;
                break;
            case 1:
                npc.name = "Poison";
                npc.actions = new String[]{null, null, null, null, null};
                npc.sizeXZ = 1;
                npc.sizeY = 1;
                npc.drawMinimapDot = false;
                break;
            case 0:
                npc.name = " ";
                npc.actions = new String[]{null, null, null, null, null};
                npc.sizeXZ = 1;
                npc.sizeY = 1;
                npc.drawMinimapDot = false;
                break;
            case 6723:
                npc.name = "Rock Golem";
                npc.combatLevel = 0;
                npc.models = new int[1];
                npc.models[0] = 29755;
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.walkAnim = 7181;
                npc.standAnim = 7180;
                npc.description = "Its a Rock Golem.";
                npc.squaresNeeded = 1;
                npc.sizeXZ = npc.sizeY = 110;
                break;
            case 6724:
                npc.name = "Heron";
                npc.combatLevel = 0;
                npc.models = new int[1];
                npc.models[0] = 29756;
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.walkAnim = 6774;
                npc.standAnim = 6772;
                npc.description = "Its a Heron.";
                npc.squaresNeeded = 1;
                break;

            case 568:
                npc.name = "Note Trader";
                npc.actions = new String[]{"Trade", null, null, null, null};
                break;

            case 6726:
                npc.name = "Beaver";
                npc.combatLevel = 0;
                npc.models = new int[1];
                npc.models[0] = 29754;
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.walkAnim = 7178;
                npc.standAnim = 7177;
                npc.description = "Its a Beaver.";
                npc.squaresNeeded = 1;
                break;

            case 6640:
                npc.name = "Kraken";
                npc.models = new int[]{28231};
                //npc.boundDim = 1;
                npc.standAnim = 3989;
                npc.walkAnim = 3989;
                npc.sizeXZ = 25;
                npc.sizeY = 25;
                npc.actions = new String[5];
                npc.drawMinimapDot = false;
                npc.actions[0] = "Pick-up";
                npc.combatLevel = 0;
                npc.squaresNeeded = 1;
                break;

            case 963:
                npc.name = "Hellpuppy";
                npc.models = new int[]{29240};
                //npc.boundDim = 1;
                npc.standAnim = 6561;
                npc.walkAnim = 6560;
                npc.originalColours = new int[]{29270};
                npc.actions = new String[5];
                npc.drawMinimapDot = false;
                npc.actions[0] = "Pick-up";
                npc.combatLevel = 0;
                npc.squaresNeeded = 1;
                break;

            case 5781:
                npc.name = "Baby mole";
                npc.models = new int[]{12073};
                //npc.boundDim = 1;
                npc.standAnim = 3309;
                npc.walkAnim = 3313;
                npc.actions = new String[5];
                npc.drawMinimapDot = false;
                npc.actions[0] = "Pick-up";
                npc.combatLevel = 0;
                npc.squaresNeeded = 1;
                npc.sizeXZ = 80;
                npc.sizeY = 80;
                break;

            case 6727:
                npc.name = "Tangleroot";
                npc.combatLevel = 0;
                npc.models = new int[1];
                npc.models[0] = 32202;
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.walkAnim = 7313;
                npc.standAnim = 7312;
                npc.description = "Its a Tangleroot.";
                npc.squaresNeeded = 1;
                break;
            case 6728:
                npc.name = "Rocky";
                npc.combatLevel = 0;
                npc.models = new int[1];
                npc.models[0] = 32203;
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.walkAnim = 7316;
                npc.standAnim = 7315;
                npc.description = "Its a Rocky.";
                npc.squaresNeeded = 1;
                break;
            case 6729:
                npc.name = "Giant squirrel";
                npc.combatLevel = 0;
                npc.models = new int[1];
                npc.models[0] = 32206;
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.walkAnim = 7310;
                npc.standAnim = 7309;
                npc.description = "Its a Giant squirrel.";
                npc.squaresNeeded = 1;
                break;
            case 6730:
                npc.name = "Rift guardian";
                npc.combatLevel = 0;
                npc.models = new int[1];
                npc.models[0] = 32204;
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.walkAnim = 7306;
                npc.standAnim = 7307;
                npc.description = "Its a Rift guardian.";
                npc.squaresNeeded = 1;
                break;
            case 6731:
                npc.models = new int[1];
                npc.models[0] = 32697;
                npc.name = "Olmlet";
                npc.description = "Its a Olmlet.";
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.squaresNeeded = 1;
                npc.standAnim = 7396;
                npc.walkAnim = 7395;
                npc.sizeXZ = npc.sizeY = 65;
                break;

            case 2000:
                npc.models = new int[2];
                npc.models[0] = 28294;
                npc.models[1] = 28295;
                npc.name = "Venenatis";
                npc.actions = new String[]{null, "Attack", null, null, null};
                npc.sizeXZ = 200;
                npc.sizeY = 200;
                MobDefinition ven = forID(60);
                npc.standAnim = ven.standAnim;
                npc.walkAnim = ven.walkAnim;
                npc.combatLevel = 464;
                npc.squaresNeeded = 3;
                break;
            case 2042://turgoise
                npc.name = "Zulrah";
                npc.actions = new String[]{null, "Attack", null, null, null};
                npc.models = new int[1];
                npc.models[0] = 14407;
                npc.standAnim = 5070;
                npc.walkAnim = 5070;
                npc.combatLevel = 725;
                npc.sizeXZ = 128;
                npc.sizeY = 128;
                break;
            case 2043://regular
                npc.name = "Zulrah";
                npc.actions = new String[]{null, "Attack", null, null, null};
                npc.models = new int[1];
                npc.models[0] = 14408;
                npc.standAnim = 5070;
                npc.walkAnim = 5070;
                npc.combatLevel = 725;
                npc.sizeXZ = 128;
                npc.sizeY = 128;
                break;
            case 2044://melee
                npc.name = "Zulrah";
                npc.actions = new String[]{null, "Attack", null, null, null};
                npc.models = new int[1];
                npc.models[0] = 14409;
                npc.standAnim = 5070;
                npc.walkAnim = 5070;
                npc.combatLevel = 725;
                npc.sizeXZ = 128;
                npc.sizeY = 128;
                break;
            case 2001:
                npc.models = new int[1];
                npc.models[0] = 28293;
                npc.name = "Scorpia";
                npc.actions = new String[]{null, "Attack", null, null, null};
                MobDefinition scor = forID(107);
                npc.standAnim = scor.standAnim;
                npc.walkAnim = scor.walkAnim;
                npc.combatLevel = 464;
                npc.squaresNeeded = 3;
                break;
            case 7286:
                npc.name = "Skotizo";
                npc.description = "Badass from the depths of hell";
                npc.combatLevel = 321;
                MobDefinition skotizo = forID(4698);
                npc.standAnim = skotizo.standAnim;
                npc.walkAnim = skotizo.walkAnim;
                npc.actions = new String[5];
                npc.actions[1] = "Attack";
                npc.models = new int[1];
                npc.models[0] = 31653;
                npc.sizeXZ = 80; //resize if you wish hes a bit small cause personal preference
                npc.sizeY = 80; // resize
                npc.squaresNeeded = 3;
                break;
            case 6766:
                npc.name = "Lizardman shaman";
                npc.description = "It's a lizardman.";
                npc.combatLevel = 150;
                npc.walkAnim = 7195;
                npc.standAnim = 7191;
                npc.actions = new String[5];
                npc.actions[1] = "Attack";
                npc.models = new int[1];
                npc.models[0] = 4039;
                npc.sizeXZ = 108;
                npc.sizeY = 108;
                npc.squaresNeeded = 3;
                break;
            case 5886:
                npc.name = "Abyssal Sire";
                npc.description = "It's an abyssal sire.";
                npc.combatLevel = 350;
                npc.walkAnim = 4534;
                npc.standAnim = 4533;
                npc.actions = new String[5];
                npc.actions[1] = "Attack";
                npc.models = new int[1];
                npc.models[0] = 29477;
                npc.sizeXZ = 108;
                npc.sizeY = 108;
                npc.squaresNeeded = 6;
                break;
            case 499:
                npc.name = "Thermonuclear smoke devil";
                npc.description = "It looks like its glowing";
                npc.combatLevel = 301;
                npc.walkAnim = 1828;
                npc.standAnim = 1829;
                npc.actions = new String[5];
                npc.actions[1] = "Attack";
                npc.models = new int[1];
                npc.models[0] = 28442;
                npc.sizeXZ = 240;
                npc.sizeY = 240;
                npc.squaresNeeded = 4;
                break;
            case 1999:
                npc.models = new int[2];
                npc.name = "Cerberus";
                npc.models[1] = 29270;
                npc.combatLevel = 318;
                npc.standAnim = 4484;
                npc.walkAnim = 4488;
                npc.actions = new String[5];
                npc.originalColours = new int[]{29270};
                npc.actions = new String[]{null, "Attack", null, null, null};
                npc.sizeXZ = 120;
                npc.sizeY = 120;
                break;
            case 2009:
                npc.name = "Callisto";
                npc.models = new int[]{28298};
                npc.actions = new String[]{null, "Attack", null, null, null};
                npc.combatLevel = 470;
                MobDefinition callisto = forID(105);
                npc.standAnim = callisto.standAnim;
                npc.walkAnim = callisto.walkAnim;
                npc.actions = callisto.actions;
                npc.sizeXZ = npc.sizeY = 110;
                npc.squaresNeeded = 4;
                break;
            case 2006:
                npc.models = new int[1];
                npc.models[0] = 28300;
                npc.name = "Vet'ion";
                npc.actions = new String[]{null, "Attack", null, null, null};
                MobDefinition vet = forID(90);
                npc.standAnim = vet.standAnim;
                npc.walkAnim = vet.walkAnim;
                npc.combatLevel = 464;
                break;

            case 3847:
                npc.name = "Kraken";
                npc.combatLevel = 291;
                npc.models = new int[]{28231};
                npc.standAnim = 3989;
                npc.walkAnim = forID(3847).walkAnim;
                npc.sizeXZ = npc.sizeY = 130;
                npc.lightning = 30;
                npc.shadow = 150;
                break;


            case 2004:
                npc.models = new int[1];
                npc.models[0] = 28231;
                npc.name = "Cave kraken";
                npc.actions = new String[]{null, "Attack", null, null, null};
                MobDefinition cave = forID(3847);
                npc.models = new int[1];
                npc.models[0] = 28233;
                npc.combatLevel = 127;
                npc.standAnim = 3989;
                npc.walkAnim = cave.walkAnim;
                npc.sizeXZ = npc.sizeY = 42;
                break;
            case 457:
                npc.name = "Ghost Town Citizen";
                npc.actions = new String[]{"Talk-to", null, "Teleport", null, null};
                break;
            case 241:
                npc.name = "Boss point dealer";
                break;
            case 543:
                npc.name = "Decanter";
                break;
            case 1396:
                npc.name = "Expert Miner";
                npc.actions = new String[]{"Talk-To", null, "Trade", null, null};
                break;
            case 5417:
                npc.combatLevel = 210;
                break;
            case 5418:
                npc.combatLevel = 90;
                break;
            case 6715:
                npc.combatLevel = 91;
                break;
            case 6716:
                npc.combatLevel = 128;
                break;
            case 6701:
                npc.combatLevel = 173;
                break;
            case 6725:
                npc.combatLevel = 224;
                break;
            case 6691:
                npc.squaresNeeded = 2;
                npc.combatLevel = 301;
                break;
            case 741:
                npc.actions = new String[]{"Open", null, null, null, null};
                npc.name = "Donator Shop";
                break;
            case 367:
                npc.name = "Item Gambler";
                break;

            case 2538:
                npc.name = "Supply store";
                break;

            case 725:
                npc.name = "Trivia Point Shop";
                break;
            case 8710:
            case 8707:
            case 8706:
            case 8705:
                npc.name = "Musician";
                npc.actions = new String[]{"Listen-to", null, null, null, null};
                break;

            case 947:
                npc.name = "Auctioneer";
                npc.actions = new String[]{"Talk-to", null, "Search", "History", null, null};
                break;
            case 465:
                npc.name = "Master Zulri";
                break;
            case 9939:
                npc.combatLevel = 607;
                break;
            case 149:
                npc.name = "Whirlpool";
                npc.models = new int[]{26699};
                npc.actions = new String[]{null, "Disturb", null, null, null};
                npc.standAnim = 6737;
                npc.walkAnim = 6737;
                npc.squaresNeeded = 4;
                npc.combatLevel = 0;
                npc.sizeY = 130;
                npc.sizeXZ = 130;
                npc.lightning = 30;
                npc.shadow = 150;
                break;
            case 148:
                npc.name = "Enormous Tentacle";
                npc.models = new int[]{13201,};
                npc.actions = new String[]{null, "Attack", null, null, null};
                npc.standAnim = 3617;
                npc.walkAnim = 3617;
                npc.combatLevel = 0;
                npc.sizeY = 200;
                npc.sizeXZ = 200;
                break;
            case 150://small
                npc.name = "Whirlpool";
                npc.models = new int[]{26699};
                npc.actions = new String[]{null, "Disturb", null, null, null};
                npc.standAnim = 6737;
                npc.walkAnim = 6737;
                npc.combatLevel = 0;
                npc.sizeY = 55;
                npc.sizeXZ = 55;
                npc.lightning = 30;
                npc.shadow = 150;
                break;
            case 688:
                npc.name = "Archer";
                break;
            case 4540:
                npc.combatLevel = 299;
                break;
            case 3101:
                npc.sizeY = npc.sizeXZ = 80;
                npc.squaresNeeded = 1;
                npc.actions = new String[]{"Talk-to", null, "Start", "Rewards", null};
                break;
            case 8275:
                npc.actions[2] = "Get-task";
                break;
            case 6222:
                npc.name = "Kree'arra";
                npc.squaresNeeded = 5;
                npc.standAnim = 6972;
                npc.walkAnim = 6973;
                npc.actions = new String[]{null, "Attack", null, null, null};
                npc.sizeY = npc.sizeXZ = 110;
                break;
            case 6203:
                npc.models = new int[]{27768, 27773, 27764, 27765, 27770};
                npc.name = "K'ril Tsutsaroth";
                npc.squaresNeeded = 5;
                npc.standAnim = 6943;
                npc.walkAnim = 6942;
                npc.actions = new String[]{null, "Attack", null, null, null};
                npc.sizeY = npc.sizeXZ = 110;
                break;
            case 1610:
            case 491:
            case 10216:
                npc.actions = new String[]{null, "Attack", null, null, null};
                break;
            case 7969:
                npc.actions = new String[]{"Talk-to", null, "Trade", null, null};
                break;
            case 1382:
                npc.name = "Glacor";
                npc.models = new int[]{58940};
                npc.squaresNeeded = 3;
                //	npc.anInt86 = 475;
                npc.sizeXZ = npc.sizeY = 180;
                npc.standAnim = 10869;
                npc.walkAnim = 10867;
                npc.actions = new String[]{null, "Attack", null, null, null};
                npc.combatLevel = 123;
                npc.drawMinimapDot = true;
                npc.combatLevel = 188;
                break;
			/*case 1383:
			npc.name = "Unstable glacyte";
			npc.models = new int[]{58942};
			npc.standAnim = 10867;
			npc.walkAnim = 10901;
			npc.actions = new String[]{null, "Attack", null, null, null};
			npc.combatLevel = 101;
			npc.drawMinimapDot = false;
			break;
		case 1384:
			npc.name = "Sapping glacyte";
			npc.models = new int[]{58939};
			npc.standAnim = 10867;
			npc.walkAnim = 10901;
			npc.actions = new String[]{null, "Attack", null, null, null};
			npc.combatLevel = 101;
			npc.drawMinimapDot = true;
			break;
		case 1385:
			npc.name = "Enduring glacyte";
			npc.models = new int[]{58937};
			npc.standAnim = 10867;
			npc.walkAnim = 10901;
			npc.actions = new String[]{null, "Attack", null, null, null};
			npc.combatLevel = 101;
			npc.drawMinimapDot = true;
			break;*/
            case 4249:
                npc.name = "Gambler";
                break;

            case 710:
                npc.actions = new String[]{"Open", null, null, null, null};
                npc.name = "Donator Shop 3";
                break;
            case 6970:
                npc.actions = new String[]{"Trade", null, "Exchange Shards", null, null};
                break;
            case 4657:
                npc.actions = new String[]{"Talk-to", null, "Claim Items", "Check Total", "Teleport"};
                break;
            case 605:
                npc.actions = new String[]{"Talk-to", null, "Vote Rewards", "Loyalty Titles", null};
                break;
            case 8591:
                npc.actions = new String[]{"Talk-to", null, "Trade", null, null};
                break;
            case 4285:
                npc.actions = new String[]{"Talk-to", null, "Trade", null, null};
                break;
            case 316:
            case 315:
            case 309:
            case 310:
            case 314:
            case 312:
            case 313:
                npc.sizeXZ = 30;
                break;
            case 318:
                npc.sizeXZ = 30;
                npc.actions = new String[]{"Net", null, "Lure", null, null};
                break;
            case 805:
                npc.actions = new String[]{"Trade", null, "Tan hide", null, null};
                break;

            case 670:
                npc.copy(MobDefinition.forID(659));
                npc.name = "Pre-Release Pete";
                npc.destColours = new int[] {7114, 7114, 5938 + 4, 5938 + 21, 5938 + 13, 5938 + 11, 5938 + 16, 5938, 5938 + 3};
                npc.originalColours = new int[] {926, 656, 654, 671, 663, 661, 666, 650, 653};
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.sizeY = 90;
                npc.sizeXZ = 90;
                break;

            case 671:
                npc.copy(MobDefinition.forID(12377));
                npc.name = "Pumpkin Pete";
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.sizeY = 90;
                npc.sizeXZ = 90;
                break;

            case 461:
            case 844:
            case 650:
            case 5112:
            case 3789:
            case 802:
            case 520:
            case 521:
            case 11226:
                npc.actions = new String[]{"Trade", null, null, null, null};
                break;
            case 8022:
            case 8028:
                String color = i == 8022 ? "Yellow" : "Green";
                npc.name = "" + color + " energy source";
                npc.actions = new String[]{"Siphon", null, null, null, null};
                break;
            case 8444:
                npc.actions = new String[5];
                npc.actions[0] = "Trade";
                break;
            case 2579:
                npc.name = "Max";
                npc.description = "He's mastered the many skills of Varrock.";
                npc.combatLevel = 138;
                npc.actions = new String[5];
                npc.actions[0] = "Talk-to";
                npc.actions[2] = "Trade";
                npc.standAnim = 808;
                npc.walkAnim = 819;
                npc.models = new int[]{65291, 65322, 506, 529, 252, 9642, 62746, 13307, 62743, 53327};
                npc.npcHeadModels = new int[]{39332, 39235};
                break;
            case 2580:
                npc.name = "Skiller";
                npc.description = "He's mastered the many skills of Varrock.";
                npc.combatLevel = 3;
                npc.actions = new String[5];
                npc.actions[0] = "Talk-to";
                npc.actions[2] = "Trade";
                npc.actions[3] = "Teleport";
                npc.standAnim = 808;
                npc.walkAnim = 819;
                npc.models = new int[]{
                        ItemDefinition.forID(13101).maleEquip1,
                        ItemDefinition.forID(10408).maleEquip1,
                        ItemDefinition.forID(10408).maleEquip2,
                        ItemDefinition.forID(10410).maleEquip1,
                        ItemDefinition.forID(12170).maleEquip1,
                        ItemDefinition.forID(1704).maleEquip1,
                        ItemDefinition.forID(88).maleEquip1,
                        ItemDefinition.forID(1580).maleEquip1,
                        IDK.cache[4].bodyModelIDs[0],
                        IDK.cache[14].bodyModelIDs[0],
                };
                break;
            case 6830:
            case 6841:
            case 6796:
            case 7331:
            case 6831:
            case 7361:
            case 6847:
            case 6872:
            case 7353:
            case 6835:
            case 6845:
            case 6808:
            case 7370:
            case 7333:
            case 7351:
            case 7367:
            case 6853:
            case 6855:
            case 6857:
            case 6859:
            case 6861:
            case 6863:
            case 9481:
            case 6827:
            case 6889:
            case 6813:
            case 6817:
            case 7372:
            case 6839:
            case 8575:
            case 7345:
            case 6799:
            case 7335:
            case 7347:
            case 6800:
            case 9488:
            case 6804:
            case 6822:
            case 6849:
            case 7355:
            case 7357:
            case 7359:
            case 7341:
            case 7329:
            case 7339:
            case 7349:
            case 7375:
            case 7343:
            case 6820:
            case 6865:
            case 6809:
            case 7363:
            case 7337:
            case 7365:
            case 6991:
            case 6992:
            case 6869:
            case 6818:
            case 6843:
            case 6823:
            case 7377:
            case 6887:
            case 6885:
            case 6883:
            case 6881:
            case 6879:
            case 6877:
            case 6875:
            case 6833:
            case 6851:
            case 5079:
            case 5080:
            case 6824:
                npc.actions = new String[]{null, null, null, null, null};
                break;
            case 6806: // thorny snail
            case 6807:
            case 6994: // spirit kalphite
            case 6995:
            case 6867: // bull ant
            case 6868:
            case 6794: // spirit terrorbird
            case 6795:
            case 6815: // war tortoise
            case 6816:
            case 6874:// pack yak
            case 6873: // pack yak
            case 3594: // yak
            case 3590: // war tortoise
            case 3596: // terrorbird
                npc.actions = new String[]{"Store", null, null, null, null};
                break;
            case 548:
                npc.actions = new String[]{"Trade", null, null, null, null};
                break;
            case 3299:
            case 437:
                npc.actions = new String[]{"Trade", null, null, null, null};
                break;

            case 1267:
            case 8459:
                npc.drawMinimapDot = true;
                break;
            case 961:
                npc.actions = new String[]{null, null, "Buy Consumables", "Restore Stats", null};
                npc.name = "Healer";
                break;
            case 705:
                npc.actions = new String[]{null, null, "Buy Armour", "Buy Weapons", "Buy Jewelries"};
                npc.name = "Warrior";
                break;
            case 1861:
                npc.actions = new String[]{null, null, "Buy Equipment", "Buy Ammunition", null};
                npc.name = "Archer";
                break;
            case 946:
                npc.actions = new String[]{null, null, "Buy Equipment", "Buy Runes", null};
                npc.name = "Mage";
                break;
            case 2253:
                npc.actions = new String[]{null, null, "Buy Skillcapes", "Buy Skillcapes (t)", "Buy Hoods"};
                break;
            case 2292:
                npc.actions = new String[]{"Trade", null, null, null, null};
                npc.name = "Merchant";
                break;
            case 2676:
                npc.actions = new String[]{"Makeover", null, null, null, null};
                break;
            case 494:
            case 1360:
                npc.actions = new String[]{"Talk-to", null, null, null, null};
                break;
            case 1685:
                npc.name = "Pure";
                npc.actions = new String[]{"Trade", null, null, null, null};
                break;
            case 3030:
                npc.name = "King black dragon";
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.models = new int[]{17414, 17415, 17429, 17422};
                npc.combatLevel = 276;
                npc.standAnim = 90;
                npc.walkAnim = 4635;
                npc.sizeY = 40;
                npc.sizeXZ = 40;
                npc.squaresNeeded = 3;
                break;

            case 3031:
                npc.name = "General graardor";
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.models = new int[]{27785, 27789};
                npc.combatLevel = 624;
                npc.standAnim = 7059;
                npc.walkAnim = 7058;
                npc.sizeY = 29;
                npc.sizeXZ = 33;
                break;

            case 3032:
                npc.name = "TzTok-Jad";
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.models = new int[]{34131};
                npc.combatLevel = 702;
                npc.standAnim = 9274;
                npc.walkAnim = 9273;
                npc.sizeY = 25;
                npc.sizeXZ = 27;
                npc.squaresNeeded = 1;
                break;

            case 3033:
                npc.name = "Chaos elemental";
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.models = new int[]{11216};
                npc.combatLevel = 305;
                npc.standAnim = 3144;
                npc.walkAnim = 3145;
                npc.sizeY = 49;
                npc.sizeXZ = 45;
                break;

            case 3034:
                npc.name = "Corporeal beast";
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.models = new int[]{40955};
                npc.combatLevel = 785;
                npc.standAnim = 10056;
                npc.walkAnim = 10055;
                npc.sizeY = 24;
                npc.sizeXZ = 25;
                npc.squaresNeeded = 1;
                break;

            case 3035:
                npc.name = "Kree'arra";
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.models = new int[]{28003, 28004};
                npc.combatLevel = 580;
                npc.standAnim = 6972;
                npc.walkAnim = 6973;
                npc.sizeY = 23;
                npc.sizeXZ = 23;
                npc.squaresNeeded = 1;
                break;

            case 3036:
                npc.name = "K'ril tsutsaroth";
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.models = new int[]{27768, 27773, 27764, 27765, 27770};
                npc.combatLevel = 650;
                npc.standAnim = 6943;
                npc.walkAnim = 6942;
                npc.sizeY = 24;
                npc.sizeXZ = 24;
                npc.squaresNeeded = 1;
                break;
            case 3037:
                npc.name = "Commander zilyana";
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.models = new int[]{28057, 28071, 28078, 28056};
                npc.combatLevel = 596;
                npc.standAnim = 6963;
                npc.walkAnim = 6962;
                npc.sizeY = 60;
                npc.sizeXZ = 60;
                npc.squaresNeeded = 1;
                break;
            case 3038:
                npc.name = "Dagannoth supreme";
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.models = new int[]{9941, 9943};
                npc.combatLevel = 303;
                npc.standAnim = 2850;
                npc.walkAnim = 2849;
                npc.sizeY = 60;
                npc.sizeXZ = 60;
                npc.squaresNeeded = 1;
                break;

            case 3039:
                npc.name = "Dagannoth prime"; //9940, 9943, 9942
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.models = new int[]{9940, 9943, 9942};
                npc.originalColours = new int[]{11930, 27144, 16536, 16540};
                npc.destColours = new int[]{5931, 1688, 21530, 21534};
                npc.combatLevel = 303;
                npc.standAnim = 2850;
                npc.walkAnim = 2849;
                npc.sizeY = 60;
                npc.sizeXZ = 60;
                npc.squaresNeeded = 1;
                break;

            case 3040:
                npc.name = "Dagannoth rex";
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.models = new int[]{9941};
                npc.originalColours = new int[]{16536, 16540, 27144, 2477};
                npc.destColours = new int[]{7322, 7326, 10403, 2595};
                npc.combatLevel = 303;
                npc.standAnim = 2850;
                npc.walkAnim = 2849;
                npc.sizeY = 60;
                npc.sizeXZ = 60;
                npc.squaresNeeded = 1;
                break;
            case 3047:
                npc.name = "Frost dragon";
                npc.combatLevel = 166;
                npc.standAnim = 13156;
                npc.walkAnim = 13157;
                npc.turn180AnimIndex = -1;
                npc.turn90CCWAnimIndex = -1;
                npc.turn90CWAnimIndex = -1;
                //npc.type = 51;
                npc.degreesToTurn = 32;
                npc.models = new int[]{56767, 55294};
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.sizeY = 45;
                npc.sizeXZ = 45;
                npc.squaresNeeded = 1;
                break;

            case 3048:
                npc.models = new int[]{44733};
                npc.name = "Tormented demon";
                npc.combatLevel = 450;
                npc.standAnim = 10921;
                npc.walkAnim = 10920;
                npc.turn180AnimIndex = -1;
                npc.turn90CCWAnimIndex = -1;
                npc.turn90CWAnimIndex = -1;
                //	npc.type = 8349;
                npc.degreesToTurn = 32;
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.sizeY = 40;
                npc.sizeXZ = 40;
                npc.squaresNeeded = 1;
                break;
            case 3050:
                npc.models = new int[]{24602, 24605, 24606};
                npc.name = "Kalphite queen";
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.combatLevel = 333;
                npc.standAnim = 6236;
                npc.walkAnim = 6236;
                npc.sizeY = 40;
                npc.sizeXZ = 40;
                npc.squaresNeeded = 1;
                break;
            case 3051:
                npc.models = new int[]{46141};
                npc.name = "Slash bash";
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.combatLevel = 111;
                npc.standAnim = 11460;
                npc.walkAnim = 11461;
                npc.sizeY = 45;
                npc.sizeXZ = 45;
                npc.squaresNeeded = 1;
                break;
            case 3052:
                npc.models = new int[]{45412};
                npc.name = "Phoenix";
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.combatLevel = 235;
                npc.standAnim = 11074;
                npc.walkAnim = 11075;
                npc.sizeY = 45;
                npc.sizeXZ = 45;
                npc.squaresNeeded = 1;
                break;
            case 3053:
                npc.models = new int[]{46058, 46057};
                npc.name = "Bandos avatar";
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.combatLevel = 299;
                npc.standAnim = 11242;
                npc.walkAnim = 11255;
                npc.sizeY = 45;
                npc.sizeXZ = 45;
                npc.squaresNeeded = 1;
                break;
            case 3054:
                npc.models = new int[]{62717};
                npc.name = "Nex";
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.combatLevel = 565;
                npc.standAnim = 6320;
                npc.walkAnim = 6319;
                npc.sizeY = 55;
                npc.sizeXZ = 55;
                npc.squaresNeeded = 1;
                break;
            case 3055:
                npc.models = new int[]{51852, 51853};
                npc.name = "Jungle strykewyrm";
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.combatLevel = 110;
                npc.standAnim = 12790;
                npc.walkAnim = 12790;
                npc.sizeY = 39;
                npc.sizeXZ = 35;
                npc.squaresNeeded = 1;
                break;
            case 3056:
                npc.models = new int[]{51848, 51850};
                npc.name = "Desert strykewyrm";
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.combatLevel = 130;
                npc.standAnim = 12790;
                npc.walkAnim = 12790;
                npc.sizeY = 39;
                npc.sizeXZ = 35;
                npc.squaresNeeded = 1;
                break;

            case 3057:
                npc.models = new int[]{51847, 51849};
                npc.name = "Ice strykewyrm";
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.combatLevel = 210;
                npc.standAnim = 12790;
                npc.walkAnim = 12790;
                npc.sizeY = 39;
                npc.sizeXZ = 35;
                npc.squaresNeeded = 1;
                break;
            case 3058:
                npc.models = new int[]{49142, 49144};
                npc.name = "Green dragon";
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.combatLevel = 79;
                npc.standAnim = 12248;
                npc.walkAnim = 12246;
                npc.sizeY = 40;
                npc.sizeXZ = 40;
                npc.squaresNeeded = 1;
                break;
            case 3059:
                npc.models = new int[]{57937};
                npc.name = "Baby blue dragon";
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.combatLevel = 48;
                npc.standAnim = 14267;
                npc.walkAnim = 14268;
                npc.sizeY = 70;
                npc.sizeXZ = 70;
                npc.squaresNeeded = 1;
                break;
            case 3060:
                npc.models = new int[]{49137, 49144};
                npc.name = "Blue dragon";
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.combatLevel = 111;
                npc.standAnim = 12248;
                npc.walkAnim = 12246;
                npc.sizeY = 45;
                npc.sizeXZ = 45;
                npc.squaresNeeded = 1;
                break;
            case 3061:
                npc.models = new int[]{14294, 49144};
                npc.name = "Black dragon";
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.combatLevel = 227;
                npc.standAnim = 12248;
                npc.walkAnim = 12246;
                npc.sizeY = 45;
                npc.sizeXZ = 45;
                npc.squaresNeeded = 1;
                break;
            case 3062:
                npc.models = new int[2];
                npc.models[0] = 28294;
                npc.models[1] = 28295;
                npc.name = "Venenatis";
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.sizeXZ = 45;
                npc.sizeY = 45;
                MobDefinition ven2 = forID(60);
                npc.standAnim = ven2.standAnim;
                npc.walkAnim = ven2.walkAnim;
                npc.combatLevel = 464;
                npc.squaresNeeded = 2;
                break;
            case 3063:
                npc.models = new int[1];
                npc.models[0] = 28293;
                npc.name = "Scorpia";
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                MobDefinition scor2 = forID(107);
                npc.standAnim = scor2.standAnim;
                npc.walkAnim = scor2.walkAnim;
                npc.sizeXZ = 55;
                npc.sizeY = 55;
                npc.combatLevel = 464;
                npc.squaresNeeded = 1;
                break;
            case 3064:
                npc.name = "Skotizo";
                npc.combatLevel = 321;
                MobDefinition skotizo2 = forID(4698);
                npc.standAnim = skotizo2.standAnim;
                npc.walkAnim = skotizo2.walkAnim;
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.models = new int[1];
                npc.models[0] = 31653;
                npc.sizeXZ = 22;
                npc.sizeY = 22;
                npc.squaresNeeded = 1;
                break;
            case 3065:

                npc.name = "Lizardman Shaman";
                npc.description = "It's a lizardman.";
                npc.combatLevel = 150;
                npc.walkAnim = 7195;
                npc.standAnim = 7191;
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.models = new int[1];
                npc.models[0] = 4039;
                npc.sizeXZ = 38;
                npc.sizeY = 38;
                npc.squaresNeeded = 1;
                break;

            case 3066:
                npc.name = "WildyWyrm";
                npc.models = new int[]{63604};
                //npc.boundDim = 1;
                npc.standAnim = 12790;
                npc.walkAnim = 12790;
                npc.combatLevel = 382;
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.sizeXZ = 30;
                npc.sizeY = 40;
                npc.squaresNeeded = 1;
                //npc.sizeXZ = 35;
                //npc.sizeY = 75;
                break;
            case 3067:
                npc.name = "Bork";
                npc.models = new int[]{40590};
                //npc.boundDim = 1;
                npc.standAnim = 8753;
                npc.walkAnim = 8752;
                npc.combatLevel = 267;
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.sizeXZ = 40;
                npc.sizeY = 40;
                npc.squaresNeeded = 1;
                //npc.sizeXZ = 35;
                //npc.sizeY = 75;
                break;

            case 3068:
                npc.name = "Barrelchest";
                npc.models = new int[]{22790};
                //npc.boundDim = 1;
                npc.standAnim = 5893;
                npc.walkAnim = 5892;
                npc.combatLevel = 267;
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.sizeXZ = 40;
                npc.sizeY = 40;
                npc.squaresNeeded = 1;
                break;
            case 3069:
                npc.name = "Rock Crab";
                npc.models = new int[2];
                npc.models[0] = 4399;
                npc.models[1] = 4400;
                //npc.boundDim = 1;
                npc.standAnim = 1310;
                npc.walkAnim = 1311;
                npc.combatLevel = 13;
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.sizeXZ = 80;
                npc.sizeY = 80;
                npc.squaresNeeded = 1;
                break;
            case 3070:
                npc.name = "Abyssal Sire";
                npc.description = "It's an abyssal sire.";
                npc.combatLevel = 350;
                npc.walkAnim = 4534;
                npc.standAnim = 4533;
                npc.actions = new String[5];
                npc.actions[0] = "Pick-up";
                npc.models = new int[1];
                npc.models[0] = 29477;
                npc.sizeXZ = 28;
                npc.sizeY = 28;
                npc.squaresNeeded = 1;
                break;
            case 153:
                npc.copy(forID(23009));
                npc.name = "Superior TzRek-Zuk";
                break;
            case 154:
                npc.copy(forID(23025));
                npc.name = "Superior Vorki";
                break;
		/*  		     
		case 1265:
			 System.out.println("Models: " + npc.models[1]);
			 System.out.println("Stand animation: "  +npc.standAnim);
			 System.out.println("Walk animation: " + npc.walkAnim);

				break; */
        }
        return npc;
    }

    public Model getHeadModel() {
        if (childrenIDs != null) {
            MobDefinition altered = getAlteredNPCDef();
            if (altered == null)
                return null;
            else
                return altered.getHeadModel();
        }
        if (npcHeadModels == null)
            return null;
        boolean everyFetched = false;
        for (int i = 0; i < npcHeadModels.length; i++)
            if (!Model.modelIsFetched(npcHeadModels[i], dataType))
                everyFetched = true;
        if (everyFetched)
            return null;
        Model parts[] = new Model[npcHeadModels.length];
        for (int j = 0; j < npcHeadModels.length; j++)
            parts[j] = Model.fetchModel(npcHeadModels[j], dataType);
        Model completeModel;
        if (parts.length == 1)
            completeModel = parts[0];
        else
            completeModel = new Model(parts.length, parts);
        if (originalColours != null) {
            for (int k = 0; k < originalColours.length; k++)
                completeModel.recolour(originalColours[k], destColours[k]);
        }
        return completeModel;
    }

    public MobDefinition getAlteredNPCDef() {
        try {
            int j = -1;
            if (varbitId != -1) {
                VarBit varBit = VarBit.cache[varbitId];
                int k = varBit.configId;
                int l = varBit.leastSignificantBit;
                int i1 = varBit.mostSignificantBit;
                int j1 = Client.anIntArray1232[i1 - l];
                j = (int) (clientInstance.variousSettings[k] >> l & j1);
            } else if (varSettingsId != -1) {
                j = (int) clientInstance.variousSettings[varSettingsId];
            }
            if (j < 0 || j >= childrenIDs.length || childrenIDs[j] == -1) {
                return null;
            } else {
                return forID(childrenIDs[j]);
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static void writeOut(int total) {
        try {
            PrintWriter writer = new PrintWriter("../osrsNpcs.txt");
            for (int i = OSRS_NPCS_OFFSET; i < total + OSRS_NPCS_OFFSET; i++) {
                MobDefinition entityDef = forID(i);

                if (entityDef == null)
                    continue;

                writer.println(i + " " + entityDef.name + " " + " [Cbt=" + entityDef.combatLevel + "], [Anims="
                        + (entityDef.walkAnim) + " " + (entityDef.standAnim) + "], [Models="
                        + (entityDef.models != null && entityDef.models.length > 0 ? entityDef.models[0] + " " : "")
                        + (entityDef.models != null && entityDef.models.length > 1
                        ? entityDef.models[1]
                        : "")
                        + (entityDef.models != null && entityDef.models.length > 2
                        ? " " + entityDef.models[2]
                        : "")
                        + (entityDef.models != null && entityDef.models.length > 3
                        ? " " + entityDef.models[3]
                        : "")
                        + "], [Sizes=" + entityDef.sizeXZ + "]");

            }
            writer.close();

            writer = new PrintWriter("../allNpcs.txt");
            for (int i = 0; i < total + OSRS_NPCS_OFFSET; i++) {
                MobDefinition entityDef = forID(i);

                if (entityDef == null)
                    continue;

                writer.println(i + " " + entityDef.name + " " + " [Cbt=" + entityDef.combatLevel + "], [Anims="
                        + (entityDef.walkAnim) + " " + (entityDef.standAnim) + "], [Models="
                        + (entityDef.models != null && entityDef.models.length > 0 ? entityDef.models[0] + " " : "")
                        + (entityDef.models != null && entityDef.models.length > 1
                        ? entityDef.models[1]
                        : "")
                        + (entityDef.models != null && entityDef.models.length > 2
                        ? " " + entityDef.models[2]
                        : "")
                        + (entityDef.models != null && entityDef.models.length > 3
                        ? " " + entityDef.models[3]
                        : "")
                        + "], [Sizes=" + entityDef.sizeXZ + "]");

            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void copy(MobDefinition def) {
        name = def.name;
        description = def.description;
        combatLevel = def.combatLevel;

        models = new int[def.models.length];

        for (int i = 0; i < models.length; i++) {
            models[i] = def.models[i];
        }

        actions = new String[def.actions.length];

        for (int i = 0; i < actions.length; i++) {
            actions[i] = def.actions[i];
        }

        if (def.originalColours != null) {
            originalColours = new int[def.originalColours.length];

            for (int i = 0; i < originalColours.length; i++) {
                originalColours[i] = def.originalColours[i];
            }
        }

        if (def.destColours != null) {
            destColours = new int[def.destColours.length];

            for (int i = 0; i < def.destColours.length; i++) {
                destColours[i] = def.destColours[i];
            }
        }

        if (def.childrenIDs != null) {
            childrenIDs = new int[def.childrenIDs.length];

            for (int i = 0; i < def.childrenIDs.length; i++) {
                childrenIDs[i] = def.childrenIDs[i];
            }
        }

        if (def.npcHeadModels != null) {
            npcHeadModels = new int[def.npcHeadModels.length];

            for (int i = 0; i < def.npcHeadModels.length; i++) {
                npcHeadModels[i] = def.npcHeadModels[i];
            }
        }

        dataType = def.dataType;
        drawMinimapDot = def.drawMinimapDot;
        sizeXZ = def.sizeXZ;
        sizeY = def.sizeY;
        shadow = def.shadow;
        lightning = def.lightning;
        backLight = def.backLight;
        frontLight = def.frontLight;
        leftLight = def.leftLight;
        middleLight = def.middleLight;
        rightLight = def.rightLight;
        standAnim = def.standAnim;
        walkAnim = def.walkAnim;
        squaresNeeded = def.squaresNeeded;
        hasRenderPriority = def.hasRenderPriority;
        degreesToTurn = def.degreesToTurn;
        varbitId = def.varbitId;
        varSettingsId = def.varSettingsId;
    }

    public static int NPCAMOUNT = 11599;

    public static void unpackConfig(CacheArchive streamLoader) {
        stream = new Stream(streamLoader.getDataForName("npc.dat"));
        Stream stream2 = new Stream(streamLoader.getDataForName("npc.idx"));
        streamOSRS = new Stream(streamLoader.getDataForName("npc3.dat"));
        Stream stream3 = new Stream(streamLoader.getDataForName("npc3.idx"));

        //FileOperations.WriteFile(signlink.findcachedir() + "npc3.dat", streamLoader.getDataForName("npc3.dat"));
        //FileOperations.WriteFile(signlink.findcachedir() + "npc3.idx", streamLoader.getDataForName("npc3.idx"));


        int totalNPCs = stream2.readUnsignedWord();
        int totalOSRSNPCs = stream3.readUnsignedWord();
        streamIndices = new int[totalNPCs];
        streamIndicesOSRS = new int[totalOSRSNPCs];

        int i = 2;

        for (int j = 0; j < totalNPCs; j++) {
            streamIndices[j] = i;
            i += stream2.readUnsignedWord();
        }

        i = 2;

        for (int j = 0; j < totalOSRSNPCs; j++) {
            streamIndicesOSRS[j] = i;
            i += stream3.readUnsignedWord();
        }

        cache = new MobDefinition[20];
        cacheOSRS = new MobDefinition[20];

        for (int k = 0; k < 20; k++) {
            cache[k] = new MobDefinition();
            cacheOSRS[k] = new MobDefinition();
        }

        //writeOut(totalOSRSNPCs);
    }

    public static void nullLoader() {
        modelCache = null;
        modelCacheOSRS = null;
        streamIndices = null;
        cache = null;
        stream = null;
    }

    public Model getAnimatedModel(int j, int k, int ai[]) {
        if (childrenIDs != null) {
            MobDefinition npc = getAlteredNPCDef();
            if (npc == null)
                return null;
            else
                return npc.getAnimatedModel(j, k, ai);
        }
        Model completedModel = dataType == DataType.OLDSCHOOL ? (Model) modelCacheOSRS.get(type) : (Model) modelCache.get(type);
        if (completedModel == null) {
            boolean everyModelFetched = false;
            for (int ptr = 0; ptr < models.length; ptr++)
                if (!Model.modelIsFetched(models[ptr], dataType))
                    everyModelFetched = true;

            if (everyModelFetched)
                return null;
            Model parts[] = new Model[models.length];
            for (int j1 = 0; j1 < models.length; j1++)
                parts[j1] = Model.fetchModel(models[j1], dataType);
            if (parts.length == 1)
                completedModel = parts[0];
            else
                completedModel = new Model(parts.length, parts);
            if (originalColours != null) {
                for (int k1 = 0; k1 < originalColours.length; k1++)
                    completedModel.recolour(originalColours[k1], destColours[k1]);
            }
            completedModel.createBones();
            completedModel.light(frontLight, backLight, rightLight, middleLight, leftLight, true);
            if (dataType == DataType.OLDSCHOOL) {
                modelCacheOSRS.put(completedModel, type);
            } else {
                modelCache.put(completedModel, type);
            }
        }
        Model animatedModel = Model.entityModelDesc;
        animatedModel.method464(completedModel, FrameReader.isNullFrame(k) & FrameReader.isNullFrame(j));
        if (k != -1 && j != -1)
            animatedModel.method471(ai, j, k, dataType);
        else if (k != -1)
            animatedModel.applyTransform(k, dataType);
        if (sizeXZ != 128 || sizeY != 128)
            animatedModel.scaleT(sizeXZ, sizeXZ, sizeY);
        animatedModel.calculateDiagonals();
        animatedModel.triangleSkin = null;
        animatedModel.vertexSkin = null;
        if (squaresNeeded == 1)
            animatedModel.rendersWithinOneTile = true;

        return animatedModel;
    }

    public Model method164(int j, int frame, int ai[], int nextFrame, int idk, int idk2) {
        if (childrenIDs != null) {
            MobDefinition npc = getAlteredNPCDef();
            if (npc == null)
                return null;
            else
                return npc.method164(j, frame, ai, nextFrame, idk, idk2);
        }
        Model completedModel = dataType == DataType.OLDSCHOOL ? (Model) modelCacheOSRS.get(type) : (Model) modelCache.get(type);
        if (completedModel == null) {
            boolean everyModelFetched = false;
            for (int ptr = 0; ptr < models.length; ptr++)
                if (!Model.modelIsFetched(models[ptr], dataType))
                    everyModelFetched = true;

            if (everyModelFetched)
                return null;
            Model parts[] = new Model[models.length];
            for (int j1 = 0; j1 < models.length; j1++)
                parts[j1] = Model.fetchModel(models[j1], dataType);
            if (parts.length == 1)
                completedModel = parts[0];
            else
                completedModel = new Model(parts.length, parts);
            if (originalColours != null) {
                for (int k1 = 0; k1 < originalColours.length; k1++)
                    completedModel.recolour(originalColours[k1], destColours[k1]);
            }
            completedModel.createBones();
            completedModel.light(frontLight, backLight, rightLight, middleLight, leftLight, true);
            if (dataType == DataType.OLDSCHOOL) {
                modelCacheOSRS.put(completedModel, type);
            } else {
                modelCache.put(completedModel, type);
            }
        }
        Model animatedModel = Model.entityModelDesc;
        animatedModel.method464(completedModel, FrameReader.isNullFrame(frame) & FrameReader.isNullFrame(j));

        if (frame != -1 && j != -1)
            animatedModel.method471(ai, j, frame, dataType);
        else if (frame != -1 && nextFrame != -1)
            animatedModel.applyTransform(frame, nextFrame, idk, idk2, dataType);
        else if (frame != -1)
            animatedModel.applyTransform(frame, dataType);
        if (sizeXZ != 128 || sizeY != 128)
            animatedModel.scaleT(sizeXZ, sizeXZ, sizeY);
        animatedModel.calculateDiagonals();
        animatedModel.triangleSkin = null;
        animatedModel.vertexSkin = null;
        if (squaresNeeded == 1)
            animatedModel.rendersWithinOneTile = true;

        if(this.id == 659) {
           // animatedModel.printColors(659);
        }

        return animatedModel;
    }

    public void readValues(Stream stream) {
        boolean osrs = stream == streamOSRS;
        do {
            int i = stream.readUnsignedByte();
            if (i == 0)
                return;
            if (i == 1) {
                int j = stream.readUnsignedByte();
                models = new int[j];
                for (int j1 = 0; j1 < j; j1++)
                    models[j1] = stream.readUnsignedWord();
            } else if (i == 2)
                name = osrs ? stream.readString() : stream.readNewString();
            else if (i == 3) {
                description = osrs ? new String(stream.readBytes()) : stream.readNewString();
            } else if (i == 12)
                squaresNeeded = stream.readSignedByte();
            else if (i == 13) {
                standAnim = stream.readUnsignedWord();
                if (osrs) {
                    standAnim += Animation.OSRS_ANIM_OFFSET;
                }
            } else if (i == 14) {
                walkAnim = stream.readUnsignedWord();

                if (osrs) {
                    runAnim = walkAnim += Animation.OSRS_ANIM_OFFSET;
                }
            } else if (i == 17) {
                walkAnim = stream.readUnsignedWord();
                turn180AnimIndex = stream.readUnsignedWord();
                turn90CWAnimIndex = stream.readUnsignedWord();
                turn90CCWAnimIndex = stream.readUnsignedWord();
                if (walkAnim == 65535)
                    walkAnim = -1;
                if (turn180AnimIndex == 65535)
                    turn180AnimIndex = -1;
                if (turn90CWAnimIndex == 65535)
                    turn90CWAnimIndex = -1;
                if (turn90CCWAnimIndex == 65535)
                    turn90CCWAnimIndex = -1;
                if (osrs) {
                    if (walkAnim != -1)
                        walkAnim += Animation.OSRS_ANIM_OFFSET;
                    if (turn180AnimIndex != -1)
                        turn180AnimIndex += Animation.OSRS_ANIM_OFFSET;
                    if (turn90CWAnimIndex != -1)
                        turn90CWAnimIndex += Animation.OSRS_ANIM_OFFSET;
                    if (turn90CCWAnimIndex != -1)
                        turn90CCWAnimIndex += Animation.OSRS_ANIM_OFFSET;
                }
            } else if (i >= 30 && i < 40) {
                if (actions == null)
                    actions = new String[5];
                actions[i - 30] = osrs ? stream.readString() : stream.readNewString();
                if (actions[i - 30].equalsIgnoreCase("hidden"))
                    actions[i - 30] = null;
            } else if (i == 40) {
                int k = stream.readUnsignedByte();
                destColours = new int[k];
                originalColours = new int[k];
                for (int k1 = 0; k1 < k; k1++) {
                    originalColours[k1] = stream.readUnsignedWord();
                    destColours[k1] = stream.readUnsignedWord();
                }
            } else if (i == 60) {
                int l = stream.readUnsignedByte();
                npcHeadModels = new int[l];
                for (int l1 = 0; l1 < l; l1++)
                    npcHeadModels[l1] = stream.readUnsignedWord();
            } else if (i == 90)
                stream.readUnsignedWord();
            else if (i == 91)
                stream.readUnsignedWord();
            else if (i == 92)
                stream.readUnsignedWord();
            else if (i == 93)
                drawMinimapDot = false;
            else if (i == 95)
                combatLevel = stream.readUnsignedWord();
            else if (i == 97)
                sizeXZ = stream.readUnsignedWord();
            else if (i == 98)
                sizeY = stream.readUnsignedWord();
            else if (i == 99)
                hasRenderPriority = true;
            else if (i == 100)
                lightning = stream.readSignedByte();
            else if (i == 101)
                shadow = stream.readSignedByte() * 5;
            else if (i == 102)
                headIcon = stream.readUnsignedWord();
            else if (i == 103)
                degreesToTurn = stream.readUnsignedWord();
            else if (i == 106) {
                varbitId = stream.readUnsignedWord();
                if (varbitId == 65535)
                    varbitId = -1;
                varSettingsId = stream.readUnsignedWord();
                if (varSettingsId == 65535)
                    varSettingsId = -1;
                int i1 = stream.readUnsignedByte();
                childrenIDs = new int[i1 + 1];
                for (int i2 = 0; i2 <= i1; i2++) {
                    childrenIDs[i2] = stream.readUnsignedWord();
                    if (childrenIDs[i2] == 65535)
                        childrenIDs[i2] = -1;
                }
            } else if (i == 107)
                clickable = false;
        } while (true);
    }

    public MobDefinition() {
        turn90CCWAnimIndex = -1;
        varbitId = -1;
        turn180AnimIndex = -1;
        varSettingsId = -1;
        combatLevel = -1;
        walkAnim = -1;
        squaresNeeded = 1;
        headIcon = -1;
        standAnim = -1;
        type = -1L;
        degreesToTurn = 32;
        turn90CWAnimIndex = -1;
        clickable = true;
        sizeY = 128;
        drawMinimapDot = true;
        sizeXZ = 128;
        hasRenderPriority = false;
    }

    public int turn90CCWAnimIndex;
    public static int cacheIndex;
    public static int cacheIndexOSRS;
    public int varbitId;
    public int turn180AnimIndex;
    public int varSettingsId;
    public static Stream stream;
    public static Stream streamOSRS;
    public int combatLevel;
    public String name;
    public String actions[];
    public int walkAnim;
    public int runAnim;
    public byte squaresNeeded;
    public int[] destColours;
    public static int[] streamIndices;
    public static int[] streamIndicesOSRS;
    public int[] npcHeadModels;
    public int headIcon;
    public int[] originalColours;
    public int standAnim;
    public long type;
    public int degreesToTurn;
    public static MobDefinition[] cache;
    public static MobDefinition[] cacheOSRS;
    public static Client clientInstance;
    public int turn90CWAnimIndex;
    public boolean clickable;
    public int lightning;
    public int sizeY;
    public boolean drawMinimapDot;
    public int childrenIDs[];
    public String description;
    public int sizeXZ;
    public int shadow;
    public boolean hasRenderPriority;
    public int[] models;
    public static MemCache modelCache = new MemCache(30);
    public static MemCache modelCacheOSRS = new MemCache(30);
    public int id;
    public DataType dataType = DataType.REGULAR;
}