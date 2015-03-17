package net.sxnic.comm.basecode;

import java.util.HashMap;
import java.util.Map;

import net.sxnic.comm.CommConstant;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.google.gson.Gson;

/**
 * @author 孙宇飞
 * @version 2009-2-20 Introduction ：
 */
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@ContextConfiguration(locations = { "classpath:/applicationContext_test.xml" })
public class DefaultBaseCodeManagerTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	BaseCodeManager bcManager;

	@Before
	public void clear() {
		bcManager.clear();
	}

	@Test
	public void testCRUD() {
		clear();

		BaseCode b1 = new BaseCode("007", "年份", "2015", "2015");
		BaseCode b2 = new BaseCode("007", "年份", "2016", "2016");

		bcManager.save(b1);
		bcManager.save(b2);

		Assert.assertEquals(2, bcManager.getAll().size());
	}

	@Test
	public void testDeleteSortBySortCode() {
		clear();

		BaseCode b1 = new BaseCode("007", "年份", "2015", "2015");
		BaseCode b2 = new BaseCode("007", "年份", "2016", "2016");

		bcManager.save(b1);
		bcManager.save(b2);

		bcManager.deleteSortBySortCode("007");

		Assert.assertEquals(0, bcManager.getAll().size());
	}

	@Test
	public void testUpdateSortCode() {
		clear();

		BaseCode b1 = new BaseCode("007", "年份", "2015", "2015");
		BaseCode b2 = new BaseCode("007", "年份", "2016", "2016");

		bcManager.save(b1);
		bcManager.save(b2);

		bcManager.updateSortCode("008", "007");

		Assert.assertEquals(0, bcManager.findBy("sortCode", "007").size());
		Assert.assertEquals(2, bcManager.findBy("sortCode", "008").size());
	}

	@Test
	public void testGetSortCodes() {
		clear();

		BaseCode b1 = new BaseCode("007", "年份", "2015", "2015");
		BaseCode b2 = new BaseCode("008", "年份1", "2016", "2016");

		bcManager.save(b1);
		bcManager.save(b2);

		Map<String, String> map = bcManager.getSortCodes();

		Assert.assertEquals(2, map.size());
		Assert.assertEquals("年份1", map.get("008"));
		Assert.assertTrue(map.containsKey("007"));
	}

	@Test
	public void testGetBaseCodeBySortCodeInfoCodeYear() {
		clear();

		BaseCode b1 = new BaseCode("008", "类别", "001", "工业攻关");
		b1.setCyear("2015");
		BaseCode b2 = new BaseCode("008", "类别", "002", "社会科学");
		b2.setCyear("2015");

		bcManager.save(b1);
		bcManager.save(b2);

		b1 = new BaseCode("008", "类别", "001", "工业攻关");
		b1.setCyear("2016");
		b2 = new BaseCode("008", "类别", "003", "专利推广");
		b2.setCyear("2016");

		bcManager.save(b1);
		bcManager.save(b2);

		Assert.assertNotNull(bcManager.getBaseCode("008", "001", "2015"));
	}

	@Test
	public void testCopySortCode() {
		clear();

		BaseCode b1 = new BaseCode("008", "类别", "001", "工业攻关");
		b1.setCyear("2015");
		BaseCode b2 = new BaseCode("008", "类别", "002", "社会科学");
		b2.setCyear("2015");

		bcManager.save(b1);
		bcManager.save(b2);

		// 有年份的copy
		bcManager.copySortCode("008", "2016", "2015");

		Assert.assertNotNull(bcManager.getBaseCode("008", "001", "2015"));
		Assert.assertNotNull(bcManager.getBaseCode("008", "001", "2016"));
		Assert.assertNotNull(bcManager.getBaseCode("008", "002", "2016"));

		b1 = new BaseCode("009", "类别", "001", "工业攻关");
		b2 = new BaseCode("009", "类别", "002", "社会科学");

		bcManager.save(b1);
		bcManager.save(b2);

		// 无年份的copy
		bcManager.copySortCode("009", "2016", "2015");

		Assert.assertNotNull(bcManager.getBaseCode("009", "001", "2016"));
		Assert.assertNotNull(bcManager.getBaseCode("009", "002", "2016"));

	}
	
	@Test
	public void testRebuildByYear() {
		clear();

		BaseCode b1 = new BaseCode("008", "类别", "001", "工业攻关");
		b1.setCyear("2015");
		BaseCode b2 = new BaseCode("008", "类别", "002", "社会科学");
		b2.setCyear("2015");

		bcManager.save(b1);
		bcManager.save(b2);
	}
	
	@Test
	public void testFuncGetBaseCode() {
		clear();
		
		BaseCode b1 = new BaseCode("001", "是否", "001", "是");
		BaseCode b2 = new BaseCode("001", "是否", "002", "否");

		bcManager.save(b1);
		bcManager.save(b2);

		b1 = new BaseCode("008", "类别", "001", "工业攻关");
		b1.setCyear("2015");
		b2 = new BaseCode("008", "类别", "002", "社会科学");
		b2.setCyear("2015");

		bcManager.save(b1);
		bcManager.save(b2);

		// 有年份的copy
		bcManager.copySortCode("008", "2016", "2015");

		Assert.assertNotNull(bcManager.getBaseCode("008", "001", "2015"));
		Assert.assertNotNull(bcManager.getBaseCode("008", "001", "2016"));
		Assert.assertNotNull(bcManager.getBaseCode("008", "002", "2016"));

		b1 = new BaseCode("009", "类别", "001", "工业攻关");
		b2 = new BaseCode("009", "类别", "002", "社会科学");

		bcManager.save(b1);
		bcManager.save(b2);

		// 无年份的copy
		bcManager.copySortCode("009", "2016", "2015");

		Assert.assertNotNull(bcManager.getBaseCode("009", "001", "2016"));
		Assert.assertNotNull(bcManager.getBaseCode("009", "002", "2016"));

		// 从数据库中读为Map
		bcManager.init();

		Gson gson = new Gson();
		String json = gson.toJson(CommConstant.BASECODE_YEAR_MAP);

		System.out.println("===basecode==year==json==" + json);

		// 从json再转为Map
		Map<String, Map<String,Map<String, String>>> BASECODE_YEAR_MAP = new HashMap<String, Map<String,Map<String, String>>>();
		BASECODE_YEAR_MAP = gson.fromJson(json, Map.class);
		
		Assert.assertEquals(3, BASECODE_YEAR_MAP.size());
		Assert.assertEquals(2, BASECODE_YEAR_MAP.get("008").size());
		Assert.assertEquals("否", BASECODE_YEAR_MAP.get("001").get("Y").get("002"));
		
	}
	

}
