package edu.nju.network.modelProxy;

import edu.nju.model.impl.UpdateMessage;
import edu.nju.model.service.ParameterModelService;

public class ParameterModelProxy extends ModelProxy implements
		ParameterModelService {

	private int maxMine;
	private int mineNum;
	
//	public ParameterModelProxy(ClientService client){
//		this.net = client;
//	}
	
	@Override
	public boolean setMineNum(int num) {
		// TODO Auto-generated method stub
		mineNum = num;
		maxMine = num;
		
		super.updateChange(new UpdateMessage("mineNum", mineNum));
		return true;
	}

	@Override
	public boolean addMineNum() {
		// TODO Auto-generated method stub
		mineNum++;
		
		if(mineNum>maxMine){
			mineNum--;
			return false;
		}
		
		super.updateChange(new UpdateMessage("mineNum", mineNum));
		return true;
	}

	@Override
	public boolean minusMineNum() {
		// 标雷，当剩余雷数小于0时，不允许标�?
		mineNum--;
		
		if(mineNum<0){
			mineNum++;
			return false;
		}
		
		super.updateChange(new UpdateMessage("mineNum", mineNum));
		return true;
	}

	@Override
	public void addClientFlag() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addHostFlag() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearFlag() {
		// TODO Auto-generated method stub
		
	}

}
