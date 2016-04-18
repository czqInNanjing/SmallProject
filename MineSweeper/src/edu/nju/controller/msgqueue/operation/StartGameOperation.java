package edu.nju.controller.msgqueue.operation;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.model.service.GameModelService;
import edu.nju.network.Configure;

public class StartGameOperation extends MineOperation{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 302068931984860833L;

	@Override
	public void execute() {
		if(Configure.isClient){
			GameModelService game = Configure.clientService.getGameModelClient();
			game.startGame();
			return;
		}
		GameModelService game = OperationQueue.getGameModel();
		
		game.startGame();
	}

}
