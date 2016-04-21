//package resource;
//
//import game.IControl;
//
///**
// * 用于在游戏中运行一个新的类
// * @author czq
// *
// */
//public class RunClass {
//
//	private boolean isRun;
//
//	private String className;
//
//	public RunClass(boolean isRun, String name) {
//		this.isRun = isRun;
//		this.className = ClassResource.getClassName(name);
//	}
//	/**
//	 * 运行一个新的类
//	 * @return
//	 */
//	public IControl doInvoke() {
//		try {
//			return (IControl)Class.forName(className).newInstance();
//		} catch (Exception ex) {
//			throw new RuntimeException(ex);
//		}
//	}
//
//	public String getClassName() {
//		return className;
//	}
//
//
//	public boolean isRun() {
//		return isRun;
//	}
//
//	public void setRun(boolean isRun) {
//		this.isRun = isRun;
//	}
//
//}
