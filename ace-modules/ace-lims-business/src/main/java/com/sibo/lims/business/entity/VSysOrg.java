package com.sibo.lims.business.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;


/**
 * 
 * 
 * @author liulong
 * @email 137022680@qq.com
 * @version 2019-01-23 14:06:53
 */
@Table(name = "v_sys_org")
public class VSysOrg implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String id;
	
	    //
    @Column(name = "NAME")
    private String name;
	
	    //
    @Column(name = "CODE")
    private String code;
	
	    //
    @Column(name = "DESCRIBTION")
    private String describtion;
	
	    //
    @Column(name = "CREATE_TIME")
    private BigDecimal createTime;
	
	    //
    @Column(name = "IS_DEL")
    private BigDecimal isDel;
	
	    //
    @Column(name = "LAST_UPD_TIME")
    private BigDecimal lastUpdTime;
	
	    //
    @Column(name = "SORT")
    private BigDecimal sort;
	
	    //
    @Column(name = "VERSION")
    private BigDecimal version;
	

	/**
	 * 设置：
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置：
	 */
	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}
	/**
	 * 获取：
	 */
	public String getDescribtion() {
		return describtion;
	}

	public BigDecimal getCreateTime() {
		return createTime;
	}

	public void setCreateTime(BigDecimal createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getIsDel() {
		return isDel;
	}

	public void setIsDel(BigDecimal isDel) {
		this.isDel = isDel;
	}

	public BigDecimal getLastUpdTime() {
		return lastUpdTime;
	}

	public void setLastUpdTime(BigDecimal lastUpdTime) {
		this.lastUpdTime = lastUpdTime;
	}

	public BigDecimal getSort() {
		return sort;
	}

	public void setSort(BigDecimal sort) {
		this.sort = sort;
	}

	public BigDecimal getVersion() {
		return version;
	}

	public void setVersion(BigDecimal version) {
		this.version = version;
	}
}
