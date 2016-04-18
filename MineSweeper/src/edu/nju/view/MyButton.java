/*
 *
 * TODO add a coordinate to a JLabel as a button, to mark the location of this button
 */
package edu.nju.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class MyButton extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9058531039012015675L;
	private Location location;
	private Image image;
//	private ImageIcon imageIcon;


	MyButton(Location aLocation) {
		super();
		this.location = aLocation;
	}
	MyButton(int x, int y) {
		super();
		location = new Location(x, y);
	}
	
	public Location getMyLocation() {
		return location;
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) {
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		}
	}
	
	public void draw(ImageIcon imageIcon){
		repaint();
	}

}