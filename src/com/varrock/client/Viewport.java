package com.varrock.client;



public class Viewport
{
    public int[] scanOffsets;
    private int canvasWidth;
    public int width;
    public int height;
    public int centerX;
    public int centerY;
    
    public Viewport(final int x1, final int y1, final int x2, final int y2, final int w) {
        super();
        this.canvasWidth = w;
        this.width = x2 - x1;
        this.height = y2 - y1;
        this.scanOffsets = new int[this.height];
        int offset = x1 + y1 * this.canvasWidth;
        for (int l = 0; l < this.height; ++l) {
            this.scanOffsets[l] = offset;
            offset += this.canvasWidth;
        }
        this.centerX = this.width / 2;
        this.centerY = this.height / 2;
    }
    
    public int getX() {
        return this.scanOffsets[0] % this.canvasWidth;
    }
    
    public int getY() {
        return this.scanOffsets[0] / this.canvasWidth;
    }
}
