package com.zyrox.client;

import java.applet.Applet;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.*;

import com.zyrox.Jframe;
import com.zyrox.client.cache.definitions.ItemDefinition;
import com.zyrox.client.content.ChatboxItemSearch;
import com.zyrox.client.content.CustomisableHotKeys;

@SuppressWarnings("all")
public class RSApplet extends Applet implements Runnable, MouseListener,
MouseMotionListener, MouseWheelListener, KeyListener, FocusListener,
WindowListener {

	public static int hotKey = 508;
	public boolean resizing;
	private int anInt4;
	private int delayTime;
	int minDelay;
	private final long aLongArray7[] = new long[10];
	int fps;
	boolean shouldDebug;
	int myWidth;
	int myHeight;
	public Graphics graphics;
	public RSImageProducer fullGameScreen;
	RSFrame mainFrame;
	private boolean shouldClearScreen;
	boolean awtFocus;
	int idleTime;
	public int mouseX;
	public int mouseY;
	public int mouseReleased;
	public int mouseDown;
	public int clickX;
	public int clickY;
	private long clickTime;
	public int clickMode3;
	public int saveClickX;
	public int saveClickY;
	long aLong29;
	final int keyArray[] = new int[256];
	private final int charQueue[] = new int[256];
	public boolean isLoading = true;
	private int readIndex;
	private int writeIndex;
	public static int anInt34;
	public boolean isApplet;
	public int resizeChatStartY;
	
	public void setCursor(byte[] data) {

		Image image = getGameComponent().getToolkit().createImage(data);
		getGameComponent().setCursor(
				getGameComponent().getToolkit().createCustomCursor(image,
						new Point(0, 0), null));
	}

	public Component getMainFrame()
	{
		if(isApplet)
			return this;
		return mainFrame;
	}
	public void rebuildFrame(boolean undecorated, int width, int height, boolean resizable, boolean full) {
		boolean webc = Client.webclient && Jframe.frame == null;

		if(webc) {
			if(mainFrame != null)
				mainFrame.dispose();
			mainFrame = null;
		}

		if(full)
			webc = false;
		myWidth = width;
		myHeight = height;
		if (mainFrame != null) {
			mainFrame.dispose();
		}
		if(Jframe.frame != null) {
			Jframe.frame.dispose();
		}
		if (!webc) {
			/*JFrame frame = Jframe.frame;
			frame.setPreferredSize(new Dimension(width, height));
			frame.setResizable(resizable);
			frame.setUndecorated(undecorated);*/
			
			
			if(Client.instance instanceof Jframe) {
				Jframe frame = (Jframe) Client.instance;
				frame.rebuildFrame(width, height, resizable, undecorated);
				frame.setClientIcon();
			} else {
				mainFrame = new RSFrame(this, width, height, undecorated, resizable);
				mainFrame.addWindowListener(this);
				mainFrame.setClientIcon();
			}
		}
		graphics = (webc || Jframe.frame != null ? this : mainFrame).getGraphics();
		if(getGameComponent().getMouseListeners().length == 0) {
			getGameComponent().addMouseListener(this);
			getGameComponent().addMouseMotionListener(this);
			getGameComponent().addKeyListener(this);
			getGameComponent().addFocusListener(this);
			getGameComponent().addMouseWheelListener(this);
		}
		getGameComponent().setFocusTraversalKeysEnabled(false);

	}

	final void createClientFrame(int w, int h) {
		isApplet = false;
		myWidth = w;
		myHeight = h;
		mainFrame = new RSFrame(this, myWidth, myHeight, Client.getClient().getClientSize() == 2, Client.getClient().getClientSize() == 1);
		graphics = getGameComponent().getGraphics();
		fullGameScreen = new RSImageProducer(myWidth, myHeight, getGameComponent());
		startRunnable(this, 1);
	}

	final void initClientFrame(int w, int h) {
		isApplet = true;
		myWidth = w;
		myHeight = h;
		graphics = getGameComponent().getGraphics();
		fullGameScreen = new RSImageProducer(myWidth, myHeight,
				getGameComponent());
		startRunnable(this, 1);
	}

	public void run() {
		getGameComponent().addMouseListener(this);
		getGameComponent().addMouseMotionListener(this);
		getGameComponent().addKeyListener(this);
		getGameComponent().addFocusListener(this);
		getGameComponent().addMouseWheelListener(this);
		if (mainFrame != null) {
			mainFrame.addWindowListener(this);
		}

		colorStart = startTime = 0L;
		startUp();
		int i = 0;
		int j = 256;
		int k = 1;
		int l = 0;
		int i1 = 0;
		for (int j1 = 0; j1 < 10; j1++) {
			aLongArray7[j1] = System.currentTimeMillis();
		}

		long l1 = System.currentTimeMillis();
		do {
			if (anInt4 < 0) {
				break;
			}
			if (anInt4 > 0) {
				anInt4--;
				if (anInt4 == 0) {
					exit();
					return;
				}
			}
			int k1 = j;
			int i2 = k;
			j = 300;
			k = 1;
			long l2 = System.currentTimeMillis();
			if (aLongArray7[i] == 0L) {
				j = k1;
				k = i2;
			} else if (l2 > aLongArray7[i]) {
				j = (int) ((long) (2560 * delayTime) / (l2 - aLongArray7[i]));
			}
			if (j < 25) {
				j = 25;
			}
			if (j > 256) {
				j = 256;
				k = (int) ((long) delayTime - (l2 - aLongArray7[i]) / 10L);
			}
			if (k > delayTime) {
				k = delayTime;
			}
			aLongArray7[i] = l2;
			i = (i + 1) % 10;
			if (k > 1) {
				for (int j2 = 0; j2 < 10; j2++) {
					if (aLongArray7[j2] != 0L) {
						aLongArray7[j2] += k;
					}
				}

			}
			if (k < minDelay) {
				k = minDelay;
			}
			try {
				Thread.sleep(k);
			} catch (InterruptedException interruptedexception) {
				i1++;
			}
			for (; l < 256; l += j) {
				clickMode3 = mouseReleased;
				saveClickX = clickX;
				saveClickY = clickY;
				aLong29 = clickTime;
				mouseReleased = NONE;
				processGameLoop();
				readIndex = writeIndex;
			}
			l &= 0xff;
			if (delayTime > 0) {
				fps = (1000 * j) / (delayTime * 256);
			}
			processDrawing();
			if (shouldDebug) {
				System.out.println((new StringBuilder()).append("ntime:")
						.append(l2).toString());
				for (int k2 = 0; k2 < 10; k2++) {
					int i3 = ((i - k2 - 1) + 20) % 10;
					System.out.println((new StringBuilder()).append("otim")
							.append(i3).append(":").append(aLongArray7[i3])
							.toString());
				}

				System.out.println((new StringBuilder()).append("fps:")
						.append(fps).append(" ratio:").append(j)
						.append(" count:").append(l).toString());
				System.out.println((new StringBuilder()).append("del:")
						.append(k).append(" deltime:").append(delayTime)
						.append(" mindel:").append(minDelay).toString());
				System.out.println((new StringBuilder()).append("intex:")
						.append(i1).append(" opos:").append(i).toString());
				shouldDebug = false;
				i1 = 0;
			}
		} while (true);
		if (anInt4 == -1) {
			exit();
		}
	}

	private void exit() {
		anInt4 = -2;
		cleanUpForQuit();
		if (mainFrame != null) {
			try {
				Thread.sleep(1000L);
			} catch (Exception exception) {
			}
			try {
				System.exit(0);
			} catch (Throwable throwable) {
			}
		}
	}

	final void setFPS(int targetFPS) {
		delayTime = 1000 / targetFPS;
	}

	public final void start() {
		if (anInt4 >= 0) {
			anInt4 = 0;
		}
	}

	public final void stop() {
		if (anInt4 >= 0) {
			anInt4 = 4000 / delayTime;
		}
	}

	public final void destroy() {
		anInt4 = -1;
		try {
			Thread.sleep(5000L);
		} catch (Exception exception) {
		}
		if (anInt4 == -1) {
			exit();
		}
	}

	public final void update(Graphics g) {
		if (graphics == null) {
			graphics = g;
		}
		shouldClearScreen = true;
		raiseWelcomeScreen();
	}

	public final void paint(Graphics g) {
		if (graphics == null) {
			graphics = g;
		}
		shouldClearScreen = true;
		raiseWelcomeScreen();
	}

	public void mouseWheelMoved(MouseWheelEvent event) {
		int rotation = event.getWheelRotation();
		
		boolean interfaceScrolling = handleInterfaceScrolling(event);
		
		if (mouseX > 0 && mouseX < 512 && mouseY > Client.getClient().clientHeight - 165 && mouseY < Client.getClient().clientHeight - 25) {
			int scrollPos = Client.anInt1089;
			
			scrollPos -= rotation * 30;
			
			if (scrollPos < 0) {
				scrollPos = 0;
			}
			
			if (scrollPos > Client.anInt1211 - 110) {
				scrollPos = Client.anInt1211 - 110;
			}
			
			if (Client.anInt1089 != scrollPos) {
				Client.anInt1089 = scrollPos;
				Client.inputTaken = true;
			}

			if (Client.instance.backDialogID == -1 && ChatboxItemSearch.SEARCHING_ITEM) {
				int position = ChatboxItemSearch.scrollBarPosition + (rotation * 30);
				if (position > ChatboxItemSearch.scrollBarHeight - 114)
					position = ChatboxItemSearch.scrollBarHeight - 114;
				if(position < 0) {
					position = 0;
				}
				ChatboxItemSearch.scrollBarPosition = position;
				Client.instance.pressY -= rotation * 30;
			}
		} else if (!interfaceScrolling) {
			if (mouseX > 0 && mouseX < 512 && mouseY > Client.clientHeight - 165 && mouseY < Client.clientHeight - 25) {
				return;
			}
			if (rotation == -1) {
				if (Client.cameraZoom > 200) {
					Client.cameraZoom -= 45;
				}
			} else {
				if (Client.cameraZoom < 1100) {
					Client.cameraZoom += 45;
				}
			}

			if (event.isControlDown()) {
				if (rotation == -1) {
					int min = Client.getClient().getClientSize() == 0 ? -600 : -420;

					if (Client.clientZoom > min) {
						Client.clientZoom -= 30;
					}
				} else {
					int max = Client.getClient().getClientSize() == 0 ? 1200 : 1800;

					if (Client.clientZoom < max) {
						Client.clientZoom += 30;
					}
				}

				Client.getClient().drawZoomDelay = 100;
			}
		}
	}

	public boolean handleInterfaceScrolling(MouseWheelEvent event) {
		int rotation = event.getWheelRotation();
		int positionX = 0;
		int positionY = 0;
		int width = 0;
		int height = 0;
		int offsetX = 0;
		int offsetY = 0;
		int childID = 0;
		
		/* Tab interface scrolling */
		int tabInterfaceID = Client.getClient().tabInterfaceIDs[Client.getClient().getGameFrame().getCurrentTab()];
		
		if (tabInterfaceID == 11000) {
			tabInterfaceID = 1151;
		}
		
		if (tabInterfaceID != -1) {
			RSInterface tab = RSInterface.interfaceCache[tabInterfaceID];
			
			offsetX = Client.getClient().getClientSize() == 0 ? Client.getClient().clientWidth - 218 : (Client.getClient().getClientSize() == 0 ? 28 : Client.getClient().clientWidth - 197);
			offsetY = Client.getClient().getClientSize() == 0 ? Client.getClient().clientHeight - 298 : (Client.getClient().getClientSize() == 0 ? 37 : Client.getClient().clientHeight - (Client.getClient().clientWidth >= 900 ? 37 : 74) - 267);
			
			if (handleScrolling(tab, rotation, offsetX, offsetY)) {
				return true;
			}
		}
		
		/* Main interface scrolling */
		if (Client.openInterfaceID != -1) {
			RSInterface rsi = RSInterface.interfaceCache[Client.openInterfaceID];
			offsetX = Client.getClient().getClientSize() == 0 ? 4 : (Client.getClient().clientWidth / 2) - 256;
			offsetY = Client.getClient().getClientSize() == 0 ? 4 : (Client.getClient().clientHeight / 2) - 167;
			
			if (handleScrolling(rsi, rotation, offsetX, offsetY)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean handleScrolling(RSInterface rsi, int rotation, int offsetX, int offsetY) {
		if (rsi.children == null) {
			return false;
		}
		
		int positionX = 0;
		int positionY = 0;
		int scrollAmount = 30;

		for (int index = 0; index < rsi.children.length; index++) {
			if (RSInterface.interfaceCache[rsi.children[index]].scrollMax > 0) {
				positionX = rsi.childX[index];
				positionY = rsi.childY[index];
				
				RSInterface child = RSInterface.interfaceCache[rsi.children[index]];

				if ((mouseX > (offsetX + positionX)) && (mouseY > (offsetY + positionY)) && (mouseX < (offsetX + positionX + child.width)) && (mouseY < (offsetY + positionY + child.height))) {
					
					if (rotation < 0) {

						if (scrollAmount > child.scrollPosition) {
							scrollAmount = child.scrollPosition;
						}

					} else {
						int currPos = child.scrollPosition + child.height;

						if (scrollAmount + currPos > child.scrollMax) {
							scrollAmount = child.scrollMax - currPos;
						}
					}

					child.scrollPosition += rotation * scrollAmount;
					return true;
				}
			}
		}
		
		return false;
	}

	/**
	 * Mouse button names mapping.
	 */
	public static final int NONE = 0;
	public static final int LEFT = 1;
	public static final int MIDDLE = 2;
	public static final int RIGHT = 3;
	
	public final static int DRAG = 2;
	public final static int RELEASED = 3;
	public final static int MOVE = 4;
	
	public int releasedX;
	public int releasedY;
	public boolean mouseWheelDown;
	public boolean shiftDown;

	public final void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		int type = e.getButton();
		if (mainFrame != null) {
			Insets insets = mainFrame.getInsets();
			x -= insets.left;// 4
			y -= insets.top;// 22
		}
		resizeChatStartY = -1;
		if (Client.getClient().getClientSize() != 0) {
			int offsetY = Client.clientHeight - 165 - Client.chatAreaHeight;
			if (y >= offsetY - 10 && y <= offsetY + 10) {
				if (x <= 500) {
					resizeChatStartY = y;
				}
			}
		}
		//if (type == 2) {
		if (SwingUtilities.isMiddleMouseButton(e)) {
			mouseWheelDown = true;
			mouseWheelX = x;
			mouseWheelY = y;
			return;
		}
		
		idleTime = 0;
		clickX = x;
		clickY = y;
		clickTime = System.currentTimeMillis();
		
		if(SwingUtilities.isRightMouseButton(e)) {
			mouseReleased = MIDDLE;
			mouseDown = MIDDLE;
		} else if(SwingUtilities.isLeftMouseButton(e)){
			mouseReleased = LEFT;
			mouseDown = LEFT;
		}
		
		if (e.isShiftDown()) {
			shiftDown = true;
		}
	}

	public final void mouseReleased(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (mainFrame != null) {
			Insets insets = mainFrame.getInsets();
			x -= insets.left;// 4
			y -= insets.top;// 22
		}
		releasedX = x;
		releasedY = y;
		idleTime = 0;
		mouseDown = NONE;
		mouseWheelDown = false;
		shiftDown = false;

		//System.out.println("x="+x+",y="+y);

		if (Client.getClient().getClientSize() != 0 && resizeChatStartY != -1) {
			int offsetY = Client.clientHeight - 165 - Client.chatAreaHeight;
			int difference = y - resizeChatStartY;
			Client.chatAreaHeight = offsetY - y;
		}
	}

	public final void mouseClicked(MouseEvent mouseevent) {

	}

	public final void mouseEntered(MouseEvent mouseevent) {
	}

	public final void mouseExited(MouseEvent mouseevent) {
		idleTime = 0;
		mouseX = -1;
		mouseY = -1;
	}

	public int mouseWheelX;
	public int mouseWheelY;

	public final void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		if (mouseWheelDown) {
			y = mouseWheelX - e.getX();
			int k = mouseWheelY - e.getY();
			mouseWheelDragged(y, -k);
			mouseWheelX = e.getX();
			mouseWheelY = e.getY();
			return;
		}
		if (mainFrame != null) {
			Insets insets = mainFrame.getInsets();
			x -= insets.left;// 4
			y -= insets.top;// 22
		}
		idleTime = 0;
		mouseX = x;
		mouseY = y;

		if (Client.getClient().getClientSize() != 0 && resizeChatStartY != -1) {
			int offsetY = Client.clientHeight - 165 - Client.chatAreaHeight;
			int difference = y - resizeChatStartY;
			Client.chatAreaHeight = offsetY - y;
		}
	}

	void mouseWheelDragged(int param1, int param2) {

	}

	public final void mouseMoved(MouseEvent mouseevent) {
		int x = mouseevent.getX();
		int y = mouseevent.getY();
		if (mainFrame != null) {
			Insets insets = mainFrame.getInsets();
			x -= insets.left;// 4
			y -= insets.top;// 22
		}
		idleTime = 0;
		mouseX = x;
		mouseY = y;
	}

	int lastB = -1;
	public final void keyPressed(KeyEvent keyevent) {
		idleTime = 0;
		int keyPressed = keyevent.getKeyCode();
		int j = keyevent.getKeyChar();

		CustomisableHotKeys.checkPressedKey(keyPressed);

		if(ChatboxItemSearch.SEARCHING_ITEM && keyPressed == 10) { //enter
			if(ChatboxItemSearch.hoveredItemIndex > 0) {

				String name = ItemDefinition.forID(ChatboxItemSearch.ITEM_RESULT_IDS[ChatboxItemSearch.hoveredItemIndex]).name;

				//send item to server
				Client.stream.createFrame(182);

				Client.stream.writeWordBigEndian(name.length() + 1);
				Client.stream.writeString(name);

			}
			ChatboxItemSearch.closeItemSearch();
		}

		boolean isMacOs = (System.getProperty("os.name").toLowerCase().contains("mac"));

		boolean keyDown = false;
		if (isMacOs) {
			if(keyevent.isMetaDown() && keyevent.getKeyCode() == KeyEvent.VK_V) {
				keyDown = true;
			}
		} else {
			if ((keyevent.isControlDown() && keyevent.getKeyCode() == KeyEvent.VK_V)) {
				keyDown = true;
			}
		}

		if(keyDown) { //v
			char text[] = getClipBoard().toCharArray();
			for(char character : text) {

				if (character > 0 && character < 256)
					keyArray[character] = 1;

				if (character > 4) {
					charQueue[writeIndex] = character;
					writeIndex = writeIndex + 1;
					if(writeIndex >= 256) {
						writeIndex = 0;
					}
					Client.instance.manageTextInput();
				}
			}
			return;
		}

		if(keyPressed == 17)
			lastB = 1;
		else if(keyPressed != 83)
			lastB = 0;
		if(keyPressed == 83 && lastB > 0) {
			if(Client.loggedIn) {
				if(Client.openInterfaceID == -1) {
					Client.stream.createFrame(185);
					Client.stream.writeWord(10000);
				}
			}
		}
		/*if (hotKey == 508) {
			if (i == KeyEvent.VK_ESCAPE) {
				Client.setTab(3);
			} else if (i == KeyEvent.VK_F1) {
				Client.setTab(3);
			} else if (i == KeyEvent.VK_F2) {
				Client.setTab(4);
			} else if (i == KeyEvent.VK_F3) {
				Client.setTab(5);
			} else if (i == KeyEvent.VK_F4) {
				Client.setTab(6);
			} else if (i == KeyEvent.VK_F5) {
				Client.setTab(0);
			}
		} else {
			if (i == KeyEvent.VK_ESCAPE) {
				Client.setTab(0);
			} else if (i == KeyEvent.VK_F1) {
				Client.setTab(3);
			} else if (i == KeyEvent.VK_F2) {
				Client.setTab(4);
			} else if (i == KeyEvent.VK_F3) {
				Client.setTab(5);
			} else if (i == KeyEvent.VK_F4) {
				Client.setTab(6);
			} else if (i == KeyEvent.VK_F5) {
				Client.setTab(0);
			}
		}*/
		if (j < 30)
			j = 0;
		if (keyPressed == 37)
			j = 1;
		if (keyPressed == 39)
			j = 2;
		if (keyPressed == 38)
			j = 3;
		if (keyPressed == 40)
			j = 4;
		if (keyPressed == 17)
			j = 5;
		if (keyPressed == 8)
			j = 8;
		if (keyPressed == 127)
			j = 8;
		if (keyPressed == 9)
			j = 9;
		if (keyPressed == 10)
			j = 10;
		if (keyPressed >= 112 && keyPressed <= 123)
			j = (1008 + keyPressed) - 112;
		if (keyPressed == 36)
			j = 1000;
		if (keyPressed == 35)
			j = 1001;
		if (keyPressed == 33)
			j = 1002;
		if (keyPressed == 34)
			j = 1003;
		if (j > 0 && j < 256)
			keyArray[j] = 1;
		if (j > 4) {
			charQueue[writeIndex] = j;
			writeIndex = writeIndex + 1;
			if(writeIndex >= 256) {
				writeIndex = 0;
			}
		}

	}

	public String getClipBoard(){
		try {
			return (String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		} catch (HeadlessException | UnsupportedFlavorException | IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public void replyLastPM()
	{

	}
	public final void keyReleased(KeyEvent keyevent) {
		idleTime = 0;
		int i = keyevent.getKeyCode();
		char c = keyevent.getKeyChar();
		if (i == 17) {
			resizing = false;
		}
		if (c < '\036') {
			c = '\0';
		}
		if (i == 37) {
			c = '\001';
		}
		if (i == 39) {
			c = '\002';
		}
		if (i == 38) {
			c = '\003';
		}
		if (i == 40) {
			c = '\004';
		}
		if (i == 17) {
			c = '\005';
		}
		if (i == 8) {
			c = '\b';
		}
		if (i == 127) {
			c = '\b';
		}
		if (i == 9) {
			c = '\t';
		}
		if (i == 10) {
			c = '\n';
		}
		if (c > 0 && c < '\200') {
			keyArray[c] = 0;
		}
	}

	public final void keyTyped(KeyEvent keyevent) {
	}

	public final int readChar(int i) {
		while (i >= 0) {
			int j = 1;
			while (j > 0) {
				j++;
			}
		}
		int k = -1;
		if (writeIndex != readIndex) {
			k = charQueue[readIndex];
			readIndex = readIndex + 1;
			if(readIndex >= 256) {
				readIndex = 0;
			}
		}
		return k;
	}

	public final void focusGained(FocusEvent focusevent) {
		awtFocus = true;
		shouldClearScreen = true;
		raiseWelcomeScreen();
	}

	public final void focusLost(FocusEvent focusevent) {
		awtFocus = false;
		for (int i = 0; i < 256; i++) {
			keyArray[i] = 0;
		}

	}

	public final void windowActivated(WindowEvent windowevent) {
	}

	public final void windowClosed(WindowEvent windowevent) {
	}

	public final void windowClosing(WindowEvent windowevent) {
		 if(RSFrame.destroy) {
			 destroy();
		 }

	}

	public final void windowDeactivated(WindowEvent windowevent) {
	}

	public final void windowDeiconified(WindowEvent windowevent) {
	}

	public final void windowIconified(WindowEvent windowevent) {
	}

	public final void windowOpened(WindowEvent windowevent) {
	}

	void startUp() {
	}

	void processGameLoop() {
	}

	void cleanUpForQuit() {
	}

	void processDrawing() {
	}

	void raiseWelcomeScreen() {
	}

	Component getGameComponent() {
		if (mainFrame != null && !isApplet) {
			return mainFrame;
		} else {
			return this;
		}
	}

	public void startRunnable(Runnable runnable, int i) {
		Thread thread = new Thread(runnable);
		thread.start();
		thread.setPriority(i);
	}

	private static final Color FONT_COLOR = Color.white;
	private static final Font LOADING_FONT = new Font("Helvetica", 1, 13);
	private static final int barWidth = 300;
	private static final int barHeight = 30;
	private static final int barSpace = 2;
	private static final int barMax = barSpace + barHeight + barSpace
			+ barWidth + barSpace + barHeight + barSpace;

	private Image loadingBuffer;

	private static void options(Graphics g) {
		try {
			if (g instanceof Graphics2D) {
				Graphics2D r = (Graphics2D) g;
				r.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				r.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			}
		} catch (Throwable t) {
		}
	}

	private static int randomColor() {
		int red = (int) (Math.random() * 17.0D);
		int green = (int) (Math.random() * 17.0D);
		int blue = (int) (Math.random() * 17.0D);
		int type = (int) (Math.random() * 8.0D);
		if (type == 0)
			type |= 1 << (int) (Math.random() * 3.0D);
		else if (type == 7)
			type &= ~(1 << (int) (Math.random() * 3.0D));

		if ((type & 0x1) != 0)
			red += 196 + (int) (Math.random() * 33.0D);

		if ((type & 0x2) != 0)
			green += 196 + (int) (Math.random() * 33.0D);

		if ((type & 0x4) != 0)
			blue += 196 + (int) (Math.random() * 33.0D);

		return (red << 16) | (green << 8) | blue;
	}

	private long startTime = 0L;
	private long colorStart = 0L;

	private static int blend(int dst, int src, int src_alpha) {
		if (src_alpha <= 0)
			return dst;

		if (src_alpha >= 0xff)
			return src;

		int src_delta = 0xff - src_alpha;
		return ((0xff00ff00 & (0xff00ff & src) * src_alpha | 0xff0000
				& (src & 0xff00) * src_alpha) >>> 8)
				+ (((0xff0000 & src_delta * (dst & 0xff00) | src_delta
						* (dst & 0xff00ff) & 0xff00ff00) >>> 8));
	}

	private static final Color BACKGROUND_COLOR = Color.black;
	private static final Color LOADING_COLOR = new Color(140, 17, 17);

	void prepareGraphics() {
		while (graphics == null) {
			graphics = (isApplet ? this : mainFrame).getGraphics();
			try {
				getGameComponent().repaint();
			} catch (Exception _ex) {
			}

			try {
				// Thread.sleep(1000L);
			} catch (Exception _ex) {
			}
		}
		Font font = new Font("Serif", 1, 16);
		graphics.setFont(font);
		graphics.setColor(Color.white);
	}
	
	void resetGraphic() {
		graphics = (isApplet ? this : mainFrame).getGraphics();
		try {
			getGameComponent().repaint();
		} catch (Exception _ex) {
		}
	}

	RSApplet() {
		resizing = false;
		delayTime = 20;
		minDelay = 1;
		shouldDebug = false;
		shouldClearScreen = true;
		awtFocus = true;
	}
	
	void drawLoadingText(int i, String s)
	{
		while(graphics == null)
		{
			graphics = getGameComponent().getGraphics();
			try
			{
				getGameComponent().repaint();
			}
			catch(Exception _ex) { }
			try
			{
				Thread.sleep(1000L);
			}
			catch(Exception _ex) { }
		}
		
		options(graphics);
		Font font = new Font("Arial", 1, 13);
		FontMetrics fontmetrics = getGameComponent().getFontMetrics(font);
		Font font1 = new Font("Arial", 0, 13);
		getGameComponent().getFontMetrics(font1);
		if(shouldClearScreen)
		{
			graphics.setColor(Color.black);
			graphics.fillRect(0, 0, myWidth, myHeight);
			shouldClearScreen = false;
		}
		Color color = new Color(163, 82, 4);
		int j = myHeight / 2 - 18;
		graphics.setColor(color);
		graphics.drawRect(myWidth / 2 - 152, j, 304, 34);
		graphics.fillRect(myWidth / 2 - 150, j + 2, i * 3, 30);
		graphics.setColor(Color.black);
			graphics.fillRect((myWidth / 2 - 150) + i * 3, j + 2, 300 - i * 3, 30);
			graphics.setFont(font);
			graphics.setColor(Color.white);
			for(int x = 0; x < 2; x ++) {
				graphics.drawString(s, (myWidth - fontmetrics.stringWidth(s)) / 2, j + 22);
			}
	}

}
