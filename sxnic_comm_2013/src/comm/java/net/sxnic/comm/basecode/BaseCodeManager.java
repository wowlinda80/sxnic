package net.sxnic.comm.basecode;

import java.util.List;
import java.util.Map;

import net.sxinfo.core.spring25.Manager;

import org.springframework.transaction.annotation.Transactional;

/**
 * 基本码Manager接口
 * 
 * @author 孙宇飞
 * @update 2015 正式引进年份概念。开始阶段假定所有的类别都没有年的概念，如果某年某类别发生变化，则复制一份新的类别在此基础上修改
 * 
 */
@Transactional
public interface BaseCodeManager extends Manager<BaseCode, String> {

	/**
	 *  根据sortCode,cyear获取一组数据,并按infoIndex，infoCode排序
	 * 
	 * @param sortCode
	 *            类别编码
	 * @return 基本码列表
	 */
	List<BaseCode> getListBySortCodeYear(String sortCode, String cyear);

	/**
	 * 根据sortCode 删除一个类别的数据
	 * 
	 * @param sortCode
	 *            类别编码
	 */
	@Transactional(readOnly = false)
	void deleteSortBySortCode(String sortCode);

	/**
	 * 统一修改sortCode
	 * 
	 * @param newSortCode
	 *            新的类别编码
	 * @param oldSortCode
	 *            旧的类别编码
	 */
	@Transactional(readOnly = false)
	void updateSortCode(String newSortCode, String oldSortCode);

	/**
	 * 获取 编码表中所有的类别编码及名称
	 * 
	 * @return List list
	 */
	Map<String,String> getSortCodes();


	/**
	 * initialize BaseCode to map. be used in AppListener
	 */
	void init();
	
	/**
	 * initialize BaseCode to map. be used in AppListener
	 */
	void initNoYear();

	/**
	 * 根据sortCode infoCode 查询
	 * 
	 * @param sortCode
	 * @param infoCode
	 * @return 信息码实体
	 */
	BaseCode getBaseCode(String sortCode, String infoCode, String cyear);

	/**
	 * 插入新的年份。两个参数都可以为空
	 * 
	 * @param sortCode
	 *            类别码007 ，如果为空则取默认007
	 * @param currYear
	 *            当前年，如果为空则获取当前年
	 */
	@Transactional(readOnly=false)
	void insertCurrYear(String sortCode, String currYear);
	
	/**
	 * 复制指定的sortCode，到新的年份
	 * @param sortCode
	 * @param newYear
	 * @param srcYear
	 */
	@Transactional(readOnly=false)
	void copySortCode(String sortCode, String newYear,String srcYear);

}
