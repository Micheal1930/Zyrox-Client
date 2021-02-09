package com.zyrox.client;


import com.zyrox.client.cache.definitions.Animation;
import com.zyrox.client.cache.definitions.MobDefinition;
import com.zyrox.client.cache.definitions.SpotAnimDefinition;

public final class NPC extends Entity
{
	
	public int index;

	private Model getAnimatedModel()
	{
		
		if(super.anim >= 0 && super.animationDelay == 0)
		{
			Animation animation = Animation.anims[super.anim];
			int currentFrame = animation.frameIDs[super.currentAnimFrame];
			int nextFrame = super.nextAnimationFrame >= animation.frameIDs.length ? currentFrame : animation.frameIDs[super.nextAnimationFrame];
			int cycle1 = animation.delays[super.currentAnimFrame];
			int cycle2 = super.anInt1528;
			//int frame = Animation.anims[super.anim].anIntArray353[super.anInt1527];
			int i1 = -1;
			if(super.entityAnimation >= 0 && super.entityAnimation != super.standAnim)
				i1 = Animation.anims[super.entityAnimation].frameIDs[super.currentForcedAnimFrame];
			return desc.method164(i1, currentFrame, Animation.anims[super.anim].animationFlowControl, nextFrame, cycle1, cycle2);
		}
		
		int currentFrame = -1;
		int nextFrame = -1;
		int cycle1 = 0;
		int cycle2 = 0;
		if(super.entityAnimation >= 0) {
			Animation animation = Animation.anims[super.entityAnimation];
			currentFrame = animation.frameIDs[super.currentForcedAnimFrame];
			nextFrame = animation.frameIDs[super.nextIdleAnimationFrame];
			cycle1 = animation.delays[super.currentForcedAnimFrame];
			cycle2 = super.anInt1519;
		}
		return desc.method164(-1, currentFrame, null, nextFrame, cycle1, cycle2);
	}

	public Model getRotatedModel()
	{
		if(desc == null)
			return null;
		Model model = getAnimatedModel();
		if(model == null)
			return null;
		super.height = model.modelHeight;
		if(super.anInt1520 != -1 && super.currentAnim != -1)
		{
			SpotAnimDefinition spotAnim = SpotAnimDefinition.cache[super.anInt1520];
			Model model_1 = spotAnim.getModel();
			if(model_1 != null)
			{
				int j = spotAnim.animation.frameIDs[super.currentAnim];
				Model model_2 = new Model(true, FrameReader.isNullFrame(j), false, model_1);
				model_2.translate(0, -super.graphicHeight, 0);
				model_2.createBones();
				model_2.applyTransform(j, spotAnim.dataType);
				model_2.triangleSkin = null;
				model_2.vertexSkin = null;
				if(spotAnim.sizeXY != 128 || spotAnim.sizeZ != 128)
					model_2.scaleT(spotAnim.sizeXY, spotAnim.sizeXY, spotAnim.sizeZ);
				model_2.light(64 + spotAnim.shadow, 850 + spotAnim.lightness, -30, -50, -30, true);
				Model aModel[] = {
						model, model_2
				};
				model = new Model(aModel);
			}
		}
		if(desc.squaresNeeded == 1)
			model.rendersWithinOneTile = true;
		return model;
	}

	public boolean isVisible()
	{
		return desc != null;
	}

	NPC()
	{
	}

	public MobDefinition desc;
}
