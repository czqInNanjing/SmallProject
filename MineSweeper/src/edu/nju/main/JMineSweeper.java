package edu.nju.main;

import edu.nju.controller.impl.MenuControllerImpl;
import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.service.MenuControllerService;
import edu.nju.model.impl.ChessBoardModelImpl;
import edu.nju.model.impl.GameModelImpl;
import edu.nju.model.impl.ParameterModelImpl;
import edu.nju.model.impl.StatisticModelImpl;
import edu.nju.network.Configure;
import edu.nju.view.MainFrame;

public class JMineSweeper {

	static MenuControllerService menuController = new MenuControllerImpl();

	public static void main(String[] args) {
		MainFrame ui = new MainFrame();
		StatisticModelImpl statisticModel = new StatisticModelImpl();
		ParameterModelImpl parameterModel = new ParameterModelImpl();
		ChessBoardModelImpl mineBoardModel = new ChessBoardModelImpl(
				parameterModel);
		GameModelImpl gameModel = new GameModelImpl(statisticModel,
				mineBoardModel);

		gameModel.addObserver(ui);
		parameterModel.addObserver(ui.getMineNumberLabel());
		mineBoardModel.addObserver(ui.getMineBoard());
		parameterModel.addObserver(ui.getFlagNumberPanel());
		Configure.ui = ui;

		OperationQueue operationQueue = new OperationQueue(mineBoardModel,
				gameModel);
		Thread operationThread = new Thread(operationQueue);
		operationThread.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		menuController.startGame();

	}

}
