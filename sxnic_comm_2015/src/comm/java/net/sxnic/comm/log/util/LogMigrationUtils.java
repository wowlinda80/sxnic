package net.sxnic.comm.log.util;

import java.io.File;
import java.util.Date;

import net.sxnic.comm.log.LogManager;
import net.sxnic.comm.utils.DataMigrationUtils;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 日志迁移工具类
 * 
 * @author 孙宇飞
 * 
 */
public class LogMigrationUtils {

    public static void treateLogFromDbtoFile(LogManager logManager) {

	String logFilePath = net.sxnic.comm.utils.PropertyUtil.getLogFilePaht() + "log" + File.separator
		+ DateFormatUtils.format(new Date(), "yyyy-MM-dd") + ".xml";

	// 备份数据到xml文件
	File destFile = new File(logFilePath);

	if (!destFile.getParentFile().isDirectory()) {
	    File temp = new File(net.sxnic.comm.utils.PropertyUtil.getLogFilePaht() + "log");
	    temp.mkdir();
	    // FileUtils.createFile(logFilePath);
	}

	try {
	    DataMigrationUtils.systemDataBackup(destFile, new String[] { "Log" });
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	// 删除原有数据
    }
    
    
    public static void treatLogFromDbtoDb(String cate){
    	//cate 默认为001 ，如果等于002则需要转移重要日志内容
    	//TODO
    }

    
}
