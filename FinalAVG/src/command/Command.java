package command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import resource.LSystem;
import resource.ResourceLoader;
import collection.ArrayMap;

/**
 * 脚本解释器 继承了Conversion的可序列化类 从Conversion那儿继承了一些转化的方法 用于读取脚本并解析，转化为Java可执行语言
 * 
 * @author czq
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class Command extends Conversion implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 缓存脚本，用于内部脚本，例如内部的函数
	 */
	//TODO 暂时用不上
	final static private Map cacheScript = Collections
			.synchronizedMap(new HashMap(1500));
	/**
	 * 在缓存中的命令行名
	 */
	private String cacheCommandName;
	/**
	 * 函数列表
	 */
	final public ArrayMap functions = new ArrayMap(20);
	/**
	 * 变量列表
	 */
	final Map setEnvironmentList = Collections.synchronizedMap(new HashMap(20));
	/**
	 * 条件分支列表
	 */
	ArrayMap conditionEnvironmentList = new ArrayMap(30);
	/**
	 * 用于读入连续数据
	 */
	final static private StringBuffer reader = new StringBuffer(3000);
	/**
	 * 正在注释中
	 */
	private boolean flaging = false;
	/**
	 * 判断if中
	 */
	private boolean ifing = false;
	/**
	 * 内部脚本记录中（之后将被调用）
	 */
	private boolean functioning = false;
	/**
	 * 分支标记（if判断是否成功）
	 */
	private boolean elseFlag = false;

	/**
	 * 是否读取if内结论语句，即判断后的语句
	 * 如果if或else判断成功，则需读取内部代码，否则值为false，不读取任何代码
	 */
	private boolean backIfBool = false;

	/**
	 * 本行命令执行结果，由AVGScript继续处理
	 */
	private String executeCommand;
	/**
	 * 当前条件分支名，或者说当前条件分支所在行数
	 */
	private String nowPosFlagName;
	/**
	 * 是否输出脚本到AVGScript中
	 */
	private boolean addCommand;
	/**
	 * 当前脚本是否为内部脚本
	 */
	private boolean isInnerCommand;
	/**
	 * 是否正在读取     用于多项选择中
	 */
	private boolean isRead;
	/**
	 * 是否正在调用
	 */
	private boolean isCall;
//	/**
//	 * 是否有缓存的脚本
//	 */
//	private boolean isCache;
	/**
	 * 是否正在读取到if
	 */
	private boolean if_bool;
	/**
	 * 是否读取到else
	 */
	private boolean elseif_bool;
	/**
	 * 内层脚本
	 */
	private Command innerCommand;
	/**
	 * 	当前脚本行
	 */
	private String commandString;
	/**
	 * 暂时存储的列表
	 */
	private List temps;
	/**
	 * 打印标记
	 */
	private List printTags;
	/**
	 * 随机数标记
	 */
	private List randTags;
	/**
	 * 脚本数据列表大小
	 */
	private int scriptSize;

	/**
	 * 目前标记的位置//目前读取到的位置
	 */
	private int offsetPos;

	/**
	 * 脚本数据列表
	 */
	private List scriptList;
	/**
	 * 脚本名
	 */
	private String scriptName;
	/**
	 * 在if-else中是否有任意一次成功
	 */
	private boolean anyIfSucceed;
	/**
	 * 如果有一次成功，当再次遇到else时 ，锁死整个if语句不再输出
	 */
	private boolean neverPrint;
	

	/**
	 * 构造器，载入指定脚本文件
	 * 
	 * @param fileName
	 *            脚本文件名
	 */
	public Command(String fileName) {
		formatCommand(fileName);
	}
	/**
	 * 构造器，载入指定脚本文件和初始资源
	 * @param fileName
	 * @param resource
	 */
	public Command(String fileName, List resource){
		formatCommand("function", resource);
	}
	public void formatCommand(String fileName) {
		formatCommand(fileName,Command.includeFile(fileName));
		
	}
	/**
	 * 规格化脚本文件并执行 主要执行标记的赋值工作，各标记含义见文档注释
	 * 
	 * @param fileName
	 */
	public void formatCommand(String fileName, List resource) {
		// 删除所有已存在变量列表和条件分支的映射
		setEnvironmentList.clear();
		conditionEnvironmentList.clear();
		//默认选择-1
		setEnvironmentList.put(V_SELECT_KEY, "-1");
		scriptName = fileName;
		scriptList = resource;
		scriptSize = scriptList.size();
		offsetPos = 0;
		flaging = false;
		ifing = false;
//		isCache = true;
		elseFlag = false;
		backIfBool = false;
		anyIfSucceed = false;
		neverPrint = false;

	}

	/**
	 * 逐行执行脚本文件，返回可用的结果至AVGScript
	 * 
	 * @return 该行命令执行结果
	 */
	public synchronized String doExecute() {
	
		executeCommand = null;

		addCommand = true;

		isInnerCommand = (innerCommand != null);

		if_bool = false;

		elseif_bool = false;
		nowPosFlagName = String.valueOf(offsetPos);
		try{
			
			// 获得全行命令
			commandString = ((String) scriptList.get(offsetPos)).trim();
			
			//TODO 脚本缓存的实现暂时没有需求，可以实现读取提前预读多个脚本行，直接返回
			/*if (commandString.startsWith(RESET_CACHE_TAG)) {
				resetCache();
				return executeCommand;
			}*/
			/*if(isCache){
				// 获得缓存命令行名
				cacheCommandName = getNowCacheOffsetName();
				// 读取缓存的脚本
				Object cache = cacheScript.get(cacheCommandName);
				if (cache != null) {
					return (String) cache;
				}
			}*/
			//处理注释
			if(dealWithComments()){
				return executeCommand;
			}
			/*//跳转至第几行
			if(commandString.startsWith("goto")){
				temps = commandSplit(commandString);
				gotoIndex(Integer.parseInt((String)temps.get(1)));
				return executeCommand;
			}
			*/
			//这个用于if判断语句后是否读取if语句的内部方法，实现跳转分支的关键！
			//TODO  实现跳转分支两种方式 ：1.直接跳转，调用gotoindex方法，使用关键字goto（待实现，主要是问题多多，例如直接跳出if那endif读不到等等）
			//2.调用其他脚本，使用include关键字  
			/*
			 * 下面为if分支中进入内部脚本的相关代码
			 */
			
			int length = conditionEnvironmentList.size();
			if (length > 0) {

				// 取得上一次if判断中是否为真，将决定是否继续读取，还是直到遇见下一个if/else再读取
				Object ifResult = conditionEnvironmentList.get(length - 1);
				if (ifResult != null) {
					backIfBool = ((Boolean) ifResult).booleanValue();
				}
			}
			if (backIfBool) {
				// 加载内部脚本 TODO
				if (commandString.startsWith(INCLUDE_TAG)) {
					temps = commandSplit(commandString);
					String fileName = (String) temps.get(1);// include + 内部脚本名
					if (fileName != null) {
						innerCommand = new Command(fileName);
						isInnerCommand = true;
						//innerCommand.isInnerCommand = true ;
						innerCommand.setVariables(getVariables());
						//修正子脚本没有父类方法的小bug
						innerCommand.functions.putAll(functions);
						this.offsetPos++;
						return executeCommand;
						// 执行完时将自动返回
						
					}
				}
				// 执行内部脚本
				if (isInnerCommand && !isCall) {
			
					if (innerCommand.scriptHasNext()) {
						 String temp =innerCommand.doExecute();
						return temp;
					} else {// 如果内部脚本执行完毕
						setVariables(innerCommand.getVariables());
						innerCommand = null;
						return executeCommand;
					}
				}
			}
			
			// 设置随机值
			setUpRandom();
			// 将脚本文件中的变量换成相应值
			setUpSET();
			/*
			 * 下面语句为内部方法调用相关语句
			 */
			
			//内部脚本方法缓存结束
			if(commandString.startsWith(END_TAG)){
				functioning = false;
				return executeCommand;
			}
			
			//遇到begin  准备开始读取方法内容并存入function列表中，方法暂定不超过十行
			if(commandString.startsWith(BEGIN_TAG)){
				functioning = true;
				temps = commandSplit(commandString);
				functions.put(temps.get(1), new ArrayList(20));
				return executeCommand;
			}
			//开始读取方法内容,存储到对应的function中
			if (functioning) {
				((ArrayList) functions.get(functions.size() - 1))
						.add(commandString);
				return executeCommand;
			}
			//执行代码段的调用标记call，做一些前期工作
			if (commandString.startsWith(CALL_TAG) ) {
				//如果if判断中的语句且判断为假，则不执行
				if(ifing && !elseFlag){
					return executeCommand;
				}
				temps = commandSplit(commandString);
				//如果该方法有参数，将参数置入变量列表中
				
					if (temps.size() > 2)
						setVariable("item1", (String) temps.get(2));
					if (temps.size() > 3)
						setVariable("item2", (String) temps.get(3));
					if (temps.size() > 4)
						setVariable("item3", (String) temps.get(4));
					if (temps.size() > 5)
						setVariable("item4", (String) temps.get(5));
				
				
				
				//获取方法内容
				List functionContent = (ArrayList) functions.get((String) temps
						.get(1));
				//创建内部脚本，用方法名以及方法内容构建(如果在子脚本里，则是子脚本的子脚本)
				innerCommand = new Command((String) temps.get(1),
						functionContent);
				//将父脚本（主脚本）的变量表映射至子脚本
				innerCommand.setVariables(getVariables());
				
//				innerCommand.closeCache();
				isCall = true;
				isInnerCommand = true;
				//主脚本标记进入下一行，类似于PC执行完调用语句时自动加4
				offsetPos++;
				return executeCommand;
			}
			//开始执行调用函数
			if(isCall&& isInnerCommand){
				//将父脚本的变量映射至子脚本（因为子脚本改变的总是父脚本的变量！！）
			    //TODO
				if(innerCommand.scriptHasNext()){
					executeCommand = innerCommand.doExecute();
					
					return executeCommand;
				}else{//子脚本执行执行完毕
					isCall = false;
					isInnerCommand = false;
					innerCommand = null;
					return executeCommand;
				}
			}
			/*
			 * 下面关于if语句的执行相关代码
			 * 注意：if+空格+表达式（表达式中不允许使用空格！）
			 * else+空格+if+空格+表达式
			 */
			dealWithIf();
			//读取选项列表
			readItems();
			
			/*
			 * 下面为输出脚本判断。如果未被处理，则传输给AVGScript
			 */
			//如果在if判断中，则判断是否应该读取（根据分支是否实现）,为避免连续判断，backIfbool和elseFlag都必须为true
			if(addCommand && ifing && !neverPrint){
				if(backIfBool  && elseFlag ){//TODO
				executeCommand = commandString;
				}
			}
			if(addCommand && !ifing ){
				executeCommand = commandString;
			}
			//处理要显示的文本图片等等，在out时就不要处理
			if(executeCommand != null && !commandString.startsWith(OUT_TAG)){
				dealWithPrint();
			}
			/*
			if(isCache){
				cacheScript.put(cacheCommandName, executeCommand);
			}
			*/
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
			
		} finally {
			if(!isInnerCommand){
				offsetPos++;
			}
		}
		return executeCommand;
	}
	
	/**
	 * 这是一个判断的方法，根据传入的条件与变量判断是否结论为真， 返回布尔值并将nowPosFlagname与结果映射
	 * 一般而言，valueA用于存储变量（或者说游戏状态，例如某些指标），valueB用于存储标准 例如运气值达到200（或表达式）会触发何种事件等等。
	 * 注意：表达式中不能含有空格 ，即mount<40
	 * @param commandString     当前脚本行
	 * @param nowPosFlagName     当前的判断名称，例如你是否喜欢某某
	 * @param setEnvironmentList    变量列表
	 * @param conditionEnvironmentList    条件列表
	 * @return
	 */
	private boolean setUpIF(String commandString, String nowPosFlagName,
			Map setEnvironmentList, Map conditionEnvironmentList) {
		boolean result = false;
		// 默认情况下当前条件不成立
		conditionEnvironmentList.put(nowPosFlagName, new Boolean(false));
		// 分割分割！
		List commandSplit = commandSplit(commandString);
		//System.out.println(commandSplit);
		//0位置是if，1位置是变量，2位置是判断符，3位置是数值
		Object ValueA = (String) commandSplit.get(1);
		
		Object ValueB = (String) commandSplit.get(3);
		
		// 如果变量列表里有这些值，用列表里的，否则为常数，用传入值
		ValueA = setEnvironmentList.get(ValueA) == null ? ValueA
				: setEnvironmentList.get(ValueA);
		ValueB = setEnvironmentList.get(ValueB) == null ? ValueB
				: setEnvironmentList.get(ValueB);

		// 获取判断条件
		String condition = (String) commandSplit.get(2);
		// 若变量 B非纯数字，进行转化（变量A一般为变量，无需判定是否为纯数字）
		if (!isNumber(ValueB)) {
		
			ValueB = parse(ValueB);
		}

		// 无法判断时
		if (ValueA == null || ValueB == null) {
			conditionEnvironmentList.put(nowPosFlagName, new Boolean(false));
		}
		// 若条件为相等时 
		if (condition.equals("==")) {
			conditionEnvironmentList.put(nowPosFlagName, result = new Boolean(
					ValueA.toString().equals(ValueB.toString())));
		}
		// 若条件为不等时
		if (condition.equals("!=")) {
			conditionEnvironmentList.put(nowPosFlagName, new Boolean(
					result = !(ValueA.toString().equals(ValueB.toString()))));
		}
		// 若为大于时
		if (condition.equals(">")) {
			conditionEnvironmentList
					.put(nowPosFlagName,
							new Boolean(result = Integer.parseInt(ValueA
									.toString()) > Integer.parseInt(ValueB
									.toString())));
		}
		// 若为小于
		if (condition.equals("<")) {
			conditionEnvironmentList
					.put(nowPosFlagName,
							new Boolean(result = Integer.parseInt(ValueA
									.toString()) < Integer.parseInt(ValueB
									.toString())));
		}
		// 若为大于等于
		if (condition.equals(">=")) {
			conditionEnvironmentList
					.put(nowPosFlagName,
							new Boolean(result = Integer.parseInt(ValueA
									.toString()) >= Integer.parseInt(ValueB
									.toString())));
		}
		// 若为小于等于
		if (condition.equals("<=")) {
			conditionEnvironmentList
					.put(nowPosFlagName,
							new Boolean(result = Integer.parseInt(ValueA
									.toString()) <= Integer.parseInt(ValueB
									.toString())));
		}
		return result;
	}
	

	/**
	 * 随机数处理，在doexcute中被调用 脚本文件使用rand（）格式 *(允许一行内出现多个rand)
	 * 使用方式：1·若括号里是变量，如果未触发条件，此时变量值为null，则生成1到4的伪随机数
	 * ，如果达成条件，则变量获得值，则将rand里面的改为变量值 
	 * 2. 若括号里是数字，则表示随机数的生成范围（0 ~ 随机数的最大值）
	 */
	private void setUpRandom() {

		// 获得要生成的随机数
		 randTags = getNameTags(commandString, RAND_TAG + BRACKET_LEFT_TAG,
				BRACKET_RIGHT_TAG);
		if (randTags == null) {
			return;
		}
		for (Iterator iterator = randTags.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			String value = (String) setEnvironmentList.get(key);
			// 如果变量列表里有对应值
			if (value != null) {
				Conversion.replaceMatch(commandString, RAND_TAG
						+ BRACKET_LEFT_TAG + key + BRACKET_RIGHT_TAG, value);
				// 否则判断key是否为数字，是则为随机数生成的范围
			} else if (isNumber(key)) {
				Conversion.replaceMatch(commandString, RAND_TAG
						+ BRACKET_LEFT_TAG + key + BRACKET_RIGHT_TAG, Integer
						.toString(GLOBAL_RAND.nextInt(Integer.parseInt(key))));
				// 变量仍未被赋值时默认生成1~4随机数
			} else {
				Conversion.replaceMatch(commandString, RAND_TAG
						+ BRACKET_LEFT_TAG + key + BRACKET_RIGHT_TAG,
						Integer.toString(GLOBAL_RAND.nextInt(4) + 1));
			}

		}
	}
	/**
	 * 用于设置变量值，对应脚本文件中的以set开头的语句，一行只能有一个set
	 * 使用方法：
	 * 1.set  变量 = 变量
	 * 2.set  变量 = 常量    (此时常量应用   英文   双引号引起来)
	 * 3.set  变量 = 含变量的表达式
	 */
	private void setUpSET() {
		/*if (commandString.startsWith(SET_TAG)) {
			List temps = commandSplit(commandString);
			int len = temps.size();
			String result = null;
			if (len == 4) {
				result = temps.get(3).toString();
			} else if (len > 4) {
				StringBuffer sbr = new StringBuffer(len);
				for (int i = 3; i < temps.size(); i++) {
					sbr.append(temps.get(i));
				}
				result = sbr.toString();
			}

			if (result != null) {
				// 替换已有变量字符
				Set set = setEnvironmentList.entrySet();
				for (Iterator it = set.iterator(); it.hasNext();) {
					Entry entry = (Entry) it.next();
					result = replaceMatch(result, (String) entry.getKey(),
							entry.getValue().toString());
				}
				// 当为普通字符串时
				if (result.startsWith("\"") && result.endsWith("\"")) {
					setEnvironmentList.put(temps.get(1), result.substring(1,
							result.length() - 1));
				} else if (isChinese(result) || isEnglishAndNumeric(result)) {
					setEnvironmentList.put(temps.get(1), result);
				} else {
					// 当为数学表达式时
					setEnvironmentList.put(temps.get(1), compute.parse(result));

				}
			}
			addCommand = false;
		}*/

		if (commandString.startsWith(SET_TAG) && !functioning) {
			List setList = commandSplit(commandString);
			
			// 用于储存等式右边的值 之所以不赋值为null是因为null+ string 居然变成了 “null+string”
			String result = ""; 
			// 将表达式或变量赋值给result
			for (int i = 3; i < setList.size(); i++) {
				result = result + (String) setList.get(i);
			}
			// 若表达式为常量
			if ((result.startsWith("\"") && result.endsWith("\""))) {
				setEnvironmentList.put(setList.get(1), result.substring(1,result.length() -1));
				return;
			} 
			// 替换表达式中所有的变量
			Set set = setEnvironmentList.entrySet();
			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				Entry entry = (Entry) iterator.next();
				result = replaceMatch(result, (String) entry.getKey(),
						(String) entry.getValue());
			}
				
			// 若表达式仅有一个变量经替换后变成数字时
			
				if(isNumber(result)){
				setEnvironmentList.put(setList.get(1), result );
			}
			// 若表达式为多个变量进行四则运算时
			else {
				//TODO
				setEnvironmentList.put(setList.get(1),
						Integer.toString(parse(result)));
				//System.out.println("mount:" +getVariable("mount"));

			
			}
			addCommand = false;
		}
			//不允许输出脚本  TODO where to place
			
		}
	
	
	/**
	 * 处理脚本中的注释问题 ， return值为是否返回commandString
	 */
	private boolean dealWithComments() {
			
			//如果正在全局注释中，判断注释是否结束
			if(flaging){
				if(commandString.startsWith(FLAG_LS_E_TAG) || commandString.endsWith(FLAG_LS_E_TAG)){
					flaging = false;
				}
				return true;
			}
			//如果非注释中，判断本行是否为注释
			if(!flaging){
		
				//如果是注释且未结束
				if(commandString.startsWith(FLAG_LS_B_TAG) && !commandString.endsWith(FLAG_LS_E_TAG)){
					flaging = true;
					return true;
				}//如果是注释且在该行结束
				if(commandString.startsWith(FLAG_L_TAG) || (commandString.startsWith(FLAG_LS_B_TAG)&& commandString.endsWith(FLAG_LS_E_TAG))){
					return true;
				}
			}
			return false;
		
	}
	/**
	 * 处理打印字符串的问题，将变量换成相应值，去掉括号及print ，返回。
	 */
	private void dealWithPrint() {
		printTags = getNameTags(commandString, PRINT_TAG + BRACKET_LEFT_TAG,
				BRACKET_RIGHT_TAG);
		if (printTags != null) {
			for (Iterator iterator = printTags.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				String value = (String) setEnvironmentList.get(key);
				if (value != null) {
					//如果要print是一个变量
					commandString = replaceMatch(commandString, (PRINT_TAG
							+ BRACKET_LEFT_TAG + key + BRACKET_RIGHT_TAG)
							.intern(), value);
					
				} else {
					//如果要print的是一个常量
					commandString = replaceMatch(commandString, (PRINT_TAG
							+ BRACKET_LEFT_TAG + key + BRACKET_RIGHT_TAG)
							.intern(), key);
				}

			}
			executeCommand = commandString;
			//System.out.println(executeCommand);
		}
		
	}
	/**
	 * 读取选项列表,即出现提问时的选项
	 * 采用形式：
	 * in
	 * 选项A
	 * 选项B
	 * .....
	 * out
	 */
	private void readItems() {
		//结束选项列表读取
		if(commandString.startsWith(OUT_TAG)){
			isRead = false;
			addCommand = false;
			executeCommand = (SELECTS_TAG + " " + reader.toString()).intern();
			
		}
		//累计选择列表
		if(isRead){
			//若选项列表为参数，逐个判断并替换
			Set set = setEnvironmentList.entrySet();
			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				Entry entry = (Entry) iterator.next();
				commandString = replaceMatch(commandString, (String) entry.getKey(),
						(String) entry.getValue());
			}
			reader.append(commandString.trim());
			reader.append(FLAG);
			addCommand = false;
		}
		//开始进入选择列表
		if(commandString.startsWith(IN_TAG)){
			isRead = true;
			addCommand = false;
			reader.delete(0, reader.length());
		}
		
		
	}
	/**
	 * 处理脚本解释器中的if-else语句
	 */
	private void dealWithIf() {
		
		if_bool = commandString.startsWith(IF_TAG);
		elseif_bool = commandString.startsWith(ELSE_TAG);
		
		if (if_bool) {
			elseFlag = setUpIF(commandString, nowPosFlagName,
					setEnvironmentList, conditionEnvironmentList);
			addCommand = false;
			ifing = true;
		} else if (elseif_bool) {
			// 如果已经有成功的了，当再次遇到else时，不再输出
			if (anyIfSucceed) {
				neverPrint = true;
			}
			// 如果继续判断,首先确认上一次判断是否成功
			// 如果上一次没有成功则判断
			if (!backIfBool && !elseFlag) {
				elseFlag = setUpIF(commandString.substring(7), nowPosFlagName,
						setEnvironmentList, conditionEnvironmentList);
				// System.out.println(commandString + elseFlag + ifing);// TODO
				addCommand = false;

				// 否则，将elseFlag和nowPosFlagName对应均设为false
			} else {
				elseFlag = setUpIF(commandString.substring(7), nowPosFlagName,
						setEnvironmentList, conditionEnvironmentList);
				//System.out.println(commandString + elseFlag + ifing);
				addCommand = false;
			}

		}
		if(elseFlag){
			anyIfSucceed = true;
		}
		
		if(commandString.startsWith(IF_END_TAG)){
			conditionEnvironmentList.clear();
			ifing = false;
			addCommand = false;
			if_bool = false;
			elseif_bool = false;
			elseFlag = false;
			backIfBool = false;
			neverPrint = false;
			anyIfSucceed = false;
			
		}

	}
	
	/**
	 * 包含指定脚本内容
	 * 
	 * @param fileName  指定的脚本文件
	 * @return  将脚本文件读取并返回
	 */
	public static List includeFile(String fileName) {
		InputStream in = null;
		BufferedReader reader = null;
		List result = new ArrayList(1000);
		try {
			
			in = ResourceLoader.getResourceToInputStream(fileName);
			
			reader = new BufferedReader(new InputStreamReader(in,
					LSystem.encoding));
			
			String record = null;
			while ((record = reader.readLine()) != null) {
				//截去字符串开头和结尾的空白
				record = record.trim();
				if (record.length() > 0) {
					if (!(record.startsWith(FLAG_L_TAG)
							)) {
						result.add(record);
					}
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	/**
	 * 判断脚本是否允许继续解析
	 * 
	 * @return
	 */
	public boolean scriptHasNext() {
		return (offsetPos < scriptSize);
	}

	/**
	 * 跳转向指定索引位置
	 * TODO 暂时未实现
	 * @param offset
	 * @return 成功返回true 否则返回false
	 */
	public boolean gotoIndex(int offset) {
		if (offset < scriptSize && offset > 0 && offset != offsetPos) {
			offsetPos = offset;
			//System.out.println(true);
			return true;
		}
		return false;
	}

	/**
	 * 注入选择变量 
	 * 内外层都注入，保持一致性
	 * @param type 选择项
	 */
	public void select(int type) {
		if (innerCommand != null) {
			innerCommand.setVariable(V_SELECT_KEY, String.valueOf(type));
		}
		setVariable(V_SELECT_KEY, String.valueOf(type));
	}

	/**
	 * 返回选择变量
	 * 
	 * @return
	 */
	public String getSelect() {
		return getVariable(V_SELECT_KEY);
	}

	/**
	 * 设置变量
	 * 
	 * @param key
	 * @param value
	 */
	public void setVariable(String key, String value) {
		setEnvironmentList.put(key, value);

	}

	/**
	 * 获得变量值
	 * 
	 * @param key
	 * @return
	 */
	public String getVariable(String key) {
		return (String) setEnvironmentList.get(key);
	}

	/**
	 * 删除变量
	 * 
	 * @param key
	 */
	public void removeVariable(String key) {
		setEnvironmentList.remove(key);
	}

	/**
	 * 插入变量集合 其实就是将map映射中的所有对复制到变量列表中 一次性产生多个变量及其映射
	 * 
	 * @param vars
	 */
	public void setVariables(Map vars) {
		setEnvironmentList.putAll(vars);
	}

	/**
	 * 返回变量集合
	 * 
	 * @return
	 */
	public Map getVariables() {
		return setEnvironmentList;
	}
	/**
	 * 截掉第一次出现的指定标记，获得其有效信息，调用了getNameTags方法 例如对于print（"dfdf"）
	 * 获得dfdf，此时startString为print（ endString为 ）
	 * 
	 * @param messages
	 *            包含有用信息及其标记
	 * @param startString
	 *            从包含这个标记的最近的 开始字符串 开始截取 一般为为标识符+左括号，如“print（”
	 * @param endString
	 *            从包含这个标记的最近的 结束字符串 结束截取 一般为右括号
	 * @return 包含此有效信息及标记的一个完整的“表达式”
	 */
	public static String getNameTag(String messages, String startString,
			String endString) {
		List results = getNameTags(messages, startString, endString);
		return (results == null || results.size() == 0) ? null
				: (String) results.get(0);
	}

	/**
	 * 截掉标记获得有效信息并返回为list，调用另一重载的方法 例如对于print（"dfdf"）
	 * 获得dfdf，此时startString为print（ endString为 ）
	 * 
	 * @param messages
	 *            包含有用信息及其标记
	 * @param startString
	 *            从包含这个标记的最近的 开始字符串 开始截取 一般为为标识符+左括号，如“print（”
	 * @param endString
	 *            从包含这个标记的最近的 结束字符串 结束截取 一般为右括号
	 * @return 包含此有效信息及标记的一个完整的“表达式”
	 */

	public static List getNameTags(String messages, String startString,
			String endString) {
		// 调用另一个getNameTags方法
		return Command.getNameTags(messages.toCharArray(),
				startString.toCharArray(), endString.toCharArray());
	}

	/**
	 * 截取指定标记内容并返回为list，一次可以截取多行
	 * 
	 * 例如对于print（"dfdf"） 获得dfdf，此时startString为print（ endString为 ）
	 * 
	 * @param messages
	 *            包含有用信息及其标记
	 * @param startString
	 *            从包含这个标记的最近的 开始字符串 开始截取 一般为为标识符+左括号，如“print（”
	 * @param endString
	 *            从包含这个标记的最近的 结束字符串 结束截取 一般为右括号
	 * @return 包含此有效信息及标记的一个完整的“表达式”
	 */
	public static List getNameTags(char[] messages, char[] startString,
			char[] endString) {
		// 用于存储获得的信息
		List tagList = new ArrayList(10);
		// 分别为开始字符串及结束字符串的长度
		int sLength = startString.length;
		int eLength = endString.length;
		// 用于标记是否已读到所要的信息
		boolean lookUp = false;
		// 用于暂时储存
		StringBuffer usefulMes = new StringBuffer(100);
		// 表示开始和结束字符串读到第几个了
		int startIndex = 0;
		int endIndex = 0;
		for (int i = 0; i < messages.length; i++) {
			// 如果找到开始字符串的字母，开始计数
			if (startString[startIndex] == messages[i]) {
				startIndex++;
			} else {
				startIndex = 0;
			}
			// 若已确认找到开始字符串
			if (startIndex == sLength) {
				lookUp = true;
				startIndex = 0;
			}
			if (lookUp) {
				usefulMes.append(messages[i]);
			}
			// 是否开始结束字符串
			if (endString[endIndex] == messages[i]) {
				endIndex++;
			} else {
				endIndex = 0;
			}
			if (endIndex == eLength) {
				lookUp = false;
				endIndex = 0;
			}
			if (usefulMes.length() != 0 && lookUp == false) {
				// 因为所读入的包含startString和endString的各一个字符，删去
				usefulMes = usefulMes.deleteCharAt(usefulMes.length() - 1);
				usefulMes = usefulMes.deleteCharAt(0);
				tagList.add(usefulMes.toString());
				// 清空字符串缓存 （注意，删除时最后一个是不被包括的，（与上面的deleteCharAt不一样~），不能再二了！！！）
				usefulMes.delete(0, usefulMes.length());
			}
		}

		return tagList;
	}
	/**
	 * 静态方法 过滤指定脚本文件内容为list，如删去回车 ，空格，运算符间均增加@，最终返回List 
	 * 
	 * @param src 
	 * @return
	 */
	public static List commandSplit(final String src) {
		//关于string。intern方法是用来提高效率用的
		String[] cmds;
		String result = src;
		result = result.replace("\r", "");//  “\r”回车
		result = (FLAG + result).intern();
		result = result.replaceAll(" ", FLAG);
		result = result.replace("\t", FLAG);
		result = result.replace("<=", (FLAG + "<=" + FLAG).intern());
		result = result.replace(">=", (FLAG + ">=" + FLAG).intern());
		result = result.replace("==", (FLAG + "==" + FLAG).intern());
		result = result.replace("!=", (FLAG + "!=" + FLAG).intern());
		if (result.indexOf("<=") == -1) {
			result = result.replace("<", (FLAG + "<" + FLAG).intern());
		}
		if (result.indexOf(">=") == -1) {
			result = result.replace(">", (FLAG + ">" + FLAG).intern());
		}
		result = result.substring(1);
		cmds = result.split(FLAG);
		return Arrays.asList(cmds);
		
	}
//	/**
//	 * 打开脚本缓存
//	 */
//	public void openCache() {
//		isCache = true;
//	}
//	/**
//	 * 关闭脚本缓存
//	 */
//	public void closeCache() {
//		isCache = false;
//	}

	/**
	 * 获得当前脚本行缓存名
	 * 
	 * @return
	 */
	public String getNowCacheOffsetName() {
		return (scriptName + FLAG + offsetPos + FLAG + commandString)
				.toLowerCase();
	}

	/**
	 * 重启脚本缓存
	 * 
	 */
	public static void resetCache() {
		cacheScript.clear();
	}

	/**
	 * 是否正在读取选项列表中
	 * 
	 * @return
	 */
	public boolean isRead() {
		return isRead;
	}

	/**
	 * 设置读取情况
	 * 
	 * @param isRead
	 */
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	/**
	 * 返回当前的读入数据集合(选项集合)
	 * 
	 * @return
	 */
	public synchronized String[] getReads() {
		String result = reader.toString();
		result = result.replace(SELECTS_TAG, "");
		return split(result, FLAG);
	}

	/**
	 * 返回指定索引的读入数据
	 *  暂时认为是选择之后返回相应值
	 * @param index
	 * @return
	 */
	public synchronized String getRead(int index) {
		try {
			return getReads()[index];// 返回getreads返回数组中的第index个
		} catch (Exception e) {
			return null;
		}
	}
	public static void main(String[] args) {
		Command command = new Command("script/script1.txt");
		for (Iterator it = command.batchToList().iterator(); it.hasNext();) {
			System.out.println(it.next());
		}
}
	/**
	 * 此方法只用于测试 批处理执行脚本文件，并返回List
	 * 
	 * @return
	 */
	public List batchToList() {
		List resultList = new ArrayList(scriptSize);

		for (; scriptHasNext();) {
			String execute = this.doExecute();
			if (execute != null) {
				resultList.add(execute);
			}
		}
		return resultList;
	}
	
}