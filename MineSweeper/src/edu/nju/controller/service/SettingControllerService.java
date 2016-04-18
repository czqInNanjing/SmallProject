package edu.nju.controller.service;

public interface SettingControllerService {
	/**
	 * 设置难度 “小
	 * @return
	 */
	public boolean setEasyGameLevel();
	/**
	 * 设置难度 “中
	 * @return
	 */
	public boolean setHardGameLevel();
	/**
	 * 设置难度 “大
	 * @return
	 */
	public boolean setHellGameLevel();
	/**
	 * 自定义雷盘的大小及雷的个数
	 * @param height 扫雷区的行数
	 * @param width  扫雷区的列数
	 * @param nums   自定义的地雷数
	 * @return
	 */
	public boolean setCustomizedGameLevel(int height,int width , int nums);
}
