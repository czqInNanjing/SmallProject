package edu.nju.network.modelProxy;


import java.util.List;

import javax.swing.JOptionPane;

import edu.nju.controller.msgqueue.operation.MineOperation;
import edu.nju.controller.msgqueue.operation.SetCustomOperation;
import edu.nju.controller.msgqueue.operation.SetEasyLevelOperation;
import edu.nju.controller.msgqueue.operation.SetHardLevelOperation;
import edu.nju.controller.msgqueue.operation.SetHellLevelOperation;
import edu.nju.controller.msgqueue.operation.StartGameOperation;
import edu.nju.model.impl.GameLevel;
import edu.nju.model.impl.UpdateMessage;
import edu.nju.model.service.GameModelService;
import edu.nju.model.state.GameResultState;
import edu.nju.network.Configure;
import edu.nju.network.client.ClientService;
/**
 * GameModel的代理，在网络对战时替代Client端的相应Model�?
 * @author 晨晖
 *
 */
public class GameModelProxy extends ModelProxy implements GameModelService{
	
	
	public GameModelProxy(ClientService client){
		this.net = client;
	}

	@Override
	public boolean setGameLevel(String level) {
		if(level.equalsIgnoreCase("easy")){
			net.submitOperation(new SetEasyLevelOperation());
		}else if(level.equalsIgnoreCase("hard")){
			net.submitOperation(new SetHardLevelOperation());
		}else if(level.equalsIgnoreCase("hell")){
			net.submitOperation(new SetHellLevelOperation());
		}else{
			//TODO
		}
//		MineOperation op = new 
		return false;
	}

	@Override
	public boolean startGame() {
		// TODO Auto-generated method stub
		MineOperation op = new StartGameOperation();
		net.submitOperation(op);
		return true;
	}

	@Override
	public boolean gameOver(GameResultState result) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public List<GameLevel> getGameLevel() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean setGameSize(int width, int height, int mineNum) {
		// TODO Auto-generated method stub
		SetCustomOperation op = new SetCustomOperation(height, width, mineNum);
		net.submitOperation(op);
		return true;
	}

	@Override
	public void OutOfNet() {
		Configure.isNetWork = false;
		JOptionPane.showMessageDialog(Configure.ui.getMainFrame(), "You have out of net!");
		if(Configure.isClient){
			Configure.isClient = false;
			Configure.clientService.close();
			new SetEasyLevelOperation().execute();
			System.out.println("here");
			return;
		}
		super.updateChange(new UpdateMessage("outOfNet", null));
	}

}
