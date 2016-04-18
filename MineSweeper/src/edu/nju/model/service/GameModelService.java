package edu.nju.model.service;

import java.util.List;

import edu.nju.model.impl.GameLevel;
import edu.nju.model.state.GameResultState;

/**
 * 游戏model，负责控制整个游戏状�?
 * @author Wangy
 *
 */
public interface GameModelService {


	/**
	 * 设定游戏尺寸
	 * @param width
	 * @param height
	 * @param mineNum
	 * @return
	 */
	public boolean setGameSize(int width, int height, int mineNum);

	/**
	 * 设定游戏等级
	 * @param level
	 * @return
	 */
	public boolean setGameLevel(String level);
	
	/**
	 * �?始游�?
	 * @return
	 */
	public boolean startGame();


	/**
	 * 结束游戏
	 * @param result 结果状�??
	 * @param time 游戏时间
	 * @return
	 */
	public boolean gameOver(GameResultState result);

	/**
	 * 获得游戏等级列表
	 * @return
	 */
	public List<GameLevel> getGameLevel();
	/**
	 * 离开网络
	 */
	public void OutOfNet();


}

