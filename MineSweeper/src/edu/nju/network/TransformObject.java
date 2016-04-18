package edu.nju.network;

import java.io.Serializable;

import edu.nju.model.impl.UpdateMessage;
/**
 * 将要更新的操作在这儿转化成一个序列化的object并传送过去？
 * @author czq
 *
 */
public class TransformObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 发生变更的类的名字，如棋盘，gamemodel 
	 */
	private String source;
	private UpdateMessage msg;
	
	public TransformObject(String src, UpdateMessage msg){
		this.source = src;
		this.msg = msg;
	}
	
	
	public String getSource(){
		return this.source;
	}
	
	public UpdateMessage getMsg(){
		return this.msg;
	}
	
	public void setSource(String src){
		this.source = src;
	}
	
	public void setMsg(UpdateMessage msge){
		this.msg = msge;
	}

}
