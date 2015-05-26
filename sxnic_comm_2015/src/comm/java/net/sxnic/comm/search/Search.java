package net.sxnic.comm.search;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import net.sxinfo.core.entity.AbstractEntity;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;

/**
 * 全文检索实体类
 * 
 * @author 孙宇飞
 * 
 */

@Entity
@Table(name = "comm_search")
@BatchSize(size = 10)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Search extends AbstractEntity {

	private static final long serialVersionUID = 6778093739663647736L;

	public static final String BELONGER_ALL = "_all";

	/**
	 * 标题，名称
	 */
	private String name;

	/**
	 * 模块名称，其实就是代码中的package名称。如user、dept、aticle、sendoc等
	 */
	@Index(name = "module_name_index")
	private String moduleName;
	
	/**
	 * 相关实体Id
	 */
	private String entityId;

	/**
	 * 所属，某些数据为专用，默认为all
	 */
	private String belonger;

	/**
	 * 访问URL
	 */
	private String url;

	/**
	 * 摘要。需要工具类，把制定对象的所有字段读出来，组合。
	 */
	@Lob
	private String summary;

	/**
	 * 内容
	 */
	@Lob
	private String content;

	public Search() {
		super();
		belonger = BELONGER_ALL;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getBelonger() {
		return belonger;
	}

	public void setBelonger(String belonger) {
		this.belonger = belonger;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

}
