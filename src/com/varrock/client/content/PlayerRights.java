package com.varrock.client.content;

import com.varrock.Configuration;

/**
 * Player rights enum that MUST be synced with servers rights.
 *
 * @author Gabriel || Wolfsdarker
 */
public enum PlayerRights {

    //to get ingame crown do crownId - 679

    PLAYER(-1, PlayerRightsType.DEFAULT),
    MODERATOR(680, PlayerRightsType.STAFF),
    ADMINISTRATOR(681, PlayerRightsType.STAFF),
    OWNER(681, PlayerRightsType.STAFF),
    DEVELOPER(682, PlayerRightsType.STAFF),

    REGULAR_DONATOR(684, 14, -1, PlayerRightsType.DONATOR),
    SUPER_DONATOR(685, 14, -1, PlayerRightsType.DONATOR),
    EXTREME_DONATOR(686, 14, -1, PlayerRightsType.DONATOR),
    PLATINUM_DONATOR(687, 15, -1, PlayerRightsType.DONATOR),
    EXECUTIVE_DONATOR(688, 15, -2, PlayerRightsType.DONATOR),

    SUPPORT(1360, 14, -1, PlayerRightsType.STAFF),

    YOUTUBER(690, 18, 0, PlayerRightsType.STAFF),

    //RUBY_MEMBER(691, 14, 1, PlayerRightsType.DONATOR),
   // MANAGER(1034, 20, 1, PlayerRightsType.STAFF),
/*    SAPPHIRE(693, PlayerRightsType.DONATOR),
    EMERALD(694, PlayerRightsType.DONATOR),
    ONYX(695, PlayerRightsType.DONATOR),
    CRYSTAL(1141, 12, 0, PlayerRightsType.DONATOR),*/
    GLOBAL_MOD(1160, 16, 0, PlayerRightsType.STAFF),

    BETA_TESTER(1378, 16, -2, PlayerRightsType.STAFF),

   // SUPER_ADMIN(1033, 16, 0, PlayerRightsType.STAFF),

    IRON_MAN(712, PlayerRightsType.CUSTOM_GAME_MODE),
    ULTIMATE_IRON_MAN(711, PlayerRightsType.CUSTOM_GAME_MODE),
    HARDCORE_IRON_MAN(1142, PlayerRightsType.CUSTOM_GAME_MODE),

    ;

    /**
     * The crown sprite ID.
     */
    private int crownId;

    /**
     * The draw offset in the X coordinate.
     */
    private int drawOffsetX;

    /**
     * The draw offset in the Y coordinate.
     */
    private int drawOffsetY;

    /**
     * Is this rank a donator?
     */
    private PlayerRightsType playerRightsType;

    PlayerRights(int crownId, PlayerRightsType playerRightsType) {
        this.crownId = crownId;
        this.drawOffsetX = 17;
        this.drawOffsetY = 1;
        this.playerRightsType = playerRightsType;
    }

    PlayerRights(int crownId, int drawOffsetX, int drawOffsetY, PlayerRightsType playerRightsType) {
        this.crownId = crownId;
        this.drawOffsetX = drawOffsetX;
        this.drawOffsetY = drawOffsetY;
        this.playerRightsType = playerRightsType;
    }

    public int getCrownId() {
        return crownId;
    }

    public int getDrawOffsetX() {
        return drawOffsetX;
    }

    public int getDrawOffsetY() {
        return drawOffsetY;
    }

    public boolean isHighStaff() {
        return this == ADMINISTRATOR || this == OWNER || this == DEVELOPER;
    }

    public static PlayerRights get(int rights) {
        if(rights == 60) {
            return PlayerRights.IRON_MAN;
        }
        if(rights == 61) {
            return PlayerRights.ULTIMATE_IRON_MAN;
        }
        if (rights >= values().length) {
            return PLAYER;
        }
        return values()[rights];
    }

    public void setPlayerRightsType(PlayerRightsType playerRightsType) {
        this.playerRightsType = playerRightsType;
    }

    public boolean isStaff() {
        return this.playerRightsType == PlayerRightsType.STAFF;
    }

    public boolean isDonator() {
        return this.playerRightsType == PlayerRightsType.DONATOR;
    }

    public boolean isCustomGameMode() {
        return this.playerRightsType == PlayerRightsType.CUSTOM_GAME_MODE;
    }
}
