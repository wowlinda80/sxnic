package net.sxnic.comm.property.action;

import net.sxinfo.core.dao.Page;
import net.sxnic.comm.utils.PropertyUtil;
import net.sxinfo.core.dao.hibernate3.SortCriteria;
import net.sxinfo.core.util.WebUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Browse extends PropertyAction {

	private static final long serialVersionUID = 1L;

	private static Log log = LogFactory.getLog(Browse.class);

	private Page propertys;

	public String execute() throws Exception {
		
		try {						
			int pageNumber = WebUtils.getPageNumber(request);
			int pageSize = PropertyUtil.findTableSize("firstbrowse.tabsize");
		
			SortCriteria sortCriteria = WebUtils.getSingleSortCriteria(request);
			propertys = propertyManager.getPropertys(pageNumber, pageSize,
					sortCriteria.getPropertyName(), sortCriteria.isAscending());	 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		return SUCCESS;
	}

	public Page getPropertys() {
		return this.propertys;
	}

	public void setPropertys(Page propertys) {
		this.propertys = propertys;
	}
}
