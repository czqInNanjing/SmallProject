package edu.nju.controller.impl;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.msgqueue.operation.SetCustomOperation;
import edu.nju.controller.msgqueue.operation.SetEasyLevelOperation;
import edu.nju.controller.msgqueue.operation.SetHardLevelOperation;
import edu.nju.controller.msgqueue.operation.SetHellLevelOperation;
import edu.nju.controller.service.SettingControllerService;

public class SettingControllerImpl implements SettingControllerService{

	@Override
	public boolean setEasyGameLevel() {
		
		return OperationQueue.addMineOperation(new SetEasyLevelOperation());
	}

	@Override
	public boolean setHardGameLevel() {
		return OperationQueue.addMineOperation(new SetHardLevelOperation());
	}

	@Override
	public boolean setHellGameLevel() {
		return OperationQueue.addMineOperation(new SetHellLevelOperation());
	}

	@Override
	public boolean setCustomizedGameLevel(int height, int width, int nums) {
		return OperationQueue.addMineOperation(new SetCustomOperation(height,width,nums));
	}

}
