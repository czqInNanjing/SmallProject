package main;

import ui.StartPanel;
import game.GameFrame;


public class Main {
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				GameFrame frame = new GameFrame("AVG",
						990, 618);
				
//				frame.setCursor(GameCursor.getCursor("image/icon/cursor.png"));
				frame.getGame().setControl(new StartPanel());
				//frame.getGame().setControl(new AVGScript("script/start.txt"));
				//frame.setFPS(true);
				// frame.updateFullScreen();
				frame.mainLoop();
				frame.showFrame();
			}
		});
	}
}
