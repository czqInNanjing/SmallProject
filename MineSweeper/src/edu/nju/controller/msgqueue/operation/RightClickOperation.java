package edu.nju.controller.msgqueue.operation;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.model.service.ChessBoardModelService;
import edu.nju.network.Configure;

/**
 * 右击操作 实现标雷
 * @author czq
 *
 */
public class RightClickOperation extends MineOperation {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8094347110993615350L;
	int x;
	int y;
	public RightClickOperation(int x , int y) {
		this.x = x;
		this.y = y;
	}
	@Override
	public void execute() {
		if(Configure.isClient){
			ChessBoardModelService chess = Configure.clientService.getChessBoard();
			chess.mark(x, y);
			return ;
		}
		if(Configure.isNetWork){
			OperationQueue.getChessBoardModel().isFromClient = this.fromClient;
		}
		ChessBoardModelService chess = OperationQueue.getChessBoardModel();
		
		chess.mark(x, y);

	}

}
