package net.sxnic.comm.attachment.action;

import net.sxinfo.core.dao.Page;
import net.sxinfo.core.dao.hibernate3.HibernateCriteria;
import net.sxinfo.core.dao.hibernate3.HibernateDaoUtils;
import net.sxinfo.core.dao.hibernate3.HibernateOrder;
import net.sxinfo.core.util.WebUtils;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

@SuppressWarnings("serial")
public class Browse extends AttachAction {

	private Page datas;

	public String execute() throws Exception {

		try {
			int page = WebUtils.getPageNumber(request);
			int pageSize = WebUtils.getPageSize(request);

			if (StringUtils.isBlank(txtQuery)) {
				datas = attachmentManager.getPage(page, pageSize, "creationDate", false);
			} else {
				
				HibernateOrder order = HibernateDaoUtils.createHibernateOrder(
						"id", false);
				HibernateCriteria hc = new HibernateCriteria().add(order);

				Criterion dc = null;

				if(StringUtils.isNotBlank(txtQuery)){
					dc = Restrictions.like("oriName", "%"+txtQuery+"%");
					hc.add(dc);
				}

				datas = attachmentManager.getPageByCriteria(page, pageSize, hc);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}
	
	public Page getDatas() {
		return datas;
	}

	public void setDatas(Page datas) {
		this.datas = datas;
	}

}