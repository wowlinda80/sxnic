package net.sxnic.comm.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

///////////////////////

public class DbUtilTest {
	
	private static Log log = LogFactory.getLog(DbUtilTest.class);

	@Test
	public void testConnect() {
		DbUtil db = new DbUtil();

		try {
//			log.debug(new Date());
//			
//			String constr="jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=10.95.51.37)(PORT=1521))(ADDRESS=(PROTOCOL=TCP)(HOST=10.95.51.38)(PORT=1521)))(LOAD_BALANCE=yes)(FAILOVER=on)(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=orcl)))";
//			int i = db.connect("oracle.jdbc.driver.OracleDriver",constr, "ywjg","tyjj**2011");
//			
//			log.debug(new Date());
//			if(i!=-1)
//			{
//				@SuppressWarnings("rawtypes")
//				Map map=db.selectMap("select * from veh_admin.vehicle t where hphm='A3R252'");
//				log.debug(new Date());
//				System.out.println(map.size());
//				 
//				db.disConnect();
//			}

			
//			Properties sysProps = new Properties();
//			sysProps.put("user", "ywjg");
//			sysProps.put("password", "tyjj**2011");
//			Connection ff=DriverManager.getConnection(constr,sysProps);
//			PreparedStatement rst = ff.prepareStatement("select * from veh_admin.vehicle");
//			ResultSet res = rst.executeQuery();
//		    
//			ResultSetMetaData rsmd = res.getMetaData();
//			
//			System.out.println(rsmd.getColumnCount());
			


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	// 获取数据库连接类
//	public static Connection getConnection() throws SQLException {
//		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//		} catch (ClassNotFoundException ex) {
//			ex.printStackTrace();
//			return null;
//		}
//		Properties sysProps = new Properties();
//		sysProps.put("user", "username");
//		sysProps.put("password", "pwd");
//		return DriverManager.getConnection(
//				"jdbc:oracle:thin:@10.1.1.1:1521:orcl", sysProps);
//
//	}
//
//	/**
//	 * 测试jndi
//	 * 
//	 * @throws SQLException
//	 */
//	public void testJndi() throws SQLException {
//		// java.util.Properties p=new Properties();
//		// p.put(Context.INITIAL_CONTEXT_FACTORY,
//		// "websphere.jndi.WLInitialContextFactory");
//		// p.put(Context.PROVIDER_URL, "java:comp/env/jdbc/certdb");
//		try {
//			Context ctx = new InitialContext();
//			Context envctx = (Context) ctx.lookup("java:comp/env");
//			DataSource ds = (DataSource) envctx.lookup("jdbc/certdb");
//			if (ds != null) {
//				Connection con = ds.getConnection();
//				Statement rst = con.createStatement();
//				ResultSet res = rst.executeQuery("select * from veh_admin.veh_out");
//				ResultSetMetaData rsmd = res.getMetaData();
//				
//				System.out.println(rsmd.getColumnCount());
//			}
//			else
//			{
//				System.out.print("erroe");
//			}
//		} catch (NamingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
}
