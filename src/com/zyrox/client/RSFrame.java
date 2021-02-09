package com.zyrox.client;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowAdapter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.zyrox.Configuration;

import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.lang.reflect.Method;

@SuppressWarnings("all")
final class RSFrame extends JFrame {

	public static boolean destroy;
	
	
	public RSFrame(RSApplet rsapplet, int width, int height, boolean undecorative, boolean resizable) {
		rsApplet = rsapplet;
		setTitle(Configuration.CLIENT_NAME);
		setUndecorated(undecorative);
		setResizable(resizable);

	
		setVisible(true);
		Insets insets = this.getInsets();
		if (resizable) {
			setMinimumSize(new Dimension(766 + insets.left + insets.right, 559 + insets.top + insets.bottom));
		}
		setSize(width + insets.left + insets.right, height + insets.top + insets.bottom);
		Client.getClient();
		setLocationRelativeTo(null);

		requestFocus();
		toFront();
		this.setFocusTraversalKeysEnabled(false);
		setBackground(Color.BLACK);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent we) { 
		        String options[] = {"Yes", "No"};
		        int userPrompt = JOptionPane.showOptionDialog(null, "Are you sure you wish to exit?", "Zyrox",
		        		JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options , options[1]);
		        if(userPrompt == JOptionPane.YES_OPTION) {
		        	destroy = true;
		            System.exit(0);
		        } else {
		        	destroy = false;
		        }
		    }
		});
		
		try {
			createBufferStrategy(2);
			bufferStrategy = getBufferStrategy();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void launchURL(String url) {
		String osName = System.getProperty("os.name");
		try {
			if (osName.startsWith("Mac OS")) {
				Class<?> fileMgr = Class.forName("com.apple.eio.FileManager");
				Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[] { String.class });
				openURL.invoke(null, new Object[] { url });
			} else if (osName.startsWith("Windows")) {
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
			} else {
				String[] browsers = { "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape", "safari" };
				String browser = null;
				for (int count = 0; count < browsers.length && browser == null; count++) {
					if (Runtime.getRuntime().exec(new String[] { "which", browsers[count] }).waitFor() == 0) {
						browser = browsers[count];
					}
				}
				if (browser == null) {
					throw new Exception("Could not find web browser");
				} else {
					Runtime.getRuntime().exec(new String[] { browser, url });
				}
			}
		} catch (Exception e) {
		
		}
	}
	
	public void setClientIcon() {
		/*Image img = Client.resourceLoader.getImage("icon");
		if(img == null)
			return;

		setIconImage(img);*/
	}

	
	public void mouseWheelMoved(MouseWheelEvent event) {
		rsApplet.mouseWheelMoved(event);
	}

	public Graphics getGraphics() {
		Graphics g = super.getGraphics();
		Insets insets = this.getInsets();
		g.translate(insets.left, insets.top);
		return g;
	}

	public int getFrameWidth() {
		Insets insets = this.getInsets();
		return getWidth() - (insets.left + insets.right);
	}

	public int getFrameHeight() {
		Insets insets = this.getInsets();
		return getHeight() - (insets.top + insets.bottom);
	}

	public void update(Graphics g) {
		rsApplet.update(g);
	}

	public void paint(Graphics g) {
		rsApplet.paint(g);
	}
	
	public BufferStrategy getBufStrategy() {
		return bufferStrategy;
	}

	private BufferStrategy bufferStrategy;

	final RSApplet rsApplet;
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Dimension screenSize = toolkit.getScreenSize();
	int screenWidth = (int) screenSize.getWidth();
	int screenHeight = (int) screenSize.getHeight();
}