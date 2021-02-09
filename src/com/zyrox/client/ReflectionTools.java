package com.zyrox.client;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ReflectionTools {

	@SuppressWarnings("rawtypes")
	public static Class[] getClasses(String packageName) throws ClassNotFoundException, IOException {
		final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		assert classLoader != null;
		final String path = packageName.replace('.', '/').replaceAll("%20", " ").replaceAll("%e2%84%a2", "�");
		final Enumeration<URL> resources = classLoader.getResources(path);
		final List<File> directories = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			directories.add(new File(resources.nextElement().getFile().replaceAll("%20", " ").replaceAll("%e2%84%a2", "�")));
		}
		final ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		for (File directory : directories) {
			classes.addAll(findClasses(directory, packageName));
		}
		return classes.toArray(new Class[classes.size()]);
	}

	public static List<Class<?>> findClasses(File directory, String packageName) {
		final List<Class<?>> classes = new ArrayList<Class<?>>();
		if (!directory.exists()) {
			return classes;
		}
		final File[] files = directory.listFiles();
		for (File file : files) {
			if (file.getName().endsWith(".class")) {
				try {
					classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
				} catch (Throwable e) {
					continue;
				}
			}
		}
		return classes;
	}
	
	public static String getName(Object object) {
		return object.getClass().getSimpleName();
	}
	
	public static String getLowerCaseName(Object object) {
		return getName(object).toLowerCase();
	}
	
}
