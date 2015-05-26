package net.sxnic.comm.log;

import java.util.List;

import net.sxinfo.core.dao.Page;
import net.sxinfo.core.spring25.Manager;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface LogManager extends Manager<Log,String>{
	
	/**
	 * 根据操作者查询
	 * @param userName 
	 * @return
	 */
	List<Log> getLogsByUserName(String userName);
	
	/**
	 * 根据操作者查询分页类
	 * @param userName
	 * @return
	 */
	Page getPageByUserName(String userName);

}
