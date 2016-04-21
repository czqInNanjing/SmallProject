package resource;


import game.GameCanvas;
import game.GameFrame;
import game.GameHandler;
import game.IGameHandler;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.util.Random;



/**
 * 绯荤粺鍚勭璁惧畾
 * 
 * @author zsq锛寃jc锛寉mc锛宑zq
 *
 */
public class LSystem {
	public static int edging = 1;//1~5瀵瑰簲window1~5
	public static boolean auto = false;
	/**
	 * 褰撳墠娓告垙妗嗘灦 闄嶄綆浜嗗畨鍏ㄦ�э紝浣嗘槸鏂逛究澶氫簡锛屼笉鐒跺湪AVGScript涓嬭鏈�灏忓寲杩樺緱浼犳寚閽堣繘鍘伙紝鐜板湪灏变笉瑕佷簡
	 */
	public static  GameFrame frame;
	
	/**
	 * 鎵�鏈夋父鎴忚繘搴︽殏鏃跺瓨鍌ㄧ殑鍦版柟
	 */
	final static public String tempFile = "data/temp.txt";
	/**
	 * 缂栫爜鏍煎紡
	 */
	final static public String encoding = "UTF-8";
	/**
	 * 鍏ㄥ眬闅忔満鏁�
	 */
	public static Random rand = new Random();
	/**
	 * 瀛椾綋澶у皬
	 */
	public static int FONT_TYPE = 20;
	/**
	 * 瀛椾綋闂磋窛
	 */
	public static int FONT_SIZE = 1;
	/**
	 * 鏂囨湰瀛椾綋绫诲瀷
	 */
	public static String FONT = "骞煎渾";

	public static GameHandler currentGameHandler;

	public static GameCanvas currentCanvas;
	/**
	 * 绐楀彛瀹藉害
	 */
	 public static int WIDTH = 990;
	/**
	 * 绐楀彛楂樺害
	 */
	 public static int HEIGHT = 618;
	 /**
	  *鏂囨湰璺濆乏杈圭晫璺濈 
	  */
	 public static int messageLeft = 15;
	 /**
	  * 鏂囨湰璺濅笂杈圭晫璺濈
	  */
	 public static int messageTop = 445;
	 /**
	  * 瀛椾綋榛樿棰滆壊
	  */
	 public static Color defaultColor = Color.WHITE;
	 /**
	  * 闊充箰鎾斁涓�
	  */
	 public static boolean musicOn = true;
	 /**
	  *瀵硅瘽妗嗛鏍� 
	  */
	 public static Image windowCanvas = BackgroundResource.windowsCanvas1;
	  /**
	  *浜虹墿绉诲姩閫熷害
	  */
	 public static int moveSpeed=10;
	 /**
	  * 
	  */
	 public static int messageSize=40;
	 /*
	  * 
	  */
	 public static int selectMessageGap=20;
	 /**
	  * 
	  */
	 public static int selectMessageSize=20;
	  /**
	  * 
	  */
	public static String currentCursor = BaleResource.creeseIconFile1;
	public static Image Cursor = BaleResource.creeseIcon1;
	public static void setSystemCursor(Cursor cursor) {
		if (currentCanvas != null) {
			currentCanvas.setCursor(cursor);
		}
	}

}
