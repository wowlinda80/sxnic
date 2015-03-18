package net.sxnic.comm.log;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import net.sxnic.comm.log.util.LogUtils;

import org.junit.Test;

import com.google.gson.Gson;

public class LogUtilsTest {

	@Test
	public void testJson() {
		String json = LogUtils.LogToExceptionJson("user1", "shenbao", "Save", "Exceptionddd");
		System.out.println(json);

		Log log = LogUtils.ExceptionJsonToLog(json);

		Assert.assertNotNull(log);
		Assert.assertEquals("user1", log.getOperator());

		Map<String, String> map = new HashMap<String, String>();
		map.put("data", json);
		map.put("status", "200");
		map.put("statusName", "成功");

		Gson gson = new Gson();

		String result = gson.toJson(map);

		Map<String, String> resultMap = gson.fromJson(result, Map.class);

		System.out.println(resultMap.get("data"));

		log = LogUtils.ExceptionJsonToLog(json);

		Assert.assertNotNull(log);
		Assert.assertEquals("Exceptionddd", log.getDetails());
		Assert.assertEquals("shenbao", log.getWebsite());
	}

}
