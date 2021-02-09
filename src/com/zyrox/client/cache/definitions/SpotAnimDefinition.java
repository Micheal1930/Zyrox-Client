package com.zyrox.client.cache.definitions;

import com.zyrox.client.*;
import com.zyrox.client.cache.DataType;

public final class SpotAnimDefinition {
	
	private static Stream streamOSRS;

	public static void unpackConfig(CacheArchive streamLoader) {
		Stream stream = new Stream(streamLoader.getDataForName("spotanim.dat"));
		streamOSRS = new Stream(streamLoader.getDataForName("spotanim3.dat"));

		//FileOperations.WriteFile(signlink.findcachedir() + "spotanim3.dat", streamLoader.getDataForName("spotanim3.dat"));

		int length = stream.readUnsignedWord();
		
		int lengthOSRS = streamOSRS.readUnsignedWord();
		
		if (cache == null) {
			cache = new SpotAnimDefinition[length + lengthOSRS];
		}
		
		for (int j = 0; j < length + lengthOSRS; j++) {
			if (cache[j] == null) {
				cache[j] = new SpotAnimDefinition();
			}
			
			if (j >= length) {
				cache[j].dataType = DataType.OLDSCHOOL;
			}
			
			cache[j].id = j;
			cache[j].readValues(cache[j].dataType == DataType.OLDSCHOOL ? streamOSRS : stream);
		}
		
		custom();
	}

	private static void custom() {
		cache[2274].modelId = cache[2281].modelId;
		cache[2274].animationId = cache[2281].animationId;
		cache[2274].rotation = 90;
		cache[2274].animation = cache[2281].animation;

		System.out.println("test: "+cache[1120].modelId);

	}

	private void readValues(Stream stream) {
		do {
			int i = stream.readUnsignedByte();
			if (i == 0)
				return;
			if (i == 1)
				modelId = stream.readUnsignedWord();
			else if (i == 2) {
				animationId = stream.readUnsignedWord();
				
				if (dataType == DataType.OLDSCHOOL) {
					animationId += Animation.OSRS_ANIM_OFFSET;
				}
				
				if (Animation.anims != null) {
					animation = Animation.anims[animationId];
				}
			} else if (i == 4)
				sizeXY = stream.readUnsignedWord();
			else if (i == 5)
				sizeZ = stream.readUnsignedWord();
			else if (i == 6)
				rotation = stream.readUnsignedWord();
			else if (i == 7)
				shadow = stream.readUnsignedByte();
			else if (i == 8)
				lightness = stream.readUnsignedByte();
			else if (i == 40) {
				int j = stream.readUnsignedByte();
				for (int k = 0; k < j; k++) {
					originalColours[k] = stream.readUnsignedWord();
					destColours[k] = stream.readUnsignedWord();
				}
			} else if (i == 41) {
				int length = stream.readUnsignedByte();
				int[] retextureToFind = new int[length];
				int[] retextureToReplace = new int[length];
				for (int index = 0; index < length; ++index) {
					retextureToFind[index] = stream.readUnsignedWord();
					retextureToReplace[index] = stream.readUnsignedWord();
				}
			} else
				System.out.println("Error unrecognised spotanim config code: "
						+ i);
		} while (true);
	}

	public Model getModel() {
		Model model = (Model) modelCache.get(id);
		if (model != null)
			return model;
		model = Model.fetchModel(modelId, dataType);
		if (model == null)
			return null;
		for (int i = 0; i < 6; i++)
			if (originalColours[0] != 0)
				model.recolour(originalColours[i], destColours[i]);
		modelCache.put(model, id);
		return model;
	}

	private SpotAnimDefinition() {
		anInt400 = 9;
		animationId = -1;
		originalColours = new int[6];
		destColours = new int[6];
		sizeXY = 128;
		sizeZ = 128;
	}

	public int anInt400;
	public static SpotAnimDefinition cache[];
	private int id;
	private int modelId;
	private int animationId;
	public Animation animation;
	private final int[] originalColours;
	private final int[] destColours;
	public int sizeXY;
	public int sizeZ;
	public int rotation;
	public int shadow;
	public int lightness;
	public DataType dataType;
	public static MemCache modelCache = new MemCache(30);

}