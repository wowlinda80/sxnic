package net.sxnic.comm.basecode;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sxinfo.core.dao.Page;
import net.sxinfo.core.spring25.Dao;
import net.sxinfo.core.spring25.DefaultManager;
import net.sxnic.comm.CommConstant;
import net.sxnic.comm.basecode.dao.BaseCodeDao;
import net.sxnic.comm.utils.CommUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("basecodeManager")
@SuppressWarnings("unchecked")
public class DefaultBaseCodeManager extends
		DefaultManager<BaseCode, BaseCodeDao, String> implements
		BaseCodeManager {

	@Autowired
	private BaseCodeDao basecodeDao;

	
	public List<BaseCode> getListBySortCodeOrderByInfoCode(String sortCode) {
		return basecodeDao.getListBySortCode(sortCode, "infoCode");
	}
	
	public List<BaseCode> getListBySortCodeInfoIndexOrderByInfoCode(String sortCode ,String infoIndex) {
		return basecodeDao.getListBySortCodeInfoIndex(sortCode,infoIndex, "infoCode");
	}
 

	public List<BaseCode> getListBySortCodeOrderByorder(String sortCode) {
		return basecodeDao.getListBySortCode(sortCode, "orderd");
	}

	public void create(BaseCode baseCode) {
		basecodeDao.save(baseCode);
	}

	public void delete(BaseCode baseCode) {
		basecodeDao.delete(baseCode);
	}

	public void update(BaseCode baseCode) {
		basecodeDao.update(baseCode);
	}

	public void update(List<BaseCode> list) {
		Iterator iter = list.iterator();
		BaseCode basecode;
		while (iter.hasNext()) {
			basecode = (BaseCode) iter.next();
			basecodeDao.update(basecode);
		}
	}

	public void deleteSortBySortCode(String sortCode) {
		basecodeDao.deleteSortBySortCode(sortCode);
	}

	public Map<String, List> initSet() {
		return basecodeDao.initMap();
	}

	public void updateSortCode(String newSortCode, String oldSortCode) {
		basecodeDao.updateSortCode(newSortCode, oldSortCode);
	}

	public void clearTable() {
		this.basecodeDao.clearTable();
	}

	public void setBasecodeDao(BaseCodeDao basecodeDao) {
		this.basecodeDao = basecodeDao;
	}

	public BaseCode getById(String id) {
		return basecodeDao.get(id);
	}

	public List getSortCodes() {
		return basecodeDao.getSortCodes();
	}

	public void deleteById(String id) {
		basecodeDao.deleteById(id);
	}

	public void save(BaseCode bc) {
		basecodeDao.save(bc);
	}

	public List<BaseCode> getListBySortCodeYear(String sortCode, String year) {
		// TODO Auto-generated method stub
		return null;
	}

	public void inputFromIoStream(InputStream is) {
		// TODO Auto-generated method stub

	}

	public void inputFromXml(String xmlPath) {
		// TODO Auto-generated method stub

	}

	public void outputToString() {
		// TODO Auto-generated method stub

	}

	public void outputToXml(String xmlPath) {
		// TODO Auto-generated method stub

	}

	public String getInfoName(String sortCode, String infoCode) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getInfoName(String sortCode, String infoCode, String year) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, Map<String, String>> init() {
		return refresh();
	}

	public Map<String, Map<String, String>> refresh() {
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();

		List<BaseCode> sortList = basecodeDao.getSortCodes();

		if (sortList == null) {
			return null;
		}

		Iterator iter = sortList.iterator();
		while (iter.hasNext()) {
			Object[] obj = (Object[]) iter.next();
			map.put((String) obj[0], basecodeDao.getInfoMap((String) obj[0]));
		}

		return map;
	}

	public Page getBasecodes(int page, int pageSize, String orderProperty,
			boolean asc) {
		return basecodeDao.getBasecodesOrderBySortCode(page, pageSize,
				orderProperty, asc);
	}

	public Page getBasecodesOrderBySortCode(int page, int pageSize,
			String orderProperty, boolean asc) {
		return basecodeDao.getBasecodesOrderBySortCode(page, pageSize,
				orderProperty, asc);
	}

	public Page getPageBySortCode(int page, int pageSize, String sortCode) {
		return basecodeDao.getPageBySortCode(page, pageSize, sortCode);
	}

	public BaseCode getBaseCode(String sortCode, String sortName,
			String infoCode, String infoName) {

		return basecodeDao.getBaseCode(sortCode, sortName, infoCode, infoName);
	}

	public void clear() {
		basecodeDao.clearTable();
	}

	@Override
	protected Dao<BaseCode, String> getEntityDao() {
		return basecodeDao;
	}

	public BaseCode getBaseCode(String sortCode, String infoCode) {
		return basecodeDao.getBaseCode(sortCode, infoCode);
	}

	@Override
	public void insertCurrYear(String sortCode, String currYear) {
		if(StringUtils.isBlank(sortCode)){
			sortCode="007";
		}
		
		if(StringUtils.isBlank(currYear)){
			currYear=CommUtils.getCurrYear();
		}
		
		//先检测是否有当前年
		BaseCode bc = this.getBaseCode(sortCode, currYear);
		
		if(bc==null){
			//最小的排序号
			String newIndex = "100";
			List<BaseCode> list = basecodeDao.findBySql("from BaseCode where sortCode=? order by infoIndex asc", new Object[]{sortCode});
			if(list ==null || list.size()==0){
				newIndex = "100";
			}else{
				newIndex = list.get(0).getInfoIndex();
				newIndex = String.valueOf(Integer.parseInt(newIndex)-1);
				newIndex = CommUtils.getFormatStr(newIndex, 0, 3);
			}
			
			bc = new BaseCode();
			bc.setSortCode(sortCode);
			bc.setSortName("年份");			
			bc.setInfoCode(currYear);
			bc.setInfoName(currYear);
			bc.setInfoIndex(newIndex);
			
			basecodeDao.save(bc);
			
			//刷新系统变量
			CommConstant.BASECODE_MAP = init();
		}
	}

}
