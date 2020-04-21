package com.varrock.client;

import com.varrock.client.cache.DataType;

@SuppressWarnings("all")
public final class FrameReader {

	public static void initialise(int i) {
		animationListRegular = new FrameReader[20000][0];
		animationListOldschool = new FrameReader[20000][0];
	}

	public static void load(int file, byte[] fileData, DataType dataType) {
		try {
			Stream stream = new Stream(fileData);
			SkinList skinList = new SkinList(stream, 0);
			int k1 = stream.readUnsignedWord();

			if(dataType == DataType.OLDSCHOOL) {
				animationListOldschool[file] = new FrameReader[(int) (k1 * 10)];
			} else {
				animationListRegular[file] = new FrameReader[(int) (k1 * 10)];
			}
			
			int ai[] = new int[500];
			int ai1[] = new int[500];
			int ai2[] = new int[500];
			int ai3[] = new int[500];
			for (int l1 = 0; l1 < k1; l1++) {
				int i2 = stream.readUnsignedWord();
				
				FrameReader frameReader;
				if(dataType == DataType.OLDSCHOOL) {
					frameReader = animationListOldschool[file][i2] = new FrameReader();
				} else {
					frameReader = animationListRegular[file][i2] = new FrameReader();
				}
				
				frameReader.mySkinList = skinList;
				int j2 = stream.readUnsignedByte();
				int l2 = 0;
				int k2 = -1;
				for (int i3 = 0; i3 < j2; i3++) {
					int j3 = stream.readUnsignedByte();

					if (j3 > 0) {
						if (skinList.opcodes[i3] != 0) {
							for (int l3 = i3 - 1; l3 > k2; l3--) {
								if (skinList.opcodes[l3] != 0)
									continue;
								ai[l2] = l3;
								ai1[l2] = 0;
								ai2[l2] = 0;
								ai3[l2] = 0;
								l2++;
								break;
							}

						}

						ai[l2] = i3;

						int c = 0;
						if (skinList.opcodes[i3] == 3)
							c = 128;

						if ((j3 & 0x1) != 0x0)
							ai1[l2] = stream.readShort2();
						else
							ai1[l2] = c;

						if ((j3 & 0x2) != 0x0)
							ai2[l2] = stream.readShort2();
						else
							ai2[l2] = c;

						if ((j3 & 0x4) != 0x0)
							ai3[l2] = stream.readShort2();
						else {
							ai3[l2] = c;
						}
						if (skinList.opcodes[i3] == 2) {
							ai1[l2] = ((ai1[l2] & 0xff) << 3) + (ai1[l2] >> 8 & 0x7);
							ai2[l2] = ((ai2[l2] & 0xff) << 3) + (ai2[l2] >> 8 & 0x7);
							ai3[l2] = ((ai3[l2] & 0xff) << 3) + (ai3[l2] >> 8 & 0x7);
						}
						k2 = i3;
						l2++;
					}
				}

				frameReader.stepCount = l2;
				frameReader.opCodeLinkTable = new int[l2];
				frameReader.xOffset = new int[l2];
				frameReader.yOffset = new int[l2];
				frameReader.zOffset = new int[l2];
				for (int k3 = 0; k3 < l2; k3++) {
					frameReader.opCodeLinkTable[k3] = ai[k3];
					frameReader.xOffset[k3] = ai1[k3];
					frameReader.yOffset[k3] = ai2[k3];
					frameReader.zOffset[k3] = ai3[k3];
				}

			}
		} catch (Exception exception) {
		}
	}

	public static void nullLoader() {
		animationListRegular = null;
		animationListOldschool = null;
	}

	public static FrameReader forID(int int1, DataType dataType) {
		try {
			int int2 = int1 >> 16;
			int1 = int1 & 0xffff;

			if(dataType == DataType.OLDSCHOOL) {
				if (animationListOldschool[int2].length == 0 || animationListOldschool[int2].length < int1) {
					Client.instance.onDemandFetcher.requestFileData(Client.OSRS_ANIM_IDX - 1, int2);
					return null;
				}
				return animationListOldschool[int2][int1];
			} else {
				if (animationListRegular[int2].length == 0 || animationListRegular[int2].length < int1) {
					Client.instance.onDemandFetcher.requestFileData(Client.ANIM_IDX - 1, int2);
					return null;
				}
				return animationListRegular[int2][int1];
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static FrameReader getTween(FrameReader f1, FrameReader f2) {
		FrameReader newFrame = new FrameReader();
		newFrame.displayLength = f1.displayLength;
		newFrame.stepCount = f1.stepCount;
		newFrame.opCodeLinkTable = f1.opCodeLinkTable;
		newFrame.aBooleanArray643 = f1.aBooleanArray643;
		newFrame.xOffset = new int[f1.xOffset.length];
		newFrame.mySkinList = f1.mySkinList;
		for (int i = 0; i < f1.xOffset.length; i++) {
			try {
				int middleXOffset = (f2.xOffset[i] - f1.xOffset[i]) / 2 + f1.xOffset[i];
				newFrame.xOffset[i] = middleXOffset;
			} catch (Exception e) {
				newFrame.xOffset[i] = f1.xOffset[i];
			}
		}
		newFrame.yOffset = new int[f1.yOffset.length];
		for (int i = 0; i < f1.yOffset.length; i++) {
			try {
				int middleYOffset = (f2.yOffset[i] - f1.yOffset[i]) / 2 + f1.yOffset[i];
				newFrame.yOffset[i] = middleYOffset;
			} catch (Exception e) {
				newFrame.yOffset[i] = f1.yOffset[i];
			}

		}
		newFrame.zOffset = new int[f1.zOffset.length];
		for (int i = 0; i < f1.zOffset.length; i++) {
			try {
				int middleZOffset = (f2.zOffset[i] - f1.zOffset[i]) / 2 + f1.zOffset[i];
				newFrame.zOffset[i] = middleZOffset;

			} catch (Exception e) {
				newFrame.zOffset[i] = f1.zOffset[i];
			}

		}
		return newFrame;
	}

	public static boolean isNullFrame(int frame) {
		return frame == -1;
	}

	private FrameReader() {
	}

	public static FrameReader animationListRegular[][];
	public static FrameReader animationListOldschool[][];
	public int displayLength;
	public SkinList mySkinList;
	public int stepCount;
	public static Client instance;
	public int opCodeLinkTable[];
	public int xOffset[];
	public int yOffset[];
	public int zOffset[];
	public static boolean[] aBooleanArray643;

}
