package edu.nju.network.host;


import java.io.IOException;


public class ServerAdapter {
	protected static HostThread socket;
	protected static HostInHandler handler;
	
	static boolean init(HostInHandler h){
		try {
			socket = new HostThread();
			handler = h;
			
			socket.start();
			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch(Exception e1){
			e1.printStackTrace();
		}
		
		return false;
	}
//	public static void write(String str){
//		socket.write(str);
//	}
	
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
