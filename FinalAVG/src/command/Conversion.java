package command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 用来被脚本解释器继承的抽象类，提供一些静态方法供调用，包括：分解字符串并返回列表， 检查是否为数字字母混合，是否数字，是否汉字，数字运算。
 * 注意：数字运算时除法是整除，余数将被忽视
 * @author czq
 *
 */
@SuppressWarnings("rawtypes")
public class Conversion implements Expression {
	/**
	 * 操作符数组
	 */
	final static private String operaters[] = { "\\+", "\\-", "\\*", "\\/",
			"\\(", "\\)" };

	/**
	 * 检查是否为字母与数字混合
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isLetterMixNumber(String value) {
		if (value == null) {
			return true;
		}
		for (int i = 0; i < value.length(); i++) {
			char temp = value.charAt(i);
			if (!((temp < 'Z' && temp > 'A') || (temp > 'a' && temp < 'z') || (temp > '0' && temp < '9'))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 分解字符串 
	 * 
	 * @param string
	 * @param tag
	 * @return
	 */
	public static String[] split(String string, String tag) {
		StringTokenizer str = new StringTokenizer(string, tag);
		String[] result = new String[str.countTokens()];
		int index = 0;
		for (; str.hasMoreTokens();) {
			result[index++] = str.nextToken();
		}
		return result;
	}

	/**
	 * 分解字符串,并返回为list 调用另一方法实现
	 * 
	 * @param string
	 * @param tag
	 * @return
	 */

	public static List stringSplit(String string, String tag) {
		return Arrays.asList(split(string, tag));
	}

	/**
	 * 检查是否数字
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNumber(Object value) {
		String temp = (String) value;
		for (int i = 0; i < temp.length(); i++) {
			if (temp.charAt(i) > '9' || temp.charAt(i) < '0') {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查是否汉字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isChinese(Object value) {
		boolean result = false;
		try {
			char[] chars = ((String) value).toCharArray();
			for (int i = 0; i < chars.length; i++) {
				// 先转化为string，汉字有两个字节，
				byte[] bytes = ("" + chars[i]).getBytes();
				if (bytes.length == 2) {
					int[] ints = new int[2];
					ints[0] = bytes[0] & 0xff;
					ints[1] = bytes[1] & 0xff;
					if (ints[0] >= 0x81 && ints[0] <= 0xFE && ints[1] >= 0x40
							&& ints[1] <= 0xFE) {
						result = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 四则运算
	 * 
	 * @param flag
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Integer operate(String flag, String v1, String v2) {
		
		return operate(flag.toCharArray()[0], Integer.parseInt(v1),
				Integer.parseInt(v2));
	}

	/**
	 * 四则运算
	 * 
	 * @param flag
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Integer operate(char flag, int v1, int v2) {
		switch (flag) {
		case '+':
			return v1 + v2;
		case '-':
			return v1 - v2;
		case '*':
			return v1 * v2;
		case '/':
			return v1 / v2;
		}
		return 0;
	}

	/**
	 * 替换指定字符串 
	 * 
	 * @param line
	 * @param oldString
	 * @param newString
	 * @return
	 */
	protected static String replaceMatch(String line, String oldString,
			String newString) {
		//return line = line.replaceAll(oldString, newString);
		int i = 0;
		int j = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {//判断老数组是否存在
			char line2[] = line.toCharArray();
			char newString2[] = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buffer = new StringBuffer(line2.length);
			buffer.append(line2, 0, i).append(newString2);
			i += oLength;
			for (j = i; (i = line.indexOf(oldString, i)) > 0; j = i) {
				buffer.append(line2, j, i - j).append(newString2);
				i += oLength;
			}
			buffer.append(line2, j, line2.length - j);
			return buffer.toString();
		} else {
			return line;
		}
	}

	// 数学表达式运算类，暂时没打算用
	// protected Compute compute = new Compute();

	


	
	/**
	 * 验证是否为四则运算
	 * 
	 * @param exp
	 * @return
	 */
	@SuppressWarnings("unused")
	private static boolean exp(String exp) {
		return exp.indexOf("+") != -1 || exp.indexOf("-") != -1
				|| exp.indexOf("*") != -1 || exp.indexOf("/") != -1;
	}

	/**
	 * 解析表达式
	 * 
	 * @param exp
	 * @return
	 */
	public Integer parse(Object exp) {
		return parse(exp.toString());
	}

	/**
	 * 解析表达式
	 * 
	 * @param exp
	 * @return
	 */
	public Integer parse(String exp) {
		//System.out.println(exp);
		int startIndex = 0;
		int endIndex = 0;
		
		while (exp.indexOf("(") != -1) {
			char[] expression = exp.toCharArray();
			// 寻找最右边的左那个括号
			for (int i = 0; i < expression.length; i++) {
				if (expression[i] == '(') {
					startIndex = i;
				}
			}
			// 寻找匹配的左括号
			for (int i = startIndex; i < expression.length; i++) {
				if (expression[i] == ')') {
					endIndex = i;
					String temp = exp.substring(startIndex, endIndex + 1);
				//	System.out.println(temp);
					int result = match(temp);
					// 用返回值替换表达式
					exp = exp.replace(exp.substring(startIndex, endIndex + 1),
							Integer.toString(result));
					break;
				}
			}
		}
		// 最后还剩一个裸表达式，没有括号~
		return match(exp);
	}

	/**
	 * 自动匹配四则运算表达式，并返回计算结果
	 * 2015.4.19
	 * @param exp
	 * @return
	 */
	private Integer match(String exp) {
		//解决开头为负数的问题
		if (!isNumber(exp.substring(0, 1))) {
			exp = ("0" + exp).intern();}
		// 用于存储分割后的表达式
		ArrayList<String> expes = null;
		// 消除左右括号以及空格
		if (exp.indexOf("(") != -1) {

			exp = exp.replace("(", "").replace(")", "").replaceAll(" ", FLAG)
					.intern();
			System.out.println(exp);
		}
		// 给所有运算符加@标记
		for (int i = 0; i < operaters.length; i++) {
			exp = exp.replaceAll(operaters[i], FLAG + operaters[i] + FLAG);
		}
		// 根据标记分割为数值和运算符，并转移到ArrayList中
		String[] exps = Conversion.split(exp, FLAG);
		expes = new ArrayList<String>(exps.length);
		for (int i = 0; i < exps.length; i++) {
			expes.add(exps[i]);
		}
		// if判断，判断是否有乘除法
		if (expes.contains("*") == true || expes.contains("/") == true) {
			// 若有，先进行乘除法运算
			while (expes.size() != -1) {
				int temp1 = expes.indexOf("*");
				int temp2 = expes.indexOf("/");
				// 若所有乘除法运算已完毕且不为一数值，跳至加减法运算
				if (temp1 == -1 && temp2 == -1) {
					break;
				} else {
					// 如果没有乘法或除法先出现，执行除法
					if ((temp1 == -1)
							|| ((temp1 != -1) && (temp2 != -1) && (temp2 < temp1))) {
						int temp = operate(expes.get(temp2),
								expes.get(temp2 - 1), expes.get(temp2 + 1));
						expes.remove(temp2);
						expes.remove(temp2);
						expes.set(temp2 - 1, Integer.toString(temp));
						continue;
					}
					// 如果没有除法或乘法先出现，执行乘法
					if ((temp2 == -1)
							|| ((temp1 != -1) && (temp2 != -1) && (temp1 < temp2))) {
					//	System.out.println(expes);
						int temp = operate(expes.get(temp1),
								expes.get(temp1 - 1), expes.get(temp1 + 1));
						expes.remove(temp1);
						expes.remove(temp1);
						expes.set(temp1 - 1, Integer.toString(temp));
						System.out.println(expes);
						continue;
					}

				}
			}
		} // 若乘除法已运算完毕或没有乘除法，进行加减法运算
		
		while (expes.size() != 1) {
			//System.out.println(expes);
			int temp = operate(expes.get(1), expes.get(0), expes.get(2));
			expes.remove(1);
			expes.remove(1);
			expes.set(0, Integer.toString(temp));
		}
		//System.out.println(expes);
		return new Integer(expes.get(0));
	}

}
