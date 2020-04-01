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
@Table(name = "v_sys_account")
public class VSysAccount implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private String id;
	
	    //
    @Column(name = "DEP")
    private String dep;
	
	    //
    @Column(name = "LOGIN_NAME")
    private String loginName;
	
	    //
    @Column(name = "PASSWORD")
    private String password;
	
	    //
    @Column(name = "SIGNATURE")
    private String signature;
	
	    //
    @Column(name = "STATUS")
    private String status;
	
	    //
    @Column(name = "ORG_ID")
    private String orgId;
	
	    //
    @Column(name = "USER_ID")
    private String userId;
	
	    //
    @Column(name = "HANDLE_TYPE")
    private String handleType;
	
	    //
    @Column(name = "DEVICE_ID")
    private String deviceId;
	
	    //
    @Column(name = "IF_ONLINE")
    private String ifOnline;
	
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
	public void setDep(String dep) {
		this.dep = dep;
	}
	/**
	 * 获取：
	 */
	public String getDep() {
		return dep;
	}
	/**
	 * 设置：
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	/**
	 * 获取：
	 */
	public String getLoginName() {
		return loginName;
	}
	/**
	 * 设置：
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取：
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置：
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}
	/**
	 * 获取：
	 */
	public String getSignature() {
		return signature;
	}
	/**
	 * 设置：
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	/**
	 * 获取：
	 */
	public String getOrgId() {
		return orgId;
	}
	/**
	 * 设置：
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取：
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置：
	 */
	public void setHandleType(String handleType) {
		this.handleType = handleType;
	}
	/**
	 * 获取：
	 */
	public String getHandleType() {
		return handleType;
	}
	/**
	 * 设置：
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	/**
	 * 获取：
	 */
	public String getDeviceId() {
		return deviceId;
	}
	/**
	 * 设置：
	 */
	public void setIfOnline(String ifOnline) {
		this.ifOnline = ifOnline;
	}
	/**
	 * 获取：
	 */
	public String getIfOnline() {
		return ifOnline;
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
