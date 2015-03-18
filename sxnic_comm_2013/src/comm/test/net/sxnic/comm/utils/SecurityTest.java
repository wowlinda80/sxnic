package net.sxnic.comm.utils;

import junit.framework.Assert;
import net.sxnic.comm.security.Base64Utils;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

public class SecurityTest {

	@Test
	public void testSecurity() {
		String temp = "DFFFF856CCC9642FD6ED265819082C0C".toLowerCase();
		String t = "中国人";

		try {
			String path = "http://192.168.1.154/jupload/download.php?file=";
			String id1= "18FAC8CB32D1D391D23A12D6EAE920EC.doc";
			String key = CommUtils.creCharOrNumRandom(8, 4);
			
			path = path +  Base64Utils.encode(id1, key) +"&key="+key;	
			System.out.println(path);
			
			
			String b = Base64Utils.encode(temp,"67654390");
			System.out.println(temp+"===加密之后==="+b);
			Assert.assertEquals(temp, Base64Utils.dccode(b,"67654390"));
			
			
			String a = Base64.encodeBase64URLSafeString(temp.getBytes());
			System.out.println(temp+"===url safe=="+a);
			
			System.out.println("===解密==="+new String(Base64.decodeBase64(a.getBytes())));
			
			System.out.println(temp+"==Base64加密==="+Base64.encodeBase64String(temp.getBytes()));
			
			System.out.println(temp+"==默认向量==="+Base64Utils.encode(temp));
			System.out.println(t+"==默认向量==UTF-8="+Base64Utils.encode(t));
			System.out.println(t+"==默认向量==GBK="+Base64Utils.encode(t));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
