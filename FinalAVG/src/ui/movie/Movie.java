package ui.movie;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;

import resource.LSystem;
import avg.AVGScript;
import game.GraphicsUtils;
import game.SimpleControl;
/**
 * 鎾斁杩囩▼鍔ㄧ敾绫�
 * @author czq
 *
 */
public class Movie extends SimpleControl {
	private String[] messages ;
	private Image bg = GraphicsUtils.loadImage("image/system/icon/black.jpg");
	private int  count = 1;
	private int mesLen;
	/**
	 * 榛樿鏄剧ず甯ф暟
	 */
	private int max = 1000;
	/**
	 * 瀛椾綋榛樿棰滆壊
	 */
	private Color fontColor = Color.WHITE;
	/**
	 * 瀛椾綋榛樿澶у皬
	 */
	private int size = 100;
	private int middle;
	private int middle2;
	/**
	 * AVG鐨勬父鎴忓紑濮嬫椂闂�
	 */
	private long startTime;
	
	public Movie(String[] messages,long startTime) {
		
		this.messages = messages;
		this.mesLen = messages.length;
		middle = (LSystem.WIDTH - (messages[0].length()*size))/2;
		if(mesLen>1){
			middle2 = (LSystem.WIDTH - (messages[1].length()*size))/2;
		}
		 this.startTime = startTime;
	}
	public Movie(String[] messages,Color fontColor,long startTime) {
		this(messages, startTime);
		this.fontColor = fontColor;
	}
	public Movie(String[] messages , Color fontColor , int max,long startTime) {
		this(messages,fontColor, startTime);
		this.max = max;
	}
	public Movie(String[] messages, Color fontColor,  String bg,long startTime){
		this(messages , fontColor , startTime );
		this.bg = GraphicsUtils.loadImage(bg);
	}
	@Override
	public void draw(Graphics g) {
//		System.out.println(messages[0]);
		g.drawImage(bg, 0, 0, LSystem.WIDTH, LSystem.HEIGHT, 0, 0,
				bg.getWidth(null), bg.getHeight(null), null);
		g.setFont(GraphicsUtils.getFont(size));
		g.setColor(fontColor);
		//閫忔槑搴﹂�愭笎闄嶄綆
		GraphicsUtils.setAlpha(g, 1 - count / (double) max);
		//浣夸箣灞呬腑鏀剧疆
		if(mesLen == 1){
				
				g.drawString(messages[0], middle ,  330);
		}
		if(mesLen == 2){
				g.drawString(messages[0],middle, 250);
			if(count > max/2){
			    g.drawString(messages[1], middle2, 380);
			}
		}
		
	}
	@Override
	public void next(){
		count++;
		
		if(count==max ){
			setCurrentControl(new AVGScript(startTime));
		}
	}
	@Override
	public void mouseClicked(MouseEvent e){
		//鍐呴儴娴嬭瘯鏃朵緵璺宠繃锛屾渶鍚庡垹鍘� TODO
		count = max - 1;
	}
}
