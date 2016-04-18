/*
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
package edu.nju.view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;


import edu.nju.model.data.StatisticData;
/**
 * 记录窗口
 * @author czq
 *
 */
public class RecordDialog {

	/**
	 *  
	 */
	public RecordDialog(JFrame parent) {
		super();
		initialization(parent);
	}


	
	public void show(){
		dialog.setVisible(true);
	}

	private void initialization(JFrame parent) {
		

		dialog = new JDialog(parent, "record", true);

		okBtn = new JButton("ok");
		okBtn.setFont(new Font("Monospaced", Font.PLAIN, 12));
		okBtn.setBounds(30, 250, 70, 25);
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
			}
		});

		clearBtn = new JButton("clear");
		clearBtn.setFont(new Font("Monospaced", Font.PLAIN, 12));
		clearBtn.setBounds(122, 250, 70, 25);
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StatisticData.clearMessage();
				textPanel.repaint();
			}
		});

		line = new JSeparator();
		line.setBounds(20, 220, 270, 5);

		panel = new JPanel();
		panel.setLayout(null);

		textPanel = new DescribeTextPanel();
		panel.add(textPanel);

		panel.add(okBtn);
		panel.add(clearBtn);
		panel.add(line);

		dialog.setContentPane(panel);
		dialog.setBounds(parent.getLocation().x + 50,
				parent.getLocation().y + 50, 300, 320);

		clear = false;

	}
	/**
	 * 读取数据并初始化
	 */
	private void getStatistics() {
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File("save.dat")));
			easyMode = reader.readLine().split(",");
			hardMode = reader.readLine().split(",");
			hellMode = reader.readLine().split(",");
			customMode = reader.readLine().split(",");
			reader.close();
		} catch ( IOException e) {
			e.printStackTrace();
		}
		easyMode[1] = changeToPercent(easyMode[1]);
		hardMode[1] = changeToPercent(hardMode[1]);
		hellMode[1] = changeToPercent(hellMode[1]);
		customMode[1] = changeToPercent(customMode[1]);
	}

	private String changeToPercent(String string) {
		if(string.startsWith("1")){
			return "100%";
		}else{
			return string.substring(2) + "%";
		}
	}

	private class DescribeTextPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = -6851819917979728551L;

		DescribeTextPanel() {
			super();
			setBounds(0, 0, 270, 250);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setFont(new Font("Monospaced", Font.PLAIN, 12));
			getStatistics();
			for (int i = 0; i < 4; i++) {
				g.drawString(headTitle[i], 15 + i*(60), 35);
				g.drawString(easyMode[i],15 + i*(60), 78);
				g.drawString(hardMode[i], 15 + i*(60) ,121);
				g.drawString(hellMode[i], 15 + i*(60), 164);
				g.drawString(customMode[i], 15 + i*(60), 209);
			}
		}
	}

	
  	private JDialog dialog;

	private JPanel panel;

	private JButton okBtn;

	private JButton clearBtn;

	private JSeparator line;

	private String headTitle[] = {"Mode", "Rate" ,"Win" , "Sum"};

	private String easyMode[];
	private String hardMode[];
	private String hellMode[];
	private String customMode[];
	
	
	private JPanel textPanel;

	boolean clear;
}