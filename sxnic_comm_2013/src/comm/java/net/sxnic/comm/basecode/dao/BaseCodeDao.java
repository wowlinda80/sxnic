package net.sxnic.comm.basecode.dao;

import java.util.List;
import java.util.Map;

import net.sxinfo.core.dao.Page;
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
	 * 查询一个sortCode相关的List ，并且按orderField排序，默认按
	 * @param sortCode
	 * @param orderField 取值infoCode或infoIndex
	 * @return
	 */
	List getListBySortCode(String sortCode, String orderField);
	
	/**
	 * 此方法暂未启用
	 * @param sortCode
	 * @param infoIndex
	 * @param orderField
	 * @return
	 */
	List getListBySortCodeInfoIndex(String sortCode,String infoIndex, String orderField);

	/**
	 * 查询所有编码的分页
	 * @return
	 */
	Page getAllCodes();

	/**
	 * 按sortCode删除
	 * @param sortCode
	 */
	void deleteSortBySortCode(String sortCode);

	/**
	 * 初始化Map
	 * @return
	 */
	Map<String, List> initMap();

	/**
	 * 清空所有数据，慎用！
	 */
	void clearTable();

	/**
	 * 查询所有的SortCode列表
	 * @return
	 */
	List getSortCodes();
	
	/**
	 * 按Id删除实体
	 * @param id
	 */
	void deleteById(String id);
	
	/**
	 * 查询所有数据的实体列表
	 */
	List<BaseCode> getAll();
	
	/**
	 * 查询分页类
	 * @param page
	 * @param pageSize
	 * @param orderProperty
	 * @param asc
	 * @return
	 */
	Page getBasecodesOrderBySortCode(int page, int pageSize, String orderProperty, boolean asc);
	/**
	 * 按sortCode查询分页类
	 * @param page
	 * @param pageSize
	 * @param sortCode
	 * @return
	 */
	Page getPageBySortCode(int page, int pageSize,String sortCode);
	
	/**
	 * 根据sortCode查询Map
	 * @param sortCode
	 * @return
	 */
	Map<String,String> getInfoMap(String sortCode);
	
	/**
	 * 根据条件查询实体
	 * @param sortCode
	 * @param sortName
	 * @param infoCode
	 * @param infoName
	 * @return
	 */
	BaseCode getBaseCode(String sortCode,String sortName,String infoCode,String infoName);
	
	/**
	 * 根据条件查询实体
	 * @param sortCode
	 * @param infoCode
	 * @return
	 */
	BaseCode getBaseCode(String sortCode,String infoCode);
	
}
