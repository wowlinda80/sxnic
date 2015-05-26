package net.sxnic.comm.utils;

import java.io.IOException;   
import org.dom4j.Document;   
import org.dom4j.DocumentHelper;   
import org.dom4j.Element;   
/**  
 * 使用dom4j生成XML工具类  
 *   
 * @author Sarin  
 *   
 */  
public class XMLUtil {   
    private Document document = null;   
  
    public Document getDocument() {   
        return document;   
    }   
    /**  
     * 构造方法，初始化Document  
     */  
    public XMLUtil() {   
        document = DocumentHelper.createDocument();   
    }   
    /**  
     * 生成根节点  
     *   
     * @param rootName  
     * @return  
     */  
    public Element addRoot(String rootName) {   
        Element root = document.addElement(rootName);   
        return root;   
    }   
    /**  
     * 生成节点  
     *   
     * @param parentElement  
     * @param elementName  
     * @return  
     */  
    public Element addNode(Element parentElement, String elementName) {   
        Element node = parentElement.addElement(elementName);   
        return node;   
    }   
    /**  
     * 为节点增加一个属性  
     *   
     * @param thisElement  
     * @param attributeName  
     * @param attributeValue  
     */  
    public void addAttribute(Element thisElement, String attributeName,   
            String attributeValue) {   
        thisElement.addAttribute(attributeName, attributeValue);   
    }   
    /**  
     * 为节点增加多个属性  
     *   
     * @param thisElement  
     * @param attributeNames  
     * @param attributeValues  
     */  
    public void addAttributes(Element thisElement, String[] attributeNames, String[] attributeValues) {   
        for (int i = 0; i < attributeNames.length; i++) {   
            thisElement.addAttribute(attributeNames[i], attributeValues[i]);   
        }   
    }   
    /**  
     * 增加节点的值  
     *   
     * @param thisElement  
     * @param text  
     */  
    public void addText(Element thisElement, String text) {   
        thisElement.addText(text);   
    }   
    /**  
     * 获取最终的XML  
     *   
     * @return  
     * @throws IOException  
     */  
    public String getXML() {   
        return document.asXML().substring(39);   
    }   
    
    public static void main(String[] args)
    {
    	String xmlStr;
        XMLUtil xml = new XMLUtil();   
        Element graph = xml.addRoot("graph");   
        xml.addAttribute(graph, "caption", "访问统计");   
        xml.addAttribute(graph, "subCaption", "浏览器类型统计");   
        xml.addAttribute(graph, "basefontsize", "12");   
        xml.addAttribute(graph, "xAxisName", "浏览器类型");  
        xml.addAttribute(graph, "yAxisName", "亿元");
        xml.addAttribute(graph, "decimalPrecision", "0");// 小数精确度，0为精确到个位   
        xml.addAttribute(graph, "showValues", "0");// 在报表上不显示数值   
        xml.addAttribute(graph, "showNames", "1");// 在饼状图中显示饼的名称
        
        for (int i = 0; i < 11; i++) {   
        	if(i==0){
                Element set = xml.addNode(graph, "set");   
                set.addAttribute("name", "太原");   
                set.addAttribute("value", "1146");   
                set.addAttribute("color", Integer.toHexString(   
                        (int) (Math.random() * 255 * 255 * 255)).toUpperCase()); 
        	}
        	if(i==1){
                Element set = xml.addNode(graph, "set");   
                set.addAttribute("name", "大同");   
                set.addAttribute("value", "715");   
                set.addAttribute("color", Integer.toHexString(   
                        (int) (Math.random() * 255 * 255 * 255)).toUpperCase()); 
        	}
        	if(i==2){
                Element set = xml.addNode(graph, "set");   
                set.addAttribute("name", "朔州");   
                set.addAttribute("value", "464");   
                set.addAttribute("color", Integer.toHexString(   
                        (int) (Math.random() * 255 * 255 * 255)).toUpperCase()); 
        	}
        	if(i==3){
                Element set = xml.addNode(graph, "set");   
                set.addAttribute("name", "忻州");   
                set.addAttribute("value", "522");   
                set.addAttribute("color", Integer.toHexString(   
                        (int) (Math.random() * 255 * 255 * 255)).toUpperCase()); 
        	}
        	if(i==4){
                Element set = xml.addNode(graph, "set");   
                set.addAttribute("name", "吕梁");   
                set.addAttribute("value", "540");   
                set.addAttribute("color", Integer.toHexString(   
                        (int) (Math.random() * 255 * 255 * 255)).toUpperCase()); 
        	}
        	if(i==5){
                Element set = xml.addNode(graph, "set");   
                set.addAttribute("name", "晋中");   
                set.addAttribute("value", "619");   
                set.addAttribute("color", Integer.toHexString(   
                        (int) (Math.random() * 255 * 255 * 255)).toUpperCase()); 
        	}
        	if(i==6){
                Element set = xml.addNode(graph, "set");   
                set.addAttribute("name", "阳泉");   
                set.addAttribute("value", "370");   
                set.addAttribute("color", Integer.toHexString(   
                        (int) (Math.random() * 255 * 255 * 255)).toUpperCase()); 
        	}
        	if(i==7){
                Element set = xml.addNode(graph, "set");   
                set.addAttribute("name", "长治");   
                set.addAttribute("value", "690");   
                set.addAttribute("color", Integer.toHexString(   
                        (int) (Math.random() * 255 * 255 * 255)).toUpperCase()); 
        	}
        	if(i==8){
                Element set = xml.addNode(graph, "set");   
                set.addAttribute("name", "晋城");   
                set.addAttribute("value", "532");   
                set.addAttribute("color", Integer.toHexString(   
                        (int) (Math.random() * 255 * 255 * 255)).toUpperCase()); 
        	}
        	if(i==9){
                Element set = xml.addNode(graph, "set");   
                set.addAttribute("name", "临汾");   
                set.addAttribute("value", "677");   
                set.addAttribute("color", Integer.toHexString(   
                        (int) (Math.random() * 255 * 255 * 255)).toUpperCase()); 
        	}
        	if(i==10){
                Element set = xml.addNode(graph, "set");   
                set.addAttribute("name", "运城市");   
                set.addAttribute("value", "734");   
                set.addAttribute("color", Integer.toHexString(   
                        (int) (Math.random() * 255 * 255 * 255)).toUpperCase()); 
        	}
  
        }   
        xmlStr = xml.getXML().replace("\"", "'");   
    }
}  
