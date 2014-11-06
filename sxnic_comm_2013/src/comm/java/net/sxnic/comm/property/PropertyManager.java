package net.sxnic.comm.property;

import java.util.Map;

import net.sxinfo.core.dao.Page;
import net.sxinfo.core.entity.EntityAlreadyExistsException;
import net.sxinfo.core.spring25.Manager;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PropertyManager extends Manager<Property, String> {

	/**
	 * 初始化一个属性Map，一般在系统启动时运行
	 * 
	 * @return
	 */
	Map<String, String> init();
	
	/**
	 * 根据属性key查询value
	 * @param name 属性key
	 * @return value
	 */
	String getValueByName(String name);

	/**
	 * 创建实体类
	 * @param property 实体类
	 * @throws EntityAlreadyExistsException
	 */
	void create(Property property) throws EntityAlreadyExistsException;

	/**
	 * 更新实体类
	 */
	void update(Property property);

	/**
	 * 分页查询
	 * 
	 * @param page
	 *            当前页
	 * @param pageSize
	 *            每页显示数量
	 * @param orderProperty
	 *            排序字段
	 * @param asc
	 *            升序or 降序
	 * @return 分页信息
	 */
	Page getPropertys(int page, int pageSize, String orderProperty, boolean asc);

	/**
	 * 根据名字查询一条属性数据
	 * 
	 * @param name 属性key
	 * @return 属性实体
	 */
	Property getByName(String propName);
}
