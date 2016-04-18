package edu.nju.network.client;


import edu.nju.controller.msgqueue.operation.MineOperation;
import edu.nju.controller.msgqueue.operation.StartGameOperation;
import edu.nju.network.Configure;
import edu.nju.network.modelProxy.ChessBoardModelProxy;
import edu.nju.network.modelProxy.GameModelProxy;
import edu.nju.network.modelProxy.ParameterModelProxy;

public class ClientServiceImpl extends ClientService {
	ClientInHandlerImpl handler = new ClientInHandlerImpl();
	GameModelProxy gameModelClient = new GameModelProxy(this);
	ChessBoardModelProxy chessBoard = new ChessBoardModelProxy(this);
	ParameterModelProxy parameterBoard = new ParameterModelProxy();
	
	@Override
	public void submitOperation(MineOperation op) {
		ClientAdapter.write(op);
	}
	
	
	public boolean init(String addr) {
		if(super.init(addr, handler)){
			Configure.clientService = this;
			handler.addObserver(gameModelClient);
			handler.addObserver(chessBoard);
			handler.addObserver(parameterBoard);
			gameModelClient.addObserver(Configure.ui);
			chessBoard.addObserver(Configure.ui.getMineBoard());
			parameterBoard.addObserver(Configure.ui.getMineNumberLabel());
			parameterBoard.addObserver(Configure.ui.getFlagNumberPanel());
			this.submitOperation(new StartGameOperation());
			return true;
		}else{
			System.out.println("fail to connect");
			return false;
		}
	};
	
	public ClientServiceImpl() {

	}
	@Override
	public void close(){
		super.close();
		handler = null;
		gameModelClient = null;
		chessBoard = null;
		parameterBoard = null;
	}
	

	public GameModelProxy getGameModelClient() {
		return gameModelClient;
	}



	public ChessBoardModelProxy getChessBoard() {
		return chessBoard;
	}

}
