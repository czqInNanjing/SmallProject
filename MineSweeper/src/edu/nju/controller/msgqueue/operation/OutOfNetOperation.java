package edu.nju.controller.msgqueue.operation;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.model.service.GameModelService;
import edu.nju.network.Configure;
/**
 * 离开网络的操作，用于playAlone或者一方断网
 * @author czq
 *
 */
public class OutOfNetOperation extends MineOperation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3012915577259332688L;

	@Override
	public void execute() {
		if(!Configure.isNetWork){
			return;
		}
		if(Configure.isClient){
			GameModelService game = Configure.clientService.getGameModelClient();
			game.OutOfNet();
			return;
		}
		GameModelService game = OperationQueue.getGameModel();
		game.OutOfNet();
		

	}

}
