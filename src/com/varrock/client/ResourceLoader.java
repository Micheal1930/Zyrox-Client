package com.varrock.client;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.HashMap;

/**
 * This class grabs resources from the 'images' folder in the res package.
 * @author Gabriel Hannason
 */
public class ResourceLoader {

	private static final HashMap<String, Image> loadedImages = new HashMap<String, Image>();

	public Image getImage(String imageName) {
		if(loadedImages.containsKey(imageName))
			return loadedImages.get(imageName);
		Image img = null;
		try {
			img = Toolkit.getDefaultToolkit().getImage(signlink.findcachedir() + ""+imageName+".png");
		} catch(Exception e) {
			e.printStackTrace();
			img = null;
		}
		if(img != null)
			loadedImages.put(imageName, img);
		return img;
	}
	
	static ResourceLoader rl = new ResourceLoader();

	public static Image loadImage(String imageName) {
		URL url = null;
		try {
			url = Client.class.getResource("./images/" + imageName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (url == null) {
			System.out.println(imageName);
			return null;
		}
		return Toolkit.getDefaultToolkit().getImage(url);
	}
	
}
