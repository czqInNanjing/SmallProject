package edu.nju.model.impl;

import edu.nju.model.service.ParameterModelService;
/**
 * 负责控制游戏参数model，现有参数：剩余雷数,服务器端和客户端的旗子数�?
 * @author czq
 *
 */
public class ParameterModelImpl extends BaseModel implements ParameterModelService{
	
	private int maxMine;
	private int mineNum;
	private int clientFlag;
	private int hostFlag;
	@Override
	public boolean setMineNum(int num) {
		mineNum = num;
		maxMine = num;
		
		super.updateChange(new UpdateMessage("mineNum", mineNum));
		return true;
	}

	@Override
	public boolean addMineNum() {
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

	public int getMineNum() {
		return mineNum;
	}
	public void addClientFlag(){
		this.clientFlag++;
		super.updateChange(new UpdateMessage("clientFlag", Integer.toString(clientFlag)));
	}
	public void addHostFlag(){
		this.hostFlag++;
		super.updateChange(new UpdateMessage("hostFlag", Integer.toString(hostFlag)));
	}
	public void clearFlag(){
		this.hostFlag = 0;
		this.clientFlag = 0;
	}

	public int getClientFlag() {
		return clientFlag;
	}

	public int getHostFlag() {
		return hostFlag;
	}
}
