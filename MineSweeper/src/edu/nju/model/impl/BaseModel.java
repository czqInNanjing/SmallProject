package edu.nju.model.impl;

import java.util.Observable;

/**
 * 基础Model类，继承Observable方法，基础Observable接口类可以向其注册，监听数据变化
 * @author Wangy
 *
 */
public class BaseModel extends Observable{

	/**
	 * 通知更新方法，请在子类中需要通知观察者的地方调用此方法
	 * 
	 * @param data
	 */
	protected void updateChange(UpdateMessage message){
		
		super.setChanged();
		super.notifyObservers(message);
		
	}
	public boolean isFromClient = false;
}