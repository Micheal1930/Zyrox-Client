package com.varrock.client.interfaces;

import com.varrock.client.cache.definitions.MobDefinition;

/**
 * Created by Jonny on 7/7/2019
 **/
public class NpcDisplayModel {

    private int animationFrame;
    private int standAnimation;
    private int npcId;
    private MobDefinition definition;
    private int frameDelay;
    private int modelZoom;

    public NpcDisplayModel(int npcId) {
        this.npcId = npcId;
        this.definition = MobDefinition.forID(npcId);
        this.standAnimation = definition.standAnim;
    }

    public int getNpcId() {
        return npcId;
    }

    public int getStandAnimation() {
        return standAnimation;
    }

    public int getAnimationFrame() {
        return animationFrame;
    }

    public void incrementAnimationFrame() {
        this.animationFrame++;
    }

    public void setAnimationFrame(int animationFrame) {
        this.animationFrame = animationFrame;
    }

    public void setNpcId(int npcId) {
        this.npcId = npcId;
        this.definition = MobDefinition.forID(npcId);
        this.standAnimation = definition.standAnim;
    }

    public MobDefinition getDefinition() {
        return definition;
    }

    public int getFrameDelay() {
        return frameDelay;
    }

    public void setFrameDelay(int frameDelay) {
        this.frameDelay = frameDelay;
    }

    public int getModelZoom() {
        return modelZoom;
    }

    public void setModelZoom(int modelZoom) {
        this.modelZoom = modelZoom;
    }
}
