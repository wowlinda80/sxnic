package net.sxnic.comm.basecode.dao;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sxinfo.core.spring25.HibernateDao;
import net.sxnic.comm.CommConstant;
import net.sxnic.comm.basecode.BaseCode;

import org.springframework.stereotype.Repository;

@Repository("basecodeDao")
public class HibernateBaseCodeDao extends HibernateDao<BaseCode, String>
		implements BaseCodeDao {


	public void updateSortCode(String newSortCode, String oldSortCode) {
		getCurrSession().createQuery(
				"update BaseCode set sortCode='" + newSortCode
						+ "' where sortCode = '" + oldSortCode + "'")
				.executeUpdate();
	}

	public void deleteSortBySortCode(String sortCode) {
		getCurrSession().createQuery(
				"delete from BaseCode where sortCode = '" + sortCode + "'")
				.executeUpdate();
	}

	

	public Map<String,String> getSortCodes() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		List list = find("select distinct sortCode,sortName from BaseCode order by sortCode");

		Iterator iter = list.iterator();
		Object[] sortCode ;
		while (iter.hasNext()) {
			sortCode = (Object[])iter.next();
			map.put(sortCode[0].toString(),sortCode[1].toString());
		}

		return map;
	}

	@Override
	public Map<String, Map<String, String>> getBySortCode(String sortCode) {
		
		Map<String, Map<String, String>> map = new LinkedHashMap<String, Map<String,String>>();
		Map<String, String> subMap = new LinkedHashMap<String, String>();
		
		//先判断sortCode有无年的概念
		List<BaseCode> list = find("select distinct cyear from BaseCode where sortCode='"+sortCode+"' order by cyear");
		if(list ==null || list.size()==0){
			List<BaseCode> infoList = find("from BaseCode where sortCode=? order by infoIndex asc,infoCode asc", new Object[]{sortCode});
			
			if(infoList ==null || infoList.size()==0){
				subMap = new LinkedHashMap<String, String>();
				for(BaseCode bc:infoList){
					subMap.put(bc.getInfoCode(), bc.getInfoName());
				}				
				
				map.put(CommConstant.SORT_YEAR, subMap);				
			}			
		}else{
			Iterator iter = list.iterator();
			List<BaseCode> infoList;
			while(iter.hasNext()){
				String fyear = (String)iter.next();
				infoList = find("from BaseCode where sortCode=? and cyear=? order by infoIndex asc,infoCode asc", new Object[]{sortCode,fyear});
				if(infoList !=null && infoList.size()>0){
					subMap = new LinkedHashMap<String, String>();
					for(BaseCode bc:infoList){
						subMap.put(bc.getInfoCode(), bc.getInfoName());
					}				
					
					map.put(fyear, subMap);	
				}				
			}
		}		
		
		return map;				
	}

	

}
