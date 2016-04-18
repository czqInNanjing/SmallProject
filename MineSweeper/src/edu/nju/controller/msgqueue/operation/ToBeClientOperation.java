package edu.nju.controller.msgqueue.operation;

import edu.nju.network.Configure;
import edu.nju.network.client.ClientServiceImpl;



/**
 * 使自己成为客户端
 * @author czq
 *
 */
public class ToBeClientOperation extends MineOperation {
	private String ip;
	/**
	 * 
	 */
	private static final long serialVersionUID = 266288040451431593L;
	public ToBeClientOperation(String ip) {
		this.ip = ip;
	}
	@Override
	public void execute() {
		if(Configure.isNetWork){
			return;
		}
		ClientServiceImpl client = new ClientServiceImpl();
		
		if(client.init(this.ip)){
			Configure.isClient = true;
			Configure.isNetWork = true;
		}else{
			System.out.println("fail to Connect the host");
		}
		
//		Configure.operationQueue.addObserver(client);
//		client.getHandler().addObserver(Configure.ui);
		
		
	}

}
