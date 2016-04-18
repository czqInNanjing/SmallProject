package edu.nju.controller.impl;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.msgqueue.operation.ToBeHostOperation;
import edu.nju.controller.service.HostControllerService;

public class HostControllerImpl implements HostControllerService {

	@Override
	public boolean serviceUpHost() {
		
		return OperationQueue.addMineOperation(new ToBeHostOperation());
	}

	

}
