package edu.nju.controller.msgqueue.operation;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.model.service.ChessBoardModelService;
import edu.nju.network.Configure;
/**
 * 左点击操作，实现挖雷
 * @author czq
 *
 */
public class LeftClickOperation extends MineOperation{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3741953243089909862L;
	private int x;
	private int y;
	public LeftClickOperation(int x ,int y){
		this.x = x;
		this.y = y;
	}

	@Override
	public void execute() {
		//如果是客户端
		if(Configure.isClient){
			ChessBoardModelService chess = Configure.clientService.getChessBoard();
			chess.excavate(x, y);
			return ;
		}
		//如果是服务器端
		if(Configure.isNetWork){
			OperationQueue.getChessBoardModel().isFromClient = this.fromClient;
		}
		ChessBoardModelService chess = OperationQueue.getChessBoardModel();
		
		chess.excavate(x, y);
	}

}
