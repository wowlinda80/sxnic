package net.sxnic.comm.feedback;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;
import net.sxnic.comm.utils.CommUtils;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml" })
public class DefaultFeedbackManagerTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private FeedbackManager feedbackManager;

	@Test
	public void testCURD() throws IOException {

		Feedback f = new Feedback();
		f.setReporter("reporter");

		feedbackManager.save(f);

		Feedback f1 = feedbackManager.get(f.getId());

		Assert.assertEquals(f, f1);

		f.setBcontent(FileUtils
				.readFileToByteArray(createTestFile("test1.dat")));

		feedbackManager.save(f);

		f1 = feedbackManager.get(f.getId());

		createTestFile1("test2.dat", f1.getBcontent());
	}

	private File createTestFile(String fileName) throws IOException {
		File file = null;
		if (CommUtils.isLinuxOs()) {
			file = new File("/tmp/" + fileName);
		} else if (CommUtils.isWindowsOs()) {
			file = new File("d:\\temp\\" + fileName);
		}
		FileUtils.writeStringToFile(file, "test file.");

		return file;
	}

	private File createTestFile1(String fileName, byte[] data)
			throws IOException {
		File file = null;
		if (CommUtils.isLinuxOs()) {
			file = new File("/tmp/" + fileName);
		} else if (CommUtils.isWindowsOs()) {
			file = new File("d:\\temp\\" + fileName);
		}
		FileUtils.writeByteArrayToFile(file, data);

		return file;
	}

}
