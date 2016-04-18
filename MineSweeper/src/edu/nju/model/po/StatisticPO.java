package edu.nju.model.po;
/**
 * 数据记录格式
 * @author czq
 *
 */
public class StatisticPO {

	private double winrate;
	private int wins;
	private int sum;
	private String level;
	private int width;
	private int height;
	
	public StatisticPO() {
		super();
	}

	public StatisticPO(double winrate, int wins, int sum, String level, int width,
			int height) {
		super();
		this.winrate = winrate;
		this.wins = wins;
		this.sum = sum;
		this.level = level;
		this.width = width;
		this.height = height;
	}

	public double getWinrate() {
		return winrate;
	}

	public void setWinrate(double winrate) {
		this.winrate = winrate;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
}
