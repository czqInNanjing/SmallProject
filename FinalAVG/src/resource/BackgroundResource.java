package resource;

import java.awt.Image;

import game.GraphicsUtils;



/**
 * 存放各种图片资源
 * @author czq
 *
 */
public abstract class BackgroundResource {

	

	
	final static public Image windowsCanvas1;
	final static public Image windowsCanvas2;
	final static public Image windowsCanvas3;
	final static public Image windowsCanvas4;
	final static public Image windowsCanvas5;

	final static public Image[] pauseImage;
	final static public Image AVGMusicIcon1;
	final static public Image AVGMusicIcon2;
	final static public  Image AVGMusicIcon3;
	final static public Image AVGSettingIcon1;
	final static public Image AVGSettingIcon2;
	
	final static public Image AVGCloseIcon1;
	final static public  Image AVGCloseIcon2;
	
	final static public Image AVGMinIcon1;
	final static public Image AVGMinIcon2;
	
	final public static Image AVGGoBackIcon1;
	final public static Image AVGGoBackIcon2;
	final public static Image AVGGoBackIcon3;
	
	final  public static Image[] storeBgs = {GraphicsUtils.loadImage("image/start/load/sky01.png"),
		GraphicsUtils.loadImage("image/start/load/sky02.png"),
		GraphicsUtils.loadImage("image/start/load/sky03.png"),
		GraphicsUtils.loadImage("image/start/load/sky04.png"),
		GraphicsUtils.loadImage("image/start/load/sky05.png"),
		GraphicsUtils.loadImage("image/start/load/sky06.png"),
		GraphicsUtils.loadImage("image/start/load/sky1.png"),
		GraphicsUtils.loadImage("image/start/load/sky2.png"),
		GraphicsUtils.loadImage("image/start/load/sky3.png"),
		GraphicsUtils.loadImage("image/start/load/word.png"),
		GraphicsUtils.loadImage("image/start/load/cover.png"),
		GraphicsUtils.loadImage("image/start/load/saveSucceed.png")};
	final  public static Image[] turtle = {GraphicsUtils.loadImage("image/start/load/save0.png"),
		GraphicsUtils.loadImage("image/start/load/save1.png"),
		GraphicsUtils.loadImage("image/start/load/save2.png"),
		GraphicsUtils.loadImage("image/start/load/save3.png"),
		GraphicsUtils.loadImage("image/start/load/save4.png")};
	final  public static Image[] storeImg =  {
		GraphicsUtils.loadImage("image/start/load/scene1.png"),
		GraphicsUtils.loadImage("image/start/load/scene2.png"),
		GraphicsUtils.loadImage("image/start/load/scene3.png"),
		GraphicsUtils.loadImage("image/start/load/scene4.png"),
		GraphicsUtils.loadImage("image/start/load/scene5.png"),
		GraphicsUtils.loadImage("image/start/load/scene6.png"),
	};
	static {
		windowsCanvas1 = GraphicsUtils.loadImage("image/system/window/window1.png");
		windowsCanvas2= GraphicsUtils.loadImage("image/system/window/window2.png");
		windowsCanvas3 = GraphicsUtils.loadImage("image/system/window/window3.png");
		windowsCanvas4 = GraphicsUtils.loadImage("image/system/window/window4.png");
		windowsCanvas5 = GraphicsUtils.loadImage("image/system/window/window5.png");
		pauseImage = GraphicsUtils.getSplitImages("image/system/icon/pause.png",37,55);
		
		
		//AVG游戏中的小图片
//		 AVGIcons = GraphicsUtils.loadImage("image/system/icon/AVGSystemicon.png");
		
		AVGMusicIcon1  = GraphicsUtils.loadImage("image/system/icon/music1.png");
		AVGMusicIcon2 = GraphicsUtils.loadImage("image/system/icon/music2.png");
		AVGMusicIcon3  = GraphicsUtils.loadImage("image/system/icon/music3.png");
		
		AVGSettingIcon1 = GraphicsUtils.loadImage("image/system/icon/setting1.png");
		AVGSettingIcon2 = GraphicsUtils.loadImage("image/system/icon/setting2.png");
//		AVGSettingIcon2 = GraphicsUtils.drawClipImage(AVGIcons, 454, 340, 30, 96, 30 + 70 , 96 + 70);
	
		AVGCloseIcon1 = GraphicsUtils.loadImage("image/system/icon/close1.png");
		AVGCloseIcon2 = GraphicsUtils.loadImage("image/system/icon/close2.png");
		
		AVGMinIcon1 = GraphicsUtils.loadImage("image/system/icon/minilize1.png");
		AVGMinIcon2 = GraphicsUtils.loadImage("image/system/icon/minilize2.png");
		
		AVGGoBackIcon1 = GraphicsUtils
				.loadImage("image/system/icon/goback1.png");
		AVGGoBackIcon2 = GraphicsUtils
				.loadImage("image/system/icon/goback2.png");
		AVGGoBackIcon3 = GraphicsUtils
				.loadImage("image/system/icon/goback3.png");
	}

	private BackgroundResource() {
	}

}
