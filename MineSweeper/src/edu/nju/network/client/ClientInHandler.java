package edu.nju.network.client;

public interface ClientInHandler {
	/**从服务器端
	 * 获得传来的TransformObject，将其中的UpdateMessage传给对应的Proxy.
	 * @param data
	 */
	public void inputHandle(Object data);
}
