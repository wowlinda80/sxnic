package net.sxnic.comm.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

import junit.framework.Assert;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

public class CommUtilsTest {

	@Test
	public void testSplitTrim() {

		String temp = CommUtils.splitTrim("1 , 2,3, 4, 5", ",");

		Assert.assertEquals("1,2,3,4,5", temp);
	}

	@Test
	public void testSubstring() {

		String str1 = "001002";

		int len = str1.length();

		Assert.assertEquals("001", StringUtils.substring(str1, 0, len - 3));
		
		Assert.assertEquals(true,CommUtils.isHanzi("晋"));
	}

	@Test
	public void testUUU() {
		String u = "U";

		Assert.assertEquals(1, u.length());
		Assert.assertTrue(u.startsWith("U"));
		
		String[] strs =StringUtils.split("basezbAdmin,object_admin,net.sxkh.basezb.BaseZb,*", ",");
		System.out.println(strs[0]);
		System.out.println(strs.length);
	}

	@Test
	public void testCalTwoDate() throws UnsupportedEncodingException {
		Date d = new Date();
		System.out.println(CommUtils.calTwoDate(
				CommUtils.FormatStringToDate("2011/05/14", "yyyy/MM/dd"),
				CommUtils.FormatStringToDate("2011/04/14", "yyyy/MM/dd")));
		System.out.println(CommUtils.FormatDateToString(
				DateUtils.addDays(new Date(), -1), "yyyy/MM/dd"));

		Assert.assertEquals("2", CommUtils.fatchDayFromDate("2000/01/02"));
		Assert.assertEquals("9", CommUtils.fatchMonthFromDate("2000/09/02"));

		System.out.println(CommUtils.FormatDateToString(new Date(),
				"yyyyMMdd_HHmmss_SSS"));

//		try {
//			URL url = new URL(
//					"http://10.95.63.23/back/2011-04-12/41115/3/16-19pm/4.20110412.173030625.41115.3.3.晋A7M275.02.0.jpg");
//			//FileUtils.copyURLToFile(url, new File("c:\\2.jpg"));
//			System.out.println(url.getHost());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		System.out.println(CommUtils.getFormatStr("1000",4));
		
		System.out.println(2000 % 1000);
		
		String temps = "1 , 2, 3, 4, , , 8, 9  ,10";
		System.out.println(temps.trim());
		System.out.println(StringUtils.trim(temps));
		System.out.println(StringUtils.trimToEmpty(temps));
		System.out.println(StringUtils.trimToNull(temps));
		System.out.println(ArrayUtils.indexOf(StringUtils.split(temps, ","), " 4"));
		String[] ts = StringUtils.split(temps, ",");
		
		System.out.println(StringUtils.isBlank(ts[5]));
		System.out.println(ts[5].trim());
		
		String encode = URLEncoder.encode("xxxx.jspa?id=123456","GBK");
		System.out.println(encode);
		 encode = URLEncoder.encode("xxxx.jspa?id=123456","UTF-8");
		System.out.println(encode);
		System.out.println(URLDecoder.decode(encode));
		
		
		System.out.println(System.getProperty("os.name"));
		System.out.println(System.getProperty("os.name").toLowerCase().contains("windows"));
		
		System.out.println(StringUtils.removeStart("/2/2/1/1/1", "/"));
		
	}

}
