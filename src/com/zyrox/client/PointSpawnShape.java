package com.zyrox.client;

import java.util.Random;

import com.zyrox.client.particles.ParticleVector;

public class PointSpawnShape implements SpawnShape {

	private ParticleVector vector;

	public PointSpawnShape(ParticleVector vector) {
		this.vector = vector;
	}

	public final ParticleVector divide(Random random) {
		return vector.clone();
	}
}