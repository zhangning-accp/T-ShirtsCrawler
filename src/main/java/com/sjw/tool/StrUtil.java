package com.sjw.tool;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 *
 *
 * 标题：项目研发
 *
 * 模块：公共部分
 *
 * 公司: yyang
 *
 * 作者：xb，2009-3-5
 *
 * 描述：
 *
 * 说明:
 *
 */
public class StrUtil {
	/**
	 * 数字格式对象
	 */
	private static NumberFormat formater = DecimalFormat.getInstance();

	// 默认除法运算精度
	private static final int DEF_DIV_SCALE = 10;

	/*
	 * 在数字前补零，并返回相应的字符 weishu 补零后的字符长度 num 要补零的数字 例 String a=addZero(5,321); 得到 00321
	 */
	public static String addZero(int weishu, int num) {

		/* int num=new Integer(num).intValue(); */
		int len = Integer.toString(num).length();
		if (len >= weishu) {
			return Integer.toString(num);
		}

		int i = 0;
		int j = weishu - len;
		String BH = "";
		while (i < j) {
			BH = "0" + BH;
			i = i + 1;
		}
		BH = BH + Integer.toString(num);
		return BH;

	}

	/**
	 * 对输入字符要求是字符或数字类型的有效性判断。 常用在口令上。
	 */
	public static boolean checkIDpass(String aim) throws IOException {
		String num = "0123456789";
		String lowcase = "abcdefghijklmnopqrstuvwxyz";
		String upcase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String single;
		int i;
		int length = aim.length();
		int errnum;
		int errlow;
		int errup;
		for (i = 0; i < length; i++) {
			single = aim.substring(i, i + 1);
			errnum = num.indexOf(single);
			errlow = lowcase.indexOf(single);
			errup = upcase.indexOf(single);
			if (errnum == -1 && errlow == -1 && errup == -1) {
				return false;
			}
		}
		return true;
	}

	// 得到数字相除的余数。
	// down 除数 up被除数
	public static int getMod(float up, float down) throws IOException {
		int result = 0;
		float dev = up / down;
		result = (int) dev;
		// if(dev>result){result+=1;}
		result = dev > result ? result + 1 : result;

		return result;
	}

	/**
	 * 字符串内容是否是一个时间
	 *
	 * @param str
	 * @return
	 */

	@SuppressWarnings("static-access")
	public static boolean isTime(String str) {
		try {
			java.sql.Time time = null;
			time = time.valueOf(str);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * 字符串内容是否是一个日期
	 *
	 * @param str
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static boolean isDate(String str) {
		try {
			if ((str == null) || (str.equals(""))) {
				return false;
			} else {
				str = str.trim();
			}

			java.sql.Date date = null;
			date = date.valueOf(str);
			if (!str.equals(date.toString())) {
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 字符串内容是否是一个整数
	 */

	public static boolean isInteger(String str) throws NumberFormatException {
		try {
			if ((str == null) || (str.equals(""))) {
				return false;
			} else {
				str = str.trim();
			}
			int thisInt = (new Integer(str)).intValue();
			if (thisInt < 0) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 字符串内容是否是一个数字
	 *
	 * @param str
	 * @return
	 * @throws NumberFormatException
	 */
	public static boolean isNumeric(String str) throws NumberFormatException {
		try {
			if ((str == null) || (str.equals(""))) {
				return false;

			} else {
				str = str.trim();
			}
			@SuppressWarnings("unused")
			float thisfloat = (new Float(str)).floatValue();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 根据关键字将字符串转为数组
	 *
	 * @param Str
	 * @param keyStr
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public static String[] toStringArray(String Str, String keyStr) {

		ArrayList sarr = new ArrayList();
		int startIndex = Str.indexOf(keyStr);
		while (startIndex >= 0) { // 存在出现位置
			String startString = Str.substring(0, startIndex); // 在这之前的字符
			if (!startString.equals("")) {
				sarr.add(startString);
			}
			Str = Str.substring(startIndex + keyStr.length(), Str.length()); // 在这之后的字符
			startIndex = Str.indexOf(keyStr);
		}
		if (Str.length() > 0) {
			sarr.add(Str);
		}
		int i = sarr.size();
		String[] s = new String[i];
		for (int j = 0; j < i; j++) {
			s[j] = (String) sarr.get(j);
		}
		return s;

	}

	/**
	 * 字符替换方法 str 需要处理的字符串 suo 需要替换掉的字符串 des需要替换为的字符串
	 */
	public static String replaceString(String Str, String Sou, String Des) {

		int startIndex;
		int endIndex;
		String startString;
		String endString;
		StringBuffer returnString = new StringBuffer();

		if (Str == null) {
			Str = "";
			returnString.append(Str);
		} else {

			while (true) {
				startIndex = Str.indexOf(Sou);
				if (startIndex >= 0) { // 存在出现位置
					endIndex = startIndex + Sou.length(); // 结束位置
					startString = Str.substring(0, startIndex); // 在这之前的字符
					if (startString == null) {
						startString = "";
					}
					endString = Str.substring(endIndex, Str.length()); // 在这之后的字符
					if (endString == null) {
						endString = "";
					}
					returnString.append(startString + Des); // 返回字符串
					Str = endString; // 原字符串改为新串
				} else {
					returnString.append(Str);
					break;
				}
			}
		}
		return returnString.toString();

	}

	public static String replaceStringS(String str, String oldSubStr, String newSubStr) {
		if (str == null) {
			return str;
		}
		StringBuffer sb = new StringBuffer();
		int i = 0;
		int j = 0;
		int len = oldSubStr.length();
		while ((i = str.indexOf(oldSubStr, j)) != -1) {
			sb.append(str.substring(j, i));
			sb.append(newSubStr);
			j = i + len;
		}
		sb.append(str.substring(j, str.length()));
		return sb.toString();
	}

	/**
	 * 本方法把一些需要屏蔽的字符串改为对应的中文字符。这样不会对页面显示造成错误
	 */
	public static String replaceHtmlString(String str) {

		if (str == null) {
			str = "";
		} else {
			str = str.trim();
			str = replaceString(str, "<", "〈");
			str = replaceString(str, ">", "〉");
			str = replaceString(str, "'", "‘");
			str = replaceString(str, "\"", "“");
		}
		return str;

	}

	/**
	 * 格式化数字字符
	 *
	 * @param psParm
	 * @param psFormat
	 * @param psLocals
	 * @return
	 */
	public static String formatNumber(String psParm, String psFormat, String psLocals) {
		psParm = String.valueOf(strTodouble(psParm));
		if (psFormat.equals("") & psLocals.equals("")) {
			return psParm;
		}
		NumberFormat form;
		if (psLocals.equals("PERCENT")) {
			form = NumberFormat.getPercentInstance(Locale.CHINA);
		} else {
			if (psLocals.equals("CURRENCY")) {
				form = NumberFormat.getCurrencyInstance(Locale.CHINA);
			} else {
				form = NumberFormat.getInstance(Locale.CHINA);
				((DecimalFormat) form).applyPattern(psFormat);
			}
		}
		return form.format(new Double(psParm));
	}

	/**
	 * 字符串转整形数字
	 *
	 * @param s
	 * @return
	 * @exception IllegalArgumentException
	 *                if this method is invoked
	 */
	public static int stringToInt(String s) {
		try {
			return Integer.valueOf(s).intValue();
		} catch (Exception e) {
			return -1;
		}

	}

	/**
	 * 字符串转double
	 *
	 * @param s
	 * @return
	 */
	public static double strTodouble(String s) {
		if (s == null || s.trim().length() <= 0) {
			return 0.0D;
		}
		s = s.trim();
		String s1 = "";
		String s2 = "";
		int ie1 = s.indexOf('e');
		if (ie1 < 0) {
			ie1 = s.indexOf('E');
		}
		if (ie1 < 0) {
			s1 = s;
		} else {
			s1 = s.substring(0, ie1).trim();
			s2 = s.substring(ie1 + 1).trim();
		}
		int iflag = 1;
		boolean isyc = false;
		StringBuffer sbs = new StringBuffer("");
		for (int i = 0; i < s1.length(); i++) {
			char ch = s1.charAt(i);
			isyc = false;
			if (i == 0) {
				if (ch == '-') {
					iflag = -1;
					continue;
				}
				if (ch == '+') {
					iflag = 1;
					continue;
				}
			}
			if (ch == ',' || ch == ' ') {
				continue;
			}
			if (ch == '.' || ch >= '0' && ch <= '9') {
				sbs.append(ch);
				continue;
			}
			isyc = true;
			break;
		}

		if (isyc || sbs.length() <= 0) {
			return 0.0D;
		}
		Double v = new Double(sbs.toString());
		double dv = (double) iflag * v.doubleValue();
		int ve = Integer.parseInt(s2);
		if (ve == 0) {
			return dv;
		}
		boolean ispos = true;
		if (ve > 0) {
			ispos = true;
		} else {
			ispos = false;
			ve *= -1;
		}
		for (int i = 0; i < ve; i++) {
			if (ispos) {
				dv *= 10;
			} else {
				dv /= 10;

			}
		}
		return dv;
	}

	/**
	 * double转字符串 , 专门将类如 2,568989E8 形式的double 型 转换成一般格式的数值，并每四位用逗号分开
	 *
	 * @param num
	 * @return String
	 */

	public static String doubleToString(double num) {
		String wx = String.valueOf(num);
		int e = wx.indexOf("E");

		if (e > 0) {
			int d = wx.indexOf(".");
			// System.out.println("e's position:"+e+"d's position:"+d);
			int xsws = e - d - 1; // 小数位数
			String a = wx.substring(e + 1);
			String b = wx.substring(0, d); // 小数点前面的字符
			// System.out.println("小数点前面的字符"+b);
			String c = wx.substring(d + 1, e); // 小数点后面的E字符前面的
			// 注：截取字符如（2，7）是截取从第二个字符开始不包括第二个字符到第7个字符之前，包括第七个字符
			// System.out.println("小数点后面的E字符前面的"+c);
			// System.out.println(a);
			String xin = b + c;
			int temp = Integer.parseInt(a) - xsws;
			;
			// System.out.println("后面补零的个数"+temp);
			if (temp > 0) {
				for (int i = 0; i < temp; i++) {
					xin += "0";
				}
			} else {
				String tempStr = "";
				for (int i = 0; i >= temp + xin.length() - 1; i--) {
					if (i == -1) {
						tempStr += ".";
					} else {
						tempStr += "0";
					}
				}
				xin = tempStr + xin;
			}
			wx = xin;
			// System.out.println("the number is:"+xin);
			if (xin.length() >= 10) {
				String tempstr = "";
				do {
					String temp1 = "," + xin.substring(xin.length() - 4, xin.length());
					tempstr = temp1 + tempstr;
					xin = xin.substring(0, xin.length() - 4);
					;
				} while (xin.length() > 4);
				xin += tempstr;
				wx = xin;
				// System.out.println("the new String is:"+xin);
			}

		} else {
			String xi = wx;
			if (xi.length() >= 10) {
				String tempstr = "";
				do {
					String temp1 = "," + xi.substring(xi.length() - 4, xi.length());
					tempstr = temp1 + tempstr;
					xi = xi.substring(0, xi.length() - 4);
					;
				} while (xi.length() > 4);
				xi += tempstr;
				wx = xi;
				// System.out.println("the new String is:"+xin);
			}

		}
		return wx;
	}

	/**
	 * 截取到小数点后b位小数
	 *
	 * @param d
	 * @param b
	 * @return
	 */
	public static double getRealDouble(double d, int b) {
		String tempDou = "" + d;
		int point = tempDou.indexOf(".");
		if (point > 0 && tempDou.length() >= point + (b + 1)) {
			tempDou = tempDou.substring(0, point + (b + 1));
		} else {
			tempDou = tempDou.substring(0, point + (b));
		}
		return Double.parseDouble(tempDou);
	}

	/**
	 * 求字符串的最大值
	 *
	 * @param strArray
	 *            String[]
	 * @return String
	 */
	public static String max(String[] strArray) {
		String maxValue = strArray[0];
		for (int i = 1; i < strArray.length; i++) {
			if (strArray[i].compareTo(maxValue) > 0) {
				maxValue = strArray[i];
			}
		}
		return maxValue;
	}

	/**
	 * 求字符串的最小值
	 *
	 * @param strArray
	 *            String[]
	 * @return String
	 */
	public static String min(String[] strArray) {
		String minValue = strArray[0];
		for (int i = 1; i < strArray.length; i++) {
			if (strArray[i].compareTo(minValue) < 0) {
				minValue = strArray[i];
			}
		}
		return minValue;
	}

	/**
	 * 取整数的最大值
	 *
	 * @param intArray
	 *            int[]
	 * @return int
	 */
	public static int max(int[] intArray) {
		int maxValue = intArray[0];
		for (int i = 1; i < intArray.length; i++) {
			if (intArray[i] > maxValue) {
				maxValue = intArray[i];
			}
		}
		return maxValue;
	}

	/**
	 * 求整数的最小值
	 *
	 * @param intArray
	 *            int[]
	 * @return int
	 */
	public static int min(int[] intArray) {
		int minValue = intArray[0];
		for (int i = 1; i < intArray.length; i++) {
			if (intArray[i] < minValue) {
				minValue = intArray[i];
			}
		}
		return minValue;
	}

	/**
	 * 取double的最大值
	 *
	 * @param intArray
	 *            int[]
	 * @return int
	 */
	public static double max(double[] intArray) {
		double maxValue = intArray[0];
		for (int i = 1; i < intArray.length; i++) {
			if (intArray[i] > maxValue) {
				maxValue = intArray[i];
			}
		}
		return maxValue;
	}

	/**
	 * 求double的最小值
	 *
	 * @param intArray
	 *            double[]
	 * @return double
	 */
	public static double min(double[] intArray) {
		double minValue = intArray[0];
		for (int i = 1; i < intArray.length; i++) {
			if (intArray[i] < minValue) {
				minValue = intArray[i];
			}
		}
		return minValue;
	}

	/**
	 * 将字符串数组转成整型数组
	 *
	 * @param strArray
	 *            String[]
	 * @return int[]
	 */
	public static int[] toIntArray(String[] strArray) {
		int[] r = new int[strArray.length];
		for (int i = 0; i < strArray.length; i++) {
			r[i] = Integer.parseInt(strArray[i]);
		}
		return r;
	}

	/**
	 * 将字符串数组转成double数组
	 *
	 * @param strArray
	 *            String[]
	 * @return double[]
	 */
	public static double[] toDoubleArray(String[] strArray) {
		double[] r = new double[strArray.length];
		for (int i = 0; i < strArray.length; i++) {
			r[i] = Double.parseDouble(strArray[i]);
		}
		return r;
	}

	/**
	 * 格式化实型数
	 *
	 * @param d
	 *            需要格式化的实型数
	 * @param digit
	 *            小数点后保留的位数
	 * @return
	 */
	public static double formatDouble(double d, int digit) {
		// 2004-07-15 李诚修改
		// 设置小数位数
		formater.setMaximumFractionDigits(digit);
		// 不用分隔符
		formater.setGroupingUsed(false);
		return Double.parseDouble(formater.format(d));
		// return Double.parseDouble(formatDoubleToString(d,digit));
	}

	/**
	 * 格式化实型数 2004-11-30 李诚修改,如果数值为零,则返回空字符串
	 *
	 * @param d
	 *            需要格式化的实型数
	 * @param digit
	 *            小数点后保留的位数
	 * @return
	 */
	public static String formatDoubleToString(double d, int digit) {
		// 设置小数位数
		formater.setMaximumFractionDigits(digit);
		// 不用分隔符
		formater.setGroupingUsed(false);
		if (d != 0) {
			return formater.format(d);
		} else {
			return "";
		}
	}

	/**
	 * 格式化数字字符串,返回格式化后的字符串
	 *
	 * @param number
	 *            需要格式化的数字
	 * @param digit
	 *            小数点后保留的位数
	 * @return
	 */
	public static String formatDouble(String number, int digit) {
		double d = Double.parseDouble(number);
		if (digit > 0) {
			// 设置小数位数
			formater.setMaximumFractionDigits(digit);
			// 不用分隔符
			formater.setGroupingUsed(false);
			return formater.format(d);
		} else {
			return String.valueOf((int) d);
		}
	}

	/**
	 * 格式化如备注,描述等长字符串,按指定的总长度进行载取, 并用指定的分隔长度进行换行(HTML换行),分隔换行的长度为0时,不换行
	 *
	 * @param sourceStr
	 *            需要格式化的字符串
	 * @param delimLen
	 *            分隔换行的长度
	 * @param length
	 *            需要保留的总长度
	 * @return 格式化后的字符串, sourceStr为NULL时,返回空字符串
	 */
	public static String formatLongString(String sourceStr, int delimLen, int length) {
		String formatStr = "";
		// 如果源字符串为空,则返回""
		if (sourceStr != null && sourceStr.length() != 0) {
			// 如果指定总长度大于源字符串长度,则按源字符串长度处理,否则进行载取
			if (length >= sourceStr.length()) {
				formatStr = sourceStr;
				formatStr = spitStringByBR(formatStr, delimLen);
			} else {
				formatStr = sourceStr.substring(0, length);
				formatStr = spitStringByBR(formatStr, delimLen);
				formatStr += "...";
			}
			return formatStr;
		}
		return "";
	}

	/**
	 * 用于HTML标记<br> , 分隔指定字符串
	 *
	 * @param formatStr
	 * @param delimLen
	 * @param length
	 * @return
	 */
	private static String spitStringByBR(String formatStr, int delimLen) {
		if (delimLen != 0 && delimLen < formatStr.length()) {
			StringBuffer formatStrBuf = new StringBuffer();
			int i = 1;
			for (i = 1; i * delimLen <= formatStr.length(); i++) {
				formatStrBuf.append(formatStr.substring((i - 1) * delimLen, i * delimLen) + "<br>");
			}
			formatStrBuf.append(formatStr.substring((i - 1) * delimLen, formatStr.length()));
			formatStr = formatStrBuf.toString();
		}
		return formatStr;
	}

	/**
	 * 格式化如备注,描述等长字符串 按默认的总长度(18个汉字或字符)进行载取 每10个字符换行一次
	 *
	 * @param sourceStr
	 *            需要格式化的字符串
	 * @return 格式化后的字符串, sourceStr为NULL时,返回空字符串
	 */
	public static String formatLongString(String sourceStr) {
		return formatLongString(sourceStr, 10, 18);
	}

	/**
	 * 提供精确的加法运算。
	 *
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */

	public static double add(double v1, double v2) {

		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.add(b2).doubleValue();

	}

	/**
	 * 提供精确的减法运算。
	 *
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 两个参数的差
	 */

	public static double sub(double v1, double v2) {

		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.subtract(b2).doubleValue();

	}

	/**
	 * 提供精确的乘法运算。
	 *
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */

	public static double mul(double v1, double v2) {

		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.multiply(b2).doubleValue();

	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
	 *
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @return 两个参数的商
	 */

	public static double div(double v1, double v2) {

		return div(v1, v2, DEF_DIV_SCALE);

	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 *
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */

	public static double div(double v1, double v2, int scale) {

		if (scale < 0) {

			throw new IllegalArgumentException(

					"The scale must be a positive integer or zero");

		}

		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 *
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */

	public static double round(double v, int scale) {

		if (scale < 0) {

			throw new IllegalArgumentException(

					"The scale must be a positive integer or zero");

		}
		BigDecimal b = new BigDecimal(Double.toString(v));

		BigDecimal one = new BigDecimal("1");

		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

	}

	/**
	 * 把科学计数法的double转换成代小数
	 *
	 * @param d
	 *            double 数值
	 * @param format
	 *            String 要返回的数值的格式，类似"#.00"2个0表示显示两位小数
	 * @return double
	 */
	public static String getDouble(double d, String format) {
		DecimalFormat formate = new DecimalFormat(format);
		String formatedNumber = formate.format(d);
		return formatedNumber;
	}

	/**
	 * 把字串转换为日期
	 *
	 * @param sdate
	 *            字串形式的日期
	 * @param format
	 *            字串格式
	 * @return 转换为日期类型
	 * @throws ParseException
	 */
	public static Date str2Date(String sdate, String format) throws ParseException {
		if (sdate == null || sdate != null && sdate.trim().length() == 0) {
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.parse(sdate);
	}

	/**
	 * 得到文件类型
	 *
	 * @param file
	 *            String ，文件路径或文件名
	 * @return String
	 */
	public static String getFileLX(String file) {
		String filexl = "";
		int wz = file.lastIndexOf('.');
		filexl = file.substring(wz + 1, file.length());
		return filexl;

	}

	/**
	 * 得到文件的类型。 实际上就是得到文件名中最后一个“.”后面的部分。
	 *
	 * @param fileName
	 *            文件名
	 * @return 文件名中的类型部分
	 * @since 0.5
	 */
	public static String getTypePart(String fileName) {
		int point = fileName.lastIndexOf('.');
		int length = fileName.length();
		if (point == -1 || point == length - 1) {
			return "";
		} else {
			return fileName.substring(point, length);
		}
	}

	/**
	 * 将文件名中的类型部分去掉。
	 *
	 * @param filename
	 *            文件名
	 * @return 去掉类型部分的结果
	 * @since 0.5
	 */
	public static String trimType(String filename) {
		int index = filename.lastIndexOf(".");
		if (index != -1) {
			return filename.substring(0, index);
		} else {
			return filename;
		}
	}

	/**
	 *
	 * @param filename
	 *            String带类型的文件名
	 * @param newString
	 *            String //不带类型
	 * @return String //替换成新的文件名
	 */
	public static String getnewString(String filename, String newString) {
		String strnew = "";
		String lx = getTypePart(filename);
		strnew = newString + lx;
		return strnew;

	}

	/**
	 * 得到系统操作时间
	 *
	 * @return
	 */
	public static String getSystemTime() {
		Calendar c = Calendar.getInstance();
		Date date = c.getTime();
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simple.format(date);
	}

	/**
	 * 判断Object是否为null
	 */
	public static boolean isNull(Object oValue) {
		return oValue == null ? true : false;
	}

	/**
	 * 判断是否为空串""
	 *
	 * @param sValue
	 * @return
	 */
	public static boolean isEmpty(String sValue) {
		if (sValue == null) {
			return true;
		}
		return sValue.trim().equals("") ? true : false;
	}

	/**
	 * 判断是否为空
	 *
	 * @param sValue
	 * @return
	 */
	public static boolean isEmptyStr(String sValue) {
		if (sValue == null) {
			return true;
		}
		if (sValue == "null") {
			return true;
		}
		if ("null".equals(sValue)) {
			return true;
		}
		return "".equals(sValue.trim()) ? true : false;
	}

	/**
	 * 将list转化为数组，此方法可以处理动态数组问题
	 *
	 * @param array
	 *            20081020 xyzhao
	 * @param array
	 * @return
	 */
	public static String[] getListZh(List<String> array) {
		String[] SZdate = new String[array.size()];
		for (int i = 0; i < array.size(); i++) {
			String date = array.get(i);
			SZdate[i] = date;
		}
		return SZdate;

	}

	/**
	 * xyzhao 列对象到实体对象的转化
	 *
	 * @param columnsObject
	 * @return
	 */
	public static String getenterZh(String columnsObject) {
		String enterObject = "";
		columnsObject = columnsObject.replaceAll("\\.", "");
		StringBuffer unite = new StringBuffer();
		for (int m = 0; m < columnsObject.length(); m++) {
			String ff = "";
			char aa = columnsObject.charAt(m); // 转换为ASCII
			int t = aa;
			if (m == 0) {
				unite.append(aa);
			} else {
				if (65 <= t && t <= 90) {
					unite.append("_");
					ff = String.valueOf(aa).toUpperCase();
					unite.append(ff);
				} else {
					unite.append(aa);
				}
			}
		}
		enterObject = unite.toString().toUpperCase();
		return enterObject;
	}

	/**
	 * 字段到对象转化
	 *
	 * @param columnName
	 * @return String
	 */
	public static String toFieldName(String columnName) {
		String[] word = columnName.split("_");
		StringBuffer fieldName = new StringBuffer(word[0].toLowerCase());
		for (int i = 1; i < word.length; i++) {
			fieldName.append(word[i].substring(0, 1).toUpperCase());
			if (word[i].length() > 1) {
				fieldName.append(word[i].substring(1).toLowerCase());
			}
		}
		return fieldName.toString();
	}

	/**
	 * 返回一个操作名的前半部分 比如：query，add，del，edit，save，update，view，app，exapp等等
	 *
	 * @param stringname
	 * @return
	 */
	public static String trimOptionname(String stringname) {
		int index = stringname.indexOf("_");
		if (index != -1) {
			return stringname.substring(0, index);
		} else {
			return "";
		}
	}

	/**
	 * 判断ArrayList 是否为空
	 *
	 * @param list
	 *            ArrayList
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	public static boolean isEmpty(ArrayList list) {
		boolean isEmpty = false;
		if (list == null) {
			isEmpty = true;
		} else if (list.size() == 0) {
			isEmpty = true;
		} else {
			isEmpty = false;
		}
		return isEmpty;
	}

	/**
	 * 判断ArrayList 是否所有Sting都为空
	 *
	 * @param list
	 *            ArrayList of String
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	public static boolean isEmptyString(ArrayList list) {
		boolean isEmpty = false;
		if (isEmpty(list)) {
			isEmpty = true;
		} else {
			isEmpty = true;
			String value = null;
			for (int i = 0; i < list.size(); i++) {
				value = (String) list.get(i);
				if (!isEmpty(value)) {
					isEmpty = false;
					break;
				}
			} // end for
		}
		return isEmpty;
	}

	/**
	 * 数据类型转化
	 *
	 * @param string
	 * @return
	 */
	public static Long convertToLong(String string) {
		if (string == null) {
			return new Long(0);
		} else {
			try {
				return new Long(string);
			} catch (Exception e) {
				e.printStackTrace();
				return new Long(0);
			}

		}
	}

	/**
	 * 根据字符串拆分组合成数组
	 *
	 * @param oldStr
	 *            //传入字符串
	 * @param repStr
	 *            //传入标记,默认为;
	 * @return //返回数组
	 *
	 * 赵小宇
	 */
	public static String[] getCz(String oldStr, String repStr) {
		String[] zfcStr;
		if (repStr == null || "".equals(repStr)) {
			repStr = ";";
		}
		if (oldStr == null || "".equals(oldStr)) {
			zfcStr = new String[1];
			zfcStr[0] = "";
			return zfcStr;
		}
		StringTokenizer StrTok = new StringTokenizer(oldStr, repStr); // 不能传入空串
		int count = StrTok.countTokens();
		zfcStr = new String[count];
		int i = 0;
		while (StrTok.hasMoreTokens()) {
			zfcStr[i] = StrTok.nextToken();
			i += 1;
		}
		return zfcStr;
	}

	/**
	 * 字符串判斷
	 *
	 * @param source
	 * @return
	 */
	public static String dealNull(String source) {
		return dealNull(source, null);
	}

	/**
	 *
	 * @param source
	 * @param defaultStr
	 * @return
	 */
	public static String dealNull(String source, String defaultStr) {
		if (defaultStr == null) {
			defaultStr = "";
		}
		return source == null ? defaultStr : source;
	}

	/**
	 * 一个字符串是否以一个大些字母或数字组成 xyzhao
	 *
	 * @param s
	 * @return
	 */
	public static boolean startsWithDigitOrUpper(String s) {
		return Pattern.compile("^[A-Z0-9]").matcher(s).find();
	}

	/**
	 * 一个字符串是否全是大写字母组成 xyzhao
	 *
	 * @param s
	 * @return boolean
	 */
	public static boolean startsWithDigitOrZMUpper(String s) {
		return Pattern.compile("^[A-Z_]+$").matcher(s).find();
	}

	/**
	 * Convert string to string after trim
	 */
	public static String toString(String sValue) {
		return isNull(sValue) ? "" : sValue.trim();
	}

	/**
	 * 判断是否为数字
	 */
	public static boolean isNumber(String sValue) {
		if (isEmpty(sValue)) {
			return false;
		}
		try {
			Double.parseDouble(sValue);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 数组转字符串 strings 目标字符串数组 compart 转换分隔符 jake
	 */
	public static String arrayChange(String[] strings, String compart) {
		String string = "";
		if (strings == null) {
			return "";
		} else {
			for (int i = 0; i < strings.length; i++) {
				string = string + strings[i] + compart;
			}
			if (string.equals("")) {
			} else {
				string = string.substring(0, string.length() - 1);
			}
		}
		return string;
	}
}
