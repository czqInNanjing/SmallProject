package game;
import java.awt.Cursor;
import java.awt.Image;


/**
 * ¥¥Ω® Û±Í
 *
 */
public class GameCursor {

	public static Cursor getCursor(String fileName) {
		Image cursor = GraphicsUtils.loadImage(fileName);
		return java.awt.Toolkit.getDefaultToolkit().createCustomCursor(
				cursor,
				new java.awt.Point(0, 0), fileName);
	}

}
