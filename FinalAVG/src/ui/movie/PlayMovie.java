package ui.movie;

import game.SimpleControl;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import media.Player;
import resource.LSystem;
import ui.StartPanel;
import command.Command;
import avg.AVGScript;
import avg.CG;
/**
 * 实现播放过场动画类
 * @author czq
 *
 */
public class PlayMovie extends SimpleControl {
	/**
	 * 文件路径
	 */
	private String filePath;
	/**
	 * 图片文件数量
	 */
	private int pictureNum;
	/**
	 * 当前图片为第几张
	 */
	private int nowPicture = 0;
	/**
	 * 
	 */
	private Image nextImage;
	
	
	
	 public PlayMovie(String filePath , int pictureNum) {
		this.filePath = filePath;
		this.pictureNum = pictureNum;
		Player.playMusic("us");
	}
	@Override
	public void next(){
		if(nowPicture== pictureNum - 40 ){
			Player.stopMusic();
		}
		if(nowPicture == pictureNum - 1 ){
			//removeCurrentControl();
			//将控制权限移交至原来的动画
			
			setCurrentControl(new StartPanel(1));
			
			return;
		}
		//
		nextImage = JPanelStartMovie.getimage(filePath, nowPicture + 4);
		nowPicture++;
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(nextImage, 0, 0, LSystem.WIDTH, LSystem.HEIGHT,  null);
	}
	public void mousePressed(MouseEvent e){
		nowPicture = pictureNum - 1;
	}
}
