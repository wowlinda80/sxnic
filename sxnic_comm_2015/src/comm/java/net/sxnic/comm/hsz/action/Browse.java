package net.sxnic.comm.hsz.action;

import net.sxinfo.core.dao.Page;
import net.sxinfo.core.dao.hibernate3.HibernateCriteria;
import net.sxinfo.core.dao.hibernate3.HibernateDaoUtils;
import net.sxinfo.core.dao.hibernate3.HibernateOrder;
import net.sxinfo.core.util.WebUtils;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

@SuppressWarnings("serial")
public class Browse extends HszAction {

	private Page datas;

	public String execute() throws Exception {

		try {
			int pageNumber = WebUtils.getPageNumber(request);
			int pageSize = WebUtils.getPageSize(request);

			HibernateOrder infoCodeOrder = HibernateDaoUtils
					.createHibernateOrder("creationDate", false);

			HibernateCriteria hc = new HibernateCriteria().add(infoCodeOrder);

			Criterion dc = null;

			if (StringUtils.isNotBlank(txtQuery)) {
				dc = Restrictions.like("title", "%" + txtQuery + "%");
				hc.add(dc);
			}

			datas = beanManager.getPageByCriteria(pageNumber, pageSize, hc);

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
