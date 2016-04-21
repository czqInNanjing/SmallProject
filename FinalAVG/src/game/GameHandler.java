package game;


import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;



/**
 * 游戏处理器，用于切换不同的simpleControl
 *
 */
public class GameHandler implements IGameHandler{

	public GameHandler() {

	}

	public synchronized void draw(final Graphics2D g) {
		if (SimpleControl.validControl()) {
			SimpleControl.currentControl.draw(g);
		}
	}

	public synchronized IControl getControl() {
		return SimpleControl.currentControl;
	}

	public synchronized void setControl(final IControl control) {
		SimpleControl.currentControl = control;
	}

	

	public void keyPressed(KeyEvent e) {
		if (SimpleControl.validControl())
			SimpleControl.currentControl.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		if (SimpleControl.validControl())
			SimpleControl.currentControl.keyReleased(e);
	}

	public void keyTyped(KeyEvent e) {
		if (SimpleControl.validControl())
			SimpleControl.currentControl.keyTyped(e);
	}

	public void mouseClicked(MouseEvent e) {
		if (SimpleControl.validControl())
			SimpleControl.currentControl.mouseClicked(e);
	}

	public void mouseEntered(MouseEvent e) {
		if (SimpleControl.validControl())
			SimpleControl.currentControl.mouseEntered(e);
	}

	public void mouseExited(MouseEvent e) {
		if (SimpleControl.validControl())
			SimpleControl.currentControl.mouseExited(e);
	}

	public void mousePressed(MouseEvent e) {
		if (SimpleControl.validControl())
			SimpleControl.currentControl.mousePressed(e);
	}

	public void mouseReleased(MouseEvent e) {
		if (SimpleControl.validControl())
			SimpleControl.currentControl.mouseReleased(e);
	}

	public void mouseDragged(MouseEvent e) {
		if (SimpleControl.validControl())
			SimpleControl.currentControl.mouseDragged(e);
	}

	public void mouseMoved(MouseEvent e) {
		if (SimpleControl.validControl())
			SimpleControl.currentControl.mouseMoved(e);
	}

}
