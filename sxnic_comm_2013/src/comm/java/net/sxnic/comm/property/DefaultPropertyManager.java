package net.sxnic.comm.property;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sxinfo.core.dao.Page;
import net.sxinfo.core.entity.EntityAlreadyExistsException;
import net.sxinfo.core.spring25.Dao;
import net.sxinfo.core.spring25.DefaultManager;
import net.sxnic.comm.CommConstant;
import net.sxnic.comm.property.dao.PropertyDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * PropertyManager的默认实现
 * 
 * @version $Revision$
 * @author 曹浩
 */
@Service("propertyManager")
public class DefaultPropertyManager extends DefaultManager<Property, PropertyDao, String> implements PropertyManager {

	@Autowired
	private PropertyDao propertyDao;

	/**
	 * @see net.sxinfo.application.property.PropertyManager#init()
	 */
	public Map<String,String> init() {

		Map<String, String> properties = new HashMap<String, String>();

		List<Property> props = propertyDao.getAllpropertys();
		 
		for (Property p : props) {
			properties.put(p.getPropName().trim(), p.getPropValue().trim());
		}

		return  properties;
	}

	/**
	 * @see net.sxinfo.application.property.PropertyManager#getAllProperties()
	 */
	public List<Property> getAll() {
		return propertyDao.getAll();
	}

	/**
	 * @see net.sxinfo.application.property.PropertyManager#create(net.sxinfo.application.property.Property)
	 */
	public void create(Property property) throws EntityAlreadyExistsException {
		propertyDao.save(property);
		CommConstant.PROPERTY_MAP.put(property.getPropName(), property
				.getPropValue());
	}

	/**
	 * @see net.sxinfo.application.property.PropertyManager#update(net.sxinfo.application.property.Property)
	 */
	public void update(Property property) {
		CommConstant.PROPERTY_MAP.remove(property.getPropName());
		propertyDao.update(property);
		CommConstant.PROPERTY_MAP.put(property.getPropName(), property
				.getPropValue());
	}

	/**
	 * @see net.sxinfo.application.property.PropertyManager#delete(net.sxinfo.application.property.Property)
	 */
	public void delete(Property property) {
		propertyDao.delete(property);
		CommConstant.PROPERTY_MAP.remove(property.getPropName());
	}

	public void deleteProperty(String[] ids) {
		for (String id : ids) {
			propertyDao.deleteById(id);
		}
	}

	public Property getProperty(String id) {
		return propertyDao.get(id);
	}

	public Page getPropertys(int page, int pageSize, String orderProperty,
			boolean asc) {
		return propertyDao.getPage(page, pageSize, orderProperty, asc);
	}

	public void setPropertyDao(PropertyDao propertyDao) {

		this.propertyDao = propertyDao;

	}

	public void clear() {
		propertyDao.clear();
	}

	public String getValueByName(String name) {
		return CommConstant.PROPERTY_MAP.get(name);
	}

	public Property getByName(String propName) {
		return propertyDao.getByName(propName);
	}

	@Override
	protected Dao<Property, String> getEntityDao() {
		return propertyDao;
	}
}
