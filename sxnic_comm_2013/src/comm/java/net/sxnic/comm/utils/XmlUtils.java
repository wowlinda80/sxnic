package net.sxnic.comm.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @author 孙宇飞
 * @version 2009-2-20 Introduction ：dom4j的工具类
 */

public class XmlUtils {

	public static Document createXmlDocument() {

		Document doc = DocumentHelper.createDocument();

		return doc;
	}

	public static void writetoDocument(File xmlFile, Document doc)
			throws IOException {

		XMLWriter writer = new XMLWriter(new FileWriter(xmlFile));

		writer.write(doc);

		writer.close();

	}

	public static void formatXml(File xmlFile, Document doc)
			throws DocumentException, IOException {
		SAXReader saxReader = new SAXReader();

		Document document = saxReader.read(xmlFile);

		XMLWriter output = null;

		/** 格式化输出,类型IE浏览一样 */
		OutputFormat format = OutputFormat.createPrettyPrint();

		/** 指定XML字符集编码 */
		format.setEncoding("GBK");

		output = new XMLWriter(new FileWriter(xmlFile), format);

		output.write(document);

		output.close();

	}
}
