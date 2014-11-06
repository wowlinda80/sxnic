package net.sxnic.comm.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import net.sxinfo.core.dao.Page;
import net.sxinfo.core.util.WebUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.LocalizedTextUtil;

/**
 * 用于显示分页的Tag
 * 
 * @author 曹浩
 */
public class PaginatorTag extends BodyTagSupport {

	/**
	 * commons logging
	 */
	private static final Log log = LogFactory.getLog(PaginatorTag.class);

	private static final int PAGES_BEFORE_CURRENT = 2;

	private static final int PAGES_AFTER_CURRENT = 7;

	private String url;

	private int count;

	private int currentPage;

	private int pageSize;

	private int numPages;

	private Page page;

	private boolean ajax = false;

	private String divName = null;

	public void setAjax(boolean ajax) {
		this.ajax = ajax;
	}

	public void setPage(Page page) {
		this.page = page;

		count = page.getTotalResults();
		currentPage = page.getPage();
		numPages = page.getTotalPages();

		// 设置pageSize
		pageSize = WebUtils.getPageSize((HttpServletRequest) pageContext
				.getRequest());
	}

	/**
	 * @param url
	 *            The url to set.
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	public int doStartTag() throws JspException {
		log.debug("count: " + count + ", numPage: " + numPages + ", pageSize: "
				+ pageSize);

		// 如果页数为1的话就不显示分页信息
		if (numPages > 1) {
			return EVAL_BODY_BUFFERED;
		}

		// 只显示记录数
		JspWriter writer = pageContext.getOut();
		try {
			writer.print(LocalizedTextUtil.findDefaultText(
					"pagination.totalCount", ActionContext.getContext()
							.getLocale(),
					new String[] { String.valueOf(count) }));
			writer.print("<hidden name='currUrl' id ='_currUrlId' value='"
					+ makeLinkNoA(url, String.valueOf(currentPage),
							String.valueOf(currentPage)) + "'/>");
		} catch (IOException e) {
			log.error(e);
			throw new JspException(e.toString());
		}
		return SKIP_BODY;
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	public int doEndTag() throws JspException {
		// 输出body content
		try {
			if (bodyContent != null) {
				String content = bodyContent.getString();
				if (content != null)
					bodyContent.writeOut(bodyContent.getEnclosingWriter());
			}
		} catch (Exception ioe) {
			log.warn(ioe);
		}

		// 如果不需要显示分页信息的话就提前结束
		if (numPages < 2) {
			return EVAL_PAGE;
		}

		// 当前的页号
		StringBuffer buf = new StringBuffer();

		buf.append("<span class=\"paginator\">");
		buf.append("<hidden name='page' value='" + currentPage + "'/>");
		buf.append("<hidden name='currUrl' id ='_currUrlId' value='"
				+ makeLinkNoA(url, String.valueOf(currentPage),
						String.valueOf(currentPage)) + "'/>");
		// buf.append("<table width=\"100%\" border=\"0\" cellpadding=\"2\"
		// cellspacing=\"0\"><tr><td>");

		// 添加总记录数
		buf.append("共" + page.getTotalResults() + "项&nbsp;&nbsp;");

		buf.append("当前是" + currentPage + "/" + page.getTotalPages()
				+ "页&nbsp;&nbsp;");

		// 添加总页数
		// buf.append(LocalizedTextUtil.findDefaultText("pagination.totalPages",
		// ActionContext.getContext().getLocale(), new String[] { String
		// .valueOf(numPages) }));

		// 左边界
		buf.append(" "
				+ LocalizedTextUtil.findDefaultText("pagination.leftBoundary",
						ActionContext.getContext().getLocale()) + "");

		// 如果有前一页的话...
		if (currentPage > 1) {
			// 打印上一页的连接
			buf.append(makeLink(url, LocalizedTextUtil.findDefaultText(
					"pagination.previousPage", ActionContext.getContext()
							.getLocale()), String.valueOf(page.getPage() - 1)));

			// 分隔线
			buf.append(" "
					+ LocalizedTextUtil.findDefaultText("pagination.pipe",
							ActionContext.getContext().getLocale()) + " ");
		}

		int before = currentPage - PAGES_BEFORE_CURRENT;
		if (before <= 0) {
			before = 1;
		}

		// 显示当前页之前的页
		if (before > 1
				&& (before != 2 || currentPage != PAGES_BEFORE_CURRENT + 2)) {
			buf.append("&nbsp;");
			buf.append(makeLink(url, String.valueOf(1), String.valueOf(1)));
			buf.append(" <b>...</b> ");
		}

		if (before == 2 && currentPage == PAGES_BEFORE_CURRENT + 2) {
			buf.append("&nbsp;");
			buf.append(makeLink(url, String.valueOf(1), String.valueOf(1)));
		}

		for (; before < currentPage; before++) {
			buf.append("&nbsp;");
			buf.append(makeLink(url, String.valueOf(before),
					String.valueOf(before)));
		}

		// 显示当前页
		buf.append("&nbsp;");
		buf.append("<span class=\"current\">" + page.getPage() + "</span>");

		int after = currentPage + PAGES_AFTER_CURRENT;

		// 显示页码
		while (currentPage < after && currentPage < numPages) {
			currentPage++;
			buf.append("&nbsp;");
			buf.append(makeLink(url, String.valueOf(currentPage),
					String.valueOf(currentPage)));
		}

		if (currentPage == numPages - 1) {
			currentPage++;
			buf.append("&nbsp;");
			buf.append(makeLink(url, String.valueOf(currentPage),
					String.valueOf(currentPage)));
		}

		// 显示最后一页
		if (numPages > currentPage + 1) {
			buf.append(" <b>...</b> ");
			buf.append(makeLink(url, String.valueOf(numPages),
					String.valueOf(numPages)));
		}

		// 如果有下一页的话...
		if (page.hasNextPage()) {
			// 分隔线
			buf.append(" "
					+ LocalizedTextUtil.findDefaultText("pagination.pipe",
							ActionContext.getContext().getLocale()) + " ");

			buf.append(makeLink(url, LocalizedTextUtil.findDefaultText(
					"pagination.nextPage", ActionContext.getContext()
							.getLocale()), String.valueOf(page.getPage() + 1)));
		}

		// 右边界
		buf.append(""
				+ LocalizedTextUtil.findDefaultText("pagination.rightBoundary",
						ActionContext.getContext().getLocale()));

		// buf.append("</td></tr></table>");
		buf.append("</span>");

		// 输出
		JspWriter writer = pageContext.getOut();
		try {
			writer.print(buf.toString());
		} catch (IOException e) {
			log.error(e);
			throw new JspException(e.toString());
		}

		return EVAL_PAGE;
	}

	/**
	 * 根据指定信息创建一个超链接
	 * 
	 * @param u
	 *            url
	 * @param text
	 *            text
	 * @param pageNumber
	 *            Page Number
	 * @return 超链接
	 */
	private String makeLink(String u, String text, String pageNumber) {
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		StringBuffer link = new StringBuffer();

		link.append("<a href=\"");

		link.append(request.getContextPath() + u
				+ (u.indexOf("?") == -1 ? "?page=" : "&page=") + pageNumber);

		link.append("\">");
		link.append(text);
		link.append("</a>");

		return link.toString();
	}
	
	private String makeLinkNoA(String u, String text, String pageNumber) {
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		StringBuffer link = new StringBuffer();	

		link.append(request.getContextPath() + u
				+ (u.indexOf("?") == -1 ? "?page=" : "&page=") + pageNumber);

		return link.toString();
	}

	/**
	 * @param divName
	 *            the divName to set
	 */
	public void setDivName(String divName) {
		this.divName = divName;
	}
}