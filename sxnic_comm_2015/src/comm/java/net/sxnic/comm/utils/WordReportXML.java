/**
 * <p>Title: 打印</p>
 * <p>Description: 对xml格式的word文档进行操作,实现网络打印功能</p>
 * <p>Copyright: Copyright (c) 山西省网络管理中心2007</p>
 * <p>Company: sxnic</p>
 * @author 舒强 shuqiang@sxinfo.net
 * @version 1.0
 */
package net.sxnic.comm.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
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

@SuppressWarnings({"unchecked","unused"})
public class WordReportXML implements JavaToWord {
	private Element DomRoot;// xml文档的根节点
	private Element DomSubRoot; // 子xml文挡的根节点

	private Map ElementMap = new HashMap();// 存放标签的名称，标签元素
	private Map BookMarkMap = new HashMap();// 存放标签的名称，标签的内容
	int intBMPoint = 0;
	Element elmBookMark;

	/**
	 * 构造函数
	 * 
	 */
	public WordReportXML() {
	}

	/**
	 * 规定标签的名称和内容
	 * 
	 * @param bookmarkname
	 *            标签名称
	 * @param content
	 *            对应的标签内容
	 */
	public void fillBookmarkText(String bookmarkname, String[] content) {
		BookMarkMap.put(bookmarkname, content);
	}

	public void fillBookmarkText(String bookmarkname, String content) {
		BookMarkMap.put(bookmarkname, content);
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
	public void readTemleteURL(String strXMLURL) {
		SAXReader reader = new SAXReader();
		try {
			// 从文件读取xml，输入文件名，返回xml文档
			Document doc = reader.read(new URL(strXMLURL));
			// 取得root节点，熟悉xml人都知道，一切xml分析都是从root元素开始的
			DomRoot = doc.getRootElement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void readTemleteFile(String strXMLURL) {
		SAXReader reader = new SAXReader();
		try {
			// 从文件读取xml，输入文件名，返回xml文档
			Document doc = reader.read(new File(strXMLURL));
			// 取得root节点，熟悉xml人都知道，一切xml分析都是从root元素开始的
			DomRoot = doc.getRootElement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从文件读取XML，输入文件名，返回XML文档
	 * 
	 * @param strXMLURL
	 *            文件名（要指出文件的全路径）
	 */
	public void readSubTemlete(String strXMLURL) {
		SAXReader reader = new SAXReader();
		try {
			// 从文件读取xml，输入文件名，返回xml文档
			Document doc = reader.read(new File(strXMLURL));
			// 取得root节点，熟悉xml人都知道，一切xml分析都是从root元素开始的
			DomSubRoot = doc.getRootElement();
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
	public Element appendNewNode(Element element) {
		Element parent = element.getParent();
		Element newEle = parent.addElement("w:r");
		Element ele = newEle.addElement("w:t");
		return ele;
	}

	public void appendTblNewNode(Element element) {
		Element newEle = element.addElement("w:r");

		Element ele = newEle.addElement("w:t");
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
	public Element appendNewNodeObj(Element element, int j) {
		Element parent = element.getParent();
		Element newEle = parent.addElement("w:r");
		Element ele = newEle.addElement("w:t");

		Element grandparent = parent.getParent();
		for (int i = 0; i < j - 1; i++) {
			Element ep = grandparent.addElement("w:p");
			Element er = ep.addElement("w:r");
			Element et = er.addElement("w:t");
		}
		return grandparent;
	}

	/**
	 * 动态生成空报表
	 * 
	 * @param strXMLURL
	 *            输出文件的路径
	 */
	public void createReport(String strXMLURL) {
		this.findBookMarks();
		this.showElementMap();
		Set keySet = ElementMap.keySet();
		Iterator it = keySet.iterator();
		Element element = null;
		while (it.hasNext()) {
			String strKey = (String) it.next();
			if (strKey.indexOf("_table") >= 0) {
				element = (Element) ElementMap.get(strKey);
				Element trEle = null;
				String[] col = (String[]) BookMarkMap.get(strKey);
				Element wp = element.getParent();
				Element tc = wp.getParent();
				Element tr = tc.getParent();
				int tcNum = tr.selectNodes(".//w:tc").size();
				int trNum = col.length / tcNum;

				Element tbl = tr.getParent();
				List trList = tbl.selectNodes(".//w:tr");
				Iterator trIt = trList.iterator();
				while (trIt.hasNext()) {
					trEle = (Element) trIt.next();
				}
				if (trNum > 2) {
					for (int ii = 0; ii < trNum - 2; ii++) {
						Element copyTr = tbl.addElement("w:tr1");
						List elepar = copyTr.getParent().content();
						elepar.set(elepar.indexOf(copyTr), trEle);
					}
				}
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

	/**
	 * 创建xml文件
	 * 
	 * @param strXMLURL
	 *            输出文件的路径
	 */
	public void createNewXML(String strXMLURL) {
		this.findBookMarks();
		this.showElementMap();
		Set keySet = ElementMap.keySet();
		Iterator it = keySet.iterator();
		String content = "";
		Element element = null;
		while (it.hasNext()) {
			String strKey = (String) it.next();
			if (strKey.indexOf("_clob") >= 0) {
				content = (String) BookMarkMap.get(strKey);
				if (content != null) {
					if (content.split("\n").length > 1) {
						int j = 0;
						element = (Element) ElementMap.get(strKey);
						Element nextElement = getNextNode(element);
						if (nextElement == null) {
							element = appendNewNodeObj(element, content
									.split("\n").length);
						}

						List list = element.selectNodes(".//w:t");
						Iterator ite = list.iterator();
						while (ite.hasNext()) {
							element = (Element) ite.next();
							element.setText(content.split("\n")[j]);
							if (j + 1 < content.split("\n").length)
								j++;
						}
					} else {
						element = (Element) ElementMap.get(strKey);
						Element nextElement = getNextNode(element);
						if (nextElement == null) {
							element = appendNewNode(element);
						} else {
							element = getWtByWr(nextElement);
						}
						if (BookMarkMap.get(strKey) == null)
							element.setText("");
						else
							element.setText(content + element.getText());
					}
				}

			}
			if (strKey.indexOf("_txt") >= 0) {
				content = (String) BookMarkMap.get(strKey);
				element = (Element) ElementMap.get(strKey);
				Element nextElement = getNextNode(element);
				if (nextElement == null) {
					element = appendNewNode(element);
				} else {
					element = getWtByWr(nextElement);
				}
				if (BookMarkMap.get(strKey) == null)
					element.setText("");
				else
					element.setText(content + element.getText());
			}

			if (strKey.indexOf("_list") >= 0) {
				System.out.println("list");
			}
			if (strKey.indexOf("_table") >= 0) {
				element = (Element) ElementMap.get(strKey);
				String[] col = (String[]) BookMarkMap.get(strKey);
				Element wp = element.getParent();
				Element tc = wp.getParent();
				Element tr = tc.getParent();
				int tcNum = tr.selectNodes(".//w:tc").size();
				int trNum = col.length / tcNum + 1;

				Element tbl = tr.getParent();

				List tblwp = tbl.selectNodes(".//w:p");

				Iterator wpIt = tblwp.iterator();
				int i = 0;
				while (wpIt.hasNext()) {

					Element wpEle = (Element) wpIt.next();
					if (i > tcNum - 1 && i < tcNum * trNum) {
						appendTblNewNode(wpEle);
					}
					i++;
				}
				List tblwt = tbl.selectNodes(".//w:t");
				int j = 0;
				int l = 0;
				Iterator wtIt = tblwt.iterator();
				while (wtIt.hasNext()) {
					Element wtEle = (Element) wtIt.next();
					if (j > tcNum - 1) {
						if (l < col.length) {
							if (col[l] == null) {
								col[l] = "";
							}
							wtEle.setText(col[l]);
							l++;
						}
					}
					j++;

				}
			}
			if (strKey.indexOf("_file") >= 0) {
				element = (Element) ElementMap.get(strKey);
				List elepar = element.getParent().getParent().content();
				elepar.set(elepar.indexOf(element.getParent()), this
						.copyTableNode());
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

	// 复制表格节点
	public Element copyTableNode() {
		Element element = (Element) DomSubRoot
				.selectSingleNode("//w:body/wx:sect/w:tbl");
		return element;
	}

}
