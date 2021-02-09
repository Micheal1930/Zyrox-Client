package com.zyrox.client.interfaces;

import com.zyrox.client.RSInterface;

/**
 * Created by Jonny on 10/10/2019
 **/
public class PendingInterfaceModule {

    private int scrollPosition;
    private int childX;
    private RSInterface child;
    private int childY;

    public PendingInterfaceModule(int scrollPosition, int childX, RSInterface child, int childY) {
        this.scrollPosition = scrollPosition;
        this.childX = childX;
        this.child = child;
        this.childY = childY;
    }

    public int getScrollPosition() {
        return scrollPosition;
    }

    public int getChildX() {
        return childX;
    }

    public RSInterface getChild() {
        return child;
    }

    public int getChildY() {
        return childY;
    }
}
