package edu.nju.controller.msgqueue.operation;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.network.Configure;
import edu.nju.network.host.HostServiceImpl;

public class ToBeHostOperation extends MineOperation {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7043419358334656533L;

	/**
	 * 
	 * 为host的model添加观察者
	 */
	@Override
	public void execute() {
		if(Configure.isNetWork){
			return;
		}
		//在这儿调用hostcontrol
		Configure.isNetWork = true;
		HostServiceImpl host = new HostServiceImpl();
		if(Configure.isNetWork){
//			OperationQueue.getGameModel().deleteObservers();
			OperationQueue.getGameModel().addObserver(host);
//			OperationQueue.getGameModel().getChessBoardModel().deleteObservers();
			OperationQueue.getGameModel().getChessBoardModel().addObserver(host);
//			OperationQueue.getGameModel().getChessBoardModel().getParameterModel().deleteObservers();
			OperationQueue.getGameModel().getChessBoardModel().getParameterModel().addObserver(host);
		}
		
	}

}
