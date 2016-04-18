package edu.nju.model.state;

/**
 * 显示在界面上的扫雷块状态
 * @author Wangy
 *
 */
public enum DisplayBlockState {	
	ZERO(0),ONE(1),TWO(2),THREE(3),FOUR(4),FIVE(5),SIX(6),SEVEN(7),EIGHT(8),
	UNCLICK,
	/**
	 * 插旗�?
	 */
	FLAG, 
	/**
	 * 成功之后显示�?
	 */
	MINE, 
	/**
	 * 踩到雷
	 */
	Bomb,
	/**
	 * 游戏失败时表示错误标识的雷
	 */
	ERROFLAG,HOST_FLAG,CLIENT_FLAG;
	private int index;
	
	private DisplayBlockState(){
		
	}
	
	private DisplayBlockState(int index){
		this.index = index;
	}
	
	
	public static DisplayBlockState getClickState(int index){		
		 for (DisplayBlockState s : DisplayBlockState.values()) {
             if (s.getIndex() == index) {
                 return s;
             }
         }
         return null;
	}
	
	public int getIndex() {
        return index;
    }
	
}
