package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;

import avg.AVGScript;
import media.Player;
import resource.BackgroundResource;
import resource.ImgStart;
import resource.LSystem;
import ui.movie.JPanelMovie;
import game.SimpleControl;
/**
 * 重新对起始画面进行制作
 * @author wjc,czq
 *
 */
public class StartPanel extends SimpleControl {
	/**
	 * 字体类型.
	 * 字体颜色.
	 * 边框类型.
	 * 字体大小.
	 * 人物速度.
	 * 自动播放.
	 * 背景音效.
	 * StartPanel 控制
	 */
	public static int font = 1;//1~5幼圆/宋体/黑体/楷体/隶书
	public static int edging = 1;//1~5对应window1~5
	public static int color = 1;//1~5白黑灰蓝橙
	public static int size = 2;//1~3小中大
	public static int speed = 2;//1~3慢中快
	/**
	 * 默认图片
	 */
	Image temp = null;
	/**
	 * 缓冲图片
	 */
	Image buffer = ImgStart.background1;
	/**
	 * 0-开场动画 1-主界面2-loadgame3-settings4-AboutUs动画5-无监听
	 */
	int state;
	/**
	 * 动画序列状态
	 */
	int step;
	/**
	 * 鼠标x坐标
	 */
	int x = 0;
	/**
	 * 鼠标y坐标
	 */	
	int y = 0;
	/**
	 * 用于从AVG中返回
	 */
	private boolean isAVG;
	private Image nowImage;
	/**
	 * 从AVG取得的音乐
	 */
	private String nowMusic;
	/**
	 * 能否设置边框类型
	 */
	private boolean canEdging = true;	
			
	public StartPanel() {
		state = 0;
		Player.playMusic("o");
		new Thread(new createMovie1()).start();
	}
	
	public StartPanel(int state) {
		this.state = state;
		Player.playMusic("o");
	}
	/**
	 * 用于在AVG中设置界面时
	 * @param isAVG
	 * @param nowImage
	 */
	public StartPanel(boolean isAVG,Image nowImage,String nowMusic){
		this(3);
		this.isAVG = isAVG;
		this.nowImage = nowImage;
		this.nowMusic = nowMusic;
		
	}
	
	@Override
	public void draw(Graphics g) {
		
		if(state==3)
			drawsettings(g);
		else
			g.drawImage(temp,0,0,LSystem.WIDTH,LSystem.HEIGHT,null);
	}
	
	private void drawsettings(Graphics g) {
		g.drawImage(temp,0,0,LSystem.WIDTH,LSystem.HEIGHT,null);
		if(!canEdging)
			g.drawImage(ImgStart.wrong,160,380,null);
		if(edging==3&&color==1)
			g.drawImage(ImgStart.ugly,350,472,null);
		switch(color){
		case 1:g.setColor(Color.WHITE);break;
		case 2:g.setColor(Color.BLACK);break;
		case 3:g.setColor(Color.LIGHT_GRAY);break;
		case 4:g.setColor(Color.BLUE);break;
		case 5:g.setColor(Color.ORANGE);break;
		}
		switch(font){
		case 1:g.setFont(new Font("幼圆",Font.BOLD,40));g.drawString("幼圆",250,125);LSystem.FONT="幼圆";break;
		case 2:g.setFont(new Font("宋体",Font.BOLD,40));g.drawString("宋体",250,125);LSystem.FONT="宋体";break;
		case 3:g.setFont(new Font("黑体",Font.BOLD,40));g.drawString("黑体",250,125);LSystem.FONT="黑体";break;
		case 4:g.setFont(new Font("楷体",Font.BOLD,40));g.drawString("楷体",250,125);LSystem.FONT="楷体";break;
		case 5:g.setFont(new Font("隶书",Font.BOLD,40));g.drawString("隶书",250,125);LSystem.FONT="隶书";break;
		}
		switch(color){
		case 1:g.setColor(Color.WHITE);g.drawString("白色",717,88);break;
		case 2:g.setColor(Color.BLACK);g.drawString("黑色",717,88);break;
		case 3:g.setColor(Color.LIGHT_GRAY);g.drawString("灰色",717,88);break;
		case 4:g.setColor(Color.BLUE);g.drawString("蓝色",717,88);break;
		case 5:g.setColor(Color.ORANGE);g.drawString("橙色",717,88);break;
		}
		switch(edging){
		case 1:g.drawImage(BackgroundResource.windowsCanvas1,230,243,null);break;
		case 2:g.drawImage(BackgroundResource.windowsCanvas2,230,243,null);break;
		case 3:g.drawImage(BackgroundResource.windowsCanvas3,230,243,null);break;
		case 4:g.drawImage(BackgroundResource.windowsCanvas4,230,243,null);break;
		case 5:g.drawImage(BackgroundResource.windowsCanvas5,230,243,null);break;
		}
		switch(size){
		case 1:g.drawImage(ImgStart.cloud,725,167,null);break;
		case 2:g.drawImage(ImgStart.cloud,800,167,null);break;
		case 3:g.drawImage(ImgStart.cloud,875,167,null);break;
		}
		switch(speed){
		case 1:g.drawImage(ImgStart.cloud,725,266,null);break;
		case 2:g.drawImage(ImgStart.cloud,800,266,null);break;
		case 3:g.drawImage(ImgStart.cloud,875,266,null);break;
		}
		if(LSystem.auto==true)
			g.drawImage(ImgStart.check1,718,373,null);
		else
			g.drawImage(ImgStart.check0,718,373,null);
		if(LSystem.musicOn==true)
			g.drawImage(ImgStart.check1,718,480,null);
		else
			g.drawImage(ImgStart.check0,718,480,null);
	}

	@Override
	public void next(){
//		if(!LSystem.musicOn){
//			Player.stopMusic();
//		}else{
//			Player.playMusic("o");
//		}
	//	Player.playMusic("us");
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		//super.mousePressed(e);
		 x = e.getX();
		 y = e.getY();
		 if(state==0){
			 click_0();
		 }else if(state==1){
			 click_1();
		 }else if(state==2){
			 click_2();
		 }else if(state==3){
			 click_3();
		 }else if(state==4){
			 click_4();
		 }else if(state==5){
			 
		 }
		 
}
	private void click_4() {
		step = 456;
	}
	private void click_3() {
		if((x>=954)&&(x<=954+33)&&(y>=15)&&(y<=15+33)){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.exit(0);
		}else if((x>=56)&&(x<=56+95)&&(y>=457)&&(y<=457+82)){
			Player.playSound("water");
			if(isAVG){
				Player.stopMusic();
				setCurrentControl(new AVGScript(nowImage,nowMusic));
				return;
			}
			state = 1;
			temp = ImgStart.background1;
		}else if((x>=130)&&(x<=130+85)&&(y>=153)&&(y<=153+25)){
			Player.playSound("water");
			if(font==5)
				font = 1;
			else
				font ++;
		}else if((x>=132)&&(x<=132+85)&&(y>=290)&&(y<=290+25)){
			Player.playSound("water");
			if(isAVG){
				canEdging = false;
			}else{
				if(edging==5)
					edging = 1;
				else
					edging ++;
					switch(edging){
					case 1:LSystem.windowCanvas = BackgroundResource.windowsCanvas1;break;
					case 2:LSystem.windowCanvas = BackgroundResource.windowsCanvas2;break;
					case 3:LSystem.windowCanvas = BackgroundResource.windowsCanvas3;break;
					case 4:LSystem.windowCanvas = BackgroundResource.windowsCanvas4;break;
					case 5:LSystem.windowCanvas = BackgroundResource.windowsCanvas5;break;
					}
			}
		}else if((x>=587)&&(x<=587+85)&&(y>=110)&&(y<=110+25)){
			Player.playSound("water");
			if(color==5)
				color = 1;
			else
				color ++;
			switch(color){
			case 1:LSystem.defaultColor=Color.WHITE;break;
			case 2:LSystem.defaultColor=Color.BLACK;break;
			case 3:LSystem.defaultColor=Color.LIGHT_GRAY;break;
			case 4:LSystem.defaultColor=Color.BLUE;break;
			case 5:LSystem.defaultColor=Color.ORANGE;break;
			}
		}else if((x>=718)&&(x<=718+28)&&(y>=373)&&(y<=373+28)){
			Player.playSound("water");
			LSystem.auto = ! LSystem.auto;
		}else if((x>=718)&&(x<=718+28)&&(y>=480)&&(y<=480+28)){
			Player.playSound("water");
			LSystem.musicOn = !LSystem.musicOn;
		}else if((x>=718)&&(x<=718+75)&&(y>=167)&&(y<=167+28)){
			Player.playSound("water");
			LSystem.FONT_TYPE = 15;
			LSystem.messageSize=56;
			LSystem.selectMessageGap=20;
			LSystem.selectMessageSize=15;
			size = 1;
		}else if((x>=793)&&(x<=793+75)&&(y>=167)&&(y<=167+28)){
			Player.playSound("water");
			LSystem.FONT_TYPE = 20;
			size = 2;
		}else if((x>=868)&&(x<=868+75)&&(y>=167)&&(y<=167+28)){
			Player.playSound("water");
			LSystem.FONT_TYPE = 25;
			LSystem.messageSize=32;
			LSystem.selectMessageGap=25;
			LSystem.selectMessageSize=25;
			size = 3;
		}else if((x>=718)&&(x<=718+75)&&(y>=264)&&(y<=264+28)){
			Player.playSound("water");
			LSystem.moveSpeed = 5;
			speed = 1;
		}else if((x>=793)&&(x<=793+75)&&(y>=264)&&(y<=264+28)){
			Player.playSound("water");
			LSystem.moveSpeed = 10;
			speed = 2;
		}else if((x>=868)&&(x<=868+75)&&(y>=264)&&(y<=264+28)){
			Player.playSound("water");
			LSystem.moveSpeed = 50;
			speed = 3;
		}else{
			temp = ImgStart.settings1;
		}
		
	}
	private void click_2() {
		// TODO Auto-generated method stub
		if((x>=954)&&(x<=954+33)&&(y>=15)&&(y<=15+33)){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.exit(0);
		}else if((x>=56)&&(x<=56+95)&&(y>=457)&&(y<=457+82)){
			Player.playSound("water");
			state = 1;
			temp = ImgStart.background1;
		}else if((x>=60)&&(x<=60+250)&&(y>=178)&&(y<=178+117)){
			state = 5;
			
		}else if((x>=370)&&(x<=370+250)&&(y>=178)&&(y<=178+117)){
			state = 5;
			
		}else if((x>=675)&&(x<=675+250)&&(y>=178)&&(y<=178+117)){
			state = 5;
			
		}else if((x>=60)&&(x<=60+250)&&(y>=332)&&(y<=332+117)){
			state = 5;
			
		}else if((x>=370)&&(x<=370+250)&&(y>=332)&&(y<=332+117)){
			state = 5;
			
		}else if((x>=675)&&(x<=675+250)&&(y>=332)&&(y<=332+117)){
			state = 5;
			
		}else{
			temp = ImgStart.sky1;
		}
	}
	private void click_1() {
		// TODO Auto-generated method stub
		if((x>=954)&&(x<=954+33)&&(y>=15)&&(y<=15+33)){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.exit(0);
		}else if((x>=370)&&(x<=370+250)&&(y>=300)&&(y<=300+47)){
			Player.playSound("water");
//			Player.stopMusic();
			state = 5;
			//removeCurrentControl();
			setCurrentControl(new AVGScript("script/script1.txt"));
			return;
		}else if((x>=370)&&(x<=370+250)&&(y>=362)&&(y<=362+47)){
			Player.playSound("water");
			Player.stopMusic();
			setCurrentControl(new StorePanel(false, 0, 0));
		}else if((x>=370)&&(x<=370+250)&&(y>=424)&&(y<=424+47)){
			Player.playSound("water");
			state = 3;
			temp = ImgStart.settings1;
		}else if((x>=370)&&(x<=370+250)&&(y>=486)&&(y<=486+47)){
			Player.playSound("water");
			state = 4;
			Player.stopMusic();
			Player.playMusic("us");
			new Thread(new createMovie2()).start();
			
		}else{
			
		}
	}
	private void click_0() {
		// TODO Auto-generated method stub
		step = 316;
	}
	@Override
	public void mouseMoved(MouseEvent e) {
//		super.mouseMoved(e);
		 x = e.getX();
		 y = e.getY();
		 if(state==0){
			 move_0();
		 }else if(state==1){
			 move_1();
		 }else if(state==2){
			 move_2();
		 }else if(state==3){
			 move_3();
		 }else if(state==4){
			 move_4();
		 }else if(state==5){
			 
		 }
		
	}
	
	
	private void move_4() {
		// TODO Auto-generated method stub
		
	}

	private void move_3() {
		// TODO Auto-generated method stub
		if((x>=954)&&(x<=954+33)&&(y>=15)&&(y<=15+33)){
			temp = ImgStart.settings01;
		}else if((x>=56)&&(x<=56+95)&&(y>=457)&&(y<=457+82)){
			temp = ImgStart.settings2;
		}else if((x>=130)&&(x<=130+85)&&(y>=153)&&(y<=153+25)){
			temp = ImgStart.settings4;
		}else if((x>=132)&&(x<=132+85)&&(y>=290)&&(y<=290+25)){
			temp = ImgStart.settings3;
		}else if((x>=587)&&(x<=587+85)&&(y>=110)&&(y<=110+25)){
			temp = ImgStart.settings5;
		}else{
			temp = ImgStart.settings1;
		}
	}

	private void move_2() {
		// TODO Auto-generated method stub
		if((x>=954)&&(x<=954+33)&&(y>=15)&&(y<=15+33)){
			temp = ImgStart.sky2;
		}else if((x>=56)&&(x<=56+95)&&(y>=457)&&(y<=457+82)){
			temp = ImgStart.sky3;
		}else if((x>=60)&&(x<=60+250)&&(y>=178)&&(y<=178+117)){
			temp = ImgStart.sky01;
		}else if((x>=370)&&(x<=370+250)&&(y>=178)&&(y<=178+117)){
			temp = ImgStart.sky02;
		}else if((x>=675)&&(x<=675+250)&&(y>=178)&&(y<=178+117)){
			temp = ImgStart.sky03;
		}else if((x>=60)&&(x<=60+250)&&(y>=332)&&(y<=332+117)){
			temp = ImgStart.sky04;
		}else if((x>=370)&&(x<=370+250)&&(y>=332)&&(y<=332+117)){
			temp = ImgStart.sky05;
		}else if((x>=675)&&(x<=675+250)&&(y>=332)&&(y<=332+117)){
			temp = ImgStart.sky06;
		}else{
			temp = ImgStart.sky1;
		}
	}

	private void move_1() {
		// TODO Auto-generated method stub
		if((x>=954)&&(x<=954+33)&&(y>=15)&&(y<=15+33)){
			temp = ImgStart.background2;
			
		}else if((x>=370)&&(x<=370+250)&&(y>=300)&&(y<=300+47)){
			temp = ImgStart.background01;
		//	repaint();
		}else if((x>=370)&&(x<=370+250)&&(y>=362)&&(y<=362+47)){
			temp = ImgStart.background02;
		//	repaint();
		}else if((x>=370)&&(x<=370+250)&&(y>=424)&&(y<=424+47)){
			temp = ImgStart.background03;
		//	repaint();
		}else if((x>=370)&&(x<=370+250)&&(y>=486)&&(y<=486+47)){
			temp = ImgStart.background04;
		//	repaint();
		}else{
			temp = ImgStart.background1;
		//	repaint();
		}
	}

	private void move_0() {
		// TODO Auto-generated method stub
		
	}


		//内部类创建动画
		class createMovie1 implements Runnable {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				step = 0;
				//遍历逐帧图片
				while(true){
					if(step>=0&&step<=316){
						temp = JPanelMovie.getSMimage(step);
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					}else {
					try {
						Thread.sleep(0);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					state = 1;
					temp = buffer;
					// 跳出线程
					break;
					}
				step++;
				}
			}
		}
		
		//内部类创建动画
				class createMovie2 implements Runnable {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						step = 4;
						//遍历逐帧图片
						while(true){
							if(step>=0&&step<=456){
								temp = JPanelMovie.getAMimage(step);
							try {
								Thread.sleep(0);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							}else {
							try {
								Thread.sleep(0);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							Player.stopMusic();
							Player.playMusic("o");
							state = 1;
							temp = buffer;
							// 跳出线程
							break;
							}
						step++;
						}
					}
				}
}