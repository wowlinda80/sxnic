package net.sxnic.comm.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.naming.Context;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.internal.util.ConfigHelper;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Introduction ：JDBC基础类
 * 
 * @author 孙宇飞
 * @version 2009-5-6
 */

public class DbUtil extends HibernateDaoSupport {

	private Log log = LogFactory.getLog(DbUtil.class);

	/** DB连接失败 */
	public static final int CST_DBERROR = -1;

	/** DB连接成功 */
	public static final int CST_DBSUCCESS = 0;

	/** 数据库连接失败 */
	public final static String CST_MSG_DBCONNECT_ERROR = "数据库连接失败！";

	/** DBConnection */
	private Connection cconOra = null;

	private String classforName;
	
	private boolean connected = false;

	/**
	 * 数据库连接状态
	 * 
	 * @param void
	 * @return boolean
	 */
	public boolean isConnect() {
		try {
			if ((cconOra == null) || (cconOra.isClosed())) {
				return false;
			} else {
				return true;
			}
		} catch (Exception ex) {
			return false;
		}
	}
	
	/**
	 * DB连接
	 * 
	 * @param void
	 * @return int
	 * @throws DocumentException
	 * @throws Exception
	 */
	public int connectByJdbcProp() throws DocumentException {

		InputStream is = this.getClass().getClassLoader().getResourceAsStream("jdbc.properties");
		Properties p = new Properties();
		
		try {			
			p.load(is);			
			Class.forName(p.getProperty("jdbc.driverClassName"));
		} catch (Exception ex) {
			ex.printStackTrace();
			return (CST_DBERROR);
		}

		try {
			cconOra = java.sql.DriverManager.getConnection(p.getProperty("jdbc.url"), p.getProperty("jdbc.username"),
					p.getProperty("jdbc.password"));
			cconOra.setAutoCommit(false);
		} catch (SQLException ex) {
			return (CST_DBERROR);
		} catch (Exception ex) {
			return (CST_DBERROR);
		}

		return (CST_DBSUCCESS);
	}

	/**
	 * DB连接
	 * 
	 * @param void
	 * @return int
	 * @throws DocumentException
	 * @throws Exception
	 */
	public int connect() throws DocumentException {
		String lstrurl = "";
		String lstruser = "";
		String lstrpassword = "";

		SAXReader saxReader = new SAXReader();
		InputStream stream = ConfigHelper
				.getResourceAsStream("/hibernate.cfg.xml");
		Document doc = saxReader.read(stream);
		List list = doc
				.selectNodes("/hibernate-configuration/session-factory/property");

		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			Element e = (Element) iter.next();
			if ("connection.driver_class".equals(e.attributeValue("name"))) {
				classforName = e.getTextTrim();
			} else if ("connection.url".equals(e.attributeValue("name"))) {
				lstrurl = e.getTextTrim();
			} else if ("connection.username".equals(e.attributeValue("name"))) {
				lstruser = e.getTextTrim();
			} else if ("connection.password".equals(e.attributeValue("name"))) {
				lstrpassword = e.getTextTrim();
			}
		}

		try {
			// JDBC THIN实例化
			Class.forName(classforName);
		} catch (Exception ex) {
			return (CST_DBERROR);
		}

		try {
			cconOra = java.sql.DriverManager.getConnection(lstrurl, lstruser,
					lstrpassword);
			cconOra.setAutoCommit(false);
		} catch (SQLException ex) {
			return (CST_DBERROR);
		} catch (Exception ex) {
			return (CST_DBERROR);
		}

		return (CST_DBSUCCESS);
	}
	
	/**
	 * 使用JNDI数据源
	 * @param jndi_name
	 * @return
	 */
	public int connect(String jndi_name) {
		try {
			
			Context envContext = CommUtils.crtJdbcJndiContext();
			DataSource ds = (DataSource) envContext.lookup(jndi_name);
			cconOra = ds.getConnection();

			if (cconOra == null) {
				return CST_DBERROR;
			}

			cconOra.setAutoCommit(false);
			connected = true;
			return CST_DBSUCCESS;

		} catch (Exception e) {
			e.printStackTrace();
			connected = false;
			return CST_DBERROR;
		}
	}

	public int connect(String classForName, String url, String userName,
			String password) throws Exception {

		try {
			// JDBC THIN实例化
			Class.forName(classForName);			
		} catch (Exception ex) {
			return (CST_DBERROR);
		}

		try {
			cconOra = DriverManager.getConnection(url, userName,
					password);
			cconOra.setAutoCommit(false);
		} catch (SQLException ex) {
			ex.printStackTrace();
			return (CST_DBERROR);
		} catch (Exception ex) {
			ex.printStackTrace();
			return (CST_DBERROR);
		}

		return (CST_DBSUCCESS);
	}

	/**
	 * 断开数据库连接
	 * 
	 * @param void
	 * @return int
	 */
	public int disConnect() {
		try {
			if (!isConnect()) {
				return (CST_DBERROR);
			}

			// 设置事务自动提交为true
			cconOra.setAutoCommit(true);

			// 关闭数据库连接
			cconOra.close();
		} catch (SQLException ex) {
			return (CST_DBERROR);
		} catch (Exception ex) {
			return (CST_DBERROR);
		}

		return (CST_DBSUCCESS);
	}

	/**
	 * 执行查询语句
	 * 
	 * @param query
	 * @return Vector
	 */
	public Vector select(String query) {
		int clm_ct = 0;

		// 定义变量
		PreparedStatement stmt = null;
		ResultSet rs = null;

		Vector cvecData = new Vector();

		try {
			if (!isConnect()) {
				return cvecData;
			}
			
			// 生成SQL的设备
			stmt = cconOra.prepareStatement(query);

			// 执行SQL语句的查询
			stmt.executeQuery();
			rs = stmt.getResultSet();

			// 取得数据集
			ResultSetMetaData rsmd = rs.getMetaData();

			clm_ct = rsmd.getColumnCount();
			
			// 保存查询结果
			while (rs.next()) {
				

				// 检索结果存入数组
				Object[] obj = new Object[clm_ct];

				for (int ii = 0; ii < clm_ct; ii++) {
					obj[ii] = rs.getString(ii + 1);
					
					if (obj[ii] == null) {
						obj[ii] = "";
					}
				}

				// 数组存入Vector
				cvecData.addElement(obj);
			}

			// 关闭结果集
			rs.close();

			// 关闭SQL的设备
			stmt.close();
		} catch (SQLException ex) {
		} catch (Exception ex) {
		}

		return cvecData;
	}
    
	@SuppressWarnings("unchecked")
	public Map selectMap(String query)
    {

		// 定义变量
		PreparedStatement stmt = null;
		ResultSet rs = null;

		@SuppressWarnings("rawtypes")
		Map rowmap = new HashMap();

		try {
			if (!isConnect()) {
				return rowmap;
			}

			// 生成SQL的设备
			stmt = cconOra.prepareStatement(query);

			// 执行SQL语句的查询
			stmt.executeQuery();
			rs = stmt.getResultSet();

			// 取得数据集
			ResultSetMetaData rsmd = rs.getMetaData();
	
			System.out.println("======"+rs.getObject("HPHM").toString());

			// 保存查询结果\
			for(int i=1;i<=rsmd.getColumnCount();i++)
			{
				int type=rsmd.getColumnType(i);
				String name=rsmd.getColumnName(i);
				if(Types.VARCHAR==type)
					rowmap.put(name, rs.getString(i));
				else if(Types.INTEGER==type)
					rowmap.put(name, rs.getInt(i));
				else if(Types.SMALLINT==type)
					rowmap.put(name, rs.getShort(i));
				else if(Types.DATE==type)
					rowmap.put(name, rs.getDate(i));
			}
			// 关闭结果集
			rs.close();

			// 关闭SQL的设备
			stmt.close();
		} catch (SQLException ex) {
		} catch (Exception ex) {
		}

		return (rowmap);
    }
	/**
	 * 查询结果集
	 * 
	 * @param query
	 * @return
	 */
	public ResultSet getRs(String query) {
		// 定义变量
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			if (!isConnect()) {
				return null;
			}

			// 生成SQL的设备
			stmt = cconOra.prepareStatement(query);

			// 执行SQL语句的查询
			stmt.executeQuery();
			rs = stmt.getResultSet();
			return rs;
			// // 关闭结果集
			// rs.close();
			//
			// // 关闭SQL的设备
			// stmt.close();

		} catch (SQLException ex) {
			return null;
		} catch (Exception ex) {
			return null;
		}

	}

	/**
	 * 执行数据库的更新操作
	 * 
	 * @param sql
	 * @return int
	 */
	public int executeDb(String sql) {
		int num = -1;
		
		// 检查SQL语句
		if (sql == null) {
			return (CST_DBERROR);
		}

		Statement stmt = null;

		try {

			// 检查数据库连接状态
			if (!isConnect()) {
				return (CST_DBERROR);
			}

			stmt = cconOra.createStatement();
			num = stmt.executeUpdate(sql);

			// 关闭SQL的设备
			stmt.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			return (CST_DBERROR);
		} catch (Exception ex) {
			ex.printStackTrace();
			return (CST_DBERROR);
		}

		return (CST_DBSUCCESS);
	}

	/**
	 * 数据库更新操作提交
	 * 
	 * @param void
	 * @return int
	 */
	public int commit() {

		// 数据库更新操作提交
		try {
			if (!isConnect()) {
				return (CST_DBERROR);
			}

			cconOra.commit();
		} catch (SQLException ex) {
			if (ex != null) {
			}

			return (CST_DBERROR);
		} catch (Exception ex) {
			return (CST_DBERROR);
		}

		return (CST_DBSUCCESS);
	}

	/**
	 * 数据库更新操作回滚
	 * 
	 * @param void
	 * @return int
	 */
	public int rollback() {
		try {

			// 数据库更新操作回滚
			if (!isConnect()) {
				return (CST_DBERROR);
			}

			cconOra.rollback();
		} catch (SQLException ex) {
			return (CST_DBERROR);
		} catch (Exception ex) {
			return (CST_DBERROR);
		}

		return (CST_DBSUCCESS);
	}

	public class comm_const {
	}

	public Connection getCconOra() {
		return cconOra;
	}

	public void setCconOra(Connection cconOra) {
		this.cconOra = cconOra;
	}

	public boolean isConnected() {
		return connected;
	}

}
