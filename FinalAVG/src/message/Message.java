package message;

import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import game.GraphicsUtils;
import resource.BackgroundResource;
import resource.BaleResource;
import resource.LSystem;


/**
 * 
 * @version 0.1
 */
@SuppressWarnings("rawtypes")
public class Message {

	
	final static private Map lazyImages = new HashMap(10);
	/**
	 * 对话框的宽度
	 */
	final static private int space_width = 10;
	/**
	 * 对话框的高度
	 */
	final static private int space_height = 7;
	/**
	 * 对话框的大小
	 */
	final static private int space_size = 24;



	/**
	 * 绘制对话框
	 * 
	 * @param obj
	 * @param g
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public static void setWindowFrame(Graphics g, int x1, int y1, int x2, int y2) {
		Image frame = Message.loadFrameWindow(x2 - x1, y2 - y1);
		g.drawImage(frame, x1, y1, null);
	}

	public static void setWindowBuoyageMessage(Graphics g, int x, int y) {
		g.drawImage(BaleResource.creeseIcon, x, y, null);
	}


	/**
	 * 加载对话框 
	 * 
	 * @param showWidth
	 * @param showHeight
	 * @param nowDraw
	 * @return
	 */
	public final static Image loadFrameWindow(int width, int height) {
		String keyName = width + "|" + height;
		Image lazyImage = (Image) lazyImages.get(keyName);
		if (lazyImage == null) {
			// 
			int objWidth = 64;
			int objHeight = 64;
			int x1 = 128;
			int x2 = 192;
			int y1 = 0;
			int y2 = 64;
			Image image = null;
			Image messageImage = null;
			try {
				image = GraphicsUtils.drawClipImage(
						LSystem.windowCanvas, objWidth, objHeight,
						x1, y1, x2, y2);
				//锟侥憋拷锟斤拷锟街憋拷锟斤拷
				messageImage = GraphicsUtils.drawClipImage(
						LSystem.windowCanvas, 128, 128, 0, 0, 128,
						128);
			} catch (Exception e) {
				e.printStackTrace();
			}

			MessageDialogSplit mds = new MessageDialogSplit(image, space_width,
					space_height, space_size);
			mds.split();
			int doubleSpace = space_size * 2;
			if (width < doubleSpace) {
				width = doubleSpace + 5;
			}
			if (height < doubleSpace) {
				height = doubleSpace + 5;
			}
			lazyImage = GraphicsUtils.createImage(width - 10, height, true);
			Graphics graphics = lazyImage.getGraphics();
			messageImage = GraphicsUtils.getResize(messageImage, width
					- space_width * 2, height - space_height);
			graphics.drawImage(messageImage, 5, 5, null);
			graphics.drawImage(mds.getLeftUpImage(), 0, 0, null);
			graphics.drawImage(mds.getRightUpImage(),
					(width - space_size - space_width), 0, null);
			graphics.drawImage(mds.getLeftDownImage(), 0,
					(height - space_size), null);
			graphics.drawImage(mds.getRightDownImage(),
					(width - space_size - space_width), (height - space_size),
					null);

			int nWidth = width - doubleSpace;
			int nHeight = height - doubleSpace;
			graphics.drawImage(GraphicsUtils.getResize(mds.getUpImage(), nWidth,
					space_size), space_size, 0, null);
			graphics.drawImage(GraphicsUtils.getResize(mds.getDownImage(), nWidth,
					space_size), space_size, (height - space_size), null);
			graphics.drawImage(GraphicsUtils.getResize(mds.getLeftImage(),
					space_size, nHeight), 0, space_size, null);
			graphics.drawImage(GraphicsUtils.getResize(mds.getRightImage(),
					space_size, nHeight), (width - space_size - space_width),
					space_size, null);
			graphics.dispose();
			lazyImages.put(keyName, lazyImage);
		}
		return lazyImage;
	}

	
}