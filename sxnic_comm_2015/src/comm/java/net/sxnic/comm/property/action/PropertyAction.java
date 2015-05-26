package net.sxnic.comm.property.action;

import net.sxnic.comm.CommActionSupport;
import net.sxnic.comm.property.Property;
import net.sxnic.comm.property.PropertyManager;

import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("serial")
public abstract class PropertyAction extends CommActionSupport {

	protected Property property;

	@Autowired
	protected PropertyManager propertyManager;

	public Property getProperty() {
		return this.property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	public PropertyManager getPropertyManager() {
		return this.propertyManager;
	}

	public void setPropertyManager(PropertyManager propertyManager) {
		this.propertyManager = propertyManager;
	}
}
