package avg;
/**
 * ´æ´¢Î»ÖÃµÄÃ¶¾Ù
 * @author czq
 * 
 */
public enum StoreText {
	save1{public String getFilePath(){return "data/save1.txt";}},
	save2{public String getFilePath(){return "data/save2.txt";}},
	save3{public String getFilePath(){return "data/save3.txt";}},
	save4{public String getFilePath(){return "data/save4.txt";}},
	save5{public String getFilePath(){return "data/save5.txt";}};
	
	public abstract String getFilePath();
	
}
