package edu.nju.controller.msgqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import edu.nju.controller.msgqueue.operation.MineOperation;
import edu.nju.model.impl.ChessBoardModelImpl;
import edu.nju.model.impl.GameModelImpl;
import edu.nju.network.Configure;

/**
 * 操作队列，所有的操作要加入队列，该队列自行按操作到达的先后顺序处理操作
 * @author 晨晖
 *
 */
public class OperationQueue implements Runnable{
	
	private static BlockingQueue<MineOperation> queue;
	
	public static boolean isRunning;
	
//	public static boolean singleUpdateSwitch = true;
	
	private static ChessBoardModelImpl chessBoard;
	private static GameModelImpl gameModel;
	
	public OperationQueue(ChessBoardModelImpl chess, GameModelImpl game){
		queue = new ArrayBlockingQueue<MineOperation>(1000);
		isRunning = true;
		chessBoard = chess;
		gameModel = game;
		Configure.operationQueue = this;
		
	}

	@Override
	public void run() {
		while(isRunning){
			MineOperation operation = getNewMineOperation();
				operation.execute();
		}
	}
	
	/**
	 * 当一个新的操作产生时，加进队列里
	 * @param operation
	 * @return 是否成功
	 */
	public static boolean addMineOperation (MineOperation operation){
		try {
			queue.put(operation);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	private static MineOperation getNewMineOperation (){
		MineOperation  operation = null;
		try {
			operation = queue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return operation;
	}
	
	public static ChessBoardModelImpl getChessBoardModel(){
		return chessBoard;
	}
	
	public static GameModelImpl getGameModel(){
		return gameModel;
	}

	

}
