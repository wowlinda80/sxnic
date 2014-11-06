package net.sxnic.comm.log;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import net.sxinfo.core.entity.AbstractEntity;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 日志实体类
 * @author 孙宇飞
 *
 */
@Entity
@Table(name = "comm_log")
@BatchSize(size = 10)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Log extends AbstractEntity {
	
	private static final long serialVersionUID = 4693211819964553088L;
	
	public static final String LOG_OPERATION_READ = "read";
	
	public static final String LOG_OPERATION_UPDATE = "update";
	
	public static final String LOG_OPERATION_DLETE = "delete";
	
	public static final String LOG_OPERATION_CREATE = "create";

	public static final String LOG_IMPORTANT_CATE_001 = "001";
	
	public static final String LOG_IMPORTANT_CATE_002 = "002";
	
	/**
	 * 操作人
	 */
	@Column(name="operator_")
	private String operator;
	
	/**
	 * 操作内容
	 */
	@Column(name="operation_")
	private String operation;
	
	/**
	 * 001一般 002重要，一般不做转移
	 */
	private String cate;
	
	/**
	 * 涉及到的类名,包括完整的包名. net.sxnic.ugr.user.User
	 */
	@Column(name="calssName_")
	private String className;
	
	/**
	 * 实体修改之前的实体
	 */
	@Lob
	@Column(name="oldobj_")
	private byte[] oldObj;
	
	/**
	 * 实体修改之后的实体
	 */
	@Lob
	@Column(name="newobj_")
	private byte[] newObj;

	/**
	 * 如果需要则记录Ip地址
	 */
	@Column(name="ipaddress_")
	private String ipAddress;

	/**
	 * 操作的详细记录（留用）
	 */
	@Column(name="details_",length=1000)
	private String details;

	public Log() {
		super();
		cate =LOG_IMPORTANT_CATE_001;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public byte[] getOldObj() {
		return oldObj;
	}

	public void setOldObj(byte[] oldObj) {
		this.oldObj = oldObj;
	}

	public byte[] getNewObj() {
		return newObj;
	}

	public void setNewObj(byte[] newObj) {
		this.newObj = newObj;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getCate() {
		return cate;
	}

	public void setCate(String cate) {
		this.cate = cate;
	}

}
