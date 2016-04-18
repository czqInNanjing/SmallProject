package edu.nju.controller.msgqueue.operation;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.model.service.GameModelService;
import edu.nju.network.Configure;

public class SetHellLevelOperation extends MineOperation {

	/**
	 * 
	 */
	private static final long serialVersionUID = -789107850501494106L;

	@Override
	public void execute() {
		if(Configure.isClient){
			GameModelService game = Configure.clientService.getGameModelClient();
			game.setGameLevel("hell");
			game.startGame();
			return;
		}
		
		GameModelService game = OperationQueue.getGameModel();
		game.setGameLevel("hell");
		game.startGame();
	}

}
