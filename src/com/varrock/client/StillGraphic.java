package com.varrock.client;


import com.varrock.client.cache.definitions.SpotAnimDefinition;

final class StillGraphic extends Animable {

	public StillGraphic(int i, int j, int l, int gfxId, int height, int k1,
						 int l1)
	{
		animFinished = false;
		gfx = SpotAnimDefinition.cache[gfxId];
		plane = i;
		xTile = l1;
		yTile = k1;
		drawHeight = height;
		startTime = j + l;
		animFinished = false;
	}

	public Model getRotatedModel()
	{
		Model model = gfx.getModel();
		if(model == null)
			return null;
		int frameToPlay = gfx.animation.frameIDs[currentFrame];
		Model animableModel = new Model(true, FrameReader.isNullFrame(frameToPlay), false, model);
		if(!animFinished)
		{
			animableModel.createBones();
			animableModel.applyTransform(frameToPlay, gfx.dataType);
			animableModel.triangleSkin = null;
			animableModel.vertexSkin = null;
		}
		if(gfx.sizeXY != 128 || gfx.sizeZ != 128)
			animableModel.scaleT(gfx.sizeXY, gfx.sizeXY, gfx.sizeZ);
		if(gfx.rotation != 0)
		{
			if(gfx.rotation == 90)
				animableModel.rotateBy90();
			if(gfx.rotation == 180)
			{
				animableModel.rotateBy90();
				animableModel.rotateBy90();
			}
			if(gfx.rotation == 270)
			{
				animableModel.rotateBy90();
				animableModel.rotateBy90();
				animableModel.rotateBy90();
			}
		}
		animableModel.light(64 + gfx.shadow, 5050 + gfx.lightness, -90, -580, -90, true);
		return animableModel;
	}

	public void processAnimation(int i)
	{
		for(tick += i; tick > gfx.animation.getFrameLength(currentFrame);)
		{
			tick -= gfx.animation.getFrameLength(currentFrame) + 1;
			currentFrame++;
			if(currentFrame >= gfx.animation.frameCount && (currentFrame < 0 || currentFrame >= gfx.animation.frameCount))
			{
				currentFrame = 0;
				animFinished = true;
			}
		}

	}

	public final int plane;
	public final int xTile;
	public final int yTile;
	public final int drawHeight;
	public final int startTime;
	public boolean animFinished;
	private final SpotAnimDefinition gfx;
	private int currentFrame;
	private int tick;
}
