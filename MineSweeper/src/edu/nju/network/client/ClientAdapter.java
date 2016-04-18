package edu.nju.network.client;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import edu.nju.network.Configure;

/**
 * 负责初始化数据以及和服务器端交互数据
 * @author czq
 *
 */
public class ClientAdapter {
	protected static ClientThread socket;
	protected static ClientInHandler handler;
	
	static boolean init(String addr,ClientInHandler h){
		try {
			socket = new ClientThread(addr);
			
			handler = h;
			
			socket.start();
			return true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.println("host not found!");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(Configure.ui.getMainFrame(), "No host now!");
		}
		
		return false;
	}
	//to write
	public static void write(Object o){
		socket.write(o);
	}
	
	//from read
	public static void readData(Object data){
		handler.inputHandle(data);
	}
	
	public static void close(){
		socket.close();
	}

}
