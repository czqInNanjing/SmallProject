/*
 *
 * TODO load the image to memory when this program is loaded, then program can
 * use image from memory directly
 */
package edu.nju.view;

import java.awt.Image;
import java.io.FileInputStream;
 


import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


import edu.nju.model.state.DisplayBlockState;
 
public class Images {
	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path){
		ImageIcon icon = new ImageIcon(path);
		return icon;
	}

	protected static Image createImage(String path) {
		try { 
			Image image = ImageIO.read(new FileInputStream(path));
			return image;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return null;
	}

	public static final Image FRAME_IMAGE = createImage("images/mainFrameIcon.gif");
	
	public static final Image BACKGROUND = createImage("images/bg.jpg");

	public static final ImageIcon START_BEGIN = createImageIcon("images/START_BEGIN.gif");

	public static final ImageIcon START_RUN = createImageIcon("images/START_RUN.gif");

	public static final ImageIcon START_END = createImageIcon("images/START_END.gif");
	
	public static final ImageIcon START_WIN = createImageIcon("images/mainFrameIcon.gif");

//	public static final ImageIcon[] number = { createImageIcon("images/1.gif"),
//			createImageIcon("images/2.gif"), createImageIcon("images/3.gif"),
//			createImageIcon("images/4.gif"), createImageIcon("images/5.gif"),
//			createImageIcon("images/6.gif"), createImageIcon("images/7.gif"),
//			createImageIcon("images/8.gif") };
	public static final ImageIcon[] number = { createImageIcon("images/1.png"),
		createImageIcon("images/2.png"), createImageIcon("images/3.png"),
		createImageIcon("images/4.png"), createImageIcon("images/5.png"),
		createImageIcon("images/6.png"), createImageIcon("images/7.png"),
		createImageIcon("images/8.png") };
	
	public static final ImageIcon MINE = createImageIcon("images/MINE.png");

	public static final ImageIcon MINE_WRONG = createImageIcon("images/MINE_WRONG.png");

	public static final ImageIcon MINE_BLAST = createImageIcon("images/MINE_BLAST.gif");

	public static final ImageIcon CLICKED = createImageIcon("images/CLICKED.png");

	public static final ImageIcon UNCLICKED = createImageIcon("images/UNCLICKED.png");

	public static final ImageIcon MARKED = createImageIcon("images/MARKED.gif");

	public static final ImageIcon MARKED_MINE = createImageIcon("images/MARKED_MINE.png");
	
	public static final ImageIcon MARKED_MINE1 = createImageIcon("images/MARKED_MINE1.jpg");
	
	public static final ImageIcon MARKED_MINE2 = createImageIcon("images/MARKED_MINE2.jpg");
	
	public static final ImageIcon ICON = createImageIcon("images/ICON.png");
	public static final ImageIcon WAIT = createImageIcon("images/WAIT.png");
	public static ImageIcon getImageIconByState(DisplayBlockState state){
		switch (state) {
		case UNCLICK:
			return UNCLICKED;
		case ZERO:
			return CLICKED;
		case ONE:
			return number[0];
		case TWO:
			return number[1];
		case THREE:
		    return number[2];
		case FOUR:
			return number[3];
		case FIVE: 
			return number[4];
		case SIX:
			return number[5];
		case SEVEN:
			return number[6];
		case EIGHT:
			return number[7];
		case FLAG:
			return MARKED_MINE;
		case MINE:
			return MINE;
		case Bomb:
			return MINE_BLAST;
		case ERROFLAG:
			return MINE_WRONG;
		case CLIENT_FLAG:
			return MARKED_MINE1;
		case HOST_FLAG:
			return MARKED_MINE2;
		default:
			break;
		}
		return UNCLICKED;
	}
 
}