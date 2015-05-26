package net.sxnic.comm.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;

import net.sxnic.comm.CommConstant;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Introduction ：
 * 
 * @author 孙宇飞
 * @version 2009-3-11
 */

public class CommUtils {

	private static final Log log = LogFactory.getLog(CommUtils.class);

	/**
	 * 判断是否为整数
	 * 
	 * @param str
	 *            要判断的字符串
	 * @return 如果是整数则返回true，否则返回false
	 */
	public static boolean isInt(String str) {
		if (StringUtils.isBlank(str)) {
			return true;
		}

		Pattern pattern = Pattern.compile("^\\d+$|-\\d+$"); // 就是判断是否为整数
		// Pattern pattern =
		// Pattern.compile("\\d+\\.\\d+$|-\\d+\\.\\d+$");//判断是否为小数
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 两个数值相加
	 * 
	 * @param v1
	 *            数值1
	 * @param v2
	 *            数值2
	 * @return 两个浮点型数值之和
	 */
	public static double add(double v1, double v2) {

		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.add(b2).doubleValue();
	}

	/**
	 * 两个数值相加
	 * 
	 * @param v1
	 * @param v2
	 * @return 两个数值之和
	 */
	public static double add(String v1, String v2) {

		BigDecimal b1 = new BigDecimal(v1);

		BigDecimal b2 = new BigDecimal(v2);

		return b1.add(b2).doubleValue();
	}

	/**
	 * 删除原有文件，用指定编码 写新文件
	 * 
	 * @param file
	 *            目标文件
	 * @param sb
	 *            文件内容
	 * @param encoding
	 *            编码
	 */
	public static void deleteandwrite(File file, StringBuffer sb, String encoding) {
		try {
			if (file.isFile()) {
				log.debug("delete file: " + file.getPath());
				file.delete();
			}
			FileUtils.writeStringToFile(file, sb.toString(), encoding);
		} catch (IOException e) {
			e.printStackTrace();
			log.debug("create file : " + file.getPath() + " error!");
		}

		log.debug("create file : " + file.getPath() + " success!");
	}

	public static String getUrlContent(String strUrl)
	// 一个public方法，返回字符串，错误则返回"error open url"
	{
		try {

			URL url = new URL(strUrl);
			System.out.println(url.openStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			String s = "";
			StringBuffer sb = new StringBuffer("");
			while ((s = br.readLine()) != null) {
				sb.append(s + "\r\n");
			}
			br.close();
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "error open url" + strUrl;
		}
	}

	/**
	 * 处理字段名，如果第一个字母不是大写强制变成大写字母，以适应setter 方法的要求。
	 * 
	 * @param fieldName
	 *            字段名
	 * @return 返回以大写字母开头的字段名
	 */
	public static String treatFieldSetterName(String fieldName) {
		if ((fieldName == null) || ("".equals(fieldName))) {
			throw new NullPointerException("fieldName:" + fieldName + " is null");
		}

		// 如果头字母是大写，则原路返回
		if (fieldName.charAt(0) >= 65 && fieldName.charAt(0) <= 90) {
			return fieldName;
		}

		String firestChart = fieldName.substring(0, 1);

		return StringUtils.replaceOnce(fieldName, firestChart, firestChart.toUpperCase());
	}

	/**
	 * 判断是否是windows操作系统
	 * 
	 * @return 如果是windows系统返回true，否则返回false
	 */
	public static boolean isWindowsOs() {
		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否是Linux操作系统
	 * 
	 * @return 如果是linux系统返回true，否则返回false
	 */
	public static boolean isLinuxOs() {
		if (System.getProperty("os.name").toLowerCase().contains("linux")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取当前服务器运行的操作系统
	 * 
	 * @return windows & linux & unix
	 */
	public static String getServerOs() {
		return System.getProperty("os.name");
	}

	/**
	 * 获取当前年
	 * 
	 * @return 当前年
	 */
	public static String getCurrYear() {
		Calendar c = Calendar.getInstance();
		return String.valueOf(c.get(Calendar.YEAR));
	}

	/**
	 * 获得当前月
	 * 
	 * @return 当前月
	 */
	public static String getCurrMonth() {
		Calendar c = Calendar.getInstance();
		return String.valueOf(c.get(Calendar.MONTH) + 1);
		//
	}

	/**
	 * 获得当前日
	 * 
	 * @return 当前日。在月份中的日期
	 */
	public static String getCurrDay() {
		Calendar c = Calendar.getInstance();
		return String.valueOf(c.get(Calendar.DATE));
	}

	/**
	 * 创建一个随机的UUID编码
	 * 
	 * @return UUID码
	 */
	public static String crtRandomUUID() {
		return java.util.UUID.randomUUID().toString();
	}

	/**
	 * 向set中添加string数组
	 * 
	 * @param set
	 * @param strs
	 */
	public static void SetAppendArray(Set<String> set, String[] strs) {
		for (String str : strs) {
			set.add(str);
		}
	}

	/**
	 * 把Set转化为Array
	 * 
	 * @param set
	 * @return 一个数组
	 */
	public static String[] SettoArray(Set<String> set) {

		if (set == null || set.size() == 0) {
			return null;
		}

		String[] strs = new String[set.size()];

		Iterator<String> iter = set.iterator();
		int i = 0;
		while (iter.hasNext()) {
			strs[i] = iter.next();
			i++;
		}

		return strs;
	}

	/**
	 * 把Array 转化为 Set
	 * 
	 * @param strs
	 * @return set
	 */
	public static Set<String> ArraytoSet(String[] strs) {

		Set<String> set = new LinkedHashSet<String>();

		for (String str : strs) {
			set.add(str);
		}

		return set;
	}

	/**
	 * 获取web路径，如http://localhost:8080/sxnicoa 。注：后面带"/"。
	 * 
	 * @param request
	 * @return 路径
	 */
	public static String findWebRealPath(HttpServletRequest request) {
		return StringUtils.substringBefore(request.getRequestURL().toString(), request.getContextPath())
				+ request.getContextPath() + "/";
	}

	/**
	 * 获取客户端的IP地址
	 * 
	 * @param request
	 * @return ip地址
	 */
	public static String findIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}

	/**
	 * 格式化 字符串 成 流水号+1
	 * 
	 * @param fstrFirst
	 *            要格式化的字符串
	 * @param fintLong
	 *            字符串长度
	 * @return String +1的流水号
	 */
	public static String getFormatStr(String fstrFirst, int fintLong) {
		try {
			if (fintLong < fstrFirst.trim().length()) {
				return fstrFirst.trim();
			}
			int lintTemp = 0;
			if (fstrFirst == null || fstrFirst.trim().length() == 0) {
				lintTemp = 1;
			} else {
				lintTemp = Integer.parseInt(fstrFirst);
				lintTemp += 1;
			}
			String lstrLast = Integer.toString(lintTemp);

			int lintLong = lstrLast.trim().length();

			for (int i = 0; i < fintLong - lintLong; i++) {
				lstrLast = "0" + lstrLast;
			}

			return lstrLast;

		} catch (Exception e) {
			String ff = "";
			for (int i = 0; i < fintLong - 1; i++) {
				ff = ff + "0";
			}
			return ff + "1";
		}
	}

	/**
	 * 格式化 字符串 成 流水号
	 * 
	 * @param fstrFirst
	 *            String
	 * @param addNum
	 *            +的数字
	 * @param fintLong
	 *            int 字符串长度
	 * @return String 流水号
	 */
	public static String getFormatStr(String fstrFirst, int addNum, int fintLong) {
		try {
			if (fintLong < fstrFirst.trim().length()) {
				return fstrFirst.trim();
			}
			int lintTemp = 0;
			if (fstrFirst == null || fstrFirst.trim().length() == 0) {
				lintTemp = 1;
			} else {
				lintTemp = Integer.parseInt(fstrFirst);
				lintTemp += addNum;
			}
			String lstrLast = Integer.toString(lintTemp);

			int lintLong = lstrLast.trim().length();

			for (int i = 0; i < fintLong - lintLong; i++) {
				lstrLast = "0" + lstrLast;
			}

			return lstrLast;

		} catch (Exception e) {
			String ff = "";
			for (int i = 0; i < fintLong - 1; i++) {
				ff = ff + "0";
			}
			return ff + "1";
		}
	}

	/**
	 * 从字节数组获取对象
	 */
	public static Object getObjectFromBytes(byte[] objBytes) throws Exception {
		if (objBytes == null || objBytes.length == 0) {
			return null;
		}
		ByteArrayInputStream bi = new ByteArrayInputStream(objBytes);
		ObjectInputStream oi = new ObjectInputStream(bi);
		return oi.readObject();
	}

	/**
	 * 从对象获取一个字节数组
	 */
	public static byte[] getBytesFromObject(Serializable obj) throws Exception {
		if (obj == null) {
			return null;
		}
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream oo = new ObjectOutputStream(bo);
		oo.writeObject(obj);
		return bo.toByteArray();
	}

	/**
	 * 随机生成长度为[length]的字符串
	 * 
	 * @param length
	 *            长度
	 * @param type
	 *            类型：1：大写；2：小写；3：大小写；4：数字；5：小写或数字；默认：小写；
	 * @return 随机字符串
	 * @throws Exception
	 */
	public static String creCharOrNumRandom(int length, int type) {
		String r = "";
		String s = "";
		switch (type) {
		case 1:
			s = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			break;
		case 2:
			s = "abcdefghijklmnopqrstuvwxyz";
			break;
		case 3:
			s = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
			break;
		case 4:
			s = "0123456789";
			break;
		case 5:
			s = "abcdefghijklmnopqrstuvwxyz0123456789";
			break;
		default:
			s = "abcdefghijklmnopqrstuvwxyz";
			break;
		}

		char[] c = s.toCharArray();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			r += c[random.nextInt(c.length)];
		}
		return r;
	}

	public static String findServerIp(String serverName) {
		try {
			String temp = "";
			if (StringUtils.isBlank(serverName)) {
				temp = InetAddress.getLocalHost().toString();
			} else {
				temp = InetAddress.getByName(serverName).toString();
			}

			return StringUtils.substringAfterLast(temp, "/");

		} catch (Exception e) {
			return null;
		}
	}

	public static String findServerHttpIp(String serverName) {
		return "http://" + findServerIp(serverName);
	}

	/**
	 * 剔除由分隔符（通常是逗号）组成的字符串中包含的空格
	 * 
	 * @param srcStr
	 *            输入字符串
	 * @param splitChart
	 *            分隔符 如逗号,
	 */
	public static String splitTrim(String srcStr, String splitChart) {

		if (StringUtils.isBlank(srcStr)) {
			return "";
		}

		String[] temp1 = StringUtils.split(srcStr, splitChart);
		String temp2 = "";

		for (String s : temp1) {
			temp2 = temp2 + StringUtils.trim(s) + splitChart;
		}
		System.out.println("---2----" + temp2);
		return StringUtils.removeEnd(temp2, splitChart);
	}

	/**
	 * 重置文件或者文件夹的大小写
	 * 
	 * @param file
	 * @param type
	 *            1是小写 其余是大写
	 * @throws IOException
	 */
	public static void fileCase(File file, String type) throws IOException {

		if (file == null) {
			return;
		}

		if (file.isDirectory()) {
			for (String fn : file.list()) {
				fileCase(new File(file.getAbsolutePath() + "\\" + fn), type);
			}
		}

		if (!file.isDirectory()) {
			List<String> newList = new ArrayList<String>();
			List<String> list = FileUtils.readLines(file, "utf-8");
			for (String s : list) {
				if ("1".equals(type)) {
					newList.add(s.toLowerCase());
				} else {
					newList.add(s.toUpperCase());
				}
			}

			String fileName = file.getAbsolutePath();
			String encoding = "utf-8";
			if (file.getName().endsWith(".css")) {
				encoding = "gbk";
			} else if (file.getName().endsWith(".html")) {
				encoding = "gbk";
			}

			file.delete();

			FileUtils.writeLines(new File(fileName), encoding, newList);

			log.debug("reset file case success! " + file.getAbsolutePath());
		}
	}

	/**
	 * string转日期
	 * 
	 * @param date
	 * @param format
	 */
	public static Date FormatStringToDate(String date, String format) {
		if (StringUtils.isBlank(date)) {
			return null;
		}

		if (StringUtils.isBlank(format)) {
			format = CommConstant.SHORT_NORMAL_DATEFORMAT;
		}

		SimpleDateFormat df = new SimpleDateFormat(format);
		try {
			return df.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 日期转string
	 * 
	 * @param date
	 * @param format
	 */
	public static String FormatDateToString(Date date, String format) {
		if (date == null) {
			return "";
		}

		if (StringUtils.isBlank(format)) {
			format = CommConstant.SHORT_NORMAL_DATEFORMAT;
		}

		try {
			SimpleDateFormat df = new SimpleDateFormat(format);
			return df.format(date);
		} catch (Exception e) {
			return "";
		}
	}

	public static String getDebugTime() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.HOUR) + "-时-" + c.get(Calendar.MINUTE) + "-分-" + c.get(Calendar.SECOND) + "-秒-"
				+ c.get(Calendar.MILLISECOND);
	}

	public static void printDebugTime() {
		System.out.println(getDebugTime());
	}

	public static void printDebugTime(String arg, String arg1) {
		System.out.println(arg + getDebugTime() + arg1);
	}

	/**
	 * 计算两个时间的天数
	 * 
	 * @param bigDate
	 *            较大的时间
	 * @param smallDate
	 *            较小的时间
	 * @return 间隔天数
	 */
	public static int calTwoDate(Date bigDate, Date smallDate) {
		long l = (bigDate.getTime() - smallDate.getTime()) / (1000 * 60 * 60 * 24);
		return (int) Math.rint(l);
	}

	/**
	 * 从字符串yyyy/MM/dd中抓取year
	 * 
	 * @param date
	 *            yyyy/MM/dd的字符串
	 * @return day，如果date格式不对则返回error
	 */
	public static String fatchYearFromDate(String date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			sdf.parse(date);
		} catch (ParseException e) {
			return "error year";
		}

		return StringUtils.left(date, 4);
	}

	/**
	 * 从字符串yyyy/MM/dd中抓取day
	 * 
	 * @param date
	 *            yyyy/MM/dd的字符串
	 * @return day，如果date格式不对则返回error
	 */
	public static String fatchDayFromDate(String date) {
		String temp = "";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			sdf.parse(date);
		} catch (ParseException e) {
			return "error day";
		}

		temp = StringUtils.substringAfterLast(date, "/");

		if (temp.startsWith("0")) {
			temp = StringUtils.removeStart(temp, "0");
		}

		return temp;
	}

	/**
	 * 从字符串yyyy/MM/dd中抓取month
	 * 
	 * @param date
	 *            yyyy/MM/dd的字符串
	 * @return month 如果date格式不对则返回error
	 */
	public static String fatchMonthFromDate(String date) {

		String temp = "";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			sdf.parse(date);
		} catch (ParseException e) {
			return "error month";
		}

		temp = StringUtils.substringBetween(date, "/");

		if (temp.startsWith("0")) {
			temp = StringUtils.removeStart(temp, "0");
		}

		return temp;
	}

	/**
	 * 创建 jdbc jndi Context
	 * 
	 * @return Context
	 * @throws Exception
	 */
	public static Context crtJdbcJndiContext() throws Exception {
		Context envContext = null;

		String webContainer = CommConstant.PROPERTY_MAP.get("web.container");
		if (!webContainer.equals(CommConstant.SERVER_INFO)) {
			webContainer = CommConstant.SERVER_INFO.toLowerCase();

			if (webContainer.contains("tomcat")) {
				webContainer = "tomcat";
			} else if (webContainer.contains("websphere")) {
				webContainer = "websphere";
			} else if (webContainer.contains("jboss")) {
				webContainer = "jboss";
			}
		}

		if ("tomcat".equals(webContainer)) {
			Context initContext = new InitialContext();
			envContext = (Context) initContext.lookup("java:/comp/env");
		} else if ("websphere".equals(webContainer)) {
			envContext = new InitialContext();
		} else {
			Context initContext = new InitialContext();
			envContext = (Context) initContext.lookup("java:/comp/env");
		}
		return envContext;
	}

	public static boolean isHanzi(String str) {
		char[] chars = str.toCharArray();
		boolean isGB2312 = false;

		for (int i = 0; i < chars.length; i++) {
			byte[] bytes = ("" + chars[i]).getBytes();
			if (bytes.length >= 2) {
				isGB2312 = true;
			}
		}
		return isGB2312;
	}

	/**
	 * 获取文件的后缀名
	 * 
	 * @param fileName
	 *            文件名或文件全路径+文件名
	 * @return
	 */
	public static String getSuffixByFileName(String fileName) {

		if (StringUtils.isBlank(fileName) || !fileName.contains(".")) {
			return "error suffix";
		}

		return StringUtils.substringAfterLast(fileName, ".");
	}

	/**
	 * 替换英文引号为中文引号，引号可以不成对儿出现
	 * 
	 * @param input
	 *            待处理的字符串
	 * @return
	 */
	public static String replaceQuote(String pStr) {
		if (pStr == null) {
			return "";
		}
		// 把字符串按照双引号截成数组
		String[] str = pStr.split("\"");
		if (str.length == 1) {
			return pStr;
		}
		// 替换后的字符串
		String Newstr = "";
		for (int i = 1; i <= str.length; i++) {
			if (i % 2 == 0) {
				Newstr += str[i - 1] + "”";
			} else {
				Newstr += str[i - 1] + "“";
			}
		}

		return Newstr.substring(0, Newstr.length() - 1);

	}

	/**
	 * 转化Set 为List
	 * 
	 * @param set
	 * @return
	 */
	public static List<Object> treatSetToList(Set<Object> set) {

		if (set == null) {
			return null;
		}

		List<Object> list = new ArrayList<Object>();
		for (Object t : set) {
			list.add(t);
		}

		return list;
	}

	/**
	 * 将两个具有相同属性名和类型的对象的复制
	 * 
	 * @param oldObj
	 *            原来的对象
	 * @param newObj
	 *            复制之后的对象
	 * @return 复制之后新的对象
	 * @throws Exception
	 */
	public static Object objClone(Object oldObj, Object newObj) throws Exception {

		if (oldObj == null || newObj == null) { // 当传入的参数存在空值是，返回空
			return null;
		}

		Class oldClass = oldObj.getClass();
		Class newClass = newObj.getClass();

		Field[] fields1 = oldClass.getDeclaredFields(); // 获取到oldClass的所有属性
		Field[] fields2 = newClass.getDeclaredFields(); // 获取到newClass的所有属性

		Method method1 = null;
		Method method2 = null;
		
		String upFidName = "";

		for (Field fid1 : fields1) { // 遍历oldClass的属性
			for (Field fid2 : fields2) { // 遍历newClass的属性
				if (fid1.getName().equals(fid2.getName())) { // 判断属性名字是否一样
					upFidName = fid1.getName().substring(0, 1).toUpperCase() + fid1.getName().substring(1); // 属性的首字母大写

					try {
						method1 = oldClass.getMethod("get" + upFidName, null); // 获取到oldClass的一个属性的get方法
						method2 = newClass.getMethod("set" + upFidName, fid1.getType());// 获取到oldClass的一个属性的set方法
					} catch (NoSuchMethodException e) {
					}
					if (method1 != null && method2 != null) {
						method2.invoke(newObj, method1.invoke(oldObj));
					}
				}
			}
		}
		return newObj;
	}

}
