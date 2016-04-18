package edu.nju.network.client;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import edu.nju.controller.msgqueue.operation.OutOfNetOperation;
import edu.nju.network.Configure;

public class ClientThread extends Thread {
	private Socket server;
	private ObjectInputStream reader;
	private ObjectOutputStream out;

	public ClientThread(String addr) throws UnknownHostException, IOException{
		super();
		
		server = new Socket(addr,Configure.PORT);
		out = new ObjectOutputStream(server.getOutputStream());	
		
		reader = new ObjectInputStream(new BufferedInputStream(server.getInputStream()));
	}
	/**
	 * 客户端线程主任务：从服务器读取信�?
	 */
	@Override
	public void run(){
		while(!this.isInterrupted()){
			
			//read from socket;
			try {
				Object obj = reader.readObject();
				
				ClientAdapter.readData(obj);
				
				
			} catch(SocketException se){
				System.out.println("socket connection is closed!!!");
				this.close();
				break;
			}catch (IOException e1) {
				this.close();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				this.close();
			}
		}
	}
	
	public void close(){
		
		try {
			out.flush();
			reader.close();
			out.close();
			server.close();
			this.interrupt();
			new OutOfNetOperation().execute();;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public Object write(Object o) {
		try {
			out.writeObject(o);
			out.flush();
		} catch (IOException e) {
			if(Configure.isNetWork){
				new OutOfNetOperation().execute();
			}
		}
		
		return true;
	}

}
