package edu.nju.controller.service;

public interface ClientControllerService {
	/**
	 * 作为客户端建立网络连接
	 * @param ip
	 * @return
	 */
	public boolean setupClient(String ip);
}
