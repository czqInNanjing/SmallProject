package edu.nju.network.modelProxy;

import java.util.Observable;
import java.util.Observer;

import edu.nju.model.impl.BaseModel;
import edu.nju.model.impl.UpdateMessage;
import edu.nju.network.TransformObject;
import edu.nju.network.client.ClientService;

/**
 * 所有的代理类的基类
 * @author 晨晖
 * 既是观察者又是被观察者，
 */
public class ModelProxy extends BaseModel implements Observer{
	protected ClientService net;
	@Override
	public void update(Observable o, Object arg) {
		TransformObject obj = (TransformObject) arg;
		String trigger_class = obj.getSource();
		UpdateMessage msg = obj.getMsg();
		Class<?> super_class = this.getClass().getInterfaces()[0];
		try {
			if(super_class.isAssignableFrom(Class.forName(trigger_class))){
				//通知view层次变换
				this.updateChange(msg);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


}
