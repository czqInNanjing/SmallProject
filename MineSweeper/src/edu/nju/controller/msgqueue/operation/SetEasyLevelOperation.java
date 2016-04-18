package edu.nju.controller.msgqueue.operation;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.model.service.GameModelService;
import edu.nju.network.Configure;

public class SetEasyLevelOperation extends MineOperation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3836295073257588952L;

	@Override
	public void execute() {
		if(Configure.isClient){
			GameModelService game = Configure.clientService.getGameModelClient();
			game.setGameLevel("easy");
			return;
		}
		
		GameModelService game = OperationQueue.getGameModel();
		game.setGameLevel("easy");
		game.startGame();
	}

}
