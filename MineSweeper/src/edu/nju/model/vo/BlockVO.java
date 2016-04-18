package edu.nju.model.vo;

import java.io.Serializable;

import edu.nju.model.state.DisplayBlockState;

/**
 * 用于显示在界面上的扫雷块
 * @author Wangy
 *
 */
public class BlockVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DisplayBlockState state;
	private int x;
	private int y;
	
	public BlockVO(DisplayBlockState state, int x, int y) {
		super();
		this.state = state;
		this.x = x;
		this.y = y;
	}

	public DisplayBlockState getState() {
		return state;
	}

	public void setState(DisplayBlockState state) {
		this.state = state;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}