

/**
 * 陈自强 学号 141250017
 * @author czq
 *
 */
public class NewMultipler {
	
	/**
	 * 测试代码
	 * @param args
	 */
	public static void main(String[] args) {
		NewMultipler test =   new NewMultipler();
		System.out.println(test.calculation("2*3="));
		System.out.println(test.calculation("2*(-3)="));
		System.out.println(test.calculation("(-277)*(-354)="));
		System.out.println(test.calculation("(-277)*1354="));
	}
	
	/**
	 * 该方法用于模拟实现阵列乘法器
	 * @param operand1
	 * @param operand2
	 * @param length 寄存器长度
	 * @return
	 */
	public String newMultipler(String operand1, String operand2, int length){
		StringBuffer result = new StringBuffer(length*2);
		//初始化，位数补齐
		if (operand1.length() < length) {
			if (operand1.startsWith("1")) {
				while (operand1.length() < length)
					operand1 = ("1" + operand1).intern();
			} else {
				while (operand1.length() < length)
					operand1 = ("0" + operand1).intern();
			}

		}
		if (operand2.length() < length) {
			if (operand2.startsWith("1")) {
				while (operand2.length() < length)
					operand2 = ("1" + operand2).intern();
			} else {
				while (operand2.length() < length)
					operand2 = ("0" + operand2).intern();
			}

		}
		char[] num1 = (new StringBuffer(operand1).reverse().toString()).toCharArray();
		char[] num2 = (new StringBuffer(operand2).reverse().toString()).toCharArray();
		//从右上到左下的“斜”进位
		char[] carries = new char[length - 1];
		for (int i = 0; i < carries.length; i++) {
			carries[i] = '0';
		}
		//从上到下的进位
		char[] tempC = new char[length -1];
		for (int i = 0; i < tempC.length; i++) {
			tempC[i] = and(num1[i + 1], num2[0]);
		}
		result.append(and(num1[0],num2[0]));
		
		
		
		for (int i = 0; i < length - 1; i++) {
			//计算tempC最高位
			
			tempC[length - 2] = and(num1[length - 1], num2[i]);
			//处理2类加法器
			if(i == length - 2){
				for (int j = 0; j < length - 1; j++) {
					String temp = fullAdder2(tempC[j], and(num1[j],num2[i+1]), carries[j]);
//					System.out.println(temp);
					if(j == 0){
						result.insert(0, temp.charAt(0));
						carries[0] = temp.charAt(1);
					}else{
						carries[j] = temp.charAt(1);
						tempC[j - 1] = temp.charAt(0);
					}
				}
				tempC[length - 2] = and(num1[length - 1], num2[i + 1]);
				break;
			}
			//处理带正权值的0类加法器
			for (int j = 0; j < length - i - 2; j++) {
				String temp = fullAdder0(tempC[j], carries[j], and(num1[j], num2[i+1]));
				if(j == 0){
					result.insert(0, temp.charAt(0));
					carries[0] = temp.charAt(1);
				}else{
					carries[j] = temp.charAt(1);
					tempC[j - 1] = temp.charAt(0);
				}
				
			}
			//处理带负权值的1类加法器
			for (int j = 0; j < i + 1; j++) {
				int start = length - i - 2 + j;

				String temp = fullAdder1(and(num1[start], num2[i+1]), carries[start], tempC[start]);
				
				carries[start] = temp.charAt(1);
				tempC[start - 1]= temp.charAt(0);
			}
		}
		
		//处理最后一部分：串行进位加法器
		char tempCarry = '0';
		for (int i = 0; i < length - 1; i++) {
			String temp = fullAdder2(tempCarry,carries[i] , tempC[i]);
			result.insert(0, temp.charAt(0));
			tempCarry = temp.charAt(1);
			
		}
		result.insert(0, tempCarry);
		return result.toString();
	}

	private char and(char c, char d) {
		if(c=='0' || d == '0'){
			return '0';
		}else{
			return '1';
		}
		
	}

	/**
	 * 0类全加器
	 * @param x
	 * @param y
	 * @param c
	 * @return
	 */
	public String fullAdder0(char x, char y , char c){
		String result = "";
		result = Integer.toString(((x - '0') + (y - '0') + (c - '0')) % 2);

		if ((x - '0') + (y - '0') + (c - '0') - 2 >= 0) {
			result += "1";
		} else {
			result += "0";
		}

		return result;
	}
	/**
	 * 1类全加器
	 * @param x
	 * @param y
	 * @param c
	 * @return
	 */
	public String fullAdder1(char x, char y , char c){
		String result = "";
		if(c == '1'){
			c = '0';
		}else{
			c = '1';
		}
		result = Integer.toString(((x - '0') + (y - '0') + (c - '0')) % 2);
		if(result.equalsIgnoreCase("1")){
			result = "0";
		}else{
			result = "1";
		}
		if ((x - '0') + (y - '0') + (c - '0') - 2 >= 0) {
			result += "1";
		} else {
			result += "0";
		}

		return result;
	}
	/**
	 * 2类全加器
	 * @param x
	 * @param y
	 * @param c
	 * @return
	 */
	public String fullAdder2(char x,char y,char c){
		if(x == '1'){
			x = '0';
		}else{
			x = '1';
		}
		if(y == '1'){
			y = '0';
		}else{
			y = '1';
		}
		
		String result = fullAdder0(x, y, c);
		if(result.charAt(1)=='1'){
			return result.substring(0, 1) + "0";
		}else{
			return result.substring(0, 1) + "1";
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 下面代码是大作业1的部分代码，在这儿用来方便测试
	 */
	
	public String calculation(String formula) {
		String operand1;
		String operand2;
		int compute = 0;
		
		if(formula.contains("+")){
			compute = 1;
			operand1 = formula.substring(0, formula.indexOf("+"));
			operand2 = formula.substring(formula.indexOf("+") + 1);
		}else if(formula.contains("*")){
			compute = 3;
			operand1 = formula.substring(0, formula.indexOf("*"));
			operand2 = formula.substring(formula.indexOf("*") + 1);
		}else if(formula.contains("/")){
			compute=4;
			operand1 = formula.substring(0, formula.indexOf("/"));
			operand2 = formula.substring(formula.indexOf("/") + 1);
		}else{
			compute = 2;
			if(formula.startsWith("(")){
				operand1 = formula.substring(0, formula.indexOf(")") + 1);
				operand2 = formula.substring(formula.indexOf(")") + 2);
			}else{
				operand1 = formula.substring(0, formula.indexOf("-"));
				operand2 = formula.substring(formula.indexOf("-") + 1);
			}
		}
		operand2 = operand2.substring(0, operand2.length() - 1);
		if(operand1.contains("(")){
			operand1 = operand1.substring(1, operand1.indexOf(")"));
		}
		if(operand2.contains(")")){
			operand2 = operand2.substring(1, operand2.indexOf(")"));
		}
		
			
			
	
			if(compute == 1){
//				return integerTrueValue(integerAddition(integerRepresentation(operand1, 32), integerRepresentation(operand2, 32), '0', 32).substring(0, 32));
			}else if(compute == 2){
//				return integerTrueValue(integerSubtraction(integerRepresentation(operand1, 32), integerRepresentation(operand2, 32), 32).substring(0, 32));
			}else if(compute == 3){
				return integerTrueValue(newMultipler(integerRepresentation(operand1, 32), integerRepresentation(operand2, 32), 32));
			}else if(compute == 4){
////				String temp = integerDivision(integerRepresentation(operand1, 32), integerRepresentation(operand2, 32), 32);
//				if(temp == "NaN" || temp =="0"){
//					return temp;
//				}
//				return integerTrueValue(temp.substring(0, 32));
			}
		
		
		
		return null;
	}
	public String integerRepresentation(String number, int length) {
		boolean isNeagtive = false;
		if (number.startsWith("-")) {
			isNeagtive = true;
			number = number.substring(1);
		}
		long temp = Long.parseLong(number);
		StringBuffer temp2 = new StringBuffer(length);
		// 计算绝对值的二进制表示
		for (;;) {
			if (temp / 2 == 0) {
				temp2.append(Long.toString(temp % 2));
				break;
			} else {
				temp2.append(Long.toString(temp % 2));
				temp = temp / 2;
			}
		}
		// 补齐0至规定位数
		for (int i = temp2.length(); i < length; i++) {
			temp2.append("0");
		}
		// 逆序
		temp2.reverse();
		// 对负数处理
		if (isNeagtive) {
			String result = computeCompletment(temp2.toString());
			temp2.replace(0, length, result);
		}

		return temp2.toString();
	}
	/**
	 * 计算整数补码
	 * 
	 * @return
	 */
	private String computeCompletment(String integer) {
		StringBuffer result = new StringBuffer(integer);
		// 根据取反加一最后一个1及其之后的数字不会受到影响
		if (integer.indexOf("1") != -1) {
			int temp3 = result.lastIndexOf("1");
			for (int i = 0; i < temp3; i++) {
				if (result.charAt(i) == '1') {
					result.setCharAt(i, '0');
				} else {
					result.setCharAt(i, '1');
				}
			}

		}
		return result.toString();
	}
	/**
	 * 将二进制整数转化为十进制整数
	 * 
	 * @param operand
	 * @return
	 */
	public String integerTrueValue(String operand) {
		int length = operand.length();
		char[] split = operand.toCharArray();
		long result = 0;
		if(split[0] != '0'){
			if (length < 64) {
				result = -(long) ((split[0] - '0') * Math.pow(2, length - 1));
			} else {
				result = Long.MIN_VALUE;
			}
		}
		
		for (int i = 1; i < length; i++) {
			result = result
					+ (long) ((split[i] - '0') * Math.pow(2, length - i - 1));

		}
		
		return Long.toString(result);
	}
}
