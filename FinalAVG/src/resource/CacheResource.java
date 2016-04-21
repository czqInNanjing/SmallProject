package resource;


import collection.ArrayMap;

/**
 * 用于缓存各种东西，提供一个映射存放各种缓存
 * @author czq
 *
 */
public abstract class CacheResource {

	final static public ArrayMap ADV_CHARAS = new ArrayMap(100);

	public static void clearAll() {
		ADV_CHARAS.clear();
	}

}
