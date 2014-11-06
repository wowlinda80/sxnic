package net.sxinfo.core.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 代表分页后的一页数据，它将包含分页必要的数据
 * 
 * @version $Revision: 1.1 $
 * @author 曹浩
 */
public class Page implements Serializable {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = -5636887113649813132L;

    /**
     * 要显示的分页结果
     */
    private List results;

    /**
     * 当前页码
     */
    private int page;

    /**
     * 每页的数据量
     */
    private int pageSize;

    /**
     * 总数据量
     */
    private int totalResults;

    /**
     * 总页数
     */
    private int totalPages;

    /**
     * 构造器
     * 
     * @param results 要显示的分页结果
     * @param page 当前页码
     * @param pageSize 每页的数据量
     * @param totalResults 总数据量
     */
    public Page(List results, int page, int pageSize, int totalResults) {
        this.results = results;
        this.page = page;
        this.pageSize = pageSize;
        this.totalResults = totalResults;
        this.totalPages = Double.valueOf(
            Math.ceil((double) totalResults / pageSize)).intValue();
    }

    /**
     * 获取下一页的页码，如果存在就返回，如果没有下一页就返回-1
     * 
     * @return 下一页的页码，如果没有下一页就返回-1
     */
    public int getNextPageNumber() {
        if (hasNextPage()) {
            return page + 1;
        }

        return -1;
    }

    /**
     * 获取上一页的页码，如果没有上一页就返回-1
     * 
     * @return 获取上一页的页码，如果没有上一页就返回-1
     */
    public int getPreviousPageNumber() {
        if (hasPreviousPage()) {
            return page - 1;
        }

        return -1;
    }

    /**
     * 是否为第一页
     * 
     * @return 如果是第一页就返回true, 否则返回false
     */
    public boolean isFirstPage() {
        return page == 1;
    }

    /**
     * 是否为最后一页
     * 
     * @return 如果是最后一页就返回true, 否则返回false
     */
    public boolean isLastPage() {
        return page >= totalPages;
    }

    /**
     * 是否有上一页
     * 
     * @return 如果有上一页就返回true, 否则返回false
     */
    public boolean hasPreviousPage() {
        return page > 1;
    }

    /**
     * 是否有下一页
     * 
     * @return 如果有下一页就返回true, 否则返回false
     */
    public boolean hasNextPage() {
        return page < totalPages;
    }

    /**
     * 获取分页后的结果集
     * 
     * @return 分页后的结果集
     */
    public List getResults() {
        return results;
    }

    /**
     * 获取当前页码
     * 
     * @return 当前页码
     */
    public int getPage() {
        return page;
    }

    /**
     * 获取每页的数据量
     * 
     * @return 每页的数据量
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 获取总页数
     * 
     * @return 总页数
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * 获取总数据量
     * 
     * @return 总数据量
     */
    public int getTotalResults() {
        return totalResults;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Page)) {
            return false;
        }

        final Page p = (Page) o;

        return new EqualsBuilder().append(results, p.getResults()).append(page,
            p.getPage()).append(pageSize, p.getPageSize()).append(totalResults,
            p.getTotalResults()).append(totalPages, p.getTotalPages())
                .isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(results).append(page).append(
            pageSize).append(totalResults).append(totalPages).toHashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("results", results).append(
            "page", page).append("pageSize", pageSize).append("totalResults",
            totalResults).append("totalPages", totalPages).toString();
    }

	public void setResults(List results) {
		this.results = results;
	}
}
