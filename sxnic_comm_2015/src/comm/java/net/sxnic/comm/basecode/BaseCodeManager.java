package net.sxnic.comm.basecode;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import net.sxinfo.core.dao.Page;
import net.sxinfo.core.spring25.Manager;

import org.springframework.transaction.annotation.Transactional;
/**
 * 基本码Manager接口
 * @author 孙宇飞
 *
 */
@Transactional
public interface BaseCodeManager extends Manager<BaseCode, String> {

	/**
	 * 获取一组数据 根据sortCode排序
	 * 
	 * @param sortCode 类别编码
	 * @return 基本码列表
	 */
	List<BaseCode> getListBySortCodeOrderByInfoCode(String sortCode);

	/**
	 * 获取一组数据 根据order排序
	 * 
	 * @param sortCode 类别编码
	 * @return 基本码列表
	 */
	List<BaseCode> getListBySortCodeOrderByorder(String sortCode);

	/**
	 * 根据ID获取一条数据
	 * 
	 * @param id 
	 * @return 基本码实体类
	 */
	BaseCode getById(String id);


	/**
	 * 根据sortCode 删除一个类别的数据
	 * 
	 * @param sortCode 类别编码
	 */
	@Transactional(readOnly = false)
	void deleteSortBySortCode(String sortCode);

	/**
	 * 统一修改sortCode
	 * 
	 * @param newSortCode 新的类别编码
	 * @param oldSortCode 旧的类别编码
	 */
	@Transactional(readOnly = false)
	void updateSortCode(String newSortCode, String oldSortCode);

	/**
	 * 初始化 基本信息表 到 MAP
	 * 
	 * @return map
	 */
	Map<String, List> initSet();

	/**
	 * 清空表
	 */
	void clearTable();

	/**
	 * 获取 编码表中所有的类别编码及名称
	 * 
	 * @return List list
	 */
	List getSortCodes();


	/**
	 * 依据 sortCode 和 year 获取一组数据。 先按order 排序，然后再按infoCode排序
	 * 
	 * @param sortCode 类别编码
	 * @param year 年份
	 * @return 基本码列表
	 */
	List<BaseCode> getListBySortCodeYear(String sortCode, String year);

	/**
	 * 按照xml文件路径，导入基本码信息
	 * @param xmlPath 文件路径
	 */
	void inputFromXml(String xmlPath);

	/**
	 * 从InputStream导入
	 * @param is
	 */
	void inputFromIoStream(InputStream is);

	/**
	 * 导出到XML文件
	 * @param xmlPath
	 */
	void outputToXml(String xmlPath);

	/**
	 * 此方法暂时不用
	 */
	void outputToString();

	/**
	 * initialize BaseCode to map. be used in AppListener
	 */
	Map<String, Map<String, String>> init();

	/**
	 * refresh the map
	 */
	Map<String, Map<String, String>> refresh();

	/**
	 * search the infoName by sortCode & infoCode
	 * 
	 * @param sortCode 类别编码
	 * @param infoCode 信息码
	 * @return
	 */
	String getInfoName(String sortCode, String infoCode);

	/**
	 * search the infoName by sortCode & infoCode & year
	 * 
	 * @param sortCode 类别编码
	 * @param infoCode 信息码
	 * @return
	 */
	String getInfoName(String sortCode, String infoCode, String year);

	/**
	 * 分页显示，如果默认没有排序设置，则按sortCode，infoCode排序
	 * 
	 * @param page 页码
	 * @param pageSize 每页显示总量
	 * @param orderProperty 人工设置排序字段
	 * @param asc 排序字段的升降序
	 * @return 分页信息
	 */
	Page getBasecodes(int page, int pageSize, String orderProperty, boolean asc);

	/**
	 * 分页显示，如果默认没有排序设置，则按sortCode，infoCode排序
	 * 
	 * @param page 页码
	 * @param pageSize 每页显示总量
	 * @param orderProperty 人工设置排序字段
	 * @param asc 排序字段的升降序
	 * @return 分页信息
	 */
	Page getBasecodesOrderBySortCode(int page, int pageSize,
			String orderProperty, boolean asc);

	/**
	 * 按sortCode分页显示，默认infoCode排序，如果sortCode为空则用getBasecodesOrderBySortCode方法替代
	 * 
	 * @param page 页码
	 * @param pageSize 每页显示总量
	 * @return 信息
	 */
	Page getPageBySortCode(int page, int pageSize, String sortCode);

	/**
	 * 验证重复数据
	 * 
	 * @param sortCode
	 * @param sortName
	 * @param infoCode
	 * @param infoName
	 * @return 如果有重复数据则会返回一个BaseCode对象，否则返回null
	 */
	BaseCode getBaseCode(String sortCode, String sortName, String infoCode,
			String infoName);
	
	/**
	 * 根据sortCode infoCode 查询
	 * @param sortCode
	 * @param infoCode
	 * @return 信息码实体
	 */
	BaseCode getBaseCode(String sortCode,String infoCode);
	
	/**
	 * 获取一组数据 根据sortCode排序
	 * 
	 * @param sortCode 类别编码
	 * @param infoIndex 子类别编码
	 * @return 信息码列表
	 */
	List<BaseCode> getListBySortCodeInfoIndexOrderByInfoCode(String sortCode ,String infoIndex);
	
	/**
	 * 插入新的年份。两个参数都可以为空
	 * @param sortCode 类别码007 ，如果为空则取默认007
	 * @param currYear 当前年，如果为空则获取当前年
	 */
	void insertCurrYear(String sortCode,String currYear);
	
	
}
