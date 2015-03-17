package net.sxnic.comm.basecode.action;

import java.util.Map;

import net.sxinfo.core.dao.Page;
import net.sxinfo.core.dao.hibernate3.HibernateCriteria;
import net.sxinfo.core.dao.hibernate3.HibernateDaoUtils;
import net.sxinfo.core.dao.hibernate3.HibernateOrder;
import net.sxinfo.core.util.WebUtils;
import net.sxnic.comm.utils.PropertyUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

@SuppressWarnings("serial")
public class Browse extends BaseCodeAction {

	private Page datas;

	private Map sortMap;

	public String execute() throws Exception {

		try {
			int pageNumber = WebUtils.getPageNumber(request);
			int pageSize = PropertyUtil.findTableSize("firstbrowse.tabsize");

			HibernateOrder order = HibernateDaoUtils.createHibernateOrder("infoIndex", true);
			HibernateCriteria hc = new HibernateCriteria().add(order);
			order = HibernateDaoUtils.createHibernateOrder("infoCode", true);
			hc.add(order);

			Criterion dc = null;

			if (StringUtils.isNotBlank(sortCode)) {
				dc = Restrictions.eq("sortCode", sortCode);
				hc.add(dc);
			}

			if (StringUtils.isNotBlank(cyear)) {
				dc = Restrictions.eq("cyear", cyear);
				hc.add(dc);
			}

			datas = basecodeManager.getPageByCriteria(pageNumber, pageSize, hc);

			sortMap = basecodeManager.getSortCodes();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	public Map getSortMap() {
		return sortMap;
	}

	public void setSortMap(Map sortMap) {
		this.sortMap = sortMap;
	}

	public Page getDatas() {
		return datas;
	}

	public void setDatas(Page datas) {
		this.datas = datas;
	}

}
