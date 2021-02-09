package com.zyrox.client.particles;

import java.util.Random;

import com.zyrox.client.Sprite;

public class ParticleDefinition {

    public static final Random RANDOM = new Random(System.currentTimeMillis());

    private ParticleVector gravity;

    private float startSize = 1f;
    private float endSize = 1f;

    public int particleDepth;

    private int startColor = -1;
    private int endColor = -1;

    private ParticleVector startVelocity = ParticleVector.ZERO;
    private ParticleVector endVelocity = ParticleVector.ZERO;

    private SpawnShape spawnShape = new PointSpawnShape(ParticleVector.ZERO);

    private float startAlpha = 1f;
    private float endAlpha = 0.05f;

    private int lifeSpan = 1;
    private int spawnRate = 1;
    private Sprite sprite;
    private ParticleVector velocityStep;
    private int colorStep;
    private float sizeStep;
    private float alphaStep;

    public static ParticleDefinition[] cache = new ParticleDefinition[]{
            new ParticleDefinition() {
                {
                    //Completionist Cape
                    setStartVelocity(new ParticleVector(0, -2, 0));
                    setEndVelocity(new ParticleVector(0, -2, 0));
                    setGravity(new ParticleVector(0, 2 / 4, 0));
                    setLifeSpan(19);
                    setStartColor(0xFFFFFF);
                    setSpawnRate(4);
                    setStartSize(1.25f);
                    setEndSize(0);
                    setStartAlpha(0.095f);
                    updateSteps();
                    setColorStep(0x000000);
                }
            },
            new ParticleDefinition() {
                {
                    //Trimmed Completionist Cape
                    setStartVelocity(new ParticleVector(0, -2, 0));
                    setEndVelocity(new ParticleVector(0, -2, 0));
                    setGravity(new ParticleVector(0, 2 / 4, 0));
                    setLifeSpan(19);
                    setStartColor(0xFFD900);
                    setSpawnRate(4);
                    setStartSize(1.25f);
                    setEndSize(0);
                    setStartAlpha(0.095f);
                    updateSteps();
                    setColorStep(0x000000);
                }
            },
            new ParticleDefinition() {
                {
                    //Master Dung. Cape
                    setStartVelocity(new ParticleVector(0, -1, 0));
                    setEndVelocity(new ParticleVector(0, -1, 0));
                    setGravity(new ParticleVector(0, 2 / 4, 0));
                    setLifeSpan(19);
                    setStartColor(0x000000);
                    setSpawnRate(4);
                    setStartSize(1.25f);
                    setEndSize(0.11f);
                    setStartAlpha(0.020f);
                    updateSteps();
                    setColorStep(0x000000);
                }
            }, new ParticleDefinition() {
        {
            // Completionist Cape
            setStartVelocity(new ParticleVector(0, -1, 0));
            setEndVelocity(new ParticleVector(0, -1, 0));
            setGravity(new ParticleVector(0, 2 / 4, 0));
            setLifeSpan(19);
            setStartColor(0xFFFFFF);
            setSpawnRate(4);
            setStartSize(1.25f);
            setEndSize(0);
            setStartAlpha(0.095f);
            updateSteps();
            setColorStep(0x000000);
        }
    }, new ParticleDefinition() {
        {
            // Trimmed Completionist Cape
            setStartVelocity(new ParticleVector(0, -1, 0));
            setEndVelocity(new ParticleVector(0, -1, 0));
            setGravity(new ParticleVector(0, 2 / 4, 0));
            setLifeSpan(19);
            setStartColor(0xFFD900);
            setSpawnRate(4);
            setStartSize(1.25f);
            setEndSize(0);
            setStartAlpha(0.095f);
            updateSteps();
            setColorStep(0x000000);
        }
    }, new ParticleDefinition() {
        {
            // Max Cape
            setStartVelocity(new ParticleVector(0, -1, 0));
            setEndVelocity(new ParticleVector(0, -1, 0));
            setGravity(new ParticleVector(0, 2 / 4, 0));
            setLifeSpan(19);
            setStartColor(0xFFFFFF);
            setSpawnRate(4);
            setStartSize(1.15f);
            setEndSize(0);
            setStartAlpha(0.095f);
            updateSteps();
            setColorStep(0x000000);
        }
    }, new ParticleDefinition() {
        { // Master Dungeoneering Cape
            setStartVelocity(new ParticleVector(0, -1, 0));
            setEndVelocity(new ParticleVector(0, -1, 0));
            setGravity(new ParticleVector(0, 2 / 4, 0));
            setLifeSpan(19);
            setStartColor(0x000000);
            setSpawnRate(4);
            setStartSize(1.15f);
            setEndSize(0.05f);
            setStartAlpha(0.095f);
            updateSteps();
            setColorStep(0x000000);
        }
    }, new ParticleDefinition() {
        { // Tok-Haar Kal
            setStartVelocity(new ParticleVector(0, -3, 0)); // x z y
            setEndVelocity(new ParticleVector(0, -3, 0));
            setGravity(new ParticleVector(0, 1 / 2, 0));
            setLifeSpan(19);
            setStartColor(0xB70000);
            setSpawnRate(2);
            setStartSize(1.2f);
            setEndSize(0);
            setStartAlpha(0.095f);
            updateSteps();
            setColorStep(0x000900);
        }
    }, new ParticleDefinition() {
        {
            // Infernal Max Cape
            setStartVelocity(new ParticleVector(0, 3, 0));
            setEndVelocity(new ParticleVector(0, 0, 0));
            setGravity(new ParticleVector(0, 1 / 3, 0));
            setLifeSpan(19);
            setStartColor(0);
            setSpawnRate(5);
            setStartSize(1.15f);
            setEndSize(0);
            setStartAlpha(0.095f);
            updateSteps();
            setColorStep(0xB70000);
        }
    }, new ParticleDefinition() {
        {
			// Sled
            setStartVelocity(new ParticleVector(0, 3, 0));
            setEndVelocity(new ParticleVector(0, -30, 0));
            setGravity(new ParticleVector(0, 0, 0));
            setLifeSpan(28); // 19
            setStartColor(0xFFFFFF);
            setSpawnRate(8);
            setStartSize(1.25f);
            setEndSize(0);
            setStartAlpha(0.095f);
            updateSteps();
            setColorStep(0x000000);
        }
    }, new ParticleDefinition() {
        { // Onyx gs 1
            setStartVelocity(new ParticleVector(0, -3, 0)); // x z y
            setEndVelocity(new ParticleVector(0, -3, 0));
            setGravity(new ParticleVector(0, 1 / 2, 0));
            setLifeSpan(19);
            setStartColor(0xB70000);
            setSpawnRate(2);
            setStartSize(1.2f);
            setEndSize(0);
            setStartAlpha(0.2f);
            updateSteps();
            setColorStep(0x000900);
        }
    }, new ParticleDefinition() {
        { // Onyx gs 2
            setStartVelocity(new ParticleVector(0, -3, 0)); // x z y
            setEndVelocity(new ParticleVector(0, 30, 0));
            setGravity(new ParticleVector(0, 1 / 2, 0));
            setLifeSpan(29);
            setStartColor(0xB70000);
            setSpawnRate(1);
            setStartSize(1.0f);
            setEndSize(0.7f);
            setStartAlpha(0.2f);
            updateSteps();
            setColorStep(0x000900);
        }
    }, new ParticleDefinition() {
        {
			// Dark Sled
            setStartVelocity(new ParticleVector(0, 3, 0));
            setEndVelocity(new ParticleVector(0, -30, 0));
            setGravity(new ParticleVector(0, 0, 0));
            setLifeSpan(28); // 19
            setStartColor(0xB70000);
            setSpawnRate(8);
            setStartSize(1.25f);
            setEndSize(0);
            setStartAlpha(0.095f);
            updateSteps();
            setColorStep(0x000000);
        }        
    },

            new ParticleDefinition() {
                { // red flaming skull
                    setStartVelocity(new ParticleVector(0, 3, 0));
                    setEndVelocity(new ParticleVector(0, 3, 0));
                    setGravity(new ParticleVector(0, 3 / 2, 0));
                    setLifeSpan(15);
                    setStartColor(0xFF0000);
                    setSpawnRate(8);
                    setStartSize(2f);
                    setEndSize(0.5f);
                    setStartAlpha(0f);
                    updateSteps();
                    setColorStep(0xFF0000);
                }
            },
            new ParticleDefinition() {
                { // blue flaming skull
                    setStartVelocity(new ParticleVector(0, 3, 0));
                    setEndVelocity(new ParticleVector(0, 3, 0));
                    setGravity(new ParticleVector(0, 3 / 2, 0));
                    setLifeSpan(15);
                    setStartColor(0x0000FF);
                    setSpawnRate(5);
                    setStartSize(2f);
                    setEndSize(0.5f);
                    setStartAlpha(0f);
                    updateSteps();
                    setColorStep(0x0000FF);
                }
            },
            new ParticleDefinition() {
                { // green flaming skull
                    setStartVelocity(new ParticleVector(0, 3, 0));
                    setEndVelocity(new ParticleVector(0, 3, 0));
                    setGravity(new ParticleVector(0, 3 / 2, 0));
                    setLifeSpan(15);
                    setStartColor(0x00ff00);
                    setSpawnRate(10);
                    setStartSize(2f);
                    setEndSize(0.5f);
                    setStartAlpha(0f);
                    updateSteps();
                    setColorStep(0x00ff00);
                }
            },
            new ParticleDefinition() {
                { // purple flaming skull
                    setStartVelocity(new ParticleVector(0, 3, 0));
                    setEndVelocity(new ParticleVector(0, 3, 0));
                    setGravity(new ParticleVector(0, 3 / 2, 0));
                    setLifeSpan(15);
                    setStartColor(0xf600f6);
                    setSpawnRate(12);
                    setStartSize(2f);
                    setEndSize(0.5f);
                    setStartAlpha(0f);
                    updateSteps();
                    setColorStep(0xf600f6);
                }
            },

    };

    public final SpawnShape getSpawnedShape() {
        return spawnShape;
    }

    public final float getStartAlpha() {
        return startAlpha;
    }

    public final void setStartAlpha(float startAlpha) {
        this.startAlpha = startAlpha;
    }

    public final float getAlphaStep() {
        return alphaStep;
    }

    public final Sprite getSprite() {
        return sprite;
    }

    public final void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public final int getSpawnRate() {
        return this.spawnRate;
    }

    public final void setSpawnRate(int spawnRate) {
        this.spawnRate = spawnRate;
    }

    public final void setStartSize(float startSize) {
        this.startSize = startSize;
    }

    public final float getStartSize() {
        return startSize;
    }

    public float getEndSize() {
        return endSize;
    }

    public int getEndColor() {
        return endColor;
    }

    public final void setEndSize(float endSize) {
        this.endSize = endSize;
    }

    public final int getStartColor() {
        return startColor;
    }

    public final void setStartColor(int startColor) {
        this.startColor = startColor;
    }

    public int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }

    public final ParticleVector getStartVelocity(int id) {
        switch (id) {
            default:
                return new ParticleVector(this.startVelocity.getX() + randomWithRange(-1, 1), this.startVelocity.getY() + randomWithRange(0, 0), this.startVelocity.getZ() + randomWithRange(-1, 1));
        }
    }

    public ParticleVector getGravity() {
        return gravity;
    }

    public void setGravity(ParticleVector gravity) {
        this.gravity = gravity;
    }

    public final void setStartVelocity(ParticleVector startVelocity) {
        this.startVelocity = startVelocity;
    }

    public ParticleVector getEndVelocity() {
        return endVelocity;
    }

    public final void setEndVelocity(ParticleVector endVelocity) {
        this.endVelocity = endVelocity;
    }

    public final int getLifeSpan() {
        return lifeSpan;
    }

    public final void setLifeSpan(int lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public final void setColorStep(int colorStep) {
        this.colorStep = colorStep;
    }

    public final float getSizeStep() {
        return sizeStep;
    }

    public final ParticleVector getVelocityStep() {
        return velocityStep;
    }

    public final int getColorStep() {
        return colorStep;
    }

    public final void updateSteps() {
        this.sizeStep = (endSize - startSize) / (lifeSpan * 1f);
        this.colorStep = (endColor - startColor) / lifeSpan;
        this.velocityStep = endVelocity.subtract(startVelocity).divide(lifeSpan);
        this.alphaStep = (endAlpha - startAlpha) / lifeSpan;
    }
}