package net.sxnic.comm.taglib;

import java.io.IOException;
import java.text.DecimalFormat;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Comments: 计算附件的大小size <p>
 * Author：孙宇飞<p> 
 * Create Date：2010-9-25<p>
 *
 *
 */
@SuppressWarnings("serial")
public class FileSizeTag extends BodyTagSupport {

	private static Logger logger = LoggerFactory.getLogger(DownloadTag.class);

	private double size;

	public int doStartTag() throws JspException {

		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() {
		JspWriter out = pageContext.getOut();
		DecimalFormat df = new DecimalFormat("###.0");

		try {
			if (size >= 1048576) {
				double result = size / 1048576;
				out.print(df.format(result) + " M");
			} else if (size >= 1024 && size < 1048576) {
				double result = size / 1024;
				out.print(df.format(result) + " K");
			} else if (size < 1024) {
				out.print(size + " B");
			} else {
				out.print("0");
			}

		} catch (IOException e) {
			logger.error("===ERROR FILESIZETAG====" + e.toString());
		}

		return EVAL_PAGE;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

}
