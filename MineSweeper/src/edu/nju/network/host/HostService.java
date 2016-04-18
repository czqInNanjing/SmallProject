package edu.nju.network.host;

import java.util.Observer;

import edu.nju.network.TransformObject;

public abstract class HostService implements Observer{
	public boolean init(HostInHandler handler){
		boolean succeed = ServerAdapter.init( handler);
		return succeed;
	}
	public void close(){
		ServerAdapter.close();
	}
	/**
	 * 将服务器端发生的操作传递给客户端
	 * @param o
	 */
	public abstract void publishData(TransformObject o);

}
