package edu.nju.controller.service;
/**
 * 提供相关游戏操作：单击，右击，双击
 * @author 晨晖
 *
 */
public interface GameControllerService {
	/**
	 * 处理鼠标在方格[x,y]的单击
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean handleLeftClick(int x,int y);
	/**
	 * 处理鼠标在方格[x,y]的右击
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean handleRightClick(int x ,int y);
	/**
	 * 处理鼠标在方格[x,y]的双击
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean handleDoubleClick(int x, int y);
}
