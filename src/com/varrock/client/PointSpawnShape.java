package com.varrock.client;

import java.util.Random;

import com.varrock.client.particles.ParticleVector;

public class PointSpawnShape implements SpawnShape {

	private ParticleVector vector;

	public PointSpawnShape(ParticleVector vector) {
		this.vector = vector;
	}

	public final ParticleVector divide(Random random) {
		return vector.clone();
	}
}