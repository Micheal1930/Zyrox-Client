package com.varrock.client;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * @author Nick Hartskeerl <apachenick@hotmail.com>
 *
 */
public class ClassCheck {
	
	/**
	 * 
	 */
	public static final String[] DEFAULT_PACKAGE = { 
		"com.varrock",
		"com.varrock.client",
		"com.varrock.cache",
		"com.varrock.client.cache",
		"com.varrock.client.particles",
		"com.varrock.client.util",
		"com.varrock.client.task"
	};

	/**
	 * 
	 * @return
	 * @throws Throwable
	 */
	public static String generate() throws Throwable {
		
		List<String> classNames = new ArrayList<String>();
		
		for(String string : DEFAULT_PACKAGE) {
		
			Class<?>[] classes = ReflectionTools.getClasses(string);
			
			for(Class<?> classz : classes) {
				
				String name = classz.getName();
				
				if(name.equals(new StringBuilder(string).append(".Configuration").toString())) {
					continue;
				}
				
				if(name != null) {
					classNames.add(name);
				}
				
			}
		
		}
		
		Collections.sort(classNames, String.CASE_INSENSITIVE_ORDER);
		
		Map<String, List<String>> objects = new HashMap<String, List<String>>();
		
		for(String className : classNames) {
			
			List<String> found = new ArrayList<String>();
			Class<?> classz = Class.forName(className);
			
			for(Method method : classz.getDeclaredMethods()) {
				
				if(method == null) {
					continue;
				}
				
				String identifier = "method_"+method.getName()+"_"+method.getModifiers()+"_";
				
				found.add(identifier);
				
			}
			
			for(Field field : classz.getDeclaredFields()) {
				
				if(field == null) {
					continue;
				}
				
				String identifier = "field_"+field.getName()+"_"+field.getModifiers()+"_"+field.getType().getName();
				
				found.add(identifier);
				
			}
			
			Collections.sort(found, String.CASE_INSENSITIVE_ORDER);
			
			objects.put(className, found);
			
		}
		
		StringBuilder builder = new StringBuilder();
		Iterator<Entry<String, List<String>>> $it = objects.entrySet().iterator();
		
		for(; $it.hasNext();) {
			
			Entry<String, List<String>> entry = $it.next();
			
			if(entry == null) {
				continue;
			}
			
			builder.append(entry.getKey());
			builder.append("\n");
			
			Iterator<String> $it2 = entry.getValue().iterator();
			
			for(; $it2.hasNext();) {
				builder.append($it2.next());
				builder.append("\n");
			}
			
		}
		
		MessageDigest m = MessageDigest.getInstance("MD5");
		byte[] buffer = m.digest(builder.toString().getBytes("UTF-8"));
        StringBuffer sb = new StringBuffer();
        
        for(int i = 0; i < buffer.length; i++) {
            sb.append(String.format("%02x", buffer[i]));
        }
        
        return sb.toString();
		
	}
	
}
