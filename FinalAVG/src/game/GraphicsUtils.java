package game;



import java.awt.AlphaComposite;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.WeakHashMap;

import javax.swing.GrayFilter;
import javax.swing.ImageIcon;

import resource.LSystem;
/**
 * 处理图片的静态方法
 *
 */
@SuppressWarnings("rawtypes")
public class GraphicsUtils {

	final static public Toolkit toolKit = Toolkit.getDefaultToolkit();
	
	/**
	 * 
	 */
	final static private Map cacheImages = new WeakHashMap(100);
	/**
	 * 获得上下文的类加载器
	 */
	final static private ClassLoader classLoader = Thread.currentThread()
			.getContextClassLoader();

	final static String newLine = "\r\n";
	/**
	 * 构造器私有
	 */
	private GraphicsUtils() {
	}
	/**
	 * 获得当前字体
	 */
	public static Font getFont() {
		return getFont(LSystem.FONT, LSystem.FONT_TYPE);
	}
	
	public static Font getFont(int size) {
		return getFont(LSystem.FONT, size);
	}

	public static Font getFont(String fontName, int size) {
		return getFont(fontName, 0, size);
	}

	public static Font getFont(String fontName, int style, int size) {
		return new Font(fontName, style, size);
	}
	/**
	 * 画文本
	 * @param graphics
	 * @param message
	 * @param i
	 * @param j
	 * @param color
	 * @param color1
	 */
	public static void drawStyleString(final Graphics graphics,
			final String message, final int i, final int j, final Color color,
			final Color color1) {

		graphics.setColor(color);
		graphics.drawString(message, i + 1, j);
		graphics.drawString(message, i - 1, j);
		graphics.drawString(message, i, j + 1);
		graphics.drawString(message, i, j - 1);
		graphics.setColor(color1);
		graphics.drawString(message, i, j);

	}
	
	/**
	 * 获得图片
	 * @param data
	 * @return
	 */
	final static public BufferedImage getImage(int[] data) {
		if (data == null || data.length < 3 || data[0] < 1 || data[1] < 1) {
			return null;
		}
		int width = data[0];
		int height = data[1];
		if (data.length < 2 + width * height) {
			return null;
		}
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_BGR);
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				image.setRGB(j, i, data[2 + j + i * width]);
			}
		}
		return image;
	}
	/**
	 * 
	 * @param bufferedimage
	 * @param w
	 * @param h
	 * @return
	 */
	public static Image getResize(Image bufferedimage, int w, int h) {
		BufferedImage result = null;
		Graphics2D graphics2d;
		(graphics2d = (result = GraphicsUtils.createImage(w, h, true))
				.createGraphics()).setRenderingHint(
				RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2d.drawImage(bufferedimage, 0, 0, w, h, 0, 0, bufferedimage
				.getWidth(null), bufferedimage.getHeight(null), null);
		graphics2d.dispose();
		return result;
	}

	/**
	 * 分割图片
	 * @param fileName
	 * @param row
	 * @param col
	 * @return
	 */
	public static Image[] getSplitImages(String fileName, int row, int col) {
		return getSplitImages(fileName, row, col, true);
	}

	/**
	 * 分割图片
	 * 
	 * @param fileName
	 * @param row
	 * @param col
	 * @return
	 */
	public static Image[] getSplitImages(String fileName, int row, int col,
			boolean isFiltrate) {
		Image image = GraphicsUtils.loadImage(fileName);
		
		return getSplitImages(image, row, col, isFiltrate);
	}

	/**
	 * 分割图片
	 * 
	 * @param image
	 * @param row
	 * @param col
	 * @return
	 */
	public static Image[] getSplitImages(Image image, int row, int col,
			boolean isFiltrate) {
		int index = 0;
		int wlenght = image.getWidth(null) / row;
		int hlenght = image.getHeight(null) / col;
		int l = wlenght * hlenght;
		Image[] abufferedimage = new Image[l];
		for (int y = 0; y < hlenght; y++) {
			for (int x = 0; x < wlenght; x++) {
				abufferedimage[index] = GraphicsUtils.createImage(row, col, true);
				Graphics g = abufferedimage[index].getGraphics();
				g.drawImage(image, 0, 0, row, col, (x * row), (y * col), row
						+ (x * row), col + (y * col), null);
				g.dispose();
				//闁繑妲戦崠鏍ь槱閻烇拷
				PixelGrabber pgr = new PixelGrabber(abufferedimage[index], 0,
						0, -1, -1, true);
				try {
					pgr.grabPixels();
				} catch (InterruptedException ex) {
					ex.getStackTrace();
				}
				int pixels[] = (int[]) pgr.getPixels();
				if (isFiltrate) {
					// 瀵邦亞骞嗛崓蹇曠
					for (int i = 0; i < pixels.length; i++) {
						// 閸樻槒澹�
						LColor color = LColor.getLColor(pixels[i]);
						if ((color.R == 247 && color.G == 0 && color.B == 255)
								|| (color.R == 255 && color.G == 255 && color.B == 255)) {
							// 闁繑妲戦崠锟�
							pixels[i] = 0;
						}
					}
				}
				ImageProducer ip = new MemoryImageSource(pgr.getWidth(), pgr
						.getHeight(), pixels, 0, pgr.getWidth());
				abufferedimage[index] = toolKit.createImage(ip);
				index++;
			}
		}
		return abufferedimage;
	}


	/**
	 * 获得图片的切割
	 * @param image 源图片
	 * @param objectWidth 图片宽
	 * @param objectHeight 图片高
	 * @param x1 所要切出来的图片左坐标
	 * @param y1所要切出来的图片上坐标
	 * @param x2所要切出来的图片右坐标
	 * @param y2所要切出来的图片下坐标
	 * @return
	 */
	public static Image drawClipImage(final Image image, int objectWidth,
			int objectHeight, int x1, int y1, int x2, int y2) {
		BufferedImage buffer = GraphicsUtils.createImage(objectWidth,
				objectHeight, true);
		Graphics g = buffer.getGraphics();
		Graphics2D graphics2D = (Graphics2D) g;
		graphics2D.drawImage(image, 0, 0, objectWidth, objectHeight, x1, y1,
				x2, y2, null);
		graphics2D.dispose();
		graphics2D = null;
		return buffer;
	}
	/**
	 * 获得图片的切割
	 * @param image 源图片
	 * @param objectWidth 图片宽
	 * @param objectHeight 图片高
	 * @param x1 所要切出来的图片左坐标
	 * @param y1所要切出来的图片上坐标
	 * @return
	 */
	public static Image drawClipImage(final Image image, int objectWidth,
			int objectHeight, int x, int y) {
		BufferedImage buffer = GraphicsUtils.createImage(objectWidth,
				objectHeight, true);
		Graphics g = buffer.getGraphics();
		Graphics2D graphics2D = (Graphics2D) g;
		graphics2D.drawImage(image, 0, 0, objectWidth, objectHeight, x, y, x
				+ objectWidth, objectHeight + y, null);
		graphics2D.dispose();
		graphics2D = null;
		return buffer;
	}

	/**
	 * 翻转图片
	 * 
	 * @return
	 */
	public static BufferedImage rotateImage(final BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();
		BufferedImage img;
		Graphics2D graphics2d;
		(graphics2d = (img = new BufferedImage(w, h, image.getColorModel()
				.getTransparency())).createGraphics()).drawImage(image, 0, 0,
				w, h, w, 0, 0, h, null);//getTransparency娑撻缚骞忓妤咃拷蹇旀鎼达拷
		graphics2d.dispose();
		return img;
	}
	public static BufferedImage rotateImage(final Image image) {
		int w = image.getWidth(null);
		int h = image.getHeight(null);
		BufferedImage img;
		Graphics2D graphics2d;
		(graphics2d = (img = new BufferedImage(w, h, 2)).createGraphics()).drawImage(image, 0, 0,
				w, h, w, 0, 0, h, null);
		graphics2d.dispose();
		return img;
	}
	/**
	 * 旋转图片
	 * @param image 
	 * @param angdeg  角度
	 * @param d  方向
	 * @return  
	 */
	public static BufferedImage rotateImage(final Image image,
			final int angdeg, final boolean d) {
		int w = image.getWidth(null);
		int h = image.getHeight(null);
		BufferedImage img;
		Graphics2D graphics2d;
		(graphics2d = (img = GraphicsUtils.createImage(w, h, true))
				.createGraphics()).setRenderingHint(
				RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2d.rotate(d ? -Math.toRadians(angdeg) : Math.toRadians(angdeg),
				w / 2, h / 2);
		graphics2d.drawImage(image, 0, 0, null);
		graphics2d.dispose();
		return img;
	}

	/**
	 * 画切割的图片
	 * 
	 * @param source
	 * @param src_x
	 * @param src_y
	 * @param desc_width
	 * @param desc_height
	 * @return
	 */
	public static BufferedImage drawClip(String source, int src_x, int src_y,
			int desc_width, int desc_height) {
		return GraphicsUtils.drawClip(GraphicsUtils.getBufferImage(GraphicsUtils
				.loadImage(source)), src_x, src_y, desc_width, desc_height);

	}

	public static void drawString(String s, final Graphics g, int i, int j,
			int k) {
		Graphics2D graphics2D = (Graphics2D) g;
		Font font = graphics2D.getFont();
		int size = graphics2D.getFontMetrics(font).stringWidth(s);
		GraphicsUtils.setAlpha(g, 0.9f);
		graphics2D.drawString(s, i + (k - size) / 2, j);
		GraphicsUtils.setAlpha(g, 1.0f);
	}

	public static void drawString(String s, final Graphics2D graphics2D, int i,
			int j, int k) {
		Font font = graphics2D.getFont();
		int size = graphics2D.getFontMetrics(font).stringWidth(s);
		GraphicsUtils.setAlpha(graphics2D, 0.9f);
		graphics2D.drawString(s, i + (k - size) / 2, j);
		GraphicsUtils.setAlpha(graphics2D, 1.0f);
	}

	/**
	 * 
	 * @param message
	 * @param fontName
	 * @param g
	 * @param x1
	 * @param y1
	 * @param style
	 * @param size
	 */
	public static void drawString(String message, String fontName,
			final Graphics g, int x1, int y1, int style, int size) {
		Graphics2D graphics2D = (Graphics2D) g;
		graphics2D.setFont(new Font(fontName, style, size));
		GraphicsUtils.setAlpha(g, 0.9f);
		graphics2D.drawString(message, x1, y1);
		GraphicsUtils.setAlpha(g, 1.0f);
	}

	/**
	 * 
	 * @param message
	 * @param g
	 * @param x1
	 * @param y1
	 * @param font
	 * @param fontSize
	 */
	public static void drawDefaultString(String message, final Graphics2D g,
			int x1, int y1, int font, int fontSize) {
		g.setFont(new Font("閸楀孩鏋冮弬浼寸摌", font, fontSize));
		g.drawString(message, x1, y1);
	}

	/**
	 * 
	 * 
	 * @param image
	 * @return
	 */
	public static Image getGray(final Image image) {

		ImageFilter filter = new GrayFilter(true, 25);
		ImageProducer imageProducer = new FilteredImageSource(getBufferImage(
				image).getSource(), filter);

		return toolKit.createImage(imageProducer);

	}

	public static BufferedImage getBufferImage(final Image image) {
		BufferedImage bufferimage = GraphicsUtils.createImage(
				image.getWidth(null), image.getHeight(null), true);
		Graphics g = bufferimage.getGraphics();
		g.drawImage(image, 0, 0, null);
		return bufferimage;
	}

	public Image drawClip(final Image image, int objectWidth, int objectHeight,
			int x1, int y1, int x2, int y2) throws Exception {
		BufferedImage buffer = GraphicsUtils.createImage(objectWidth,
				objectHeight, true);
		Graphics g = buffer.getGraphics();
		Graphics2D graphics2D = (Graphics2D) g;
		graphics2D.drawImage(image, 0, 0, objectWidth, objectHeight, x1, y1,
				x2, y2, null);
		graphics2D.dispose();
		graphics2D = null;
		return buffer;
	}

	/**
	 * 画切割图片
	 * 
	 * @param image
	 * @param src_x
	 * @param src_y
	 * @param desc_width
	 * @param desc_height
	 * @return
	 */
	public static BufferedImage drawClip(final Image image, int src_x,
			int src_y, int desc_width, int desc_height) {
		BufferedImage thumbImage = new BufferedImage(desc_width, desc_height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = thumbImage.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.drawImage(image, 0, 0, desc_width, desc_height, src_x,
				src_y, image.getWidth(null), image.getHeight(null), null);

		graphics2D.dispose();

		return thumbImage;

	}

	public final static InputStream getResource(final String innerFileName) {
		return new BufferedInputStream(classLoader
				.getResourceAsStream(innerFileName));
	}

	/**
	 * 加载图片
	 * 
	 * @param inputstream
	 * @return
	 */
	@SuppressWarnings("unchecked")
	final static public Image loadImage(final String innerFileName) {
//		String keyName = innerFileName.toLowerCase();
//		Image cacheImage = (Image) cacheImages.get(keyName);
//		
//		if(cacheImage == null){
//			
		Image	cacheImage = new ImageIcon(innerFileName).getImage();
//			cacheImages.put(keyName	, cacheImage);
//			
//			waitImage(100, cacheImage);
//		}
		System.out.println(innerFileName);
		return cacheImage;
		}

	/**
	 * 等待直至图片加载完毕
	 * 
	 * @param delay
	 * @param image
	 */
	private final static void waitImage(int delay, Image image) {
		try {
			for (int i = 0; i < delay; i++) {
				if (toolKit.prepareImage(image, -1, -1, null)) {
					return;
				}
				Thread.sleep(delay);
			}
		} catch (Exception e) {

		}
	}

	/**
	 * 创建缓存图片
	 * 
	 * @param i
	 * @param j
	 * @param flag
	 * @return
	 */
	final static public BufferedImage createImage(int i, int j, boolean flag) {
		if (flag) {
			return new BufferedImage(i, j, 2);
		} else {
			return new BufferedImage(i, j, 1);
		}

	}

	/**
	 * 等待图片加载
	 * 
	 * @param ms
	 */
	final static public void wait(final int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException ex) {
		}
	}


	/**
	 * 设置透明度
	 * 
	 * @param g
	 * @param d  参数为百分之几
	 */
	final static public void setAlpha(Graphics g, double d) {
		AlphaComposite alphacomposite = AlphaComposite
				.getInstance(3, (float) d);
		((Graphics2D) g).setComposite(alphacomposite);
	}



}