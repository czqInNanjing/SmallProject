package ui.movie;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import resource.ImgStart;
import media.Player;

public class JPanelStartMovie extends JPanel {
	
//	//当前图片序号
//	int step = 1;
//	Image temp = null;
//	
//	public JPanelStartMovie(JFrameGame game) {
//		this.game = game;
//		// 设置为绝对布局
//		this.setLayout(null);
//	}

//	//内部类创建动画
//	public class createMovie implements Runnable {
//
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
//			step = 1;
//			//遍历逐帧图片
//			while(true){
//				if(step>=0&&step<=316){
//					temp = getimage(step);
//					repaint();
//				try {
//					Thread.sleep(0);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				}else {
//				try {
//					Thread.sleep(0);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				// 取消焦点
////				JPanelStartMovie.this.setFocusable(false);
//				game.setmainpanel();
//				game.removestartmovie();
//				// 跳出线程
//				break;
//				}
//			step++;
//			}
//		}
//
//	}
//
//	//启动线程
//	public void play(){
//		//线程
//		new Thread(new createMovie()).start();
//	}
	//读取图片
	public static Image getSMimage(int m){
		Image img = null;
		try {
			if(m>=0&&m<=9){
				img = ImageIO.read(new File("image/startmovie/DD_0000" + m + ".png"));
			}else if(m>=10&&m<=99){
				img = ImageIO.read(new File("image/startmovie/DD_000" + m + ".png"));
			}else if(m>=100&&m<=999){
				img = ImageIO.read(new File("image/startmovie/DD_00" + m + ".png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	/**
	 * 用于在脚本中做过场动画
	 * @param filePath
	 * @param m
	 * @return
	 */
	public static Image getimage(String filePath,int m){
		Image img = null;
//		System.out.println(filePath);
		try {
			if(m>=0&&m<=9){
				img = ImageIO.read(new File(filePath +"_0000" + m + ".png"));
			}else if(m>=10&&m<=99){
				img = ImageIO.read(new File(filePath +"_000" + m + ".png"));
			}else if(m>=100&&m<=999){
				img = ImageIO.read(new File(filePath +"_00" + m + ".png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
//	public void paint(Graphics g) {
//		// 设置panel为透明
//		this.setOpaque(true);
//		super.paint(g);
//		g.drawImage(temp,0,0,990,618,null);
//	}
//			
//	@Override
//	public void keyPressed(KeyEvent e) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void keyReleased(KeyEvent e) {
//		// TODO Auto-generated method stub
////		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
////			step = 316;
////		}
//	}
//
//	@Override
//	public void keyTyped(KeyEvent e) {
//		// TODO Auto-generated method stub
//		
//	}
//	@Override
//	public void mouseClicked(MouseEvent arg0) {
//		// TODO Auto-generated method stub
//		step = 316;
//
//	}
//	@Override
//	public void mouseEntered(MouseEvent arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//	@Override
//	public void mouseExited(MouseEvent arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//	@Override
//	public void mousePressed(MouseEvent arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//	@Override
//	public void mouseReleased(MouseEvent arg0) {
//		// TODO Auto-generated method stub
//		
//	}

}
