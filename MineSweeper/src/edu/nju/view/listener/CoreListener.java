package edu.nju.view.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import edu.nju.controller.impl.GameControllerImpl;
import edu.nju.controller.impl.MenuControllerImpl;
import edu.nju.controller.service.GameControllerService;
import edu.nju.controller.service.MenuControllerService;
import edu.nju.view.Location;
import edu.nju.view.MainFrame;
import edu.nju.view.MyButton;
/**
 * 负责主界面的监听
 * @author czq
 *
 */
public class CoreListener implements MouseListener, ActionListener {
	
	

	private MainFrame ui;
	MenuControllerService menuController = new MenuControllerImpl();
	GameControllerService mouseController = new GameControllerImpl();
	private boolean gameOver;

	public CoreListener(MainFrame ui){
		super();
  		this.ui = ui;
	}
	public CoreListener() {
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==ui.getStartButton()){
			menuController.startGame();			
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(this.gameOver){
			return;
		}
		if (e.getClickCount() > 2) 
			return;
		
		if (e.getButton() == MouseEvent.BUTTON3) {// 右键相应雷格
			MyButton button = (MyButton) e.getSource();
			Location location = button.getMyLocation();
			mouseController.handleRightClick(location.x, location.y);
		} else if (e.getButton() == MouseEvent.BUTTON1) {// 左键相应雷格
			if (e.getClickCount() == 2) {// 双击左键
				MyButton button = (MyButton) e.getSource();
				Location location = button.getMyLocation();
				mouseController.handleDoubleClick(location.x, location.y);
			} else {// 单击左键
				MyButton button = (MyButton) e.getSource();
				Location location = button.getMyLocation();
				mouseController.handleLeftClick(location.x, location.y);
			}
		} else if (e.getButton() == MouseEvent.BUTTON2) {// 点击滚轮�?
			
		}
		

		
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
	public boolean isGameOver() {
		return gameOver;
	}
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
}
