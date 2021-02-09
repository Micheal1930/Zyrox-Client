package com.zyrox.client.content.drop_down;

import com.zyrox.client.Client;
import com.zyrox.client.DrawingArea;
import com.zyrox.client.RSInterface;
import com.zyrox.client.Sprite;

public class DropDownMenu extends RSInterface {

    /**
     * The background color of the drop down menu.
     */
    private final int backgroundColor;

    /**
     * The color of the hovered selected item.
     */
    private final int hoverColor;

    /**
     * Array of {@link DropDownAction}s that will be displayed when the Drop
     * Down Menu has been opened.
     */
    private final DropDownAction[] actions;

    /**
     * Determines if the drop down menu has been opened.
     */
    private boolean isOpen;

    /**
     * Determines the amount of columns the content of drop menu should be distributed into.
     */
    private final int columnAmount;

    /**
     * Determines the column width the contents should fit into.
     */
    private final int columnWidth;

    private static final int ARRROW_UP = 1035;

    private static final int ARRROW_DOWN = 1036;

    public DropDownMenu(int identifier, int width, int backgroundColor, int hoverColor, int columnAmount, int columnWidth,
                        DropDownAction[] actions) {
        super(identifier, width, 16, 18, 18);
        this.backgroundColor = backgroundColor;
        this.hoverColor = hoverColor;
        this.actions = actions;
        this.columnAmount = columnAmount;
        this.columnWidth = columnWidth;
        message = actions == null || actions.length < 1 ? "NO ACTION" : actions[0].getMessage();
        disabledSprite = Client.cacheSprite[ARRROW_UP];
        enabledSprite = Client.cacheSprite[ARRROW_DOWN];
    }

    @Override
    public boolean drawInterface(final Client client, int widgetDrawX, int widgetDrawY, int subWidgetDrawX,
                                 int subWidgetDrawY) {

        int drawX = subWidgetDrawX;
        int drawY = subWidgetDrawY;
        int parentDDMWidth = width;
        int parentDDMHeight = 16;
        int childDDMWidth = columnWidth * columnAmount;
        int rowAmount = (int) Math.ceil((float) getActions().length / columnAmount);
        int childDDMHeight = rowAmount * 16 + 2;

        /**
         * Draw rectangle and fill rectangle for the parent DDM.
         */
        DrawingArea.drawPixels2(parentDDMHeight, drawY, drawX, getBackgroundColor(), parentDDMWidth); //Fill Rectangle
        DrawingArea.fillPixels(drawX, parentDDMWidth, parentDDMHeight, 0, drawY); //Rectangle

        /**
         * Draw arrow down/up sprite. Determined by the isOpen field.
         */
        final Sprite draw = isOpen() ? disabledSprite : enabledSprite;
        draw.drawSprite(subWidgetDrawX + width - draw.myWidth, subWidgetDrawY);

        /**
         * Draw the first drop down menu option text in the parent DDM.
         */
        client.newSmallFont.drawCenteredString(message, drawX + (width - 16) / 2, drawY + 13, 0xFF981F, -1, false);

        /**
         * Handle drawing of the child DDM contents etc.
         */
        if (isOpen()) {

            if (Client.openInterfaceID == 28630 && id >= 28670 && id <= 28683) { //Hot keys
                final int tabStoneSprite = (id - 28670) + (id - 28);
                RSInterface.interfaceCache[tabStoneSprite].disabledSprite = Client.cacheSprite[12];
            }

            DrawingArea.drawPixels2(childDDMHeight, drawY + 15, drawX, getBackgroundColor(), childDDMWidth); //Fill Rectangle
            DrawingArea.fillPixels(drawX, childDDMWidth, childDDMHeight, 0, drawY + 15); //Rectangle

            int counter = 0;
            drawY += 15;
            for (DropDownAction action : getActions()) {
                if (action == null) {
                    continue;
                }
                if (action.isHighlighted()) {
                    DrawingArea.drawPixels2(16, subWidgetDrawY + 16 + counter * 16, subWidgetDrawX + 1, getHoverColor(),
                            columnWidth - 2);
                }

                client.newSmallFont.drawCenteredString(action.getMessage(), drawX + columnWidth / 2, drawY + 16 - 2, 0xFF981F, -1, false);

                action.setHighlighted(false);
                drawY += 16;
                counter++;
                if (counter == rowAmount) {
                    drawX += columnWidth;
                    drawY = subWidgetDrawY + 15;
                    subWidgetDrawX += columnWidth;
                    counter = 0;
                }
            }


        }
        return true;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;

        Client.dropdownOpen = isOpen;
    }

    public int getColumnAmount() {
        return columnAmount;
    }

    public int getColumnWidth() {
        return columnWidth;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getHoverColor() {
        return hoverColor;
    }

    public DropDownAction[] getActions() {
        return actions;
    }
}
