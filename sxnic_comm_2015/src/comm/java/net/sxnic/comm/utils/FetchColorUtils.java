package net.sxnic.comm.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author 张国业  2011-11-10
 * @version
 */
public class FetchColorUtils {

	private static Log log = LogFactory.getLog(FetchColorUtils.class);

	private static String[] colorArray = {"c3e1f9","b9a4cf","b5474a", "40a9e0", 
		 "fff68d", "82a35c", "a974aa", "e07376", "40aaaa", "ffa974", "a9cb40", "f8cd4b",
		 "990033", "006633", "CCCC00", "660033", "999933", "660099", "993366", "333399", "666633"};

	public static String getColorByIndex(int index){
		
		if(index < colorArray.length)
			return colorArray[index];
		else
			return "000000";
		
	}
	
	public static final String[] YJ_LIGHT_COLOR = {"FF0000 ","FFD306","8CEA00","BEBEBE"};
}
