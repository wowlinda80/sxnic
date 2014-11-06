package net.sxnic.comm.log;

import net.sxnic.comm.basecode.BaseCode;
import net.sxnic.comm.basecode.BaseCodeManager;
import net.sxnic.comm.utils.CommUtils;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@ContextConfiguration(locations = { "classpath:/applicationContext_test.xml" })
public class DefaultLogManagerTest extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Autowired
	private LogManager logManager;

	@Autowired
	private BaseCodeManager basecodeManager;
	
	@Test
	public void testCRUD() throws Exception{
		
		BaseCode bc = new BaseCode("1","1","1","1","1","1");
		
		BaseCode temp =new BaseCode();

		basecodeManager.save(bc);

		temp = (BaseCode)BeanUtils.cloneBean(bc);	
		
		BaseCode bc1 = basecodeManager.get(bc.getId());
		
		bc1.setInfoCode("2222");
		basecodeManager.update(bc1);
		
		Log log = new Log();
		
		log.setOperator("2");
		log.setOperation(Log.LOG_OPERATION_UPDATE);
		log.setOldObj(CommUtils.getBytesFromObject(temp));
		log.setNewObj(CommUtils.getBytesFromObject(bc1));
		
		logManager.save(log);
		
		System.out.println(((BaseCode)CommUtils.getObjectFromBytes(log.getOldObj())).getInfoCode());
		System.out.println(((BaseCode)CommUtils.getObjectFromBytes(log.getNewObj())).getInfoCode());
		 
	}
	
}
