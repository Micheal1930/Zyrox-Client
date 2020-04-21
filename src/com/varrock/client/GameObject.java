package com.varrock.client;

public class GameObject {

	public GameObject(int id, int x, int y, int z, int face) {
		this(id, x, y, z, face, 10);
	}
	
	public GameObject(int id, int x, int y, int z, int face, int type) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.z = z;
		this.face = face;
		this.type = type;
	}
	
	public int id;
	public int x, y, z;
	public int face;
	public int type;
}
