package com.zyrox.client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**

 * @author Nick Hartskeerl <apachenick@hotmail.com>
 */
public enum FormatTypes {

	/**
	 * The display name format type.
	 */
	DISPLAY_NAME {
		@Override
		public String format(String string) {
			string = string.replace("_", " ");
			final StringBuilder builder = new StringBuilder();
			boolean uppered = false;
			for (int index = 0; index < string.length(); index++) {
				final char c = string.charAt(index);
				final boolean canUpper = !uppered && Formatter.validCharacter(c);
				builder.append(canUpper ? Character.toUpperCase(c) : c);
				if (canUpper)
					uppered = true;
			}
			return builder.toString();
		}
	},

	/**
	 * The protocol format type.
	 */
	PROTOCOL {
		@Override
		public String format(String string) {
			return string.toLowerCase().replace(" ", "_");
		}
	},

	/**
	 * The chat message format type.
	 */
	CHAT_MESSAGE {
		@Override
		public String format(String string) {
			string = string.replace("_", " ");
			final StringBuilder builder = new StringBuilder();
			boolean containsValidCharacter = false;
			int needUppered = 0;
			for (int index = 0; index < string.length(); index++) {
				final char c = string.charAt(index);
				if (!containsValidCharacter) {
					if (((c == '.' || c == ' ') || !containsValidCharacter) && needUppered == 0
							&& !(containsValidCharacter && Character.isLowerCase(c))) {
						needUppered = 2;
					}
				}
				builder.append(
						Formatter.validCharacter(c) && needUppered == 2 ? Character.toUpperCase(c) : Character.toLowerCase(c));
				if (Formatter.validCharacter(c) && needUppered > 0) {
					if (needUppered == 1) {
						containsValidCharacter = true;
					}
					needUppered--;
				}
			}
			String input = builder.toString();
			final Pattern regex = Pattern.compile("([\\?!\\.]\\s*)([a-z])");
			Matcher matcher = regex.matcher(input);
			while (matcher.find()) {
				input = matcher.replaceFirst(matcher.group(1) + matcher.group(2).toUpperCase());
				matcher = regex.matcher(input);
			}
			return input.length() <= 0 ? ""
					: String.format("%s%s", Character.toUpperCase(input.charAt(0)), input.substring(1));
		}
	};

	/**
	 * Format a string.
	 * 
	 * @param string
	 *            The string to format.
	 * @return The formatted string.
	 */
	public abstract String format(String string);

}