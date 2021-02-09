package com.zyrox.client;

import java.awt.Graphics2D;

public class SkillOrbs {

    /**
     * The array containing all skill orbs.
     * Each skill orb per available skill.
     */
    public static final SkillOrb[] orbs = new SkillOrb[Skills.SKILL_COUNT];

    public static Graphics2D g2d;

    /**
     * Initializes orbs and their sprites.
     */
    public static void init() {
        for (int i = 0; i < Skills.SKILL_COUNT; i++) {
            orbs[i] = new SkillOrb(i, Client.cacheSprite[i == 24 ? 1166 : 41 + i == 64 ? 95 : 41 + i]);
        }
    }

    /**
     * Processes all orbs.
     */
    public static void process() {

        g2d = DrawingArea.createGraphics(true);

        int totalOrbs = 0;

        for (SkillOrb orb : orbs) {
            if (draw(orb)) {
                totalOrbs++;
            }
        }

        int y = 12;
        
        int x = Client.getClient().getClientSize() == 0 ? (int) (Client.clientWidth / 3.1) - (totalOrbs * 30) : (Client.clientWidth / 2) - (totalOrbs * 30);
        
        if (x < 5) {
            x = 5;
        }

        SkillOrb hover = null;

        for (SkillOrb orb : orbs) {
            if (draw(orb)) {

                if (orb.getShowTimer().finished()) {
                    orb.decrementAlpha();
                }

                orb.draw(x, y);

                if (Client.instance.hover(x, y, Client.cacheSprite[1157])) {
                    hover = orb;
                }

                x += 62;

                int xLimit = Client.getClient().getClientSize() == 0 ? 300 : Client.clientWidth - 203;
                if (x > xLimit + 160) {
                    break;
                }
            }
        }

        if (hover != null) {
            hover.drawTooltip();
        }
        
        g2d.dispose();
    }

    /**
     * Should a skillorb be drawn?
     *
     * @param orb
     * @return
     */
    private static boolean draw(SkillOrb orb) {
        return !orb.getShowTimer().finished() || orb.getAlpha() > 0;
    }

    /**
     * Are any orbs being drawn?
     */
    public static boolean showingOrbs() {
        for (SkillOrb orb : orbs)
            if (draw(orb))
                return true;
        return false;
    }
}
