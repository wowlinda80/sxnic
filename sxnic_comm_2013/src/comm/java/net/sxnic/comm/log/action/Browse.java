package net.sxnic.comm.log.action;

/**
 *  
 *
 * @version $Revision$
 * @author 吕盛槐  2011-10-05
 */
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import net.sxinfo.core.dao.Page;
import net.sxinfo.core.dao.hibernate3.HibernateCriteria;
import net.sxinfo.core.dao.hibernate3.HibernateDaoUtils;
import net.sxinfo.core.dao.hibernate3.HibernateOrder;
import net.sxnic.comm.utils.PropertyUtil;
import net.sxinfo.core.util.WebUtils;

@SuppressWarnings("serial")
public class Browse extends LogAction {

	private Page logs;
	
	/**
	 * 按描述模糊查询
	 */
	private String txtname;

	public String execute() throws Exception {
		
		 try {	
			    System.out.println("txtname======="+txtname);
			 int page = WebUtils.getPageNumber(request);
				int pageSize = PropertyUtil.findTableSize("firstbrowse.tabsize");
				
				HibernateOrder infoCodeOrder = HibernateDaoUtils.createHibernateOrder(
						"creationDate", false);
				HibernateCriteria hc = new HibernateCriteria().add(infoCodeOrder);
				Criterion dc = null;
				// 组合条件
				if (StringUtils.isNotBlank(txtname)) {
					dc = Restrictions.like("details", "%" + txtname + "%");
					hc.add(dc);
				}
				logs = logManager.getPageByCriteria(page, pageSize, hc);
				
				 
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
		
		
		return SUCCESS;
	}

	public Page getLogs() {
		return logs;
	}

	public void setLogs(Page logs) {
		this.logs = logs;
	}

	public String getTxtname() {
		return txtname;
	}

	public void setTxtname(String txtname) {
		this.txtname = txtname;
	}
	
	

}
