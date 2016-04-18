/**
 * 
 * @author czq 陈自强 学号141250017
 *
 */
public class ALU {
	NewMultipler test = new NewMultipler();
	// 1
	/**
	 *  该方法用于模拟两个操作数的四则运算，操作数可以为整数或浮点数，运算类型包括加减乘除。
	 *  当两个操作数均为整数时，将操作数表示为32位的整数，并调用相应的整数运算方法进行运算；
	 *  当至少一个操作数为浮点数时，采用32位的IEEE754表示操作数，并调用相应的浮点数运算方法进行运算。
	 * @param formula 字符串表示的计算公式，
	 * 					其形式为：操作数操作符操作数=，例如“5+(-7)=”。
	 * 				计算公式中有且仅有2个操作数，采用十进制表示，当操作数为负数时可能用括号括住；
	 *			 	有且仅有+-* /中的1个作为操作符；以=号结束。
	 * @return 计算结果的真值。如果是负数，最左边为“-”；如果是正数或0，不需要符号位。
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
		
		if(operand1.contains(".") || operand2.contains(".")){
			if(compute == 1){
				return floatTrueValue(floatAddition(ieee754(operand1,32), ieee754(operand2, 32), 23, 8, 8).substring(0, 32), 23, 8);
			}else if(compute == 2){
				return floatTrueValue(floatSubtraction(ieee754(operand1,32), ieee754(operand2, 32), 23, 8, 8).substring(0, 32), 23, 8);
			}else if(compute == 3){
				return floatTrueValue(floatMultiplication(ieee754(operand1,32), ieee754(operand2, 32), 23, 8).substring(0, 32), 23, 8);
			}else if(compute == 4){
				return floatTrueValue(floatDivision(ieee754(operand1,32), ieee754(operand2, 32), 23, 8).substring(0, 32), 23, 8);
				
			}
			
			
			
		} else{
			if(compute == 1){
				return integerTrueValue(integerAddition(integerRepresentation(operand1, 32), integerRepresentation(operand2, 32), '0', 32).substring(0, 32));
			}else if(compute == 2){
				return integerTrueValue(integerSubtraction(integerRepresentation(operand1, 32), integerRepresentation(operand2, 32), 32).substring(0, 32));
			}else if(compute == 3){
				return integerTrueValue(test.newMultipler(integerRepresentation(operand1, 32), integerRepresentation(operand2, 32), 32));
			}else if(compute == 4){
				String temp = integerDivision(integerRepresentation(operand1, 32), integerRepresentation(operand2, 32), 32);
				if(temp == "NaN" || temp =="0"){
					return temp;
				}
				return integerTrueValue(temp.substring(0, 32));
			}
		}
		
		
		return null;
	}

	// 2
	/**
	 * 返回十进制整数的补码表示
	 * 
	 * @param number
	 * @param length
	 *            表示的位数
	 * @return
	 */
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

	// 3
	/**
	 * 计算十进制浮点数的浮点数表示形式
	 * 
	 * @param number
	 * @param sLength
	 *            大于8
	 * @param eLength
	 *            大于8
	 * @return
	 */
	public String floatRepresentation(String number, int sLength, int eLength) {
		boolean isNegative = false;
		StringBuffer result = new StringBuffer(64);
		StringBuffer decimarParts = new StringBuffer(sLength);
		StringBuffer exponent = new StringBuffer(eLength);
		Long integerPart;
		double decimarPart;
		// 小数点移动位数
		int pointMove = 0;
		if (number.startsWith("-")) {
			isNegative = true;
			number = number.substring(1);
		}
		
		if(number.contains(".")){
		 integerPart = Long
				.parseLong(number.substring(0, number.indexOf(".")));
		 decimarPart = Double.parseDouble("0"
				+ number.substring(number.indexOf(".")));
		}else{
			integerPart = Long.parseLong(number);
			decimarPart = 0.0;
		}
		// 获得整数部分二进制表示
		if (integerPart != 0) {
			for (;;) {
				if (integerPart / 2 == 0) {
					result.append(Long.toString(integerPart % 2));
					break;
				} else {
					result.append(Long.toString(integerPart % 2));
					integerPart = integerPart / 2;
				}
			}
			result.reverse();
			result.delete(0, 1);
			// 获得小数点移动的位数
			pointMove = result.length();
		}

		// 获得小数部分的二进制表示，并存入decimarparts中
		for (;;) {
			if (decimarParts.length() == sLength*10) {
				break;
			} else {
				if ((decimarPart = decimarPart * 2) >= 1) {
					decimarPart = decimarPart - 1;
					decimarParts.append("1");
				} else {
					decimarParts.append("0");
				}
			}

		}
		
		// 如果整数部分为0，则为右移
		if (integerPart == 0) {
//			if(decimarParts.indexOf("1") == -1){
//				
//			}
			pointMove = -(decimarParts.indexOf("1") + 1);
			result.delete(0, result.length());
			result.append(decimarParts.substring(decimarParts.indexOf("1") + 1));
		}
		
		// 求指数部分
		int temp = pointMove +  (int)Math.pow(2, eLength - 1) - 1;
		for (;;) {
			//若为0
			if(decimarParts.indexOf("1") == -1 && integerPart == 0){
				for (int i = 0; i < eLength; i++) {
					exponent.append('0');
				}
				break;
			}
			//若为无穷大
			if (temp >= (int)Math.pow(2, eLength) - 1) {
				for (int i = 0; i < eLength; i++) {
					exponent.append('1');
				}
				break;
			}
			//若为非规格化数
			if(temp <=0){
				for (int i = 0; i < eLength; i++) {
					exponent.append('0');
				}
				break;
			}
			if (temp / 2 == 0) {
				exponent.append(Integer.toString(temp % 2));
				break;
			} else {
				exponent.append(Integer.toString(temp % 2));
				temp = temp / 2;
			}

		}
		exponent.reverse();
		while(exponent.length() < eLength){
			exponent.insert(0, '0');
		}
		
		//考虑反规格化数和舍入方式
		//右移一位存进去
		if (exponent.indexOf("1") == -1) {
			
			result.delete(0, result.length());
			decimarParts.delete(0, 126);
			
		}
//		System.out.println(result);
//		System.out.println(decimarParts);
		result.append(decimarParts.toString());
		result.delete(sLength + 1, result.length());
//		System.out.println(result);
		//舍入
		if (result.charAt(sLength) == '1') {
			int tempNum = result.lastIndexOf("0");
			if(tempNum != -1){
				result.setCharAt(tempNum, '1');
				for (int i = tempNum + 1; i < result.length(); i++) {
					result.setCharAt(i, '0');
				} 
			}else{
				//全为1，进位加到整数
				for (int i = 0; i < decimarParts.length(); i++) {
					decimarParts.setCharAt(i, '0');
				}
				int t = result.lastIndexOf("0");
				result.setCharAt(t, '1');
				for (int i = t + 1; i < result.length(); i++) {
					result.setCharAt(i, '0');
				}
				
			}
		}
		
		result.insert(0, exponent.toString());
		if(isNegative){
			result.insert(0, '1');
			
		}else{
			result.insert(0, '0');
		}
		
		return result.substring(0, 1+ sLength + eLength).toString();
	}

	// 4
	/**
	 * 将十进制浮点数用Ieee754 表示
	 * 
	 * @param number
	 * @param length
	 *            表示位数，为32 或 64 位
	 * @return
	 */
	public String ieee754(String number, int length) {
		if (length == 32) {
			return floatRepresentation(number, 23, 8);
		} else if (length == 64) {
			return floatRepresentation(number, 52, 11);
		}
		return null;
	}

	// 5
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
		//没办法，老要溢出，只好这样了
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

	// 6
	/**
	 * 将二进制浮点数转化为十进制
	 * 
	 * @param operand
	 * @param sLength
	 *            有效数位
	 * @param eLength
	 *            指数位
	 * @return
	 */
	public String floatTrueValue(String operand, int sLength, int eLength) {
		boolean isPostive = operand.startsWith("0");
		// 指数的二进制字符串
		String indexStr = operand.substring(1, 1 + eLength);
		// 有效数的二进制字符串
		String effStr = operand.substring(1 + eLength);
		double result = 0;
		// 因为是无符号的，所以前面加个0表示清白
		int index = Integer.parseInt(integerTrueValue("0" + indexStr));
		// 有效数序列
		char[] effChars = effStr.toCharArray();
		if (index == 0 && effStr.indexOf("1") == -1) {
			return "0";
		}
		if (index == 255) {
			if (effStr.indexOf("1") == -1) {
				if (isPostive)
					return "+Inf";
				else
					return "-Inf";
			} else {
				return "NaN";
			}
			
		}
		// 对于非规格化数
		if (index == 0 && effStr.indexOf("1") != -1) {
			for (int i = 0; i < sLength; i++) {
				result = result + (effChars[i] - '0')
						* Math.pow(2, index - 127 - i);
			}
			// 对于规格化数
		} else {
			result = Math.pow(2, index - 127);
			for (int i = 0; i < sLength; i++) {
				result = result + (effChars[i] - '0')
						* Math.pow(2, index - 128 - i);
			}
		}
		if(!isPostive){
			result = -result;
		}
		return Double.toString(result);
	}

	// 7
	/**
	 * 对二进制数按位取反
	 * 
	 * @param operand
	 * @return
	 */
	public String negation(String operand) {
		char[] temp = operand.toCharArray();
		StringBuffer sbf = new StringBuffer(operand.length());
		for (int i = 0; i < temp.length; i++) {
			if (temp[i] == '0') {
				sbf.append("1");
			} else {
				sbf.append("0");
			}
		}
		return sbf.toString();
	}

	// 8
	/**
	 * 模拟左移操作
	 * 
	 * @param operand
	 * @param n
	 * @return
	 */
	public String leftShift(String operand, int n) {
		StringBuffer sbf = new StringBuffer(operand.length());
		sbf.append(operand.substring(n));
		for (int i = 0; i < n; i++) {
			sbf.append("0");
		}
		return sbf.toString();
	}

	// 9
	/**
	 * 模拟算术右移操作
	 * 
	 * @param operand
	 * @param n
	 * @return
	 */
	public String rightAriShift(String operand, int n) {
		StringBuffer sbf = new StringBuffer(operand.length());
		sbf.append(operand.substring(0, operand.length() - n));
		for (int i = 0; i < n; i++) {
			if (operand.startsWith("1"))
				sbf.insert(0, '1');
			else
				sbf.insert(0, '0');
		}
		return sbf.toString();
	}

	// 10
	/**
	 * 逻辑右移操作
	 * 
	 * @param operand
	 * @param n
	 * @return
	 */
	public String rightLogShift(String operand, int n) {
		StringBuffer sbf = new StringBuffer(operand.length());
		sbf.append(operand.substring(0, operand.length() - n));
		for (int i = 0; i < n; i++) {
			sbf.insert(0, '0');
		}
		return sbf.toString();
	}

	// 11
	/**
	 * 全加法器
	 * 
	 * @param x
	 * @param y
	 * @param c
	 * @return 第一位为和，第二位为进位
	 */
	public String fullAdder(char x, char y, char c) {
		String result = "";
		result = Integer.toString(((x - '0') + (y - '0') + (c - '0')) % 2);

		if ((x - '0') + (y - '0') + (c - '0') - 2 >= 0) {
			result += "1";
		} else {
			result += "0";
		}

		return result;
	}

	// 12
	/**
	 * 模拟八位先行进位加法器
	 * 
	 * @param operand1
	 *            操作数1
	 * @param operand2
	 *            操作数2
	 * @param c
	 *            初始进位
	 * @return
	 */
	public String claAdder(String operand1, String operand2, char c) {

		char[] x = (new StringBuffer(operand1)).reverse().toString()
				.toCharArray();
		char[] y = (new StringBuffer(operand2)).reverse().toString()
				.toCharArray();
		char[] cs = new char[9];
		StringBuffer result = new StringBuffer(9);

		cs[0] = c;
		//使用递归先算出所有进位
		for (int i = 0; i < (cs.length - 1); i++) {
			cs[i + 1] = computeC(x, y, c, i);
		}
		for (int i = 0; i < 8; i++) {
			result.append(fullAdder(x[i], y[i], cs[i]).substring(0, 1));
		}
		result.reverse();
		result.append(cs[8]);

		return result.toString();
	}

	/**
	 * 模拟先行进位加法器计算进位，使用按位运算符时需小心。其运算顺序低于加减
	 * 
	 * @param x
	 * @param y
	 * @param c
	 * @param i
	 * @return
	 */
	private char computeC(char[] x, char[] y, char c, int i) {
		if (i == 0) {
			return (char) (((x[0] - '0') & (y[0] - '0') | ((x[0] - '0') | (y[0] - '0'))
					& (c - '0')) + '0');
		} else {
			return (char) ((((x[i] - '0') | (y[i] - '0'))
					& (computeC(x, y, c, i - 1) - '0') | ((x[i] - '0') & (y[i] - '0'))) + '0');
		}
	}

	// 13
	/**
	 * 模拟部分先行进位加法器
	 * 
	 * @param operand1
	 * @param operand2
	 * @param c
	 *            初始进位
	 * @param length
	 *            寄存器长度 ，大于等于8
	 * @return 计算结果+是否溢出
	 */
	public String integerAddition(String operand1, String operand2, char c,
			int length) {
		int save = length;
		StringBuffer result = new StringBuffer(length + 1);
		// 首先将寄存器，操作数长度进行规范
		while (length % 8 != 0) {
			length++;
		}
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
		// 每次取八位进行运算，将所得结果存储，将进位赋予下一次运算
		String temp;
		for (int i = 0; i < length / 8; i++) {
			temp = claAdder(
					operand1.substring(length - 8 * (i + 1), length - 8 * i),
					operand2.substring(length - 8 * (i + 1), length - 8 * i), c);
			result.insert(0, temp.substring(0, 8));
			c = temp.charAt(8);
		}
		
		//之前规范化现在要重新处理
		result.delete(0, length - save);
		
		// 判断是否溢出
		if (operand1.startsWith("0") && operand2.startsWith("0")
				&& (result.charAt(0) == '1') || operand1.startsWith("1")
				&& operand2.startsWith("1") && (result.charAt(0) == '0'))
			result.append('1');
		else
			result.append('0');

		return result.toString();
	}

	// 14
	/**
	 * 该方法用于模拟减法，要求调用integerAddition实现
	 * @param operand1 被减数 补码表示
	 * @param operand2 减数 补码表示
	 * @param length 存放操作数的寄存器长度。length不小于操作数长度，当某个操作数的长度小于length时，需在高位补符号位
	 * @return 长度为length+1 的字符串，从左向右。前length位为计算结果，最后一位为是否溢出
	 */
	public String integerSubtraction(String operand1, String operand2,
			int length) {
		return integerAddition(operand1, negation(operand2), '1', length);
	}

	// 15
	/**
	 * 该方法用于模拟Booth乘法，要求调用integerAddition方法和integerSubtraction方法来实现。
	 * @param operand1 被乘数，用补码表示
	 * @param operand2 乘数，用补码表示。
	 * @param length 存放操作数的寄存器的长度。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位。
	 * @return 长度为length*2，为计算结果，用补码表示。
	 */
	public String integerMultiplication(String operand1, String operand2,
			int length) {
		StringBuffer result = new StringBuffer();
		// 初始化
		for (int i = 0; i < length * 2 + 1; i++) {
			result.append('0');
		}
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
		// 模拟N次右移及加减运算
		char[] y = (operand2 + "0").toCharArray();
		for (int i = 0; i < length; i++) {
			int j = (y[length - i] - y[length - i - 1]);
			if (j == 1) {
				result.replace(
						0,
						length,
						integerAddition(operand1, result.substring(0, length),
								'0', length).substring(0, length));
			}
			if (j == -1) {
				result.replace(
						0,
						length,
						integerSubtraction(result.substring(0, length),
								operand1, length).substring(0, length));
			}

			// 进行算术右移
			if (result.charAt(0) == '0')
				result.insert(0, '0');
			else
				result.insert(0, '1');
		}

		return result.toString().substring(0, length * 2);
	}

	// 16
	/**
	 * 模拟整数除法（恢复余数法）
	 * 
	 * @param operand1
	 * @param operand2
	 * @param length
	 * @return length*2位 前部分为商 后部分为余数
	 */
	public String integerDivision(String operand1, String operand2, int length) {
		StringBuffer result = new StringBuffer(length * 2);
		if (operand2.indexOf("1") == -1 ) {
			return "NaN";
		}
		if (operand1.indexOf("1") == -1) {
			return "0";
		}
		// 初始化
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
		// 将补齐至2N位的被除数置入result中
		if (operand1.startsWith("1")) {
			for (int i = 0; i < length; i++) {
				result.append('1');
			}
		} else {
			for (int i = 0; i < length; i++) {
				result.append('0');
			}
		}
		result.append(operand1);

		String temp;
		String tempresult;
		for (int i = 0; i < length; i++) {
			// 模拟左移
			result.deleteCharAt(0);
			// 保留前length位以供恢复
			temp = result.toString().substring(0, length);
			// 根据余数与除数符号判定加还是减
			if (((operand2.charAt(0) - '0') ^ (result.charAt(0) - '0')) == 1) {
				// 搞这两个麻烦的东西是为了处理位数小于整数加法位数的问题，因加法会对不满8位的进行扩展，故取结果时从后面取起，还要抛弃溢出位
				tempresult = integerAddition(
						result.toString().substring(0, length), operand2, '0',
						length).substring(0, operand1.length());
				
				if (tempresult.charAt(0) == temp.charAt(0)) {
					result.append('1');
					result.replace(0, length, tempresult);
				} else {
					result.append('0');
					result.replace(0, length, temp);
				}
			} else {
				tempresult = integerSubtraction(
						result.toString().substring(0, length), operand2,
						length).substring(0, operand1.length());
				
				if (tempresult.charAt(0) == temp.charAt(0)) {
					result.append('1');
					result.replace(0, length, tempresult);
				} else {
					result.append('0');
					result.replace(0, length, temp);
				}
			}
		}
		// 如果除数与被除数符号不同，转换商的符号
		if (((operand1.charAt(0) - '0') ^ (operand2.charAt(0) - '0')) == 1) {
			result.replace(length, length * 2, computeCompletment(result
					.toString().substring(length, length * 2)));
		}
		// 调转商和余数位置
		temp = result.toString().substring(0, length);
		result.replace(0, length,
				result.toString().substring(length, length * 2));
		result.replace(length, length * 2, temp);

		return result.toString();
	}

	// 17
	/**
	 * 该方法用 于模拟浮点数的加法，要求调用integerAddition和integersubtraction实现
	 * 
	 * @param operand1
	 * @param operand2
	 * @param sLength
	 *            尾数长度，取值大于等于8
	 * @param eLength
	 *            指数长度，取值大于等于8
	 * @param gLength
	 *            保护位长度
	 * @return 长度为1+sLength+eLength+1的字符串。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）；
	 *         最后1位为是否溢出，其中溢出为1，不溢出为0。舍入采用就近舍入。
	 */
	public String floatAddition(String operand1, String operand2, int sLength,
			int eLength, int gLength) {
		if(!operand1.substring(1).contains("1")){
			return operand2;
		}else if (!operand2.substring(1).contains("1")){
			return operand1;
		}
		boolean isNegative = false;
		int isOverFlow = 0;
		int e1 = computeInteger(operand1.substring(1,eLength + 1));
		int e2 = computeInteger(operand2.substring(1,eLength + 1));
		//隐藏位
		String s1 = "1"+operand1.substring(eLength +1);
		String s2 = "1"+operand2.substring(eLength +1);
		//增量小阶码 右移尾数,并补齐位数至保护位数，阶码相等时保护位没有意义
		if(e1 != e2){
			if(e1 > e2){
				for (int i = 0; i < e1 - e2; i++) {
					s2 = "0" + s2 ;
				}
				//超过保护位，舍去后面的,否则补齐0
				if(s2.length() >= sLength + gLength + 1){
					s2 = s2.substring(0, sLength+gLength +1);
				}
				
				if(!s2.substring(0, sLength + 1).contains("1")){
					return operand1;
				}
				e2=e1;
			}else{
				for (int i = 0; i < e2 - e1; i++) {
					s1 ="0" +s1;
				}
				if(s1.length() >= sLength + gLength+1){
					s1 = s1.substring(0, sLength+gLength+1);
				}
				if(!s1.substring(0, sLength + 1).contains("1")){
					return operand2;
				}
				e1=e2;
			}
			
		}
		//补齐至保护位
		while(s2.length() < sLength + gLength + 1){
				s2 = s2 + "0";
		}
		while(s1.length() < sLength + gLength + 1){
			s1 = s1 + "0";
		}
		
		
//		System.out.println(gLength);
		//带符号尾数相加
		String result;
		//判断首位的符号位是否相同，同加
		if(operand1.charAt(0) == operand2.charAt(0)){
			//补一位确定是否有进位
			result = integerAddition("0" +s1, "0"+s2, '0', sLength + gLength + 1 + 1);
			System.out.println("s"+result);
			//如果溢出
			if(result.charAt(0) == '1'){
				result = "1" + result;
				e1++;
				if(e1 >(int)Math.pow(2, eLength - 1) - 1){
					isOverFlow = 1;
				}
			}
			//去掉加的首位
			result = result.substring(1);
			if(operand1.charAt(0)=='0'){
				isNegative = false;
			}else{
				isNegative = true;
			}
			//异号则减
		}else{
			result = integerAddition("0" + s1, "0" + computeCompletment(s2), '0', sLength + gLength + 1 + 1);
			//首位如果有进位
			if(result.charAt(0) == '1'){
				isNegative = (operand1.charAt(0) == '1')?true:false;
				result = result.substring(1);
			}else{
				isNegative = (operand1.charAt(0)=='0')? true : false;
				result = result.substring(1);
				result = computeCompletment(result);
				
			}
			
		}
		result = result.substring(0, sLength + gLength + 1);
		//尾数为0
		if(result.substring(0,sLength).indexOf("1") == -1){
			e1 = 0;
		}
		

		
		//规格化结果
		int sub = e1 - result.indexOf("1");
		//出现下溢
		if(sub <= 0){
			e1 = 0;
			if(sub == 0){
				result = "0" + result;
			}else{
				result = result.substring(Math.abs(sub) - 1);
			}
		}else{
			e1 = sub;
			result = result.substring(result.indexOf("1") + 1);
		}
		
		while(result.length() < sLength + gLength){
			result += "0";
		}
		
		//舍入
		if (gLength != 0) {
			if (result.charAt(sLength) == '1') {
				int temp = result.substring(0, sLength).lastIndexOf("0");
				StringBuffer sbf = new StringBuffer(result);
				sbf.setCharAt(temp, '1');
				for (int i = temp + 1; i < sbf.length(); i++) {
					sbf.setCharAt(i, '0');
				}
				result = sbf.toString();
			}
			result = result.substring(0, sLength);
//			System.out.println(result.length());
		}
		
		String exponent = integerRepresentation(Integer.toString(e1), eLength);
		if(isNegative){
			return "1" + exponent + result + Integer.toString(isOverFlow);
		}else{
			return "0" + exponent + result + Integer.toString(isOverFlow);
		}
		
	}
	
	// 18
	/**
	 * 模拟浮点数减法
	 * @param operand1
	 * @param operand2
	 * @param sLength
	 * @param eLength
	 * @param gLength
	 * @return
	 */
	public String floatSubtraction(String operand1, String operand2,
			int sLength, int eLength, int gLength) {
		
		operand2 = (operand2.charAt(0)=='0'?"1":"0") + operand2.substring(1);
		
		return floatAddition(operand1, operand2, sLength, eLength, gLength);
	}

	// 19
	/**
	 * 该方法用于模拟浮点数的乘法，要求调用integerAddition、integerSubtraction等方法来实现。
	 * @param operand1 被乘数，用二进制表示。
	 * @param operand2 乘数，用二进制表示。
	 * @param sLength 尾数的长度，取值大于等于8。
	 * @param eLength指数的长度，取值大于等于8。
	 * @return 1+sLength+eLength 为积，用二进制表示。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入采用就近舍入。
	 */
	public String floatMultiplication(String operand1, String operand2,
			int sLength, int eLength) {
		//任何一项为0 返回0
		if(!operand1.substring(1).contains("1")){
			return "0"+operand1.substring(1);
		}
		if (!operand2.substring(1).contains("1")){
			return "0"+operand2.substring(1);
		}
		int e1 = computeInteger(operand1.substring(1,eLength + 1));
		int e2 = computeInteger(operand2.substring(1,eLength + 1));
		int e = e1 + e2 - (int)Math.pow(2, eLength - 1) - 1;
		if(e>= (int)Math.pow(2, eLength)){
			return "exponent over flow";
		}else if(e < 0){
			return "exponent under flow";
		}
		
		//尾数相乘
		String s1 = "1"+operand1.substring(eLength + 1);
		String s2 = "1"+operand2.substring(1+ eLength);
		StringBuffer result = new StringBuffer((sLength+ 1) *2);
		for (int i = 0; i < sLength + 1; i++) {
			result.append("0");
		}
		for (int i = 0; i < sLength + 1; i++) {
			if(s2.charAt(s2.length() - i - 1) == '1'){
				//有时候加了之后会溢出，因此此时移位插入的是1
				String temp = integerAddition("0" +result.substring(0, sLength+1), "0"+s1, '0', sLength + 1);
				
				temp = temp.substring(0, sLength + 1);
				result.delete(0, sLength + 1);
				result.insert(0, temp);
			}else{
				result.insert(0, '0');
			}
			
			
		}
		
		
		//规格化
		if(result.indexOf("1") != -1){
			e = e + result.indexOf("1") + 1;
			result.delete(0, result.indexOf("1") +  1);
		}
		//舍入                
		if(result.charAt(sLength) == '1'){
			int temp = result.substring(0, sLength).lastIndexOf("0");
			if(temp != -1){
				result.setCharAt(temp, '1');
				for (int i = temp + 1; i < result.length(); i++) {
					result.setCharAt(i, '0');
				}
			}else{
				e++;
				for (int i = 0; i < result.length(); i++) {
					result.setCharAt(i, '0');
				}
			}
			
			
		}
		//位数不够则补齐
		while(result.length() < sLength){
			result.append('0');
		}
		
		String exponent = integerRepresentation(Integer.toString(e), eLength);
		
		if(operand1.charAt(0) == operand2.charAt(0)){
			return "0" + exponent + result.substring(0, sLength);
		}else{
			return "1" + exponent + result.substring(0, sLength);
		}
	}
//	public String floatMultiplication(String operand1, String operand2,
//			int sLength, int eLength) {
//		//任何一项为0 返回0
//				if(!operand1.substring(1).contains("1")){
//					return "0"+operand1.substring(1);
//				}
//				if (!operand2.substring(1).contains("1")){
//					return "0"+operand2.substring(1);
//				}
//				int e1 = computeInteger(operand1.substring(1,eLength + 1));
//				int e2 = computeInteger(operand2.substring(1,eLength + 1));
//				int e = e1 + e2 - (int)Math.pow(2, eLength - 1) - 1;
//				if(e>= (int)Math.pow(2, eLength)){
//					return "exponent over flow";
//				}else if(e < 0){
//					return "exponent under flow";
//				}
//				
//				//尾数相乘
////				StringBuffer result = new StringBuffer((sLength+ 1) *2);
////				String s1 = "1"+operand1.substring(eLength + 1);
////				String s2 = "1"+operand2.substring(1+ eLength);
////				result = new StringBuffer(test.newMultipler("0" + s1, "0" +s2, sLength + 1 + 1).substring(2) );
////				System.out.println(result.toString());
////				System.out.println(result.length());
//				
//				String s1 = "1"+operand1.substring(eLength + 1);
//				String s2 = "1"+operand2.substring(1+ eLength);
//				StringBuffer result = new StringBuffer((sLength+ 1) *2);
//				for (int i = 0; i < sLength + 1; i++) {
//					result.append("0");
//				}
//				for (int i = 0; i < sLength + 1; i++) {
//					if(s2.charAt(s2.length() - i - 1) == '1'){
//						//有时候加了之后会溢出，因此此时移位插入的是1
//						String temp = integerAddition("0" +result.substring(0, sLength+1), "0"+s1, '0', sLength + 1);
//						
//						temp = temp.substring(0, sLength + 1);
//						result.delete(0, sLength + 1);
//						result.insert(0, temp);
//					}else{
//						result.insert(0, '0');
//					}
//					
//					
//				}
//				
//				System.out.println(result);
//				System.out.println(result.length());
//				
//				//规格化
//				if(result.indexOf("1") != -1){
//					e = e + result.indexOf("1") + 1;
//					result.delete(0, result.indexOf("1") +  1);
//				}
//				//舍入                
//				if(result.charAt(sLength) == '1'){
//					int temp = result.substring(0, sLength).lastIndexOf("0");
//					if(temp != -1){
//						result.setCharAt(temp, '1');
//						for (int i = temp + 1; i < result.length(); i++) {
//							result.setCharAt(i, '0');
//						}
//					}else{
//						e++;
//						for (int i = 0; i < result.length(); i++) {
//							result.setCharAt(i, '0');
//						}
//					}
//					
//					
//				}
//				//位数不够则补齐
//				while(result.length() < sLength){
//					result.append('0');
//				}
//				
//				String exponent = integerRepresentation(Integer.toString(e), eLength);
//				
//				if(operand1.charAt(0) == operand2.charAt(0)){
//					return "0" + exponent + result.substring(0, sLength);
//				}else{
//					return "1" + exponent + result.substring(0, sLength);
//				}
//	}
	// 20
	/**
	 * 该方法用于模拟浮点数的恢复余数除法，要求调用integerAddition、integerSubtraction等方法来实现。
	 * @param operand1 被除数，用补码表示。
	 * @param operand2 除数，用补码表示。
	 * @param sLength 尾数的长度，取值大于等于8
	 * @param eLength 指数的长度，取值大于等于8
	 * @return 返回值：长度为1+sLength+eLength，为商，用二进制表示。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）。
	 * 			舍入采用就近舍入
	 */
	public String floatDivision(String operand1, String operand2, int sLength,
			int eLength) {
		String exponent;
		if(!operand1.substring(1).contains("1")){
			return "0" + operand1.substring(1);
		}
		if(!operand2.substring(1).contains("1")){
			if(operand1.charAt(0) == operand2.charAt(0)){
				return "01111111100000000000000000000000";
			}else{
				return "11111111100000000000000000000000";
			}
		}
		int e1 = computeInteger(operand1.substring(1,eLength + 1));
		int e2 = computeInteger(operand2.substring(1,eLength + 1));
		int e = e1 - e2 + (int)Math.pow(2, eLength - 1) - 1;
		if(e > (int)Math.pow(2, eLength)){
			return "exponent over flow";
		}else if(e < 0){
			return "exponent under flow";
		}
		/*
		 * 恢复余数法
		 */
//		System.out.println(e1 + " " +e2);
//		String s1 = "1" + operand1.substring(1+eLength);
//		String s2 = "1" + operand2.substring(1+ eLength);
//		System.out.println(s1);
//		System.out.println(s2);
//		String result = integerDivision( s1, s2, sLength + 1  ).substring(0,sLength+1 + 1);
//		System.out.println(result.length());
//		System.out.println(result);
		
		StringBuffer result = new StringBuffer();
		String s1 = "1" + operand1.substring(1+eLength);
		String s2 = "1" + operand2.substring(1+ eLength);
		s1 = "0" + s1;
		s2 = "0" + s2;
//		System.out.println(s1);
//		System.out.println(s2);
		//初始化result
		result.append(s1);
		for (int i = 0; i < sLength + 1; i++) {
			result.append('0');
		}
		String temp;
		String tempResult;
		for (int i = 0; i < sLength + 1 + 1; i++) {
			//保留前N位供恢复
			 temp = result.substring(0,sLength + 1 + 1);
			//加0以示清白,抛弃溢出位
			tempResult = integerSubtraction("0" + temp, "0"+s2, sLength + 1 + 1 + 1).substring(0, sLength + 1 + 1 +1);
//			System.out.println("tempRe:" + tempResult);
//			System.out.println("result:" + result);
			//
			if(tempResult.charAt(0) =='1'){
				result.append('0');
			}else{
				result.replace(0, sLength + 1 + 1, tempResult.substring(1));
				result.append('1');
			}
			//模拟左移
			result.deleteCharAt(0);
			
		}
			//余数舍去
			result.delete(0, sLength+1);
			//规格化
			String quotient = result.substring(0, sLength + 1);
			if(quotient.contains("1")){
				e = e - quotient.indexOf("1");
				//如果1不是最后一个
				if(quotient.indexOf("1") != quotient.length() - 1){
					quotient = quotient.substring(quotient.indexOf("1") + 1);
				}else{
					quotient = "0";
				}
				while(quotient.length() < sLength){
					quotient += "0";
				}
			}else{
				
			}
			//舍入
			
			exponent = integerRepresentation(Integer.toString(e), eLength);
			System.out.println(quotient);
			if(operand1.charAt(0) == operand2.charAt(0)){
				return "0" + exponent + quotient;
			}else{
				return "1" + exponent + quotient;
			}
			
			
	}
	
	
	/**
	 * 计算二进制无符号整数的真值
	 * @param substring
	 * @return
	 */
	public int computeInteger(String string) {
		string = new StringBuffer(string).reverse().toString();
		int result = 0;
		for (int i = 0; i < string.length(); i++) {
			if (string.charAt(i) == '0') {
				continue;
			} else
				result += (int) Math.pow(2, i);
		}
		return result;
	}
	/**
	 * 大作业2 改进方法 改进无符号整数的乘法以及浮点数的乘法
	 * @param operand1
	 * @param operand2
	 * @param length
	 * @return
	 */
	public String NewIntegerMultiplication(String operand1, String operand2,
			int length){
		
		
		
		
		
		
		
		return null;
	}
}
