package com.zyrox.client.cache.definitions;


import com.zyrox.client.CacheArchive;
import com.zyrox.client.FileOperations;
import com.zyrox.client.Stream;
import com.zyrox.client.signlink;

public final class VarBit {

	public static void unpackConfig(CacheArchive streamLoader) {
		Stream stream = new Stream(FileOperations.readFile(signlink.findcachedir() + "varbit.dat"));
		int cacheSize = stream.readUnsignedWord();
		if (cache == null)
			cache = new VarBit[cacheSize];
		for (int j = 0; j < cacheSize; j++) {
			if (cache[j] == null)
				cache[j] = new VarBit();
			cache[j].readValues(stream);
		}

		if (stream.currentOffset != stream.buffer.length)
			System.out.println("varbit load mismatch");
	}

	private void readValues(Stream stream) {
		configId = stream.readUnsignedWord();
		leastSignificantBit = stream.readUnsignedByte();
		mostSignificantBit = stream.readUnsignedByte();
	}

	private VarBit() {
	}

	public static VarBit cache[];
	public int configId;
	public int leastSignificantBit;
	public int mostSignificantBit;
	boolean aBoolean651;
}
