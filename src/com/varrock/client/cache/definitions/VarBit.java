package com.varrock.client.cache.definitions;


import com.varrock.client.CacheArchive;
import com.varrock.client.FileOperations;
import com.varrock.client.Stream;
import com.varrock.client.signlink;

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
