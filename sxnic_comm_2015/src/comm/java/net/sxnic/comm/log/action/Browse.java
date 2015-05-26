package net.sxnic.comm.log.action;

import net.sxinfo.core.dao.Page;
import net.sxinfo.core.dao.hibernate3.HibernateCriteria;
import net.sxinfo.core.dao.hibernate3.HibernateDaoUtils;
import net.sxinfo.core.dao.hibernate3.HibernateOrder;
import net.sxinfo.core.util.WebUtils;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

@SuppressWarnings("serial")
public class Browse extends LogAction {

	private Page datas;

	public String execute() throws Exception {

		try {

			page = WebUtils.getPageNumber(request);
			pageSize = WebUtils.getPageSize(request);

			HibernateOrder infoCodeOrder = HibernateDaoUtils.createHibernateOrder("creationDate", false);
			HibernateCriteria hc = new HibernateCriteria().add(infoCodeOrder);
			Criterion dc = null;
			// 组合条件
			if (StringUtils.isNotBlank(txtQuery)) {
				dc = Restrictions.like("details", "%" + txtQuery + "%");
				hc.add(dc);
			}
			datas = logManager.getPageByCriteria(page, pageSize, hc);

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
