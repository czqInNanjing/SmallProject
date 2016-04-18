package edu.nju.view;

import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.nju.model.impl.UpdateMessage;
/**
 * 网络对战模式下，用于显示双方的旗子数
 * @author czq
 *
 */
public class FlagNumberPanel extends JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8155803350919104112L;
	
	private JLabel hostFlag;
	private JLabel clientFlag;
	
	public FlagNumberPanel() {
		hostFlag = new JLabel("Now Fight!");
		clientFlag = new JLabel("");
		this.setLayout(new FlowLayout());
		this.add(hostFlag);
		this.add(clientFlag);
	}

	@Override
	public void update(Observable o, Object arg) {
			UpdateMessage update = (UpdateMessage)arg;
			if(update.getKey().equalsIgnoreCase("hostFlag")){
				System.out.println("HostflagChange");
				hostFlag.setText("Host: "+(String)update.getValue());
			}
			if(update.getKey().equalsIgnoreCase("clientFlag")){
				System.out.println("ClientflagChange");
				clientFlag.setText("Client: "+(String)update.getValue());
				
			}
		
	}
	public void refresh(){
		this.hostFlag.setText("Now Fight!");
		this.clientFlag.setText("");
	}
	
}
