package game;



public class LColor {

	public int R = 0;

	public int G = 0;

	public int B = 0;

	private LColor() {
	}


	/**
	 * �����ɫ
	 * 
	 * @param c
	 * @return
	 */
	public static LColor getLColor(int pixel) {
		LColor color = new LColor();
		color.R = (pixel & 0x00ff0000) >> 16;
		color.G = (pixel & 0x0000ff00) >> 8;
		color.B = pixel & 0x000000ff;
		return color;
	}

	/**
	 * 锟斤拷color锟斤拷锟斤拷为锟斤拷锟斤�?
	 * 
	 * @param color
	 * @return
	 */
	public int getPixel(final LColor color) {
		return (color.R << 16) | (color.G << 8) | color.B;
	}

	public int getPixel() {
		return (R << 16) | (G << 8) | B;
	}

	/**
	 * 注锟斤拷r,g,b锟斤拷�??
	 * 
	 * @param r
	 * @param g
	 * @param b
	 */
	public LColor(final int r, final int g, final int b) {
		this.R = r;
		this.G = g;
		this.B = b;
	}

	public static LColor fromArgb(final int r, final int g, final int b) {
		return new LColor(r, g, b);
	}

}
