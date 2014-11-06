package net.sxnic.comm.log;

import net.sxnic.comm.log.util.LogMigrationUtils;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml" })
public class LogMigrationUtilsTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private LogManager logManager;

	@Test
	public void testTreateLogFromDbtoFile() {
		LogMigrationUtils.treateLogFromDbtoFile(logManager);

	}
}
