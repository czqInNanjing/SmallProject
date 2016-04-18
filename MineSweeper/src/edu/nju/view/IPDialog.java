package edu.nju.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;

import edu.nju.network.Configure;

public class IPDialog {
	private JDialog dialog;
	private String ip;
	private JTextField ipArea; 
	private JButton useDefaultIp;
	private JButton Ok;
	public IPDialog(JFrame parent) {
		dialog = new JDialog(parent,"IP",true); 
		intializeComponents();
		dialog.setBounds(0, 0, 100, 200);
		dialog.setLocationRelativeTo(parent);
		dialog.setVisible(true);
	}
	private void intializeComponents() {
		
		ipArea = new JTextField(12); 
		useDefaultIp = new JButton("Use Default Ip");
		Ok = new JButton("OK");
		Ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
				ip = ipArea.getText();
				
			}
		});
		useDefaultIp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ipArea.setText(Configure.SERVER_ADDRESS);
				useDefaultIp.setEnabled(false);
				
			}
		});
		dialog.setLayout(new FlowLayout());
		ipArea.setBounds(10, 10, 120, 10);
		Ok.setBounds(10,30,40,10);
		useDefaultIp.setBounds(60, 30, 40, 30);
		dialog.add(ipArea);
		dialog.add(Ok);
		dialog.add(useDefaultIp);
//		this.dialog.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
		
	}
	public String getIp() {
		return ip;
	}
}
