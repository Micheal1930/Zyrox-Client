package com.varrock.client;

import java.awt.Color;
import java.awt.Shape;
import java.text.NumberFormat;
import java.util.Locale;

import com.varrock.util.SecondsTimer;

public class SkillOrb {

    /**
     * The skill this orb is intended for.
     */
    private final int skill;

    /**
     * The sprite icon for this skill orb.
     */
    private final Sprite icon;

    /**
     * The show timer. Resets when this orb
     * receives experience.
     */
    private SecondsTimer showTimer = new SecondsTimer();

    /**
     * The orb's current alpha (transparency)
     */
    private int alpha;

    /**
     * The angle representing XP progress, in degrees
     */
    private int progressAngle = 0;

    /**
     * The change in angle since last draw() call
     */
    private int progressChange = 0;

    /**
     * The angle stepper. Used to achieve a smooth increase transition on XP gain
     */
    private int angleStepper = 0;

    /**
     * Time since draw() was last called
     */
    private long lastDraw = 0;

    /**
     * Constructs this skill orb
     *
     * @param skill
     */
    public SkillOrb(int skill, Sprite icon) {
        this.skill = skill;
        this.icon = icon;
    }

    /**
     * Called upon the player receiving experience.
     * <p>
     * Resets the attributes of the orb
     * to make sure the orb is drawn
     * properly.
     */
    public void receivedExperience() {
        alpha = 255;
        showTimer.start(12);
    }

    /**
     * Draws this skill orb
     *
     * @param x
     * @param y
     */
    public void draw(int x, int y) {

        if (alpha < 0) {
            alpha = 0;
        }

        // Draw inner semi-transparent circle
        DrawingArea.drawFilledCircle(x + 28, y + 28, 24, 0x504a41, alpha < 180 ? alpha : 180);
        
        calculateAngleStep();

        DrawingArea.createGraphics(true);
        Shape xpRing = createRing(x, y, 360);
        Shape progressRing = createRing(x, y, angleStepper);

        lastDraw = System.currentTimeMillis();

        drawRing(xpRing, new Color(0, 0, 0, alpha));
        drawRing(progressRing, new Color(255, 200, 0, alpha));

        icon.drawAdvancedSprite(x + 29 - icon.myWidth / 2, 28 - icon.myHeight / 2 + y, alpha);
    }

    /**
     * Create a Ring Shape objet
     *
     * @param x
     * @param y
     * @param angle
     * @return
     */

    public Shape createRing(int x, int y, int angle) {
        Shape sector = DrawingArea.createSector(x + 2, y + 1, 55, angle);
        Shape innerCircle = DrawingArea.createCircle(x + 7, y + 6, 45);

        return DrawingArea.createRing(sector, innerCircle);
    }

    /**
     * Draw the ring of specified colour
     *
     * @param ring
     * @param colour
     */
    public void drawRing(Shape ring, Color colour) {
        SkillOrbs.g2d.setColor(colour);
        SkillOrbs.g2d.fill(ring);
    }

    /**
     * Calculate the angle step, in order to achieve a smooth increase transition on XP gain
     */
    private void calculateAngleStep() {
        progressAngle = (int) ((percentage() / 100.0) * 360);

        if (System.currentTimeMillis() - lastDraw < 600) {
            if (progressChange <= 0) {
                progressChange = progressAngle - angleStepper;
            }
        }

        boolean levelUp = false;
        if (progressAngle < angleStepper) {
            levelUp = true;
        }

        if (progressChange < 360 && ((progressChange > 0 && angleStepper <= progressAngle - 2) || levelUp)) {
            angleStepper += 2;
            progressChange--;
        } else {
            progressChange = 0;
            angleStepper = progressAngle;
        }

        if (angleStepper > 360) {
            angleStepper = progressAngle;
        }
    }

    /**
     * Gets progress bar colour
     *
     * @return
     */
    private Color getColour() {
        return currentLevel() >= 99 ? new Color(255, 165, 0, alpha) : new Color(232, 232, 232, alpha);
    }

    /**
     * Draws a tooltip containing information about
     * this skill orb.
     */
    public void drawTooltip() {

        final int percentProgress = percentage();
        NumberFormat nf = NumberFormat.getInstance(Locale.UK);
        int mouseX = Client.instance.mouseX + 25;
        int mouseY = Client.instance.mouseY + 25;
        
        int width = 152;

		/* Draw box */
        DrawingArea.drawTransparentBox(mouseX + 1, mouseY + 6, width, 72, 0x504a41, 180);
        DrawingArea.drawBoxOutline(mouseX + 1, mouseY + 6, width, 72, 0x383023);

		/* Draw stat information */
        String skillName = Skill.forId(skill).getName();

        String[] labels = new String[]{skillName, "Current XP:", "Xp to level:"};
        String[] info = new String[]{Integer.toString(Client.instance.currentMaxStats[skill]), nf.format(Client.instance.currentExp[skill]), nf.format(remainderExp())};
        int y = 20;

        for (int i = 0; i < 3; i++, y += 17) {
            Client.instance.newRegularFont.drawBasicString(labels[i], mouseX + 5, mouseY + y, i == 0 ? 16777215 : 0xffb000, 1);
            Client.instance.newRegularFont.drawRightAlignedString(info[i], mouseX + width - 4, mouseY + y, 16777215, 1);
        }

		/* Draw progress bar */
        DrawingArea.drawTransparentBox(mouseX + 3, mouseY + 60, width - 4, 14, 0x2c2720, 180);
        DrawingArea.drawBox(mouseX + 3, mouseY + 60, (int) ((percentProgress / 100.0) * (width - 4)), 14, Client.getProgressColor(percentProgress));

        Client.instance.newSmallFont.drawCenteredString(percentProgress + "% ", mouseX + (width - 4) / 2 + 6, mouseY + 72, 0xFFFFFF, 1);
    }

    private int currentLevel() {
        return Client.instance.currentMaxStats[skill];
    }

    private int startExp() {
        return Client.getXPForLevel(currentLevel());
    }

    private int requiredExp() {
        return Client.getXPForLevel(currentLevel() + 1);
    }

    private int obtainedExp() {
        return Client.instance.currentExp[skill] - startExp();
    }

    private int remainderExp() {
        if (currentLevel() < 99) {
            return requiredExp() - (startExp() + obtainedExp());
        } else {
            return 200_000_000 - Client.instance.currentExp[skill];
        }
    }

    private int percentage() {
        // Attempt to calculate percent progress
        int percent = 0;
        try {
            if (currentLevel() < 99) {
                percent = (int) (((double) obtainedExp() / (double) (requiredExp() - startExp())) * 100);
            } else {
                percent = (int) (((double) Client.instance.currentExp[skill] / 200_000_000) * 100);
            }
            // Max percent progress is 100
            if (percent > 100) {
                percent = 100;
            }
        } catch (ArithmeticException e) {
            e.printStackTrace();
        }
        return percent;
    }

    /**
     * Gets the timer
     *
     * @return
     */
    public SecondsTimer getShowTimer() {
        return showTimer;
    }

    /**
     * Gets the skill
     *
     * @return
     */
    public int getSkill() {
        return skill;
    }

    /**
     * Gets the alpha
     *
     * @return
     */
    public int getAlpha() {
        return alpha;
    }

    /**
     * Decrements alpha
     */
    public void decrementAlpha() {
        alpha -= 10;
    }
}
