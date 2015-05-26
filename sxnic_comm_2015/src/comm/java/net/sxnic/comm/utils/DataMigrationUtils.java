package net.sxnic.comm.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sxinfo.core.entity.AbstractEntity;
import net.sxnic.comm.CommConstant;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Introduction ：db到xml文件 的双向数据迁移工具类
 * 
 * @author 孙宇飞
 * @version 2009-10-29
 */

public class DataMigrationUtils {

	private static Logger log = LoggerFactory
			.getLogger(DataMigrationUtils.class);

	/**
	 * java包名，便于找到table对应的Entity名字
	 */
	public static final String BASE_PACKAGE_NAME = "net.sxnic.comm";

	/**
	 * 基本码表 表名，即实体名
	 */
	public static final String BASECODE_BACKUP_TABLENAME = "BaseCode";

	/**
	 * 属性表 表名，即实体名
	 */
	public static final String PROPERTY_BACKUP_TABLENAME = "Property";

	/**
	 * 菜单表 表名，即实体名
	 */
	public static final String MENU_BACKUP_TABLENAME = "Menu";

	/**
	 * 日志 表名，即实体名
	 */
	public static final String LOG_BACKUP_TABLENAME = "Log";

	/**
	 * 默认的系统表 表名数组
	 */
	public static final String[] BASEDATA_BACKUP_TABLENAMES = new String[] {
			BASECODE_BACKUP_TABLENAME, PROPERTY_BACKUP_TABLENAME,
			MENU_BACKUP_TABLENAME, LOG_BACKUP_TABLENAME };

	/**
	 * 主数据表 表名数组
	 */
	public static final String[] MAINDATA_BACKUP_TABLENAMES = new String[] {
			"MainData", "SubData", "ExtraData" };

	public static final String USER_BACKUP_TABLENAME = "User";

	public static final String MAINDATA_BACKUP_TABLENAME = "MainData";
	public static final String EXTRADATA_BACKUP_TABLENAME = "ExtraData";
	public static final String SUBDATA_BACKUP_TABLENAME = "SubData";

	/**
	 * 备份
	 * 
	 * @param destFile 指定的备份文件
	 * @param nodes 要备份的表名
	 * @throws Exception
	 */
	public static void systemDataBackup(File destFile, String[] tables)
			throws Exception {

		// 如果没有指定的备份文件名，则系统采用默认的备份文件路径策略
		if (destFile == null) {
			destFile = createBackupFile();
		}

		File tempFile = null;

		// 构建 xml文件类
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("root");

		// 遍历所有表名
		for (String table : tables) {
			// 根据表名获取manager类
			Object manager = getManagerOnWeb(table);
			// 查询table中的所有数据
			List list = getData(manager);
			
			// 利用反射，把list中的数据set到xml文件中
			treatFildstoXml(doc, root, table, list);

			// 格式化xml文件输出器
			OutputFormat format = OutputFormat.createPrettyPrint();

			tempFile = new File(createTempFilePath() + "\\" + table + ".xml");
			// 写入数据
			if (CommUtils.isWindowsOs()) {
				format.setEncoding("GBK");
			} else if (CommUtils.isLinuxOs()) {
				format.setEncoding("UTF-8");
			}
			

			XMLWriter writer = new XMLWriter(new FileOutputStream(destFile),
					format);
			
			writer.write(doc);

			writer.close();

		}
		// 压缩目标文件
		//ZipFileUtil.ZipFile(destFile, new File(createTempFilePath()));
		// File tempZipFile = new File(createTempFilePath());

		// 删除临时文件及其文件夹
		//FileUtils.deleteDirectory(new File(createTempFilePath()));

		log.debug("write xml file over");
	}

	/**
	 * 根据表名nodeName，得到实体Entity类并实例化为对象，利用反射，调用其中的setter和getter方法
	 * 
	 * @param doc 要保存的xml文件对象
	 * @param root xml文件中的root根节点
	 * @param tableName 表名。如BaseCode，Property
	 * @param list 数据
	 * @throws Exception
	 */
	public static void treatFildstoXml(Document doc, Element root,
			String tableName, List list) throws Exception {

		Element table = root.addElement(tableName);
		Element rows = table.addElement("rows");

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		Object manager = getManagerOnWeb(tableName);
		Element e = null;
		Object obj = null;
		Method method = null;
		String content = null;

		Iterator iter = list.iterator();
		while (iter.hasNext()) {

			Element row = rows.addElement("row");

			// 利用反射，得到table的Entity实体类的实例
			obj = net.sxinfo.core.util.ClassUtils.newInstance(BASE_PACKAGE_NAME
					+ "." + tableName.toLowerCase() + "." + tableName,
					DataMigrationUtils.class);

			obj = iter.next();

			// id createdate
			method = AbstractEntity.class.getMethod("getId");
			row.addElement("id").setText((String) method.invoke(obj));

			method = AbstractEntity.class.getMethod("getCreationDate");
			row.addElement("creationDate").setText(
					f.format((Date) method.invoke(obj)));

			// Entity中所有申明的field
			Field[] fields = obj.getClass().getDeclaredFields();

			String fieldName = null;
			// 遍历fields
			for (Field field : fields) {
				fieldName = field.getName();

				if ("serialVersionUID".equals(fieldName)
						|| field.getModifiers() == 25) {
					continue;
				}

				content = getEntityContent(obj, field);
				if (StringUtils.isBlank(content) || "null".equals(content)) {
					content = "";
				}

				e = row.addElement(field.getName());
				e.setText(content);
				e.addAttribute("type", String.valueOf(field.getType()).replace("class ", ""));
			}
		}

		log.debug("put " + tableName + " to xml file over");
		return;
	}

	public static String getEntityContent(Object obj, Field field)
			throws Exception {

		// 检查field的类型
		// log.debug(field.getType());

		String opt = "get";
		if (field.getType().equals(boolean.class)) {
			opt = "is";
		}

		// 得到Entity中的getter方法
		Method method = obj.getClass().getMethod(
				opt + CommUtils.treatFieldSetterName(field.getName()));

		// 执行Entity中的getter方法，并把数据赋值到xml指定的节点上

		return String.valueOf(method.invoke(obj));
	}

	/**
	 * 系统数据还原
	 * 
	 * @param backupFile 备份文件一个rar文件或者xml文件
	 * @throws Exception
	 */
	public static void systemDataRestore(File backupFile) throws Exception {
		// 判断是否是压缩文件还是xml文件
		String fileName = backupFile.getName();
		if (fileName.toLowerCase().endsWith(".rar")) {
			//ZipFileUtil.unZipFile(backupFile, "c:\\temp\\");
			backupFile = new File(createTempFilePath());
		}

		SAXReader saxReader = new SAXReader();
		File[] files = null;

		if (backupFile.isDirectory()) {
			files = backupFile.listFiles();
		} else if (backupFile.isFile()) {
			files = new File[1];
			files[0] = backupFile;
		} else
			return;

		// 遍历所有文件
		for (File file : files) {
			if (!file.isFile()) {
				systemDataRestore(file);
			}

			Document doc = saxReader.read(file);
			String tableName = file.getName();
			tableName = StringUtils.remove(tableName, ".xml");
			treatXmltoEntity(doc, tableName);
		}

		// 删除解压的文件夹
		FileUtils.deleteDirectory(new File(createTempFilePath()));

		log.debug("------------restore over----------------");
	}

	public static void treatXmltoEntity(Document doc, String tableName)
			throws Exception {
		List list = doc.selectNodes("/root/" + tableName + "/rows/row");

		Iterator iter = list.iterator();

		Object manager = getManagerOnJunit(tableName);
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		Element e = null;
		Object obj = null;
		Method method = null;
		String createDate = null;

		while (iter.hasNext()) {
			e = (Element) iter.next();

			obj = net.sxinfo.core.util.ClassUtils.newInstance(BASE_PACKAGE_NAME
					+ "." + tableName.toLowerCase() + "." + tableName,
					DataMigrationUtils.class);

			// id createdate
			method = AbstractEntity.class.getMethod("setId", "String"
					.getClass());
			method.invoke(obj, e.selectSingleNode("id").getText());

			method = AbstractEntity.class.getMethod("setCreationDate",
					java.util.Date.class);
			createDate = e.selectSingleNode("creationDate").getText();
			method.invoke(obj, f.parse(createDate));

			// 遍历所有元素
			Field[] fields = obj.getClass().getDeclaredFields();

			for (Field field : fields) {
				if ("serialVersionUID".equals(field.getName())) {
					continue;
				}

				method = obj.getClass()
						.getMethod(
								"set"
										+ CommUtils.treatFieldSetterName(field
												.getName()),
								"String".getClass());
				method.invoke(obj, e.selectSingleNode(field.getName())
						.getText());
			}

			// manager.create(obj);
			try {
				method = manager.getClass().getMethod("create", obj.getClass());
				method.invoke(manager, obj);
			} catch (Exception e1) {
				e1.printStackTrace();
				method = manager.getClass().getMethod("create" + tableName,
						obj.getClass());
				method.invoke(manager, obj);
			}
		}

		log.debug("put backup file data to " + tableName + "  over");
		return;
	}

	/**
	 * 创建临时备份文件夹，以便对其下文件压缩，然后保存
	 * 
	 * @return
	 */
	public static String createTempFilePath() {
		String osName = System.getProperty("os.name");
		File file = null;

		if (osName.toLowerCase().startsWith("windows")) {
			file = new File(net.sxnic.comm.utils.PropertyUtil.getLogFilePaht()
					+ File.separator + "_temp_" + File.separator);
		} else if ("linux".equals(osName.toLowerCase())) {
			file = new File(net.sxnic.comm.utils.PropertyUtil.getLogFilePaht()
					+ File.separator + "_temp_" + File.separator);
		} else
			return null;

		if (!file.isDirectory()) {
			file.mkdir();
		}
		return file.getPath();
	}

	/**
	 * 创建备份文件
	 * 
	 * @return
	 */
	public static File createBackupFile() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);

		String backuppath = CommConstant.PROPERTY_MAP.get("backup.file.path");
		if (StringUtils.isBlank(backuppath)) {
			backuppath = "files\\backup\\";
		}
		String savePath = System.getProperty("webapp.root") + backuppath;
		savePath = savePath + String.valueOf(year) + "\\"
				+ String.valueOf(month) + "\\";

		log.debug("backup file path is :" + savePath);

		File file = new File(savePath);

		Date now = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd--HH-MM-SS");

		String fileName = f.format(now) + ".rar";

		return new File(savePath + fileName);
	}

	/**
	 * 根据 manager 类中的getAll方法获取表里所有数据
	 * 
	 * @param manager
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static List getData(Object manager) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {

		Method method = manager.getClass().getMethod("getAll");

		return (List) method.invoke(manager);
	}

	/**
	 * 在做单元测试的时候使用的 manager 提取方法
	 * 
	 * @param tableName
	 * @return
	 */
	public static Object getManagerOnJunit(String tableName) {
		ApplicationContext ac = new FileSystemXmlApplicationContext(System
				.getProperty("user.dir")
				+ "\\src\\test\\applicationContext.xml");

		return ac.getBean(tableName.toLowerCase() + "Manager");
	}

	/**
	 * 在正式运行的时候时候使用的 manager 提取方法
	 * 
	 * @param tableName
	 * @return
	 */
	public static Object getManagerOnWeb(String tableName) {
		ApplicationContext ac;
		try {
//			ac = WebApplicationContextUtils
//					.getRequiredWebApplicationContext(ServletActionContext
//							.getServletContext());
			ac = WebApplicationContextUtils
					.getRequiredWebApplicationContext(null);
		} catch (Exception e) {
			ac = new FileSystemXmlApplicationContext(
					"classpath:applicationContext.xml");
		}

		return ac.getBean(tableName.toLowerCase() + "Manager");
	}

	/**
	 * 数据表备份
	 * 
	 * @throws Exception
	 */
	public static void mainDataBackup() throws Exception {
		// 构建 xml文件类
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("root");

		Element row = null;
		Element e = null;
		int col_count = 0;
		Object[] obj = null;
		ResultSetMetaData rsmd = null;
		String query = null;
		String col_type = null;

		DbUtil db = new DbUtil();
		db.connect();

		for (String tableName : MAINDATA_BACKUP_TABLENAMES) {
			Element table = root.addElement(tableName);
			Element rows = table.addElement("rows");

			query = "select * from " + "app_" + tableName.toLowerCase();
			log.debug(query);
			ResultSet rs = db.getRs(query);
			rsmd = rs.getMetaData();
			col_count = rs.getMetaData().getColumnCount();

			while (rs.next()) {
				row = rows.addElement("row");
				obj = new Object[col_count];

				for (int ii = 0; ii < col_count; ii++) {
					col_type = rsmd.getColumnTypeName(ii + 1);
					log.debug("col_type == " + col_type);

					obj[ii] = rs.getString(ii + 1);
					log.debug("obj == " + obj[ii]);
					if (obj[ii] == null) {
						obj[ii] = "";
					}

					e = row.addElement("field");
					e.addAttribute("name", rsmd.getColumnName(ii + 1));
					e.setText(obj[ii].toString());
					e.addAttribute("type", rsmd.getColumnClassName(ii + 1));
				}
			}
		}

		db.disConnect();
		db = null;

		// 格式化xml文件输出器
		OutputFormat format = OutputFormat.createPrettyPrint();
		// 设置xml编码为utf8
		format.setEncoding("GBK");

		File tempFile = new File(createTempFilePath() + "\\" + "maindata"
				+ ".xml");
		// 写入数据
		XMLWriter writer = new XMLWriter(new FileWriter(tempFile), format);
		writer.write(doc);

		writer.close();
	}

	/**
	 * 主表数据恢复
	 * 
	 * @param restoreFile
	 * @throws DocumentException
	 */
	public static void mainDataRestore(File restoreFile)
			throws DocumentException {
		SAXReader saxReader = new SAXReader();
		Document doc = saxReader.read(restoreFile);
		StringBuffer insertSQL = null;
		StringBuffer updateSQL = null;
		DbUtil du = new DbUtil();
		du.connect();

		for (String tableName : MAINDATA_BACKUP_TABLENAMES) {
			List list = doc.selectNodes("/root/" + tableName + "/rows");
			Iterator iter = list.iterator();
			String id = null;

			while (iter.hasNext()) {
				Element rowsElement = (Element) iter.next();

				List entityList = rowsElement.selectNodes("row");
				Iterator iter1 = entityList.iterator();

				while (iter1.hasNext()) {

					Element rowElement = (Element) iter1.next();
					Iterator iter2 = rowElement.selectNodes("field").iterator();

					insertSQL = new StringBuffer();
					insertSQL.append("insert into app_"
							+ tableName.toLowerCase() + " values (");

					updateSQL = new StringBuffer();
					updateSQL.append("update app_" + tableName.toLowerCase()
							+ " set ");

					while (iter2.hasNext()) {
						Element fieldElement = (Element) iter2.next();
						String fieldName = fieldElement.attributeValue("name");
						String type = fieldElement.attributeValue("type");

						if ("id".equals(fieldName)) {
							id = fieldName;
						}

						if (type.toLowerCase().endsWith("string")) {
							insertSQL.append("'" + fieldElement.getTextTrim()
									+ "',");
							updateSQL.append(" " + fieldName + " = '"
									+ fieldElement.getTextTrim() + "', ");
						} else if (type.toLowerCase().endsWith("double")) {
							insertSQL.append("" + fieldElement.getTextTrim()
									+ ",");
							updateSQL.append(" " + fieldName + " = "
									+ fieldElement.getTextTrim() + ", ");
						} else if (type.toLowerCase().endsWith("integer")) {
							insertSQL.append("" + fieldElement.getTextTrim()
									+ ", ");
							updateSQL.append(" " + fieldName + " = "
									+ fieldElement.getTextTrim() + ", ");
						} else if (type.toLowerCase().endsWith("long")) {
							insertSQL.append(" " + fieldElement.getTextTrim()
									+ ", ");
							updateSQL.append(" " + fieldName + " = "
									+ fieldElement.getTextTrim() + ", ");
						} else if (type.toLowerCase().endsWith("timestamp")) {
							insertSQL.append(" " + fieldElement.getTextTrim()
									+ ", ");
							updateSQL.append(" " + fieldName + " = '"
									+ fieldElement.getTextTrim() + "',");
						}
					}
					insertSQL.append(")");

					// 根据ID查看是否表中有当前Id的数据，如果有则更新，否则插入
					if (DataMigrationUtils.isTableNull(du, tableName, id)) {
						du.executeDb(insertSQL.toString());
					} else {
						du.executeDb(updateSQL.toString());
					}
					du.commit();
				}
			}

		}
		du.disConnect();
		du = null;
	}

	/**
	 * 检验是否表中有id对应的数据
	 * 
	 * @param du 数据库执行类
	 * @param tableName 表名
	 * @param id 对应ID
	 * @return 如果有与Id对应的数据则返回false，没有则返回true
	 */
	public static boolean isTableNull(DbUtil du, String tableName, String id) {
		if (du == null || StringUtils.isBlank(tableName)) {
			return false;
		}

		String query = "select id from " + tableName + " where id = '" + id
				+ "'";

		List list = du.select(query);
		if (list == null || list.size() <= 0) {
			return true;
		}
		return false;
	}

	public static void backupAttachment() {

	}
}
