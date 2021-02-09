package com.zyrox.client;

import com.zyrox.client.cache.node.Deque;

@SuppressWarnings("all")
final class WorldController {

	public WorldController(int ai[][][]) {
		int height = 104;// was parameter
		int width = 104;// was parameter
		int depth = 4;// was parameter
		interactableObjectCache = new InteractableObject[5000];
		anIntArray486 = new int[10000];
		anIntArray487 = new int[10000];
		zMapSize = depth;
		xMapSize = width;
		yMapSize = height;
		tileArray = new Tile[depth][width][height];
		anIntArrayArrayArray445 = new int[depth][width + 1][height + 1];
		anIntArrayArrayArray440 = ai;
		initToNull();
	}

	public WallObject fetchWallObject(int i, int j, int k) {
		Tile tile = tileArray[i][j][k];
		if (tile == null || tile.wallObject == null)
			return null;
		else
			return tile.wallObject;
	}

	public WallDecoration fetchWallDecoration(int i, int j, int l) {
		Tile tile = tileArray[i][j][l];
		if (tile == null || tile.wallDecoration == null)
			return null;
		else
			return tile.wallDecoration;
	}

	public InteractableObject fetchInteractableObject(int z, int x, int y) {
		Tile tile = tileArray[z][x][y];
		if (tile == null)
			return null;
		for (int l = 0; l < tile.entityCount; l++) {
			InteractableObject interactableObject = tile.interactableObjects[l];
			if (interactableObject.tileLeft == x && interactableObject.tileTop == y) {
				return interactableObject;
			}
		}
		return null;
	}

	public GroundDecoration fetchGroundDecoration(int i, int j, int k) {
		Tile tile = tileArray[i][j][k];
		if (tile == null || tile.groundDecoration == null)
			return null;
		else
			return tile.groundDecoration;
	}
	
	
	public int fetchWallObjectNewUID(int i, int j, int k) {
		Tile tile = tileArray[i][j][k];
		if (tile == null || tile.wallObject == null)
			return 0;
		else
			return tile.wallObject.wallObjUID;
	}

	public int fetchWallDecorationNewUID(int i, int j, int l) {
		Tile tile = tileArray[i][j][l];
		if (tile == null || tile.wallDecoration == null)
			return 0;
		else
			return tile.wallDecoration.wallDecorUID;
	}

	public int fetchObjectMeshNewUID(int z, int x, int y) {
		Tile tile = tileArray[z][x][y];
		if (tile == null)
			return 0;
		for (int l = 0; l < tile.entityCount; l++) {
			InteractableObject interactableObject = tile.interactableObjects[l];
			if (interactableObject.tileLeft == x && interactableObject.tileTop == y) {
				return interactableObject.interactiveObjUID;
			}
		}
		return 0;
	}

	public int fetchGroundDecorationNewUID(int i, int j, int k) {
		Tile tile = tileArray[i][j][k];
		if (tile == null || tile.groundDecoration == null)
			return 0;
		else
			return tile.groundDecoration.groundDecorUID;
	}

	public static void nullLoader() {
		aClass28Array462 = null;
		cullingClusterPointer = null;
		cullingClusters = null;
		tileDeque = null;
		tile_visibility_maps = null;
		tile_visibility_map = null;
	}

	public void initToNull() {
		for (int z = 0; z < zMapSize; z++) {
			for (int x = 0; x < xMapSize; x++) {
				for (int y = 0; y < yMapSize; y++)
					tileArray[z][x][y] = null;

			}

		}
		for (int l = 0; l < amountOfCullingClusters; l++) {
			for (int j1 = 0; j1 < cullingClusterPointer[l]; j1++)
				cullingClusters[l][j1] = null;

			cullingClusterPointer[l] = 0;
		}

		for (int k1 = 0; k1 < amountOfInteractableObjects; k1++)
			interactableObjectCache[k1] = null;

		amountOfInteractableObjects = 0;
		for (int l1 = 0; l1 < aClass28Array462.length; l1++)
			aClass28Array462[l1] = null;

	}

	public void initTiles(int hl) {
		currentHL = hl;
		for (int k = 0; k < xMapSize; k++) {
			for (int l = 0; l < yMapSize; l++)
				if (tileArray[hl][k][l] == null)
					tileArray[hl][k][l] = new Tile(hl, k, l);

		}

	}

	public void applyBridgeMode(int y, int x) {
		Tile tile = tileArray[0][x][y];
		for (int l = 0; l < 3; l++) {
			Tile tile_ = tileArray[l][x][y] = tileArray[l + 1][x][y];
			if (tile_ != null) {
				tile_.tileZ--;
				for (int entityPtr = 0; entityPtr < tile_.entityCount; entityPtr++) {
					InteractableObject iObject = tile_.interactableObjects[entityPtr];
					if ((iObject.uid >> 29 & 3) == 2 && iObject.tileLeft == x && iObject.tileTop == y)
						iObject.zPos--;
				}

			}
		}
		if (tileArray[0][x][y] == null)
			tileArray[0][x][y] = new Tile(0, x, y);
		tileArray[0][x][y].tileBelowThisTile = tile;
		tileArray[3][x][y] = null;
	}

	public static void createCullingCluster(int id, int tileStartX, int worldEndZ, int tileEndX, int tileEndY, int worldStartZ, int tileStartY, int searchMask) {
		CullingCluster cluster = new CullingCluster();
		cluster.tileStartX = tileStartX / 128;
		cluster.tileEndX = tileEndX / 128;
		cluster.tileStartY = tileStartY / 128;
		cluster.tileEndY = tileEndY / 128;
		cluster.searchMask = searchMask;
		cluster.worldStartX = tileStartX;
		cluster.worldEndX = tileEndX;
		cluster.worldStartY = tileStartY;
		cluster.worldEndY = tileEndY;
		cluster.worldStartZ = worldStartZ;
		cluster.worldEndZ = worldEndZ;
		cullingClusters[id][cullingClusterPointer[id]++] = cluster;
	}

	public void setVisiblePlanesFor(int z, int x, int y, int logicHeight) {
		Tile tile = tileArray[z][x][y];
		if (tile != null) {
			tileArray[z][x][y].logicHeight = logicHeight;
		}
	}

	public void addTile(int i, int j, int k, int l, int i1, int overlaytex,
			int underlaytex, int k1, int l1, int i2, int j2, int k2, int l2,
			int i3, int j3, int k3, int l3, int i4, int j4, int k4, int l4,
			boolean tex) {
		if (l == 0) {
			PlainTile class43 = new PlainTile(k2, l2, i3, j3, underlaytex, k4,
					false, tex);

			for (int i5 = i; i5 >= 0; i5--) {
				if (tileArray[i5][j][k] == null) {
					tileArray[i5][j][k] = new Tile(i5, j, k);
				}
			}

			tileArray[i][j][k].plainTile = class43;
			return;
		}

		if (l == 1) {
			PlainTile class43_1 = new PlainTile(k3, l3, i4, j4, overlaytex, l4,
					k1 == l1 && k1 == i2 && k1 == j2, tex);

			for (int j5 = i; j5 >= 0; j5--) {
				if (tileArray[j5][j][k] == null) {
					tileArray[j5][j][k] = new Tile(j5, j, k);
				}
			}

			tileArray[i][j][k].plainTile = class43_1;
			return;
		}

		ShapedTile class40 = new ShapedTile(k, k3, j3, i2, overlaytex, underlaytex,
				i4, i1, k2, k4, i3, j2, l1, k1, l, j4, l3, l2, j, l4, tex);

		for (int k5 = i; k5 >= 0; k5--) {
			if (tileArray[k5][j][k] == null) {
				tileArray[k5][j][k] = new Tile(k5, j, k);
			}
		}

		tileArray[i][j][k].shapedTile = class40;
	}

	public void addGroundDecoration(int plane, int zPos, int yPos, Animable animable, byte byte0, int uid, int xPos, 
			int groundDecorUID) {
		if (animable == null)
			return;
		GroundDecoration decoration = new GroundDecoration();
		decoration.node = animable;
		decoration.groundDecorUID = groundDecorUID;
		decoration.xPos = xPos * 128 + 64;
		decoration.yPos = yPos * 128 + 64;
		decoration.zPos = zPos;
		decoration.uid = uid;
		decoration.objConfig = byte0;
		if (tileArray[plane][xPos][yPos] == null)
			tileArray[plane][xPos][yPos] = new Tile(plane, xPos, yPos);
		tileArray[plane][xPos][yPos].groundDecoration = decoration;
	}

	public void addGroundItemTile(int xPos, int uid, Animable secondItem, int zPos, Animable thirdItem, 
			Animable firstItem, int plane, int yPos) {
		GroundItem groundItem = new GroundItem();
		groundItem.firstGroundItem = firstItem;
		groundItem.xPos = xPos * 128 + 64;
		groundItem.yPos = yPos * 128 + 64;
		groundItem.zPos = zPos;
		groundItem.uid = uid;
		groundItem.secondGroundItem = secondItem;
		groundItem.thirdGroundItem = thirdItem;
		int isHighestPriority = 0;
		Tile tile = tileArray[plane][xPos][yPos];
		if (tile != null) {
			for (int k1 = 0; k1 < tile.entityCount; k1++)
				if (tile.interactableObjects[k1].node instanceof Model) {
					int tempInt = ((Model) tile.interactableObjects[k1].node).myPriority;
					if (tempInt > isHighestPriority)
						isHighestPriority = tempInt;
				}

		}
		groundItem.topItem = isHighestPriority;
		if (tileArray[plane][xPos][yPos] == null)
			tileArray[plane][xPos][yPos] = new Tile(plane, xPos, yPos);
		tileArray[plane][xPos][yPos].groundItem = groundItem;
	}

	public void addWallObject(int orientation, Animable node, int uid, int yPos, byte objConfig, int xPos, Animable node2, 
			int zPos, int orientation_2, int plane, int wallObjUID) {
		if (node == null && node2 == null)
			return;
		WallObject wallObject = new WallObject();
		wallObject.uid = uid;
		wallObject.objConfig = objConfig;
		wallObject.xPos = xPos * 128 + 64;
		wallObject.yPos = yPos * 128 + 64;
		wallObject.zPos = zPos;
		wallObject.node1 = node;
		wallObject.node2 = node2;
		wallObject.wallObjUID = wallObjUID;
		wallObject.orientation = orientation;
		wallObject.orientation1 = orientation_2;
		for (int zPtr = plane; zPtr >= 0; zPtr--)
			if (tileArray[zPtr][xPos][yPos] == null)
				tileArray[zPtr][xPos][yPos] = new Tile(zPtr, xPos, yPos);

		tileArray[plane][xPos][yPos].wallObject = wallObject;
	}

	public void addWallDecoration(int uid, int yPos, int rotation, int plane, int xOff, int zPos, 
			Animable node, int xPos, byte config, int yOff, int configBits, int wallDecorUID) {
		if (node == null)
			return;
		WallDecoration dec = new WallDecoration();
		dec.uid = uid;
		dec.objConfig = config;
		dec.xPos = xPos * 128 + 64 + xOff;
		dec.yPos = yPos * 128 + 64 + yOff;
		dec.zPos = zPos;
		dec.node = node;
		dec.wallDecorUID = wallDecorUID;
		dec.configurationBits = configBits;
		dec.rotation = rotation;
		for (int zPtr = plane; zPtr >= 0; zPtr--)
			if (tileArray[zPtr][xPos][yPos] == null)
				tileArray[zPtr][xPos][yPos] = new Tile(zPtr, xPos, yPos);

		tileArray[plane][xPos][yPos].wallDecoration = dec;
	}

	public boolean addInteractableEntity(int ui, byte config, int worldZ, int tileBottom, Animable node, int tileRight, int z, 
			int rotation, int tileTop, int tileLeft, int interactiveUID) {
		if (node == null) {
			return true;
		} else {
			int worldX = tileLeft * 128 + 64 * tileRight;
			int worldY = tileTop * 128 + 64 * tileBottom;
			return addEntity(z, tileLeft, tileTop, tileRight, tileBottom, 
					worldX, worldY, worldZ, node, rotation, false, ui, config, interactiveUID);
		}
	}

	public boolean addMutipleTileEntity(int z, int rotation, int worldZ, int ui, int worldY, int j1, int worldX, 
			Animable nodeToAdd, boolean flag) {
		if (nodeToAdd == null)
			return true;
		int tileLeft = worldX - j1;
		int tileTop = worldY - j1;
		int tileRight = worldX + j1;
		int tileBottom = worldY + j1;
		if (flag) {
			if (rotation > 640 && rotation < 1408)
				tileBottom += 128;
			if (rotation > 1152 && rotation < 1920)
				tileRight += 128;
			if (rotation > 1664 || rotation < 384)
				tileTop -= 128;
			if (rotation > 128 && rotation < 896)
				tileLeft -= 128;
		}
		tileLeft /= 128;
		tileTop /= 128;
		tileRight /= 128;
		tileBottom /= 128;
		return addEntity(z, tileLeft, tileTop, (tileRight - tileLeft) + 1, (tileBottom - tileTop) + 1, 
				worldX, worldY, worldZ, nodeToAdd, rotation, true, ui, (byte) 0, 0);
	}

	public boolean addSingleTileEntity(int z, int worldY, Animable node, int rotation, int tileBottom, int worldX, int worldZ, int tileLeft, int tileRight, int ui, int tileTop) {
		return node == null || addEntity(z, tileLeft, tileTop, (tileRight - tileLeft) + 1, (tileBottom - tileTop) + 1, 
				worldX, worldY, worldZ, node, rotation, true, ui, (byte) 0, 0);
	}

	private boolean addEntity(int z, int tileLeft, int tileTop, int tileRight, int tileBottom, int worldX, int worldY, 
			int worldZ, Animable node, int rotation, boolean flag, int ui, byte objConf, int interactiveObjUID) {
		/**
		 * Max entities on coord is 5 i guess
		 */
		for (int _x = tileLeft; _x < tileLeft + tileRight; _x++) {
			for (int _y = tileTop; _y < tileTop + tileBottom; _y++) {
				if (_x < 0 || _y < 0 || _x >= xMapSize || _y >= yMapSize)
					return false;
				Tile tile = tileArray[z][_x][_y];
				if (tile != null && tile.entityCount >= 5)
					return false;
			}

		}

		InteractableObject io = new InteractableObject();
		io.uid = ui;
		io.objConf = objConf;
		io.zPos = z;
		io.worldX = worldX;
		io.worldY = worldY;
		io.interactiveObjUID = interactiveObjUID;
		io.worldZ = worldZ;
		io.node = node;
		io.rotation = rotation;
		io.tileLeft = tileLeft;
		io.tileTop = tileTop;
		io.tileRight = (tileLeft + tileRight) - 1;
		io.tileBottom = (tileTop + tileBottom) - 1;
		for (int x = tileLeft; x < tileLeft + tileRight; x++) {
			for (int y = tileTop; y < tileTop + tileBottom; y++) {
				int position = 0;
				if (x > tileLeft)
					position++;
				if (x < (tileLeft + tileRight) - 1)
					position += 4;
				if (y > tileTop)
					position += 8;
				if (y < (tileTop + tileBottom) - 1)
					position += 2;
				for (int zPtr = z; zPtr >= 0; zPtr--)
					if (tileArray[zPtr][x][y] == null)
						tileArray[zPtr][x][y] = new Tile(zPtr, x, y);

				Tile tile = tileArray[z][x][y];
				tile.interactableObjects[tile.entityCount] = io;
				tile.anIntArray1319[tile.entityCount] = position;
				tile.anInt1320 |= position;
				tile.entityCount++;
			}

		}

		if (flag)
			interactableObjectCache[amountOfInteractableObjects++] = io;
		return true;
	}

	public void clearInteractableObjects() {
		for (int i = 0; i < amountOfInteractableObjects; i++) {
			InteractableObject iObject = interactableObjectCache[i];
			updateObjectEntities(iObject);
			interactableObjectCache[i] = null;
		}

		amountOfInteractableObjects = 0;
	}

	private void updateObjectEntities(InteractableObject iObject) {
		for (int j = iObject.tileLeft; j <= iObject.tileRight; j++) {
			for (int k = iObject.tileTop; k <= iObject.tileBottom; k++) {
				Tile tile = tileArray[iObject.zPos][j][k];
				if (tile != null) {
					for (int l = 0; l < tile.entityCount; l++) {
						if (tile.interactableObjects[l] != iObject)
							continue;
						tile.entityCount--;
						for (int entityPtr = l; entityPtr < tile.entityCount; entityPtr++) {
							tile.interactableObjects[entityPtr] = tile.interactableObjects[entityPtr + 1];
							tile.anIntArray1319[entityPtr] = tile.anIntArray1319[entityPtr + 1];
						}

						tile.interactableObjects[tile.entityCount] = null;
						break;
					}

					tile.anInt1320 = 0;
					for (int j1 = 0; j1 < tile.entityCount; j1++)
						tile.anInt1320 |= tile.anIntArray1319[j1];

				}
			}

		}

	}

	public void moveWallDec(int y, int moveAmt, int x, int z) {
		Tile tile = tileArray[z][x][y];
		if (tile == null)
			return;
		WallDecoration wallDec = tile.wallDecoration;
		if (wallDec != null) {
			int xCoord = x * 128 + 64;
			int yCoord = y * 128 + 64;
			wallDec.xPos = xCoord + ((wallDec.xPos - xCoord) * moveAmt) / 16;
			wallDec.yPos = yCoord + ((wallDec.yPos - yCoord) * moveAmt) / 16;
		}
	}

	public void removeWallObject(int x, int y, int z) {
		Tile tile = tileArray[y][x][z];
		if (tile != null) {
			tile.wallObject = null;
		}
	}

	public void removeWallDecoration(int y, int z, int x) {
		Tile tile = tileArray[z][x][y];
		if (tile != null) {
			tile.wallDecoration = null;
		}
	}

	public void removeInteractableObject(int z, int x, int y) {
		Tile tile = tileArray[z][x][y];
		if (tile == null)
			return;
		for (int i = 0; i < tile.entityCount; i++) {
			InteractableObject subObject = tile.interactableObjects[i];
			if ((subObject.uid >> 29 & 3) == 2 && subObject.tileLeft == x && subObject.tileTop == y) {
				updateObjectEntities(subObject);
				return;
			}
		}

	}

	public void removeGroundDecoration(int z, int y, int x) {
		Tile tile = tileArray[z][x][y];
		if (tile == null)
			return;
		tile.groundDecoration = null;
	}

	public void removeGroundItemFromTIle(int z, int x, int y) {
		Tile tile = tileArray[z][x][y];
		if (tile != null) {
			tile.groundItem = null;
		}
	}

	public WallObject getWallObject(int z, int x, int y) {
		Tile tile = tileArray[z][x][y];
		if (tile == null)
			return null;
		else
			return tile.wallObject;
	}

	public WallDecoration getWallDecoration(int x, int y, int z) {
		Tile tile = tileArray[z][x][y];
		if (tile == null)
			return null;
		else
			return tile.wallDecoration;
	}

	public InteractableObject getInteractableObject(int x, int y, int z) {
		Tile tile = tileArray[z][x][y];
		if (tile == null)
			return null;
		for (int l = 0; l < tile.entityCount; l++) {
			InteractableObject subObject = tile.interactableObjects[l];
			if ((subObject.uid >> 29 & 3) == 2 && subObject.tileLeft == x && subObject.tileTop == y)
				return subObject;
		}
		return null;
	}

	public GroundDecoration getGroundDecoration(int y, int x, int z) {
		Tile tile = tileArray[z][x][y];
		if (tile == null || tile.groundDecoration == null)
			return null;
		else
			return tile.groundDecoration;
	}

	public int getWallObjectUID(int z, int x, int y) {
		Tile tile = tileArray[z][x][y];
		if (tile == null || tile.wallObject == null)
			return 0;
		else
			return tile.wallObject.uid;
	}

	public int getWallDecorationUID(int z, int x, int y) {
		Tile tile = tileArray[z][x][y];
		if (tile == null || tile.wallDecoration == null)
			return 0;
		else
			return tile.wallDecoration.uid;
	}

	public int getInteractableObjectUID(int plane, int x, int y) {
		Tile tile = tileArray[plane][x][y];
		if (tile == null)
			return 0;
		for (int i = 0; i < tile.entityCount; i++) {
			InteractableObject iObject = tile.interactableObjects[i];
			if (iObject.tileLeft == x && iObject.tileTop == y)
				return iObject.uid;
		}

		return 0;
	}

	public int getGroundDecorationUID(int z, int x, int y) {
		Tile tile = tileArray[z][x][y];
		if (tile == null || tile.groundDecoration == null)
			return 0;
		else
			return tile.groundDecoration.uid;
	}

	public int getIDTagForXYZ(int z, int x, int y, int uidMatch) {
		Tile tile = tileArray[z][x][y];
		if (tile == null)
			return -1;
		if (tile.wallObject != null && tile.wallObject.uid == uidMatch)
			return tile.wallObject.objConfig & 0xff;
		if (tile.wallDecoration != null && tile.wallDecoration.uid == uidMatch)
			return tile.wallDecoration.objConfig & 0xff;
		if (tile.groundDecoration != null && tile.groundDecoration.uid == uidMatch)
			return tile.groundDecoration.objConfig & 0xff;
		for (int entityPtr = 0; entityPtr < tile.entityCount; entityPtr++)
			if (tile.interactableObjects[entityPtr].uid == uidMatch)
				return tile.interactableObjects[entityPtr].objConf & 0xff;

		return -1;
	}

	public void shadeModels(int i, int k, int i1) {
		int j = 100;
		int l = 5500;
		int j1 = (int) Math.sqrt(k * k + i * i + i1 * i1);
		int k1 = l >> 4;
		for (int l1 = 0; l1 < zMapSize; l1++) {
			for (int i2 = 0; i2 < xMapSize; i2++) {
				for (int j2 = 0; j2 < yMapSize; j2++) {
					Tile class30_sub3 = tileArray[l1][i2][j2];
					if (class30_sub3 != null) {
						WallObject class10 = class30_sub3.wallObject;
						if (class10 != null && class10.node1 != null && class10.node1.vertexNormals != null) {
							mergeModels(l1, 1, 1, i2, j2, (Model) class10.node1);
							if (class10.node2 != null && class10.node2.vertexNormals != null) {
								mergeModels(l1, 1, 1, i2, j2, (Model) class10.node2);
								renderModels((Model) class10.node1, (Model) class10.node2, 0, 0, 0, false);
								((Model) class10.node2).method480(j, k1, k, i, i1);
							}
							((Model) class10.node1).method480(j, k1, k, i, i1);
						}
						for (int k2 = 0; k2 < class30_sub3.entityCount; k2++) {
							InteractableObject class28 = class30_sub3.interactableObjects[k2];
							if (class28 != null && class28.node != null && class28.node.vertexNormals != null) {
								mergeModels(l1, (class28.tileRight - class28.tileLeft) + 1, (class28.tileBottom - class28.tileTop) + 1, i2, j2, (Model) class28.node);
								((Model) class28.node).method480(j, k1, k, i, i1);
							}
						}

						GroundDecoration class49 = class30_sub3.groundDecoration;
						if (class49 != null && class49.node.vertexNormals != null) {
							renderGrounDec(i2, l1, (Model) class49.node, j2);
							((Model) class49.node).method480(j, k1, k, i, i1);
						}
					}
				}

			}

		}

	}

	private void renderGrounDec(int i, int j, Model model, int k) {
		if (i < xMapSize) {
			Tile class30_sub3 = tileArray[j][i + 1][k];
			if (class30_sub3 != null && class30_sub3.groundDecoration != null && class30_sub3.groundDecoration.node.vertexNormals != null)
				renderModels(model, (Model) class30_sub3.groundDecoration.node, 128, 0, 0, true);
		}
		if (k < xMapSize) {
			Tile class30_sub3_1 = tileArray[j][i][k + 1];
			if (class30_sub3_1 != null && class30_sub3_1.groundDecoration != null && class30_sub3_1.groundDecoration.node.vertexNormals != null)
				renderModels(model, (Model) class30_sub3_1.groundDecoration.node, 0, 0, 128, true);
		}
		if (i < xMapSize && k < yMapSize) {
			Tile class30_sub3_2 = tileArray[j][i + 1][k + 1];
			if (class30_sub3_2 != null && class30_sub3_2.groundDecoration != null && class30_sub3_2.groundDecoration.node.vertexNormals != null)
				renderModels(model, (Model) class30_sub3_2.groundDecoration.node, 128, 0, 128, true);
		}
		if (i < xMapSize && k > 0) {
			Tile class30_sub3_3 = tileArray[j][i + 1][k - 1];
			if (class30_sub3_3 != null && class30_sub3_3.groundDecoration != null && class30_sub3_3.groundDecoration.node.vertexNormals != null)
				renderModels(model, (Model) class30_sub3_3.groundDecoration.node, 128, 0, -128, true);
		}
	}

	private void mergeModels(int z, int j, int k, int x, int y, Model model) {
		boolean flag = true;
		int j1 = x;
		int k1 = x + j;
		int l1 = y - 1;
		int i2 = y + k;
		for (int j2 = z; j2 <= z + 1; j2++)
			if (j2 != zMapSize) {
				for (int k2 = j1; k2 <= k1; k2++)
					if (k2 >= 0 && k2 < xMapSize) {
						for (int l2 = l1; l2 <= i2; l2++)
							if (l2 >= 0 && l2 < yMapSize && (!flag || k2 >= k1 || l2 >= i2 || l2 < y && k2 != x)) {
								Tile class30_sub3 = tileArray[j2][k2][l2];
								if (class30_sub3 != null) {
									int i3 = (anIntArrayArrayArray440[j2][k2][l2] + anIntArrayArrayArray440[j2][k2 + 1][l2] + anIntArrayArrayArray440[j2][k2][l2 + 1] + anIntArrayArrayArray440[j2][k2 + 1][l2 + 1]) / 4 - (anIntArrayArrayArray440[z][x][y] + anIntArrayArrayArray440[z][x + 1][y] + anIntArrayArrayArray440[z][x][y + 1] + anIntArrayArrayArray440[z][x + 1][y + 1]) / 4;
									WallObject class10 = class30_sub3.wallObject;
									if (class10 != null && class10.node1 != null && class10.node1.vertexNormals != null)
										renderModels(model, (Model) class10.node1, (k2 - x) * 128 + (1 - j) * 64, i3, (l2 - y) * 128 + (1 - k) * 64, flag);
									if (class10 != null && class10.node2 != null && class10.node2.vertexNormals != null)
										renderModels(model, (Model) class10.node2, (k2 - x) * 128 + (1 - j) * 64, i3, (l2 - y) * 128 + (1 - k) * 64, flag);
									for (int j3 = 0; j3 < class30_sub3.entityCount; j3++) {
										InteractableObject class28 = class30_sub3.interactableObjects[j3];
										if (class28 != null && class28.node != null && class28.node.vertexNormals != null) {
											int k3 = (class28.tileRight - class28.tileLeft) + 1;
											int l3 = (class28.tileBottom - class28.tileTop) + 1;
											renderModels(model, (Model) class28.node, (class28.tileLeft - x) * 128 + (k3 - j) * 64, i3, (class28.tileTop - y) * 128 + (l3 - k) * 64, flag);
										}
									}

								}
							}

					}

				j1--;
				flag = false;
			}

	}

	private void renderModels(Model model, Model model_1, int i, int j, int k, boolean flag) {
		anInt488++;
		int l = 0;
		int ai[] = model_1.verticesXCoordinate;
		int amtOfVertices = model_1.numberOfVerticeCoordinates;
		for (int verticeId = 0; verticeId < model.numberOfVerticeCoordinates; verticeId++) {
			VertexNormal vertexNormal = model.vertexNormals[verticeId];
			VertexNormal vertexNormalOff = model.vertexNormalOffset[verticeId];
			if (vertexNormalOff.anInt605 != 0) {
				int vertY = model.verticesYCoordinate[verticeId] - j;
				if (vertY <= model_1.anInt1651) {
					int vertX = model.verticesXCoordinate[verticeId] - i;
					if (vertX >= model_1.anInt1646 && vertX <= model_1.anInt1647) {
						int vertZ = model.verticesZCoordinate[verticeId] - k;
						if (vertZ >= model_1.anInt1649 && vertZ <= model_1.anInt1648) {
							for (int vertId_1 = 0; vertId_1 < amtOfVertices; vertId_1++) {
								VertexNormal class33_2 = model_1.vertexNormals[vertId_1];
								VertexNormal class33_3 = model_1.vertexNormalOffset[vertId_1];
								if (vertX == ai[vertId_1] && vertZ == model_1.verticesZCoordinate[vertId_1] && vertY == model_1.verticesYCoordinate[vertId_1] && class33_3.anInt605 != 0) {
									vertexNormal.anInt602 += class33_3.anInt602;
									vertexNormal.anInt603 += class33_3.anInt603;
									vertexNormal.anInt604 += class33_3.anInt604;
									vertexNormal.anInt605 += class33_3.anInt605;
									class33_2.anInt602 += vertexNormalOff.anInt602;
									class33_2.anInt603 += vertexNormalOff.anInt603;
									class33_2.anInt604 += vertexNormalOff.anInt604;
									class33_2.anInt605 += vertexNormalOff.anInt605;
									l++;
									anIntArray486[verticeId] = anInt488;
									anIntArray487[vertId_1] = anInt488;
								}
							}

						}
					}
				}
			}
		}

		if (l < 3 || !flag)
			return;
		for (int k1 = 0; k1 < model.numberOfTriangleFaces; k1++)
			if (anIntArray486[model.face_a[k1]] == anInt488 && anIntArray486[model.face_b[k1]] == anInt488 && anIntArray486[model.face_c[k1]] == anInt488)
				model.face_render_type[k1] = -1;

		for (int l1 = 0; l1 < model_1.numberOfTriangleFaces; l1++)
			if (anIntArray487[model_1.face_a[l1]] == anInt488 && anIntArray487[model_1.face_b[l1]] == anInt488 && anIntArray487[model_1.face_c[l1]] == anInt488)
				model_1.face_render_type[l1] = -1;

	}

	private boolean hdMinimap = true;
	
	public void method309(int pixels[], int pixelOffset, int z, int x, int y) {
		if (hdMinimap) {
			Tile class30_sub3 = tileArray[z][x][y];
		    if(class30_sub3 == null) {
		        return;
		    }
		    PlainTile class43 = class30_sub3.plainTile;
		    if(class43 != null) {
		        if (class43.anInt716 != 12345678) {
		            if (class43.anInt722 == 0) {
		                return;
		            }
		            int hs = class43.anInt716 & ~0x7f;
		            int l1 = class43.anInt719 & 0x7f;
		            int l2 = class43.anInt718 & 0x7f;
		            int l3 = (class43.anInt716 & 0x7f) - l1;
		            int l4 = (class43.anInt717 & 0x7f) - l2;
		            l1 <<= 2;
		            l2 <<= 2;
		            for(int k1 = 0; k1 < 4; k1++) {
		                if (!class43.textured) {
		                    pixels[pixelOffset] = Rasterizer.anIntArray1482[hs | (l1 >> 2)];
		                    pixels[pixelOffset + 1] = Rasterizer.anIntArray1482[hs | (l1 * 3 + l2 >> 4)];
		                    pixels[pixelOffset + 2] = Rasterizer.anIntArray1482[hs | (l1 + l2 >> 3)];
		                    pixels[pixelOffset + 3] = Rasterizer.anIntArray1482[hs | (l1 + l2 * 3 >> 4)];
		                } else {
		                    int j1 = class43.anInt722;
		                    int lig = 0xff - ((l1 >> 1) * (l1 >> 1) >> 8);
		                    pixels[pixelOffset] = ((j1 & 0xff00ff) * lig & ~0xff00ff) + ((j1 & 0xff00) * lig & 0xff0000) >> 8;
		                    lig = 0xff - ((l1 * 3 + l2 >> 3) * (l1 * 3 + l2 >> 3) >> 8);
		                    pixels[pixelOffset + 1] = ((j1 & 0xff00ff) * lig & ~0xff00ff) + ((j1 & 0xff00) * lig & 0xff0000) >> 8;
		                    lig = 0xff - ((l1 + l2 >> 2) * (l1 + l2 >> 2) >> 8);
		                    pixels[pixelOffset + 2] = ((j1 & 0xff00ff) * lig & ~0xff00ff) + ((j1 & 0xff00) * lig & 0xff0000) >> 8;
		                    lig = 0xff - ((l1 + l2 * 3 >> 3) * (l1 + l2 * 3 >> 3) >> 8);
		                    pixels[pixelOffset + 3] = ((j1 & 0xff00ff) * lig & ~0xff00ff) + ((j1 & 0xff00) * lig & 0xff0000) >> 8;
		                }
		                l1 += l3;
		                l2 += l4;
		                pixelOffset += 512;
		            }
		            return;
		        }
		        int mapColor = class43.anInt722;
		        if(mapColor == 0) {
		            return;
		        }
		        for(int k1 = 0; k1 < 4; k1++) {
		            pixels[pixelOffset] = mapColor;
		            pixels[pixelOffset + 1] = mapColor;
		            pixels[pixelOffset + 2] = mapColor;
		            pixels[pixelOffset + 3] = mapColor;
		            pixelOffset += 512;
		        }
		        return;
		    }
		    ShapedTile class40 = class30_sub3.shapedTile;
		    if(class40 == null) {
		        return;
		    }
		    int l1 = class40.anInt684;
		    int i2 = class40.anInt685;
		    int j2 = class40.anInt686;
		    int k2 = class40.anInt687;
		    int ai1[] = tileShapePoints[l1];
		    int ai2[] = tileShapeIndices[i2];
		    int l2 = 0;
		    if (class40.color62 != 12345678) {
		        int hs1 = class40.color62 & ~0x7f;
		        int l11 = class40.color92 & 0x7f;
		        int l21 = class40.color82 & 0x7f;
		        int l31 = (class40.color62 & 0x7f) - l11;
		        int l41 = (class40.color72 & 0x7f) - l21;
		        l11 <<= 2;
		        l21 <<= 2;
		        for(int k1 = 0; k1 < 4; k1++) {
		            if (!class40.textured) {
		                if(ai1[ai2[l2++]] != 0) {
		                    pixels[pixelOffset] = Rasterizer.anIntArray1482[hs1 | (l11 >> 2)];
		                }
		                if(ai1[ai2[l2++]] != 0) {
		                    pixels[pixelOffset + 1] = Rasterizer.anIntArray1482[hs1 | (l11 * 3 + l21 >> 4)];
		                }
		                if(ai1[ai2[l2++]] != 0) {
		                    pixels[pixelOffset + 2] = Rasterizer.anIntArray1482[hs1 | (l11 + l21 >> 3)];
		                }
		                if(ai1[ai2[l2++]] != 0) {
		                    pixels[pixelOffset + 3] = Rasterizer.anIntArray1482[hs1 | (l11 + l21 * 3 >> 4)];
		                }
		            } else {
		                int j1 = k2;
		                if(ai1[ai2[l2++]] != 0) {
		                    int lig = 0xff - ((l11 >> 1) * (l11 >> 1) >> 8);
		                    pixels[pixelOffset] = ((j1 & 0xff00ff) * lig & ~0xff00ff) + ((j1 & 0xff00) * lig & 0xff0000) >> 8;
		                }
		                if(ai1[ai2[l2++]] != 0) {
		                    int lig = 0xff - ((l11 * 3 + l21 >> 3) * (l11 * 3 + l21 >> 3) >> 8);
		                    pixels[pixelOffset + 1] = ((j1 & 0xff00ff) * lig & ~0xff00ff) + ((j1 & 0xff00) * lig & 0xff0000) >> 8;
		                }
		                if(ai1[ai2[l2++]] != 0) {
		                    int lig = 0xff - ((l11 + l21 >> 2) * (l11 + l21 >> 2) >> 8);
		                    pixels[pixelOffset + 2] = ((j1 & 0xff00ff) * lig & ~0xff00ff) + ((j1 & 0xff00) * lig & 0xff0000) >> 8;
		                }
		                if(ai1[ai2[l2++]] != 0) {
		                    int lig = 0xff - ((l11 + l21 * 3 >> 3) * (l11 + l21 * 3 >> 3) >> 8);
		                    pixels[pixelOffset + 3] = ((j1 & 0xff00ff) * lig & ~0xff00ff) + ((j1 & 0xff00) * lig & 0xff0000) >> 8;
		                }
		            }
		            l11 += l31;
		            l21 += l41;
		            pixelOffset += 512;
		        }
		        if (j2 != 0 && class40.color61 != 12345678) {
		            pixelOffset -= 512 << 2;
		            l2 -= 16;
		            hs1 = class40.color61 & ~0x7f;
		            l11 = class40.color91 & 0x7f;
		            l21 = class40.color81 & 0x7f;
		            l31 = (class40.color61 & 0x7f) - l11;
		            l41 = (class40.color71 & 0x7f) - l21;
		            l11 <<= 2;
		            l21 <<= 2;
		            for(int k1 = 0; k1 < 4; k1++) {
		                if(ai1[ai2[l2++]] == 0) {
		                    pixels[pixelOffset] = Rasterizer.anIntArray1482[hs1 | (l11 >> 2)];
		                }
		                if(ai1[ai2[l2++]] == 0) {
		                    pixels[pixelOffset + 1] = Rasterizer.anIntArray1482[hs1 | (l11 * 3 + l21 >> 4)];
		                }
		                if(ai1[ai2[l2++]] == 0) {
		                    pixels[pixelOffset + 2] = Rasterizer.anIntArray1482[hs1 | (l11 + l21 >> 3)];
		                }
		                if(ai1[ai2[l2++]] == 0) {
		                    pixels[pixelOffset + 3] = Rasterizer.anIntArray1482[hs1 | (l11 + l21 * 3 >> 4)];
		                }
		                l11 += l31;
		                l21 += l41;
		                pixelOffset += 512;
		            }
		        }
		        return;
		    }
		    if(j2 != 0) {
		        for(int i3 = 0; i3 < 4; i3++) {
		            pixels[pixelOffset] = ai1[ai2[l2++]] != 0 ? k2 : j2;
		            pixels[pixelOffset + 1] = ai1[ai2[l2++]] != 0 ? k2 : j2;
		            pixels[pixelOffset + 2] = ai1[ai2[l2++]] != 0 ? k2 : j2;
		            pixels[pixelOffset + 3] = ai1[ai2[l2++]] != 0 ? k2 : j2;
		            pixelOffset += 512;
		        }
		        return;
		    }
		    for(int j3 = 0; j3 < 4; j3++) {
		        if(ai1[ai2[l2++]] != 0) {
		            pixels[pixelOffset] = k2;
		        }
		        if(ai1[ai2[l2++]] != 0) {
		            pixels[pixelOffset + 1] = k2;
		        }
		        if(ai1[ai2[l2++]] != 0) {
		            pixels[pixelOffset + 2] = k2;
		        }
		        if(ai1[ai2[l2++]] != 0) {
		            pixels[pixelOffset + 3] = k2;
		        }
		        pixelOffset += 512;
		    }
		} else {
			int j = 512;//was parameter
			Tile class30_sub3 = tileArray[z][x][y];
			if(class30_sub3 == null)
				return;
			PlainTile class43 = class30_sub3.plainTile;
			if(class43 != null)
			{
				int j1 = class43.anInt722;
				if(j1 == 0)
					return;
				for(int k1 = 0; k1 < 4; k1++)
				{
					pixels[pixelOffset] = j1;
					pixels[pixelOffset + 1] = j1;
					pixels[pixelOffset + 2] = j1;
					pixels[pixelOffset + 3] = j1;
					pixelOffset += j;
				}

				return;
			}
			ShapedTile class40 = class30_sub3.shapedTile;
			if(class40 == null)
				return;
			int l1 = class40.anInt684;
			int i2 = class40.anInt685;
			int j2 = class40.anInt686;
			int k2 = class40.anInt687;
			int ai1[] = tileShapePoints[l1];
			int ai2[] = tileShapeIndices[i2];
			int l2 = 0;
			if(j2 != 0)
			{
				for(int i3 = 0; i3 < 4; i3++)
				{
					pixels[pixelOffset] = ai1[ai2[l2++]] != 0 ? k2 : j2;
					pixels[pixelOffset + 1] = ai1[ai2[l2++]] != 0 ? k2 : j2;
					pixels[pixelOffset + 2] = ai1[ai2[l2++]] != 0 ? k2 : j2;
					pixels[pixelOffset + 3] = ai1[ai2[l2++]] != 0 ? k2 : j2;
					pixelOffset += j;
				}

				return;
			}
			for(int j3 = 0; j3 < 4; j3++)
			{
				if(ai1[ai2[l2++]] != 0)
					pixels[pixelOffset] = k2;
				if(ai1[ai2[l2++]] != 0)
					pixels[pixelOffset + 1] = k2;
				if(ai1[ai2[l2++]] != 0)
					pixels[pixelOffset + 2] = k2;
				if(ai1[ai2[l2++]] != 0)
					pixels[pixelOffset + 3] = k2;
				pixelOffset += j;
			}
		}
	}
	

	public static void setupViewport(int minZ, int maxZ, int width, int height, int ai[]) {
		left = 0;
		top = 0;
		right = width;
		bottom = height;
		midX = width / 2;
		midY = height / 2;
		boolean isOnScreen[][][][] = new boolean[9][32][53][53];
		for (int yAngle = 128; yAngle <= 384; yAngle += 32) {
			for (int xAngle = 0; xAngle < 2048; xAngle += 64) {
				yCurveSin = Model.SINE[yAngle];
				yCUrveCos = Model.COSINE[yAngle];
				xCurveSin = Model.SINE[xAngle];
				xCurveCos = Model.COSINE[xAngle];
				int l1 = (yAngle - 128) / 32;
				int j2 = xAngle / 64;
				for (int l2 = -26; l2 <= 26; l2++) {
					for (int j3 = -26; j3 <= 26; j3++) {
						int k3 = l2 * 128;
						int i4 = j3 * 128;
						boolean flag2 = false;
						for (int k4 = -minZ; k4 <= maxZ; k4 += 128) {
							if (!isOnScreen(ai[l1] + k4, i4, k3))
								continue;
							flag2 = true;
							break;
						}

						isOnScreen[l1][j2][l2 + 25 + 1][j3 + 25 + 1] = flag2;
					}

				}

			}

		}

		for (int k1 = 0; k1 < 8; k1++) {
			for (int i2 = 0; i2 < 32; i2++) {
				for (int k2 = -25; k2 < 25; k2++) {
					for (int i3 = -25; i3 < 25; i3++) {
						boolean flag1 = false;
						label0: for (int l3 = -1; l3 <= 1; l3++) {
							for (int j4 = -1; j4 <= 1; j4++) {
								if (isOnScreen[k1][i2][k2 + l3 + 25 + 1][i3 + j4 + 25 + 1])
									flag1 = true;
								else if (isOnScreen[k1][(i2 + 1) % 31][k2 + l3 + 25 + 1][i3 + j4 + 25 + 1])
									flag1 = true;
								else if (isOnScreen[k1 + 1][i2][k2 + l3 + 25 + 1][i3 + j4 + 25 + 1]) {
									flag1 = true;
								} else {
									if (!isOnScreen[k1 + 1][(i2 + 1) % 31][k2 + l3 + 25 + 1][i3 + j4 + 25 + 1])
										continue;
									flag1 = true;
								}
								break label0;
							}

						}

						tile_visibility_maps[k1][i2][k2 + 25][i3 + 25] = flag1;
					}

				}

			}

		}

	}

	private static boolean isOnScreen(int z, int y, int x) {
		int l = y * xCurveSin + x * xCurveCos >> 16;
		int i1 = y * xCurveCos - x * xCurveSin >> 16;
		int dist = z * yCurveSin + i1 * yCUrveCos >> 16;
		int k1 = z * yCUrveCos - i1 * yCurveSin >> 16;
		if (dist < 50 || dist > 4000)
			return false;
		int l1 = midX + l * WorldController.focalLength / dist;
		int i2 = midY + k1 * WorldController.focalLength / dist;
		return l1 >= left && l1 <= right && i2 >= top && i2 <= bottom;
	}

	public void request2DTrace(int x, int y) {
		aBoolean467 = true;
		anInt468 = !Client.antialiasing ? y : y << 1;
		anInt469 = !Client.antialiasing ? x : x << 1;
		anInt470 = -1;
		anInt471 = -1;
	}

	public void render(int xCam, int yCam, int xCurve, int zCam, int plane, int yCurve) {
		if (xCam < 0)
			xCam = 0;
		else if (xCam >= xMapSize * 128)
			xCam = xMapSize * 128 - 1;
		if (yCam < 0)
			yCam = 0;
		else if (yCam >= yMapSize * 128)
			yCam = yMapSize * 128 - 1;
		anInt448++;
		yCurveSin = Model.SINE[yCurve];
		yCUrveCos = Model.COSINE[yCurve];
		xCurveSin = Model.SINE[xCurve];
		xCurveCos = Model.COSINE[xCurve];
		tile_visibility_map = tile_visibility_maps[(yCurve - 128) / 32][xCurve / 64];
		anInt455 = xCam;
		anInt456 = zCam;
		anInt457 = yCam;
		xCamPosTile = xCam / 128;
		yCamPosTile = yCam / 128;
		plane__ = plane;
		anInt449 = xCamPosTile - 25;
		if (anInt449 < 0)
			anInt449 = 0;
		anInt451 = yCamPosTile - 25;
		if (anInt451 < 0)
			anInt451 = 0;
		anInt450 = xCamPosTile + 25;
		if (anInt450 > xMapSize)
			anInt450 = xMapSize;
		anInt452 = yCamPosTile + 25;
		if (anInt452 > yMapSize)
			anInt452 = yMapSize;
		processCulling();
		anInt446 = 0;
		for (int k1 = currentHL; k1 < zMapSize; k1++) {
			Tile tiles[][] = tileArray[k1];
			for (int x_ = anInt449; x_ < anInt450; x_++) {
				for (int y_ = anInt451; y_ < anInt452; y_++) {
					Tile tile = tiles[x_][y_];
					if (tile != null)
						if (tile.logicHeight > plane || !tile_visibility_map[(x_ - xCamPosTile) + 25][(y_ - yCamPosTile) + 25] && anIntArrayArrayArray440[k1][x_][y_] - zCam < 3000) {
							tile.aBoolean1322 = false;
							tile.aBoolean1323 = false;
							tile.anInt1325 = 0;
						} else {
							tile.aBoolean1322 = true;
							tile.aBoolean1323 = true;
							tile.aBoolean1324 = tile.entityCount > 0;
							anInt446++;
						}
				}

			}

		}

		for (int l1 = currentHL; l1 < zMapSize; l1++) {
			Tile aclass30_sub3_1[][] = tileArray[l1];
			for (int l2 = -25; l2 <= 0; l2++) {
				int i3 = xCamPosTile + l2;
				int k3 = xCamPosTile - l2;
				if (i3 >= anInt449 || k3 < anInt450) {
					for (int i4 = -25; i4 <= 0; i4++) {
						int k4 = yCamPosTile + i4;
						int i5 = yCamPosTile - i4;
						if (i3 >= anInt449) {
							if (k4 >= anInt451) {
								Tile class30_sub3_1 = aclass30_sub3_1[i3][k4];
								if (class30_sub3_1 != null && class30_sub3_1.aBoolean1322)
									renderTile(class30_sub3_1, true);
							}
							if (i5 < anInt452) {
								Tile class30_sub3_2 = aclass30_sub3_1[i3][i5];
								if (class30_sub3_2 != null && class30_sub3_2.aBoolean1322)
									renderTile(class30_sub3_2, true);
							}
						}
						if (k3 < anInt450) {
							if (k4 >= anInt451) {
								Tile class30_sub3_3 = aclass30_sub3_1[k3][k4];
								if (class30_sub3_3 != null && class30_sub3_3.aBoolean1322)
									renderTile(class30_sub3_3, true);
							}
							if (i5 < anInt452) {
								Tile class30_sub3_4 = aclass30_sub3_1[k3][i5];
								if (class30_sub3_4 != null && class30_sub3_4.aBoolean1322)
									renderTile(class30_sub3_4, true);
							}
						}
						if (anInt446 == 0) {
							aBoolean467 = false;
							WorldController.focalLength = 512;
							return;
						}
					}

				}
			}

		}

		for (int j2 = currentHL; j2 < zMapSize; j2++) {
			Tile aclass30_sub3_2[][] = tileArray[j2];
			for (int j3 = -25; j3 <= 0; j3++) {
				int l3 = xCamPosTile + j3;
				int j4 = xCamPosTile - j3;
				if (l3 >= anInt449 || j4 < anInt450) {
					for (int l4 = -25; l4 <= 0; l4++) {
						int j5 = yCamPosTile + l4;
						int k5 = yCamPosTile - l4;
						if (l3 >= anInt449) {
							if (j5 >= anInt451) {
								Tile class30_sub3_5 = aclass30_sub3_2[l3][j5];
								if (class30_sub3_5 != null && class30_sub3_5.aBoolean1322)
									renderTile(class30_sub3_5, false);
							}
							if (k5 < anInt452) {
								Tile class30_sub3_6 = aclass30_sub3_2[l3][k5];
								if (class30_sub3_6 != null && class30_sub3_6.aBoolean1322)
									renderTile(class30_sub3_6, false);
							}
						}
						if (j4 < anInt450) {
							if (j5 >= anInt451) {
								Tile class30_sub3_7 = aclass30_sub3_2[j4][j5];
								if (class30_sub3_7 != null && class30_sub3_7.aBoolean1322)
									renderTile(class30_sub3_7, false);
							}
							if (k5 < anInt452) {
								Tile tile = aclass30_sub3_2[j4][k5];
								if (tile != null && tile.aBoolean1322)
									renderTile(tile, false);
							}
						}
						if (anInt446 == 0) {
							aBoolean467 = false;
							WorldController.focalLength = 512;
							return;
						}
					}

				}
			}
		}
		aBoolean467 = false;
		WorldController.focalLength = 512;
	}

	private void renderTile(Tile mainTile, boolean flag) {
		tileDeque.insertBack(mainTile);
		do {
			Tile tempTile;
			do {
				tempTile = (Tile) tileDeque.popFront();
				if (tempTile == null)
					return;
			} while (!tempTile.aBoolean1323);
			int i = tempTile.tileX;
			int j = tempTile.tileY;
			int k = tempTile.tileZ;
			int l = tempTile.plane;
			Tile tiles[][] = tileArray[k];
			if (tempTile.aBoolean1322) {
				if (flag) {
					if (k > 0) {
						Tile tile = tileArray[k - 1][i][j];
						if (tile != null && tile.aBoolean1323)
							continue;
					}
					if (i <= xCamPosTile && i > anInt449) {
						Tile tile = tiles[i - 1][j];
						if (tile != null && tile.aBoolean1323 && (tile.aBoolean1322 || (tempTile.anInt1320 & 1) == 0))
							continue;
					}
					if (i >= xCamPosTile && i < anInt450 - 1) {
						Tile tile = tiles[i + 1][j];
						if (tile != null && tile.aBoolean1323 && (tile.aBoolean1322 || (tempTile.anInt1320 & 4) == 0))
							continue;
					}
					if (j <= yCamPosTile && j > anInt451) {
						Tile class30_sub3_5 = tiles[i][j - 1];
						if (class30_sub3_5 != null && class30_sub3_5.aBoolean1323 && (class30_sub3_5.aBoolean1322 || (tempTile.anInt1320 & 8) == 0))
							continue;
					}
					if (j >= yCamPosTile && j < anInt452 - 1) {
						Tile class30_sub3_6 = tiles[i][j + 1];
						if (class30_sub3_6 != null && class30_sub3_6.aBoolean1323 && (class30_sub3_6.aBoolean1322 || (tempTile.anInt1320 & 2) == 0))
							continue;
					}
				} else {
					flag = true;
				}
				tempTile.aBoolean1322 = false;
				if (tempTile.tileBelowThisTile != null) {
					Tile lowerTile = tempTile.tileBelowThisTile;
					if (lowerTile.plainTile != null) {
						if (!method320(0, i, j))
							drawPlainTile(lowerTile.plainTile, 0, yCurveSin, yCUrveCos, xCurveSin, xCurveCos, i, j);
					} else if (lowerTile.shapedTile != null && !method320(0, i, j))
						drawShapedTile(i, yCurveSin, xCurveSin, lowerTile.shapedTile, yCUrveCos, j, xCurveCos);
					WallObject wallObject = lowerTile.wallObject;
					if (wallObject != null)
						wallObject.node1.renderAtPoint(0, yCurveSin, yCUrveCos, xCurveSin, xCurveCos, wallObject.xPos - anInt455, wallObject.zPos - anInt456, wallObject.yPos - anInt457, wallObject.uid, wallObject.wallObjUID);
					for (int i2 = 0; i2 < lowerTile.entityCount; i2++) {
						InteractableObject iObject = lowerTile.interactableObjects[i2];
						if (iObject != null)
							iObject.node.renderAtPoint(iObject.rotation, yCurveSin, yCUrveCos, xCurveSin, xCurveCos, iObject.worldX - anInt455, iObject.worldZ - anInt456, iObject.worldY - anInt457, iObject.uid, iObject.interactiveObjUID);
					}

				}
				boolean flag1 = false;
				if (tempTile.plainTile != null) {
					if (!method320(l, i, j)) {
						flag1 = true;
						drawPlainTile(tempTile.plainTile, l, yCurveSin, yCUrveCos, xCurveSin, xCurveCos, i, j);
					}
				} else if (tempTile.shapedTile != null && !method320(l, i, j)) {
					flag1 = true;
					drawShapedTile(i, yCurveSin, xCurveSin, tempTile.shapedTile, yCUrveCos, j, xCurveCos);
				}
				int j1 = 0;
				int j2 = 0;
				WallObject class10_3 = tempTile.wallObject;
				WallDecoration class26_1 = tempTile.wallDecoration;
				if (class10_3 != null || class26_1 != null) {
					if (xCamPosTile == i)
						j1++;
					else if (xCamPosTile < i)
						j1 += 2;
					if (yCamPosTile == j)
						j1 += 3;
					else if (yCamPosTile > j)
						j1 += 6;
					j2 = anIntArray478[j1];
					tempTile.anInt1328 = anIntArray480[j1];
				}
				if (class10_3 != null) {
					if ((class10_3.orientation & anIntArray479[j1]) != 0) {
						if (class10_3.orientation == 16) {
							tempTile.anInt1325 = 3;
							tempTile.anInt1326 = anIntArray481[j1];
							tempTile.anInt1327 = 3 - tempTile.anInt1326;
						} else if (class10_3.orientation == 32) {
							tempTile.anInt1325 = 6;
							tempTile.anInt1326 = anIntArray482[j1];
							tempTile.anInt1327 = 6 - tempTile.anInt1326;
						} else if (class10_3.orientation == 64) {
							tempTile.anInt1325 = 12;
							tempTile.anInt1326 = anIntArray483[j1];
							tempTile.anInt1327 = 12 - tempTile.anInt1326;
						} else {
							tempTile.anInt1325 = 9;
							tempTile.anInt1326 = anIntArray484[j1];
							tempTile.anInt1327 = 9 - tempTile.anInt1326;
						}
					} else {
						tempTile.anInt1325 = 0;
					}
					if ((class10_3.orientation & j2) != 0 && !method321(l, i, j, class10_3.orientation))
						class10_3.node1.renderAtPoint(0, yCurveSin, yCUrveCos, xCurveSin, xCurveCos, class10_3.xPos - anInt455, class10_3.zPos - anInt456, class10_3.yPos - anInt457, class10_3.uid, class10_3.wallObjUID);
					if ((class10_3.orientation1 & j2) != 0 && !method321(l, i, j, class10_3.orientation1))
						class10_3.node2.renderAtPoint(0, yCurveSin, yCUrveCos, xCurveSin, xCurveCos, class10_3.xPos - anInt455, class10_3.zPos - anInt456, class10_3.yPos - anInt457, class10_3.uid, class10_3.wallObjUID);
				}
				if (class26_1 != null && !method322(l, i, j, class26_1.node.modelHeight))
					if ((class26_1.configurationBits & j2) != 0)
						class26_1.node.renderAtPoint(class26_1.rotation, yCurveSin, yCUrveCos, xCurveSin, xCurveCos, class26_1.xPos - anInt455, class26_1.zPos - anInt456, class26_1.yPos - anInt457, class26_1.uid, class26_1.wallDecorUID);
					else if ((class26_1.configurationBits & 0x300) != 0) {
						int j4 = class26_1.xPos - anInt455;
						int l5 = class26_1.zPos - anInt456;
						int k6 = class26_1.yPos - anInt457;
						int i8 = class26_1.rotation;
						int k9;
						if (i8 == 1 || i8 == 2)
							k9 = -j4;
						else
							k9 = j4;
						int k10;
						if (i8 == 2 || i8 == 3)
							k10 = -k6;
						else
							k10 = k6;
						if ((class26_1.configurationBits & 0x100) != 0 && k10 < k9) {
							int i11 = j4 + faceXoffset2[i8];
							int k11 = k6 + faceYOffset2[i8];
							class26_1.node.renderAtPoint(i8 * 512 + 256, yCurveSin, yCUrveCos, xCurveSin, xCurveCos, i11, l5, k11, class26_1.uid, class26_1.wallDecorUID);
						}
						if ((class26_1.configurationBits & 0x200) != 0 && k10 > k9) {
							int j11 = j4 + faceXOffset3[i8];
							int l11 = k6 + faceYOffset3[i8];
							class26_1.node.renderAtPoint(i8 * 512 + 1280 & 0x7ff, yCurveSin, yCUrveCos, xCurveSin, xCurveCos, j11, l5, l11, class26_1.uid, class26_1.wallDecorUID);
						}
					}
				if (flag1) {
					GroundDecoration class49 = tempTile.groundDecoration;
					if (class49 != null)
						class49.node.renderAtPoint(0, yCurveSin, yCUrveCos, xCurveSin, xCurveCos, class49.xPos - anInt455, class49.zPos - anInt456, class49.yPos - anInt457, class49.uid, class49.groundDecorUID);
					GroundItem object4_1 = tempTile.groundItem;
					if (object4_1 != null && object4_1.topItem == 0) {
						if (object4_1.secondGroundItem != null)
							object4_1.secondGroundItem.renderAtPoint(0, yCurveSin, yCUrveCos, xCurveSin, xCurveCos, object4_1.xPos - anInt455, object4_1.zPos - anInt456, object4_1.yPos - anInt457, object4_1.uid, object4_1.newuid);
						if (object4_1.thirdGroundItem != null)
							object4_1.thirdGroundItem.renderAtPoint(0, yCurveSin, yCUrveCos, xCurveSin, xCurveCos, object4_1.xPos - anInt455, object4_1.zPos - anInt456, object4_1.yPos - anInt457, object4_1.uid, object4_1.newuid);
						if (object4_1.firstGroundItem != null)
							object4_1.firstGroundItem.renderAtPoint(0, yCurveSin, yCUrveCos, xCurveSin, xCurveCos, object4_1.xPos - anInt455, object4_1.zPos - anInt456, object4_1.yPos - anInt457, object4_1.uid, object4_1.newuid);
					}
				}
				int k4 = tempTile.anInt1320;
				if (k4 != 0) {
					if (i < xCamPosTile && (k4 & 4) != 0) {
						Tile class30_sub3_17 = tiles[i + 1][j];
						if (class30_sub3_17 != null && class30_sub3_17.aBoolean1323)
							tileDeque.insertBack(class30_sub3_17);
					}
					if (j < yCamPosTile && (k4 & 2) != 0) {
						Tile class30_sub3_18 = tiles[i][j + 1];
						if (class30_sub3_18 != null && class30_sub3_18.aBoolean1323)
							tileDeque.insertBack(class30_sub3_18);
					}
					if (i > xCamPosTile && (k4 & 1) != 0) {
						Tile class30_sub3_19 = tiles[i - 1][j];
						if (class30_sub3_19 != null && class30_sub3_19.aBoolean1323)
							tileDeque.insertBack(class30_sub3_19);
					}
					if (j > yCamPosTile && (k4 & 8) != 0) {
						Tile class30_sub3_20 = tiles[i][j - 1];
						if (class30_sub3_20 != null && class30_sub3_20.aBoolean1323)
							tileDeque.insertBack(class30_sub3_20);
					}
				}
			}
			if (tempTile.anInt1325 != 0) {
				boolean flag2 = true;
				for (int k1 = 0; k1 < tempTile.entityCount; k1++) {
					if (tempTile.interactableObjects[k1].height == anInt448 || (tempTile.anIntArray1319[k1] & tempTile.anInt1325) != tempTile.anInt1326)
						continue;
					flag2 = false;
					break;
				}

				if (flag2) {
					WallObject class10_1 = tempTile.wallObject;
					if (!method321(l, i, j, class10_1.orientation))
						class10_1.node1.renderAtPoint(0, yCurveSin, yCUrveCos, xCurveSin, xCurveCos, class10_1.xPos - anInt455, class10_1.zPos - anInt456, class10_1.yPos - anInt457, class10_1.uid, class10_1.wallObjUID);
					tempTile.anInt1325 = 0;
				}
			}
			if (tempTile.aBoolean1324)
				try {
					int i1 = tempTile.entityCount;
					tempTile.aBoolean1324 = false;
					int l1 = 0;
					label0: for (int k2 = 0; k2 < i1; k2++) {
						InteractableObject class28_1 = tempTile.interactableObjects[k2];
						if (class28_1.height == anInt448)
							continue;
						for (int k3 = class28_1.tileLeft; k3 <= class28_1.tileRight; k3++) {
							for (int l4 = class28_1.tileTop; l4 <= class28_1.tileBottom; l4++) {
								Tile class30_sub3_21 = tiles[k3][l4];
								if (class30_sub3_21.aBoolean1322) {
									tempTile.aBoolean1324 = true;
								} else {
									if (class30_sub3_21.anInt1325 == 0)
										continue;
									int l6 = 0;
									if (k3 > class28_1.tileLeft)
										l6++;
									if (k3 < class28_1.tileRight)
										l6 += 4;
									if (l4 > class28_1.tileTop)
										l6 += 8;
									if (l4 < class28_1.tileBottom)
										l6 += 2;
									if ((l6 & class30_sub3_21.anInt1325) != tempTile.anInt1327)
										continue;
									tempTile.aBoolean1324 = true;
								}
								continue label0;
							}

						}

						aClass28Array462[l1++] = class28_1;
						int i5 = xCamPosTile - class28_1.tileLeft;
						int i6 = class28_1.tileRight - xCamPosTile;
						if (i6 > i5)
							i5 = i6;
						int i7 = yCamPosTile - class28_1.tileTop;
						int j8 = class28_1.tileBottom - yCamPosTile;
						if (j8 > i7)
							class28_1.anInt527 = i5 + j8;
						else
							class28_1.anInt527 = i5 + i7;
					}

					while (l1 > 0) {
						int i3 = -50;
						int l3 = -1;
						for (int j5 = 0; j5 < l1; j5++) {
							InteractableObject class28_2 = aClass28Array462[j5];
							if (class28_2.height != anInt448)
								if (class28_2.anInt527 > i3) {
									i3 = class28_2.anInt527;
									l3 = j5;
								} else if (class28_2.anInt527 == i3) {
									int j7 = class28_2.worldX - anInt455;
									int k8 = class28_2.worldY - anInt457;
									int l9 = aClass28Array462[l3].worldX - anInt455;
									int l10 = aClass28Array462[l3].worldY - anInt457;
									if (j7 * j7 + k8 * k8 > l9 * l9 + l10 * l10)
										l3 = j5;
								}
						}

						if (l3 == -1)
							break;
						InteractableObject class28_3 = aClass28Array462[l3];
						class28_3.height = anInt448;
						if (!method323(l, class28_3.tileLeft, class28_3.tileRight, class28_3.tileTop, class28_3.tileBottom, class28_3.node.modelHeight))
							class28_3.node.renderAtPoint(class28_3.rotation, yCurveSin, yCUrveCos, xCurveSin, xCurveCos, class28_3.worldX - anInt455, class28_3.worldZ - anInt456, class28_3.worldY - anInt457, class28_3.uid, class28_3.interactiveObjUID);
						for (int k7 = class28_3.tileLeft; k7 <= class28_3.tileRight; k7++) {
							for (int l8 = class28_3.tileTop; l8 <= class28_3.tileBottom; l8++) {
								Tile class30_sub3_22 = tiles[k7][l8];
								if (class30_sub3_22.anInt1325 != 0)
									tileDeque.insertBack(class30_sub3_22);
								else if ((k7 != i || l8 != j) && class30_sub3_22.aBoolean1323)
									tileDeque.insertBack(class30_sub3_22);
							}

						}

					}
					if (tempTile.aBoolean1324)
						continue;
				} catch (Exception _ex) {
					_ex.printStackTrace();
					tempTile.aBoolean1324 = false;
				}
			if (!tempTile.aBoolean1323 || tempTile.anInt1325 != 0)
				continue;
			if (i <= xCamPosTile && i > anInt449) {
				Tile class30_sub3_8 = tiles[i - 1][j];
				if (class30_sub3_8 != null && class30_sub3_8.aBoolean1323)
					continue;
			}
			if (i >= xCamPosTile && i < anInt450 - 1) {
				Tile class30_sub3_9 = tiles[i + 1][j];
				if (class30_sub3_9 != null && class30_sub3_9.aBoolean1323)
					continue;
			}
			if (j <= yCamPosTile && j > anInt451) {
				Tile class30_sub3_10 = tiles[i][j - 1];
				if (class30_sub3_10 != null && class30_sub3_10.aBoolean1323)
					continue;
			}
			if (j >= yCamPosTile && j < anInt452 - 1) {
				Tile class30_sub3_11 = tiles[i][j + 1];
				if (class30_sub3_11 != null && class30_sub3_11.aBoolean1323)
					continue;
			}
			tempTile.aBoolean1323 = false;
			anInt446--;
			GroundItem object4 = tempTile.groundItem;
			if (object4 != null && object4.topItem != 0) {
				if (object4.secondGroundItem != null)
					object4.secondGroundItem.renderAtPoint(0, yCurveSin, yCUrveCos, xCurveSin, xCurveCos, object4.xPos - anInt455, object4.zPos - anInt456 - object4.topItem, object4.yPos - anInt457, object4.uid, object4.newuid);
				if (object4.thirdGroundItem != null)
					object4.thirdGroundItem.renderAtPoint(0, yCurveSin, yCUrveCos, xCurveSin, xCurveCos, object4.xPos - anInt455, object4.zPos - anInt456 - object4.topItem, object4.yPos - anInt457, object4.uid, object4.newuid);
				if (object4.firstGroundItem != null)
					object4.firstGroundItem.renderAtPoint(0, yCurveSin, yCUrveCos, xCurveSin, xCurveCos, object4.xPos - anInt455, object4.zPos - anInt456 - object4.topItem, object4.yPos - anInt457, object4.uid, object4.newuid);
			}
			if (tempTile.anInt1328 != 0) {
				WallDecoration class26 = tempTile.wallDecoration;
				if (class26 != null && !method322(l, i, j, class26.node.modelHeight))
					if ((class26.configurationBits & tempTile.anInt1328) != 0)
						class26.node.renderAtPoint(class26.rotation, yCurveSin, yCUrveCos, xCurveSin, xCurveCos, class26.xPos - anInt455, class26.zPos - anInt456, class26.yPos - anInt457, class26.uid, class26.wallDecorUID);
					else if ((class26.configurationBits & 0x300) != 0) {
						int l2 = class26.xPos - anInt455;
						int j3 = class26.zPos - anInt456;
						int i4 = class26.yPos - anInt457;
						int k5 = class26.rotation;
						int j6;
						if (k5 == 1 || k5 == 2)
							j6 = -l2;
						else
							j6 = l2;
						int l7;
						if (k5 == 2 || k5 == 3)
							l7 = -i4;
						else
							l7 = i4;
						if ((class26.configurationBits & 0x100) != 0 && l7 >= j6) {
							int i9 = l2 + faceXoffset2[k5];
							int i10 = i4 + faceYOffset2[k5];
							class26.node.renderAtPoint(k5 * 512 + 256, yCurveSin, yCUrveCos, xCurveSin, xCurveCos, i9, j3, i10, class26.uid, class26.wallDecorUID);
						}
						if ((class26.configurationBits & 0x200) != 0 && l7 <= j6) {
							int j9 = l2 + faceXOffset3[k5];
							int j10 = i4 + faceYOffset3[k5];
							class26.node.renderAtPoint(k5 * 512 + 1280 & 0x7ff, yCurveSin, yCUrveCos, xCurveSin, xCurveCos, j9, j3, j10, class26.uid, class26.wallDecorUID);
						}
					}
				WallObject class10_2 = tempTile.wallObject;
				if (class10_2 != null) {
					if ((class10_2.orientation1 & tempTile.anInt1328) != 0 && !method321(l, i, j, class10_2.orientation1))
						class10_2.node2.renderAtPoint(0, yCurveSin, yCUrveCos, xCurveSin, xCurveCos, class10_2.xPos - anInt455, class10_2.zPos - anInt456, class10_2.yPos - anInt457, class10_2.uid, class10_2.wallObjUID);
					if ((class10_2.orientation & tempTile.anInt1328) != 0 && !method321(l, i, j, class10_2.orientation))
						class10_2.node1.renderAtPoint(0, yCurveSin, yCUrveCos, xCurveSin, xCurveCos, class10_2.xPos - anInt455, class10_2.zPos - anInt456, class10_2.yPos - anInt457, class10_2.uid, class10_2.wallObjUID);
				}
			}
			if (k < zMapSize - 1) {
				Tile class30_sub3_12 = tileArray[k + 1][i][j];
				if (class30_sub3_12 != null && class30_sub3_12.aBoolean1323)
					tileDeque.insertBack(class30_sub3_12);
			}
			if (i < xCamPosTile) {
				Tile class30_sub3_13 = tiles[i + 1][j];
				if (class30_sub3_13 != null && class30_sub3_13.aBoolean1323)
					tileDeque.insertBack(class30_sub3_13);
			}
			if (j < yCamPosTile) {
				Tile class30_sub3_14 = tiles[i][j + 1];
				if (class30_sub3_14 != null && class30_sub3_14.aBoolean1323)
					tileDeque.insertBack(class30_sub3_14);
			}
			if (i > xCamPosTile) {
				Tile class30_sub3_15 = tiles[i - 1][j];
				if (class30_sub3_15 != null && class30_sub3_15.aBoolean1323)
					tileDeque.insertBack(class30_sub3_15);
			}
			if (j > yCamPosTile) {
				Tile class30_sub3_16 = tiles[i][j - 1];
				if (class30_sub3_16 != null && class30_sub3_16.aBoolean1323)
					tileDeque.insertBack(class30_sub3_16);
			}
		} while (true);
	}
	
	public boolean opaque_floor_texture = false;
	private void drawPlainTile(PlainTile class43, int i, int j, int k, int l, int i1, int j1, int k1) {
		int l1;
		int i2 = l1 = (j1 << 7) - anInt455;
		int j2;
		int k2 = j2 = (k1 << 7) - anInt457;
		int l2;
		int i3 = l2 = i2 + 128;
		int j3;
		int k3 = j3 = k2 + 128;
		int l3 = anIntArrayArrayArray440[i][j1][k1] - anInt456;
		int i4 = anIntArrayArrayArray440[i][j1 + 1][k1] - anInt456;
		int j4 = anIntArrayArrayArray440[i][j1 + 1][k1 + 1] - anInt456;
		int k4 = anIntArrayArrayArray440[i][j1][k1 + 1] - anInt456;
		int l4 = k2 * l + i2 * i1 >> 16;
		k2 = k2 * i1 - i2 * l >> 16;
		i2 = l4;
		l4 = l3 * k - k2 * j >> 16;
		k2 = l3 * j + k2 * k >> 16;
		l3 = l4;
		if (k2 < 50) {
			return;
		}
		l4 = j2 * l + i3 * i1 >> 16;
		j2 = j2 * i1 - i3 * l >> 16;
		i3 = l4;
		l4 = i4 * k - j2 * j >> 16;
		j2 = i4 * j + j2 * k >> 16;
		i4 = l4;
		if (j2 < 50) {
			return;
		}
		l4 = k3 * l + l2 * i1 >> 16;
		k3 = k3 * i1 - l2 * l >> 16;
		l2 = l4;
		l4 = j4 * k - k3 * j >> 16;
		k3 = j4 * j + k3 * k >> 16;
		j4 = l4;
		if (k3 < 50) {
			return;
		}
		l4 = j3 * l + l1 * i1 >> 16;
		j3 = j3 * i1 - l1 * l >> 16;
		l1 = l4;
		l4 = k4 * k - j3 * j >> 16;
		j3 = k4 * j + j3 * k >> 16;
		k4 = l4;
		if (j3 < 50) {
			return;
		}
		int i5 = Rasterizer.textureInt1 + i2 * WorldController.focalLength / k2;
		int j5 = Rasterizer.textureInt2 + l3 * WorldController.focalLength / k2;
		int k5 = Rasterizer.textureInt1 + i3 * WorldController.focalLength / j2;
		int l5 = Rasterizer.textureInt2 + i4 * WorldController.focalLength / j2;
		int i6 = Rasterizer.textureInt1 + l2 * WorldController.focalLength / k3;
		int j6 = Rasterizer.textureInt2 + j4 * WorldController.focalLength / k3;
		int k6 = Rasterizer.textureInt1 + l1 * WorldController.focalLength / j3;
		int l6 = Rasterizer.textureInt2 + k4 * WorldController.focalLength / j3;
		Rasterizer.anInt1465 = 0;
		if ((i6 - k6) * (l5 - l6) - (j6 - l6) * (k5 - k6) > 0) {
			Rasterizer.aBoolean1462 = i6 < 0 || k6 < 0 || k5 < 0 || i6 > DrawingArea.viewportRX || k6 > DrawingArea.viewportRX || k5 > DrawingArea.viewportRX;
			if (aBoolean467 && method318(anInt468, anInt469, j6, l6, l5, i6, k6, k5)) {
				anInt470 = j1;
				anInt471 = k1;
			}
			if (class43.anInt720 == -1 || class43.anInt720 > 50) {
				if (class43.anInt718 != 0xbc614e) {
					if (Client.getOption("hd_tex") && class43.anInt720 != -1) {
						if (class43.aBoolean721) {
							Rasterizer.drawMaterializedTriangle(j6, l6, l5, i6, k6, k5, class43.anInt718, class43.anInt719, class43.anInt717, i2, i3, l1, l3, i4, k4, k2, j2, j3, class43.anInt720, k3, j3, j2);
						} else {
							Rasterizer.drawMaterializedTriangle(j6, l6, l5, i6, k6, k5, class43.anInt718, class43.anInt719, class43.anInt717, l2, l1, i3, j4, k4, i4, k3, j3, j2, class43.anInt720, k3, j3, j2);
						}
					} else {
						Rasterizer.drawGouraudTriangle(j6, l6, l5, i6, k6, k5, class43.anInt718, class43.anInt719, class43.anInt717, k3, j3, j2);
					}
				}
			} else if (!lowMem) {
				if (class43.aBoolean721) {
					Rasterizer.drawTexturedTriangle(j6, l6, l5, i6, k6, k5, class43.anInt718, class43.anInt719, class43.anInt717, i2, i3, l1, l3, i4, k4, k2, j2, j3, class43.anInt720, k3, j3, j2);
				} else {
					Rasterizer.drawTexturedTriangle(j6, l6, l5, i6, k6, k5, class43.anInt718, class43.anInt719, class43.anInt717, l2, l1, i3, j4, k4, i4, k3, j3, j2, class43.anInt720, k3, j3, j2);
				}
			} else {
				int i7 = anIntArray485[class43.anInt720];
				Rasterizer.drawGouraudTriangle(j6, l6, l5, i6, k6, k5, method317(i7, class43.anInt718), method317(i7, class43.anInt719), method317(i7, class43.anInt717), k3, j3, j2);
			}
		}
		if ((i5 - k5) * (l6 - l5) - (j5 - l5) * (k6 - k5) > 0) {
			Rasterizer.aBoolean1462 = i5 < 0 || k5 < 0 || k6 < 0 || i5 > DrawingArea.viewportRX || k5 > DrawingArea.viewportRX || k6 > DrawingArea.viewportRX;
			if (aBoolean467 && method318(anInt468, anInt469, j5, l5, l6, i5, k5, k6)) {
				anInt470 = j1;
				anInt471 = k1;
			}
			if (class43.anInt720 == -1 || class43.anInt720 > 50) {
				if (class43.anInt716 != 0xbc614e) {
					if (Client.getOption("hd_tex") && class43.anInt720 != -1) {
						Rasterizer.drawMaterializedTriangle(j5, l5, l6, i5, k5, k6, class43.anInt716, class43.anInt717, class43.anInt719, i2, i3, l1, l3, i4, k4, k2, j2, j3, class43.anInt720, k2, j2, j3);
					} else {
						Rasterizer.drawGouraudTriangle(j5, l5, l6, i5, k5, k6, class43.anInt716, class43.anInt717, class43.anInt719, k2, j2, j3);
					}
				}
			} else {
				if(!lowMem) {
					Rasterizer.drawTexturedTriangle(j5, l5, l6, i5, k5, k6, class43.anInt716, class43.anInt717, class43.anInt719, i2, i3, l1, l3, i4, k4, k2, j2, j3, class43.anInt720, k2, j2, j3);
					return;
				}
				int j7 = anIntArray485[class43.anInt720];
				Rasterizer.drawGouraudTriangle(j5, l5, l6, i5, k5, k6, method317(j7, class43.anInt716), method317(j7, class43.anInt717), method317(j7, class43.anInt719), k2, j2, j3);
			}
		}
	}

	private void drawShapedTile(int i, int j, int k, ShapedTile class40, int l, int i1, int j1) {
		int k1 = class40.anIntArray673.length;
		for (int l1 = 0; l1 < k1; l1++) {
			int i2 = class40.anIntArray673[l1] - anInt455;
			int k2 = class40.anIntArray674[l1] - anInt456;
			int i3 = class40.anIntArray675[l1] - anInt457;
			int k3 = i3 * k + i2 * j1 >> 16;
			i3 = i3 * j1 - i2 * k >> 16;
			i2 = k3;
			k3 = k2 * l - i3 * j >> 16;
			i3 = k2 * j + i3 * l >> 16;
			k2 = k3;
			if (i3 < 50) {
				return;
			}
			if (Client.getOption("hd_tex") || class40.anIntArray682 != null) {
				ShapedTile.anIntArray690[l1] = i2;
				ShapedTile.anIntArray691[l1] = k2;
				ShapedTile.anIntArray692[l1] = i3;
			}
			ShapedTile.anIntArray688[l1] = Rasterizer.textureInt1 + i2 * WorldController.focalLength / i3;
			ShapedTile.anIntArray689[l1] = Rasterizer.textureInt2 + k2 * WorldController.focalLength / i3;
			ShapedTile.screenZ[l1] = i3;
		}

		Rasterizer.anInt1465 = 0;
		k1 = class40.anIntArray679.length;
		for (int j2 = 0; j2 < k1; j2++) {
			int l2 = class40.anIntArray679[j2];
			int j3 = class40.anIntArray680[j2];
			int l3 = class40.anIntArray681[j2];
			int i4 = ShapedTile.anIntArray688[l2];
			int j4 = ShapedTile.anIntArray688[j3];
			int k4 = ShapedTile.anIntArray688[l3];
			int l4 = ShapedTile.anIntArray689[l2];
			int i5 = ShapedTile.anIntArray689[j3];
			int j5 = ShapedTile.anIntArray689[l3];
			if ((i4 - j4) * (j5 - i5) - (l4 - i5) * (k4 - j4) > 0) {
				Rasterizer.aBoolean1462 = i4 < 0 || j4 < 0 || k4 < 0 || i4 > DrawingArea.viewportRX || j4 > DrawingArea.viewportRX || k4 > DrawingArea.viewportRX;
				if (aBoolean467 && method318(anInt468, anInt469, l4, i5, j5, i4, j4, k4)) {
					anInt470 = i;
					anInt471 = i1;
				}
				if (class40.anIntArray682 == null || class40.anIntArray682[j2] == -1 || class40.anIntArray682[j2] > 50) {
					if (class40.anIntArray676[j2] != 0xbc614e) {
						if (Client.getOption("hd_tex") && class40.anIntArray682 != null && class40.anIntArray682[j2] != -1) {
							if (class40.aBoolean683 || class40.anIntArray682[j2] == 505) {
								Rasterizer.drawMaterializedTriangle(l4, i5, j5, i4, j4, k4, class40.anIntArray676[j2], class40.anIntArray677[j2], class40.anIntArray678[j2], ShapedTile.anIntArray690[0], ShapedTile.anIntArray690[1], ShapedTile.anIntArray690[3], ShapedTile.anIntArray691[0], ShapedTile.anIntArray691[1], ShapedTile.anIntArray691[3], ShapedTile.anIntArray692[0], ShapedTile.anIntArray692[1], ShapedTile.anIntArray692[3], class40.anIntArray682[j2], ShapedTile.screenZ[l2], ShapedTile.screenZ[j3], ShapedTile.screenZ[l3]);
							} else {
								Rasterizer.drawMaterializedTriangle(l4, i5, j5, i4, j4, k4, class40.anIntArray676[j2], class40.anIntArray677[j2], class40.anIntArray678[j2], ShapedTile.anIntArray690[l2], ShapedTile.anIntArray690[j3], ShapedTile.anIntArray690[l3], ShapedTile.anIntArray691[l2], ShapedTile.anIntArray691[j3], ShapedTile.anIntArray691[l3], ShapedTile.anIntArray692[l2], ShapedTile.anIntArray692[j3], ShapedTile.anIntArray692[l3], class40.anIntArray682[j2], ShapedTile.screenZ[l2], ShapedTile.screenZ[j3], ShapedTile.screenZ[l3]);
							}
						} else {
							Rasterizer.drawGouraudTriangle(l4, i5, j5, i4, j4, k4, class40.anIntArray676[j2], class40.anIntArray677[j2], class40.anIntArray678[j2], ShapedTile.screenZ[l2], ShapedTile.screenZ[j3], ShapedTile.screenZ[l3]);
						}
					}
				} else if (!lowMem) {
					if (class40.aBoolean683) {
						Rasterizer.drawTexturedTriangle(l4, i5, j5, i4, j4, k4, class40.anIntArray676[j2], class40.anIntArray677[j2], class40.anIntArray678[j2], ShapedTile.anIntArray690[0], ShapedTile.anIntArray690[1], ShapedTile.anIntArray690[3], ShapedTile.anIntArray691[0], ShapedTile.anIntArray691[1], ShapedTile.anIntArray691[3], ShapedTile.anIntArray692[0], ShapedTile.anIntArray692[1], ShapedTile.anIntArray692[3], class40.anIntArray682[j2], ShapedTile.screenZ[l2], ShapedTile.screenZ[j3], ShapedTile.screenZ[l3]);
					} else {
						Rasterizer.drawTexturedTriangle(l4, i5, j5, i4, j4, k4, class40.anIntArray676[j2], class40.anIntArray677[j2], class40.anIntArray678[j2], ShapedTile.anIntArray690[l2], ShapedTile.anIntArray690[j3], ShapedTile.anIntArray690[l3], ShapedTile.anIntArray691[l2], ShapedTile.anIntArray691[j3], ShapedTile.anIntArray691[l3], ShapedTile.anIntArray692[l2], ShapedTile.anIntArray692[j3], ShapedTile.anIntArray692[l3], class40.anIntArray682[j2], ShapedTile.screenZ[l2], ShapedTile.screenZ[j3], ShapedTile.screenZ[l3]);
					}
				} else {
					int k5 = anIntArray485[class40.anIntArray682[j2]];
					Rasterizer.drawGouraudTriangle(l4, i5, j5, i4, j4, k4, method317(k5, class40.anIntArray676[j2]), method317(k5, class40.anIntArray677[j2]), method317(k5, class40.anIntArray678[j2]), ShapedTile.screenZ[l2], ShapedTile.screenZ[j3], ShapedTile.screenZ[l3]);
				}
			}
		}
	}

	private int method317(int colour1, int colour2) {
		colour2 = 127 - colour2;
		colour2 = (colour2 * (colour1 & 0x7f)) / 160;
		if (colour2 < 2)
			colour2 = 2;
		else if (colour2 > 126)
			colour2 = 126;
		return (colour1 & 0xff80) + colour2;
	}

	private boolean method318(int mouseX, int mouseY, int triangleYA, int triangleYB, int triangleYC, int triangleXA, int triangleXB, int triangleXC) {
		if (mouseY < triangleYA && mouseY < triangleYB && mouseY < triangleYC)
			return false;
		if (mouseY > triangleYA && mouseY > triangleYB && mouseY > triangleYC)
			return false;
		if (mouseX < triangleXA && mouseX < triangleXB && mouseX < triangleXC)
			return false;
		if (mouseX > triangleXA && mouseX > triangleXB && mouseX > triangleXC)
			return false;
		int i2 = (mouseY - triangleYA) * (triangleXB - triangleXA) - (mouseX - triangleXA) * (triangleYB - triangleYA);
		int j2 = (mouseY - triangleYC) * (triangleXA - triangleXC) - (mouseX - triangleXC) * (triangleYA - triangleYC);
		int k2 = (mouseY - triangleYB) * (triangleXC - triangleXB) - (mouseX - triangleXB) * (triangleYC - triangleYB);
		return i2 * k2 > 0 && k2 * j2 > 0;
	}

	private void processCulling() {
		int count = cullingClusterPointer[plane__];
		CullingCluster clusters[] = cullingClusters[plane__];
		processedClusterPtr = 0;
		for (int k = 0; k < count; k++) {
			CullingCluster cluster = clusters[k];
			if (cluster.searchMask == 1) {
				int xDistFromCamStart = (cluster.tileStartX - xCamPosTile) + 25;
				if (xDistFromCamStart < 0 || xDistFromCamStart > 50)
					continue;
				int yDistFromCamStart = (cluster.tileStartY - yCamPosTile) + 25;
				if (yDistFromCamStart < 0)
					yDistFromCamStart = 0;
				int yDistFromCamEnd = (cluster.tileEndY - yCamPosTile) + 25;
				if (yDistFromCamEnd > 50)
					yDistFromCamEnd = 50;
				boolean visisble = false;
				while (yDistFromCamStart <= yDistFromCamEnd)
					if (tile_visibility_map[xDistFromCamStart][yDistFromCamStart++]) {
						visisble = true;
						break;
					}
				if (!visisble)
					continue;
				int xDistFromCamStartReal = anInt455 - cluster.worldStartX;
				if (xDistFromCamStartReal > 32) {
					cluster.tileDistanceEnum = 1;
				} else {
					if (xDistFromCamStartReal >= -32)
						continue;
					cluster.tileDistanceEnum = 2;
					xDistFromCamStartReal = -xDistFromCamStartReal;
				}
				cluster.worldDistanceFromCameraStartY = (cluster.worldStartY - anInt457 << 8) / xDistFromCamStartReal;
				cluster.worldDistanceFromCameraEndY = (cluster.worldEndY - anInt457 << 8) / xDistFromCamStartReal;
				cluster.worldDistanceFromCameraStartZ = (cluster.worldStartZ - anInt456 << 8) / xDistFromCamStartReal;
				cluster.worldDistanceFromCameraEndZ = (cluster.worldEndZ - anInt456 << 8) / xDistFromCamStartReal;
				processedClusters[processedClusterPtr++] = cluster;
				continue;
			}
			if (cluster.searchMask == 2) {
				int yDIstFromCamStart = (cluster.tileStartY - yCamPosTile) + 25;
				if (yDIstFromCamStart < 0 || yDIstFromCamStart > 50)
					continue;
				int xDistFromCamStart = (cluster.tileStartX - xCamPosTile) + 25;
				if (xDistFromCamStart < 0)
					xDistFromCamStart = 0;
				int xDistFromCamEnd = (cluster.tileEndX - xCamPosTile) + 25;
				if (xDistFromCamEnd > 50)
					xDistFromCamEnd = 50;
				boolean visible = false;
				while (xDistFromCamStart <= xDistFromCamEnd)
					if (tile_visibility_map[xDistFromCamStart++][yDIstFromCamStart]) {
						visible = true;
						break;
					}
				if (!visible)
					continue;
				int yDistFromCamStartReal = anInt457 - cluster.worldStartY;
				if (yDistFromCamStartReal > 32) {
					cluster.tileDistanceEnum = 3;
				} else {
					if (yDistFromCamStartReal >= -32)
						continue;
					cluster.tileDistanceEnum = 4;
					yDistFromCamStartReal = -yDistFromCamStartReal;
				}
				cluster.worldDistanceFromCameraStartX = (cluster.worldStartX - anInt455 << 8) / yDistFromCamStartReal;
				cluster.worldDistanceFromCameraEndX = (cluster.worldEndX - anInt455 << 8) / yDistFromCamStartReal;
				cluster.worldDistanceFromCameraStartZ = (cluster.worldStartZ - anInt456 << 8) / yDistFromCamStartReal;
				cluster.worldDistanceFromCameraEndZ = (cluster.worldEndZ - anInt456 << 8) / yDistFromCamStartReal;
				processedClusters[processedClusterPtr++] = cluster;
			} else if (cluster.searchMask == 4) {
				int yDistFromCamStartReal = cluster.worldStartZ - anInt456;
				if (yDistFromCamStartReal > 128) {
					int yDistFromCamStart = (cluster.tileStartY - yCamPosTile) + 25;
					if (yDistFromCamStart < 0)
						yDistFromCamStart = 0;
					int yDistFromCamEnd = (cluster.tileEndY - yCamPosTile) + 25;
					if (yDistFromCamEnd > 50)
						yDistFromCamEnd = 50;
					if (yDistFromCamStart <= yDistFromCamEnd) {
						int xDistFromCamStart = (cluster.tileStartX - xCamPosTile) + 25;
						if (xDistFromCamStart < 0)
							xDistFromCamStart = 0;
						int xDistFromCamEnd = (cluster.tileEndX - xCamPosTile) + 25;
						if (xDistFromCamEnd > 50)
							xDistFromCamEnd = 50;
						boolean visisble = false;
						label0: for (int _x = xDistFromCamStart; _x <= xDistFromCamEnd; _x++) {
							for (int _y = yDistFromCamStart; _y <= yDistFromCamEnd; _y++) {
								if (!tile_visibility_map[_x][_y])
									continue;
								visisble = true;
								break label0;
							}

						}

						if (visisble) {
							cluster.tileDistanceEnum = 5;
							cluster.worldDistanceFromCameraStartX = (cluster.worldStartX - anInt455 << 8) / yDistFromCamStartReal;
							cluster.worldDistanceFromCameraEndX = (cluster.worldEndX - anInt455 << 8) / yDistFromCamStartReal;
							cluster.worldDistanceFromCameraStartY = (cluster.worldStartY - anInt457 << 8) / yDistFromCamStartReal;
							cluster.worldDistanceFromCameraEndY = (cluster.worldEndY - anInt457 << 8) / yDistFromCamStartReal;
							processedClusters[processedClusterPtr++] = cluster;
						}
					}
				}
			}
		}

	}

	private boolean method320(int y, int x, int z) {
		int l = anIntArrayArrayArray445[y][x][z];
		if (l == -anInt448)
			return false;
		if (l == anInt448)
			return true;
		int i1 = x << 7;
		int j1 = z << 7;
		if (method324(i1 + 1, anIntArrayArrayArray440[y][x][z], j1 + 1) && method324((i1 + 128) - 1, anIntArrayArrayArray440[y][x + 1][z], j1 + 1) && method324((i1 + 128) - 1, anIntArrayArrayArray440[y][x + 1][z + 1], (j1 + 128) - 1) && method324(i1 + 1, anIntArrayArrayArray440[y][x][z + 1], (j1 + 128) - 1)) {
			anIntArrayArrayArray445[y][x][z] = anInt448;
			return true;
		} else {
			anIntArrayArrayArray445[y][x][z] = -anInt448;
			return false;
		}
	}

	private boolean method321(int z, int x, int y, int l) {
		if (!method320(z, x, y))
			return false;
		int i1 = x << 7;
		int j1 = y << 7;
		int k1 = anIntArrayArrayArray440[z][x][y] - 1;
		int l1 = k1 - 120;
		int i2 = k1 - 230;
		int j2 = k1 - 238;
		if (l < 16) {
			if (l == 1) {
				if (i1 > anInt455) {
					if (!method324(i1, k1, j1))
						return false;
					if (!method324(i1, k1, j1 + 128))
						return false;
				}
				if (z > 0) {
					if (!method324(i1, l1, j1))
						return false;
					if (!method324(i1, l1, j1 + 128))
						return false;
				}
				return method324(i1, i2, j1) && method324(i1, i2, j1 + 128);
			}
			if (l == 2) {
				if (j1 < anInt457) {
					if (!method324(i1, k1, j1 + 128))
						return false;
					if (!method324(i1 + 128, k1, j1 + 128))
						return false;
				}
				if (z > 0) {
					if (!method324(i1, l1, j1 + 128))
						return false;
					if (!method324(i1 + 128, l1, j1 + 128))
						return false;
				}
				return method324(i1, i2, j1 + 128) && method324(i1 + 128, i2, j1 + 128);
			}
			if (l == 4) {
				if (i1 < anInt455) {
					if (!method324(i1 + 128, k1, j1))
						return false;
					if (!method324(i1 + 128, k1, j1 + 128))
						return false;
				}
				if (z > 0) {
					if (!method324(i1 + 128, l1, j1))
						return false;
					if (!method324(i1 + 128, l1, j1 + 128))
						return false;
				}
				return method324(i1 + 128, i2, j1) && method324(i1 + 128, i2, j1 + 128);
			}
			if (l == 8) {
				if (j1 > anInt457) {
					if (!method324(i1, k1, j1))
						return false;
					if (!method324(i1 + 128, k1, j1))
						return false;
				}
				if (z > 0) {
					if (!method324(i1, l1, j1))
						return false;
					if (!method324(i1 + 128, l1, j1))
						return false;
				}
				return method324(i1, i2, j1) && method324(i1 + 128, i2, j1);
			}
		}
		if (!method324(i1 + 64, j2, j1 + 64))
			return false;
		if (l == 16)
			return method324(i1, i2, j1 + 128);
		if (l == 32)
			return method324(i1 + 128, i2, j1 + 128);
		if (l == 64)
			return method324(i1 + 128, i2, j1);
		if (l == 128) {
			return method324(i1, i2, j1);
		} else {
			System.out.println("Warning unsupported wall type");
			return true;
		}
	}

	private boolean method322(int i, int j, int k, int l) {
		if (!method320(i, j, k))
			return false;
		int i1 = j << 7;
		int j1 = k << 7;
		return method324(i1 + 1, anIntArrayArrayArray440[i][j][k] - l, j1 + 1) && method324((i1 + 128) - 1, anIntArrayArrayArray440[i][j + 1][k] - l, j1 + 1) && method324((i1 + 128) - 1, anIntArrayArrayArray440[i][j + 1][k + 1] - l, (j1 + 128) - 1) && method324(i1 + 1, anIntArrayArrayArray440[i][j][k + 1] - l, (j1 + 128) - 1);
	}

	private boolean method323(int y, int x, int k, int z, int i1, int j1) {
		if (x == k && z == i1) {
			if (!method320(y, x, z))
				return false;
			int k1 = x << 7;
			int i2 = z << 7;
			return method324(k1 + 1, anIntArrayArrayArray440[y][x][z] - j1, i2 + 1) && method324((k1 + 128) - 1, anIntArrayArrayArray440[y][x + 1][z] - j1, i2 + 1) && method324((k1 + 128) - 1, anIntArrayArrayArray440[y][x + 1][z + 1] - j1, (i2 + 128) - 1) && method324(k1 + 1, anIntArrayArrayArray440[y][x][z + 1] - j1, (i2 + 128) - 1);
		}
		for (int l1 = x; l1 <= k; l1++) {
			for (int j2 = z; j2 <= i1; j2++)
				if (anIntArrayArrayArray445[y][l1][j2] == -anInt448)
					return false;

		}

		int k2 = (x << 7) + 1;
		int l2 = (z << 7) + 2;
		int i3 = anIntArrayArrayArray440[y][x][z] - j1;
		if (!method324(k2, i3, l2))
			return false;
		int j3 = (k << 7) - 1;
		if (!method324(j3, i3, l2))
			return false;
		int k3 = (i1 << 7) - 1;
		return method324(k2, i3, k3) && method324(j3, i3, k3);
	}

	private boolean method324(int x, int y, int z) {
		for (int l = 0; l < processedClusterPtr; l++) {
			CullingCluster cluster = processedClusters[l];
			if (cluster.tileDistanceEnum == 1) {
				int i1 = cluster.worldStartX - x;
				if (i1 > 0) {
					int j2 = cluster.worldStartY + (cluster.worldDistanceFromCameraStartY * i1 >> 8);
					int k3 = cluster.worldEndY + (cluster.worldDistanceFromCameraEndY * i1 >> 8);
					int l4 = cluster.worldStartZ + (cluster.worldDistanceFromCameraStartZ * i1 >> 8);
					int i6 = cluster.worldEndZ + (cluster.worldDistanceFromCameraEndZ * i1 >> 8);
					if (z >= j2 && z <= k3 && y >= l4 && y <= i6)
						return true;
				}
			} else if (cluster.tileDistanceEnum == 2) {
				int j1 = x - cluster.worldStartX;
				if (j1 > 0) {
					int k2 = cluster.worldStartY + (cluster.worldDistanceFromCameraStartY * j1 >> 8);
					int l3 = cluster.worldEndY + (cluster.worldDistanceFromCameraEndY * j1 >> 8);
					int i5 = cluster.worldStartZ + (cluster.worldDistanceFromCameraStartZ * j1 >> 8);
					int j6 = cluster.worldEndZ + (cluster.worldDistanceFromCameraEndZ * j1 >> 8);
					if (z >= k2 && z <= l3 && y >= i5 && y <= j6)
						return true;
				}
			} else if (cluster.tileDistanceEnum == 3) {
				int k1 = cluster.worldStartY - z;
				if (k1 > 0) {
					int l2 = cluster.worldStartX + (cluster.worldDistanceFromCameraStartX * k1 >> 8);
					int i4 = cluster.worldEndX + (cluster.worldDistanceFromCameraEndX * k1 >> 8);
					int j5 = cluster.worldStartZ + (cluster.worldDistanceFromCameraStartZ * k1 >> 8);
					int k6 = cluster.worldEndZ + (cluster.worldDistanceFromCameraEndZ * k1 >> 8);
					if (x >= l2 && x <= i4 && y >= j5 && y <= k6)
						return true;
				}
			} else if (cluster.tileDistanceEnum == 4) {
				int l1 = z - cluster.worldStartY;
				if (l1 > 0) {
					int i3 = cluster.worldStartX + (cluster.worldDistanceFromCameraStartX * l1 >> 8);
					int j4 = cluster.worldEndX + (cluster.worldDistanceFromCameraEndX * l1 >> 8);
					int k5 = cluster.worldStartZ + (cluster.worldDistanceFromCameraStartZ * l1 >> 8);
					int l6 = cluster.worldEndZ + (cluster.worldDistanceFromCameraEndZ * l1 >> 8);
					if (x >= i3 && x <= j4 && y >= k5 && y <= l6)
						return true;
				}
			} else if (cluster.tileDistanceEnum == 5) {
				int i2 = y - cluster.worldStartZ;
				if (i2 > 0) {
					int j3 = cluster.worldStartX + (cluster.worldDistanceFromCameraStartX * i2 >> 8);
					int k4 = cluster.worldEndX + (cluster.worldDistanceFromCameraEndX * i2 >> 8);
					int l5 = cluster.worldStartY + (cluster.worldDistanceFromCameraStartY * i2 >> 8);
					int i7 = cluster.worldEndY + (cluster.worldDistanceFromCameraEndY * i2 >> 8);
					if (x >= j3 && x <= k4 && z >= l5 && z <= i7)
						return true;
				}
			}
		}

		return false;
	}

	public static boolean lowMem = true;
	private final int zMapSize;
	private final int xMapSize;
	private final int yMapSize;
	private final int[][][] anIntArrayArrayArray440;
	private final Tile[][][] tileArray;
	private int currentHL;
	private int amountOfInteractableObjects;
	private final InteractableObject[] interactableObjectCache;
	private final int[][][] anIntArrayArrayArray445;
	private static int anInt446;
	private static int plane__;
	private static int anInt448;
	private static int anInt449;
	private static int anInt450;
	private static int anInt451;
	private static int anInt452;
	private static int xCamPosTile;
	private static int yCamPosTile;
	private static int anInt455;
	private static int anInt456;
	private static int anInt457;
	private static int yCurveSin;
	private static int yCUrveCos;
	private static int xCurveSin;
	private static int xCurveCos;
	private static InteractableObject[] aClass28Array462 = new InteractableObject[100];
	private static final int[] faceXoffset2 = { 53, -53, -53, 53 };
	private static final int[] faceYOffset2 = { -53, -53, 53, 53 };
	private static final int[] faceXOffset3 = { -45, 45, 45, -45 };
	private static final int[] faceYOffset3 = { 45, 45, -45, -45 };
	private static boolean aBoolean467;
	private static int anInt468;
	private static int anInt469;
	public static int anInt470 = -1;
	public static int anInt471 = -1;
	private static final int amountOfCullingClusters;
	private static int[] cullingClusterPointer;
	private static CullingCluster[][] cullingClusters;
	private static int processedClusterPtr;
	private static final CullingCluster[] processedClusters = new CullingCluster[500];
	private static Deque tileDeque = new Deque();
	private static final int[] anIntArray478 = { 19, 55, 38, 155, 255, 110, 137, 205, 76 };
	private static final int[] anIntArray479 = { 160, 192, 80, 96, 0, 144, 80, 48, 160 };
	private static final int[] anIntArray480 = { 76, 8, 137, 4, 0, 1, 38, 2, 19 };
	private static final int[] anIntArray481 = { 0, 0, 2, 0, 0, 2, 1, 1, 0 };
	private static final int[] anIntArray482 = { 2, 0, 0, 2, 0, 0, 0, 4, 4 };
	private static final int[] anIntArray483 = { 0, 4, 4, 8, 0, 0, 8, 0, 0 };
	private static final int[] anIntArray484 = { 1, 1, 0, 0, 0, 8, 0, 0, 8 };
	private static final int[] anIntArray485 = { 41, 39248, 41, 4643, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 43086, 41, 41, 41, 41, 41, 41, 41, 8602, 41, 28992, 41, 41, 41, 41, 41, 5056, 41, 41, 41, 7079, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 3131, 41, 41, 41 };
	private final int[] anIntArray486;
	private final int[] anIntArray487;
	private int anInt488;
	private final int[][] tileShapePoints = { new int[16], { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1 }, { 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0 }, { 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1 }, { 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0 }, { 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1 }, { 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1 } };
	private final int[][] tileShapeIndices = { { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 }, { 12, 8, 4, 0, 13, 9, 5, 1, 14, 10, 6, 2, 15, 11, 7, 3 }, { 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 }, { 3, 7, 11, 15, 2, 6, 10, 14, 1, 5, 9, 13, 0, 4, 8, 12 } };
	private static boolean[][][][] tile_visibility_maps = new boolean[8][32][51][51];
	private static boolean[][] tile_visibility_map;
	private static int midX;
	private static int midY;
	private static int left;
	private static int top;
	private static int right;
	private static int bottom;
	public static int focalLength;

	static {
		focalLength = 512;
		amountOfCullingClusters = 4;
		cullingClusterPointer = new int[amountOfCullingClusters];
		cullingClusters = new CullingCluster[amountOfCullingClusters][500];
	}
}
