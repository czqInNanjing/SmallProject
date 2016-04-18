package edu.nju.model.service;

import edu.nju.model.state.GameResultState;

/**
 * 统计Model，控制用户的统计数据
 * @author Wangy
 *
 */
public interface StatisticModelService {
	
	/**
	 * 在界面上显示统计结果
	 */
	public void showStatistics();
	
	/**
	 * 记录游戏结果，并在界面上显示统计结果
	 * @param result 结果状态
	 * @param time 游戏时间
	 */
	public void recordStatistic(GameResultState result, int time,String gameLevel);

}
