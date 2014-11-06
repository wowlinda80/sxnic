package net.sxnic.comm.utils;
/**
 * 
 * @author ego
 *
 */
public interface JavaToWord {
	public void fillBookmarkText(String bookmarkname, String[] content);
	
	public void fillBookmarkText(String bookmarkname, String content);
	
	public void readTemleteURL(String strXMLURL);
	
	public void readTemleteFile(String strXMLFile);

	public void createReport(String strXMLFile);
	
	public void createNewXML(String strXMLFile);
}
