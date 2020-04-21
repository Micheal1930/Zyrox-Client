package com.varrock.client.content;

import java.util.ArrayList;

import com.varrock.client.Client;

public class LoginScreen {
	private static final int MAX_CHARACTERS = 3;
	
	public static boolean[] socialMediaState = new boolean[5];
	
	public static boolean[] deleteCharacterState = new boolean[MAX_CHARACTERS];

	public static final String[] SOCIAL_MEDIA = { "Facebook", "Twitter", "YouTube", "Twitch", "Discord" };

	public static final ArrayList<CharacterFile> characters = new ArrayList<CharacterFile>();

	public static final String[] SOCIAL_MEDIA_LINKS = { "https://www.facebook.com/zanarisrs", "", "http://www.youtube.com",
			"", "https://discord.gg/8vwC9EK", "" };


	public static void add(String username, String password, boolean safe) {
		if (characters.size() == MAX_CHARACTERS) {
			return;
		}
		for (CharacterFile c : characters) {
			if (c.username.equalsIgnoreCase(username)) {
				return;
			}
		}
		CharacterFile file = new CharacterFile(username, password);
		characters.add(file);
		if(safe) {
			Client.instance.saveSettings();
		}
	}
	
	public static void delete(String username) {
		ArrayList<CharacterFile> chars = (ArrayList<CharacterFile>) characters.clone();
		for (int i = 0; i< chars.size(); i++) {
			CharacterFile c = chars.get(i);
			if (c.username.equalsIgnoreCase(username)) {
				characters.remove(i);
				characters.trimToSize();
				Client.instance.saveSettings();
				break;
			}
		}
	}

	public static final class CharacterFile {
		public String username;
		public String password;

		public CharacterFile(String username, String password) {
			this.username = username;
			this.password = password;
		}
	}
}
