package edu.nju.controller.impl;
import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.msgqueue.operation.DoubleClickOperation;
import edu.nju.controller.msgqueue.operation.LeftClickOperation;
import edu.nju.controller.msgqueue.operation.MineOperation;
import edu.nju.controller.msgqueue.operation.RightClickOperation;
import edu.nju.controller.service.GameControllerService;
/**
 * 游戏控制器类，负责传送操作，将操作加到信息队列中
 * @author czq
 * 
 */
public class GameControllerImpl implements GameControllerService{
	
	
	@Override
	public boolean handleLeftClick(int x, int y) {
		MineOperation op = new LeftClickOperation(x,y);
		
		return OperationQueue.addMineOperation(op);
	}

	@Override
	public boolean handleRightClick(int x, int y) {
		MineOperation op = new RightClickOperation(x,y);
		
		return OperationQueue.addMineOperation(op);
	}

	@Override
	public boolean handleDoubleClick(int x, int y) {
		MineOperation op = new DoubleClickOperation(x,y);
		
		return OperationQueue.addMineOperation(op);
	}

}
