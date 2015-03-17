package net.sxnic.comm.basecode.dao;

import java.util.Map;

import net.sxinfo.core.spring25.Dao;
import net.sxnic.comm.basecode.BaseCode;

public interface BaseCodeDao extends Dao<BaseCode,String> {
	
	/**
	 * 更新BaseCode类的SortCode
	 * @param newSortCode 
	 * @param oldSortCode
	 */
	void updateSortCode(String newSortCode, String oldSortCode);
	
	
	/**
	 * 按sortCode删除
	 * @param sortCode
	 */
	void deleteSortBySortCode(String sortCode);
	
	/**
	 * 获取 编码表中所有的类别编码及名称，到Map
	 * 
	 * @return List list
	 */
	Map<String,String> getSortCodes();
	
	Map<String,Map<String,String>> getBySortCode(String sortCode);
	
	
}
