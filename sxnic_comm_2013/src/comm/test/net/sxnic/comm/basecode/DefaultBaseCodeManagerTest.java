package net.sxnic.comm.basecode;

import java.util.List;

import junit.framework.Assert;
import net.sxinfo.core.dao.Page;
import net.sxnic.comm.CommConstant;
import net.sxnic.comm.utils.CommUtils;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * @author 孙宇飞
 * @version 2009-2-20 Introduction ：
 */
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@ContextConfiguration(locations = { "classpath:/applicationContext_test.xml" })
public class DefaultBaseCodeManagerTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	BaseCodeManager basecodeManager;

	@Test
	public void testCreate() {

		BaseCode bc = new BaseCode("10000", "性别", "01", "男");
		BaseCode bc2 = new BaseCode("10000", "性别", "02", "女");

		basecodeManager.save(bc);
		basecodeManager.save(bc2);

		List<BaseCode> list = basecodeManager
				.getListBySortCodeOrderByInfoCode("10000");

		Assert.assertEquals(2, list.size());

	}

	@Test
	public void testInit() {

		BaseCode bc = new BaseCode("10000", "性别", "01", "男");
		BaseCode bc2 = new BaseCode("10000", "性别", "02", "女");

		basecodeManager.save(bc);
		basecodeManager.save(bc2);

		CommConstant.BASECODE_MAP = basecodeManager.init();

		Assert.assertEquals("男", BaseCodeUtils.getInfoName("10000", "01"));
	}

	@Test
	public void testGetPage() {
		BaseCode bc = new BaseCode("10000", "性别", "01", "男");
		BaseCode bc2 = new BaseCode("10000", "性别", "02", "女");

		basecodeManager.save(bc);
		basecodeManager.save(bc2);

		Page page = basecodeManager.getBasecodes(1, 2, null, true);

		Assert.assertEquals(2, page.getResults().size());

	}

	@Test
	public void testGetPageBySortCode() {
		BaseCode bc = new BaseCode("10000", "性别", "01", "男");
		BaseCode bc2 = new BaseCode("10000", "性别", "02", "女");

		basecodeManager.save(bc);
		basecodeManager.save(bc2);

		Page page = basecodeManager.getPageBySortCode(1, 2, "10000");

		Assert.assertEquals(2, page.getResults().size());
	}

	@Test
	public void testDeleteById() {
		BaseCode bc = new BaseCode("10000", "性别", "01", "男");
		BaseCode bc2 = new BaseCode("10000", "性别", "02", "女");

		basecodeManager.save(bc);
		basecodeManager.save(bc2);

		basecodeManager.delete(bc);
		basecodeManager.deleteById(bc2.getId());

		List<BaseCode> list = basecodeManager
				.getListBySortCodeOrderByInfoCode("10000");

		Assert.assertEquals(0, list.size());
	}

	@Test
	public void testDeleteSort() {
		BaseCode bc = new BaseCode("10000", "性别", "01", "男");
		BaseCode bc2 = new BaseCode("10000", "性别", "02", "女");

		basecodeManager.save(bc);
		basecodeManager.save(bc2);

		basecodeManager.deleteSortBySortCode("10000");

		List<BaseCode> list = basecodeManager
				.getListBySortCodeOrderByInfoCode("10000");

		Assert.assertEquals(0, list.size());
	}

	@Test
	public void testGetBaseCode() {
		BaseCode bc = new BaseCode("100000", "性别", "0111", "男");
		basecodeManager.save(bc);
		
		BaseCode bc2 = basecodeManager.getBaseCode("100000", "性别", "0111", "男");
		
		Assert.assertEquals(bc, bc2);

		BaseCode bc1 = basecodeManager.getBaseCode("100000", "性别", "0111", "男");

		Assert.assertNotNull(bc1);
	}
	
	@Test
	public void testDeleteSortBySortCode(){
		BaseCode bc = new BaseCode("2100000", "性别", "0111", "男");
		basecodeManager.save(bc);
		
		basecodeManager.deleteSortBySortCode("2100000");
		
		BaseCode bc1 = basecodeManager.getBaseCode("2100000", "性别", "0111", "男");
		Assert.assertNull(bc1);
	}
	
	@Test
	public void testInsertCurrYear(){
		BaseCode bc = new BaseCode("007", "年份", "2013", "2013");
		bc.setInfoIndex("100");
		basecodeManager.save(bc);
		
		BaseCode bc2 = basecodeManager.getBaseCode("007", "2013");
		
		Assert.assertEquals("100", bc2.getInfoIndex());
		//有数据插入，则排序号-1
		basecodeManager.insertCurrYear("", "");
		
		BaseCode bc1 = basecodeManager.getBaseCode("007", CommUtils.getCurrYear());
		
		Assert.assertEquals("099", bc1.getInfoIndex());
		
		//无数据插入，排序号从100开始
		basecodeManager.deleteSortBySortCode("007");
		
		basecodeManager.insertCurrYear("", "");
		bc2 = basecodeManager.getBaseCode("007", CommUtils.getCurrYear());
			
		Assert.assertEquals("100", bc2.getInfoIndex());
		
	}

}
