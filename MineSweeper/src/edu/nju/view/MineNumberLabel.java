package edu.nju.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import edu.nju.model.impl.UpdateMessage;
/**
 * 雷标签，当标记雷的时候，数字可能会相应改变
 * @author czq
 *
 */
public class MineNumberLabel extends JLabel implements Observer {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MineNumberLabel(){
		
	}
	
	private int remainMinesNumber;
	@Override
	public void update(Observable o, Object arg) {
		UpdateMessage updateMessage = (UpdateMessage) arg;
		if(updateMessage.getKey().equals("mineNum")){
			
			int remainMines = (Integer) updateMessage.getValue();
			this.setRemainMinesNumber(remainMines);
			this.setText(remainMines+"");
		}

	}
	public int getRemainMinesNumber() {
		return remainMinesNumber;
	}
	public void setRemainMinesNumber(int remainMinesNumber) {
		this.remainMinesNumber = remainMinesNumber;
	}

}
