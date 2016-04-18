package edu.nju.model.service;


/**
 * 负责控制游戏参数Model，现有参数：剩余雷数
 * @author Wangy
 *
 */
public interface ParameterModelService {

	/**
	 * 设置剩余雷数
	 * @param num
	 * @return
	 */
	public boolean setMineNum(int num);
	
	/**
	 * 减少雷数
	 * @return 是否允许继续减少雷（标记雷）
	 */
	public boolean minusMineNum();
	
	/**
	 * 增加雷数
	 * @return
	 */
	public boolean addMineNum();
	/**
	 * 增加客户端的旗子数
	 */
	public void addClientFlag();
	/**
	 * 增加服务器的旗子数
	 */
	public void addHostFlag();
	/**
	 * 清空所有旗子
	 */
	public void clearFlag();
	
}
