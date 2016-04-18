package edu.nju.controller.msgqueue.operation;

import java.io.Serializable;

/**
 * 所有操作的父类
 * @author czq
 *
 */
public abstract class MineOperation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1614975816230723610L;
	/**
	 * 是否来自客户端，用户网络通信功能
	 */
	public boolean fromClient = false;
	public abstract void execute();
}
