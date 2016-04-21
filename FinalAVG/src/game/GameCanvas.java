package game;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import resource.LSystem;

/**
 * ”Œœ∑ª≠≤º
 *
 */
public class GameCanvas extends Canvas  {

	private static final long serialVersionUID = 1982278682597393958L;

	final static private Toolkit systemToolKit = GraphicsUtils.toolKit;

	private boolean start;

	private IGameHandler game;

	private Graphics canvasGraphics = null;

	private BufferStrategy bufferStrategy;

	public GameCanvas(IGameHandler handler, int width, int height) {
		format(handler, width, height);
	}

	public GameCanvas() {
		format(LSystem.currentGameHandler, LSystem.WIDTH, LSystem.HEIGHT);
		

	}

	public void format(IGameHandler handler, int width, int height) {
		LSystem.WIDTH = width;
		LSystem.HEIGHT = height;
		this.game = handler;
		this.start = false;
		this.setBackground(Color.black);
		this.setPreferredSize(new Dimension(width, height));
		this.setIgnoreRepaint(true);
		this.addKeyListener(handler);
		this.addMouseListener(handler);
		this.addMouseMotionListener(handler);
		this.setCursor(GameCursor.getCursor(LSystem.currentCursor));
	}

	public void createBufferGraphics() {
		createBufferStrategy(3);
		bufferStrategy = getBufferStrategy();
	}

	public synchronized void paintScreen() {
		canvasGraphics = bufferStrategy.getDrawGraphics();
		if (isShowing()) {
			drawNextScreen(canvasGraphics);

			bufferStrategy.show();
			canvasGraphics.dispose();
			systemToolKit.sync();
			Thread.yield();
		}
	}

	public synchronized void drawNextScreen(Graphics g) {
		if (this.start) {
			IControl control = game.getControl();
			control.next();
			control.draw(g);
		}
	}

	public void startPaint() {
		this.start = true;
	}

	public void endPaint() {
		this.start = false;
	}



}
