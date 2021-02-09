package com.zyrox.util;

public class StringUtils {

	public static String capitalizeFirst(String name) {
		if (name.length() < 1)
			return "";
		StringBuilder builder = new StringBuilder(name.length());
		char first = Character.toUpperCase(name.charAt(0));
		builder.append(first).append(name.toLowerCase().substring(1));
		return builder.toString();
	}

	public static String insertCommasToNumber(String number) {
		return number.length() < 4 ? number : insertCommasToNumber(number.substring(0, number.length() - 3)) + "," + number.substring(number.length() - 3, number.length());
	}

	public static String capitalize(String s) {
		s = s.toLowerCase();
		for (int i = 0; i < s.length(); i++) {
			if (i == 0) {
				s = String.format("%s%s", Character.toUpperCase(s.charAt(0)), s.substring(1));
			}
			if (!Character.isLetterOrDigit(s.charAt(i))) {
				if (i + 1 < s.length()) {
					s = String.format("%s%s%s", s.subSequence(0, i + 1), Character.toUpperCase(s.charAt(i + 1)), s.substring(i + 2));
				}
			}
		}
		return s;
	}


}
