package net.sxnic.comm.basecode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sxinfo.core.dao.hibernate3.HibernateCriteria;
import net.sxinfo.core.dao.hibernate3.HibernateDaoUtils;
import net.sxinfo.core.dao.hibernate3.HibernateOrder;
import net.sxinfo.core.spring25.Dao;
import net.sxinfo.core.spring25.DefaultManager;
import net.sxnic.comm.CommConstant;
import net.sxnic.comm.basecode.dao.BaseCodeDao;
import net.sxnic.comm.utils.CommUtils;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("basecodeManager")
public class DefaultBaseCodeManager extends DefaultManager<BaseCode, BaseCodeDao, String> implements BaseCodeManager {

	@Autowired
	private BaseCodeDao basecodeDao;

	@Override
	protected Dao<BaseCode, String> getEntityDao() {
		return basecodeDao;
	}

	@Override
	public void insertCurrYear(String sortCode, String currYear) {
		if (StringUtils.isBlank(sortCode)) {
			sortCode = "007";
		}

		if (StringUtils.isBlank(currYear)) {
			currYear = CommUtils.getCurrYear();
		}

		// 先检测是否有当前年
		List<BaseCode> bcs = this.getListBySortCodeYear(sortCode, currYear);

		if (bcs == null || bcs.size() == 0) {
			// 最小的排序号
			String newIndex = "100";
			List<BaseCode> list = basecodeDao.findBySql("from BaseCode where sortCode=? order by infoIndex asc",
					new Object[] { sortCode });
			if (list == null || list.size() == 0) {
				newIndex = "100";
			} else {
				newIndex = list.get(0).getInfoIndex();
				newIndex = String.valueOf(Integer.parseInt(newIndex) - 1);
				newIndex = CommUtils.getFormatStr(newIndex, 0, 3);
			}

			BaseCode bc = new BaseCode();
			bc.setSortCode(sortCode);
			bc.setSortName("年份");
			bc.setInfoCode(currYear);
			bc.setInfoName(currYear);
			bc.setInfoIndex(newIndex);

			basecodeDao.save(bc);

			// 刷新系统变量
			init();
		}
	}

	@Override
	public List<BaseCode> getListBySortCodeYear(String sortCode, String cyear) {

		if (StringUtils.isBlank(sortCode)) {
			return null;
		}

		HibernateOrder order = HibernateDaoUtils.createHibernateOrder("infoIndex", true);
		HibernateCriteria hc = new HibernateCriteria().add(order);
		order = HibernateDaoUtils.createHibernateOrder("infoCode", true);
		hc.add(order);

		Criterion dc = null;

		// 没有年直接按sortCode查询 返回
		if (StringUtils.isBlank(cyear)) {
			dc = Restrictions.eq("sortCode", sortCode);
			hc.add(dc);

			return basecodeDao.getByCriteria(hc);
		}

		// 先验证有无sortCode year对应的编码，如果有则返回，如果没有则只查询sortCode
		dc = Restrictions.eq("cyear", cyear);
		hc.add(dc);

		dc = Restrictions.eq("sortCode", sortCode);
		hc.add(dc);

		List<BaseCode> bcs = basecodeDao.getByCriteria(hc);

		if (bcs != null && bcs.size() > 0) {
			return bcs;
		} else {
			return getListBySortCodeYear(sortCode, "");
		}
	}

	@Override
	public void deleteSortBySortCode(String sortCode) {
		basecodeDao.deleteSortBySortCode(sortCode);
	}

	@Override
	public void updateSortCode(String newSortCode, String oldSortCode) {
		basecodeDao.updateSortCode(newSortCode, oldSortCode);
	}

	@Override
	public Map<String, String> getSortCodes() {
		return basecodeDao.getSortCodes();
	}

	@Override
	public void init() {
		CommConstant.BASECODE_YEAR_MAP = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String, String> sortCodeMap = getSortCodes();
		for (String sortCode : sortCodeMap.keySet()) {
			CommConstant.BASECODE_YEAR_MAP.put(sortCode, basecodeDao.getBySortCode(sortCode));
		}
	}

	@Override
	public BaseCode getBaseCode(String sortCode, String infoCode, String cyear) {
		HibernateOrder order = HibernateDaoUtils.createHibernateOrder("infoIndex", true);
		HibernateCriteria hc = new HibernateCriteria().add(order);
		order = HibernateDaoUtils.createHibernateOrder("infoCode", true);
		hc.add(order);

		Criterion dc = null;

		if (StringUtils.isNotBlank(sortCode)) {
			dc = Restrictions.eq("sortCode", sortCode);
			hc.add(dc);
		}

		if (StringUtils.isNotBlank(infoCode)) {
			dc = Restrictions.eq("infoCode", infoCode);
			hc.add(dc);
		}

		if (StringUtils.isNotBlank(cyear)) {
			dc = Restrictions.eq("cyear", cyear);
			hc.add(dc);
		}

		List<BaseCode> bcs = basecodeDao.getByCriteria(hc);

		if (bcs == null || bcs.size() == 0) {
			return null;
		} else {
			return bcs.get(0);
		}
	}

	@Override
	public void copySortCode(String sortCode, String newYear, String srcYear) {
		List<BaseCode> list = getListBySortCodeYear(sortCode, srcYear);
		
		BaseCode newBc ;
		for(BaseCode bc:list){
			newBc = new BaseCode(sortCode,bc.getSortName(),bc.getInfoCode(),bc.getInfoName());
			newBc.setCyear(newYear);
			newBc.setInfoIndex(bc.getInfoIndex());
			
			basecodeDao.save(newBc);
		}		
	}
}
