package com.zyrox.client.cache.definitions;

import com.zyrox.client.CacheArchive;
import com.zyrox.client.FileOperations;
import com.zyrox.client.Stream;
import com.zyrox.client.signlink;

@SuppressWarnings("all")
public final class Varp {

	public static void unpackConfig(CacheArchive streamLoader) {
		Stream stream = new Stream(FileOperations.readFile(signlink.findcachedir() + "varp.dat"));
		cacheSize = 0;
		int cacheSize = stream.readUnsignedWord();
		if (cache == null)
			cache = new Varp[cacheSize];
		if (idLinkTable == null)
			idLinkTable = new int[cacheSize];
		for (int j = 0; j < cacheSize; j++) {
			if (cache[j] == null)
				cache[j] = new Varp();
			cache[j].readValues(stream, j);
		}
		if (stream.currentOffset != stream.buffer.length)
			System.out.println("varptype load mismatch");
	}

	private void readValues(Stream stream, int i) {
		do {
			int j = stream.readUnsignedByte();
			if (j == 0)
				return;
			int dummy;
			if (j == 1)
				stream.readUnsignedByte();
			else if (j == 2)
				stream.readUnsignedByte();
			else if (j == 3)
				idLinkTable[cacheSize++] = i;
			else if (j == 5)
				usage = stream.readUnsignedWord();
			else if (j == 7)
				stream.getIntLittleEndian();
			else if (j == 8) {
			} else if (j == 10)
				stream.readString();
			else if (j == 12)
				stream.getIntLittleEndian();
			else
				System.out.println("Error unrecognised config code: " + j);
		} while (true);
	}

	private Varp() {
	}

	public static Varp cache[];
	private static int cacheSize;
	private static int[] idLinkTable;
	public int usage;

}
