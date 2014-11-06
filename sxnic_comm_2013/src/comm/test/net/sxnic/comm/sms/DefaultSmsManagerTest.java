package net.sxnic.comm.sms;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import net.sxnic.comm.CommSpringJunitTest;

public class DefaultSmsManagerTest extends CommSpringJunitTest{
	
	@Autowired
	private SmsManager smsManager;

	@Test
	public void testCRUD(){
		smsManager.findLastestUnReadedByReceiver("syf");
		
	}
}
