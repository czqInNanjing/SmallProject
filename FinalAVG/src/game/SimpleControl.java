package game;



import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

/**
 * 一个将被用来继承的抽象类
 * 提供了监听，作画等功能
 * @author czq
 *
 */
public abstract class SimpleControl implements IControl {

	protected static IControl currentControl;

	protected static boolean isControlOfNull;

	public static Point mouse = new Point(0, 0);

	public static Random rand = new Random();

	public static boolean left_click;

	public static boolean right_click;

	public static boolean fg_click;

	public SimpleControl() {
	}
	

	public synchronized void setCurrentControl(final IControl control) {
		SimpleControl.currentControl = control;
		SimpleControl.isControlOfNull = (SimpleControl.currentControl == null);
	}

	public synchronized void removeCurrentControl() {
		SimpleControl.currentControl = null;
		SimpleControl.isControlOfNull = true;
	}
	/**
	 * 是否能控制
	 * @return
	 */
	public static synchronized boolean validControl() {
		return !SimpleControl.isControlOfNull;
	}
	
	public void next() {
	}

	public void setFrame(int i) {
	}
	
	public void mouseClicked(MouseEvent e) {
		mouse = e.getPoint();
	}
	//判断是哪个键被按下
	public void mousePressed(MouseEvent e) {
		mouse = e.getPoint();
		if (e.getButton() == 1) {
			SimpleControl.left_click = true;
		}
		if (e.getButton() == 3) {
			SimpleControl.right_click = true;
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == 1) {
			SimpleControl.left_click = false;
		}
		if (e.getButton() == 3) {
			SimpleControl.right_click = false;
		}
	}

	public void mouseEntered(MouseEvent e) {
		mouse = e.getPoint();
	}

	public void mouseExited(MouseEvent e) {
		mouse = e.getPoint();
	}

	public void mouseDragged(MouseEvent e) {
		mouse = e.getPoint();
	}

	public void mouseMoved(MouseEvent e) {
		mouse = e.getPoint();
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}



}
