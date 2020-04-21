package com.varrock.client;

/**
 * 
 * @author Nick Hartskeerl <apachenick@hotmail.com>
 */
public class Formatter {
	
	/**
	 * The consonants in the English language.
	 */
	private static final String[] CONSONANTS = { "a", "e", "i", "o", "u" };
	
	/**
	 * Format's the specific string on the given type.
	 * @param type The given type.
	 * @param string The specific string.
	 * @return The formatted string.
	 */
	public static final String format(FormatTypes type, String string) {
		return (type == null || string == null)  ? null : type.format(string);
	}
	
	/**
	 * Check if a character is valid.
	 * @param character The character to check.
	 * @return {@code true} If the character is valid.
	 */
	public static final boolean validCharacter(char character) {
		return ("" + character).matches("^[a-zA-Z0-9_]+");
	}
	
	/**
	 * Check if two strings equal after formatting.
	 * @param type The type.
	 * @param first The first string. 
	 * @param second The second string.
	 * @return {@code true} If both aren't null and equals.
	 */
	public static final boolean equals(final FormatTypes type, final String first, final String second) {
		final String firstFormatted = format(type, first), secondFormatted = format(type, second);
		return firstFormatted != null && secondFormatted != null && firstFormatted.equals(secondFormatted);
	}
	
	/**
	 * Check is the indefinite article should be applied to the word.
	 * @param word The word.
	 * @return If it should be applied {@code true}.
	 */
	public static boolean isIndefinite(String word) {
		word = word.toLowerCase();
		for(final String consonant : CONSONANTS) {
			if(word.startsWith(consonant)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Capitalize a character in a word.
	 * @param word The word.
	 * @param index The character's index.
	 * @return The word with the capitalized character.
	 */
	public static String capitalize(String word, int index) {
		final StringBuilder builder = new StringBuilder();
		if(index > 0) {
			builder.append(word.substring(0, index - 1));
		}
		builder.append(word.charAt(index)+"".toUpperCase());
		builder.append(word.substring(index + 1, word.length()));
		return builder.toString();
	}
	
}