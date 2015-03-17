package net.sxnic.comm;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.time.DateUtils;

public class CommConstant {

	/**
	 * （单个字段排序）用来保存当前排序的字段排序规则。格式：field:sortype name:asc $$ code:desc
	 */
	public static final String SORT_SESSION_KEY = "sort_session_key";

	/**
	 * 组合排序的map
	 */
	public static final String SORTMAP_SESSION_KEY = "sortmap_key";
	
	/**
	 * sortCode没有年的概念时，统一用Y作为Map的key
	 */
	public static final String SORT_YEAR = "Y";

	public static String SYSTEM_CONTEXTPATH = "sxkh";

	/**
	 * 系统路径: http://10.10.9.58:8080/sxnicoa
	 */
	public static String SYSTEM_HOSTPATH = "";

	public static String SYSTEM_SERVERPOST = "";

	/**
	 * 在系统启动时需要初始化的 基本信息码表MAP
	 */
	public static Map<String, Map<String, String>> BASECODE_MAP = new ConcurrentHashMap<String, Map<String, String>>();
	
	/**
	 * 2015年正式引入年份概念，Map的key分别表示：类别、年、信息码
	 */
	public static Map<String, Map<String,Map<String, String>>> BASECODE_YEAR_MAP = new ConcurrentHashMap<String, Map<String,Map<String, String>>>();
	
	/**
	 * 在系统启动时需要初始化的 属性表MAP
	 */
	public static Map<String, String> PROPERTY_MAP = new ConcurrentHashMap<String, String>();
	
	/**
	 * 在系统启动时需要初始化的 菜单MAP
	 */
	public static Map<String, String> MENU_MAP = new ConcurrentHashMap<String, String>();	

	/**
	 * Word文档的打印字体格式
	 */
	public static Map<String, Map<String, String>> fontMap = new HashMap<String, Map<String, String>>();

	/**
	 * 上传附件位置，是否绝对路径的前缀
	 */
	public static final String ABSOLUTE_PATH_PREFIX = "DIR:";

	/**
	 * 初始化 基本码表 文件名
	 */
	public static final String INIT_BASECODE_TXT = "basecode.txt";

	/**
	 * 初始化 菜单 文件名
	 */
	public static final String INIT_MENU_TXT = "menu.txt";

	/**
	 * 初始化 属性表 文件名
	 */
	public static final String INIT_PROPERTY_TXT = "property.txt";
	
	/**
	 * 初始化 处室 文件名
	 */
	public static final String INIT_DEPARTMENT_TXT = "dept.txt";
	
	/**
	 * 初始化 用户文件名
	 */
	public static final String INIT_UGR_TXT = "ugr.txt";


	/**
	 * 初始化 角色，组 文件名
	 */
	public static final String INIT_BASCIC_ROLE_GROUP_TXT = "wht_basic_user_role_group.txt";

	/**
	 * 在定义文件或者txt文件中，所有以#开头的行，都为忽略行，即为注释行
	 */
	public static final String TXT_IGNORE_CHAR = "#";
	
	/**
	 * 在定义文件或者txt文件中，所有包含###的行，都为忽略行，即为注释行
	 */
	public static final String TXT_IGNORE_CHARS = "###";

	/**
	 * 日期时间的格式化格式
	 */
	public static final String LONG_DATETIME_FORMAT_CN = "yyyy年MM月dd日 HH时mm分ss秒";

	/**
	 * 日期的格式化格式
	 */
	public static final String SHORT_DATE_FORMAT_CN = "yyyy年MM月dd日";
	
	/**
	 * 时间日期格式 长
	 */
	public static final String LONG_NORMAL_DATEFORMAT = "yyyy-MM-dd HH:mm";
	
	/**
	 * 时间日期格式 短
	 */
	public static final String SHORT_NORMAL_DATEFORMAT = "yyyy-MM-dd";
	
	
	/**
	 * 服务器IP 
	 */
	public static String SERVER_IP="";
	
	/**
	 * 服务器端口号
	 */
	public static String SERVER_PORT="";
	
	/**
	 * 服务器类型 
	 */
	public static String SERVER_INFO="";

	/**
	 * Session key
	 */
	public static final String SESSION_KEY = "app.session_key";

	/**
	 * 默认autologin时间为1年
	 */
	public static final int AUTO_LOGIN_COOKIE_MAX_AGE = Long.valueOf(
			DateUtils.MILLIS_PER_DAY * 365).intValue();

	/**
	 * 无权限提示页
	 */
	public static final String NOT_AUTHORIZED_PAGE = "/unAuthorized.jsp";

	/**
	 * Cookie key
	 */
	public static final String AUTOLOGIN_COOKIE_KEY = "app.cookie_user";

	/**
	 * Redirect URL KEY
	 */
	public static final String REDIRECT_URL_KEY = "redirectUrl";

	/**
	 * Default Page size
	 */
	public static final int DEFAULT_PAGE_SIZE = 20;

	/**
	 * 文档下载路径:http://218.26.227.171/shenbaodoc/
	 */
	public static String DOC_DOWNLOAD_PATH = "";

	/**
	 * 默认的页面风格名称
	 */
	public static final String DEFAULT_CSS_STYLE = "classic";

	/**
	 * 一个基础属性文件名
	 */
	public static final String APPLICATION_PROPERTIES_FILE = "application.properties";


	/**
	 * 月份
	 */
	public static final String[] MONTHS = new String[] { "1", "2", "3", "4",
			"5", "6", "7", "8", "9", "10", "11", "12" };
	
	
	public static final String CERT_START_HMS = "00:00:00";
	
	public static final String CERT_END_HMS = "23:59:59";
	
	public static final String APPCONTEXT_USERNAME="appConext_userName";
}
