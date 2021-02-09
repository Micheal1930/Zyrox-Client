package com.zyrox.client;



import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class RSFontSystem extends DrawingArea {

	public int baseCharacterHeight = 0;
	public int anInt4142;
	public int anInt4144;
	public int[] characterDrawYOffsets;
	public int[] characterHeights;
	public int[] characterDrawXOffsets;
	public int[] characterWidths;
	public int[] iconWidths;
	public byte[] aByteArray4151;
	public byte[][] fontPixels;
	public int[] characterScreenWidths;
	public static String aRSString_4135;
	public static String startTransparency;
	public static String startDefaultShadow;
	public static String endShadow = "/shad";
	public static String endEffect;
	public static String aRSString_4143;
	public static String endStrikethrough = "/str";
	public static String aRSString_4147;
	public static String startColor;
	public static String lineBreak;
	public static String startStrikethrough;
	public static String endColor;
	public static String startImage;
	public static String endUnderline;
	public static String defaultStrikethrough;
	public static String startShadow;
	public static String startEffect;
	public static String aRSString_4162;
	public static String aRSString_4163;
	public static String endTransparency;
	public static String aRSString_4165;
	public static String startUnderline;
	public static String startDefaultUnderline;
	public static String aRSString_4169;
	public static String[] splitTextStrings;
	public static int defaultColor;
	public static int textShadowColor;
	public static int strikethroughColor;
	public static int defaultTransparency;
	public static int anInt4175;
	public static int underlineColor;
	public static int defaultShadow;
	public static int anInt4178;
	public static int transparency;
	public static int textColor;

	public static boolean SMILIES_TOGGLED = true;

	public RSFontSystem(boolean TypeFont, String s, CacheArchive archive) {
		try {
			int length = (s.equals("regularhit") || s.equals("bighit")) ? 58 : 256;
			fontPixels = new byte[length][];
			characterWidths = new int[length];
			characterHeights = new int[length];
			characterDrawXOffsets = new int[length];
			characterDrawYOffsets = new int[length];
			characterScreenWidths = new int[length];
			Stream stream = new Stream(archive.getDataForName(s + ".dat"));
			Stream stream_1 = new Stream(archive.getDataForName("index.dat"));
			stream_1.currentOffset = stream.readShort() + 4;
			int k = stream_1.readUByte();
			if (k > 0) {
				stream_1.currentOffset += 3 * (k - 1);
			}
			for (int l = 0; l < length; l++) {
				characterDrawXOffsets[l] = stream_1.readUByte();
				characterDrawYOffsets[l] = stream_1.readUByte();
				int i1 = characterWidths[l] = stream_1.readShort();
				int j1 = characterHeights[l] = stream_1.readShort();
				int k1 = stream_1.readUByte();
				int l1 = i1 * j1;
				fontPixels[l] = new byte[l1];
				if (k1 == 0) {
					for (int i2 = 0; i2 < l1; i2++) {
						fontPixels[l][i2] = stream.readByte();
					}

				} else if (k1 == 1) {
					for (int j2 = 0; j2 < i1; j2++) {
						for (int l2 = 0; l2 < j1; l2++) {
							fontPixels[l][j2 + l2 * i1] = stream.readByte();
						}

					}

				}
				if (j1 > baseCharacterHeight && l < 128) {
					baseCharacterHeight = j1;
				}
				characterDrawXOffsets[l] = 1;
				characterScreenWidths[l] = i1 + 2;
				int k2 = 0;
				for (int i3 = j1 / 7; i3 < j1; i3++) {
					k2 += fontPixels[l][i3 * i1];
				}

				if (k2 <= j1 / 7) {
					characterScreenWidths[l]--;
					characterDrawXOffsets[l] = 0;
				}
				k2 = 0;
				for (int j3 = j1 / 7; j3 < j1; j3++) {
					k2 += fontPixels[l][(i1 - 1) + j3 * i1];
				}

				if (k2 <= j1 / 7) {
					characterScreenWidths[l]--;
				}
			}

			if (TypeFont) {
				characterScreenWidths[32] = characterScreenWidths[73];
			} else {
				characterScreenWidths[32] = characterScreenWidths[105];
			}
		} catch (Exception e) {
		}
	}

	public void drawStringMoveY(String string, int drawX, int drawY, int color, int shadow, int randomMod, int randomMod2) {
		if (string != null) {
			setColorAndShadow(color, shadow);
			double d = 7.0 - (double) randomMod2 / 8.0;
			if (d < 0.0) {
				d = 0.0;
			}
			int[] yOffset = new int[string.length()];
			for (int index = 0; index < string.length(); index++) {
				yOffset[index] = (int) (Math.sin((double) index / 1.5 + (double) randomMod) * d);
			}
			drawBaseStringMoveXY(string, drawX - getTextWidth(string) / 2, drawY, null, yOffset);
		}
	}

	public int getCharacterWidth(int i) {
		return characterScreenWidths[i & 0xff];
	}

	public void setDefaultTextEffectValues(int color, int shadow, int trans) {
		strikethroughColor = -1;
		underlineColor = -1;
		textShadowColor = defaultShadow = shadow;
		textColor = defaultColor = color;
		transparency = defaultTransparency = trans;
		anInt4178 = 0;
		anInt4175 = 0;
	}

	public void drawCenteredStringMoveXY(String string, int drawX, int drawY, int color, int shadow, int randomMod) {
		if (string != null) {
			setColorAndShadow(color, shadow);
			int[] xMods = new int[string.length()];
			int[] yMods = new int[string.length()];
			for (int index = 0; index < string.length(); index++) {
				xMods[index] = (int) (Math.sin((double) index / 5.0 + (double) randomMod / 5.0) * 5.0);
				yMods[index] = (int) (Math.sin((double) index / 3.0 + (double) randomMod / 5.0) * 5.0);
			}
			drawBaseStringMoveXY(string, drawX - getTextWidth(string) / 2, drawY, xMods, yMods);
		}
	}

	public void drawCenteredStringMoveY(String class100, int drawX, int drawY, int color, int shadow, int i_54_) {
		if (class100 != null) {
			setColorAndShadow(color, shadow);
			int[] yOffset = new int[class100.length()];
			for (int index = 0; index < class100.length(); index++) {
				yOffset[index] = (int) (Math.sin((double) index / 2.0 + (double) i_54_ / 5.0) * 5.0);
			}
			drawBaseStringMoveXY(class100, drawX - getTextWidth(class100) / 2, drawY, null, yOffset);
		}
	}
	public void drawBasicString(String string, int drawX, int drawY) {
		drawY -= baseCharacterHeight;
		int startIndex = -1;
		//string = handleOldSyntax(string);
		for (int currentCharacter = 0; currentCharacter < string.length(); currentCharacter++) {
			int character = string.charAt(currentCharacter);
			if (character > 255) {
				character = 32;
			}
			if (character == 60) {
				startIndex = currentCharacter;
			} else {
				if (character == 62 && startIndex != -1) {
					String effectString = string.substring(startIndex + 1, currentCharacter);
					startIndex = -1;
					if (effectString.equals(startEffect)) {
						character = 60;
					} else if (effectString.equals(endEffect)) {
						character = 62;
					} else if (effectString.equals(aRSString_4135)) {
						character = 160;
					} else if (effectString.equals(aRSString_4162)) {
						character = 173;
					} else if (effectString.equals(aRSString_4165)) {
						character = 215;
					} else if (effectString.equals(aRSString_4147)) {
						character = 128;
					} else if (effectString.equals(aRSString_4163)) {
						character = 169;
					} else if (effectString.equals(aRSString_4169)) {
						character = 174;
					} else {
						if (effectString.startsWith(startImage)) {
							try {
								int imageId = Integer.valueOf(effectString.substring(4));
								Sprite icon = SpriteLoader.sprites[679+imageId];

								int iconModY = icon.myHeight;
								int offsetX = 0;
								if(679+imageId >= 1410 && 679+imageId <= 1413) {
									iconModY += 2;
									offsetX += 2;
								}

								if(imageId >= 8)
									iconModY-= 2;
								if (transparency == 256) {
									icon.drawAdvancedSprite(drawX, (drawY + baseCharacterHeight - iconModY));
								} else {
									icon.drawAdvancedSprite(drawX, (drawY + baseCharacterHeight - iconModY), transparency);
								}
								drawX += icon.myWidth + offsetX;
							} catch (Exception exception) {
								/* empty */
							}
						} else {
							setTextEffects(effectString);
						}
						continue;
					}
				}
				if (startIndex == -1) {
					int width = characterWidths[character];
					int height = characterHeights[character];
					if (character != 32) {
						if (transparency == 256) {
							if (textShadowColor != -1) {
								drawCharacter(character, drawX + characterDrawXOffsets[character] + 1, drawY + characterDrawYOffsets[character] + 1, width, height, textShadowColor, true);
							}
							drawCharacter(character, drawX + characterDrawXOffsets[character], drawY + characterDrawYOffsets[character], width, height, textColor, false);
						} else {
							if (textShadowColor != -1) {
								drawTransparentCharacter(character, drawX + characterDrawXOffsets[character] + 1, drawY + characterDrawYOffsets[character] + 1, width, height, textShadowColor, transparency, true);
							}
							drawTransparentCharacter(character, drawX + characterDrawXOffsets[character], drawY + characterDrawYOffsets[character], width, height, textColor, transparency, false);
						}
					} else if (anInt4178 > 0) {
						anInt4175 += anInt4178;
						drawX += anInt4175 >> 8;
			anInt4175 &= 0xff;
					}
					int lineWidth = characterScreenWidths[character];
					if (strikethroughColor != -1) {
						DrawingArea474.drawHorizontalLine(drawX, drawY + (int) ((double) baseCharacterHeight * 0.69999999999999996D), lineWidth, strikethroughColor);
					}
					if (underlineColor != -1) {
						DrawingArea474.drawHorizontalLine(drawX, drawY + baseCharacterHeight, lineWidth, underlineColor);
					}
					drawX += lineWidth;
				}
			}
		}
	}
	
	public void drawBasicString2(String string, int drawX, int drawY) {
		drawY -= baseCharacterHeight;
		//string = handleOldSyntax(string);
		for (int currentCharacter = 0; currentCharacter < string.length(); currentCharacter++) {
			int character = string.charAt(currentCharacter);
			if (character > 255) {
				character = 32;
			}
			int width = characterWidths[character];
			int height = characterHeights[character];
			if (character != 32) {
				if (transparency == 256) {
					if (textShadowColor != -1) {
						drawCharacter(character, drawX + characterDrawXOffsets[character] + 1, drawY + characterDrawYOffsets[character] + 1, width, height, textShadowColor, true);
					}
					drawCharacter(character, drawX + characterDrawXOffsets[character], drawY + characterDrawYOffsets[character], width, height, textColor, false);
				} else {
					if (textShadowColor != -1) {
						drawTransparentCharacter(character, drawX + characterDrawXOffsets[character] + 1, drawY + characterDrawYOffsets[character] + 1, width, height, textShadowColor, transparency, true);
					}
					drawTransparentCharacter(character, drawX + characterDrawXOffsets[character], drawY + characterDrawYOffsets[character], width, height, textColor, transparency, false);
				}
			} else if (anInt4178 > 0) {
				anInt4175 += anInt4178;
				drawX += anInt4175 >> 8;
	anInt4175 &= 0xff;
			}
			int lineWidth = characterScreenWidths[character];
			if (strikethroughColor != -1) {
				DrawingArea474.drawHorizontalLine(drawX, drawY + (int) ((double) baseCharacterHeight * 0.69999999999999996D), lineWidth, strikethroughColor);
			}
			if (underlineColor != -1) {
				DrawingArea474.drawHorizontalLine(drawX, drawY + baseCharacterHeight, lineWidth, underlineColor);
			}
			drawX += lineWidth;
		}
	}

	public void drawRAString(String string, int drawX, int drawY, int color, int shadow) {
		if (string != null) {
			setColorAndShadow(color, shadow);
			drawBasicString(string, drawX - getTextWidth(string), drawY);
		}
	}

	public void drawBaseStringMoveXY(String string, int drawX, int drawY, int[] xModifier, int[] yModifier) {
		drawY -= baseCharacterHeight;
		int startIndex = -1;
		int modifierOffset = 0;
		for (int currentCharacter = 0; currentCharacter < string.length(); currentCharacter++) {
			int character = string.charAt(currentCharacter);
			if (character == 60) {
				startIndex = currentCharacter;
			} else {
				if (character == 62 && startIndex != -1) {
					String effectString = string.substring(startIndex + 1, currentCharacter);
					startIndex = -1;
					if (effectString.equals(startEffect)) {
						character = 60;
					} else if (effectString.equals(endEffect)) {
						character = 62;
					} else if (effectString.equals(aRSString_4135)) {
						character = 160;
					} else if (effectString.equals(aRSString_4162)) {
						character = 173;
					} else if (effectString.equals(aRSString_4165)) {
						character = 215;
					} else if (effectString.equals(aRSString_4147)) {
						character = 128;
					} else if (effectString.equals(aRSString_4163)) {
						character = 169;
					} else if (effectString.equals(aRSString_4169)) {
						character = 174;
					} else {
						if (effectString.startsWith(startImage)) {
							try {
								int xModI;
								if (xModifier != null) {
									xModI = xModifier[modifierOffset];
								} else {
									xModI = 0;
								}
								int yMod;
								if (yModifier != null) {
									yMod = yModifier[modifierOffset];
								} else {
									yMod = 0;
								}
								modifierOffset++;
								int iconId = Integer.valueOf(effectString.substring(4));
								Sprite icon = SpriteLoader.sprites[679+iconId];
								int iconOffsetY = icon.myHeight;
								if (transparency == 256) {
									icon.drawAdvancedSprite(drawX + xModI, (drawY + baseCharacterHeight - iconOffsetY + yMod));
								} else {
									icon.drawAdvancedSprite(drawX + xModI, (drawY + baseCharacterHeight - iconOffsetY + yMod), transparency);
								}
								drawX += icon.myWidth;
							} catch (Exception exception) {
								/* empty */
							}
						} else {
							setTextEffects(effectString);
						}
						continue;
					}
				}
				if (startIndex == -1) {
					int width = characterWidths[character];
					int height = characterHeights[character];
					int xOff;
					if (xModifier != null) {
						xOff = xModifier[modifierOffset];
					} else {
						xOff = 0;
					}
					int yOff;
					if (yModifier != null) {
						yOff = yModifier[modifierOffset];
					} else {
						yOff = 0;
					}
					modifierOffset++;
					if (character != 32) {
						if (transparency == 256) {
							if (textShadowColor != -1) {
								drawCharacter(character, (drawX + characterDrawXOffsets[character] + 1 + xOff), (drawY + characterDrawYOffsets[character] + 1 + yOff), width, height, textShadowColor, true);
							}
							drawCharacter(character, drawX + characterDrawXOffsets[character] + xOff, drawY + characterDrawYOffsets[character] + yOff, width, height, textColor, false);
						} else {
							if (textShadowColor != -1) {
								drawTransparentCharacter(character, (drawX + characterDrawXOffsets[character] + 1 + xOff), (drawY + characterDrawYOffsets[character] + 1 + yOff), width, height, textShadowColor, transparency, true);
							}
							drawTransparentCharacter(character, drawX + characterDrawXOffsets[character] + xOff, drawY + characterDrawYOffsets[character] + yOff, width, height, textColor, transparency, false);
						}
					} else if (anInt4178 > 0) {
						anInt4175 += anInt4178;
						drawX += anInt4175 >> 8;
				anInt4175 &= 0xff;
					}
					int i_109_ = characterScreenWidths[character];
					if (strikethroughColor != -1) {
						DrawingArea474.drawHorizontalLine(drawX, drawY + (int) ((double) baseCharacterHeight * 0.7), i_109_, strikethroughColor);
					}
					if (underlineColor != -1) {
						DrawingArea474.drawHorizontalLine(drawX, drawY + baseCharacterHeight, i_109_, underlineColor);
					}
					drawX += i_109_;
				}
			}
		}
	}

	public void setTextEffects(String string) {
		do {
			try {
				if (string.startsWith(startColor)) {
					String color = string.substring(4);
					textColor = color.length() < 6 ? Color.decode(color).getRGB() : Integer.parseInt(color, 16);
				} else if (string.equals(endColor)) {
					textColor = defaultColor;
				} else if (string.startsWith(startTransparency)) {
					transparency = Integer.valueOf(string.substring(6));
				} else if (string.equals(endTransparency)) {
					transparency = defaultTransparency;
				} else if (string.startsWith(startStrikethrough)) {
					String color = string.substring(4);
					strikethroughColor = color.length() < 6 ? Color.decode(color).getRGB() : Integer.parseInt(color, 16);
				} else if (string.equals(defaultStrikethrough)) {
					strikethroughColor = 8388608;
				} else if (string.equals(endStrikethrough)) {
					strikethroughColor = -1;
				} else if (string.startsWith(startUnderline)) {
					String color = string.substring(2);
					underlineColor = color.length() < 6 ? Color.decode(color).getRGB() : Integer.parseInt(color, 16);
				} else if (string.equals(startDefaultUnderline)) {
					underlineColor = 0;
				} else if (string.equals(endUnderline)) {
					underlineColor = -1;
				} else if (string.startsWith(startShadow)) {
					String color = string.substring(5);
					textShadowColor = color.length() < 6 ? Color.decode(color).getRGB() : Integer.parseInt(color, 16);
				} else if (string.equals(startDefaultShadow)) {
					textShadowColor = 0;
				} else if (string.equals(endShadow)) {
					textShadowColor = defaultShadow;
				} else {
					if (!string.equals(lineBreak)) {
						break;
					}
					setDefaultTextEffectValues(defaultColor, defaultShadow, defaultTransparency);
				}
			} catch (Exception exception) {
				break;
			}
			break;
		} while (false);
	}

	public void setColorAndShadow(int color, int shadow) {
		strikethroughColor = -1;
		underlineColor = -1;
		textShadowColor = defaultShadow = shadow;
		textColor = defaultColor = color;
		transparency = defaultTransparency = 256;
		anInt4178 = 0;
		anInt4175 = 0;
	}
	
	public void setColorAndShadowAndTrans(int color, int shadow, int trans) {
		strikethroughColor = -1;
		underlineColor = -1;
		textShadowColor = defaultShadow = shadow;
		textColor = defaultColor = color;
		transparency = defaultTransparency = trans;
		anInt4178 = 0;
		anInt4175 = 0;
	}

	public int getTextWidth(String string) {
		if (string == null) {
			return 0;
		}
		int startIndex = -1;
		int finalWidth = 0;
		for (int currentCharacter = 0; currentCharacter < string.length(); currentCharacter++) {
			int character = string.charAt(currentCharacter);
			if (character > 255) {
				character = 32;
			}
			if (character == 60) {
				startIndex = currentCharacter;
			} else {
				if (character == 62 && startIndex != -1) {
					String effectString = string.substring(startIndex + 1, currentCharacter);
					startIndex = -1;
					if (effectString.equals(startEffect)) {
						character = 60;
					} else if (effectString.equals(endEffect)) {
						character = 62;
					} else if (effectString.equals(aRSString_4135)) {
						character = 160;
					} else if (effectString.equals(aRSString_4162)) {
						character = 173;
					} else if (effectString.equals(aRSString_4165)) {
						character = 215;
					} else if (effectString.equals(aRSString_4147)) {
						character = 128;
					} else if (effectString.equals(aRSString_4163)) {
						character = 169;
					} else if (effectString.equals(aRSString_4169)) {
						character = 174;
					} else {
						if (effectString.startsWith(startImage)) {
							try {// <img=
								int iconId = Integer.valueOf(effectString.substring(4));
								finalWidth += SpriteLoader.sprites[679+iconId].myWidth;
							} catch (Exception exception) {
								/* empty */
							}
						}
						continue;
					}
				}
				if (startIndex == -1) {
					finalWidth += characterScreenWidths[character];
				}
			}
		}
		return finalWidth;
	}

	public void drawBasicString(String string, int drawX, int drawY, int color, int shadow) {
		if (string != null) {
			setColorAndShadow(color, shadow);
			drawBasicString(string, drawX, drawY);
		}
	}
	
	public void drawBasicString(String string, int drawX, int drawY, int color, int shadow, int trans) {
		if (string != null) {
			setColorAndShadowAndTrans(color, shadow, trans);
			drawBasicString(string, drawX, drawY);
		}
	}
	
	public void drawBasicString2(String string, int drawX, int drawY, int color, int shadow) {
		if (string != null) {
			setColorAndShadow(color, shadow);
			drawBasicString2(string, drawX, drawY);
		}
	}

	public void drawCenteredString(String string, int drawX, int drawY, int color, int shadow, boolean showCharSixty) {
		if (string != null) {
			setColorAndShadow(color, shadow);
			string = handleOldSyntax(string);
			drawBasicString(string, drawX - getTextWidth(string) / 2, drawY, showCharSixty);
		}
	}

	public void drawCenteredString(String string, int drawX, int drawY, int color, int shadow, int trans) {
		if (transparency < 0 || transparency > 256) {
			transparency = defaultTransparency;
		}
		if (string != null) {
			setColorAndShadow(color, shadow);
			string = handleOldSyntax(string);
			transparency = trans;
			drawBasicString(string, drawX - getTextWidth(string) / 2, drawY);
		}
	}

	public void drawBasicString(String string, int drawX, int drawY, boolean showCharSixty) {
		drawY -= baseCharacterHeight;
		int startIndex = -1;
		string = handleOldSyntax(string);
		for (int currentCharacter = 0; currentCharacter < string.length(); currentCharacter++) {
			int character = string.charAt(currentCharacter);
			if (character > 255) {
				character = 32;
			}
			if (character == 60 && !showCharSixty) {
				startIndex = currentCharacter;
			} else {
				if (character == 62 && startIndex != -1) {
					String effectString = string.substring(startIndex + 1, currentCharacter);
					startIndex = -1;
					if (effectString.equals(startEffect)) {
						character = 60;
					} else if (effectString.equals(endEffect)) {
						character = 62;
					} else if (effectString.equals(aRSString_4135)) {
						character = 160;
					} else if (effectString.equals(aRSString_4162)) {
						character = 173;
					} else if (effectString.equals(aRSString_4165)) {
						character = 215;
					} else if (effectString.equals(aRSString_4147)) {
						character = 128;
					} else if (effectString.equals(aRSString_4163)) {
						character = 169;
					} else if (effectString.equals(aRSString_4169)) {
						character = 174;
					} else {
						if (effectString.startsWith(startImage)) {
							try {
								int imageId = Integer.valueOf(effectString.substring(4));
								Sprite chatImageId = SpriteLoader.sprites[imageId];
								int iconModY = chatImageId.maxHeight - ((imageId >= 0 && imageId <= 7) ? 10 : 0);
								/*if (transparency == 256) {
									chatImageId.method346(drawX, (drawY + baseCharacterHeight - iconModY));
								} else {
									chatImageId.drawSprite(drawX,(drawY + baseCharacterHeight - iconModY), transparency);
								}*/

								chatImageId.drawSprite(drawX + 2, (drawY + baseCharacterHeight) - 12 + chatImageId.drawOffsetY);
								drawX += chatImageId.maxWidth;
							} catch (Exception exception) {

								/* empty */
							}
						} else {
							setTextEffects(effectString);
						}
						continue;
					}
				}
				if (startIndex == -1) {
					int width = characterWidths[character];
					int height = characterHeights[character];
					if (character != 32) {
						if (transparency == 256) {
							if (textShadowColor != -1) {
								drawCharacter(character, drawX + characterDrawXOffsets[character] + 1,
										drawY + characterDrawYOffsets[character] + 1, width, height, textShadowColor,
										true);
							}
							drawCharacter(character, drawX + characterDrawXOffsets[character],
									drawY + characterDrawYOffsets[character], width, height, textColor, false);
						} else {
							if (textShadowColor != -1) {
								drawTransparentCharacter(character, drawX + characterDrawXOffsets[character] + 1,
										drawY + characterDrawYOffsets[character] + 1, width, height, textShadowColor,
										transparency, true);
							}
							drawTransparentCharacter(character, drawX + characterDrawXOffsets[character],
									drawY + characterDrawYOffsets[character], width, height, textColor, transparency,
									false);
						}
					} else if (anInt4178 > 0) {
						anInt4175 += anInt4178;
						drawX += anInt4175 >> 8;
						anInt4175 &= 0xff;
					}
					int lineWidth = characterScreenWidths[character];
					if (strikethroughColor != -1) {
						DrawingArea.drawHorizontalLine(drawX,
								drawY + (int) (baseCharacterHeight * 0.69999999999999996D), lineWidth,
								strikethroughColor);
					}
					if (underlineColor != -1) {
						DrawingArea.drawHorizontalLine(drawX, drawY + baseCharacterHeight, lineWidth,
								underlineColor);
					}
					drawX += lineWidth;
				}
			}
		}
	}

	public void drawCenteredString(String string, int drawX, int drawY, int color, int shadow) {
		if (string != null) {
			setColorAndShadow(color, shadow);
			//string = handleOldSyntax(string);
			drawBasicString(string, drawX - getTextWidth(string) / 2, drawY);
		}
	}

	public void drawRightAlignedString(String string, int drawX, int drawY, int color, int shadow) {
		if (string != null) {
			setColorAndShadow(color, shadow);
			string = handleOldSyntax(string);
			drawBasicString(string, drawX - getTextWidth(string), drawY);
		}
	}

	public static String replaceAllString(String strOrig, String strFind, String strReplace) {  
		if(strOrig == null) {  
			return null;  
		}  
		StringBuffer sb = new StringBuffer(strOrig);  
		String toReplace = "";  

		if (strReplace == null) toReplace = "";  
		else toReplace = strReplace;  

		int pos = strOrig.length();  

		while (pos > -1) {  
			pos = strOrig.lastIndexOf(strFind, pos);  
			if (pos > -1) sb.replace(pos, pos+strFind.length(), toReplace);  
			pos = pos - strFind.length();  
		}  

		return sb.toString();  
	}  

	private static String replace(String s, String from, String to)
	{
		if((from == "=D" || from == "=d") && s.contains("col"))
			return s;
		return replaceAllString(s, from, to);

		/*try {

			if(s.contains(from)) {
				System.out.println("FOund: "+from+" in string "+s+", replacing to: "+s.replaceAll(from, to));
				return s.replace(from, to);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}*/

		//return s;
	}

	public String[] wrap(String text, int maximumWidth) {
		String[] words = text.split(" ");

		if (words.length == 0) {
			return new String[] { text };
		}

		List<String> lines = new ArrayList<>();

		String line = new String();

		int lineWidth = 0;

		int spaceWidth = getTextWidth(" ");

		for (String word : words) {
			if (word.isEmpty()) {
				continue;
			}
			int wordWidth = getTextWidth(word);
			boolean isLastWord = word.equals(words[words.length - 1]);

			if (wordWidth + lineWidth >= maximumWidth && !isLastWord) {
				lines.add(line.trim());
				line = new String(word.concat(" "));
				lineWidth = wordWidth + spaceWidth;
			} else if (isLastWord) {
				if (wordWidth + lineWidth > maximumWidth) {
					lines.add(line.trim());
					lines.add(word);
				} else {
					lines.add(line.concat(word));
				}
			} else {
				line = line.concat(word).concat(" ");
				lineWidth += wordWidth + spaceWidth;
			}
		}

		return lines.toArray(new String[lines.size()]);
	}

	public static String handleOldSyntax(String text) {

		text = replace(text, "@red@", "<col=ff0000>");
		text = replace(text, "@gre@", "<col=65280>");
		text = replace(text, "@blu@", "<col=255>");
		text = replace(text, "@yel@", "<col=ffff00>");
		text = replace(text, "@cya@", "<col=65535>");
		text = replace(text, "@mag@", "<col=ff00ff>");
		text = replace(text, "@whi@", "<col=ffffff>");
		text = replace(text, "@lre@", "<col=ff9040>");
		text = replace(text, "@dre@", "<col=800000>");
		text = replace(text, "@bla@", "<col=0>");
		text = replace(text, "@or1@", "<col=ffb000>");
		text = replace(text, "@or2@", "<col=ff7000>");
		text = replace(text, "@or3@", "<col=ff3000>");
		text = replace(text, "@gr1@", "<col=c0ff00>");
		text = replace(text, "@gr2@", "<col=80ff00>");
		text = replace(text, "@gr3@", "<col=40ff00>");
		text = replace(text, "@dev@", "<img=3>");
		text = replace(text, "@des@", "<img=4>");
		text = replace(text, "@vet@", "<img=5>");
		text = replace(text, "@don@", "<img=6>");
		text = replace(text, "@or2@", "<col=ff7000>");
		/*if(text != null && SMILIES_TOGGLED && !text.contains(":store")) {
			//Cba making another sprite[] ill just use modicons for these lol //Gabbe
			text = replace(text, ":=)", "<img=12>");text = replace(text, "=)", "<img=12>"); text = replace(text, ":)", "<img=12>");text = replace(text, ":]", "<img=12>");
			text = replace(text, ":=(", "<img=13>");text = replace(text, "=(", "<img=13>");text = replace(text, ":(", "<img=13>");text = replace(text, ":[", "<img=13>");
			text = replace(text, ":|", "<img=14>");
			text = replace(text, ":S", "<img=15>"); text = replace(text, ":s", "<img=15>");
			text = replace(text, ":O", "<img=17>"); text = replace(text, ":o", "<img=17>");
			text = replace(text, ":8", "<img=16>");
			text = replace(text, ":$", "<img=18>");
			text = replace(text, ";)", "<img=19>");text = replace(text, ";]", "<img=19>");
			text = replace(text, ":/", "<img=20>"); text = replace(text, ":\\", "<img=20>"); text = replace(text, "\\:", "<img=20>");
			text = replace(text, "(y)", "<img=21>"); text = replace(text, "(Y)", "<img=21>");
			text = replace(text, "(n)", "<img=22>"); text = replace(text, "(N)", "<img=22>");
			text = replace(text, ":p", "<img=23>"); text = replace(text, ":P", "<img=23>");
			text = replace(text, ":d", "<img=24>"); text = replace(text, ":D", "<img=24>");text = replace(text, "=D", "<img=24>"); text = replace(text, "=d", "<img=24>");
			text = replace(text, "^^", "<img=25>");
			text = replace(text, "<3", "<img=26>"); text = replace(text, "(L)", "<img=26>"); text = replace(text, "(l)", "<img=26>");
			text = replace(text, ":'(", "<img=28>");
			text = replace(text, ":*", "<img=29>");
			text = replace(text, "(a)", "<img=27>"); text = replace(text, "(A)", "<img=27>");
			text = replace(text, "-.-", "<img=30>");
			text = replace(text, "O.o", "<img=31>"); text = replace(text, "o.O", "<img=31>");text = replace(text, "o.o", "<img=31>"); text = replace(text, "O.O", "<img=31>");
		}*/
		return text;
	}

	public static void nullLoader() {
		startEffect = null;
		endEffect = null;
		aRSString_4135 = null;
		aRSString_4162 = null;
		aRSString_4165 = null;
		aRSString_4147 = null;
		aRSString_4163 = null;
		aRSString_4169 = null;
		startImage = null;
		lineBreak = null;
		startColor = null;
		endColor = null;
		startTransparency = null;
		endTransparency = null;
		startUnderline = null;
		startDefaultUnderline = null;
		endUnderline = null;
		startShadow = null;
		startDefaultShadow = null;
		endShadow = null;
		startStrikethrough = null;
		defaultStrikethrough = null;
		endStrikethrough = null;
		aRSString_4143 = null;
		splitTextStrings = null;
	}

	public static void createTransparentCharacterPixels(int[] is, byte[] is_0_, int i, int i_1_, int i_2_, int i_3_, int i_4_, int i_5_, int i_6_, int i_7_) {
		i = ((i & 0xff00ff) * i_7_ & ~0xff00ff) + ((i & 0xff00) * i_7_ & 0xff0000) >> 8;
					i_7_ = 256 - i_7_;
					for (int i_8_ = -i_4_; i_8_ < 0; i_8_++) {
						for (int i_9_ = -i_3_; i_9_ < 0; i_9_++) {
							if (is_0_[i_1_++] != 0) {
								int i_10_ = is[i_2_];
								is[i_2_++] = ((((i_10_ & 0xff00ff) * i_7_ & ~0xff00ff) + ((i_10_ & 0xff00) * i_7_ & 0xff0000)) >> 8) + i;
							} else {
								i_2_++;
							}
						}
						i_2_ += i_5_;
						i_1_ += i_6_;
					}
	}

	public void drawTransparentCharacter(int i, int i_11_, int i_12_, int i_13_, int i_14_, int i_15_, int i_16_, boolean bool) {
		int i_17_ = i_11_ + i_12_ * width;
		int i_18_ = width - i_13_;
		int i_19_ = 0;
		int i_20_ = 0;
		if (i_12_ < topY) {
			int i_21_ = topY - i_12_;
			i_14_ -= i_21_;
			i_12_ = topY;
			i_20_ += i_21_ * i_13_;
			i_17_ += i_21_ * width;
		}
		if (i_12_ + i_14_ > bottomY) {
			i_14_ -= i_12_ + i_14_ - bottomY;
		}
		if (i_11_ < topX) {
			int i_22_ = topX - i_11_;
			i_13_ -= i_22_;
			i_11_ = topX;
			i_20_ += i_22_;
			i_17_ += i_22_;
			i_19_ += i_22_;
			i_18_ += i_22_;
		}
		if (i_11_ + i_13_ > bottomX) {
			int i_23_ = i_11_ + i_13_ - bottomX;
			i_13_ -= i_23_;
			i_19_ += i_23_;
			i_18_ += i_23_;
		}
		if (i_13_ > 0 && i_14_ > 0) {
			createTransparentCharacterPixels(pixels, fontPixels[i], i_15_, i_20_, i_17_, i_13_, i_14_, i_18_, i_19_, i_16_);
		}
	}

	public static void createCharacterPixels(int[] is, byte[] is_24_, int i, int i_25_, int i_26_, int i_27_, int i_28_, int i_29_, int i_30_) {
		int i_31_ = -(i_27_ >> 2);
		i_27_ = -(i_27_ & 0x3);
		for (int i_32_ = -i_28_; i_32_ < 0; i_32_++) {
			for (int i_33_ = i_31_; i_33_ < 0; i_33_++) {
				if (is_24_[i_25_++] != 0) {
					is[i_26_++] = i;
				} else {
					i_26_++;
				}
				if (is_24_[i_25_++] != 0) {
					is[i_26_++] = i;
				} else {
					i_26_++;
				}
				if (is_24_[i_25_++] != 0) {
					is[i_26_++] = i;
				} else {
					i_26_++;
				}
				if (is_24_[i_25_++] != 0) {
					is[i_26_++] = i;
				} else {
					i_26_++;
				}
			}
			for (int i_34_ = i_27_; i_34_ < 0; i_34_++) {
				if (is_24_[i_25_++] != 0) {
					is[i_26_++] = i;
				} else {
					i_26_++;
				}
			}
			i_26_ += i_29_;
			i_25_ += i_30_;
		}
	}

	public void drawCharacter(int character, int i_35_, int i_36_, int i_37_, int i_38_, int i_39_, boolean bool) {
		int i_40_ = i_35_ + i_36_ * width;
		int i_41_ = width - i_37_;
		int i_42_ = 0;
		int i_43_ = 0;
		if (i_36_ < topY) {
			int i_44_ = topY - i_36_;
			i_38_ -= i_44_;
			i_36_ = topY;
			i_43_ += i_44_ * i_37_;
			i_40_ += i_44_ * width;
		}
		if (i_36_ + i_38_ > bottomY) {
			i_38_ -= i_36_ + i_38_ - bottomY;
		}
		if (i_35_ < topX) {
			int i_45_ = topX - i_35_;
			i_37_ -= i_45_;
			i_35_ = topX;
			i_43_ += i_45_;
			i_40_ += i_45_;
			i_42_ += i_45_;
			i_41_ += i_45_;
		}
		if (i_35_ + i_37_ > bottomX) {
			int i_46_ = i_35_ + i_37_ - bottomX;
			i_37_ -= i_46_;
			i_42_ += i_46_;
			i_41_ += i_46_;
		}
		if (i_37_ > 0 && i_38_ > 0) {
			try {
				createCharacterPixels(pixels, fontPixels[character], i_39_, i_43_, i_40_, i_37_, i_38_, i_41_, i_42_);
			} catch (Exception e) {

			}

		}
	}

	static {
		startTransparency = "trans=";
		startStrikethrough = "str=";
		startDefaultShadow = "shad";
		startColor = "col=";
		lineBreak = "br";
		defaultStrikethrough = "str";
		endUnderline = "/u";
		startImage = "img=";
		startShadow = "shad=";
		startUnderline = "u=";
		endColor = "/col";
		startDefaultUnderline = "u";
		endTransparency = "/trans";

		aRSString_4143 = Integer.toString(100);
		aRSString_4135 = "nbsp";
		aRSString_4169 = "reg";
		aRSString_4165 = "times";
		aRSString_4162 = "shy";
		aRSString_4163 = "copy";
		endEffect = "gt";
		aRSString_4147 = "euro";
		startEffect = "lt";
		defaultTransparency = 256;
		defaultShadow = -1;
		anInt4175 = 0;
		textShadowColor = -1;
		textColor = 0;
		defaultColor = 0;
		strikethroughColor = -1;
		splitTextStrings = new String[100];
		underlineColor = -1;
		anInt4178 = 0;
		transparency = 256;
	}
}