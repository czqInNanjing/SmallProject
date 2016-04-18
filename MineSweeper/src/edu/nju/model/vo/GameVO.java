package edu.nju.model.vo;

import java.io.Serializable;

import edu.nju.model.state.GameResultState;
import edu.nju.model.state.GameState;

public class GameVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GameState gameState;
	private int width;
	private int height;
	private String level;
	
	private GameResultState gameResultStae;
	private int time;

	public GameVO(GameState gameState, int width, int height,
			String level, GameResultState gameResultStae, int time) {
		super();
		this.gameState = gameState;
		this.width = width;
		this.height = height;
		this.level = level;
		this.gameResultStae = gameResultStae;
		this.time = time;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public GameResultState getGameResultStae() {
		return gameResultStae;
	}

	public void setGameResultStae(GameResultState gameResultStae) {
		this.gameResultStae = gameResultStae;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
}
