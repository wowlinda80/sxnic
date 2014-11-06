package net.sxnic.comm.basecode;

import java.io.File;
import java.io.IOException;

import net.sxnic.comm.basecode.util.BaseCodeImportUtils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml" })
public class ImportBaseCode extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private BaseCodeManager baseCodeManager;

	private static Logger log = LoggerFactory.getLogger(ImportBaseCode.class);

	@Test
	public void testInitFromFile() throws IOException {
		String filePath = System.getProperty("user.dir") + File.separator
				+ "WebContent" +  File.separator
				+ "init" + File.separator + "basecode.txt";
System.out.println("filePath=="+filePath);
		File dataFile = new File(filePath);

		BaseCodeImportUtils.importBaseCode(baseCodeManager, dataFile);

	}
}
