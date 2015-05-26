package net.sxnic.comm.search.action;

import net.sxinfo.core.dao.Page;
import net.sxinfo.core.dao.hibernate3.HibernateCriteria;
import net.sxinfo.core.dao.hibernate3.HibernateDaoUtils;
import net.sxinfo.core.dao.hibernate3.HibernateOrder;
import net.sxinfo.core.util.WebUtils;
import net.sxnic.comm.CommActionSupport;
import net.sxnic.comm.CommConstant;
import net.sxnic.comm.search.Search;
import net.sxnic.comm.search.SearchManager;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 全文检索
 * 
 * @author 孙宇飞
 * 
 */
@SuppressWarnings("serial")
public class Browse extends CommActionSupport {

	private String txtQuery;

	private Page datas;

	@Autowired
	private SearchManager searchManager;

	public String execute() throws Exception {

		// 如果无检索关键字时，则不能出现查询内容
		if (StringUtils.isBlank(txtQuery)) {
			txtQuery = "FFHGHGHGHGHGHGHGHGHGH";
		}

		HibernateOrder infoCodeOrder = HibernateDaoUtils.createHibernateOrder(
				"creationDate", false);

		HibernateCriteria hc = new HibernateCriteria().add(infoCodeOrder);

		Criterion dc = null;

		int pageNumber = WebUtils.getPageNumber(request);
		int pageSize = WebUtils.getPageSize(request);

		// 所属
		dc = Restrictions.eq("belonger", Search.BELONGER_ALL);

		// 所属
		Criterion dc2 = Restrictions.eq("belonger", request.getSession()
				.getAttribute(CommConstant.APPCONTEXT_USERNAME));
		dc = Restrictions.or(dc, dc2);

		// 标题
		Criterion dc11 = Restrictions.like("name", "%" + txtQuery + "%");

		// 摘要
		Criterion dc12 = Restrictions.like("summary", "%" + txtQuery + "%");
		dc11 = Restrictions.or(dc11, dc12);

		// 内容
		dc12 = Restrictions.like("content", "%" + txtQuery + "%");
		dc11 = Restrictions.or(dc11, dc12);

		dc = Restrictions.and(dc, dc11);
		hc.add(dc);

		datas = searchManager.getPageByCriteria(pageNumber, pageSize, hc);
		
		if("FFHGHGHGHGHGHGHGHGHGH".equals(txtQuery)){
			txtQuery ="";
		}

		return SUCCESS;
	}

	public String getTxtQuery() {
		return txtQuery;
	}

	public void setTxtQuery(String txtQuery) {
		this.txtQuery = txtQuery;
	}

	public Page getDatas() {
		return datas;
	}

	public void setDatas(Page datas) {
		this.datas = datas;
	}

}
