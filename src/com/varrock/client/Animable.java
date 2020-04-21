package com.varrock.client;
public class Animable extends QueueNode {

	public void renderAtPoint(int i, int j, int k, int l, int i1, int j1,
			int k1, int l1, int i2, int newuid) {
		Model model = getRotatedModel();
		if (model != null) {
			modelHeight = model.modelHeight;
			model.renderAtPoint(i, j, k, l, i1, j1, k1, l1, i2, newuid);
		}
		// System.gc();
	}

	public Model getRotatedModel() {
		return null;
	}

	public Model getRotatedModelHD() {
		return null;
	}

	public Animable() {
		modelHeight = 1000;
	}

	VertexNormal vertexNormals[];
	public int modelHeight;
}