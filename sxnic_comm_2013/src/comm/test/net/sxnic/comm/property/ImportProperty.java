package net.sxnic.comm.property;

import java.io.File;
import java.io.IOException;

import net.sxinfo.core.entity.EntityAlreadyExistsException;
import net.sxnic.comm.property.util.PropertyImportUtils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml" })
public class ImportProperty extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private PropertyManager propertyManager;

	private static Logger log = LoggerFactory.getLogger(ImportProperty.class);

	@Test
	public void testInitFromFile() throws IOException, EntityAlreadyExistsException {
		String filePath = System.getProperty("user.dir") + File.separator
				+ "WebContent" + File.separator + "init" + File.separator
				+ "property.txt";

		File dataFile = new File(filePath);

		PropertyImportUtils.importProperty(propertyManager, dataFile);

	}
}