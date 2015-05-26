package net.sxnic.comm;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;


/**
 * 通用的Spring Junit测试父类
 * @author 孙宇飞
 *
 */
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml" })
public class CommSpringJunitTest extends AbstractTransactionalJUnit4SpringContextTests{

}
