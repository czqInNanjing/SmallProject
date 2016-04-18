package edu.nju.network.host;

import java.util.Observable;

import edu.nju.controller.msgqueue.operation.SetEasyLevelOperation;
import edu.nju.model.impl.UpdateMessage;
import edu.nju.network.TransformObject;

public class HostServiceImpl extends HostService {
	HostInHandler handler = new HostInHandlerImpl();
	public HostServiceImpl() {
		super.init(handler);
		
	}
	/**
	 * 每次当游戏model发生变换时，将信息传递到客户端，被观察�?�是服务器的model
	 */
	@Override
	public void update(Observable o, Object arg) {
		UpdateMessage msge = (UpdateMessage) arg;
		if(msge.getKey().equalsIgnoreCase("outOfNet")){
			this.close();
		}
		String trigger_class = o.getClass().getName();
		TransformObject obj = new TransformObject(trigger_class, msge);
		this.publishData(obj);
	}

	@Override
	public void publishData(TransformObject o) {
		ServerAdapter.write(o);
	}
	@Override
	public void close(){
		super.close();
		handler = null;
		new SetEasyLevelOperation().execute();
	}
	

}
