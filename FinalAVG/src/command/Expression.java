package command;

import java.util.Random;

/**
 * 规定脚本中一些表达式并常量化
 * 
 * @author czq
 *
 */
public interface Expression {

	/**
	 * 全局随机数
	 */
	Random GLOBAL_RAND = new Random();
	/**
	 * 当前选择项
	 */
	String V_SELECT_KEY = "SELECT";

	/**
	 * 左括号
	 */
	String BRACKET_LEFT_TAG = "(";

	/**
	 * 右括号
	 */
	String BRACKET_RIGHT_TAG = ")";

	/**
	 * 内部代码段开始标记
	 */
	String BEGIN_TAG = "begin";

	/**
	 * 内部代码段结束标记
	 */
	String END_TAG = "FuncEnd";

	/**
	 * 代码段调用标记
	 */
	String CALL_TAG = "call";

	/**
	 * 缓存刷新标记
	 */
	String RESET_CACHE_TAG = "reset";

	/**
	 * 累计输入选项
	 */
	String IN_TAG = "in";

	/**
	 * 累计输出选项
	 */
	String OUT_TAG = "out";

	/**
	 * 多个选项标记
	 */
	String SELECTS_TAG = "selects";

	/**
	 * 选项标记
	 */
	String PRINT_TAG = "print";

	/**
	 * 随机数标记
	 */
	String RAND_TAG = "rand";

	/**
	 * 设置变量标记
	 */
	String SET_TAG = "set";

	/**
	 * 载入内部脚本标记
	 */
	String INCLUDE_TAG = "has";

	/**
	 * 判断标记
	 */
	String IF_TAG = "if";

	/**
	 * 判断结束标记
	 */
	String IF_END_TAG = "endif";

	/**
	 * 判断转折标记
	 */
	String ELSE_TAG = "else";

	/**
	 * 注释标记
	 */
	String FLAG_L_TAG = "//";
	/**
	 * 注释标记
	 */
	String FLAG_LS_B_TAG = "/*";
	/**
	 * 注释标记
	 */
	String FLAG_LS_E_TAG = "*/";

	/**
	 * 分割用标记符
	 */
	String FLAG = "@";

	/**
	 * 分割用标记符
	 */
	char FLAG_CHAR = FLAG.toCharArray()[0];

}
