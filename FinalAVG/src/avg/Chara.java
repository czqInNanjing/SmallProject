package avg;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import resource.LSystem;
import game.GraphicsUtils;


public class Chara {

	/**
	 * 人物CG
	 */
	private Image characterCG;
	/**
	 * 人物宽度
	 */
	private int width;
	/**
	 * 人物高度
	 */
	private int height;
	/**
	 * 人物距左端的坐标
	 */
	private int x;
	/**
	 * 人物距右端的坐标
	 */
	private int y;
	/**
	 * 人物是否要移动
	 */
	private boolean isMove;
	/**
	 * 用于得到人物横向移动的坐标
	 */
	private int moveX;
	/**
	 *  人物的移动方向
	 */
	private int direction;
	/**
	 *  人物移动速度
	 */
	private int moveSleep =LSystem.moveSpeed;
	/**
	 * 是否正在移动
	 */
	private boolean moving = false;
	
	/**
	 * 可控制人物移动速度的构造器
	 * @param image
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param moveSleep
	 */
	public Chara(Image image, final int x, final int y, int width, int height,int moveSleep){
		this(image, x, y, image.getWidth(null), image.getHeight(null));
		this.moveSleep=moveSleep;
	}


	/**
	 * 
	 * @param image
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Chara(Image image, final int x, final int y, int width, int height) {
		this.characterCG = image;
		this.isMove = true;
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.moveX = 0;
		this.direction = getDirection();
		//得到人物移动方向
		if (direction == 0) {
			this.moveX = -(width / 2);
		} else {
			this.moveX = LSystem.WIDTH;
		}
	}

	public Chara(Image image, final int x, final int y) {
		this(image, x, y, image.getWidth(null), image.getHeight(null));
	}

	public Chara(final String fileName, final int x, final int y) {
		this(normalization(fileName), x, y);
	}
	/**
	 * 调整人物图片的方法
	 * @param oldImage
	 * @return
	 */
	private static Image normalization(final String fileName){
		//oldImage=oldImage.getScaledInstance(800, 600, Image.SCALE_DEFAULT);
		//return oldImage;
		//System.out.println(fileName);
		Image image=GraphicsUtils.loadImage(fileName);	
	
		BufferedImage newImage = null;
		if(fileName.contains("罗辑"))	{
			System.out.println("haslj");
			newImage=((sun.awt.image.ToolkitImage)image).getBufferedImage();
			return newImage.getSubimage(250,0,450,590);	
		}
		else
			return image;
	}
//	public static void main(String[] args){
//		
//	}
	/**
	 * 得到方向
	 * @return
	 */
	private int getDirection() {
		int offsetX = LSystem.WIDTH / 2;
		if (x < offsetX) {
			return 0;
		} else {
			return 1;
		}
	}

	public void setMove(boolean move) {
		isMove = move;
	}


	

	public int getNext() {
		return moveX;
	}

	public int getMaxNext() {
		return x;
	}
	/**
	 * 得到下一个moveX
	 * @return  moving
	 */
	public synchronized boolean next() {
		moving = false;
		if (moveX != x) {
			for (int sleep = 0; sleep < moveSleep; sleep++) {
				if (direction == 0) {
					moving = (x > moveX);
					if (moving)
						moveX += 1;
				} else {
					moving = (x < moveX);
					if (moving)
						moveX -= 1;
				}			
			}
		}
		return moving;
	}

	public synchronized void draw(Graphics g) {
		g.drawImage(characterCG, moveX, y, null);
	}

	public Image getCharacterCG() {
		return characterCG;
	}

	public void setCharacterCG(Image characterCG) {
		this.characterCG = characterCG;
	}

	public int getX() {
		return x;
	}
	/**
	 * 设置当前X坐标
	 * @param x
	 */
	public void setX(int x) {
		if (isMove) {
			this.moveX=x-moveX;
		} else {
			this.moveX = x;
		}
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getMoveSleep() {
		return moveSleep;
	}

	public void setMoveSleep(int moveSleep) {
		this.moveSleep = moveSleep;
	}

	public int getMoveX() {
		return moveX;
	}


	public void drawStatic(Graphics g) {
		g.drawImage(characterCG, x, y, null);
	}

}
