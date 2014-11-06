package net.sxnic.comm.property.dao;

import java.util.List;

import net.sxinfo.core.spring25.Dao;
import net.sxnic.comm.property.Property;

public interface PropertyDao extends Dao<Property, String> {

	/**
	 * 清空属性表
	 */
	void clear();

	/**
	 * 查询所有的属性表
	 * @return 属性列表
	 */
	List<Property> getAllpropertys();

	/**
	 * 根据属性key查询value
	 * 
	 * @param name
	 *            属性key
	 * @return value
	 */
	String getValueByName(String name);

	/**
	 * 更新实体类
	 */
	void update(Property property);

	/**
	 * 根据名字查询一条属性数据
	 * 
	 * @param name
	 *            属性key
	 * @return 属性实体
	 */
	Property getByName(String propName);

}
