package avg;

import game.GameCursor;
import game.GraphicsUtils;
import game.SimpleControl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.swing.JFrame;


import media.Player;
import resource.BackgroundResource;
import resource.BaleResource;
import resource.CacheResource;
import resource.LSystem;
import ui.StartPanel;
import ui.StorePanel;
import ui.movie.Movie;
import command.Command;
import command.Conversion;

/**
 * AVG游戏主体，处理游戏中各种进程
 * 
 * @author czq，ymc，zsq
 *
 */
@SuppressWarnings("rawtypes")
public class AVGScript extends SimpleControl {

	/**
	 * 脚本解释器
	 */
	private Command command;
	/**
	 * 设置颜色
	 */
	private Color color;
	/**
	 * 字体
	 */
	private String fontName = LSystem.FONT;
	/**
	 * 是否为信息
	 */
	private boolean isMessage;

	/**
	 * 角色名
	 */
	private String roleName;
	/**
	 * 文本距离边界距离
	 */
	private MessagePrint mesPrint = new MessagePrint();

	/**
	 * 设置对话框在图像中位置
	 */
	private MessageDialog dialog = new MessageDialog();
	/**
	 * 是否已点击
	 */
	private boolean isClick;
	/**
	 * 脚本名
	 */
	private String scriptName;
	/**
	 * TODO
	 */
	private int[] flags;
	/**
	 * 选择第几项
	 */
	private int selectFlag = 0;
	/**
	 * 文本最大行数
	 */
	private int stringMaxLine = 8;
	/**
	 * 选项的信息 TODO
	 */
	private String[] selectMessages;
	/**
	 * 人物CG
	 */
	private CG cg = new CG();
//	/**
//	 * TODO
//	 */
//	private Chara character;
	/**
	 * 猫的运动
	 */
	private Pause pause = new Pause();
	/**
	 * 自动播放参数
	 */
	private int sleep;
	/**
	 * 控制自动播放参数
	 */
	private int sleepMax;
	// /**
	// * 运行外部类
	// */
	// private RunClass runClass;
	/**
	 * 背景摇晃
	 */
	private int shakeNumber;
	/**
	 * 人物是否正在移动
	 */
	private boolean moving;
	/**
	 * 是否已经选中了信息
	 */
	private boolean isSelectMessage;
	/**
	 * 音乐图标，默认开启
	 */
	private Image MusicIcon = BackgroundResource.AVGMusicIcon1;
	/**
	 * 设置图标，当鼠标移近时会变换
	 */
	private Image SettingIcon = BackgroundResource.AVGSettingIcon1;
	private Image CloseIcon = BackgroundResource.AVGCloseIcon1;
	private Image MinIcon = BackgroundResource.AVGMinIcon1;
	/**
	 * 音乐是否播放中
	 */
	private boolean musicPlaying = LSystem.musicOn;
	/**
	 * 现在的音乐名
	 */
	private String nowMusic;
	/**
	 * 游戏开始的时间
	 */
	private long startTime;
	// /**
	// * 对话框风格
	// */
	// private Image windowCanvas = LSystem.windowCanvas;
	// TODO 估计用不上，直接调用lsystem就可以了
	/**
	 * 不要问我这是干啥的，我也不知道 -----
	 */
	private boolean ispassing;
	/**
	 * 
	 */
	private boolean drawReac;
	/**
	 * 返回主界面按钮
	 */
	private Image GoBackIcon = BackgroundResource.AVGGoBackIcon1;
	/**
	 * 确认是否返回主界面
	 */
	private boolean theFirstTime;
	/**
	 * 是否存档
	 */
	private boolean isSaved;
	/**
	 * 是否选择存档
	 */
	private boolean isSelectSaved;
	/**
	 * 乌龟路经
	 */
	private String[] turtlePath = { "image/start/load/save1.png",
			"image/start/load/save2.png" };
	/**
	 * 鼠标切换
	 */
	private int count;;

	/**
	 * 用于在过场动画中再次启动脚本
	 */
	public AVGScript() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
					new File(LSystem.tempFile)));

			this.command = (Command) ois.readObject();
			ois.close();
			initialize();
			pause.start();

			nextScript();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 构造器 ，载入指定脚本文件
	 * 
	 * @param initscript
	 */
	public AVGScript(final String initscript) {
		format(initscript, true);

	}

	/**
	 * 用于在一个脚本中读入另一个脚本
	 * 
	 * @param initscript
	 * @param startTime
	 */
	public AVGScript(final String initscript, long startTime) {
		this.startTime = startTime;
		format(initscript, false);

	}

	public AVGScript(long startTime) {
		this();
		this.startTime = startTime;
	}

	/**
	 * 用于在游戏中进入设置界面后强制写回背景
	 * 
	 * @param nowImage
	 */
	public AVGScript(Image nowImage, String nowMusic) {
		this();
		cg.setBackgroundCG(nowImage);
		Player.playMusic(nowMusic);
	}

	/**
	 * 执行AVGSCript
	 * 
	 * @param initscript
	 */
	public void format(final String initscript, boolean aNewOne) {
		pause.start();

		initialize(initscript, aNewOne);

		runScript(scriptName);
	}

	/**
	 * 初始化AVGSCript
	 * 
	 * @param initscript
	 */
	private void initialize(final String initscript, boolean aNewOne) {
		sleep = 0;
		sleepMax = 500;
		scriptName = initscript;
		shakeNumber = 0;
		selectFlag = 0;
		shakeNumber = -1;
		isClick = false;
		isSelectMessage = false;
		isMessage = false;
		selectMessages = new String[stringMaxLine];
		flags = new int[stringMaxLine];
		pause.intermit();
		dialog.initialize();
		CacheResource.ADV_CHARAS.clear();
		if (aNewOne) {
			startTime = System.currentTimeMillis();
		}
		isSaved=false;
		isSelectSaved=false;

	}

	/**
	 * 初始化
	 */
	private void initialize() {
		sleep = 0;
		sleepMax = 500;
		shakeNumber = 0;
		selectFlag = 0;
		shakeNumber = -1;
		isClick = false;
		isSelectMessage = false;
		isMessage = false;
		selectMessages = new String[stringMaxLine];
		flags = new int[stringMaxLine];
		pause.intermit();
		dialog.initialize();
		startTime = System.currentTimeMillis();
		isSaved = false;
		isSelectSaved = false;

	}

	/**
	 * 
	 */
	public void finalize() {
		flush();
	}

	/**
	 * 
	 */
	public void flush() {
		cg = null;
		dialog = null;
		mesPrint = null;
		selectMessages = null;
		flags = null;
		pause.stop();
		pause = null;
	}

	/**
	 * 
	 * @return
	 */
	public boolean nextMessages() {
		return mesPrint.next();
	}

	/**
	 * 绘制游戏界面
	 */
	public synchronized void draw(final Graphics g) {

		if (cg.getBackgroundCG() != null) {
			// 如果有摇晃的情况
			if (shakeNumber > 0) {
				g.drawImage(cg.getBackgroundCG(), shakeNumber / 2
						- SimpleControl.rand.nextInt(shakeNumber), shakeNumber
						/ 2 - SimpleControl.rand.nextInt(shakeNumber), null);
			} else {
				// 画背景
				g.drawImage(cg.getBackgroundCG(), 0, 0, LSystem.WIDTH,
						LSystem.HEIGHT, 0, 0,
						cg.getBackgroundCG().getWidth(null), cg
								.getBackgroundCG().getHeight(null), null);
				// 画小图标
				if(!ispassing){
					paintIcons(g);
				}
				
			}
		}
		// 画人物cg
		if(!isSelectSaved){
			for (int i = 0; i < cg.getCharas().size(); i++) {
				Chara chara = (Chara) cg.getCharas().get(i);
				if (chara.next()) {
					moving = true;
				} else {
					moving = false;
				}
				//非当前人物透明度为原来的0.75
				if(i < cg.getCharas().size() - 1){
					GraphicsUtils.setAlpha(g, 0.75);
				}else{
					GraphicsUtils.setAlpha(g, 1.0d);
				}
				chara.draw(g);
			}
		}
		else{
			//System.out.println("isSelectSaved");
			Chara character= new Chara(turtlePath[1],350,120);
			character.setMove(false);
			character.drawStatic(g);
		}
		/*
		 * 选项显示
		 */
		if (isMessage && selectMessages != null && selectMessages.length > 0) {
			boolean next = false;
			// 字体大小
			// TODO
			int size = LSystem.selectMessageSize;
			int gap = LSystem.selectMessageGap;
			pause.go();
			// 画对话框
			dialog.showDialog(g);
			g.setFont(GraphicsUtils.getFont(fontName, 0, size));

			g.setColor(Color.white);
			// 画人物名字框框
			dialog.showRoleName(g, roleName);
			if (isSelectMessage) {
				g.setColor(LSystem.defaultColor);
				// g.setFont(new Font(LSystem.FONT,Font.,LSystem.FONT_TYPE));
				char[] meschars;
				int sizeWidth = -(size * 2);
				int left = dialog.getMESSAGE_LINE_X() + 4;
				int top = dialog.getMESSAGE_LINE_Y() + 25;
				for (int i = 0; i < stringMaxLine; i++) {
					meschars = selectMessages[i].toCharArray();
					for (int j = 0; j < meschars.length; j++) {
						g.drawString(String.valueOf(meschars[j]), (size * j)
								+ left - sizeWidth, top + i * gap);
					}
					if (flags[selectFlag] != -1) {
						dialog.showDialog(g, selectFlag, size,
								LSystem.FONT_SIZE);
					}
				}
			} else {
				next = mesPrint.next();
				mesPrint.draw(g);
				if (LSystem.auto&&!isSaved) {
					sleep++;
					//System.out.println(sleep);
					sleepControl();
				}
			}
			// 画猫
			if (!next && !moving) {
				pause.draw(
						g,
						dialog.getMESSAGE_LINE_X2()
								- dialog.getMESSAGE_LINE_X1() - 30,
						dialog.getMESSAGE_LINE_Y2() - 60);

			}
		}

	}

	/**
	 * 
	 */
	private void sleepControl() {
		if (isMessage && !isSelectMessage) {
			if (sleep > sleepMax) {
				sleep = 0;
				nextScript();
			}
		}
	}

	/**
	 * 画小图标 TODO
	 * 
	 * @param g
	 */
	private void paintIcons(Graphics g) {
		if (drawReac) {
			g.setColor(Color.BLUE);
			GraphicsUtils.setAlpha(g, 0.3d);
			g.fillRect(920, 0, 50, 330);
			GraphicsUtils.setAlpha(g, 1.0d);
		}
		g.drawImage(MinIcon, 930, 185, 30, 30, null);
		g.drawImage(MusicIcon, 930, 135, 30, 30, null);
		g.drawImage(SettingIcon, 930, 75, 30, 30, null);
		g.drawImage(CloseIcon, 930, 15, 30, 30, null);
		g.drawImage(GoBackIcon, 930, 225, 30, 30, null);
		g.drawImage(LSystem.Cursor, 930, 275, 30, 30, null);
		if (theFirstTime) {
			g.drawImage(BackgroundResource.AVGGoBackIcon3, 748, 155, null);
		}
	}

	/**
	 * 告知command玩家的选择
	 * 
	 * @param type
	 *            选择了第几项
	 */
	public synchronized void select(int type) {
		if (command != null) {
			command.select(type);
			isSelectMessage = false;
		}
	}

	/**
	 * 重置鼠标图像 当碰到选项时切换鼠标图标 TODO
	 */
	private synchronized void resetFlag() {
		if (!isMessage) {
			return;
		}
		if (selectMessages != null) {
			int count = (mouse.y - dialog.getMESSAGE_LINE_Y()) / 25;
			if (count < 0) {
				count = 0;
				return;
			}
			if (count >= stringMaxLine) {
				count = 0;
				return;
			}
			if (flags[count] != -1) {
				isClick = true;
			}
			int maxSize = 0;
			for (; maxSize < selectMessages.length; maxSize++) {
				if (selectMessages[maxSize].length() == 0) {
					break;
				}
			}
			maxSize -= 1;
			if (maxSize > 0 && count > maxSize) {
				count = maxSize;
			}
			selectFlag = count;
		}
	}

	/**
	 * 逐行执行脚本文件
	 */
	private synchronized void nextScript() {
		isMessage = false;
		isClick = false;
		int count = 0;
		for (int i = 0; i < stringMaxLine; i++) {
			selectMessages[i] = "";
			flags[i] = -1;
		}

		for (; command.scriptHasNext();) {
			// 获得command处理的结果
			String result = command.doExecute();
			// 恢复选择的初始值
			command.select(-1);
			if (result == null) {
				nextScript();
				break;
			}
			// 分解命令
			List commands = Conversion.stringSplit(result, " ");
			int size = commands.size();
			String cmdFlag = (String) commands.get(0);

			String mesFlag = null, orderFlag = null, lastFlag = null;
			if (size == 2) {
				mesFlag = (String) commands.get(1);
			} else if (size == 3) {
				mesFlag = (String) commands.get(1);
				orderFlag = (String) commands.get(2);
			} else if (size == 4) {
				mesFlag = (String) commands.get(1);
				orderFlag = (String) commands.get(2);
				lastFlag = (String) commands.get(3);
			}
			if(cmdFlag.equalsIgnoreCase("return")){
				setCurrentControl(new StartPanel());
			}
			if (cmdFlag.equalsIgnoreCase("mes")) {
				roleName = null;
				isMessage = true;
				roleName = Command.getNameTag(mesFlag, "{", "}");
				// targetMessage表示人物说的话
				String targetMessage = null;
				if (roleName != null) {
					int nameLength = roleName.length() + 2;
					targetMessage = mesFlag.substring(nameLength,
							mesFlag.length());
				} else {
					targetMessage = mesFlag;
				}
				mesPrint.setMessage(targetMessage);
				count = 0;// TODO 暂且认为是处理单句信息太多的问题
				flags[count] = -1;
				count++;
				if (count == stringMaxLine) {
					break;
				}
				break;
			}

			if (cmdFlag.equalsIgnoreCase("selects")) {
				isMessage = true;
				isSelectMessage = true;
				String[] selects = command.getReads();
				for (int i = 0; i < selects.length; i++) {
					selectMessages[i] = selects[i];
					flags[i] = i;
				}
				break;
			}
			if (cmdFlag.equalsIgnoreCase("cg")) {
				// 关于cg移动的
				if(mesFlag.equalsIgnoreCase("save")){
					System.out.println("ok");
					isSaved=true;
					cg.addImage(turtlePath[0], 350, 120);
				}
				// 删除cg 可单独删除某个cg TODO
				else if (mesFlag.equalsIgnoreCase("del")) {
					if (orderFlag != null) {
						cg.removeImage(orderFlag);
					} else {
						cg.clear();
					}
				}
				// cg 表情变换 左边的将被删掉
				else if (lastFlag != null && orderFlag.equalsIgnoreCase("to")) {
					Chara character = cg.removeImage(mesFlag);
					// 将to后的cg显示出来
					int x = character.getX();
					int y = character.getY();
					character = new Chara(lastFlag, 0, 0);
					character.setMove(false);
					character.setX(x);
					character.setY(y);
					cg.addChara(lastFlag, character);

				} else {
					// cg 移动 x y 分别为坐标
					int x = 0, y = 50;
					if (orderFlag != null) {
						x = Integer.parseInt(orderFlag);
					}
					if (size >= 4) {
						y = Integer.parseInt((String) commands.get(3));
					}
					cg.addImage(mesFlag, x, y);
				}
				continue;
			}

			// 闪现
			if (cmdFlag.equalsIgnoreCase("flash")) {
				String[] colors = mesFlag.split(",");
				if (color == null && colors.length == 3) {
					color = new Color(Integer.parseInt(colors[0]),
							Integer.parseInt(colors[1]),
							Integer.parseInt(colors[2]));
					// sleep = 20;
					isMessage = false;
				}
			}

			// 画背景
			if (cmdFlag.equalsIgnoreCase("gb")) {
				cg.setBackgroundCG(GraphicsUtils.loadImage(mesFlag));

				continue;
			}
			// 图片震动 ，参数为振幅
			if (cmdFlag.equalsIgnoreCase("shake")) {
				shakeNumber = Integer.valueOf(mesFlag).intValue();
				continue;
			}

			// if (cmdFlag.equalsIgnoreCase("run")) {
			// runClass = new RunClass(true, mesFlag);
			// isMessage = false;
			// continue;
			// }
			// TODO 到达存储点时
			if (cmdFlag.equalsIgnoreCase("store")) {
				storeCommand();
				setCurrentControl(new StorePanel(true,
						Integer.parseInt(mesFlag), System.currentTimeMillis()
								- startTime));
				continue;
			}
			if (cmdFlag.equalsIgnoreCase("bgm")) {
				if (musicPlaying) {
					Player.stopMusic();
				}
				nowMusic = mesFlag;
				Player.playMusic(nowMusic);
				continue;
			}
			if (cmdFlag.equalsIgnoreCase("sound")) {
				Player.playSound(mesFlag);
				continue;
			}
//			// 过场动画
//			if (cmdFlag.equalsIgnoreCase("change")) {
//				storeCommand();
//				setCurrentControl(new PlayMovie(mesFlag,
//						Integer.parseInt(orderFlag), startTime));
//
//				continue;
//
//			}
			if (cmdFlag.equalsIgnoreCase("title1")) {
				storeCommand();
				result = result.substring(7);
				if (musicPlaying) {
					Player.stopMusic();
					musicPlaying = false;
				}
				setCurrentControl(new Movie(result.split(" "), startTime));
				continue;
			}
			if (cmdFlag.equalsIgnoreCase("title2")) {
				storeCommand();
				result = result.substring(9);
				if (musicPlaying) {
					Player.stopMusic();
					musicPlaying = false;
				}
				setCurrentControl(new Movie(result.split(" "),
						MessagePrint.getColor(mesFlag.charAt(0)), startTime));
				break;
			}
			if (cmdFlag.equalsIgnoreCase("title3")
					|| cmdFlag.equalsIgnoreCase("title4")) {
				storeCommand();
				String[] temp;
				if (commands.size() == 4) {
					temp = new String[1];
					temp[0] = (String) commands.get(3);
				} else {
					temp = new String[2];
					temp[0] = (String) commands.get(3);
					temp[1] = (String) commands.get(4);
				}
				if (cmdFlag.equalsIgnoreCase("title3")) {
					if (musicPlaying) {
						Player.stopMusic();
						musicPlaying = false;
					}
					setCurrentControl(new Movie(temp,
							MessagePrint.getColor(((String) commands.get(1))
									.charAt(0)),
							Integer.parseInt((String) commands.get(2)),
							startTime));
				} else {
					// System.out.println(commands);
					// System.out.println(temp);
					// System.out.println(mesFlag);
					// System.out.println(orderFlag);
					setCurrentControl(new Movie(temp,
							MessagePrint.getColor(((String) commands.get(1))
									.charAt(0)), (String) commands.get(2),
							startTime));
				}
				break;
			}
			if (cmdFlag.equalsIgnoreCase("pass")) {
				if (musicPlaying) {
					Player.stopMusic();
					musicPlaying = false;
				}
				cg.setBackgroundCG(GraphicsUtils
						.loadImage("image/system/icon/black.jpg"));
				ispassing = true;
				break;
			}
			if (cmdFlag.equalsIgnoreCase("jump")) {
				setCurrentControl(new AVGScript(mesFlag, startTime));
			}
			ispassing = false;
		}
	}

	@Override
	public void next() {
		if (ispassing) {
			nextScript();
		}
	}

	/**
	 * 将command序列化保存起来
	 */
	private void storeCommand() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(LSystem.tempFile, false));
			oos.writeObject(command);
			oos.flush();
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 启动脚本
	 * 
	 * @param fileName
	 */
	private synchronized void runScript(final String fileName) {
		if (command == null) {
			command = new Command(fileName);
			Command.resetCache();
		} else {
			command.formatCommand(fileName, Command.includeFile(fileName));
		}
		nextScript();
	}

	/**
	 * 鼠标移动监听
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
		resetFlag();
		if (e.getX() >= 928 && e.getX() <= 960 && e.getY() >= 14
				&& e.getY() <= 260) {
			drawReac = true;
		} else {
			drawReac = false;
		}
		// 添加小物件监听
		if (e.getX() >= 928 && e.getX() <= 960 && e.getY() >= 14
				&& e.getY() <= 44) {
			CloseIcon = BackgroundResource.AVGCloseIcon2;
		} else {
			CloseIcon = BackgroundResource.AVGCloseIcon1;

		}
		if (e.getX() >= 928 && e.getX() <= 960 && e.getY() >= 125
				&& e.getY() <= 155) {
			MusicIcon = BackgroundResource.AVGMusicIcon2;
		} else {

			if (musicPlaying)
				MusicIcon = BackgroundResource.AVGMusicIcon1;
			else
				MusicIcon = BackgroundResource.AVGMusicIcon3;
		}
		if (e.getX() >= 928 && e.getX() <= 960 && e.getY() >= 70
				&& e.getY() <= 100) {
			SettingIcon = BackgroundResource.AVGSettingIcon2;
		} else {
			SettingIcon = BackgroundResource.AVGSettingIcon1;
		}
		if (e.getX() >= 928 && e.getX() <= 960 && e.getY() >= 170
				&& e.getY() <= 200) {
			MinIcon = BackgroundResource.AVGMinIcon2;

		} else {
			MinIcon = BackgroundResource.AVGMinIcon1;
		}
		if (e.getX() >= 928 && e.getX() <= 960 && e.getY() >= 225
				&& e.getY() <= 255) {
			GoBackIcon = BackgroundResource.AVGGoBackIcon2;

		} else {
			GoBackIcon = BackgroundResource.AVGGoBackIcon1;
		}
		if(isSaved){
			if(e.getX()>=400&&e.getX()<=560&&e.getY()>=250&&e.getY()<=360){
				isSelectSaved=true;
			}
			else{
				isSelectSaved=false;
			}
		}
	}

	/**
	 * 鼠标按下监听 包括获得选项 以及读下一行脚本
	 */
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		resetFlag();
		sleep=0;
		// 如果人物正在移动则返回
		if (moving) {
			return;
		}
		if (e.getX() >= 928 && e.getX() <= 960 && e.getY() >= 225
				&& e.getY() <= 255) {
			Player.playSound("water");
			if (theFirstTime) {
				theFirstTime = false;
				setCurrentControl(new StartPanel());
			} else {
				theFirstTime = true;
				return;
			}

		}
		// TODO 小物件监听
		// 关闭按钮
		if (e.getX() >= 928 && e.getX() <= 960 && e.getY() >= 14
				&& e.getY() <= 44) {
			Player.playSound("water");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.exit(0);
		}
		// 声音按钮
		if (e.getX() >= 928 && e.getX() <= 960 && e.getY() >= 125
				&& e.getY() <= 155) {
			if (musicPlaying) {
				musicPlaying = false;
				Player.playSound("water");
				MusicIcon = BackgroundResource.AVGMusicIcon3;
				Player.stopMusic();
			} else {
				if (nowMusic != null) {
					musicPlaying = true;
					Player.playSound("water");
					MusicIcon = BackgroundResource.AVGMusicIcon1;
					Player.playMusic(nowMusic);
				}
			}
			return;
		}
		if (e.getX() >= 928 && e.getX() <= 960 && e.getY() >= 70
				&& e.getY() <= 100) {
			storeCommand();
			Player.playSound("water");
			setCurrentControl(new StartPanel(true, cg.getBackgroundCG(),
					nowMusic));
			return;
		}
		if (e.getX() >= 928 && e.getX() <= 960 && e.getY() >= 170
				&& e.getY() <= 200) {
			Player.playSound("water");
			// TODO 最小化
			try {
				Thread.sleep(300);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			LSystem.frame.setExtendedState(JFrame.ICONIFIED);
			return;
		}
		if(e.getX() >= 928 && e.getX() <= 960 && e.getY() >= 275
				&& e.getY() <= 305){
			Player.playSound("water");
			if(count == 0){
				LSystem.currentCanvas.setCursor(GameCursor.getCursor(BaleResource.creeseIconFile1));
				LSystem.Cursor = BaleResource.creeseIcon1;
			}else if(count == 1){
				LSystem.currentCanvas.setCursor(GameCursor.getCursor(BaleResource.creeseIconFile2));
				LSystem.Cursor = BaleResource.creeseIcon2;
			}else if(count == 2){
				LSystem.currentCanvas.setCursor(GameCursor.getCursor(BaleResource.creeseIconFile3));
				LSystem.Cursor = BaleResource.creeseIcon3;
			}else if(count == 3){
				LSystem.currentCanvas.setCursor(GameCursor.getCursor(BaleResource.creeseIconFile4));
				LSystem.Cursor = BaleResource.creeseIcon4;
			}else if(count == 4){
				LSystem.currentCanvas.setCursor(GameCursor.getCursor(BaleResource.creeseIconFile5
						));
				LSystem.Cursor = BaleResource.creeseIcon5;

			}
			count++;
			if(count == 5){
				count = 0;
			}
			return;
		}

		/*
		 * if (runClass != null && runClass.isRun()) { pause.stop();
		 * LSystem.currentGameHandler.setControl(runClass.doInvoke()); return; }
		 */
		// if (!isSelectMessage && SimpleControl.left_click && sleep <= 0) {
		// if (!isMessage) {
		// isMessage = true;
		// }

		// }
		if(isSaved){
			if(e.getX()>=400&&e.getX()<=560&&e.getY()>=250&&e.getY()<=360){
				isSaved=false;
			}
			else{
				return;
			}
		}
		if(isSelectMessage){
			if(e.getX()>=50&&e.getX()<=940&&e.getY()>=410&&e.getY()<=580){}
			else
				return;
		}
		if (isSelectMessage && isMessage && isClick && SimpleControl.left_click) {
			if (flags[selectFlag] != -1) {
				// 向command里注入变量
				select(flags[selectFlag]);
				isSelectMessage = false;
			}
		}
		theFirstTime = false;
		nextScript();

		// GraphicsUtils.wait(10);
	}

}
