package com.varrock;

import java.net.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.varrock.client.Client;
import com.varrock.client.Launcher;
import com.varrock.client.ResourceLoader;

import java.awt.event.WindowEvent;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.*;

public class Jframe extends Client implements ActionListener {

	private static final long serialVersionUID = 1L;
	public static JFrame frame;

	public Jframe(int width, int height, boolean resizable) {
		super();
		try {
			signlink.startpriv(InetAddress.getByName(Configuration.HOST));
			initUI(width, height, resizable);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void initUI(int width, int height, boolean resizable) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			JPopupMenu.setDefaultLightWeightPopupEnabled(false);
			
			frame = new JFrame(Configuration.CLIENT_NAME);
			frame.setLayout(new BorderLayout());
			frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

			
			frame.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent we) { 
		        String options[] = {"Yes", "No"};
		        int userPrompt = JOptionPane.showOptionDialog(null, "Are you sure you wish to exit?", "Varrock",
		        		JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options , options[1]);
		        if(userPrompt == JOptionPane.YES_OPTION) {
					System.exit(-1);
		            System.exit(0);
		        }
			}
		});
			setFocusTraversalKeysEnabled(false);
			JPanel gamePanel = new JPanel();
			Insets insets = this.getInsets();
			if(System.getProperty("os.name").toLowerCase().indexOf("mac") >= 0) {
				width += 10;
				height += 10;
			}
			//super.setPreferredSize(new Dimension(width - 10, height - 10));
			super.setPreferredSize(new Dimension(width, height));
			frame.setLayout(new BorderLayout());
			gamePanel.setLayout(new BorderLayout());
			gamePanel.add(this);
			gamePanel.setBackground(Color.BLACK);
			setClientIcon();
			//initializeMenuBar();
			frame.getContentPane().add(gamePanel, BorderLayout.CENTER);
			frame.pack();
			frame.setResizable(resizable);
			init();
			Jframe.super.graphics = getGameComponent().getGraphics();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true); 
			frame.createBufferStrategy(2);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void rebuildFrame(int width, int height, boolean resizable, boolean undecorated) {
		

		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		frame = new JFrame(Configuration.CLIENT_NAME);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
	    @Override
	    public void windowClosing(WindowEvent we) { 
	        String options[] = {"Yes", "No"};
	        int userPrompt = JOptionPane.showOptionDialog(null, "Are you sure you wish to exit?", "Varrock",
	        		JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options , options[1]);
	        if(userPrompt == JOptionPane.YES_OPTION) {
				/*if(!Configuration.LOCALHOST) {
					openURL("http://simplicityps.org/forums");
				}*/
				System.exit(-1);
	            System.exit(0);
	        } else {
	        	 
	        }
	    }
	});
		frame.setUndecorated(undecorated);
		setFocusTraversalKeysEnabled(false);
		final JPanel gamePanel = new JPanel();
		Insets insets = this.getInsets();
		super.setPreferredSize(new Dimension(width - 10, height - 10));
		frame.setLayout(new BorderLayout());
		gamePanel.setLayout(new BorderLayout());
		gamePanel.add(this, BorderLayout.CENTER);
		gamePanel.setBackground(Color.BLACK);

		frame.getContentPane().add(gamePanel, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(resizable);
		//init();
		graphics = getGameComponent().getGraphics();
		frame.setLocation((screenWidth - width) / 2, ((screenHeight - height) / 2) - screenHeight == getMaxHeight() ? 0 : undecorated ? 0 : 70);
		frame.setVisible(true); 
		frame.createBufferStrategy(2);
		

		frame.addComponentListener(new ComponentAdapter() {
			
			@Override
			public void componentResized(ComponentEvent e) {

				Dimension dimension = new Dimension(frame.getWidth(), frame.getHeight());
				
				gamePanel.setMinimumSize(dimension);
				gamePanel.setPreferredSize(dimension);
				gamePanel.setSize(dimension);
				
				Jframe.super.setPreferredSize(new Dimension(frame.getWidth() - 10, frame.getHeight() - 10));
				//Jframe.super.revalidate();
				Jframe.super.repaint();

				Jframe.super.graphics = getGameComponent().getGraphics();

			}
			
		});
		
	}

	public void setClientIcon() {
		InputStream icon64 = this.getClass().getResourceAsStream("com/varrock/client/images/i64.png");
		InputStream icon32 = this.getClass().getResourceAsStream("com/varrock/client/images/i32.png");
		InputStream icon16 = this.getClass().getResourceAsStream("com/varrock/client/images/i16.png");
		System.out.println(this.getClass().getCanonicalName());
		
		try {// lol
			Image whip64 = ImageIO.read(icon64);// its not loading the sprites now either :P. I think that's normal
			Image whip32 = ImageIO.read(icon32);
			Image whip16 = ImageIO.read(icon16);
			ArrayList<Image> icons = new ArrayList<Image>();
			icons.add(whip64);//
			icons.add(whip32);// they was loading before
			icons.add(whip16);
			frame.setIconImages(icons);
		} catch(Exception e) {
			e.printStackTrace();// I know, but when you're loading from a jar it should be different idk so no way to get it to work?
		}
	}


	/**
	 * Our jpanel for the menu bar
	 */
	private static JPanel menuPanel;
	
	/**
	 * Initializes the menu bar
	 */
	public void initializeMenuBar() {

		/*
		 * Initialize our menu panel to hold our menu buttons
		 */
		menuPanel = new JPanel();

		/*
		 * Set the menu panel as non focusable
		 */
		menuPanel.setFocusable(false);

		/*
		 * Disable focus traversal keys
		 */
		menuPanel.setFocusTraversalKeysEnabled(false);

		menuPanel.setBackground(Color.decode("0xD28400"));

		menuPanel.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
		menuPanel.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);

		/*
		 * Create our buttons
		 */
		JButton homeButton = createButton("Home", "House_icon.png", "Open the official Varrock homepage.");
		JButton forumsButton = createButton("Forums", "forums.png", "Open the official Varrock forums.");

		JButton knowledgeBaseButton = createButton("Knowledge Base", "3366503.gif", "Open the Varrock Knowledge Base on the forums.");
		JButton storeButton = createButton("Store", "cart_icon.gif", "Open the official Varrock store.");
		JButton voteButton = createButton("Vote", "Small-checkmark.png", "Open the official Varrock voting page.");
		JButton scoresButton = createButton("Hiscores", "hiscores.png", "Open the official Varrock Hiscores");

		JButton tsButton = createButton("Join Discord", "discord.png", "Join the Varrock discord.");

		
		/*
		 * Add our buttons to the menu panel
		 */
		menuPanel.add(homeButton);
		menuPanel.add(forumsButton);
		menuPanel.add(knowledgeBaseButton);
		menuPanel.add(storeButton);
		menuPanel.add(voteButton);
		menuPanel.add(scoresButton);
//		menuPanel.add(tsButton);

		/*
		 * Add our menu panel to our frame
		 */
		frame.getContentPane().add(menuPanel, BorderLayout.NORTH);
	}
	
	/**
	 * Creates a button on the menu panel
	 * 
	 * @param title
	 *            The Title of the button
	 * @param image
	 *            The image to display
	 * @param tooltip
	 *            The tooltip when hovering over the button
	 * @return The created button
	 */
	private JButton createButton(String title, String image, String tooltip) {
		JButton button = new JButton(title);
		if (image != null)
			button.setIcon(new ImageIcon(ResourceLoader.loadImage(image)));
		button.addActionListener(this);
		if (tooltip != null)
			button.setToolTipText(tooltip);
		button.setBorder(BorderFactory.createEmptyBorder());
		button.setContentAreaFilled(false);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setForeground(Color.WHITE);
		return button;
	}

	public URL getCodeBase() {
		try {
			return new URL("http://" + Configuration.HOST + "/");
		} catch (Exception e) {
			return super.getCodeBase();
		}
	}

	public URL getDocumentBase() {
		return getCodeBase();
	}

	public void loadError(String s) {
		System.out.println("loadError: " + s);
	}

	public String getParameter(String key) {
			return "";
	}

	public static void openUpWebSite(String url) {
		Desktop d = Desktop.getDesktop();
		try {
			d.browse(new URI(url)); 	
		} catch (Exception e) {
		}
	}

	public void actionPerformed(ActionEvent evt) {
		String cmd = evt.getActionCommand();
		try {
			if (cmd != null) {
				if (cmd.equals("Home")) {
					openURL("http://www.varrock.io");
				} else if (cmd.equals("Forums")) {
					openURL("https://varrock.io/forums/");
				} else if (cmd.equals("Knowledge Base")) {
					openURL("https://varrock.io/forums/");
				} else if (cmd.equals("Store")) {
					openURL("https://varrock.io/store/");
				} else if (cmd.equals("Vote")) {
					openURL("https://varrock.io/vote/");
				} else if (cmd.equals("Hiscores")) {
					openURL("https://varrock.io/hiscores/");
				} else if (cmd.equals("Join Discord")) {
					openURL("https://discord.gg/a3Jm2Pe");
				}
			}
		} catch (Exception e) {
		}
	}
	
	/**
	 * Opens a URL in your default web browser
	 * 
	 * @param url
	 *            The url of the website to open
	 */
	static void openURL(String url) {
		Desktop d = Desktop.getDesktop();
		try {
			d.browse(new URI(url));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Dimension screenSize = toolkit.getScreenSize();
	int screenWidth = (int) screenSize.getWidth();
	int screenHeight = (int) screenSize.getHeight();
	
}