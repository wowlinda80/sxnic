package net.sxnic.comm.hsz;

import org.springframework.transaction.annotation.Transactional;

import net.sxinfo.core.spring25.Manager;

@Transactional
public interface HszManager extends Manager<Hsz, String> {
	
	/**
	 * 根据回收站Id恢复数据
	 * @param id
	 */
	void recover(String id);

}
