package com.varrock.client;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileOperations {

    public FileOperations()  {
    }

    public static final byte[] ReadFile(String s) {
        try  {
            File file = new File(s);
            int i = (int)file.length();
            byte abyte0[] = new byte[i];
            DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new FileInputStream(s)));
            datainputstream.readFully(abyte0, 0, i);
            datainputstream.close();
            TotalRead++;
            return abyte0;
        } catch(Exception exception) {
        }
        return null;
    }

    public static final void WriteFile(String s, byte abyte0[]) {
        try {
            (new File((new File(s)).getParent())).mkdirs();
            FileOutputStream fileoutputstream = new FileOutputStream(s);
            fileoutputstream.write(abyte0, 0, abyte0.length);
            fileoutputstream.close();
            TotalWrite++;
            CompleteWrite++;
        }  catch(Throwable throwable) {
            System.out.println((new StringBuilder()).append("Write Error: ").append(s).toString());
        }
    }
	
	public static boolean FileExists(String file) {
		File f = new File(file);
		if(f.exists())
			return true;
		else
			return false;
	}
	
    public static byte[] readFile(String name) {
		try {
			RandomAccessFile raf = new RandomAccessFile(name, "r");
			ByteBuffer buf = raf.getChannel().map(
					FileChannel.MapMode.READ_ONLY, 0, raf.length());
			try {
				if (buf.hasArray()) {
					return buf.array();
				} else {
					byte[] array = new byte[buf.remaining()];
					buf.get(array);
					return array;
				}
			} finally {
				raf.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

    public static void imageToFile(Sprite image, int itemId, File folder) {
        BufferedImage bi = new BufferedImage(image.myWidth, image.myHeight, BufferedImage.TYPE_INT_RGB);
        bi.setRGB(0, 0, image.myWidth, image.myHeight, image.myPixels, 0, image.myWidth);
        Image img = makeColorTransparent(bi, new Color(0, 0, 0));
        BufferedImage trans = imageToBufferedImage(img);
        try {
            File out = new File(folder.getAbsolutePath() + "/" + itemId + ".png");
            ImageIO.write(trans, "png", out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage imageToBufferedImage(Image image) {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return bufferedImage;
    }

    public static Image makeColorTransparent(BufferedImage im, final Color color) {
        RGBImageFilter filter = new RGBImageFilter() {
            public int markerRGB = color.getRGB() | 0xFF000000;
            public final int filterRGB(int x, int y, int rgb) {
                if ((rgb | 0xFF000000) == markerRGB) {
                    return 0x00FFFFFF & rgb;
                } else {
                    return rgb;
                }
            }
        };
        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }

    public static void trimImage(String file) {
        BufferedImage img;
        try {
            img = ImageIO.read(new File(signlink.findcachedir() + file));
            img = trimImage(img);
           /* if(img.getWidth() > 64 || img.getHeight() > 64) {
                img = Scalr.resize(img, 64);
            }*/
            try {
                File out = new File(signlink.findcachedir() + file);
                ImageIO.write(img, "png", out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
        }
    }

    public static BufferedImage trimImage(BufferedImage img) {
        final byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
        int width = img.getWidth();
        int height = img.getHeight();
        int x0, y0, x1, y1;                      // the new corners of the trimmed image
        int i, j;                                // i - horizontal iterator; j - vertical iterator
        leftLoop:
        for (i = 0; i < width; i++) {
            for (j = 0; j < height; j++) {
                if (pixels[(j * width + i) * 4] != 0) { // alpha is the very first byte and then every fourth one
                    break leftLoop;
                }
            }
        }
        x0 = i;
        topLoop:
        for (j = 0; j < height; j++) {
            for (i = 0; i < width; i++) {
                if (pixels[(j * width + i) * 4] != 0) {
                    break topLoop;
                }
            }
        }
        y0 = j;
        rightLoop:
        for (i = width - 1; i >= 0; i--) {
            for (j = 0; j < height; j++) {
                if (pixels[(j * width + i) * 4] != 0) {
                    break rightLoop;
                }
            }
        }
        x1 = i + 1;
        bottomLoop:
        for (j = height - 1; j >= 0; j--) {
            for (i = 0; i < width; i++) {
                if (pixels[(j * width + i) * 4] != 0) {
                    break bottomLoop;
                }
            }
        }
        y1 = j + 1;
        return img.getSubimage(x0, y0, x1 - x0, y1 - y0);
    }

    public static int TotalRead = 0;
    public static int TotalWrite = 0;
    public static int CompleteWrite = 0;
}