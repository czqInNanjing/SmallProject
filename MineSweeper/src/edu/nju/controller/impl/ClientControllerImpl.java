package edu.nju.controller.impl;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.msgqueue.operation.ToBeClientOperation;
import edu.nju.controller.service.ClientControllerService;

public class ClientControllerImpl implements ClientControllerService {

	@Override
	public boolean setupClient(String ip) {
		
		return OperationQueue.addMineOperation(new ToBeClientOperation(ip));
	}

}
