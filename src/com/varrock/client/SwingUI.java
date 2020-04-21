package com.varrock.client;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;

public class SwingUI extends Client implements ActionListener {

    private static JMenuItem menuItem;
	private JFrame frame;

	public SwingUI(String args[]) {
		super();
		try {
			initUI();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void initUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			JFrame.setDefaultLookAndFeelDecorated(true);
			JPopupMenu.setDefaultLightWeightPopupEnabled(false);
			frame = new JFrame("Varrock");
			frame.setLayout(new BorderLayout());
			frame.setResizable(false);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JPanel gamePanel = new JPanel();

			gamePanel.setLayout(new BorderLayout());
			gamePanel.add(this);
			gamePanel.setPreferredSize(new Dimension(765, 503));

			initMenubar();
			frame.getContentPane().add(gamePanel, BorderLayout.CENTER);
			frame.pack();

			frame.setVisible(true); // can see the client
			frame.setResizable(false); // resizeable frame

			init();
		} catch (Exception e) {
				e.printStackTrace();
		}
	}

	public void initMenubar() {
			JMenu fileMenu = new JMenu("File");
			String[] mainButtons = new String[] { "Forums", "-", "Exit" };
			for (String name : mainButtons) {
				JMenuItem menuItem = new JMenuItem(name);
				if (name.equalsIgnoreCase("-")) {
					fileMenu.addSeparator();
				} else if(name.equalsIgnoreCase("Forums")) {
					JMenu forumsMenu = new JMenu("Forums");
					fileMenu.add(forumsMenu);
					JMenuItem runeServer = new JMenuItem("Rune-Server");
					runeServer.addActionListener(this);
					forumsMenu.add(runeServer);
				} else {
					menuItem.addActionListener(this);
					fileMenu.add(menuItem);
				}
			}

			JMenuBar menuBar = new JMenuBar();
			JMenuBar jmenubar = new JMenuBar();

			frame.add(jmenubar);
			menuBar.add(fileMenu);
			frame.getContentPane().add(menuBar, BorderLayout.NORTH);
	}

	private static void openURL(String url) {
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
				if (cmd.equalsIgnoreCase("exit")) {
					System.exit(0);
				} else if (cmd.equalsIgnoreCase("Rune-Server")) {
					openURL("http://www.rune-server.org/forums.php");
				}
			}
		} catch (Exception e) {
		}
	}
}