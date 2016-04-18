package edu.nju.view;

import javax.swing.JLabel;

/**
 * 时间条，用来计算时间
 * 
 * @author czq
 *
 */
public class TimeLabel extends JLabel implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8972104425088398463L;
	private long startTime;
	private boolean isRunning;

	public TimeLabel() {
		this.startTime = System.currentTimeMillis();
	}

	@Override
	public void run() {
		while (isRunning) {
			this.setText(String.valueOf((System.currentTimeMillis() - startTime) / 1000));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		if (isRunning) {
			this.startTime = System.currentTimeMillis();
		}
		this.isRunning = isRunning;
	}

}
