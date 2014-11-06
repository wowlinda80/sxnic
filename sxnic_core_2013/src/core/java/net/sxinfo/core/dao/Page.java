package net.sxinfo.core.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * �����ҳ���һҳ���ݣ�����������ҳ��Ҫ������
 * 
 * @version $Revision: 1.1 $
 * @author �ܺ�
 */
public class Page implements Serializable {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = -5636887113649813132L;

    /**
     * Ҫ��ʾ�ķ�ҳ���
     */
    private List results;

    /**
     * ��ǰҳ��
     */
    private int page;

    /**
     * ÿҳ��������
     */
    private int pageSize;

    /**
     * ��������
     */
    private int totalResults;

    /**
     * ��ҳ��
     */
    private int totalPages;

    /**
     * ������
     * 
     * @param results Ҫ��ʾ�ķ�ҳ���
     * @param page ��ǰҳ��
     * @param pageSize ÿҳ��������
     * @param totalResults ��������
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
     * ��ȡ��һҳ��ҳ�룬������ھͷ��أ����û����һҳ�ͷ���-1
     * 
     * @return ��һҳ��ҳ�룬���û����һҳ�ͷ���-1
     */
    public int getNextPageNumber() {
        if (hasNextPage()) {
            return page + 1;
        }

        return -1;
    }

    /**
     * ��ȡ��һҳ��ҳ�룬���û����һҳ�ͷ���-1
     * 
     * @return ��ȡ��һҳ��ҳ�룬���û����һҳ�ͷ���-1
     */
    public int getPreviousPageNumber() {
        if (hasPreviousPage()) {
            return page - 1;
        }

        return -1;
    }

    /**
     * �Ƿ�Ϊ��һҳ
     * 
     * @return ����ǵ�һҳ�ͷ���true, ���򷵻�false
     */
    public boolean isFirstPage() {
        return page == 1;
    }

    /**
     * �Ƿ�Ϊ���һҳ
     * 
     * @return ��������һҳ�ͷ���true, ���򷵻�false
     */
    public boolean isLastPage() {
        return page >= totalPages;
    }

    /**
     * �Ƿ�����һҳ
     * 
     * @return �������һҳ�ͷ���true, ���򷵻�false
     */
    public boolean hasPreviousPage() {
        return page > 1;
    }

    /**
     * �Ƿ�����һҳ
     * 
     * @return �������һҳ�ͷ���true, ���򷵻�false
     */
    public boolean hasNextPage() {
        return page < totalPages;
    }

    /**
     * ��ȡ��ҳ��Ľ����
     * 
     * @return ��ҳ��Ľ����
     */
    public List getResults() {
        return results;
    }

    /**
     * ��ȡ��ǰҳ��
     * 
     * @return ��ǰҳ��
     */
    public int getPage() {
        return page;
    }

    /**
     * ��ȡÿҳ��������
     * 
     * @return ÿҳ��������
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * ��ȡ��ҳ��
     * 
     * @return ��ҳ��
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * ��ȡ��������
     * 
     * @return ��������
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
