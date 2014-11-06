package net.sxnic.comm.utils;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aspose.words.Bookmark;
import com.aspose.words.BookmarkCollection;
import com.aspose.words.Document;

/**
 * Word 操作工具类
 * 参考文档
 * http://www.aspose.com/docs/display/wordsnet/Moving+the+Cursor 
 * @author CGD
 * 
 */
public class WordUtils {

	static {
		Document.setLicence("sj_laokai");
	}
    
	/**
	 * 设置单标签
	 * @param doc
	 * @param bookmarkName
	 * @param text
	 * @throws Exception
	 */
	public static void setBookmark(Document doc,String bookmarkName,String text) throws Exception{
		Bookmark bookmark=doc.getRange().getBookmarks().get(bookmarkName);
		if (bookmark!=null){
			bookmark.setText(text);
		}
	}
	
	/**
	 * 用实体属性设置标签
	 * @param doc
	 * @param obj
	 * @throws Exception
	 */
	public static void setBookmarks(Document doc,Object obj) throws Exception{
		if (obj==null){
			return;
		}
		BookmarkCollection list= doc.getRange().getBookmarks();
		for(int i=0;i<list.getCount();i++){
			String name=list.get(i).getName();
			//System.out.println("bookmark:"+name);
			//处理字段格式
			String attr=capFirst(WordUtils.underlineToCap(name));
		    Object text=WordUtils.getter(obj, attr);
		    if (text!=null){
		    	list.get(i).setText(text.toString());
		    }
		}
	}
	
	/**
	 * 通过get方法获取属性
	 * @param obj
	 * @param att
	 * @return
	 */
	public static Object getter(Object obj, String att) {
		try {			
			Method method = obj.getClass().getMethod("get" + att);
			if (method!=null){
				return method.invoke(obj);
			}
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 转化为驼峰式
	 * 
	 * @param oldString
	 * @return
	 */
	public static String underlineToCap(String oldString) {
		if (null == oldString || "".equals(oldString.trim())) {
			return "";
		}
		String[] subs = oldString.split("_");
		if (1 == subs.length) {
			oldString = oldString.replaceAll("_", "");
			return String.valueOf(oldString.charAt(0)).toUpperCase()
					+ oldString.substring(1);
		}
		StringBuffer newString = new StringBuffer();
		for (String sub : subs) {
			if (sub.equals("")) {
				continue;
			}
			try {
				newString.append(String.valueOf(sub.charAt(0)).toUpperCase()
						+ sub.substring(1).toLowerCase());
			} catch (Exception e) {
				System.out.println(oldString);

			}
		}
		return newString.toString();
	}

	/**
	 * 首字母小写
	 * 
	 * @param string
	 * @return
	 */
	public static String uncapFirst(String string) {
		String s = String.valueOf(string.charAt(0)).toLowerCase();
		s = s + string.substring(1);
		return s;
	}

	/**
	 * 首字母大写
	 * 
	 * @param string
	 * @return
	 */
	public static String capFirst(String string) {
		String s = String.valueOf(string.charAt(0)).toUpperCase();
		s = s + string.substring(1);
		return s;
	}
	/**
	 * 格式化日期
	 * @param format
	 * @param date
	 * @return
	 */
	public static String formatDate(String format,Date date){
		if (date==null){
			return "";
		}
		SimpleDateFormat time=new SimpleDateFormat(format);
		return time.format(date);
	}

}
