package edu.nju.model.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;

import edu.nju.controller.msgqueue.operation.StartGameOperation;
import edu.nju.model.service.GameModelService;
import edu.nju.model.service.StatisticModelService;
import edu.nju.model.state.GameResultState;
import edu.nju.model.state.GameState;
import edu.nju.model.vo.GameVO;
import edu.nju.network.Configure;

public class GameModelImpl extends BaseModel implements GameModelService{
	
	private StatisticModelService statisticModel;
	private ChessBoardModelImpl chessBoardModel;
	
	private List<GameLevel> levelList;
	
	private GameState gameState;
	private int width;
	private int height;
	private int mineNum;
	
	private String level;
	
	private GameResultState gameResultState;
	private int time;
	private long startTime;

	public GameModelImpl(StatisticModelService statisticModel, ChessBoardModelImpl chessBoardModel){
		this.statisticModel = statisticModel;
		this.chessBoardModel = chessBoardModel;
		gameState = GameState.OVER;
		
		chessBoardModel.setGameModel(this);
		
		levelList = new ArrayList<GameLevel>();
		levelList.add(new GameLevel(0,"hell",30,16,99));
		levelList.add(new GameLevel(1,"hard",16,16,40));
		levelList.add(new GameLevel(2,"easy",9,9,10));
	}
	
	

	@Override
	public boolean startGame() {
		// TODO Auto-generated method stub
		gameState = GameState.RUN;
		startTime = Calendar.getInstance().getTimeInMillis();
		
		
		GameLevel gl = null;
		for(GameLevel tempLevel : levelList){
			if(tempLevel.getName().equals(level)){
				gl = tempLevel;
				break;
			}
		}
		if(gl == null&&width==0&&height == 0)
			gl = levelList.get(2);
		
		if(gl != null){
			height = gl.getWidth();
			width = gl.getHeight();
			mineNum = gl.getMineNum();
			level = gl.getName();
		}
		
		this.chessBoardModel.initialize(width, height, mineNum);
		
		super.updateChange(new UpdateMessage("start",this.convertToDisplayGame()));
		return true;
	}
	
	@Override
	public boolean gameOver(GameResultState result) {
		
		this.gameState = GameState.OVER;
		this.gameResultState = result;
		this.time = (int)(Calendar.getInstance().getTimeInMillis() - startTime)/1000;
		if(!Configure.isNetWork){
			this.statisticModel.recordStatistic(result, time,level);
			this.statisticModel.showStatistics();
		}
		
		super.updateChange(new UpdateMessage("end",this.convertToDisplayGame()));		
		return false;
	}

	@Override
	public boolean setGameLevel(String level) {
		this.level = level;
		return true;
	}

	@Override
	public boolean setGameSize(int width, int height, int mineNum) {
		this.width = width;
		this.height = height;
		this.mineNum = mineNum;
		if(levelList.size() == 4){
			levelList.remove(3);
		}
		levelList.add(new GameLevel(3, "customSet", width, height, mineNum));
		return true;
	}
	
	private GameVO convertToDisplayGame(){
		return new GameVO(gameState, width, height,level, gameResultState, time);
	}

	@Override
	public List<GameLevel> getGameLevel() {
		// TODO Auto-generated method stub
		return this.levelList;
	}
	public ChessBoardModelImpl getChessBoardModel() {
		return chessBoardModel;
	}


	public GameResultState getGameResultState() {
		return gameResultState;
	}



	@Override
	public void OutOfNet() {
		Configure.isNetWork = false;
		JOptionPane.showMessageDialog(Configure.ui.getMainFrame(), "You have out of net!");
		if(Configure.isClient){
			Configure.isClient = false;
			Configure.clientService.close();
			new StartGameOperation();
			return;
		}
		super.updateChange(new UpdateMessage("outOfNet", null));
	}
}