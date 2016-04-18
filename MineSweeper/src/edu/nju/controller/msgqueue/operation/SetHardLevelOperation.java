package edu.nju.controller.msgqueue.operation;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.model.service.GameModelService;
import edu.nju.network.Configure;

public class SetHardLevelOperation extends MineOperation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6615353871061767920L;

	@Override
	public void execute() {
		if(Configure.isClient){
			GameModelService game = Configure.clientService.getGameModelClient();
			game.setGameLevel("hard");
			game.startGame();
			return;
		}
		
		GameModelService game = OperationQueue.getGameModel();
		game.setGameLevel("hard");
		game.startGame();
	}

}

