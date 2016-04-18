package edu.nju.model.impl;


import edu.nju.model.data.StatisticData;
import edu.nju.model.po.StatisticPO;
import edu.nju.model.service.StatisticModelService;
import edu.nju.model.state.GameResultState;
import edu.nju.network.Configure;
import edu.nju.view.RecordDialog;
/**
 * 数据记录和存储模型，负责存储数据
 * @author czq
 *
 */
public class StatisticModelImpl extends BaseModel implements StatisticModelService{
	
	private StatisticData statisticDao = new StatisticData();
	
	public StatisticModelImpl(){
		//初始化Dao
	}

	@Override
	public void recordStatistic(GameResultState result, int time, String gameLevel) {
		StatisticPO lastData = statisticDao.getStatistic(gameLevel);
		int wins = lastData.getWins();
		int sum = lastData.getSum();
		if(result ==GameResultState.SUCCESS){
			wins++;
		}else{
		}
		sum++;
		double winRate = wins/(double)sum;
		statisticDao.saveStatistic(new StatisticPO(winRate, wins, sum, gameLevel, 0, 0));
	}

	@Override
	public void showStatistics() {
		RecordDialog record = new RecordDialog(Configure.ui.getMainFrame());

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		record.show();
	}
	
}
