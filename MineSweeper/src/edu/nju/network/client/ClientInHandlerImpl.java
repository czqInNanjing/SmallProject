package edu.nju.network.client;

import java.util.Observable;

import edu.nju.network.TransformObject;

public class ClientInHandlerImpl extends Observable implements ClientInHandler{
	/**
	 * 获得从服务器端传来的transformobject并转化处理，通知clientService进行改变
	 */
	@Override
	public void inputHandle(Object data) {
		
		
		TransformObject obj = (TransformObject) data;
			
		this.setChanged();
		this.notifyObservers(obj);
		
	}

}
