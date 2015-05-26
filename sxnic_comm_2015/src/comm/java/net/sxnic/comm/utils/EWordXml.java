package net.sxnic.comm.utils;

import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * comment:
 * 
 * @author wangm
 * @version 创建时间: 2009-12-19
 */
public class EWordXml extends WordXML {

	/**
	 *  rect 备用
	 * 
	 * @param text
	 */

	@SuppressWarnings("unchecked")
	public void fillrtextbox(String id, String text) {

		Element e = (Element) DomRoot.selectSingleNode("//v:rect[@id='" + id
				+ "']");
		if (e != null) {
			Node node = e
					.selectSingleNode(".//v:textbox/w:txbxContent/w:p/w:r");
			if (node == null) {
				Element wp = (Element) e
						.selectSingleNode(".//v:textbox/w:txbxContent/w:p");
				Element wrpr = (Element) wp.selectSingleNode(".//w:pPr/w:rPr");
				Element wr = wp.addElement("w:r");
				wr.add(wrpr.createCopy());
				Element wt = wr.addElement("w:t");
				wt.setText(text);

			} else
				e.selectSingleNode(".//v:textbox/w:txbxContent/w:p/w:r/w:t")
						.setText(text);
		}
		// List<Element>
		// elist=DomRoot.selectNodes("//v:rect");//v:textbox/w:txbxContent/w:p
		// System.out.print(elist.size());
		// Element temp=null;
		// for(Element t:elist)
		// {
		//
		// Node
		// node=t.selectSingleNode(".//v:textbox/w:txbxContent/w:p/w:r/w:t");
		// if(node!=null)
		// {
		// if(StringUtils.isBlank(node.getText()))
		// {
		// node.setText(text);
		// }
		// temp=((Element)t.selectNodes(".//v:textbox/w:txbxContent/w:p/w:r").get(0)).createCopy();
		// }
		// else
		// {
		// t.add(temp);
		// t.selectSingleNode(".//w:r/w:t").setText(text);
		// }
		// }
	}
    /*
     * shape 在用
     */
	public void fillstextbox(String id, String text) {
		Element e = (Element) DomRoot.selectSingleNode("//v:shape[@id='" + id
				+ "']");
		if (e != null) {
			Node node = e
					.selectSingleNode(".//v:textbox/w:txbxContent/w:p/w:r");
			if (node == null) {
				Element wp = (Element) e
						.selectSingleNode(".//v:textbox/w:txbxContent/w:p");
				Element wr = wp.addElement("w:r");
				Element wrpr = (Element) wp.selectSingleNode(".//w:pPr/w:rPr");
				if (wrpr != null)
					wr.add(wrpr.createCopy());
				Element wt = wr.addElement("w:t");
				wt.setText(text);

			} else
				e.selectSingleNode(".//v:textbox/w:txbxContent/w:p/w:r/w:t")
						.setText(text);
		}
	}

	public String getBookMark(String bookname)
	{
		String context="";
		Element e=(Element)DomRoot.selectSingleNode("//aml:annotation[@w:name='"+bookname+"']");
		Element eparent=e.getParent();
		Iterator it=eparent.selectNodes(".//w:r/w:t").iterator();
		while(it.hasNext())
		{
			Node node=(Node)it.next();
			context=node.getText();
		}
		
		return context;
	}
	public void readTemlete(String strXMLURL) {
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(new String(strXMLURL));
			DomRoot = doc.getRootElement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void setBookmarktext(String markname,String marktext)
	{
		Element e=(Element)DomRoot.selectSingleNode("//aml:annotation[@w:name='"+markname+"']");
		Element eparent=e.getParent();
		
		Iterator it=eparent.elementIterator();
        while(it.hasNext())
        {
          Element ex=(Element)it.next();
          if(ex.attributeValue("w:type").equals("Word.Bookmark.End"))
          {
        	  
          }
        }
		List list=eparent.selectNodes(".//w:r");
		if(list.size()<1)
		{
			
		}
	}
	/*
	 * 在原来的基础上
	 */
	public void createNewXML1(String strXMLURL) {
		
		try {
			OutputFormat format = OutputFormat.createCompactFormat();
			if (CommUtils.isWindowsOs()) {
				format.setEncoding("GBK");
			} else if (CommUtils.isLinuxOs()) {
				format.setEncoding("UTF-8");
			}
			XMLWriter writer = new XMLWriter(new FileOutputStream(strXMLURL),
					format);
			writer.write(DomRoot.getDocument());
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public static void main(String[] args) {
		EWordXml wordxml = new EWordXml();
		wordxml.readTemlete("e://test1.doc");
		wordxml.fillstextbox("miji", "密级1");
		wordxml.fillstextbox("jinji", "紧急程度");
		wordxml.fillstextbox("fwh", "晋科办发［2009］61号");
		wordxml.fillstextbox("title", "关于印发《山西省科技厅关于深入开展民主评议政风行风工作的实施方案》的通知");
		wordxml.fillstextbox("qfr", "测试人");

//		wordxml.fillBookmarkText("ztc", "政风行风  评议  方案  印发  通知");
//		wordxml.fillBookmarkText("zs", "各市科技局、厅直属各单位:");
//		wordxml.fillBookmarkText("cbjg", "省政府行评办。");
//		wordxml.fillBookmarkText("csjg", "抄送机构");
//		wordxml.fillBookmarkText("fa", "厅领导、机关各处室。");
//		wordxml.fillBookmarkText("yfjg", "山西省科学技术厅办公室");
//		wordxml.fillBookmarkText("fj1", "附件:1、《山西省科技厅关于深入开展民主评议政风行风工作 的实施方案");
//
//		wordxml.findBookMarks();
		wordxml.createNewXML1("e://002.doc");
//		EWordXml wordxml = new EWordXml();
//		wordxml.readTemlete("e://test.doc");
//		String aa=wordxml.getBookMark("zs");
//		System.out.println(aa);
	}
}
