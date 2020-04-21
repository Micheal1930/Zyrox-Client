package com.varrock.client;


public final class SkinList {

	public SkinList(Stream stream, int junk) {
		int amt = stream.readUnsignedWord();
		opcodes = new int[amt];
		skinList = new int[amt][];
		for (int j = 0; j < amt; j++)
			opcodes[j] = stream.readUnsignedWord();

		for (int j = 0; j < amt; j++)
			skinList[j] = new int[stream.readUnsignedWord()];

		for (int j = 0; j < amt; j++)
			for (int l = 0; l < skinList[j].length; l++)
				skinList[j][l] = stream.readUnsignedWord();
	}

	public final int[] opcodes;
	public final int[][] skinList;
}
