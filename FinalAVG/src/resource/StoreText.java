package resource;
/**
 * 存储位置的枚举
 * @author czq
 * 
 */
public enum StoreText {
	save{public String getFilePath(){return "data/saveCondition.txt";}},
	save1{public String getFilePath(){return "data/save1.txt";}},
	save2{public String getFilePath(){return "data/save2.txt";}},
	save3{public String getFilePath(){return "data/save3.txt";}},
	save4{public String getFilePath(){return "data/save4.txt";}},
	save5{public String getFilePath(){return "data/save5.txt";}},
	save6{public String getFilePath(){return "data/save6.txt";}};
	
	public abstract String getFilePath();
	
}
