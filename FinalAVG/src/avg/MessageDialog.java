package avg;

import java.awt.Color;
import java.awt.Graphics;

import resource.LSystem;
import game.GraphicsUtils;
import message.Message;

/**
 * 对话框显示处理
 * @author czq zsq
 *
 */
public class MessageDialog {
	/**
	 * 左顶点X
	 */
	private int MESSAGE_LINE_X1 = 50;
	/**
	 * 左顶点Y
	 */
	private int MESSAGE_LINE_Y1 = 410;
	/**
	 * 右下X
	 */
	private int MESSAGE_LINE_X2 = LSystem.WIDTH - MESSAGE_LINE_X1 ;
	/**
	 * 右下Y
	 */
	private int MESSAGE_LINE_Y2 = LSystem.HEIGHT-35;

	/**
	 * 角色框X
	 */
	private int MESSAGE_LINE_X = 60;
	/**
	 * 角色框Y
	 */
	private int MESSAGE_LINE_Y = 408;


	private int LINE;

	public void initialize() {
		setLINE(MESSAGE_LINE_X2 - MESSAGE_LINE_X / 15);
	}

	public void showDialog(Graphics g, int select, int font, int fontSize) {
		Message.setWindowBuoyageMessage(g, (getMESSAGE_LINE_X() + font), select
				* (font + fontSize) + getMESSAGE_LINE_Y());
	}
	/**
	 * 展示对话框
	 * @param g
	 */
	public void showDialog(Graphics g) {
			Message.setWindowFrame(g, MESSAGE_LINE_X1, MESSAGE_LINE_Y1,
					MESSAGE_LINE_X2, MESSAGE_LINE_Y2);
	}
	/**
	 * 展示角色框
	 * @param g
	 * @param name
	 * @return
	 */
	public int showRoleName(Graphics g, String name) {
		if (name != null && name.length() > 0) {
			int hSize = 10;
			int nameLength = name.length() + 2;
			int fontWidth = g.getFontMetrics().stringWidth(name);
			int fontHeight = g.getFontMetrics().getHeight();
			//浜哄悕闀垮害
			int nameX = this.getMESSAGE_LINE_X() + hSize;
			int nameY = this.getMESSAGE_LINE_Y() - 70;
			int width = fontWidth + 50;
			int height = 100;
			g.drawImage(Message.loadFrameWindow(width, LSystem.FONT_TYPE),
					nameX, nameY, null);
			GraphicsUtils.drawStyleString(g, name, nameX - 5
					+ (width / 2 - fontWidth / 2), nameY
					+ (height / 2 - fontHeight / 2), Color.blue, Color.white);
			return nameLength;
		}
		return 0;
	}


	public int getLINE() {
		return LINE;
	}

	public void setLINE(int line) {
		LINE = line;
	}


	public int getMESSAGE_LINE_X() {
		return MESSAGE_LINE_X;
	}

	public void setMESSAGE_LINE_X(int message_line_x) {
		MESSAGE_LINE_X = message_line_x;
	}

	public int getMESSAGE_LINE_X1() {
		return MESSAGE_LINE_X1;
	}

	public void setMESSAGE_LINE_X1(int message_line_x1) {
		MESSAGE_LINE_X1 = message_line_x1;
	}

	public int getMESSAGE_LINE_X2() {
		return MESSAGE_LINE_X2;
	}

	public void setMESSAGE_LINE_X2(int message_line_x2) {
		MESSAGE_LINE_X2 = message_line_x2;
	}

	public int getMESSAGE_LINE_Y() {
		return MESSAGE_LINE_Y;
	}

	public void setMESSAGE_LINE_Y(int message_line_y) {
		MESSAGE_LINE_Y = message_line_y;
	}

	public int getMESSAGE_LINE_Y1() {
		return MESSAGE_LINE_Y1;
	}

	public void setMESSAGE_LINE_Y1(int message_line_y1) {
		MESSAGE_LINE_Y1 = message_line_y1;
	}

	public int getMESSAGE_LINE_Y2() {
		return MESSAGE_LINE_Y2;
	}

	public void setMESSAGE_LINE_Y2(int message_line_y2) {
		MESSAGE_LINE_Y2 = message_line_y2;
	}
}