package edu.nju.controller.msgqueue.operation;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.model.service.ChessBoardModelService;
import edu.nju.network.Configure;

/**
 * 双击操作，实现快速挖雷
 * @author czq
 *
 */
public class DoubleClickOperation extends MineOperation {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3569856833554688489L;
	int x;
	int y;
	public DoubleClickOperation(int x , int y) {
		this.x = x;
		this.y=y;
	}
	@Override
	public void execute() {
		if(Configure.isClient){
			ChessBoardModelService chess = Configure.clientService.getChessBoard();
			chess.quickExcavate(x, y);
			return ;
		}
		ChessBoardModelService chess = OperationQueue.getChessBoardModel();
		if(Configure.isNetWork){
			OperationQueue.getChessBoardModel().isFromClient = this.fromClient;
		}
		chess.quickExcavate(x, y);
	}

}
