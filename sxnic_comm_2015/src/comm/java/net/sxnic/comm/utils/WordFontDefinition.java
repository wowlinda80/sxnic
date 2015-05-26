package net.sxnic.comm.utils;

import java.util.HashMap;
import java.util.Map;

public class WordFontDefinition {
	
	
	/**
	 * 获取WORD文档XML格式中的字体MAP
	 * @return
	 */
	public static Map<String,Map<String,String>> getFontMap(){
		
		Map<String,Map<String,String>> fontMap = new HashMap<String,Map<String,String>>();
		//宋体,小四
		Map<String,String> map1 = new HashMap<String,String>();
		map1.put("w:ascii", "宋体");
		map1.put("w:fareast", "宋体");
		map1.put("w:hint", "fareast");
		map1.put("wx:val","宋体");
		map1.put("w:val", "38");
		
		fontMap.put("01", map1);
		
		//宋体,三号
		Map<String,String> map2 = new HashMap<String,String>();
		
		map2.put("w:ascii", "宋体");
		map2.put("w:fareast", "宋体");
		map2.put("w:hint", "fareast");
		map2.put("wx:val","宋体");
		map2.put("w:val", "32");
		
		fontMap.put("02", map2);
		
		//仿宋GB_2312,四号
		Map<String,String> map3 = new HashMap<String,String>();
		map3.put("w:ascii", "仿宋_GB2312");
		map3.put("w:fareast", "仿宋_GB2312");
		map3.put("w:hint", "fareast");
		map3.put("wx:val","仿宋_GB2312");
		map3.put("w:val", "28");
		
		fontMap.put("03", map3);
		
		//仿宋GB_2312,小四
		Map<String,String> map4 = new HashMap<String,String>();
		map4.put("w:ascii", "仿宋_GB2312");
		map4.put("w:fareast", "仿宋_GB2312");
		map4.put("w:hint", "fareast");
		map4.put("wx:val","仿宋_GB2312");
		map4.put("w:val", "24");
		
		fontMap.put("04", map4);
		
		return fontMap;
	}
} 
