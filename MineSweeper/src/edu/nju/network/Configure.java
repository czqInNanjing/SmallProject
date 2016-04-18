package edu.nju.network;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.network.client.ClientServiceImpl;
import edu.nju.view.MainFrame;

public class Configure {
	/**
	 * 服务器IP地址
	 */
	public static String SERVER_ADDRESS = "127.0.0.1";
	public final static int PORT = 6000;
	
	/**
	 * 是否正在联机�?
	 */
	public static boolean isNetWork = false;
	public static boolean isClient = false;
	public static OperationQueue operationQueue = null;
	public static MainFrame ui = null;
	public static ClientServiceImpl clientService = null;

}