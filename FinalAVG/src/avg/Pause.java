package avg;


import java.awt.Graphics;
import java.awt.Image;

import resource.BackgroundResource;

/**
 * 在运动的一只猫
 * @author czq
 *
 */
public class Pause implements Runnable {

	private Image[] myImage;

	private int sleepMax;

	private int sleep;

	private boolean isLoop;

	private boolean isRun;

	private Image nowImage;

	public Pause() {
		this(BackgroundResource.pauseImage);
	}

	public Pause(Image[] image) {
		myImage = image;
		sleepMax = myImage.length - 1;
		nowImage = myImage[0];
	}

	public void go() {
		isRun = true;
	}
	/**
	 * 使暂停
	 */
	public void intermit() {
		isRun = false;
	}

	public void start() {
		go();
		isLoop = true;
		Thread thread = new Thread(this);
		thread.start();
	}

	public void stop() {
		intermit();
		isLoop = false;
	}

	public void run() {
		for (; isLoop;) {
			if (isRun) {
				nowImage = myImage[sleep];
				if (sleep < sleepMax) {
					sleep++;
				} else {
					sleep = 0;
				}
			}
			try {
				Thread.sleep(200);
			} catch (Exception e) {
			}
		}
	}

	public void draw(Graphics g, int x, int y) {
		g.drawImage(nowImage, x, y,null);
	}

}
