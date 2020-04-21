package com.varrock.client;
// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferInt;
import java.awt.image.DirectColorModel;
import java.awt.image.Raster;
import java.util.Hashtable;

public final class RSImageProducer
{
	public RSImageProducer(int width, int height, Component component)
	{
		this.width = width;
		this.height = height;
		this.component = component;
		int count = width * height;
		depthbuffer = new float[count];
		pixels = new int[count];
		image = new BufferedImage(COLOR_MODEL, Raster.createWritableRaster(COLOR_MODEL.createCompatibleSampleModel(width, height), new DataBufferInt(pixels, count), null), false, new Hashtable<Object, Object>());
		initDrawingArea();
	}

	public void drawGraphics(int y, Graphics gfx, int x)
	{
		draw(gfx, x, y);
	}

	public void draw(Graphics gfx, int x, int y)
	{
		gfx.drawImage(image, x, y, component);
	}

	public void draw(Graphics gfx, int x, int y, int clipX, int clipY, int clipWidth, int clipHeight)
	{
		Shape tmp = gfx.getClip();
		try
		{
			clip.x = clipX;
			clip.y = clipY;
			clip.width = clipWidth;
			clip.height = clipHeight;
			gfx.setClip(clip);
			gfx.drawImage(image, x, y, component);
		}
		finally
		{
			gfx.setClip(tmp);
		}
	}

	public void initDrawingArea()
	{
		DrawingArea.initDrawingArea(height, width, pixels, depthbuffer);
	}

	private float[] depthbuffer;
	public final int[] pixels;
	public final int width;
	public final int height;
	public final BufferedImage image;
	public final Component component;
	private final Rectangle clip = new Rectangle();
	private static final ColorModel COLOR_MODEL = new DirectColorModel(32, 0xff0000, 0xff00, 0xff);
}
