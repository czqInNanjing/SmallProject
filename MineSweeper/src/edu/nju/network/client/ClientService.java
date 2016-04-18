package edu.nju.network.client;

import edu.nju.controller.msgqueue.operation.MineOperation;

public abstract class ClientService {
	public boolean init(String addr,ClientInHandler handler){
		boolean succeed = ClientAdapter.init(addr, handler);
		if(succeed){
			System.out.println("succeed to connect");
		}
		return succeed;
	}
	
	public void close(){
		ClientAdapter.close();
	}
	/**
	 * 这边的相关操作呈递到服务器端
	 * @param op
	 */
	public abstract void submitOperation(MineOperation op);
	
	
}
