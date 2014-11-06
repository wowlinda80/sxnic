package net.sxnic.comm.basecode.action;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sxinfo.core.dao.Page;
import net.sxinfo.core.dao.hibernate3.SortCriteria;
import net.sxinfo.core.util.WebUtils;
import net.sxnic.comm.utils.PropertyUtil;

import org.apache.commons.lang.StringUtils;

@SuppressWarnings({"unchecked","serial"})
public class Browse extends BaseCodeAction {


	private Page basecodes;

	private Map sortMap;

	public String execute() throws Exception {
		
		try {						
			int pageNumber = WebUtils.getPageNumber(request);
			int pageSize = PropertyUtil.findTableSize("firstbrowse.tabsize");
		
			if (!StringUtils.isBlank(sortCode) && !"00".equals(sortCode)) {
				basecodes = basecodeManager.getPageBySortCode(pageNumber, pageSize,
						sortCode);
				 
			} else {
				SortCriteria sortCriteria = WebUtils.getSingleSortCriteria(request);
		
				basecodes = basecodeManager.getBasecodes(pageNumber, pageSize,
						sortCriteria.getPropertyName(), sortCriteria.isAscending());
				
				 
			}
		
			sortMap = treatSortListToMap(basecodeManager.getSortCodes());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		
		return SUCCESS;
	}
	
	public Map treatSortListToMap(List list){
		Iterator iter = list.iterator();
		Map map = new LinkedHashMap();
		while(iter.hasNext()){
			Object[] strs = (Object[])iter.next();
			map.put(strs[0], strs[1]);
		}
		return map;
	}

	public Page getBasecodes() {
		return this.basecodes;
	}

	public void setBasecodes(Page basecodes) {
		this.basecodes = basecodes;
	}

	public Map getSortMap() {
		return sortMap;
	}

	public void setSortMap(Map sortMap) {
		this.sortMap = sortMap;
	}



	
}
