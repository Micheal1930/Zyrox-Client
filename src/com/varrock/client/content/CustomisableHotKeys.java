package com.varrock.client.content;

import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.Set;

import com.varrock.client.Client;
import com.varrock.client.RSInterface;
import com.varrock.client.content.drop_down.DropDownAction;
import com.varrock.client.content.drop_down.DropDownMenu;

/**
 * Created using Eclipse.
 *
 * @author Ali | (https://www.rune-server.ee/members/_ali/)
 */
@SuppressWarnings("serial")
public class CustomisableHotKeys {

    public static final int interfaceID = 90000;

    public enum ChildTabKeyRelation {

        COMBAT_TAB(90040, 0, 1, KeyEvent.VK_F1),
        SKILLS_TAB(90041, 1, 2, KeyEvent.VK_F2),
        QUEST_TAB(90042, 2, -1, -1),
        ACHIEVEMENTS(90043, 14, 3, KeyEvent.VK_ESCAPE),
        INVENTORY_TAB(90044, 3, 4, KeyEvent.VK_F3),
        EQUIPMENT_TAB(90045, 4, 5, KeyEvent.VK_F4),
        PRAYER_TAB(90046, 5, 6, KeyEvent.VK_F5),
        SPELLBOOK_TAB(90047, 6, 7, KeyEvent.VK_F6),
        FAMILAR_TAB(90048, 13, 8, KeyEvent.VK_F7),
        FRIENDS_TAB(90049, 8, 9, KeyEvent.VK_F8),
        IGNORE_TAB(90050, 9, 9, KeyEvent.VK_F9),
        CLANCHAT_TAB(90051, 7, 10, KeyEvent.VK_F10),
        SETTINGS_TAB(90052, 11, 11, KeyEvent.VK_F11),
        EMOTES_TAB(90053, 12, 12, KeyEvent.VK_F12),
        ;

        /**
         * ChildId of the tab.
         */
        private final int childId;

        /**
         * Drop menu option identifier number.
         */
        private final int tabId;

        /**
         * Drop menu option identifier number.
         */
        private final int identifier;

        /**
         * Key code in relation to the identifer.
         */
        private final int keyCode;

        ChildTabKeyRelation(final int childId, final int tabId, int identifier, final int keyCode) {
            this.childId = childId;
            this.tabId = tabId;
            this.identifier = identifier;
            this.keyCode = keyCode;
        }

        private static final Set<ChildTabKeyRelation> VALUES = Collections.unmodifiableSet(EnumSet.allOf(ChildTabKeyRelation.class));

        public int getChildId() {
            return childId;
        }

        public int getTabId() {
            return tabId;
        }

        public int getKeyCode() {
            return keyCode;
        }


        public static int getIdentifierByKey(int keyId) {
            for (ChildTabKeyRelation value : VALUES) {
                if (value.keyCode == keyId) {
                    return value.identifier;
                }
            }
            return -1;
        }

        public static int getKeyIdByIdentifier(int identifier) {
            for (ChildTabKeyRelation value : VALUES) {
                if (value.identifier == identifier) {
                    return value.keyCode;
                }
            }
            return -1;
        }

    }

    /**
     * The radio button config value setting identifier.
     */
    public static final int ESC_VALUE_SETTING_IDENTIFIER = 400;

    /**
     * Does esc key close interfaces?/
     */
    private static boolean escClosesInterface = true;

    private static LinkedList<HotKey> hotKeys = new LinkedList<HotKey>() {
        {
            for (ChildTabKeyRelation value : ChildTabKeyRelation.VALUES) {
                add(new HotKey(value.childId, value.identifier, value.keyCode, value.tabId));
            }
        }
    };

    /**
     * The hotkeys.
     *
     * @return the hotkeys
     */
    public static LinkedList<HotKey> getHotKeys() {
        return hotKeys;
    }

    /**
     * Checks whether the key pressed is a match for the available hot keys. If
     * so opens the tab the {@value keyCode} is binded to. If there are no saved
     * customised hot keys then the default ones from class initialisation is
     * used.
     *
     * @param keyCode - The key this user has pressed.
     */
    public static void checkPressedKey(final int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_F1:
            case KeyEvent.VK_F2:
            case KeyEvent.VK_F3:
            case KeyEvent.VK_F4:
            case KeyEvent.VK_F5:
            case KeyEvent.VK_F6:
            case KeyEvent.VK_F7:
            case KeyEvent.VK_F8:
            case KeyEvent.VK_F9:
            case KeyEvent.VK_F10:
            case KeyEvent.VK_F11:
            case KeyEvent.VK_F12:
            case KeyEvent.VK_ESCAPE:

                HotKey hotkey = null;

                for (HotKey hotKey : hotKeys) {
                    if (hotKey.keyId == keyCode) {
                        hotkey = hotKey;
                    }
                }

                System.out.println("Tab Id: " + (hotkey != null ? hotkey.tabId : -1));

                if (hotkey != null) {
                    Client.getClient().setTab(hotkey.tabId);
                    if (keyCode == KeyEvent.VK_ESCAPE && isEscClosesInterface()) {
                        Client.instance.clearTopInterfaces();
                        break;
                    }
                } else {
                    if (keyCode == KeyEvent.VK_ESCAPE && isEscClosesInterface()) {
                        Client.instance.clearTopInterfaces();
                        break;
                    }
                }

                break;
        }

    }

    /**
     * Restore the default Pre-Eoc Keys for this user.
     */
    public static void restoreDefaults() {

        hotKeys.clear();

        for (ChildTabKeyRelation value : ChildTabKeyRelation.VALUES) {
            hotKeys.add(new HotKey(value.childId, value.identifier, value.keyCode, value.tabId));
        }

        if (isEscClosesInterface()) {
            setEscConfigButtonSettings(0);
            setEscClosesInterface(false);
        }

    }

    /**
     * Updates the drop down menu display to the correct message dependent on
     * players customised keys.
     */
    public static void updateDropDownMenuDisplaysOnLogin() {

        for (HotKey hotKey : hotKeys) {

            final DropDownMenu inter = (DropDownMenu) RSInterface.interfaceCache[hotKey.childId];

            if (inter != null) {
                if (hotKey.keyId < 0) {
                    inter.message = "None";
                } else {
                    inter.message = getActionMessage(inter, hotKey.keyId);
                }
            }
        }

        if (isEscClosesInterface()) {
            setEscConfigButtonSettings(1);
        }

    }

    /**
     * Set the config button status, to show the end user whether they have the
     * esc closes interface option selected or not.
     *
     * @param status 0 to turn the config button tick off, 1 to represent Esc closes interface is on.
     */
    private static void setEscConfigButtonSettings(final int status) {
        Client.instance.varbitConfigs[ESC_VALUE_SETTING_IDENTIFIER] = status;
        Client.instance.variousSettings[ESC_VALUE_SETTING_IDENTIFIER] = status;
        Client.getClient().handleActions(ESC_VALUE_SETTING_IDENTIFIER);
    }

    /**
     * Handles the swap between 2 tab F Keys. Self explanatory.
     *
     * @param childId The childId we are handling as known as the tab.
     * @param keyUsed The drop down menu selection unique Id as known as
     *                identifier/action id.
     */
    public static void processHotKeySelection(final int childId, final int keyUsed) {

        HotKey key = null;
        int keyIndex = -1;

        for (int i = 0; i < hotKeys.size(); i++) {
            if (hotKeys.get(i).childId == childId) {
                key = hotKeys.get(i);
                keyIndex = i;
            }
        }

        if (key == null) {
            return;
        }

        if (keyUsed == key.keyId) {
            return;
        }

        HotKey oldKey = null;
        int oldKeyIndex = -1;

        for (int i = 0; i < hotKeys.size(); i++) {
            if (hotKeys.get(i).keyId == keyUsed) {
                oldKey = hotKeys.get(i);
                oldKeyIndex = i;
            }
        }

        if (oldKey != null) {
            hotKeys.get(oldKeyIndex).setKeyId(-1);
            DropDownMenu inter = (DropDownMenu) RSInterface.interfaceCache[oldKey.childId];
            inter.message = "None";
        }

        hotKeys.get(keyIndex).setKeyId(keyUsed);
        DropDownMenu inter = (DropDownMenu) RSInterface.interfaceCache[childId];
        inter.message = getActionMessage(inter, keyUsed);
    }

    public static String getActionMessage(DropDownMenu menu, int keyId) {
        for (DropDownAction action : menu.getActions()) {
            if (action.getIdentifier() == keyId) {
                return action.getMessage();
            }
        }
        return "None";
    }

    public static boolean isEscClosesInterface() {
        return escClosesInterface;
    }

    public static void setEscClosesInterface(boolean escClosesInterface) {
        CustomisableHotKeys.escClosesInterface = escClosesInterface;
    }


    private CustomisableHotKeys() {

    }

    public static class HotKey {

        private final int childId;

        private int keyId;

        private final int identifierId;

        private final int tabId;


        public HotKey(int childId, int identifierId, int keyId, int tabId) {
            this.childId = childId;
            this.identifierId = identifierId;
            this.keyId = keyId;
            this.tabId = tabId;
        }

        public int getChildId() {
            return childId;
        }

        public int getIdentifierId() {
            return identifierId;
        }

        public int getTabId() {
            return tabId;
        }

        public int getKeyId() {
            return keyId;
        }

        public void setKeyId(int keyId) {
            this.keyId = keyId;
        }
    }

}
