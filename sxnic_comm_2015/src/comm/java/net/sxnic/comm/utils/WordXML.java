package net.sxnic.comm.utils;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * 操作xml摸板生成打印文件
 * 
 * @version V1.00
 * @author 舒强
 * 
 */
@SuppressWarnings("unchecked")
public class WordXML {

	private static Map<String, Map<String, String>> FontMap = new HashMap<String, Map<String, String>>();
	protected Element DomRoot;// xml文档的根结点
	private Map ElementMap = new HashMap();// 存放标签的名称，标签元素
	private Map BookMarkMap = new HashMap();// 存放标签的名称，标签的内容
	private Map<String, Map<String, String>> FontMarkMap = new HashMap<String, Map<String, String>>(); // 存放标签的字体
	int intBMPoint = 0;
	Element elmBookMark;

	/**
	 * 构造函数
	 * 
	 */
	public WordXML() {
		FontMap = WordFontDefinition.getFontMap();
	}

	/**
	 * 规定标签的名称和内容
	 * 
	 * @param bookmarkname
	 *            标签名称
	 * @param content
	 *            对应的标签内容
	 */
	public void fillBookmarkText(String bookmarkname, String content) {
		BookMarkMap.put(bookmarkname, content);
		FontMarkMap.put(bookmarkname, FontMap.get(bookmarkname.substring(
				bookmarkname.length() - 2, bookmarkname.length())));
	}

	/**
	 * 查找标签
	 * 
	 */
	public void findBookMarks() {
		findBookMarks(DomRoot);
	}

	/**
	 * 查找标签
	 * 
	 * @param element
	 *            xml根元素
	 */
	public void findBookMarks(Element element) {
		// @在这里的运用，查找所有Word标签元素，dom4j对XPATH有良好的支持,这里应该很好的了解一下XPATH的语法
		// 这里的"//aml:annotation[@w:type='Word.Bookmark.Start']"表示所有属性为w:type='Word.Bookmark.Start'的aml:annotation元素
		List listNode = element
				.selectNodes("//aml:annotation[@w:type='Word.Bookmark.Start']");
		Iterator it = listNode.iterator();
		while (it.hasNext()) {
			Element elem = (Element) it.next();
			ElementMap.put(elem.attributeValue("name"), elem);
		}
	}

	/**
	 * 通过HashMap把ElementMap和BookMarkMap连起来
	 * 
	 */
	public void showElementMap() {
		Set keySet = ElementMap.keySet();
		Iterator it = keySet.iterator();
		Element element = null;
		while (it.hasNext()) {
			String strKey = (String) it.next();
			element = (Element) ElementMap.get(strKey);

		}
	}

	/**
	 * 得到包含标签内容的节点元素的父节点r
	 * 
	 * @param element
	 * @return
	 */
	public Element getNextNode(Element element) {
		Element parent = element.getParent();// 得到父节点<w:p>
		Iterator it = parent.elementIterator();// 枚举所有子节点<w:pPr><w:r><w:r><w:r><aml:annotation><aml:annotation><w:r>
		int dirtyInt = 1;
		while (it.hasNext()) {
			Element curE = (Element) it.next();
			if (dirtyInt != 1) {
				if (curE.getName().equalsIgnoreCase("r"))
					return curE;// 返回最后一个节点元素，其中包含标签的内容
			}
			if (curE.equals(element)) {
				// 当节点元素为<aml:annotation
				// w:type="Word.Bookmark.Start">时，dirthInt开始自减
				dirtyInt--;
			}
		}
		return null;
	}

	/**
	 * 得到包含标签内容的节点元素t
	 * 
	 * @param wr
	 *            父节点
	 * @return
	 */
	public Element getWtByWr(Element wr) {
		// 找到写标签内容的节点<w:t>
		Iterator it = wr.elementIterator();// 枚举所有子节点<w:rPr><w:t>
		while (it.hasNext()) {
			Element element = (Element) it.next();
			if (element.getName().equalsIgnoreCase("t")) {
				return element;
			}
		}
		return null;
	}

	/**
	 * 从文件读取XML，输入文件名，返回XML文档
	 * 
	 * @param strXMLURL
	 *            文件名（要指出文件的全路径）
	 */
	public void readTemlete(String strXMLURL) {
		SAXReader reader = new SAXReader();
		try {
			// 从文件读取xml，输入文件名，返回xml文档
			// Document doc = reader.read(new URL(strXMLURL));

			Document doc = reader.read(new String(strXMLURL));
			// 取得root节点，熟悉xml人都知道，一切xml分析都是从root元素开始的
			DomRoot = doc.getRootElement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 增加新节点
	 * 
	 * @param element
	 * @return
	 */
	public Element appendNewNode(Element element, String strKey) {
		Element parent = element.getParent();
		Element newEle = parent.addElement("w:r");
		this.addAttribute(newEle, strKey);
		Element ele = newEle.addElement("w:t");
		return ele;
	}

	/**
	 * 为大字段增加节点
	 * 
	 * @param element
	 *            标签元素
	 * @param j
	 *            增加节点的个数
	 * @return
	 */
	public Element appendNewNodeObj(Element element, int j, String strKey) {
		// Element parent= element.getParent();
		// Element newEle= parent.addElement("w:r");
		// this.addAttribute(newEle,strKey);
		// Element ele= newEle.addElement("w:t");
		//  
		// Element grandparent = parent.getParent();
		// for (int i=0;i<j-1;i++)
		// {
		// Element ep = grandparent.addElement("w:p");
		// Element eppr = ep.addElement("w:pPr");
		// Element eSnap = eppr.addElement("w:snapToGrid");
		// eSnap.addAttribute("w:val", "off");
		// Element er = ep.addElement("w:r");
		// this.addAttribute(er,strKey);
		// Element et = er.addElement("w:t");
		// }

		Element parent = element.getParent();
		Element newEle = parent.addElement("w:r");
		this.addAttribute(newEle, strKey);
		Element ele = newEle.addElement("w:t");

		for (int i = 0; i < j - 1; i++) {
			Element esplitr = parent.addElement("w:r");
			Element esplitbr = esplitr.addElement("w:br");
			Element er = parent.addElement("w:r");
			this.addAttribute(er, strKey);
			Element et = er.addElement("w:t");
		}
		return parent;
	}

	/**
	 * 规定标签内容的字体
	 * 
	 */
	public void addAttribute(Element element, String strKey) {
		Map<String, String> map = FontMarkMap.get(strKey);
		if (map != null) {
			Element e = element.addElement("w:rPr");
			Element erFonts = e.addElement("w:rFonts");
			erFonts.addAttribute("w:ascii", map.get("w:ascii"));
			erFonts.addAttribute("w:fareast", map.get("w:fareast"));
			erFonts.addAttribute("w:hint", map.get("w:hint"));
			Element eFont = e.addElement("wx:font");
			eFont.addAttribute("wx:val", map.get("wx:val"));
			Element esz = e.addElement("w:sz");
			esz.addAttribute("w:val", map.get("w:val"));
		}
	}

	/**
	 * 创建xml文件
	 * 
	 * @param strXMLURL
	 *            输出文件的路径
	 */
	public void createNewXML(String strXMLURL) {
		Set keySet = ElementMap.keySet();
		Iterator it = keySet.iterator();
		String content = "";
		Element element = null;
		while (it.hasNext()) {
			String strKey = (String) it.next();
			if ((String) BookMarkMap.get(strKey) != null)
				content = (String) BookMarkMap.get(strKey);
			if ((strKey.indexOf("clob") >= 0)
					&& (content.split("\n").length > 1)) {
				int j = 0;
				element = (Element) ElementMap.get(strKey);
				Element nextElement = getNextNode(element);
				if (nextElement == null) {
					element = appendNewNodeObj(element,
							content.split("\n").length, strKey);
				}
				List list = element.selectNodes(".//w:t");
				Iterator ite = list.iterator();
				while (ite.hasNext()) {
					element = (Element) ite.next();
					element.setText(content.split("\n")[j]);
					j++;
				}

			} else {
				element = (Element) ElementMap.get(strKey);
				Element nextElement = getNextNode(element);
				if (nextElement == null) {
					element = appendNewNode(element, strKey);
				} else {
					element = getWtByWr(nextElement);
				}
				if (BookMarkMap.get(strKey) == null)
					element.setText("");
				else
					element.setText(content + element.getText());
			}
		}

		Document doc = element.getDocument();
		try {
			OutputFormat format = OutputFormat.createCompactFormat();
			if (CommUtils.isWindowsOs()) {
				format.setEncoding("GBK");
			} else if (CommUtils.isLinuxOs()) {
				format.setEncoding("UTF-8");
			}
			XMLWriter writer = new XMLWriter(new FileOutputStream(strXMLURL),
					format);
			writer.write(doc);
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		WordXML wordXml = new WordXML();
		wordXml.readTemlete("e://test.xml");
		wordXml.fillBookmarkText("shuq01", "恭恭敬敬恭恭敬敬恭恭敬敬恭恭敬敬111");
		wordXml.fillBookmarkText("shuq02", "恭恭敬敬恭恭敬敬恭恭敬敬恭恭敬敬222");
		wordXml.findBookMarks();
		wordXml.showElementMap();
		// 生成xml文件
		wordXml.createNewXML("e://temp//test.doc");

	}

	public Map getFontMarkMap() {
		return FontMarkMap;
	}

	public void setFontMarkMap(Map fontMarkMap) {
		FontMarkMap = fontMarkMap;
	}

	public Element getDomRoot() {
		return DomRoot;
	}

	public void setDomRoot(Element domRoot) {
		DomRoot = domRoot;
	}

}
