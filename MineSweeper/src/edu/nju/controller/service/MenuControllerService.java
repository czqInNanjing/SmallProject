package edu.nju.controller.service;

import javax.swing.JFrame;

public interface MenuControllerService {
	/**
	 * 开始游戏
	 * @return
	 */
	public boolean startGame();
	
	public boolean CustomSet(JFrame ui);

	public boolean record(JFrame ui);

//	public boolean toBeClient();
//
//	public boolean toBeHost();
	
	public void playAlone();

}
