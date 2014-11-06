package net.sxnic.comm.property.dao;

import java.util.List;

import net.sxinfo.core.spring25.HibernateDao;
import net.sxnic.comm.property.Property;

import org.springframework.stereotype.Repository;

/**
 * PropertyDao的Hibernate实现类
 * 
 * @author 孙宇飞
 * 
 */
@Repository("propertyDao")
public class HibernatePropertyDao extends HibernateDao<Property, String>
		implements PropertyDao {

	public String getValueByName(String name) {
		Property p = this.findByUnique("propName", name);

		if (p != null) {
			return p.getPropValue();
		}

		return null;
	}
	
	public List<Property> getAllpropertys() {

		return this.find("from Property where 1=1" );
	}

	public void clear() {
		getCurrSession()
				.createQuery("delete Property").executeUpdate();

	}

	public Property getByName(String propName) {
		return this.findUnique("from Property where propName=?",
				new Object[] { propName });
	}
}
