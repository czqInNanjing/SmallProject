package edu.nju.network.host;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

import edu.nju.controller.msgqueue.operation.OutOfNetOperation;
import edu.nju.controller.msgqueue.operation.StartGameOperation;
import edu.nju.network.Configure;

public class HostThread extends Thread {
	private ServerSocket server;
	private Socket client;
	private ObjectInputStream reader;
	private ObjectOutputStream out;
	
	public HostThread() throws IOException{
		super();
		
		server = new ServerSocket(Configure.PORT);
		System.out.println("Waiting for Client!!!");
		client = server.accept();
		System.out.println("connect succeed!");
		reader = new ObjectInputStream(new BufferedInputStream(client.getInputStream()));
		
		out = new ObjectOutputStream(client.getOutputStream());
	}
	
	//read data
	@Override
	public void run(){
		while(!this.isInterrupted()){
			//read from socket
			if(!Configure.isNetWork){
				this.close();
			}
			try {
				Object obj = reader.readObject();
				if(obj != null){
					System.out.println("Got it!!!");
					ServerAdapter.readData(obj);
				}
				
			} catch(SocketException se){
				System.out.println("socket connection is closed!!!");
				this.close();
				return;
			}catch (IOException e1) {
				this.close();
				return;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void close(){
		
		try {
			reader.close();
			out.close();
			client.close();
			server.close();
			this.interrupt();
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			new OutOfNetOperation().execute();
		}
	}

	public boolean write(Object o) {
		try {
			out.writeObject(o);
			out.flush();
			
		} catch (IOException e) {
			if(Configure.isNetWork == false){
				new StartGameOperation();
				System.out.println(Configure.isNetWork);
			}
		}
		
		return true;
	}

}
