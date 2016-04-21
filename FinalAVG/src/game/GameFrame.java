package game;




import resource.ImgStart;
import resource.LSystem;

import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JFrame;

/**
 *游戏主体框架*
 */
public class GameFrame extends JFrame implements Runnable {

	

	/**
	 *游戏控制器
	 */
	private IGameHandler game;

	private String titleName;
	/**
	 * 主线程
	 */
	private Thread mainLoop;
	/**
	 * 画布，即panel
	 */
	private GameCanvas gameCanvas;



	public GameFrame(String titleName, int width, int height) {
		this(new GameHandler(), titleName, width, height);
//		dispose();
		setIconImage(ImgStart.icon);
		LSystem.frame = this;
		
	}
	/**
	 * 构造器
	 * @param game 游戏控制器
	 * @param titleName 
	 * @param width 
	 * @param height
	 */
	public GameFrame( GameHandler game, String titleName, int width, int height) {
		super(titleName);
		setUndecorated(true);
		this.game = game;
		this.titleName = titleName;
		this.addKeyListener(game);
		this.setPreferredSize(new Dimension(width, height));
		this.requestFocus();
//		this.addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent e) {
//				System.exit(0);
//			}
//		});
		this.initCanvas(width, height);
				this.pack();
		this.setResizable(false);
		//居中
		this.setLocationRelativeTo(null);
		this.setIgnoreRepaint(true);
		this.gameCanvas.createBufferGraphics();
		LSystem.currentGameHandler = game;
		LSystem.currentCanvas = gameCanvas;
	}

	public void setCursor(Cursor cursor) {
		LSystem.setSystemCursor(cursor);
	}

	
	public void showFrame() {
		this.setVisible(true);
	}

	public void hideFrame() {
		this.setVisible(false);
	}
	/**
	 * 主线程，不断在draw画面
	 */
	public void run() {
		try {
			for (;;) {
				gameCanvas.paintScreen();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * 主线程
	 */
	public void mainLoop() {
		this.mainLoop = new Thread(this);
		this.mainLoop.setPriority(Thread.MIN_PRIORITY);
		this.mainLoop.start();
	}
	/**
	 * 初始化Panel
	 * @param width
	 * @param height
	 */
	private void initCanvas(final int width, final int height) {
		GameCanvas gameCanvas = new GameCanvas(game, width, height);
		this.gameCanvas = gameCanvas;
		this.gameCanvas.startPaint();
		this.add(gameCanvas);
	}

	public IGameHandler getGame() {
		return this.game;
	}

	public Thread getMainLoop() {
		return mainLoop;
	}

	public String getTitleName() {
		return titleName;
	}

}
