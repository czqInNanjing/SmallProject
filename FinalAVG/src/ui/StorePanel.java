package ui;

import game.GraphicsUtils;
import game.SimpleControl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

import media.Player;
import resource.BackgroundResource;
import resource.ImgStart;
import resource.LSystem;
import resource.StoreText;
import avg.AVGScript;

/**
 * 存储游戏及加载游戏界面
 * 
 * @author czq
 *
 *
 */
public class StorePanel extends SimpleControl {
	/**
	 * 是否在存储界面
	 */
	boolean isStoring;
	/**
	 * 6个存储文件的状态 为null表示为空，否则其格式为 游戏时长+","+最后游戏时间+","+第几幕
	 */
	String[] saveCondition = new String[6];
	/**
	 * 存储界面背景图 , 此图片为默认
	 */
	private Image bg = BackgroundResource.storeBgs[6];
	/**
	 * 背景图集
	 */
	private Image[] bgs = BackgroundResource.storeBgs;
	/**
	 * 各个幕的画
	 */
	private Image[] images = BackgroundResource.storeImg;
	/**
	 * 存档小乌龟
	 */
	private Image[] turtle = BackgroundResource.turtle;
	private Image temp = turtle[0];
	/**
	 * 传进来的是第几幕的存档点
	 */
	private int scene;
	/**
	 * 获取日期
	 */
	private Date date = new Date();
	private SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
	/**
	 * 游戏时长
	 */
	private String playTime;
	/**
	 * 最后游戏时间
	 */
	private String lastTime;
	/**
	 * 是否覆盖存档
	 */
	private boolean coverFile;
	/**
	 * 用来记录是否已点击到有存档的位置
	 * 例如上次点击一号，这次也要再点击一号才有效
	 */
	private int coverLocation = -1;
	/**
	 * 是否存储成功
	 */
	private boolean saveSucceed;
	/**
	 * 是否展示游戏时长，最后游玩时间 
	 */
	private boolean showPlayTime;
	/**
	 * 是否允许存储
	 */
	private boolean allowStore = true;
	/**
	 * 游戏时长
	 */
	private long timeMills;
	/**
	 * 构造器，传入当前状态
	 * 
	 * @param isStoring
	 *            true则为存储界面，否则为加载界面
	 * @param scene
	 *            要存储的为第几幕
	 * @param timeMills 此次游戏时间
	 */
	public StorePanel(boolean isStoring, int scene, long timeMills) {
		this.isStoring = isStoring;
		if (isStoring) {
			this.scene = scene;
			this.timeMills = timeMills;
			this.temp = turtle[4];
		}
		getSaveCondition();
		Player.playMusic("o");
		coverFile = false;
	}

	@Override
	/**
	 * 根据saveCondition来画图
	 */
	public void draw(Graphics g) {
		g.drawImage(bg, 0, 0, LSystem.WIDTH, LSystem.HEIGHT, 0, 0,
				bg.getWidth(null), bg.getHeight(null), null);
			g.drawImage(temp,100,42,120,120,null);
		for (int i = 0; i < saveCondition.length; i++) {
			if (saveCondition[i] == null) {
				continue;
			} else {
				String[] temp = saveCondition[i].split(",");
				// 获得将要显示的第几幕图片
				Image tempImg = null;
				switch (temp[2]) {
				case "1":
					tempImg = images[0];
					break;
				case "2":
					tempImg = images[1];
					break;
				case "3":
					tempImg = images[2];
					break;
				case "4":
					tempImg = images[3];
					break;
				case "5":
					tempImg = images[4];
					break;
				case "6":
					tempImg = images[5];
					break;
				}
				if (i <= 2) {
					g.drawImage(tempImg, i * 327 + 80, 185, 150, 97, null);
				} else {
					g.drawImage(tempImg, (i - 3) * 327 + 80, 340, 150, 97, null);
				}
			}
		}
		// 实现时间，是否覆盖的问题
		if (coverFile) {
//			g.drawImage(bgs[10], 50, 45,905,120, null);
			temp = turtle[2];
			
		}
		if(saveSucceed){
//			g.drawImage(bgs[11], 240, 45, null);
			temp = turtle[1];
			g.drawImage(temp,100,42,120,120,null);
		}
		if(showPlayTime){
//			g.setFont(GraphicsUtils.getFont(40));
			g.setFont(new Font("幼圆",Font.BOLD,30));
			g.setColor(Color.WHITE);
			g.drawString("游戏时长", 230, 500);
			g.drawString("最后游戏时间", 230,560);
			playTime(playTime,g);
			lastTime(lastTime,g);
		}
		
	}
	/**
	 * 处理playTime
	 */
	public void playTime(String s,Graphics g){
		int x = 26;
		int y = 36;
		int temp=462;
		String list[] = s.split(":");
		char[] char1 = list[0].toCharArray();
		char[] char2 = list[1].toCharArray();
		char[] char3 = list[2].toCharArray();
		for(int i=0;i<=char1.length-1;i++){
			g.drawImage(ImgStart.num,temp,463,temp+x,463+y,(char1[i]-'0')*26,0,(char1[i]-'0')*26+x,36,null);
			temp+=x;
		}
//		g.drawImage(ImgStart.maohao,temp-30,553-13,temp+x,533+y,0,0,20,36,null);
		temp+=x;
		for(int i=0;i<=char2.length-1;i++){
			g.drawImage(ImgStart.num,temp,463,temp+x,463+y,(char2[i]-'0')*26,0,(char2[i]-'0')*26+x,36,null);
			temp+=x;
		}
//		g.drawImage(ImgStart.maohao,temp-30,553-13,temp+x,533+y,0,0,20,36,null);
		temp+=x;
		for(int i=0;i<=char2.length-1;i++){
			g.drawImage(ImgStart.num,temp,463,temp+x,463+y,(char3[i]-'0')*26,0,(char3[i]-'0')*26+x,36,null);
			temp+=x;
		}
	}
	
	/**
	 * 处理lastTime
	 */
	public void lastTime(String s,Graphics g){
		int x = 26;
		int y = 36;
		String list[] = s.split("/");
		char[] char1 = list[0].toCharArray();
		char[] char2 = list[1].toCharArray();
		char[] char3 = list[2].toCharArray();
		for(int i=0;i<=3;i++){
			g.drawImage(ImgStart.num,462+x*i,533,462+x*(i+1),533+y,(char1[i]-'0')*26,0,(char1[i]-'0')*26+x,36,null);
		}
		for(int i=0;i<=1;i++){
			g.drawImage(ImgStart.num,586+x*i,533,586+x*(i+1),533+y,(char2[i]-'0')*26,0,(char2[i]-'0')*26+x,36,null);
		}
		for(int i=0;i<=1;i++){
			g.drawImage(ImgStart.num,654+x*i,533,654+x*(i+1),533+y,(char3[i]-'0')*26,0,(char3[i]-'0')*26+x,36,null);
		}
	}
	
	/**
	 * 
	 */
	@Override
	public void next() {
		getSaveCondition();
	}

	/**
	 * 鼠标移动监听，需实现高光与否
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		// 返回监听
		if ((x>=56)&&(x<=56+95)&&(y>=457)&&(y<=457+82)) {
			bg = bgs[8];
			return;
		}
		// 退出监听
		if (x >= 952 && x <= 983 && y >= 15 && y <= 40) {
			bg = bgs[7];
			return;
		}
		if (x >= 60 && x <= 310 && y >= 175 && y <= 310) {
			bg = bgs[0];
			if(saveCondition[0] != null){
				showPlayTime = true;
				getTime(0);
			}
			return;
		}
		if (x >= 367 && x <= 610 && y >= 175 && y <= 310) {
			bg = bgs[1];
			if(saveCondition[1] != null){
				showPlayTime = true;
				getTime(1);
			}
			return;
		}
		if (x >= 674 && x <= 924 && y >= 175 && y <= 310) {
			bg = bgs[2];
			if(saveCondition[2] != null){
				showPlayTime = true;
				getTime(2);
			}
			return;
		}
		if (x >= 60 && x <= 310 && y >= 330 && y <= 447) {
			bg = bgs[3];
			if(saveCondition[3] != null){
				showPlayTime = true;
				getTime(3);
			}
			return;
		}
		if (x >= 367 && x <= 610 && y >= 330 && y <= 447) {
			bg = bgs[4];
			if(saveCondition[4] != null){
				showPlayTime = true;
				getTime(4);
			}
			return;
		}
		if (x >= 674 && x <= 924 && y >= 330 && y <= 447) {
			bg = bgs[5];
			if(saveCondition[5] != null){
				showPlayTime = true;
				getTime(5);
			}
			return;
		}
		showPlayTime = false;
		bg = bgs[6];
	}
	/**
	 * 获得最后游戏时间
	 * @param i
	 */
	private void getTime(int i) {
		String[] temp = saveCondition[i].split(",");
		playTime = temp[0];
		lastTime = temp[1];
	}

	/**
	 * 鼠标点击监听，实现存档/读档/询问是否覆盖
	 * 
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		
		saveSucceed = false;
		
		int x = e.getX();
		int y = e.getY();
		if ((x>=56)&&(x<=56+95)&&(y>=457)&&(y<=457+82)) {
			Player.playSound("water");
			if (isStoring) {
				Player.stopMusic();
				setCurrentControl(new AVGScript(timeMills));
			} else {
				//返回主界面
				Player.stopMusic();
				setCurrentControl(new StartPanel(1));
			}
			return;
		}
		if (x >= 952 && x <= 983 && y >= 15 && y <= 40) {
			Player.playSound("water");
			GraphicsUtils.wait(300);
			System.exit(0);
			return;
		}
		
		// 区块一监听
		if (x >= 60 && x <= 310 && y >= 175 && y <= 310) {
			if (isStoring && allowStore) {
				
				if (saveCondition[0] != null) {
					// 在某个位置显示是否覆盖存档
					if (coverFile && coverLocation == 0) {
						saveFile(0,scene,true);
						Player.playSound("choose2");
						return;
					} else {
						coverFile = true;
						coverLocation = 0;
						Player.playSound("water");
						return;
					}
				} else {
					saveFile(0,scene,false);
					return;
				}
			} else if(!isStoring){
				if(saveCondition[0] != null){
				loadFile(StoreText.save1.getFilePath());}
				else return;
			}
			return;
		}
		if (x >= 367 && x <= 610 && y >= 175 && y <= 310) {
			if (isStoring && allowStore) {

				if (saveCondition[1] != null) {
					// 在某个位置显示是否覆盖存档
					if (coverFile && coverLocation == 1) {
						saveFile(1,scene,true);
						Player.playSound("choose2");
						return;
					} else {
						coverFile = true;
						coverLocation = 1;
						Player.playSound("water");
						return;
					}
				} else {
					saveFile(1,scene,false);
					return;
				}
			} else if(!isStoring) {
				if(saveCondition[1] == null){
					return;
				}
				
				loadFile(StoreText.save2.getFilePath());
			}
			return;
		}
		if (x >= 674 && x <= 924 && y >= 175 && y <= 310) {
			if (isStoring && allowStore) {

				if (saveCondition[2] != null) {
					// 在某个位置显示是否覆盖存档
					if (coverFile && coverLocation == 2) {
						saveFile(2,scene,true);
						Player.playSound("choose2");
						return;
					} else {
						coverFile = true;
						coverLocation =2;
						Player.playSound("water");
						return;
					}
				} else {
					
					saveFile(2,scene,false);
					return;
				}
			} else if(!isStoring) {
				if(saveCondition[2] == null){
					return;
				}
				loadFile(StoreText.save3.getFilePath());
			}
			return;
		}
		if (x >= 60 && x <= 310 && y >= 330 && y <= 447) {
			if (isStoring && allowStore) {

				if (saveCondition[3] != null) {
					// 在某个位置显示是否覆盖存档
					if (coverFile && coverLocation == 3) {
						saveFile(3,scene,true);
						Player.playSound("choose2");
						return;
					} else {
						coverFile = true;
						coverLocation =3;
						Player.playSound("water");
						return;
					}
				} else {
					
					saveFile(3, scene,false);
					return;
				}
			} else  if(!isStoring) {
				if(saveCondition[3] == null){
					return;
				}
				loadFile(StoreText.save4.getFilePath());
			}
			return;
		}
		if (x >= 367 && x <= 610 && y >= 330 && y <= 447) {
			if (isStoring &&allowStore) {

				if (saveCondition[4] != null) {
					// 在某个位置显示是否覆盖存档
					if (coverFile && coverLocation == 4) {
						Player.playSound("choose2");
						saveFile(4 ,scene,true);
						return;
					} else {
						coverFile = true;
						coverLocation = 4;
						Player.playSound("water");
						return;
					}
				} else {
					
					saveFile(4,scene,false);
					return;
				}
			} else if(!isStoring){
				if(saveCondition[4] == null){
					return;
				}
				loadFile(StoreText.save5.getFilePath());
			}
			return;
		}
		if (x >= 674 && x <= 924 && y >= 330 && y <= 447) {
			if (isStoring && allowStore) {

				if (saveCondition[5] != null) {
					if (coverFile && coverLocation == 5) {
						saveFile(5 , this.scene,true);
						Player.playSound("choose2");
						return;
					} else {
						coverFile = true;
						coverLocation =5;
						Player.playSound("water");
						return;
					}
				} else {
					saveFile(5 , this.scene,false);
					return;
				}
			} else if(!isStoring){
				if(saveCondition[5] == null){
					return;
				}
				loadFile(StoreText.save6.getFilePath());
			}
			return;
		}
		
		coverFile = false;
		coverLocation = -1;
	}

	/**
	 * 存储游戏
	 * 
	 * @param i 目标存档
	 * 
	 * @param reSave  是否为覆盖存档
	 */
	private void saveFile(int i, int scene, boolean reSave) {
		//计算游戏时长
		storeMessage(i);
		int hour, min, second = 0;
		hour = (int) timeMills / (1000 * 60 * 60 * 24);
		min = (int) (timeMills % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		second = (int) (timeMills % (1000 * 60)) / 1000;
		if (reSave) {

			String[] temp = saveCondition[i].substring(0,
					saveCondition[i].indexOf(",")).split(":");
			hour += Integer.parseInt(temp[0]);
			min += Integer.parseInt(temp[1]);
			second += Integer.parseInt(temp[2]);
		}
		saveCondition[i] = hour + ":" + min + ":" + second + ","
				+ df.format(date) + "," + scene;

		writeSaveCondition();
		saveSucceed = true;
		coverFile = false;
		allowStore = false;

	}

	/**
	 * 加载剧本
	 * 
	 * @param filePath
	 *            存档
	 */
	private void loadFile(String filePath) {
		copyFile(filePath, LSystem.tempFile);
		GraphicsUtils.wait(300);
		setCurrentControl(new AVGScript());
	}

	/**
	 * 存储剧本
	 * 
	 * @param i
	 *            第几号存储
	 */
	private void storeMessage(int i) {
		String filePath = null;
		switch (i) {
		case 0:
			filePath = StoreText.save1.getFilePath();
			break;
		case 1:
			filePath = StoreText.save2.getFilePath();
			break;
		case 2:
			filePath = StoreText.save3.getFilePath();
			break;
		case 3:
			filePath = StoreText.save4.getFilePath();
			break;
		case 4:
			filePath = StoreText.save5.getFilePath();
			break;
		case 5:
			filePath = StoreText.save6.getFilePath();
			break;
		}
		copyFile(LSystem.tempFile, filePath);
		// 复制文件

	}

	/**
	 * 用于复制文件
	 * 
	 * @param oldFile
	 *            源文件
	 * @param newFile
	 *            目标文件
	 */
	private void copyFile(String oldFile, String newFile) {
		try {
			FileInputStream in = new FileInputStream(new File(oldFile));
			FileOutputStream out = new FileOutputStream(new File(newFile));
			FileChannel inChannel = in.getChannel();
			FileChannel outChannel = out.getChannel();
			inChannel.transferTo(0, inChannel.size(), outChannel);
			in.close();
			out.close();
			inChannel.close();
			outChannel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获得当前存储状况
	 */
	private void getSaveCondition() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(
					StoreText.save.getFilePath())));
			String[] temp = new String[saveCondition.length];
			for (int i = 0; i < saveCondition.length; i++) {
				temp[i] = reader.readLine();
			}
			reader.close();

			for (int i = 0; i < saveCondition.length; i++) {
				String[] temp2 = temp[i].split(",");
				if (temp2[1].equalsIgnoreCase("empty")) {
					saveCondition[i] = null;
				} else {
					saveCondition[i] = temp2[2] + "," + temp2[3] + ","
							+ temp2[4];
				}

				// System.out.println(saveCondition[i]);
			}

		} catch (Exception e) {
			System.out.println("Save File Not Found");
			e.printStackTrace();
		}
	}

	/**
	 * 写入存储状况文件 但是具体的状态存储，包括修改saveconditin【i】
	 * 
	 * 应当在mouseclicked时完成
	 */
	private void writeSaveCondition() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					StoreText.save.getFilePath(), false));
			for (int i = 0; i < saveCondition.length; i++) {
				writer.write("save" + (i + 1) + ",");
				if (saveCondition[i] == null) {
					writer.write("empty,");
				} else {
					writer.write("full,");
					writer.write(saveCondition[i]);
				}
				writer.newLine();
			}
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// public static void main(String[] args) {
	// StorePanel test = new StorePanel(false , 0);
	// // test.writeSaveCondition();
	// test.getSaveCondition();
	// test.storeMessage(0);
	// System.out.println(test.df.format(test.date));
	// ;
	// }

}
