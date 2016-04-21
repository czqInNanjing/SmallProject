package game;


import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public interface IGameHandler extends MouseListener, MouseMotionListener, KeyListener {

	
	public abstract IControl getControl();

	public abstract void setControl(final IControl control);
}
