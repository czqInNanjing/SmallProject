package edu.nju.controller.impl;

import javax.swing.JFrame;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.msgqueue.operation.OutOfNetOperation;
import edu.nju.controller.msgqueue.operation.RecordOperation;
import edu.nju.controller.msgqueue.operation.SetCustomOperation;
import edu.nju.controller.msgqueue.operation.StartGameOperation;
import edu.nju.controller.service.MenuControllerService;
import edu.nju.view.CustomDialog;

public class MenuControllerImpl implements MenuControllerService{

	@Override
	public boolean startGame() {
		OperationQueue.addMineOperation(new StartGameOperation());
		return true;
	}


	@Override
	public boolean record(JFrame frame) {
		OperationQueue.addMineOperation(new RecordOperation(frame));
		return false;
	}

//	@Override
//	public boolean toBeClient() {
//		OperationQueue.addMineOperation(new ToBeClientOperation());
//		return false;
//	}
//
//	@Override
//	public boolean toBeHost() {
//		OperationQueue.addMineOperation(new ToBeHostOperation());
//		return false;
//	}





	




	@Override
	public boolean CustomSet(JFrame ui) {
		CustomDialog custom = new CustomDialog(ui);
		custom.show();
		return OperationQueue.addMineOperation(new SetCustomOperation(custom.getHeight(), custom.getWidth(), custom.getMineNumber() ));
	}





	@Override
	public void playAlone() {
		// TODO Auto-generated method stub
		OperationQueue.addMineOperation(new OutOfNetOperation());
	}

}
