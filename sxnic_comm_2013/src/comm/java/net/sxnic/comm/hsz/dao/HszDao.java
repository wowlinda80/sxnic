package net.sxnic.comm.hsz.dao;

import net.sxinfo.core.spring25.Dao;
import net.sxnic.comm.hsz.Hsz;

public interface HszDao extends Dao<Hsz,String>{
	
	/**
	 * 根据回收站Id恢复数据
	 * @param id
	 */
	void recover(String id);
}
