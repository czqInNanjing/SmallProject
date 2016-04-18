package edu.nju.model.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

import edu.nju.model.po.StatisticPO;

/**
 * 负责进行统计数据获取和记录操作
 * @author Wangy
 *
 */
public class StatisticData {
	/**
	 * 读取数据
	 * @return
	 */
	public StatisticPO getStatistic(String level){
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File("save.dat")));
			while(true){
				String[] temp = reader.readLine().split(",");
				
				if(temp[0].equalsIgnoreCase(level)){
					reader.close();
					return new StatisticPO(Double.parseDouble(temp[1]), Integer.parseInt(temp[2]), Integer.parseInt(temp[3]), level, 0, 0);
				}
			}
			
		} catch (IOException e) {
			return new StatisticPO(0.0, 0, 0, level, 0, 0);
		}finally{
			
		}
	}
	/**
	 * 存储某条数据
	 * @param statistic
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean saveStatistic(StatisticPO statistic){
			String level = statistic.getLevel();
			ArrayList<String> records = new ArrayList<>();
			File save = new File("save.dat");
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(save));
			while(true){
				String temp = reader.readLine();
				if(temp == null){
					break;
				}
				if(temp.startsWith(level)){
					temp = level + "," + (new DecimalFormat("0.00").format(statistic.getWinrate())) + "," + Integer.toString(statistic.getWins()) + "," + Integer.toString(statistic.getSum()) ;
				}
				records.add(temp);
			}
			reader.close();
			BufferedWriter writer = new BufferedWriter(new FileWriter(save, false));
			for (Iterator iterator = records.iterator(); iterator.hasNext();) {
				writer.write((String) iterator.next());
				writer.append("\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 清空数据
	 */
	public static void clearMessage(){
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(new File("save.dat"), false));
			writer.write("easy,0.00,0,0\nhard,0.00,0,0\nhell,0.00,0,0\ncustom,0.00,0,0");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}