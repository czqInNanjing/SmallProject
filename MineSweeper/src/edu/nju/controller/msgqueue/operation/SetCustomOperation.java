package edu.nju.controller.msgqueue.operation;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.model.service.GameModelService;
import edu.nju.network.Configure;

/**
 * 实现用户自己设置雷数
 * 
 * @author czq
 *
 */
public class SetCustomOperation extends MineOperation {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4524248134663032614L;
	private int height;
	private int width;
	private int nums;

	public SetCustomOperation(int height, int width, int nums) {
		this.height = height;
		this.width = width;
		this.nums = nums;
	}

	@Override
	public void execute() {
		//网络部分
		if (Configure.isClient) {
			GameModelService game = Configure.clientService
					.getGameModelClient();
			game.setGameLevel("customSet");
			if (nums > height * width || nums < 10 || nums > 668 || height > 24
					|| height < 9 || width < 9 || width > 30) {
				height = 9;
				width = 9;
				nums = 10;
			}
			game.setGameSize(width, height, nums);
			return;
		} else if (Configure.isNetWork && !Configure.isClient) {

			GameModelService game = OperationQueue.getGameModel();
			game.setGameLevel("customSet");
			game.setGameSize(width, height, nums);
			game.startGame();
			return;
		}
		
		
		GameModelService game = OperationQueue.getGameModel();
		game.setGameLevel("customSet");
		if (nums > height * width || nums < 10 || nums > 668 || height > 24
				|| height < 9 || width < 9 || width > 30) {
			height = 9;
			width = 9;
			nums = 10;
		}
		game.setGameSize(width, height, nums);
		game.startGame();
	}

}
