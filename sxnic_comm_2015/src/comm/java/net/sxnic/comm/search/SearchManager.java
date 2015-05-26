package net.sxnic.comm.search;

import org.springframework.transaction.annotation.Transactional;

import net.sxinfo.core.spring25.Manager;

@Transactional
public interface SearchManager extends Manager<Search,String>{
	
	/**
	 * 根据 实体ID和所有者查询
	 * @param entityId 实体ID 可为空
	 * @param belonger 所有者 不可为空
	 * @return 实体类
	 */
	Search findByEntityIdBelonger(String entityId,String belonger);

}
