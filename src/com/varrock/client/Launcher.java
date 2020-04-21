package com.varrock.client;

import java.io.File;

public class Launcher {

	public static void main(String[] args) throws Exception {
		File jarFile = new File(Launcher.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		String cmd = "java -Xmx1g -Xms1g -cp \"" + jarFile.getPath() + "\" \"com.varrock.client.Client\"";
		Runtime.getRuntime().exec(cmd); // that looks alright
	}

}
