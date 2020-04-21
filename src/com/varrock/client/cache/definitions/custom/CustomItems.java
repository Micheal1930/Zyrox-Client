package com.varrock.client.cache.definitions.custom;

import com.varrock.client.cache.definitions.ItemDefinition;

public class CustomItems {

    public static ItemDefinition loadDefinition(ItemDefinition itemDef) {
        switch (itemDef.id) {

            case 14433:
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 32799;
                itemDef.name = "Halloween Twisted Bow Pet";
                itemDef.modelZoom = 2000;
                itemDef.rotationY = 720;
                itemDef.rotationX = 1500;
                itemDef.modelOffset1 = 3;
                itemDef.modelOffsetY = 1;
                itemDef.description = "A mystical bow carved from a very hot place.";
                itemDef.editedModelColor = new int[]{16, 24, 33, 13223, 14236};
                itemDef.newModelColor = new int[]{4024, 4024, 7073, 4024, 4024};
                return itemDef;

            case 14434:
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 32799;
                itemDef.name = "Dark Twisted Bow Pet";
                itemDef.modelZoom = 2000;
                itemDef.rotationY = 720;
                itemDef.rotationX = 1500;
                itemDef.modelOffset1 = 3;
                itemDef.modelOffsetY = 1;
                itemDef.description = "A mystical bow carved from a very hot place.";
                itemDef.editedModelColor = new int[]{16, 24, 33, 13223, 14236};
                itemDef.newModelColor = new int[]{1024, 1024, 937, 1024, 1024};
                return itemDef;

            case 14435:
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.modelID = 32799;
                itemDef.name = "Twisted Bow Pet";
                itemDef.modelZoom = 2000;
                itemDef.rotationY = 720;
                itemDef.rotationX = 1500;
                itemDef.modelOffset1 = 3;
                itemDef.modelOffsetY = 1;
                itemDef.description = "A mystical bow carved from a very hot place.";
                return itemDef;

            case 14438:
                itemDef.modelID = 35742;
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.name = "@or2@Scythe Of Vitur Pet";
                itemDef.description = "It is the Scythe Of Vitur";
                itemDef.modelZoom = 2200;
                itemDef.stackable = false;
                itemDef.rotationX = 23;
                itemDef.rotationY = 327;
                itemDef.maleEquip1 = 35371;
                itemDef.femaleEquip1 = 35371;
                return itemDef;

            case 6199:
                itemDef.name = "Mystery Box";
                return itemDef;

            case 4003:
                itemDef.copy(ItemDefinition.forID(6199));
                itemDef.actions = new String[5];
                itemDef.actions[0] = "Open";
                itemDef.name = "Super Mystery Box";
                itemDef.editedModelColor = new int[] {22410, 2999};
                itemDef.newModelColor = new int[] {302770, 127};
                itemDef.description = "A box that is full of mysterious items!";
                return itemDef;

            case 4005:
                itemDef.copy(ItemDefinition.forID(6199));
                itemDef.actions = new String[5];
                itemDef.actions[0] = "Open";
                itemDef.name = "Ultimate Mystery Box";
                itemDef.editedModelColor = new int[] {22410, 2999};
                itemDef.newModelColor = new int[] {127, 935};
                itemDef.description = "A box that is full of mysterious items!";
                itemDef.stackable = false;
                return itemDef;

            case 4007:
                itemDef.copy(ItemDefinition.forID(6199));
                itemDef.actions = new String[5];
                itemDef.actions[0] = "Open";
                itemDef.name = "Supreme Mystery Box";
                itemDef.editedModelColor = new int[] {22410, 2999};
                itemDef.newModelColor = new int[] {350770, 127};
                itemDef.description = "A box that is full of mysterious items!";
                itemDef.stackable = false;
                return itemDef;

            case 11848:
                itemDef.copy(ItemDefinition.forID(30_000 + 12877));
                itemDef.actions = new String[5];
                itemDef.actions[0] = "Open";
                return itemDef;

            case 11850:
                itemDef.copy(ItemDefinition.forID(30_000 + 12873));
                itemDef.actions = new String[5];
                itemDef.actions[0] = "Open";
                return itemDef;

            case 11854:
                itemDef.copy(ItemDefinition.forID(30_000 + 12879));
                itemDef.actions = new String[5];
                itemDef.actions[0] = "Open";
                return itemDef;

            case 11856:
                itemDef.copy(ItemDefinition.forID(30_000 + 12875));
                itemDef.actions = new String[5];
                itemDef.actions[0] = "Open";
                return itemDef;

            case 11846:
                itemDef.copy(ItemDefinition.forID(30_000 + 12881));
                itemDef.actions = new String[5];
                itemDef.actions[0] = "Open";
                return itemDef;

            case 11852:
                itemDef.copy(ItemDefinition.forID(30_000 + 12883));
                itemDef.actions = new String[5];
                itemDef.actions[0] = "Open";
                return itemDef;
            case 4736:
            case 17215:
                itemDef.femaleEquip1 = itemDef.maleEquip1;
                itemDef.femaleEquip2 = itemDef.femaleEquip2;
                return itemDef;

            case 19123:
                itemDef.copy(ItemDefinition.forID(15335));
                itemDef.toCustomNote(15335);
                return itemDef;

            case 19124:
                itemDef.copy(ItemDefinition.forID(15334));
                itemDef.toCustomNote(15334);
                return itemDef;
            case 19125:
                itemDef.copy(ItemDefinition.forID(15333));
                itemDef.toCustomNote(15333);
                return itemDef;
            case 19126:
                itemDef.copy(ItemDefinition.forID(15332));
                itemDef.toCustomNote(15332);
                return itemDef;

            case 14913:
                itemDef.copy(ItemDefinition.forID(11614));
                itemDef.editedModelColor = new int[]{40023, 40036, 1822, 36, 25, 34243, 9230, 40040, 10348, 48, 34251, 0, 43335, 6218, 23, 11013};
                itemDef.newModelColor = new int[]{90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 90, 70};
                itemDef.name = "Angelic Cape";
                return itemDef;

        }
        return null;
    }

}
