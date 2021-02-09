package com.zyrox.client;


import com.zyrox.client.cache.node.Node;

final class GameObjectSpawnRequest extends Node {

	GameObjectSpawnRequest()
	{
		removeTime = -1;
	}

	public int currentIDRequested;
	public int currentFaceRequested;
	public int currentTypeRequested;
	public int removeTime;
	public int plane;
	public int objectType;
	public int tileX;
	public int tileY;
	public int objectId;
	public int face;
	public int type;
	public int spawnTime;
}
