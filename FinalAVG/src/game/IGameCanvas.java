package game;



import java.awt.Cursor;


public interface IGameCanvas {
	


	public abstract void setCursor(Cursor cursor);
	

	
	public abstract void createBufferGraphics();
	
	public abstract void paintScreen();

	public abstract void startPaint();

	public abstract void endPaint();

}
